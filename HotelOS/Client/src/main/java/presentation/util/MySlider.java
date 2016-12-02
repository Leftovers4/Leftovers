package presentation.util;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.scene.control.Label;
import javafx.util.Duration;

/**
 * Created by Hitiger on 2016/12/2.
 * Description :
 */
public class MySlider {

    public static void moveSliderLabel(Label label, double x){
        Timeline timeline = new Timeline();
        timeline.setAutoReverse(false);
        KeyValue newX = new KeyValue(label.layoutXProperty(),x);
        KeyFrame kf = new KeyFrame(Duration.millis(200), newX);
        timeline.getKeyFrames().add(kf);
        timeline.play();
    }
}
