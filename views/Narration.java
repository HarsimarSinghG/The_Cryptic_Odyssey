package views;

import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.input.MouseButton;
import views.AdventureGameView;
import com.sun.speech.freetts.Voice;
import com.sun.speech.freetts.VoiceManager;
import java.io.IOException;
import java.io.InputStream;
/**
 * This class is responsible for narrating the game to the user.
 */
public class Narration {
    static boolean target;

    public Narration() {
        target = false;
    }
    /**
     * Updates the narration for the user.
     *
     * @param view The view to narrate.
     */
    public <view> void update(AdventureGameView view) {
        if (target == false) {
            target = true;
            view.NarrationOption.setStyle("-fx-background-color: rgba(0, 255, 0) ; -fx-text-fill: white;");
        } else {
            target = false;
            view.NarrationOption.setStyle("-fx-background-color: #17871b; -fx-text-fill: white;");

        }
        narrate(view);
    }

    /**
     * Narrates the game to the user.
     *
     * @param view The view to narrate.
     */

    public static void narrate(AdventureGameView view) {
        System.setProperty("freetts.voices", "com.sun.speech.freetts.en.us.cmu_us_kal.KevinVoiceDirectory");
        VoiceManager voiceManager = VoiceManager.getInstance();
        if (Narration.target) {
            Voice voice = voiceManager.getVoice("kevin16");
            voice.allocate();
            view.saveButton.setOnMouseClicked(mouseEvent -> {
                if (mouseEvent.getButton() == MouseButton.SECONDARY){
                    view.stopArticulation();
                    voice.speak("This is save button");
                }
            });
            view.Health.setOnMouseClicked(mouseEvent -> {
                if (mouseEvent.getButton() == MouseButton.SECONDARY) {
                    view.stopArticulation();
                    voice.speak("Current health is "+ view.model.getPlayer().getHealth() );
                }
            });
            view.zoomFeature.zoomInButton.setOnMouseClicked(mouseEvent -> {
                if(mouseEvent.getButton()==MouseButton.SECONDARY){
                    view.stopArticulation();
                    voice.speak("This is Zoom in button.");
                }
            });
            view.zoomFeature.zoomOutButton.setOnMouseClicked(mouseEvent -> {
                if(mouseEvent.getButton()==MouseButton.SECONDARY){
                    view.stopArticulation();
                    voice.speak("This is Zoom out button.");
                }
            });
            view.fontFeature.getFontButton().setOnMouseClicked(mouseEvent -> {
                if(mouseEvent.getButton()==MouseButton.SECONDARY){
                    view.stopArticulation();
                    voice.speak("This is fonts button.");
                }
            });
            view.loadOrDeleteButton.setOnMouseClicked(mouseEvent -> {
                if (mouseEvent.getButton() == MouseButton.SECONDARY){
                    view.stopArticulation();
                    voice.speak("This is load button");
                }
            });
            view.helpButton.setOnMouseClicked(mouseEvent -> {
                if (mouseEvent.getButton() == MouseButton.SECONDARY){
                    view.stopArticulation();
                    voice.speak("This is help button");
                }
            });
            view.settingsButton.setOnMouseClicked(mouseEvent -> {
                if (mouseEvent.getButton() == MouseButton.SECONDARY){
                    view.stopArticulation();
                    voice.speak("This is settings menu");
                }
            });
            view.Contrast.setOnMouseClicked(mouseEvent -> {
                if (mouseEvent.getButton() == MouseButton.SECONDARY){
                    view.stopArticulation();
                    voice.speak("This is High Contrast Settings");
                }
            });
            view.inputTextField.setOnMouseClicked(mouseEvent -> {
                if (mouseEvent.getButton() == MouseButton.SECONDARY){
                    view.stopArticulation();
                    voice.speak("This is input field");
                }
            });
            view.NarrationOption.setOnMouseClicked(mouseEvent -> {
                if (mouseEvent.getButton() == MouseButton.SECONDARY){
                    view.stopArticulation();
                    voice.speak("This is Narration Settings");
                }
            });
            view.KeyboardControls.setOnMouseClicked(mouseEvent -> {
                if (mouseEvent.getButton() == MouseButton.SECONDARY){
                    view.stopArticulation();
                    voice.speak("This is Keyboard Only Settings");
                }
            });
             view.GamesButton.setOnMouseClicked(mouseEvent -> {
                if (mouseEvent.getButton() == MouseButton.SECONDARY){
                    view.stopArticulation();
                    voice.speak("This is the Change Game Button");
                }
            });
            view.roomImageView.setOnMouseClicked(mouseEvent -> {
                if (mouseEvent.getButton() == MouseButton.SECONDARY){
                    voice.speak("This is the "+view.model.getPlayer().getCurrentRoom().getRoomName() );
                }
            });
            for (Node object: view.objectsInRoom.getChildren()){
                object.setOnMouseClicked(mouseEvent -> {
                    if (mouseEvent.getButton() == MouseButton.SECONDARY){
                        voice.speak("This is a "+object.getAccessibleRoleDescription()+" in the room");
                    }
                });
            }
            for (Node object: view.objectsInInventory.getChildren()){
                object.setOnMouseClicked(mouseEvent -> {
                    if (mouseEvent.getButton() == MouseButton.SECONDARY){
                        voice.speak("This is a "+object.getAccessibleRoleDescription()+" in the inventory");
                    }
                });
            }
        }
        else {
            view.Health.setOnMouseClicked(mouseEvent -> {
            });
            view.zoomFeature.zoomInButton.setOnMouseClicked(mouseEvent -> {
            });
            view.zoomFeature.zoomOutButton.setOnMouseClicked(mouseEvent -> {

            });
            view.fontFeature.getFontButton().setOnMouseClicked(mouseEvent -> {

            });
            view.saveButton.setOnMouseClicked(mouseEvent -> {

            });
            view.loadOrDeleteButton.setOnMouseClicked(mouseEvent -> {});

            view.helpButton.setOnMouseClicked(mouseEvent -> {

            });
            view.settingsButton.setOnMouseClicked(mouseEvent -> {

            });
            view.Contrast.setOnMouseClicked(mouseEvent -> {

            });
            view.inputTextField.setOnMouseClicked(mouseEvent -> {

            });
            view.NarrationOption.setOnMouseClicked(mouseEvent -> {

            });
            view.KeyboardControls.setOnMouseClicked(mouseEvent -> {

            });
            view.GamesButton.setOnMouseClicked(mouseEvent -> {

            });
            view.roomImageView.setOnMouseClicked(mouseEvent -> {

            });
            for (Node object: view.objectsInRoom.getChildren()){
                object.setOnMouseClicked(mouseEvent -> {

                });
            }
            for (Node object: view.objectsInInventory.getChildren()){
                object.setOnMouseClicked(mouseEvent -> {

                });
            }
        }
    }

}
