import org.opencv.core.DMatch;
import org.opencv.core.Mat;

import java.util.LinkedList;
import java.util.List;

public class Place {
    LinkedList<Image> images;
    String name;
    int inlierAvg;
    Mat H;

    public Place(LinkedList<Image> images, String name) {
        this.images = images;
        this.name = name;
    }

    public int computeProbability(Image imToLocalize) {
        int inlierAvg = 0;
        int inlierMax = 0;
        Mat HforMax = null;
        int i = 0;

        for (Image image : images) {
            Mat H = image.featureComparison(imToLocalize, i);
            if (inlierAvg > inlierMax) {
                inlierMax = image.inliers.size();
                HforMax = H;
            }
            inlierAvg += image.inliers.size();
            i++;
        }

        inlierAvg /= images.size();

        this.H = HforMax;
        return inlierAvg;
    }

}
