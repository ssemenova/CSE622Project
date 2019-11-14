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
		double kFlat[] = { 3080, 0, 2016, 0, 3101.53, 1512, 0, 0, 1 };
		Mat K = new Mat(3, 3, CvType.CV_32F);
		int row = 0, col = 0;
		K.put(row, col, kFlat);

		String imageDatabaseDir = args[1];
		System.out.println("CREATING DATABASE");
		PlaceDatabase db = new PlaceDatabase(imageDatabaseDir);

		ServerSocket server = null;
		InetAddress addr = null;
		DataInputStream dataInputStream = null;
		Socket client = null;
		System.out.println("DATABASE created");
		try {
			addr = InetAddress.getByName(IP);
			server = new ServerSocket(PORT, 200, addr );

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
	}

	public static String testImagesSocket(Image image, PlaceDatabase db, Mat K) {
		Place location =  db.getLocation(image);
		Mat H = location.H;
		System.out.println(H);
		System.out.println("Image  is " + location.name);

		List<Mat> rotations = new LinkedList<>();
		List<Mat> translations = new LinkedList<>();
		List<Mat> normals = new LinkedList<>();
		Calib3d.decomposeHomographyMat(H, K, rotations, translations, normals);

		return location.name;
	}
}