package views;

import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;

public class FontFeature extends TextFont{

    // Font buttons
    Button ArialButton, TNRButton, CourierButton, GeorgiaButton, TahomaButton, ImpactButton, CalibriButton;
    GridPane gridPane; // Gridpane of the AdventureGameView
    Button fontButton; // Fonts selection buttons
    Font currentFont; // The current font the texts are using

    /**
     * FontFeature Constructor:
     * Initializes the font selection feature for the game.
     *
     * @param font The current font being used.
     * @param gridPane The gridPane where everything is based on.
     * @param view The AdventureGameView instance being used.
     *
     */
    public FontFeature(Font font, GridPane gridPane, AdventureGameView view){
        super(view);

        // Font selection button creation
        this.fontButton = new Button(TranslatorExample.Translate(AdventureGameView.languagesMap.get(view.language),"en","Fonts"));
        customizeButton(fontButton, 100, 50);
        addFontChangeEvent();

        this.currentFont = font;
        this.gridPane = gridPane;
    }

    /**
     * showFontSample method:
     * Create a new table of available fonts on the right side of the game grid pane for the player to select.
     *
     */
    public void showFontSample() {

        if (!this.view.fontsToggle) {
            this.view.fontsToggle = true;

            // Available Font Buttons creation
            ArialButton = new Button(TranslatorExample.Translate(AdventureGameView.languagesMap.get(view.language),"en","Arial"));
            customizeButton(ArialButton, 148, 50);
            ArialButton.setFont(new Font("Arial", this.view.currentFont.getSize()));
            addArialEvent();

            TNRButton = new Button(TranslatorExample.Translate(AdventureGameView.languagesMap.get(view.language),"en","TNR"));
            customizeButton(TNRButton, 148, 50);
            TNRButton.setFont(new Font("Times New Roman", this.view.currentFont.getSize()));
            addTNREvent();

            CourierButton = new Button(TranslatorExample.Translate(AdventureGameView.languagesMap.get(view.language),"en","Courier"));
            customizeButton(CourierButton, 148, 50);
            CourierButton.setFont(new Font("Courier New", this.view.currentFont.getSize()));
            addCourierEvent();

            GeorgiaButton = new Button(TranslatorExample.Translate(AdventureGameView.languagesMap.get(view.language),"en","Georgia"));
            customizeButton(GeorgiaButton, 148, 50);
            GeorgiaButton.setFont(new Font("Georgia", this.view.currentFont.getSize()));
            addGeorgiaEvent();

            TahomaButton = new Button(TranslatorExample.Translate(AdventureGameView.languagesMap.get(view.language),"en","Tahoma"));
            customizeButton(TahomaButton, 148, 50);
            TahomaButton.setFont(new Font("Tahoma", this.view.currentFont.getSize()));
            addTahomaEvent();

            ImpactButton = new Button(TranslatorExample.Translate(AdventureGameView.languagesMap.get(view.language),"en","Impact"));
            customizeButton(ImpactButton, 148, 50);
            ImpactButton.setFont(new Font("Impact", this.view.currentFont.getSize()));
            addImpactEvent();

            CalibriButton = new Button(TranslatorExample.Translate(AdventureGameView.languagesMap.get(view.language),"en","Calibri"));
            customizeButton(CalibriButton, 148, 50);
            CalibriButton.setFont(new Font("Calibri", this.view.currentFont.getSize()));
            addCalibriEvent();

            // Create a VBox to hold font sample elements
            VBox fontSamples = new VBox();
            fontSamples.setAlignment(Pos.CENTER);
            fontSamples.setStyle("-fx-background-color: black;");
            fontSamples.setSpacing(10);
            fontSamples.setPadding(new Insets(10));
            fontSamples.getChildren().addAll(ArialButton, TNRButton, CourierButton, GeorgiaButton, TahomaButton, ImpactButton, CalibriButton);

            // Add the font sample VBox to the left side of the GridPane
            this.view.gridPane.add(fontSamples, 2, 1);
            GridPane.setHalignment(fontSamples, HPos.CENTER);

        }

        // Toggles font bar function
        else {
            this.view.fontsToggle = false;
            this.view.updateItems();
        }
    }

    /**
     * addFontChangeEvent method:
     * Gives the fontButton button its purpose.
     */
    public void addFontChangeEvent() {
        fontButton.setOnAction(e -> {
            showFontSample();
        });
    }

    /**
     * addArialEvent method:
     * Gives the Arial button its purpose.
     */
    public void addArialEvent() {
        ArialButton.setOnAction(e -> {
            double size = this.view.currentFont.getSize();
            updateText("Arial", size);
        });
    }

    /**
     * addTNREvent method:
     * Gives the Times New Roman button its purpose.
     */
    public void addTNREvent() {
        TNRButton.setOnAction(e -> {
            double size = this.view.currentFont.getSize();
            updateText("Times New Roman", size);
        });
    }

    /**
     * addCourierEvent method:
     * Gives the Courier button its purpose.
     */
    public void addCourierEvent() {
        CourierButton.setOnAction(e -> {
            double size = this.view.currentFont.getSize();
            updateText("Courier New", size);
        });
    }

    /**
     * addGeorgiaEvent method:
     * Gives the Georgia button its purpose.
     */
    public void addGeorgiaEvent() {
        GeorgiaButton.setOnAction(e -> {
            double size = this.view.currentFont.getSize();
            String font = GeorgiaButton.getText();
            updateText("Georgia", size);
        });
    }

    /**
     * addTahomaEvent method:
     * Gives the Tahoma button its purpose.
     */
    public void addTahomaEvent() {
        TahomaButton.setOnAction(e -> {
            double size = this.view.currentFont.getSize();
            updateText("Tahoma", size);
        });
    }

    /**
     * addImpactEvent method:
     * Gives the Impact button its purpose.
     */
    public void addImpactEvent() {
        ImpactButton.setOnAction(e -> {
            double size = this.view.currentFont.getSize();
            updateText("Impact", size);
        });
    }

    /**
     * addCalibriEvent method:
     * Gives the Calibri button its purpose.
     */
    public void addCalibriEvent() {
        CalibriButton.setOnAction(e -> {
            double size = this.view.currentFont.getSize();
            updateText("Calibri", size);
        });
    }

    /**
     * Font button getter method.
     * @return Button fontButton
     */
    public Button getFontButton(){return this.fontButton;}
}
