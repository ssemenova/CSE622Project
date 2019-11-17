import org.opencv.calib3d.Calib3d;
import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;

import java.io.File;
import java.net.*;

import javax.imageio.ImageIO;

import java.awt.image.BufferedImage;
import java.io.*;
import java.util.LinkedList;
import java.util.List;

class Server {
	static{ System.loadLibrary(Core.NATIVE_LIBRARY_NAME); }

	private static final int PORT = 5001;
	private static final String IP = "192.168.1.19";

	public static void main(String[] args) {
		Mat K = getK();

		String imageDatabaseDir = args[0];
		PlaceDatabase db = new PlaceDatabase(imageDatabaseDir);

		Image image = new Image("../data/1.jpg", "1.jpg");
		System.out.println(testImagesSocket(image, db, K));

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

	public static String testImagesSocket(Image image, PlaceDatabase db, Mat K) {
		Place location =  db.getLocation(image);
		if (location != null) {
			Mat H = location.H;

			List<Mat> rotations = new LinkedList<>();
			List<Mat> translations = new LinkedList<>();
			List<Mat> normals = new LinkedList<>();
			Calib3d.decomposeHomographyMat(H, K, rotations, translations, normals);
		} else {
			return "None";
		}

		return location.name;
	}

	public static Mat getK() {
		double kFlat[] = { 3080, 0, 2016, 0, 3101.53, 1512, 0, 0, 1 };
		Mat K = new Mat(3, 3, CvType.CV_32F);
		int row = 0, col = 0;
		K.put(row, col, kFlat);
		return K;
	}
}