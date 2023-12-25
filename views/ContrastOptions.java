package views;
import javafx.scene.effect.ColorAdjust;
/**
 * This class represents the contrast options for the AdventureGameView.
 */
public class ContrastOptions implements Options {
    /**
     * A boolean value that indicates whether the target is set or not.
     */
    boolean target;
    /**
     * Constructs a new ContrastOptions object with target set to false.
     */
    public ContrastOptions(){
        target = false;
    }
    /**
     * Updates the view with the contrast options.
     * @param view The AdventureGameView object to update.
     * @param <view> The type of the view.
     */
    public <view> void update(AdventureGameView view) {
        if (target == false) {
            ColorAdjust colorAdjust = new ColorAdjust();
            colorAdjust.setContrast(1);
            colorAdjust.setHue(0.5);
            colorAdjust.setBrightness(0);
            colorAdjust.setSaturation(.5);
            view.stackPane.setEffect(colorAdjust);
            target = true;
        } else {
            ColorAdjust colorAdjust = new ColorAdjust();
            colorAdjust.setContrast(0);
            colorAdjust.setHue(0);
            colorAdjust.setBrightness(0);
            colorAdjust.setSaturation(0);
            view.stackPane.setEffect(colorAdjust);
            target = false;
        }
    }
}
