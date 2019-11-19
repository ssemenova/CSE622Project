import javafx.util.Pair;
import org.opencv.core.Mat;

import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

class PlaceDatabase {
    HashMap<String, Image> images;
    HashMap<String, Image> surfaces;
	double LOCALIZATION_THRESHOLD = .5;

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
			String surfaceFileName = surface.getName();
			String surfaceName = surfaceFileName.substring(0, surfaceFileName.lastIndexOf('.'));
			this.surfaces.put(surfaceName,
				new Image(
					dbName + "surfaces/" + surface.getName(),
					surfaceName
				));
		}
	}

	private void setUpPlaces(String dbName) {
		File placesFolder= new File(dbName + "places");

		File[] imageFiles = placesFolder.listFiles();
		this.images = new HashMap<>();

		for (File imageFile : imageFiles) {
			String imageFileName = imageFile.getName();
			String imageName = imageFileName.substring(0, imageFileName.lastIndexOf('.'));
			if (!imageFileName.equals(".DS_Store") && !imageFileName.contains("occurrence")) {
				this.images.put(imageName,
					new Image(
						dbName + "places/" + imageFileName,
						imageName));
			}
		}
	}

	private void setUpSurfaceOccurrencesFromDataFile(String dbName) {
    	File file = new File(dbName + "places/surface_occurrences.txt");

		try {
			BufferedReader br = new BufferedReader(new FileReader(file));

			String line;
			while ((line = br.readLine()) != null) {
				Image im = this.images.get(line);
				while (!(line = br.readLine()).equals("----")) {
					String[] surfaceString = line.split("\\s+");
					Image i = this.surfaces.get(surfaceString[0]);
					im.addSurface(surfaceString, i);
				}
			}
		} catch (FileNotFoundException e) {
			System.out.println("Surfaces file not found!");
		} catch (IOException e) {
			System.out.println("Could not read from file!");
		}
	}

    public List<Pair<Double, Pair<Image, Mat>>> getLocation(Image imToLocalize) {
    	List<Pair<Double, Pair<Image, Mat>>> results = new LinkedList<>();

		for (String imageName : this.images.keySet()) {
			Image currentImage = this.images.get(imageName);

			Pair<Mat, Double> featureComparisonForImage = currentImage.featureComparison(imToLocalize);
			if (featureComparisonForImage != null) {
				Mat currentH = featureComparisonForImage.getKey();
				double currentP = featureComparisonForImage.getValue();

				System.out.println(imageName + " " + currentP);

				results.add(new Pair(
						currentP,
						new Pair(currentImage, currentH)
				));

			}
		}

		List<Pair<Double, Pair<Image, Mat>>> sortedResults = results.stream()
				.sorted(Comparator.comparing(Pair::getKey))
				.collect(Collectors.toList());

		return sortedResults;
	}
}
