import javafx.util.Pair;
import org.opencv.calib3d.Calib3d;
import org.opencv.core.*;
import org.opencv.features2d.*;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;

import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.io.IOException;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import javax.imageio.ImageIO;

import static org.opencv.core.Core.ROTATE_90_CLOCKWISE;
import static org.opencv.imgcodecs.Imgcodecs.*;
import static org.opencv.imgproc.Imgproc.COLOR_BGR2BGRA;

class Image {
	MatOfKeyPoint keypoints;
	Mat descriptors;
	Mat imageMatrix;
	String name;
	double height;
	double width;
	HashMap<String, Mat> surfacesOccurringH;

	/* Use this constructor to set up place images in a place database */
	public Image(String path, String filename, boolean surface) {
		if (! surface) {
			this.imageMatrix = Imgcodecs.imread(path);//, Imgcodecs.IMREAD_GRAYSCALE);
			//Core.rotate(Imgcodecs.imread(path), this.imageMatrix, ROTATE_90_CLOCKWISE);
		} else {
			this.imageMatrix = Imgcodecs.imread(path);
		}

		if (this.imageMatrix.size().equals(new Size(0, 0))) {
			System.out.println("Could not read image " + filename);
		} else {

			this.height = (double) imageMatrix.height();
			this.width = (double) imageMatrix.width();
			this.name = filename;
			this.surfacesOccurringH = new HashMap<>();

			//Imgcodecs.imwrite("raw " + this.name + ".png", this.imageMatrix);
		/*
		try {
			ImageIO.write(matToBufferedImage(imageMatrix), "png", new File("output/" + filename));
		} catch(IOException e) {
			System.out.println("IOException when drawing image");
		}*/


			detectKeypointsForSetup();
		}
	}

	/* Use this constructor to set up test images as they arrive from an inputstream
	from an open socket.
	 */
	public Image(byte[] inputStream) {
		this.imageMatrix = imdecode(new MatOfByte(inputStream), IMREAD_UNCHANGED);
		this.name = "test image";
		this.height = (double) imageMatrix.height();
		this.width = (double) imageMatrix.width();

		ORB orb = ORB.create();
		this.keypoints = new MatOfKeyPoint();
		this.descriptors = new Mat();
		orb.detectAndCompute(imageMatrix, new Mat(), keypoints, descriptors);
		detectKeypointsForSetup();
	}

	/*
	 * Given a surface descriptor shaped like:
	 * [SurfaceName, (1,2), (3,4), (5,6), (7,8)]
	 * Where each point is the top left, top right, bottom left,
	 * and bottom right destination points in the image,
	 * calculate the homography from the surface image to this image,
	 * and add it to the list of surfaces occurring in this image.
	 */
	public void addSurface(String[] surfaceDescriptor, Image surfaceImage) {
		List<Point> listDestinationPoints = new LinkedList<>();
		for (int i = 1; i <= 4; i++) {
			String[] stringVals = surfaceDescriptor[i].split(",");
			// This might be wrong:
			double[] vals = {
				Double.parseDouble(stringVals[0]), // - (this.width / 2),
				Double.parseDouble(stringVals[1]) // - (this.height / 2)
			};
			listDestinationPoints.add(new Point(vals));
		}

		List<Point> listSourcePoints = new LinkedList<>();
		listSourcePoints.add(new Point(0, 0));
		listSourcePoints.add(new Point(surfaceImage.width, 0));
		listSourcePoints.add(new Point(0, surfaceImage.height));
		listSourcePoints.add(new Point(surfaceImage.width, surfaceImage.height));

		MatOfPoint2f sourcePoints = new MatOfPoint2f();
		sourcePoints.fromList(listSourcePoints);
		MatOfPoint2f destinationPoints = new MatOfPoint2f();
		destinationPoints.fromList(listDestinationPoints);

		Mat H = Imgproc.getPerspectiveTransform(sourcePoints, destinationPoints);

		/* Uncomment to print images to test
		Mat destImage = new Mat();
		Imgproc.warpPerspective(surfaceImage.imageMatrix, destImage, H, this.imageMatrix.size());

		try {
			ImageIO.write(matToBufferedImage(destImage),
					"png",
					new File("output/" + this.name + " " + surfaceDescriptor[0] + ".png"));
		} catch (IOException e) {

		}
		 */

		this.surfacesOccurringH.put(surfaceDescriptor[0], H);

		// Decompose homography into Rs and Ts. Just accept the first solution.
		//List<Mat> Rs = new LinkedList<>();
		//List<Mat> Ts = new LinkedList<>();
		//List<Mat> normals = new LinkedList<>();
		//Calib3d.decomposeHomographyMat(H, getK(), Rs, Ts, normals);

		//this.surfacesOccurring.put(surfaceDescriptor[0], new Pair(Rs.get(0), Ts.get(0)));
	}

	public void detectKeypointsForSetup() {
		ORB detector = ORB.create(1000);

		this.descriptors = new Mat();
		this.keypoints = new MatOfKeyPoint();
		detector.detectAndCompute(this.imageMatrix, new Mat(), keypoints, descriptors);
		//convertKeypoints();
	}

	/*
	 * Converts keypoints so the 0,0 point is at the center of the screen,
	 * vs openCV's default upper left corner, and so that the entire
	 * pixel coordinate range is from -1 to 1.
	 */
	private void convertKeypoints() {
		KeyPoint[] kps = this.keypoints.toArray();

		double halfWidth = this.imageMatrix.width() / 2;
		double halfHeight = this.imageMatrix.height() / 2;

		for (KeyPoint kp : kps) {
			double[] newPoint = {
				kp.pt.x - halfWidth,
				kp.pt.y - halfHeight};
			kp.pt = new Point(newPoint);
		}

		this.keypoints.fromArray(kps);
	}
	/*

     * Called by the PlaceDatabase, at an attempt at localization, to retrieve a
     * set of inlying points between this image and the test image.
     */
	public Pair<Mat, Double> featureComparison(Image otherImage, int i) {
		BFMatcher matcher = BFMatcher.create(Core.NORM_HAMMING);
		List<MatOfDMatch> knnMatches = new ArrayList<>();
		matcher.knnMatch(this.descriptors, otherImage.descriptors, knnMatches, 2);
		List<DMatch> thresholdedMatches = thresholdKnnResults(knnMatches);

		if (thresholdedMatches.size() < 10) {
			// Can't find a homography with fewer than four matches,
			// but don't bother overfitting if there aren't that
			// many matches to begin with.
			return null;
		}

		Pair<Mat, List<DMatch>> homographyResults = this.computeHomographyGivenMatches(otherImage, thresholdedMatches);

		if (homographyResults == null) {
			return null;
		}

		Mat H = homographyResults.getKey();
		List<DMatch> inliers = homographyResults.getValue();

//		drawProjection("infeaturecomparison ", homographyResults.getKey(), this);

		// Might be better to use a measure of probability that isn't just
		// how many feature matches there were.
		return new Pair(H, (double) inliers.size() / (double) thresholdedMatches.size());
	}

	/*
	 * Threshold test for feature match results.
	 */
	private List<DMatch> thresholdKnnResults(List<MatOfDMatch> knnMatches) {
		float ratioThresh = 0.6f;
		List<DMatch> thresholdedMatches = new ArrayList<>();
		for (int i = 0; i < knnMatches.size(); i++) {
			if (knnMatches.get(i).rows() > 1) {
				DMatch[] matches = knnMatches.get(i).toArray();
				if (matches[0].distance < ratioThresh * matches[1].distance) {
					thresholdedMatches.add(matches[0]);
				}
			}
		}
		return thresholdedMatches;
	}

	/*
     * Computes a homography, using RANSAC, for a given other image and set of matches
     * between that image and this one. Returns the list of inliers.
     */
    private Pair<Mat, List<DMatch>> computeHomographyGivenMatches(Image otherImage, List<DMatch> matches) {
		List<Point> obj = new ArrayList<>();
		List<Point> scene = new ArrayList<>();
		List<KeyPoint> listOfKeypointsObject = this.keypoints.toList();
		List<KeyPoint> listOfKeypointsScene = otherImage.keypoints.toList();
		for (int i = 0; i < matches.size(); i++) {
			obj.add(listOfKeypointsObject.get(matches.get(i).queryIdx).pt);
			scene.add(listOfKeypointsScene.get(matches.get(i).trainIdx).pt);
		}
		MatOfPoint2f objMat = new MatOfPoint2f(), sceneMat = new MatOfPoint2f();
		objMat.fromList(obj);
		sceneMat.fromList(scene);
		double ransacReprojThreshold = 5.0;
		Mat mask = new Mat();
		Mat H = Calib3d.findHomography( objMat, sceneMat, Calib3d.RANSAC, ransacReprojThreshold, mask);

		List<DMatch> inliers = new LinkedList<>();
		for (int i = 0; i < matches.size(); i++) {
			if (mask.get(i, 0)[0] != 0) {
				inliers.add(matches.get(i));
			}
		}

		return inliers.size() == 0 ? null : new Pair(H, inliers);
	}


	/**************** STUFF FOR DEBUGGING ****************/

    /*
     * Draws this image and its keypoints to the specified filename.
     * Use this for testing.
     */
    public void drawImage(String filename) {
        Mat outputImage = new Mat();
        Features2d.drawKeypoints(imageMatrix, keypoints, outputImage);
        try {
            ImageIO.write(matToBufferedImage(outputImage), "png", new File("output/" + filename));
        } catch(IOException e) {
            System.out.println("IOException when drawing image");
        }
    }

    public void drawProjection(String filename, Mat H, Image originalImage) {
		Mat outputImage = new Mat();
		Imgproc.warpPerspective(this.imageMatrix, outputImage, H, originalImage.imageMatrix.size());

		try {
			ImageIO.write(matToBufferedImage(outputImage), "png", new File("output/" + filename));
		} catch(IOException e) {
			System.out.println("IOException when drawing image");
		}
	}

    /*
     * Draws this image with a supplied other image, and lines between each feature match.
     */
    private void drawInliers(List<DMatch> inliersList, Image otherImage, int index) {
		Mat outImage = new Mat();
		MatOfDMatch inliersMat = new MatOfDMatch();
		inliersMat.fromList(inliersList);
		Scalar color = new Scalar( 255, 0, 0 );
		Features2d.drawMatches(
				this.imageMatrix, this.keypoints,
				otherImage.imageMatrix, otherImage.keypoints,
				inliersMat, outImage, color,
				color);

		try {
			ImageIO.write(matToBufferedImage(outImage),
					"png",
					new File("output/inliersfor" + this.name + " " + index + ".png"));
		} catch(IOException e) {}
	}

    /*
	 * Converts a buffered image to a mat
	 */
    private BufferedImage matToBufferedImage(Mat m) {
		if (!m.empty()) {
			int type = BufferedImage.TYPE_BYTE_GRAY;
			if (m.channels() > 1) {
				type = BufferedImage.TYPE_4BYTE_ABGR;
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

    public String toString() { return name;
	}

	private static Mat getK() {
		double kFlat[] = {1555.823494, 0, 959.5, 0, 1555.823494, 539.5, 0, 0, 1};
		Mat K = new Mat(3, 3, CvType.CV_32F);
		int row = 0, col = 0;
		K.put(row, col, kFlat);
		return K;
	}
}
