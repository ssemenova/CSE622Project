import javafx.util.Pair;

import java.io.File;
import java.util.*;
import java.util.stream.Collectors;

class PlaceDatabase {
    LinkedList<Place> places;
    LinkedList<Image> surfaces;
	int LOCALIZATION_THRESHOLD = 10;

    public PlaceDatabase(String dbName) {
		System.out.println("CREATING DATABASE");
		places = new LinkedList<>();
		surfaces = new LinkedList<>();

		setUpSurfaces(dbName);
		setUpPlaces(dbName);
		System.out.println("DONE WITH DATABASE CREATION");
    }

	private void setUpSurfaces(String dbName) {
		File surfacesFolder = new File(dbName + "surfaces");
		File[] surfacesList = surfacesFolder.listFiles();

		for (File surface : surfacesList) {
			this.surfaces.add(new Image(
					dbName + "surfaces/" + surface.getName(),
					surface.getName().stripTrailing(),
					"null"
			));
		}
	}

	private void setUpPlaces(String dbName) {
		File placesFolder= new File(dbName + "places");
		File[] placesList = placesFolder.listFiles();

		for (File placeFolder : placesList) {
			if (placeFolder.isDirectory()) {
				File[] images = placeFolder.listFiles();
				LinkedList<Image> allImagesForPlace = new LinkedList<>();
				String placeName = placeFolder.getName();

				for (File image : images) {
					String imageName = image.getName();
					allImagesForPlace.add(new Image(
							dbName + "places/" + placeName + "/" + imageName,
							imageName,
							placeName,
							surfaces));
				}

				places.add(new Place(allImagesForPlace, placeName));
			}
		}
	}

    public Place getLocation(Image imToLocalize) {
    	List<Pair<Place, Double>> probabilities = new LinkedList<>();

		for (Place candidate : places) {
			probabilities.add(new Pair(candidate, candidate.computeProbability(imToLocalize)));
		}

		List<Pair<Place, Double>> sortedPrs = probabilities.stream()
				.sorted(Comparator.comparing(Pair::getValue))
				.collect(Collectors.toList());

		if (sortedPrs.get(sortedPrs.size() - 1).getValue() > LOCALIZATION_THRESHOLD) {
			return sortedPrs.get(sortedPrs.size() - 1).getKey();
		} else {
			return null;
		}
	}
}
