import org.opencv.core.DMatch;

import java.util.LinkedList;
import java.util.List;

public class Place {
    LinkedList<Image> images;
    String name;

    public Place(LinkedList<Image> images, String name) {
        this.images = images;
        this.name = name;
    }

    public double computeProbability(Image imToLocalize) {
        int inlierAvg = 0;
        int i = 0;

        for (Image image : images) {
            inlierAvg += image.featureComparison(imToLocalize, i);
            i++;
        }

        inlierAvg /= images.size();

        return inlierAvg;
    }

}
