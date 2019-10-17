import org.opencv.calib3d.Calib3d;
import org.opencv.core.*;
import org.opencv.features2d.BFMatcher;
import org.opencv.features2d.ORB;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.features2d.Features2d;

import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.io.IOException;
import java.io.File;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import javax.imageio.ImageIO;

import static org.opencv.core.Core.NORM_HAMMING;

class Image{    
    MatOfKeyPoint keypoints;
    Mat descriptors;
    Mat imageMatrix;
    String name;

    public Image(String filename) {
    	this.imageMatrix = Imgcodecs.imread(filename, Imgcodecs.IMREAD_GRAYSCALE);
		generateFeaturesForImage(imageMatrix);
		this.name = filename;
    }
    
    public Image(MatOfKeyPoint keypoints, Mat descriptors) {
		this.keypoints = keypoints;
		this.descriptors = descriptors;
    }

    public void generateFeaturesForImage(Mat imageMatrix) {
		ORB orb = ORB.create();
        this.keypoints = new MatOfKeyPoint();
        this.descriptors = new Mat();
		orb.detectAndCompute(imageMatrix, new Mat(), keypoints, descriptors);
    }

    public LinkedList<DMatch> computeHomography(Image otherImage, List<DMatch> matches) {
		List<Point> otherImagePT = new ArrayList<>();
		List<Point> thisPT = new ArrayList<>();
		List<KeyPoint> listOfKeypointsObject = otherImage.keypoints.toList();
		List<KeyPoint> listOfKeypointsScene = this.keypoints.toList();
		for (int i = 0; i < matches.size(); i++) {
			otherImagePT.add(listOfKeypointsObject.get(matches.get(i).queryIdx).pt);
			thisPT.add(listOfKeypointsScene.get(matches.get(i).trainIdx).pt);
		}

		MatOfPoint2f objMat = new MatOfPoint2f();
		MatOfPoint2f sceneMat = new MatOfPoint2f();
		objMat.fromList(otherImagePT);
		sceneMat.fromList(thisPT);

		Mat mask = new Mat();
		Calib3d.findHomography( objMat, sceneMat, Calib3d.RANSAC, 1.0, mask);

		LinkedList<DMatch> better_matches = new LinkedList<DMatch>();
		for (int i = 0; i < matches.size(); i++) {
			if (mask.get(i, 0)[0] != 0.0) {
				better_matches.add(matches.get(i));
			}
		}

		return better_matches;
	}

    public List<DMatch> computeMatches(Image otherImage, int index) {
		BFMatcher matcher = BFMatcher.create(NORM_HAMMING, true);
		MatOfDMatch matches = new MatOfDMatch();
		matcher.match(otherImage.descriptors, this.descriptors, matches);
		List<DMatch> listMatches = matches.toList();

		List<DMatch> thresholdedMatches = new ArrayList<>();
		for (DMatch match : listMatches) {
			if (match.distance < 60) {
				thresholdedMatches.add(match);
			}
		}

		LinkedList<DMatch> homographyInliers = this.computeHomography(otherImage, thresholdedMatches);

		Mat outImage = new Mat();
		MatOfDMatch better_matches_mat = new MatOfDMatch();
		better_matches_mat.fromList(homographyInliers);

		Scalar color = new Scalar( 255, 0, 0 );
		Features2d.drawMatches(
				otherImage.imageMatrix, otherImage.keypoints,
				this.imageMatrix, this.keypoints,
				better_matches_mat, outImage, color,
				color);

		try {
			ImageIO.write(matToBufferedImage(outImage), "png", new File("output/" + index + ".png"));
		} catch(IOException e) {}

		return homographyInliers;
	}
    
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

    public String toString() {
    	return name;
	}
}
