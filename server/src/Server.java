import org.opencv.core.Core;

import java.io.File;

class Server {
    static{ System.loadLibrary(Core.NATIVE_LIBRARY_NAME); }

    public static void main(String[] args) {
		String testImageDir = args[0];
		String imageDatabaseDir = args[1];

		PlaceDatabase db = createPlaceDatabase(imageDatabaseDir);

		File testFolder = new File(testImageDir);
		File[] imageFiles = testFolder.listFiles();
		for (int i = 0; i < imageFiles.length; i++) {
			if (imageFiles[i].isFile()) {
				Image im = new Image(testImageDir + imageFiles[i].getName());
				System.out.println("Image " + imageFiles[i].getName() + " is " + db.getLocation(im));
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

   
}
