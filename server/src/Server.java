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
import java.lang.Boolean;

class Server {
    static{ System.loadLibrary(Core.NATIVE_LIBRARY_NAME); }

    public static void main(String[] args) {
	String testImageDir = args[0];
	String imageDatabaseDir = args[1];

	PlaceDatabase db = createPlaceDatabase(imageDatabaseDir);

	File testFolder = new File(testFolder);
	File[] imageFiles = folder.listFiles();
	for (int i = 0; i < imageFiles.length; i++) {
	    if (imageFiles[i].isFile()) {
		getLocation();
	    }
	}
    }

    public static PlaceDatabase createPlaceDatabase(String imageDatabaseDir) {
	System.out.println("CREATING DATABASE");
	PlaceDatabase db = new PlaceDatabase();
	
	File folder = new File(imageDatabaseDir);
	File[] imageFiles = folder.listFiles();

	for (int i = 0; i < imageFiles.length; i++) {
	    if (imageFiles[i].isFile()) {
		Image image = new Image(imageDatabaseDir + imageFiles[i].getName());
		db.addImage(image);
	    }
	}
	
	return db;
    }

    public static void computeSimilarity() {
    }

   
}
