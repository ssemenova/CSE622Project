import javafx.util.Pair;
import org.opencv.core.Mat;

import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

class PlaceDatabase {
    HashMap<String, Image> images;
    HashMap<String, Image> surfaces;
	int LOCALIZATION_THRESHOLD = 10;

    public PlaceDatabase(String dbName) {
		System.out.println("CREATING DATABASE");
		images = new HashMap<>();
		surfaces = new HashMap<>();

		setUpSurfaces(dbName);
		setUpPlaces(dbName);
		setUpSurfaceOccurrencesFromDataFile(dbName);
		System.out.println("DONE WITH DATABASE CREATION");
    }

	private void setUpSurfaces(String dbName) {
		File surfacesFolder = new File(dbName + "surfaces");
		File[] surfacesList = surfacesFolder.listFiles();

		for (File surface : surfacesList) {
			this.surfaces.put(surface.getName(),
				new Image(
					dbName + "surfaces/" + surface.getName(),
					surface.getName()
			));
		}
	}

	private void setUpPlaces(String dbName) {
		File placesFolder= new File(dbName + "places");

		File[] imageFiles = placesFolder.listFiles();
		this.images = new HashMap<>();

		for (File imageFile : imageFiles) {
			String imageFileName = imageFile.getName();
			if (imageFileName.equals(".DS_Store") || imageFileName.contains("occurrence")) {
				this.images.put(imageFileName,
					new Image(
						dbName + "places/" + "/" + imageFileName,
						imageFileName));
			}
		}
	}

	private List[] setUpSurfaceOccurrencesFromDataFile(String dbName) {
    	File file = new File(dbName + "occurrences.txt");

		try {
			BufferedReader br = new BufferedReader(new FileReader(file));

			String line;
			while ((line = br.readLine()) != null) {
				if (line.equals("---")) {
					line = br.readLine();
					Image im = images.get(Integer.parseInt(line));


				}
				//System.out.println(st);
			}
		} catch (FileNotFoundException e) {
			System.out.println("Surfaces file not found!");
		} catch (IOException e) {
			System.out.println("Could not read from file!");
		}
		return null;
	}

    public Pair<Image, Mat> getLocation(Image imToLocalize) {
    	double bestProbability = 0;
    	Pair<Image, Mat> imageHomographyForBestP = null;

		for (String imageName : this.images.keySet()) {
			Image currentImage = this.images.get(imageName);

			Pair<Mat, Double> featureComparisonForImage = currentImage.featureComparison(imToLocalize);
			if (featureComparisonForImage != null) {
				Mat currentH = featureComparisonForImage.getKey();
				double currentP = featureComparisonForImage.getValue();

				if (currentH != null && currentP > bestProbability) {
					imageHomographyForBestP = new Pair(currentImage, currentH);
					bestProbability = currentP;
				}
			}
		}


		if (bestProbability > LOCALIZATION_THRESHOLD) {
			return imageHomographyForBestP;
		} else {
			return null;
		}
	}
}
