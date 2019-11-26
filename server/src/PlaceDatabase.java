import javafx.util.Pair;
import org.apache.commons.math3.linear.MatrixUtils;
import org.apache.commons.math3.linear.RealMatrix;
import org.opencv.calib3d.Calib3d;
import org.opencv.core.*;
import org.opencv.imgproc.Imgproc;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.io.*;
import java.util.*;
import java.util.stream.Collectors;
import org.apache.commons.math3.linear.SingularValueDecomposition;



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
	 * Will return a Map from SurfaceName->{model matrix}
	 */
	public HashMap<String, Mat> getModelMatricesForImage(Image image, Mat K) {
		List<Pair<Double, Pair<Image, Mat>>> results =  getLocationList(image);

		if (results.size() == 0) {
			return new HashMap<>();
		}

		HashMap<String, Mat> modelMatrices = new HashMap<>();

		// Get rotation and translation matrices from the image match to the
		// input image using the homography found between them.
		Image firstImage = getImageFromLocationList(0, results);
		List<Mat> RsImage1 = new LinkedList<>(); List<Mat> TsImage1 = new LinkedList<>();
		Calib3d.decomposeHomographyMat(getHFromLocationList(0, results), K, RsImage1, TsImage1, new LinkedList<>());
		Mat R = RsImage1.get(0); Mat T = TsImage1.get(0);

		// For each surface occurring in the original image, get the rotation and translation
		// matrices from that surface to the match image, and use the composed R and T matrices
		// from surface -> match image -> query image to create a model matrix
		for (String surfaceName : firstImage.surfacesOccurring.keySet()) {
			modelMatrices.put(
					surfaceName,
					getModelMatrix(firstImage, surfaceName, R, T)
			);
		}

		// Add any surfaces that were not seen in the first image but that may be
		// occurring in the input image.
		boolean stop = results.size() == 1; int i = 1;

		while (!stop) {
			Image currentImage = getImageFromLocationList(i, results);
			double probability = getProbabilityFromLocationList(i, results);

			if (probability < PROBABILITY_THRESHOLD || i == results.size() - 1) {
				stop = true;
			} else {
				List<Mat> RsCurrentImage = new LinkedList<>(); List<Mat> TsCurrentImage = new LinkedList<>();
				Calib3d.decomposeHomographyMat(
						getHFromLocationList(i, results), K, RsCurrentImage, TsCurrentImage, new LinkedList<>());
				R = RsCurrentImage.get(0); T = TsCurrentImage.get(0);

				for (String surfaceName : currentImage.surfacesOccurring.keySet()) {
					if (!firstImage.surfacesOccurring.containsKey(surfaceName)) {
						modelMatrices.put(
								surfaceName,
								getModelMatrix(currentImage, surfaceName, R, T)
						);
					}
				}

				i++;
			}
		}

		return modelMatrices;
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

	private double getProbabilityFromLocationList(int index, List<Pair<Double, Pair<Image, Mat>>> results) {
		return results.get(index).getKey();
	}

	private Image getImageFromLocationList(int index, List<Pair<Double, Pair<Image, Mat>>> results) {
		return results.get(index).getValue().getKey();
	}

	private Mat getHFromLocationList(int index, List<Pair<Double, Pair<Image, Mat>>> results) {
		return results.get(index).getValue().getValue();
	}

	/*
	 * Given a match image, a surface name, and a Rotation and Translation matrix for converting the
	 * image into the input image, get the combined R and T matrix for converting the surface
	 * into the first image, then the first image into the query image,
	 * and use those to construct a model matrix.
	 */
	private Mat getModelMatrix(Image matchImage, String surfaceName, Mat RMatchToQueryImage, Mat TMatchToQueryImage) {
		Mat modelMatrix = new Mat(4, 4, CV_64F);

		// Multiply the surface->image 1 RT with the image 1->query image RT
		Mat R = new Mat(3, 3, CV_64F);
		Mat T = new Mat(1, 3, CV_64F);
		Core.multiply(matchImage.surfacesOccurring.get(surfaceName).getKey(), RMatchToQueryImage, R);
		Core.multiply(matchImage.surfacesOccurring.get(surfaceName).getValue(), TMatchToQueryImage, T);

		// Convert the rotation matrix into a realmatrix (clunky, but can't find a way
		// to do svd otherwise), with some items negated.
		// Then, multiply the u and vt of the matrix from the svd.
		// Then the R matrix is ready to be put into the model matrix.
		RealMatrix tempRrealmatrix = matToRealMatrix(R);
		tempRrealmatrix.setEntry(0, 0, R.get(0, 0)[0] * 1);
		tempRrealmatrix.setEntry(0, 1, R.get(0, 1)[0] * -1);
		tempRrealmatrix.setEntry(0, 2, R.get(0, 2)[0] * -1);
		tempRrealmatrix.setEntry(1, 0, R.get(1, 0)[0] * -1);
		tempRrealmatrix.setEntry(1, 1, R.get(1, 1)[0] * 1);
		tempRrealmatrix.setEntry(1, 2, R.get(1, 2)[0] * 1);
		tempRrealmatrix.setEntry(2, 0, R.get(2, 0)[0] * -1);
		tempRrealmatrix.setEntry(2, 1, R.get(2, 1)[0] * 1);
		tempRrealmatrix.setEntry(2, 2, R.get(2, 2)[0] * 1);

		// SVD
		SingularValueDecomposition svd = new SingularValueDecomposition(tempRrealmatrix);
		RealMatrix u = svd.getU();
		RealMatrix vt = svd.getVT();
		RealMatrix multipliedUAndVt = u.multiply(vt);
		Mat newR = realMatrixToMat(multipliedUAndVt);

		// Put the R matrix into the model matrix
		modelMatrix.put(0, 0, newR.get(0, 0)[0]);
		modelMatrix.put(0, 1, newR.get(0, 1)[0]);
		modelMatrix.put(0, 2, newR.get(0, 2)[0]);
		modelMatrix.put(1, 0, newR.get(1, 0)[0]);
		modelMatrix.put(1, 1, newR.get(1, 1)[0]);
		modelMatrix.put(1, 2, newR.get(1, 2)[0]);
		modelMatrix.put(2, 0, newR.get(2, 0)[0]);
		modelMatrix.put(2, 1, newR.get(2, 1)[0]);
		modelMatrix.put(2, 2, newR.get(2, 2)[0]);

		// Put the T matrix into the model matrix
		modelMatrix.put(0, 3, T.get(0, 0)[0]);
		modelMatrix.put(1, 3, T.get(1, 0)[0] * -1);
		modelMatrix.put(2, 3, T.get(2, 0)[0] * -1);

		// Bottom row should be 0 0 0 1
		modelMatrix.put(3,0, 0);
		modelMatrix.put(3,1, 0);
		modelMatrix.put(3,2, 0);
		modelMatrix.put(3,3, 1);

		return modelMatrix;
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

	/**** Type conversions... *****/

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

	private Mat realMatrixToMat(RealMatrix m) {
		Mat mat = new Mat(m.getRowDimension(), m.getColumnDimension(), CV_64F);

		for (int i = 0; i < m.getRowDimension(); i++) {
			for (int j = 0; j < m.getColumnDimension(); j++) {
				mat.put(i, j, m.getEntry(i,j));
			}
		}

		return mat;
	}

	private RealMatrix matToRealMatrix(Mat mat) {
		RealMatrix rm = MatrixUtils.createRealMatrix(mat.rows(), (int) mat.row(0).size().width );

		for (int i = 0 ; i < mat.rows(); i ++) {
			for (int j = 0; j < mat.row(i).size().width; j ++) {
				rm.setEntry(i, j, mat.get(i, j)[0]);
			}
		}

		return rm;
	}

	/**** For debugging ****/

	private void printImage(Mat H2, Mat H1, String surfaceName, Image im) {
		Mat destImage = new Mat();

		Imgproc.warpPerspective(
				this.surfaces.get(surfaceName).imageMatrix,
				destImage,
				H1,
				im.imageMatrix.size());

		System.out.println("Homography one");

		Mat finalImage = new Mat();
		Imgproc.warpPerspective(
				destImage,
				finalImage,
				H2,
				im.imageMatrix.size());

		try {
			ImageIO.write(matToBufferedImage(finalImage),
					"png",
					new File("output/" + surfaceName + ".png"));
		} catch (IOException e) {

		}
	}
}
