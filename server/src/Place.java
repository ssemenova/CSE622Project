import org.opencv.core.Mat;

import java.util.LinkedList;

public class Place {
    LinkedList<Image> images;
    String name;
    Mat H;

    public Place(LinkedList<Image> images, String name) {
        this.images = images;
        this.name = name;
    }

    public double computeProbability(Image imToLocalize) {
        double inlierAvg = 0;
        int inlierMax = 0;
        Mat HforMax = new Mat();

        for (Image image : images) {
            Mat H = image.featureComparison(imToLocalize);
            if (H != null && inlierAvg > inlierMax) {
                inlierMax = image.inliers.size();
                HforMax = H;
            }
            inlierAvg += image.inliers.size();
        }

        inlierAvg /= images.size();

        this.H = HforMax;
        return inlierAvg;
    }

}
