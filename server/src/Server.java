import org.opencv.core.Core;
import java.time.LocalTime;
import java.io.File;

class Server {
    static{ System.loadLibrary(Core.NATIVE_LIBRARY_NAME); }

    public static void main(String[] args) {
		String testImageDir = args[0];
		String imageDatabaseDir = args[1];

		System.out.println("CREATING DATABASE");
		PlaceDatabase db = new PlaceDatabase(imageDatabaseDir);

		long startTime = System.currentTimeMillis();
		testImages(testImageDir, db);
		long endTime = System.currentTimeMillis();
		System.out.println(endTime - startTime);
    }

    public static void testImages(String testImageDir, PlaceDatabase db) {
		File testFolder = new File(testImageDir);
		File[] imageFiles = testFolder.listFiles();

		for (int i = 0; i < imageFiles.length; i++) {
			if (imageFiles[i].isFile()) {
				Image im = new Image(testImageDir + imageFiles[i].getName(), imageFiles[i].getName(), null);
				System.out.println("Image " + imageFiles[i].getName() + " is " + db.getLocation(im));
			}
		}
	}

   
}
