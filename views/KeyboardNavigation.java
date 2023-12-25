package views;

import views.AdventureGameView;
import views.LoadView;
import views.SaveView;
/**
 * This class is responsible for handling keyboard navigation in the AdventureGameView.
 * It listens for key presses and updates the view accordingly.
 */
public class KeyboardNavigation {
    static boolean target;

    public KeyboardNavigation(){
        target = false;
    }
    /**
     * This method is called when a key is pressed. It updates the view accordingly.
     * @param view The AdventureGameView to update.
     */
    public static <view> void update(AdventureGameView view) {
        if (target) {
            target = false;
            view.KeyboardControls.setStyle("-fx-background-color: #17871b; -fx-text-fill: white;");

        } else {
            target = true;
            view.KeyboardControls.setStyle("-fx-background-color: rgba(0, 255, 0) ; -fx-text-fill: white;");

        }
        navigate(view);
    }
    /**
     * This method navigates the view based on the current target.
     * @param view The AdventureGameView to update.
     */
    public static void navigate(AdventureGameView view){
        if (target) {
            view.gridPane.setOnKeyPressed(keyEvent -> {
                if (view.helpToggle && keyEvent.getCode().getName() == "H") {
                    view.showInstructions();
                    view.inputTextField.clear();
                } else if (view.helpToggle) {
                    view.showInstructions();
                    view.submitEvent(keyEvent.getCharacter());
                    view.inputTextField.clear();
                } else if (keyEvent.getCode().getName() == "Tab") {
                    view.inputTextField.requestFocus();
                } else if (keyEvent.getCode().getName() == "Caps Lock") {
                    SaveView saveView = new SaveView(view);
                }  else if (keyEvent.getCode().getName() == "Shift") {
                    LoadView loadView = new LoadView(view);
                } else if (keyEvent.getCode().getName() == "M") {
                    SettingsMenu settingsMenu = new SettingsMenu(view);
                } else if (keyEvent.getCode().getName() == "Up") {
                    view.contrastOptions.update(view);
                } else if (keyEvent.getCode().getName() == "Down") {
                    view.narration.update(view);
                } else if (keyEvent.getCode().getName() == "Left") {
                    update(view);
                } else {
                    view.submitEvent(keyEvent.getCode().getName());
                    view.inputTextField.clear();
                }
            });
        } else {
            view.gridPane.setOnKeyPressed(event ->{});
        }
    }
}
