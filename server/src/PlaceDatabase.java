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

import static org.opencv.core.Core.DECOMP_SVD;
import static org.opencv.core.CvType.CV_64F;

class PlaceDatabase {
    HashMap<String, Image> images;
    HashMap<String, Image> surfaces;
	double PROBABILITY_THRESHOLD = .7;
	Mat K;
	Mat inverseK;

    public PlaceDatabase(String dbName, Mat K) {
		System.out.println("CREATING DATABASE");
		this.K = K;
		this.inverseK = getInverseCameraMatrix();

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
	public HashMap<String, Mat> getModelMatricesForImage(Image queryImage) {
		List<Pair<Double, Pair<Image, Pair<Mat, Mat>>>> results =  getLocationList(queryImage);

		if (results.size() == 0) {
			return new HashMap<>();
		}

		HashMap<String, Mat> modelMatrices = new HashMap<>();

		// Get rotation and translation matrices from the image match to the
		// input image using the homography found between them.
		Image firstImage = getImageFromLocationList(results.size() - 1, results);
		Pair<Mat, Mat> RTMatchToQuery = getRTFromLocationList(0, results);

		// For each surface occurring in the original image, get the rotation and translation
		// matrices from that surface to the match image, and use the composed R and T matrices
		// from surface -> match image -> query image to create a model matrix
		for (String surfaceName : firstImage.surfacesOccurringRandT.keySet()) {
			Pair<Mat, Mat> RTSurfaceToMatch = firstImage.surfacesOccurringRandT.get(surfaceName);

			System.out.println(firstImage);
			modelMatrices.put(
				surfaceName,
				getModelMatrix(RTMatchToQuery, RTSurfaceToMatch)
			);

			/*
			modelMatrices.put(

					surfaceName,
					getModelMatrix(firstImage, surfaceName, getHFromLocationList(0, results))
			);
			*/
		}

		// Add any surfaces that were not seen in the first image but that may be
		// occurring in the input image.
		boolean stop = results.size() == 2; int i = 1;

		while (!stop) {
			Image currentImage = getImageFromLocationList(results.size() - i, results);
			double probability = getProbabilityFromLocationList(i, results);

			if (probability < PROBABILITY_THRESHOLD || i == results.size() - 1) {
				stop = true;
			} else {
				RTMatchToQuery = getRTFromLocationList(i, results);


				for (String surfaceName : currentImage.surfacesOccurringRandT.keySet()) {
					if (!firstImage.surfacesOccurringRandT.containsKey(surfaceName)) {
						Pair<Mat, Mat> RTSurfaceToMatch = currentImage.surfacesOccurringRandT.get(surfaceName);

						modelMatrices.put(
							surfaceName,
							getModelMatrix(RTMatchToQuery, RTSurfaceToMatch)
						);

						/*
						modelMatrices.put(
								surfaceName,
								getModelMatrix(currentImage, surfaceName, getHFromLocationList(i, results))
						);*/
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
	private List<Pair<Double, Pair<Image, Pair<Mat, Mat>>>> getLocationList(Image imToLocalize) {
		List<Pair<Double, Pair<Image, Pair<Mat, Mat>>>> results = new LinkedList<>();

		int i = 0;

		for (String imageName : this.images.keySet()) {
			Image currentImage = this.images.get(imageName);

			Pair<Double, Pair<Mat, Mat>> featureComparisonForImage = currentImage.featureComparison(imToLocalize, i);
			if (featureComparisonForImage != null) {
				Pair<Mat, Mat> RandT = featureComparisonForImage.getValue();
				double currentP = featureComparisonForImage.getKey();

				results.add(new Pair(
						currentP,
						new Pair(currentImage, RandT)
				));

			}
			i++;
		}

		List<Pair<Double, Pair<Image, Pair<Mat, Mat>>>> sortedResults = results.stream()
				.sorted(Comparator.comparing(Pair::getKey))
				.collect(Collectors.toList());

		System.out.println(sortedResults.get(0).getKey());
		System.out.println(sortedResults.get(sortedResults.size() - 1).getKey());

		return sortedResults;
	}

	private double getProbabilityFromLocationList(int index, List<Pair<Double, Pair<Image, Pair<Mat, Mat>>>> results) {
		return results.get(index).getKey();
	}

	private Image getImageFromLocationList(int index, List<Pair<Double, Pair<Image, Pair<Mat, Mat>>>> results) {
		return results.get(index).getValue().getKey();
	}

	private Pair<Mat, Mat> getRTFromLocationList(int index, List<Pair<Double, Pair<Image, Pair<Mat, Mat>>>> results) {
		return results.get(index).getValue().getValue();
	}

	private Mat matrixMultiply(Mat m1, Mat m2, int rows, int cols) {
		Mat response = new Mat(rows, cols, CV_64F);
		Core.gemm(m1, m2, 1, Mat.zeros(rows, cols, CV_64F) , 0, response, 0);
		return response;
	}

	private Mat getModelMatrix(Pair<Mat,Mat> RTMatchToQuery, Pair<Mat,Mat> RTSurfaceToMatch) {
		Mat modelMatrix = new Mat(4, 4, CV_64F);

		// Multiply the surface->image 1 homography with the image 1->query image homography
		//Mat R = matrixMultiply(RTMatchToQuery.getKey(), RTSurfaceToMatch.getKey(), 3, 3);
		Mat RMatchToQueryRodrigues = new Mat(3, 3, CV_64F);
		Mat RSurfaceToMatchRodrigues = new Mat(3, 3, CV_64F);
		Calib3d.Rodrigues(RTMatchToQuery.getKey(), RMatchToQueryRodrigues);
		Calib3d.Rodrigues(RTSurfaceToMatch.getKey(), RSurfaceToMatchRodrigues);
		Mat R = matrixMultiply(RMatchToQueryRodrigues, RSurfaceToMatchRodrigues, 3, 3);

		Mat T = new Mat(3, 1, CV_64F);
		T.put(0, 0, (RTMatchToQuery.getValue().get(0, 0)[0] + RTSurfaceToMatch.getValue().get(0, 0)[0]) / 1080);
		T.put(1, 0, (RTMatchToQuery.getValue().get(1, 0)[0] + RTSurfaceToMatch.getValue().get(1, 0)[0]) / 1980);
		T.put(2, 0, RTMatchToQuery.getValue().get(2, 0)[0] + RTSurfaceToMatch.getValue().get(2, 0)[0]);

		/*

		// Get the new center coordinates of the surface

		Mat offset = new Mat(3, 1, CV_64F);
		offset.put(0, 0, 1080 / 2);
		offset.put(1, 0, 1980 / 2);
		offset.put(2, 0, 0);
		Mat centers = matrixMultiply(R, offset, 3, 1);

		System.out.println(centers.dump());
*/
		System.out.println("final translation " + T.dump());
		System.out.println("final rotation " + R.dump());

		List<Point3> listSourcePoints = new LinkedList<>();
		listSourcePoints.add(new Point3(821, 915, 0));
		listSourcePoints.add(new Point3(1459,720, 0));
		listSourcePoints.add(new Point3(1347,1603, 0));
		listSourcePoints.add(new Point3(2056,1287, 0));
		MatOfPoint3f sourcePoints = new MatOfPoint3f();
		sourcePoints.fromList(listSourcePoints);


		MatOfPoint2f imgPts = new MatOfPoint2f();
		//MatOfPoint3f objectPoints, Mat rvec, Mat tvec, Mat cameraMatrix, MatOfDouble distCoeffs, MatOfPoint2f imagePoints
		Calib3d.projectPoints(sourcePoints, RTMatchToQuery.getKey(), RTMatchToQuery.getValue(), this.K, new MatOfDouble(), imgPts);

		System.out.println(imgPts.dump());

		/*
		T.put(0, 0, T.get(0, 0)[0] + centers.get(0, 0)[0] / 5000);
		T.put(1, 0, T.get(1, 0)[0] + centers.get(1, 0)[0] / 5000);
		T.put(2, 0, T.get(2, 0)[0] + centers.get(2, 0)[0] / 5000);
*/

		// Put the R matrix into the model matrix
		modelMatrix.put(0, 0, R.get(0, 0)[0]);
		modelMatrix.put(1, 0, - R.get(0, 1)[0]);
		modelMatrix.put(2, 0, - R.get(0, 2)[0]);
		modelMatrix.put(0, 1, - R.get(1, 0)[0]);
		modelMatrix.put(1, 1, R.get(1, 1)[0]);
		modelMatrix.put(2, 1, R.get(1, 2)[0]);
		modelMatrix.put(0, 2, - R.get(2, 0)[0]);
		modelMatrix.put(1, 2, R.get(2, 1)[0]);
		modelMatrix.put(2, 2, R.get(2, 2)[0]);

		// Put the T matrix into the model matrix
		modelMatrix.put(3, 0, T.get(0, 0)[0]);
		modelMatrix.put(3, 1, - T.get(1, 0)[0]);
		modelMatrix.put(3, 2, - T.get(2, 0)[0]);

		// Bottom row should be 0 0 0 1
		modelMatrix.put(0,3, 0);
		modelMatrix.put(1,3, 0);
		modelMatrix.put(2,3, 0);
		modelMatrix.put(3,3, 1);

		return modelMatrix;

	}

	/*
	 * Given a match image, a surface name, and a Rotation and Translation matrix for converting the
	 * image into the input image, get the combined R and T matrix for converting the surface
	 * into the first image, then the first image into the query image,
	 * and use those to construct a model matrix.
	 */
	private Mat getModelMatrix(Mat H) {
		Mat modelMatrix = new Mat(4, 4, CV_64F);

		// Decompose homography into translation and rotation vectors
		Mat inverseCameraMatrixLocal = new Mat(3, 3, CV_64F);

		// Column vectors of homography
		Mat h1 = H.col(0);
		Mat h2 = H.col(1);
		Mat h3 = H.col(2);

		Mat inverseH1 = matrixMultiply(this.inverseK, h1, 3, 3);

		// Calculate a length for normalizing
		double lambda = Math.sqrt(
			h1.get(0, 0)[0] * h1.get(0, 0 )[0] +
			h1.get(1,0)[0] * h1.get(1,0)[0] +
			h1.get(2,0)[0] * h1.get(2,0)[0]
		);

		Mat rotationMatrix = new Mat(3,3, CV_64F);

		// Normalize inverseCameraMatrix
		if (lambda != 0) {
			Scalar s = new Scalar(1 / lambda);
			Core.multiply(inverseK, s, inverseCameraMatrixLocal);
		}

		// Column vectors of rotation matrix
		Mat r1 = matrixMultiply(inverseCameraMatrixLocal, h1, 3, 1);
		Mat r2 = matrixMultiply(inverseCameraMatrixLocal, h2, 3, 1);
		Mat r3 = r1.cross(r2);

		// Put rotation columns into rotation matrix
		rotationMatrix.put(0, 0, r1.get(0,0)[0]);
		rotationMatrix.put(0, 1, r2.get(0,0)[0] * -1);
		rotationMatrix.put(0, 2, r3.get(0,0)[0] * -1);
		rotationMatrix.put(1, 0, r1.get(1,0)[0] * -1);
		rotationMatrix.put(1, 1, r2.get(1,0)[0]);
		rotationMatrix.put(1, 2, r3.get(1,0)[0]);
		rotationMatrix.put(2, 0, r1.get(2,0)[0] * -1);
		rotationMatrix.put(2, 1, r2.get(2,0)[0]);
		rotationMatrix.put(2, 2, r3.get(2,0)[0]);

		// Translation vector T
		Mat translationVector = matrixMultiply(inverseCameraMatrixLocal, h3, 3, 1);
		translationVector.put(1, 0, translationVector.get(1, 0)[0] * -1);
		translationVector.put(2, 0, translationVector.get(2, 0)[0] * -1);

		// Convert the rotation matrix into a realmatrix (clunky, but can't find a way
		// to do svd otherwise). Then, multiply the u and vt of the matrix from the svd.
		// Then the R matrix is ready to be put into the model matrix.
		RealMatrix tempRrealmatrix = matToRealMatrix(rotationMatrix);
		tempRrealmatrix.setEntry(0, 0, rotationMatrix.get(0, 0)[0]);
		tempRrealmatrix.setEntry(0, 1, rotationMatrix.get(0, 1)[0]);
		tempRrealmatrix.setEntry(0, 2, rotationMatrix.get(0, 2)[0]);
		tempRrealmatrix.setEntry(1, 0, rotationMatrix.get(1, 0)[0]);
		tempRrealmatrix.setEntry(1, 1, rotationMatrix.get(1, 1)[0]);
		tempRrealmatrix.setEntry(1, 2, rotationMatrix.get(1, 2)[0]);
		tempRrealmatrix.setEntry(2, 0, rotationMatrix.get(2, 0)[0]);
		tempRrealmatrix.setEntry(2, 1, rotationMatrix.get(2, 1)[0]);
		tempRrealmatrix.setEntry(2, 2, rotationMatrix.get(2, 2)[0]);

		// SVD
		SingularValueDecomposition svd = new SingularValueDecomposition(tempRrealmatrix);
		RealMatrix u = svd.getU();
		RealMatrix vt = svd.getVT();
		RealMatrix multipliedUAndVt = u.multiply(vt);
		rotationMatrix = realMatrixToMat(multipliedUAndVt);

		// Put the R matrix into the model matrix
		modelMatrix.put(0, 0, rotationMatrix.get(0, 0)[0]);
		modelMatrix.put(1, 0, rotationMatrix.get(0, 1)[0]);
		modelMatrix.put(2, 0, rotationMatrix.get(0, 2)[0]);
		modelMatrix.put(0, 1, rotationMatrix.get(1, 0)[0]);
		modelMatrix.put(1, 1, rotationMatrix.get(1, 1)[0]);
		modelMatrix.put(2, 1, rotationMatrix.get(1, 2)[0]);
		modelMatrix.put(0, 2, rotationMatrix.get(2, 0)[0]);
		modelMatrix.put(1, 2, rotationMatrix.get(2, 1)[0]);
		modelMatrix.put(2, 2, rotationMatrix.get(2, 2)[0]);

		// Put the T matrix into the model matrix
		modelMatrix.put(3, 0, translationVector.get(0, 0)[0]);
		modelMatrix.put(3, 1, - translationVector.get(1, 0)[0]);
		modelMatrix.put(3, 2, - translationVector.get(2, 0)[0]);

		// Bottom row should be 0 0 0 1
		modelMatrix.put(0,3, 0);
		modelMatrix.put(1,3, 0);
		modelMatrix.put(2,3, 0);
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

	/*
	* Used in the function that gets the model matrix
	*/
	private Mat getInverseCameraMatrix() {
		Mat inverseK = new Mat(3,3, CV_64F);

		inverseK.put(0, 0, (1 / K.get(0, 0)[0]));
		inverseK.put(0, 1, 0);
		inverseK.put(0, 2, 0 - (K.get(0, 2)[0] / K.get(0,0)[0]));
		inverseK.put(1, 0, 0);
		inverseK.put(1, 1, 1/K.get(1,1)[0]);
		inverseK.put(1, 2, 0 - (K.get(1,2)[0] / K.get(1,1)[0]));
		inverseK.put(2, 0, 0);
		inverseK.put(2, 1, 0);
		inverseK.put(2, 2, 1);

		return inverseK;
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
