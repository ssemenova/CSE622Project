import org.opencv.core.Core;

import java.io.File;

class Server {
    static{ System.loadLibrary(Core.NATIVE_LIBRARY_NAME); }

    public static void main(String[] args) {
		String testImageDir = args[0];
		String imageDatabaseDir = args[1];

		System.out.println("CREATING DATABASE...");
		PlaceDatabase db = new PlaceDatabase(imageDatabaseDir);

		testImages(db, testImageDir);
    }

    public static void testImages(PlaceDatabase db, String testImageDir) {
    	System.out.println("TESTING IMAGES IN " + testImageDir + " ...");
		File testFolder = new File(testImageDir);
		File[] imageFiles = testFolder.listFiles();
		for (int i = 0; i < imageFiles.length; i++) {
			if (imageFiles[i].isFile()) {
				String imageName = imageFiles[i].getName();
				Image im = new Image(testImageDir + imageName, imageName, "Unknown");
				System.out.println("Image " + imageName + " is " + db.getLocation(im));
			}
		}
	}
   
}
