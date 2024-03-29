import org.opencv.core.*;
import org.opencv.imgcodecs.Imgcodecs;

import java.io.*;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

import static org.opencv.core.CvType.CV_8UC3;

class Server {
	static{ System.loadLibrary(Core.NATIVE_LIBRARY_NAME); }

	private static final int PORT = 5001;
	private static final String IP = "192.168.1.19";

	public static void main(String[] args) {
		Mat K = getK();

		System.out.println("Setting up database");
		String imageDatabaseDir = args[0];
		PlaceDatabase db = new PlaceDatabase(imageDatabaseDir, K);

		System.out.println("Done with database");

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

				long startTime = System.nanoTime();
				Mat response = db.getModelMatricesForImage(currentImage);
				Imgcodecs.imwrite("response.png", response);
				long endTime = System.nanoTime();
				System.out.println("all " + (endTime - startTime) / 1000000);


				// read from response

				client.close();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		catch (Exception e){
			e.printStackTrace();
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