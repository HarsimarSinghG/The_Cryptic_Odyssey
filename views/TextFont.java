package views;

import javafx.scene.control.Button;
import javafx.scene.text.Font;

import java.util.Objects;

public class TextFont {

    AdventureGameView view; // The current AdventureGameView

    /**
     * TextFont Constructor:
     * The parent class of the ZoomFeature and the FontFeature class.
     *
     * @param view The AdventureGameView instance being used.
     *
     */
    public TextFont(AdventureGameView view){
        this.view = view;
    }

    /**
     * customizeButton method:
     * Set the dimension of a given button to the specified width and height.
     *
     * @param button The button being resized.
     * @param w The chosen width for the button.
     * @param h The chosen height for the button.
     *
     */

    protected void customizeButton(Button button, int w, int h) {
        button.setMinWidth(w);
        button.setMinHeight(h);
    }

    /**
     * updateText method:
     * Set all texts in the game to the font style and size chosen by the player.
     *
     * @param font The font style chosen by the player.
     * @param size The font size chosen by the player.
     *
     */
    protected void updateText(String font, double size) {
        if (size <= 10){
            size = 10;
        }

        else if (size >= 26){
            size = 26;
        }

        if (Objects.equals(font, "Georgia") && size >= 25){
            size = 25;
        }

        else if ((Objects.equals(font, "Courier New")) && size >= 23){
            size = 23;
        }

        Font currentFont = new Font(font, size);

        // Update font for all buttons
        this.view.saveButton.setFont(currentFont);
        this.view.loadOrDeleteButton.setFont(currentFont);
        this.view.settingsButton.setFont(currentFont);
        this.view.zoomFeature.zoomInButton.setFont(currentFont);
        this.view.zoomFeature.zoomOutButton.setFont(currentFont);
        this.view.fontFeature.fontButton.setFont(currentFont);
        this.view.fontFeature.fontButton.setFont(currentFont);
        this.view.commandLabel.setFont(currentFont);
        this.view.NarrationOption.setFont(currentFont);
        this.view.inputTextField.setFont(currentFont);
        this.view.GamesButton.setFont(currentFont);
        this.view.helpButton.setFont(currentFont);
        this.view.KeyboardControls.setFont(currentFont);
        this.view.Contrast.setFont(currentFont);
        this.view.objLabel.setFont(currentFont);
        this.view.invLabel.setFont(currentFont);


        // Special fonts limitations
        if ((Objects.equals(font, "Courier New")) && size >= 15) {
            this.view.GamesButton.setFont(new Font(font, 17));
            this.view.helpButton.setFont(new Font(font, 15));
            this.view.KeyboardControls.setFont(new Font(font, 18));
            this.view.Contrast.setFont(new Font(font, 15));
            this.view.objLabel.setFont(new Font(font, 16));
            this.view.invLabel.setFont(new Font(font, 16));
        }

        else if (size >= 17) {
            this.view.GamesButton.setFont(new Font(font, 17));
            this.view.helpButton.setFont(new Font(font, 17));
            this.view.KeyboardControls.setFont(new Font(font, 23));
            this.view.Contrast.setFont(new Font(font, 17));
            this.view.objLabel.setFont(new Font(font, 20));
            this.view.invLabel.setFont(new Font(font, 20));
        }



        else {
            this.view.objLabel.setFont(currentFont);
            this.view.invLabel.setFont(currentFont);
        }


        if (this.view.fontsToggle) {
            this.view.fontFeature.ArialButton.setFont(new Font(this.view.fontFeature.ArialButton.getFont().getName(), size));
            this.view.fontFeature.TNRButton.setFont(new Font(this.view.fontFeature.TNRButton.getFont().getName(), size));
            if (size >= 23){
                this.view.fontFeature.CourierButton.setFont(new Font(this.view.fontFeature.CourierButton.getFont().getName(), 23));
            }
            else {
                this.view.fontFeature.CourierButton.setFont(new Font(this.view.fontFeature.CourierButton.getFont().getName(), size));
            }
            this.view.fontFeature.GeorgiaButton.setFont(new Font(this.view.fontFeature.GeorgiaButton.getFont().getName(), size));
            this.view.fontFeature.TahomaButton.setFont(new Font(this.view.fontFeature.TahomaButton.getFont().getName(), size));
            this.view.fontFeature.ImpactButton.setFont(new Font(this.view.fontFeature.ImpactButton.getFont().getName(), size));
            this.view.fontFeature.CalibriButton.setFont(new Font(this.view.fontFeature.CalibriButton.getFont().getName(), size));
        }

        this.view.currentFont = currentFont;
    }

}
