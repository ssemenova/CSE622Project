import javafx.util.Pair;
import org.opencv.calib3d.Calib3d;
import org.opencv.core.*;
import org.opencv.imgproc.Imgproc;

import java.io.File;
import java.net.*;

import javax.imageio.ImageIO;

import java.awt.image.BufferedImage;
import java.io.*;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

class Server {
	static{ System.loadLibrary(Core.NATIVE_LIBRARY_NAME); }

	private static final int PORT = 5001;
	private static final String IP = "192.168.1.19";

	public static void main(String[] args) {
		Mat K = getK();

		System.out.println("Setting up database");
		String imageDatabaseDir = args[0];
		PlaceDatabase db = new PlaceDatabase(imageDatabaseDir);

		System.out.println("Done with database");
		Image image = new Image("../data/1.jpg", "1.jpg");

	}
/*
		ServerSocket server = null;
		InetAddress addr = null;
		DataInputStream dataInputStream = null;
		Socket client = null;
		try {
			addr = InetAddress.getByName(IP);
			server = new ServerSocket(PORT, 200);

			System.out.println("Server Listening");
			while(true) {
				client = server.accept();
				System.out.println("Client connected");
				dataInputStream = new DataInputStream(client.getInputStream());

				int nRead;
				byte[] data = new byte[16 * 1024];
				ByteArrayOutputStream buffer = new ByteArrayOutputStream();
				while ((nRead = dataInputStream.read(data, 0, data.length)) != -1) {
					buffer.write(data, 0, nRead);
				}
				byte[] bytes = buffer.toByteArray();

				Image currentImage = new Image(bytes);
				testImagesSocket(currentImage, db, K);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		catch (Exception e){
			e.printStackTrace();
		}
		long startTime = System.currentTimeMillis();
		long endTime = System.currentTimeMillis();
		System.out.println(endTime - startTime);
	}*/

	public static void testImagesSocket(Image image, PlaceDatabase db, Mat K) {
		List<Pair<Double, Pair<Image, Mat>>> results =  db.getLocation(image);

		// Get rotation and translation matrices from the image match to the
		// input image using the homography found between them.
		Mat image1ToInputH = results.get(0).getValue().getValue();
		Image firstImage = results.get(0).getValue().getKey();

		getComposedHomographiesForSurfaces();

		List<Mat> image1ToInputRs = new LinkedList<>();
		List<Mat> image1ToInputTs = new LinkedList<>();
		Calib3d.decomposeHomographyMat(image1ToInputH, K, image1ToInputRs, image1ToInputTs, new LinkedList<>());

		HashMap<String, Mat> finalRotations = new HashMap<>();
		HashMap<String, Mat> finalTranslations = new HashMap<>();

		// For each surface occurring in the original image, get the rotation and translation
		// matrices from that surface to the match image, and save the composed R and T matrices
		// from surface -> match image -> query image.
		for (String surfaceName : firstImage.surfacesOccurring.keySet()) {
			Pair<Mat, Mat> surfaceToImage1RAndT = firstImage.surfacesOccurring.get(surfaceName);

			Mat multipliedR = new Mat(2, 3, CvType.CV_64F);
			Mat multipliedT = new Mat(1, 3, CvType.CV_64F);
			Core.multiply(surfaceToImage1RAndT.getKey(), image1ToInputRs.get(0), multipliedR);
			Core.multiply(surfaceToImage1RAndT.getValue(), image1ToInputTs.get(0), multipliedT);

			finalRotations.put(surfaceName, multipliedR);
			finalTranslations.put(surfaceName, multipliedT);

			MatOfPoint2f destImage = new MatOfPoint2f();

			Calib3d.projectPoints(
					new MatOfPoint3f(db.surfaces.get(surfaceName).imageMatrix),
					multipliedR,
					multipliedT,
					K,
					null,
					destImage
			);

			System.out.println(destImage);
/*
			try {
				ImageIO.write(matToBufferedImage(destImage),
						"png",
						new File("output/" + this.name + " " + surfaceDescriptor[0] + ".png"));
			} catch (IOException e) {

			}
*/

		}

	}

	public static Mat getK() {
		double kFlat[] = {1555.823494, 0, 959.5, 0, 1555.823494, 539.5, 0, 0, 1};
		Mat K = new Mat(3, 3, CvType.CV_32F);
		int row = 0, col = 0;
		K.put(row, col, kFlat);
		return K;
	}
}