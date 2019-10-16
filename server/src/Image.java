import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.MatOfKeyPoint;
import org.opencv.core.CvType;
import org.opencv.core.Scalar;
import org.opencv.features2d.ORB;
import org.opencv.imgproc.Imgproc;
import org.opencv.core.Point;
import org.opencv.core.Size;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.features2d.Features2d;
import org.opencv.highgui.HighGui;

import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.io.IOException;
import java.io.File;
import javax.imageio.ImageIO;

class Image{    
    MatOfKeyPoint keypoints;
    Mat descriptors;
    Mat imageMatrix;
    int id;

    public Image(String filename) {
        this.imageMatrix = Imgcodecs.imread(filename);
	System.out.println(imageMatrix);
	generateORBDataForImage(imageMatrix);
    }
    
    public Image(MatOfKeyPoint keypoints, Mat descriptors) {
	this.keypoints = keypoints;
	this.descriptors = descriptors;
    }

    public void generateORBDataForImage(Mat imageMatrix) {
	ORB orb = ORB.create();
        this.keypoints = new MatOfKeyPoint();
        this.descriptors = new Mat();
	orb.detectAndCompute(imageMatrix, new Mat(), keypoints, descriptors);
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
}
