import javafx.util.Pair;

import java.io.File;
import java.util.*;
import java.util.stream.Collectors;

class PlaceDatabase {
    LinkedList<Place> places;
	int LOCALIZATION_THRESHOLD = 1;

    public PlaceDatabase(String dbName) {
		places = new LinkedList<>();

		File dbFolder = new File(dbName);
		File[] placesFolder = dbFolder.listFiles();

		for (File placeFolder : placesFolder) {
			if (placeFolder.isDirectory()) {
				File[] images = placeFolder.listFiles();
				LinkedList<Image> allImagesForPlace = new LinkedList<>();
				String placeName = placeFolder.getName();

				for (File image : images) {
					String imageName = image.getName();
					allImagesForPlace.add(new Image(
							dbName + placeName + "/" + imageName,
							imageName,
							placeName));
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
