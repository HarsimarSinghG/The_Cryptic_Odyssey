package views;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.scene.control.*;


public class ZoomFeature extends TextFont{

    Button zoomInButton, zoomOutButton; // Buttons for the zoom functions
    GridPane gridPane; // Gridpane that the AdventureGameView uses
    Font currentFont; // The current font being used

    /**
     * ZoomFeature Constructor:
     * Initializes the zoom (both zoom in and out) feature for the game.
     *
     * @param font The current font being used.
     * @param gridPane The gridPane where everything is based on.
     * @param view The AdventureGameView instance being used.
     *
     */
    public ZoomFeature(Font font, GridPane gridPane, AdventureGameView view){
        super(view);

        // Zoom in button creator
        zoomInButton = new Button(TranslatorExample.Translate(AdventureGameView.languagesMap.get(view.language),"en","Zoom In"));
        super.customizeButton(zoomInButton, 150, 50);
        addZoomInEvent();

        // Zoom out button creator
        zoomOutButton = new Button(TranslatorExample.Translate(AdventureGameView.languagesMap.get(view.language),"en","Zoom Out"));
        customizeButton(zoomOutButton, 150, 50);
        addZoomOutEvent();

        this.currentFont = font;

    }

    /**
     * addZoomInEvent method:
     * Gives the zoom in button its purpose.
     */
    // Zoom event for zoom in button
    public void addZoomInEvent() {
        zoomInButton.setOnAction(e -> {
            double size = this.view.currentFont.getSize() + 1;
            String font = this.view.currentFont.getName();
            super.updateText(font, size);
        });
    }

    /**
     * addZoomOutEvent method:
     * Gives the zoom out button its purpose.
     */
    public void addZoomOutEvent() {
        zoomOutButton.setOnAction(e -> {
            double size = this.view.currentFont.getSize() - 1;
            String font = this.view.currentFont.getName();
            super.updateText(font, size);
        });
    }

    /**
     * Zoom in button getter method.
     * @return Button ZoomInButton
     */
    public Button getZoomInButton(){return this.zoomInButton;}

    /**
     * Zoom out button getter method.
     * @return Button ZoomOutButton
     */
    public Button getZoomOutButton(){return this.zoomOutButton;}


}
