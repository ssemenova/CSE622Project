import javafx.util.Pair;
import org.opencv.calib3d.Calib3d;
import org.opencv.core.*;
import org.opencv.features2d.*;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;
import org.opencv.xfeatures2d.SIFT;

import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.io.IOException;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import javax.imageio.ImageIO;

import static org.opencv.core.Core.NORM_HAMMING;
import static org.opencv.imgcodecs.Imgcodecs.CV_LOAD_IMAGE_UNCHANGED;
import static org.opencv.imgcodecs.Imgcodecs.imdecode;
import static org.opencv.features2d.ORB.FAST_SCORE;

class Image {
	MatOfKeyPoint keypoints;
	Mat descriptors;
	Mat imageMatrix;
	String name;
	List<DMatch> inliers;
	HashMap<String, Pair<Mat, Mat>>
	//List<Pair<Image, Mat>> surfacesOccurring;

	/* Use this constructor to set up place images in a place database */
	public Image(String path, String filename) {
		this.imageMatrix = Imgcodecs.imread(path, Imgcodecs.CV_LOAD_IMAGE_GRAYSCALE);//, Imgcodecs.IMREAD_GRAYSCALE);

		if (this.imageMatrix.size().equals(new Size(0, 0))) {
			System.out.println("Could not read image!");
		}

		this.name = filename;

		detectKeypointsForSetup();
	}

	/* Use this constructor to set up test images as they arrive from an inputstream
	from an open socket.
	 */
	public Image(byte[] inputStream) {
		this.imageMatrix = imdecode(new MatOfByte(inputStream), CV_LOAD_IMAGE_UNCHANGED);
		this.name = "test image";

		ORB orb = ORB.create();
		this.keypoints = new MatOfKeyPoint();
		this.descriptors = new Mat();
		orb.detectAndCompute(imageMatrix, new Mat(), keypoints, descriptors);
	}

	public void setUpOccurringSurfaces() {
		// TODO
	}

	public void detectKeypointsForSetup() {
		SIFT detector = SIFT.create(500, 3, .01, 50);

		this.descriptors = new Mat();
		this.keypoints = new MatOfKeyPoint();
		detector.detectAndCompute(this.imageMatrix, new Mat(), keypoints, descriptors);
	}

	/*
     * Called by the PlaceDatabase, at an attempt at localization, to retrieve a
     * set of inlying points between this image and the test image.
     */
	public Pair<Mat, Double> featureComparison(Image otherImage) {
		DescriptorMatcher matcher = DescriptorMatcher.create(DescriptorMatcher.FLANNBASED);
		List<MatOfDMatch> knnMatches = new ArrayList<>();
		matcher.knnMatch(otherImage.descriptors, this.descriptors, knnMatches, 2);
		List<DMatch> thresholdedMatches = thresholdKnnResults(knnMatches);

		if (thresholdedMatches.size() < 10) {
			// Can't find a homography with fewer than four matches,
			// but don't bother overfitting if there aren't that
			// many matches to begin with.
			return null;
		}

		Mat H = this.computeHomographyGivenMatches(otherImage, thresholdedMatches);

		drawInliers(thresholdedMatches, otherImage, 1);

		return new Pair(H, thresholdedMatches.size());
	}

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
    private Mat computeHomographyGivenMatches(Image otherImage, List<DMatch> matches) {
		List<Point> obj = new ArrayList<>();
		List<Point> scene = new ArrayList<>();
		List<KeyPoint> listOfKeypointsObject = otherImage.keypoints.toList();
		List<KeyPoint> listOfKeypointsScene = this.keypoints.toList();
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

		this.inliers = inliers;
		return this.inliers.size() == 0 ? null : H;
	}

    /*
     * Draws this image and its keypoints to the specified filename.
     * Use this for testing.
     */
    private void drawImage(String filename) {
        Mat outputImage = new Mat();
        Features2d.drawKeypoints(imageMatrix, keypoints, outputImage);
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
				otherImage.imageMatrix, otherImage.keypoints,
				this.imageMatrix, this.keypoints,
				inliersMat, outImage, color,
				color);

		try {
			ImageIO.write(matToBufferedImage(outImage),
					"png",
					new File("output/" + this.name + " " + index + ".png"));
		} catch(IOException e) {}
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

    public String toString() { return name;
	}
}
