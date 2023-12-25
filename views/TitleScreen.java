package views;


import AdventureModel.AdventureGame;
import AdventureModel.AdventureObject;
import AdventureModel.Passage;
import AdventureModel.Room;
import javafx.animation.PauseTransition;
import javafx.application.Platform;
import javafx.event.EventType;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.layout.*;
import javafx.scene.input.KeyEvent; //you will need these!
import javafx.scene.input.KeyCode;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Duration;
import javafx.event.EventHandler; //you will need this too!
import javafx.scene.AccessibleRole;


import java.io.File;
import java.io.IOException;
import java.util.ArrayList;


/**
 * A class that displays a scene for the title screen of the game.
 * @author Harsimar Singh
 */
public class TitleScreen {
    private static TitleScreen titleScreenInstance;


    String currentLanguage = "English";
    private AdventureGame model;


    private AdventureGameView adventureGameView;


    Button newGameButton;


    Button languagesButton;


    Button quitGameButton;


    Label titleLabel;


    Button loadButton;




    Stage stage;

    Scene scene;


    private TitleScreen() {
        titleScreenInstance = this;
    }


    /**
     * Creating a singleton for the Title Screen.
     * @author Harsimar Singh
     */
    public static TitleScreen getInstance() {
        // Check if the instance is null, then create a new instance
        if (titleScreenInstance == null) {
            titleScreenInstance = new TitleScreen();
        }
        return titleScreenInstance;
    }

    public void setScene(Stage stage){
        stage.setScene(scene);
    }

    /**
     * The ShowTitleScreen method constructs a new stage with the scene as TitleScreen.
     * @author Harsimar Singh
     */
    public void ShowTitleScreen(){

        stage = new Stage();
        adventureGameView = new AdventureGameView(this.stage,currentLanguage );
        stage.setTitle("group_43's Adventure Game");
        StackPane stackPane = new StackPane();
        stackPane.setPadding(new Insets(20));
        stackPane.setBackground(new Background(new BackgroundFill(
                Color.valueOf("#000000"),
                new CornerRadii(0),
                new Insets(0)
        )));
        Image image = new Image("file:title_screen.jpg");


        ImageView imageView = new ImageView(image);
        imageView.setPreserveRatio(true);
        imageView.setFitWidth(1000);
        imageView.setFitHeight(800);


        loadButton = new Button("LOAD/DELETE GAME");
        loadButton.setOnAction(event -> {
            LoadView loadView = new LoadView(adventureGameView);


        });


        loadButton.setId("LOAD");
        adventureGameView.customizeButton(loadButton, 200, 50);
        MakeButtonsTransparent(loadButton);
        AdventureGameView.makeButtonAccessible(loadButton, "Load Button", "This button loads a game from a file.", "This button loads the game from a file. Click it in order to load a game that you saved at a prior date.");


        newGameButton = new Button("NEW GAME");
        newGameButton.setOnAction(event -> {
            ShowVBoxForNewGame();
        });
        newGameButton.setId("NEW GAME");
        adventureGameView.customizeButton(newGameButton, 200, 50);
        MakeButtonsTransparent(newGameButton);
        AdventureGameView.makeButtonAccessible(newGameButton, "New Game Button", "This button loads a new game from a file.", "This button loads a new game from a file. Click it in order to load a new game.");


        languagesButton = new Button("LANGUAGES");
        ShowAllLanguages(languagesButton);
        languagesButton.setId("LANGUAGES");
        adventureGameView.customizeButton(languagesButton,200,50);
        MakeButtonsTransparent(languagesButton);
        AdventureGameView.makeButtonAccessible(languagesButton,"Settings Button","This button is used to open the settings menu.", "This button is used to open the settings menu. Click on it to open settings menu.");


        quitGameButton = new Button("QUIT GAME");
        quitGameButton.setOnAction(event -> {System.exit(0);});
        quitGameButton.setId("QUIT GAME");
        adventureGameView.customizeButton(quitGameButton,200,50);
        MakeButtonsTransparent(quitGameButton);
        AdventureGameView.makeButtonAccessible(quitGameButton,"Quit game button", "This button is used to quit the game.", "Click on this button to quit the game/Application.");


        titleLabel = new Label("The Cryptic Odyssey");


        titleLabel.setFont(Font.font("Arial", 40));
        titleLabel.setTextFill(Color.BLACK);






        stackPane.getChildren().addAll(imageView, newGameButton, loadButton, languagesButton, quitGameButton, titleLabel);
        loadButton.setTranslateX(-350);
        newGameButton.setTranslateX(-350);
        languagesButton.setTranslateX(-350);
        quitGameButton.setTranslateX(-350);
        newGameButton.setTranslateY(90);
        loadButton.setTranslateY(140);
        languagesButton.setTranslateY(190);
        quitGameButton.setTranslateY(240);


        titleLabel.setTranslateX(-330);
        titleLabel.setTranslateY(-150);


        titleLabel.setWrapText(true);
        titleLabel.setMaxWidth(200);


        scene = new Scene( stackPane);
        scene.setFill(Color.BLACK);
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }


    /**
     * The MakeButtonsTransparent makes the buttons' background as transparent.
     * @param button is the button for which the background needs to be made transparent.
     * @ author Harsimar Singh
     */
    private void MakeButtonsTransparent(Button button){
        button.setStyle("-fx-background-color: rgba(0, 0, 0, 0.05); -fx-text-fill: black;");
    }


    /**
     * The ShowAllLanguages method displays all the languages in a drop-down menu in a new stage
     * for the user to select a language.
     * @param languagesButton is the button with which a new stage appears with a drop-down box
     *                        for every language available.
     * @ author Harsimar Singh
     */

    private void ShowAllLanguages(Button languagesButton){
        languagesButton.setOnAction(event -> {
            final Stage dialog = new Stage(); //dialogue box
            dialog.initModality(Modality.APPLICATION_MODAL);
            dialog.initOwner(this.stage);


            ListView<String>  languagesList = new ListView<>();






            VBox dialogVbox = new VBox(20);
            dialogVbox.setPadding(new Insets(20, 20, 20, 20));
            dialogVbox.setStyle("-fx-background-color: #121212;");
            Label selectLanguageLabel = new Label();
            selectLanguageLabel.setId("CurrentLanguage"); // DO NOT MODIFY ID
            languagesList.setId("LanguagesList");  // DO NOT MODIFY ID
            languagesList.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
            addLanguages(languagesList);

            Button selectLanguageButton = new Button("Select language");
            selectLanguageButton.setId("SelectLanguage"); // DO NOT MODIFY ID
            AdventureGameView.makeButtonAccessible(selectLanguageButton, "select language", "This is the button to select a language", "Use this button to indicate a language you would like to select.");


            selectLanguageButton.setOnAction(event1 -> {
                currentLanguage = languagesList.getSelectionModel().getSelectedItem();
                if(currentLanguage==null){
                    currentLanguage="English";
                }
                selectLanguageLabel.setText(currentLanguage);
                loadButton.setText(TranslatorExample.Translate(AdventureGameView.languagesMap.get(currentLanguage),"en","LOAD"));
                languagesButton.setText(TranslatorExample.Translate(AdventureGameView.languagesMap.get(currentLanguage),"en","LANGUAGES"));
                quitGameButton.setText(TranslatorExample.Translate(AdventureGameView.languagesMap.get(currentLanguage),"en","QUIT GAME"));
                newGameButton.setText(TranslatorExample.Translate(AdventureGameView.languagesMap.get(currentLanguage),"en","NEW GAME"));
                titleLabel.setText(TranslatorExample.Translate(AdventureGameView.languagesMap.get(currentLanguage),"en","THE CRYPTIC ODYSSEY"));

                titleLabel.setFont(Font.font("Arial", 40));
                titleLabel.setTextFill(Color.BLACK);
                titleLabel.setTranslateX(-330);
                titleLabel.setTranslateY(-150);


                titleLabel.setWrapText(true);
                titleLabel.setMaxWidth(200);

                this.stage.setTitle(TranslatorExample.Translate(AdventureGameView.languagesMap.get(currentLanguage),"en","group_43's Adventure Game"));
            });




            VBox selectGameBox = new VBox(10, selectLanguageLabel, languagesList, selectLanguageButton);

            // Default styles which can be modified
            languagesList.setPrefHeight(100);
            selectLanguageLabel.setStyle("-fx-text-fill: #e8e6e3");
            selectLanguageLabel.setFont(new Font(16));
            selectLanguageButton.setStyle("-fx-background-color: #17871b; -fx-text-fill: white;");
            selectLanguageButton.setPrefSize(200, 50);
            selectLanguageButton.setFont(new Font(16));

            selectGameBox.setAlignment(Pos.CENTER);
            dialogVbox.getChildren().add(selectGameBox);
            Scene dialogScene = new Scene(dialogVbox, 400, 400);
            dialog.setScene(dialogScene);
            dialog.show();
        });
    }



    /**
     * The addLanguages method adds the languages from the languagesList to the drop-down menu.
     * @param languagesList is the list that contains all available languages.
     * @ author Harsimar Singh
     */
    private void addLanguages(ListView<String> languagesList){
        languagesList.getItems().add("English");
        languagesList.getItems().add("Hindi");
        languagesList.getItems().add("Persian");
        languagesList.getItems().add("Spanish");
        languagesList.getItems().add("Italian");
        languagesList.getItems().add("French");
        languagesList.getItems().add("Turkish");
        languagesList.getItems().add("Arabic");
    }


    /**
     * The ShowVBoxForNewGame makes a inputTextField for the player to enter the game's name.
     * @ author Harsimar Singh
     */
    private void ShowVBoxForNewGame(){
        //note that the buttons in this view are not accessible!!
        Label selectLanguageLabel = new Label(String.format(TranslatorExample.Translate(AdventureGameView.languagesMap.get(currentLanguage),"en","Write the name of the game and press enter.")));

        final Stage dialog = new Stage(); //dialogue box
        dialog.initModality(Modality.APPLICATION_MODAL);
        dialog.initOwner(this.stage);

        TextField textField = new TextField();

        VBox dialogVbox = new VBox(20,textField, selectLanguageLabel);
        dialogVbox.setPadding(new Insets(20, 20, 20, 20));
        dialogVbox.setStyle("-fx-background-color: #121212;");


        selectLanguageLabel.setStyle("-fx-text-fill: #e8e6e3");
        selectLanguageLabel.setFont(new Font(16));
        Scene dialogScene = new Scene(dialogVbox, 400, 150);
        dialog.setScene(dialogScene);
        dialog.show();


        textField.setOnKeyPressed(keyEvent -> {
            if(keyEvent.getCode()==KeyCode.ENTER){
                String s = textField.getText().strip();
                textField.clear();
                try{
                    this.model = new AdventureGame(s);
                    this.adventureGameView = new AdventureGameView(this.model,this.stage, currentLanguage);
                    dialog.close();
                } catch (RuntimeException e){
                    selectLanguageLabel.setText(TranslatorExample.Translate(AdventureGameView.languagesMap.get(currentLanguage),"en","No such file exists."));
                }
            }


        });

    }}



