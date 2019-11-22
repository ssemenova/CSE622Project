import javafx.util.Pair;
import org.opencv.calib3d.Calib3d;
import org.opencv.core.*;
import org.opencv.imgproc.Imgproc;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

import static org.opencv.core.CvType.CV_64F;

class PlaceDatabase {
    HashMap<String, Image> images;
    HashMap<String, Image> surfaces;
	double LOCALIZATION_THRESHOLD = .5;
	double PROBABILITY_THRESHOLD = .3;

    public PlaceDatabase(String dbName) {
		System.out.println("CREATING DATABASE");
		images = new HashMap<>();
		surfaces = new HashMap<>();

		setUpSurfaces(dbName);
		setUpPlaces(dbName);
		setUpSurfaceOccurrencesFromDataFile(dbName);
		System.out.println("DONE WITH DATABASE CREATION");
    }

	private void setUpSurfaces(String dbName) {
		File surfacesFolder = new File(dbName + "surfaces");
		File[] surfacesList = surfacesFolder.listFiles();

		for (File surface : surfacesList) {
			String surfaceFileName = surface.getName();
			String surfaceName = surfaceFileName.substring(0, surfaceFileName.lastIndexOf('.'));
			this.surfaces.put(surfaceName,
				new Image(
					dbName + "surfaces/" + surface.getName(),
					surfaceName
				));
		}
	}

	private void setUpPlaces(String dbName) {
		File placesFolder= new File(dbName + "places");

		File[] imageFiles = placesFolder.listFiles();
		this.images = new HashMap<>();

		for (File imageFile : imageFiles) {
			String imageFileName = imageFile.getName();
			String imageName = imageFileName.substring(0, imageFileName.lastIndexOf('.'));
			if (!imageFileName.equals(".DS_Store") && !imageFileName.contains("occurrence")) {
				this.images.put(imageName,
					new Image(
						dbName + "places/" + imageFileName,
						imageName));
			}
		}
	}

	private void setUpSurfaceOccurrencesFromDataFile(String dbName) {
    	File file = new File(dbName + "places/surface_occurrences.txt");

		try {
			BufferedReader br = new BufferedReader(new FileReader(file));

			String line;
			while ((line = br.readLine()) != null) {
				Image im = this.images.get(line);
				while (!(line = br.readLine()).equals("----")) {
					String[] surfaceString = line.split("\\s+");
					Image i = this.surfaces.get(surfaceString[0]);
					im.addSurface(surfaceString, i);
				}
			}
		} catch (FileNotFoundException e) {
			System.out.println("Surfaces file not found!");
		} catch (IOException e) {
			System.out.println("Could not read from file!");
		}
	}

	/*
	 * Public method for Server to call.
	 * Will return a Map from SurfaceName->{R matrix, T matrix}
	 * For all occurring surfaces, to transform the surface into the input image's 2d space.
	 */
	public HashMap<String, Pair<Mat, Mat>> getRAndTForInputImage(Image image, Mat K) {
		List<Pair<Double, Pair<Image, Mat>>> results =  getLocationList(image);
		HashMap<String, Pair<Mat, Mat>> finalRTs = new HashMap<>();

		// Get rotation and translation matrices from the image match to the
		// input image using the homography found between them.
		Image firstImage = getImageFromResultsList(0, results);
		Pair<Mat, Mat> RAndTForImage1 = decomposeHomography(K, getHFromResultsList(0, results));

		// For each surface occurring in the original image, get the rotation and translation
		// matrices from that surface to the match image, and save the composed R and T matrices
		// from surface -> match image -> query image.
		for (String surfaceName : firstImage.surfacesOccurring.keySet()) {
			finalRTs.put(
					surfaceName,
					multiplyRTs(firstImage, surfaceName, RAndTForImage1)
			);
		}

		// Add any surfaces that were not seen in the first image but that may be
		// occurring in the input image.
		boolean stop = false; int i = 1;

		while (!stop) {
			Image currentImage = getImageFromResultsList(i, results);
			double probability = getProbabilityFromResultsList(i, results);

			if (probability < PROBABILITY_THRESHOLD || i == results.size() - 1) {
				stop = true;
			} else {
				Pair<Mat, Mat> RAndTForCurrentImage = decomposeHomography(K, getHFromResultsList(0, results));

				for (String surfaceName : currentImage.surfacesOccurring.keySet()) {
					if (!firstImage.surfacesOccurring.containsKey(surfaceName)) {
						finalRTs.put(
								surfaceName,
								multiplyRTs(currentImage, surfaceName, RAndTForCurrentImage)
						);
					}
				}

				i++;
			}
		}

		MatOfPoint3f cube = new MatOfPoint3f();
		cube.push_back(new MatOfPoint3f(new Point3(0, 0, 0.0f)));
		double[] bla = {0, 0, 0, 0, 0, 0, 0, 0};
		MatOfDouble distCoeffs = new MatOfDouble();
		distCoeffs.fromArray(bla);

		for (String surfaceName : finalRTs.keySet()){
			Mat R = finalRTs.get(surfaceName).getKey();
			Mat T = finalRTs.get(surfaceName).getValue();
		}

		return finalRTs;
	}

	/*
	 * Given an input image, get a list of
	 * {Double-> {Image -> Mat}}
	 * pairs, where the double is the probability that the input image is at that location,
	 * the image is the comparison image,
	 * and the mat is the homography translating the comparison image to the input image.
	 * The list is sorted by probability.
	 */
    private List<Pair<Double, Pair<Image, Mat>>> getLocationList(Image imToLocalize) {
    	List<Pair<Double, Pair<Image, Mat>>> results = new LinkedList<>();

		for (String imageName : this.images.keySet()) {
			Image currentImage = this.images.get(imageName);

			Pair<Mat, Double> featureComparisonForImage = currentImage.featureComparison(imToLocalize);
			if (featureComparisonForImage != null) {
				Mat currentH = featureComparisonForImage.getKey();
				double currentP = featureComparisonForImage.getValue();

				results.add(new Pair(
						currentP,
						new Pair(currentImage, currentH)
				));

			}
		}

		List<Pair<Double, Pair<Image, Mat>>> sortedResults = results.stream()
				.sorted(Comparator.comparing(Pair::getKey))
				.collect(Collectors.toList());

		return sortedResults;
	}

	private double getProbabilityFromResultsList(int index, List<Pair<Double, Pair<Image, Mat>>> results) {
		return results.get(index).getKey();
	}

	private Image getImageFromResultsList(int index, List<Pair<Double, Pair<Image, Mat>>> results) {
		return results.get(index).getValue().getKey();
	}

	private Mat getHFromResultsList(int index, List<Pair<Double, Pair<Image, Mat>>> results) {
		return results.get(index).getValue().getValue();
	}

	/*
	 * Given an image, a surface name, and another rotation and translation,
	 * get the RT from the surface->image, and multiply that by the given RT.
	 */
	private Pair<Mat, Mat> multiplyRTs(
			Image image,
			String surfaceName,
			Pair<Mat, Mat> image1ToInputImageRAndT) {
		Pair<Mat, Mat> surfaceToImage1RAndT = image.surfacesOccurring.get(surfaceName);

		Mat multipliedR = new Mat(3, 3, CV_64F);
		Mat multipliedT = new Mat(1, 3, CV_64F);
		Core.multiply(surfaceToImage1RAndT.getKey(), image1ToInputImageRAndT.getKey(), multipliedR);
		Core.multiply(surfaceToImage1RAndT.getValue(), image1ToInputImageRAndT.getValue(), multipliedT);

		return new Pair(multipliedR, multipliedT);
	}

	/*
	 * Return the first answer for finding a homography decomposition given a K and H.
	 */
	private Pair<Mat, Mat> decomposeHomography(Mat K, Mat H) {
		List<Mat> Rs = new LinkedList<>();
		List<Mat> Ts = new LinkedList<>();
		Calib3d.decomposeHomographyMat(H, K, Rs, Ts, new LinkedList<>());

		return new Pair(Rs.get(0), Ts.get(0));
	}

	/*
	 * Converts a buffered image to a mat
	 */
	private BufferedImage matToBufferedImage(Mat m) {
		if (!m.empty()) {
			int type = BufferedImage.TYPE_BYTE_GRAY;
			if (m.channels() > 1) {
				type = BufferedImage.TYPE_3BYTE_BGR;
			}
			int bufferSize = m.channels() * m.cols() * m.rows();
			byte[] b = new byte[bufferSize];
			m.get(0, 0, b);
			BufferedImage image = new BufferedImage(m.cols(), m.rows(), type);
			final byte[] targetPixels = ((DataBufferByte) image.getRaster().getDataBuffer()).getData();
			System.arraycopy(b, 0, targetPixels, 0, b.length);
			return image;
		}

		return null;
	}

}
