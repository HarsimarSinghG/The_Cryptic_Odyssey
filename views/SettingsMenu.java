package views;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;

public class SettingsMenu {
    /**
     * SettingsMenu Constructor:
     * Initializes the zoom (both zoom in and out) feature for the game.
     *
     * @param view The AdventureGameView instance being used.
     *
     */
    public SettingsMenu(AdventureGameView view){
        if (!view.settingsToggle) {
            view.settingsToggle = true;

            if (view.helpToggle) {
                view.helpToggle = false;
            }

            if (view.fontsToggle){
                view.fontsToggle = false;
                view.updateItems();
            }

            // Creation of the base box

            view.Contrast.setOnAction(actionEvent -> {
                view.contrastOptions.update(view);
            });
           view.NarrationOption.setOnAction(actionEvent -> {
               view.narration.update(view);
           });
           view.KeyboardControls.setOnAction(actionEvent -> {
               view.keyboardNavigation.update(view);
           });

            VBox sampleBox = new VBox(10, view.Contrast, view.NarrationOption, view.KeyboardControls);
            sampleBox.setAlignment(Pos.CENTER);
            sampleBox.setMaxHeight(200);
            sampleBox.setStyle("-fx-background-color: black;");

            VBox topButtons = new VBox(10, view.saveButton, view.loadOrDeleteButton);
            topButtons.setMaxHeight(200);
            topButtons.setAlignment(Pos.TOP_CENTER);

            HBox bottomButtons = new HBox(10.0, (Node) view.zoomFeature.getZoomInButton(), (Node) view.zoomFeature.getZoomOutButton(), (Node) view.fontFeature.getFontButton());
            bottomButtons.setMaxHeight(200);

            bottomButtons.setAlignment(Pos.BOTTOM_CENTER);

            // Populating the setting menu
            VBox settingMenu = new VBox(75); // Adding spacing between children
            settingMenu.setStyle("-fx-text-fill: black;");
            settingMenu.setAlignment(Pos.TOP_CENTER);
            settingMenu.setBackground(new Background(new BackgroundFill(
                    Color.valueOf("#000000"),
                    new CornerRadii(0),
                    new Insets(0)
            )));
            settingMenu.setPrefWidth(630);


            settingMenu.getChildren().addAll(topButtons, sampleBox, bottomButtons);

            view.RemoveIndexFromGridPane();
            view.gridPane.add(settingMenu, 1, 1);


        }

        // Turns off font and help toggle when exiting the setting menu
        else {
            if (view.fontsToggle){
                view.fontsToggle = false;
                view.updateItems();
            }
            view.helpToggle = false;
            view.settingsToggle = false;
            view.RemoveIndexFromGridPane();
            view.updateScene("");
        }
    }
}
