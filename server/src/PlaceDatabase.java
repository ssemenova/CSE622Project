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
import java.util.*; 

class PlaceDatabase {
    LinkedList<Image> images;
    
    public PlaceDatabase() {
	images = new LinkedList<Image>();
    }

    public void addImage(MatOfKeyPoint keypoints, Mat descriptors) {
	images.add(new Image(keypoints, descriptors));
    }

    public void addImage(Image image) {
	images.add(image);
    }

    public String toString() {
	String result = "";
	for (Image image : images) {
	    result += image.keypoints + "\n";
	}
	return result;
    }

    public void saveDescriptorsToImageFiles() {
        File directory = new File("output/");
	if (!directory.exists()){
	    directory.mkdir();
	}
    
	for (int i = 0; i < images.size(); i++) {
	    images.get(i).drawImage(i + ".png");
	}
    }
}
