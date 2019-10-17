import org.opencv.core.*;
import org.opencv.features2d.DescriptorMatcher;

import java.io.File;
import java.util.*;

class PlaceDatabase {
    LinkedList<Image> images;
    int DESCRIPTOR_MATCH_THRESHOLD = 5;
    
    public PlaceDatabase() {
		images = new LinkedList<Image>();
    }

    public void addImage(MatOfKeyPoint keypoints, Mat descriptors) {
		images.add(new Image(keypoints, descriptors));
    }

    public void addImage(Image image) {
		images.add(image);
    }

    public String getLocation(Image imToLocalize) {
    	Image mostLikely = null;
		List<DMatch> mostLikelyDescriptors = null;
    	int lastHighest = 0;
    	int i = 0;

		for (Image candidate : images) {
			List<DMatch> matches = candidate.computeMatches(imToLocalize, i);
			if(matches.size() > lastHighest) {
				mostLikely = candidate;
				mostLikelyDescriptors = matches;
				lastHighest = matches.size();
			}
			i++;
		}

		return mostLikely != null ? mostLikely.toString() : "Unknown";
	}

    public String toString() {
		String result = "";
		for (Image image : images) {
			result += image.keypoints + "\n";
		}
		return result;
    }

    public void saveDescriptorsToImageFiles() {
		File directory = new File("output/");
		if (!directory.exists()){
			directory.mkdir();
		}

		for (int i = 0; i < images.size(); i++) {
			images.get(i).drawImage(i + ".png");
		}
    }
}
