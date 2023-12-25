package views;

import AdventureModel.AdventureGame;
import AdventureModel.AdventureObject;
import AdventureModel.Passage;
import AdventureModel.Room;

import AdventureModel.*;

import javafx.animation.PauseTransition;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.AccessibleRole;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.*;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Objects;
import java.util.HashMap;

/**
 * Class AdventureGameView.
 *
 * This is the Class that will visualize your model.
 * You are asked to demo your visualization via a Zoom
 * recording. Place a link to your recording below.
 *
 */
public class AdventureGameView implements Serializable {

    public boolean fontsToggle = false;
    String language = "English";
    static HashMap<String,String> languagesMap = new HashMap<>();
    AdventureGame model; //model of the game
    Stage stage; //stage on which all is rendered
    //    -----------------------------------------------------------------------------------------------------------------
    Button loadOrDeleteButton,GamesButton, saveButton, helpButton, settingsButton, Contrast, KeyboardControls, NarrationOption; //buttons
    ContrastOptions contrastOptions = new ContrastOptions();
    KeyboardNavigation keyboardNavigation = new KeyboardNavigation();
    Narration narration = new Narration();
//    -----------------------------------------------------------------------------------------------------------------
    public Boolean helpToggle = false; //is help on display?
    Boolean settingsToggle = false; //is settings on display?
    ArrayList<Integer> roomsList = new ArrayList<Integer>();
    public static Integer objectsCollected = 0;
    public GridPane gridPane = new GridPane(); //to hold images and buttons

    ZoomFeature zoomFeature;
    FontFeature fontFeature;
    Font currentFont = new Font("Arial", 17);
    Label objLabel, commandLabel, invLabel;

    public ImageView Health = new ImageView();
    //to hold images and buttons

    public StackPane stackPane = new StackPane();
    public Label roomDescLabel = new Label(); //to hold room description and/or instructions
    VBox objectsInRoom = new VBox(); //to hold room items
    VBox objectsInInventory = new VBox(); //to hold inventory items
    ImageView roomImageView; //to hold room image
    public TextField inputTextField; //for user input
    Scene scene;
    private MediaPlayer mediaPlayer; //to play audio
    private boolean mediaPlaying; //to know if the audio is playing

    /**
     * Adventure Game View Constructor
     * __________________________
     * Initializes attributes
     */
    public AdventureGameView(AdventureGame model, Stage stage, String language) {
        InitialiseHashMap(languagesMap);
        this.language = language;
        this.model = model;
        this.stage = stage;
        intiUI(0);
    }

    public AdventureGameView(Stage stage, String language){
        InitialiseHashMap(languagesMap);
        this.language = language;
        this.stage = stage;
        intiUI(1);
    }


    /**
     * This method populates the languagesMap with all the available languages in the game.
     * @param languagesMap is the available languages in the game.
     * @ author Harsimar Singh
     */
    private void InitialiseHashMap(HashMap<String,String> languagesMap){
        languagesMap.put("English","en");
        languagesMap.put("Hindi","hi");
        languagesMap.put("Persian","fa");
        languagesMap.put("Spanish","es");
        languagesMap.put("Italian","it");
        languagesMap.put("French","fr");
        languagesMap.put("Turkish","tr");
        languagesMap.put("Arabic","ar");
    }

    /**
     * Initialize the UI
     */
    public void intiUI(int check) {

        // setting up the stage
//        this.stage.setTitle("group_43's Adventure Game"); //Replace <YOUR UTORID> with your UtorID

        //Inventory + Room items
        objectsInInventory.setSpacing(10);
        objectsInInventory.setAlignment(Pos.TOP_CENTER);
        objectsInRoom.setSpacing(10);
        objectsInRoom.setAlignment(Pos.TOP_CENTER);
        roomsList.add(1);
        // GridPane, anyone?
        gridPane.setPadding(new Insets(20));
        gridPane.setBackground(new Background(new BackgroundFill(
                Color.valueOf("#000000"),
                new CornerRadii(0),
                new Insets(0)
        )));

        //Three columns, three rows for the GridPane
        ColumnConstraints column1 = new ColumnConstraints(150);
        ColumnConstraints column2 = new ColumnConstraints(650);
        ColumnConstraints column3 = new ColumnConstraints(150);
        column3.setHgrow( Priority.SOMETIMES ); //let some columns grow to take any extra space
        column1.setHgrow( Priority.SOMETIMES );

        // Row constraints
        RowConstraints row1 = new RowConstraints();
        RowConstraints row2 = new RowConstraints( 550 );
        RowConstraints row3 = new RowConstraints();
        row1.setVgrow( Priority.SOMETIMES );
        row3.setVgrow( Priority.SOMETIMES );

        gridPane.getColumnConstraints().addAll( column1 , column2 , column1 );
        gridPane.getRowConstraints().addAll( row1 , row2 , row1 );

        // Buttons
        saveButton = new Button(TranslatorExample.Translate(languagesMap.get(language),"en","Save"));
        saveButton.setId("Save");
        customizeButton(saveButton, 200, 50);
        makeButtonAccessible(saveButton, "Save Button", "This button saves the game.", "This button saves the game. Click it in order to save your current progress, so you can play more later.");
        addSaveEvent();

        loadOrDeleteButton = new Button(TranslatorExample.Translate(languagesMap.get(language),"en","Load/Delete"));
        loadOrDeleteButton.setId("Load_Delete");
        customizeButton(loadOrDeleteButton, 200, 50);
        makeButtonAccessible(loadOrDeleteButton, "Load Button", "This button loads a game from a file.", "This button loads the game from a file. Click it in order to load a game that you saved at a prior date.");
        addLoadEvent();

        helpButton = new Button(TranslatorExample.Translate(languagesMap.get(language),"en","Help"));
        helpButton.setId("Help");
        customizeButton(helpButton, 70, 50);
        makeButtonAccessible(helpButton, "Help Button", "This button gives game instructions.", "This button gives instructions on the game controls. Click it to learn how to play.");
        addInstructionEvent();
        // ------------------------------------------------------------------------------------------------------------------------

        // Setting feature initializations
        settingsButton = new Button(TranslatorExample.Translate(languagesMap.get(language),"en","Settings"));
        settingsButton.setId("Setting");
        customizeButton(settingsButton, 200, 50);
        addSettingEvent();

        Contrast = new Button(TranslatorExample.Translate(languagesMap.get(language),"en","Color Blindness Contrast"));
        NarrationOption = new Button(TranslatorExample.Translate(languagesMap.get(language),"en","Mouse input speech"));
        KeyboardControls = new Button(TranslatorExample.Translate(languagesMap.get(language),"en","Keyboard Only Users"));
        customizeButton(Contrast, 250, 50);
        customizeButton(NarrationOption, 250, 50);
        customizeButton(KeyboardControls, 250, 50);

        zoomFeature = new ZoomFeature(currentFont, gridPane, this);
        fontFeature = new FontFeature(currentFont, gridPane, this);

        saveButton.setFont(currentFont);
        loadOrDeleteButton.setFont(currentFont);
        helpButton.setFont(currentFont);
        settingsButton.setFont(currentFont);
        Contrast.setFont(currentFont);
        NarrationOption.setFont(currentFont);
        KeyboardControls.setFont(currentFont);

        // ------------------------------------------------------------------------------------------------------------------------


        GamesButton = new Button(TranslatorExample.Translate(languagesMap.get(language),"en","Change Game"));
        GamesButton.setId("Show Available Games");
        customizeButton(GamesButton, 140, 50);
        makeButtonAccessible(GamesButton, "Show Available Games", "This button loads a game from a file.", "This button loads the game from a file. Click it in order to load a game that you saved at a prior date.");
        addGameView();

        HBox topButtons = new HBox();
        topButtons.getChildren().addAll(helpButton,settingsButton, GamesButton);
        topButtons.setSpacing(10);
        topButtons.setAlignment(Pos.CENTER);

        inputTextField = new TextField();
        inputTextField.setFont(currentFont);
        inputTextField.setFocusTraversable(true);

        inputTextField.setAccessibleRole(AccessibleRole.TEXT_AREA);
        inputTextField.setAccessibleRoleDescription("Text Entry Box");
        inputTextField.setAccessibleText("Enter commands in this box.");
        inputTextField.setAccessibleHelp("This is the area in which you can enter commands you would like to play.  Enter a command and hit return to continue.");
        addTextHandlingEvent(); //attach an event to this input field

        //labels for inventory and room items
        objLabel =  new Label(TranslatorExample.Translate(languagesMap.get(language),"en","Objects in Room"));
        objLabel.setAlignment(Pos.CENTER);
        objLabel.setStyle("-fx-text-fill: white;");
        objLabel.setFont(currentFont);

        invLabel =  new Label(TranslatorExample.Translate(languagesMap.get(language),"en","Your Inventory"));
        invLabel.setAlignment(Pos.CENTER);
        invLabel.setStyle("-fx-text-fill: white;");
        invLabel.setFont(currentFont);

        //add all the widgets to the GridPane
        gridPane.add( objLabel, 0, 0, 1, 1 );  // Add label
        gridPane.add( topButtons, 1, 0, 1, 1 );  // Add buttons
        gridPane.add( invLabel, 2, 0, 1, 1 );  // Add label

        Label statsLabel = new Label(TranslatorExample.Translate(languagesMap.get(language),"en","Rooms visted: 1/")+"10 , "+TranslatorExample.Translate(languagesMap.get(language),"en","Objects collected: 0/")+ AdventureLoader.ReturnTotalObjects());
        statsLabel.setStyle("-fx-text-fill: white;");
        statsLabel.setTranslateX(380);
        statsLabel.setTranslateY(-375);

        commandLabel = new Label(TranslatorExample.Translate(languagesMap.get(language),"en","What would you like to do?"));
        commandLabel.setStyle("-fx-text-fill: white;");
        commandLabel.setFont(currentFont);

        if(check==0) {
            updateScene(""); //method displays an image and whatever text is supplied
            updateItems(); //update items shows inventory and objects in rooms
        }
        // adding the text area and submit button to a VBox
        VBox textEntry = new VBox();
        textEntry.setStyle("-fx-background-color: #000000;");
        textEntry.setPadding(new Insets(20, 20, 20, 20));
        textEntry.getChildren().addAll(commandLabel, inputTextField);
        textEntry.setSpacing(10);
        textEntry.setAlignment(Pos.CENTER);
        gridPane.add( textEntry, 0, 2, 3, 1 );

        String path = "hp 5.png";
        Image curr_health = new Image(path);
        Health = new ImageView(curr_health);

        Health.setPreserveRatio(true);
        Health.setFitHeight(150);
        Health.setFitWidth(150);
        Health.setTranslateX(-400);
        Health.setTranslateY(-375);

        stackPane.getChildren().addAll(gridPane, Health, statsLabel);
        // Render everything

        scene = new Scene(stackPane ,  1000, 800);
        PushToTalk();
        scene.setFill(Color.BLACK);
        if(check==0){
            this.stage.setScene(scene);}
//        this.stage.setResizable(false);
//        this.stage.show();

    }
    /**
     * Update Health Label whenever room or object is updated
     * @author Harsh
     */
    public void updateHealth(Integer health){
        if (health >= 5){
            health = 5;
        }
        String path = "hp " + health.toString() +".png";
        Image curr_health = new Image(path);
        Node _x = stackPane.getChildren().remove(1);

        Health = new ImageView(curr_health);

        Health.setPreserveRatio(true);
        Health.setFitHeight(150);
        Health.setFitWidth(150);
        Health.setTranslateX(-400);
        Health.setTranslateY(-375);
        stackPane.getChildren().add(1, Health);
    }


    private void PushToTalk(){
        scene.setOnKeyPressed(keyEvent -> {
            if(keyEvent.getCode()==KeyCode.EQUALS){
                stopArticulation();
                try {
                    String result = SpeechToText.Convert(languagesMap.get(language));
                    String translatedText = TranslatorExample.Translate("en",languagesMap.get(language),result);
                    translatedText = translatedText.replace(" the "," ");
                    translatedText = translatedText.replace(" a "," ");
                    translatedText = translatedText.replace(" an "," ");
                    submitEvent(translatedText);
                } catch (Exception e) {
                    System.out.println("An error occurred during speech conversion.");
                }
            }
        });
    }




    /**
     * makeButtonAccessible
     * __________________________
     * For information about ARIA standards, see
     * https://www.w3.org/WAI/standards-guidelines/aria/
     * @param inputButton the button to add screenreader hooks to
     * @param name ARIA name
     * @param shortString ARIA accessible text
     * @param longString ARIA accessible help text
     */
    public static void makeButtonAccessible(Button inputButton, String name, String shortString, String longString) {
        inputButton.setAccessibleRole(AccessibleRole.BUTTON);
        inputButton.setAccessibleRoleDescription(name);
        inputButton.setAccessibleText(shortString);
        inputButton.setAccessibleHelp(longString);
        inputButton.setFocusTraversable(true);
    }

    /**
     * customizeButton
     * __________________________
     *
     * @param inputButton the button to make stylish :)
     * @param w width
     * @param h height
     */
    public void customizeButton(Button inputButton, int w, int h) {
        inputButton.setPrefSize(w, h);
        inputButton.setFont(new Font("Arial", 16));
        inputButton.setStyle("-fx-background-color: #17871b; -fx-text-fill: white;");
    }

    /**
     * addTextHandlingEvent
     * __________________________
     * Add an event handler to the myTextField attribute
     * Your event handler should respond when users
     * hits the ENTER or TAB KEY. If the user hits
     * the ENTER Key, strip white space from the
     * input to myTextField and pass the stripped
     * string to submitEvent for processing.
     * If the user hits the TAB key, move the focus
     * of the scene onto any other node in the scene
     * graph by invoking requestFocus method.
     */
    private void addTextHandlingEvent() {
        //add your code here!
//        EventHandler<KeyEvent> eventEventHandler = new EventHandler<KeyEvent>() {
//            @Override
//            public void handle(KeyEvent keyEvent) {
//                if(keyEvent.getCode()== KeyCode.ENTER){
//                    String s = inputTextField.getText().strip();
//                    submitEvent(s);
//                    inputTextField.clear();
//                }
//                else if(keyEvent.getCode()==KeyCode.TAB){
//                    helpButton.requestFocus();
//                }
//            }
//        };
//        this.inputTextField.addEventHandler(KeyEvent.KEY_PRESSED,eventEventHandler);
        this.inputTextField.setOnKeyPressed(keyEvent -> {
            if(keyEvent.getCode()==KeyCode.ENTER){
                String translatedText = TranslatorExample.Translate("en",languagesMap.get(language),inputTextField.getText());
                translatedText = translatedText.replace(" the "," ");
                translatedText = translatedText.replace(" a "," ");
                translatedText = translatedText.replace(" an "," ");
                String s = translatedText;
                if(this.helpToggle && s.equalsIgnoreCase("HELP") || s.equalsIgnoreCase("H")){
                    showInstructions();
                    inputTextField.clear();
                }
                else if(this.helpToggle){
                    showInstructions();
                    submitEvent(s);
                    inputTextField.clear();
                }
                else{
                    submitEvent(s);
                    inputTextField.clear();
                }
            }
            else if(keyEvent.getCode()==KeyCode.TAB){
                helpButton.requestFocus();
            }
        });
    }

    /**
     * submitEvent
     * __________________________
     * @param text the command that needs to be processed
     */
    public void submitEvent(String text) {

        text = text.strip(); //get rid of white space
        stopArticulation(); //if speaking, stop

        if (text.equalsIgnoreCase("LOOK") || text.equalsIgnoreCase("L")) {
            String roomDesc = this.model.getPlayer().getCurrentRoom().getRoomDescription();
            String objectString = this.model.getPlayer().getCurrentRoom().getObjectString();
            if (!objectString.isEmpty()) roomDescLabel.setText(roomDesc + "\n\nObjects in this room:\n" + objectString);
            articulateRoomDescription(); //all we want, if we are looking, is to repeat description.
            return;
        } else if (text.equalsIgnoreCase("HELP") || text.equalsIgnoreCase("H")) {
            showInstructions();
            return;
        } else if (text.equalsIgnoreCase("COMMANDS") || text.equalsIgnoreCase("C")) {
            showCommands(); //this is new!  We did not have this command in A1
            return;
        }

        //try to move!
        String output = this.model.interpretAction(text); //process the command!

        if(!roomsList.contains(this.model.getPlayer().getCurrentRoom().getRoomNumber())){
            roomsList.add(this.model.getPlayer().getCurrentRoom().getRoomNumber());
        }
        if (this.model.getPlayer().getCurrentRoom().getPoison() == true && !Objects.equals(output, "INVALID COMMAND")) {
            updateHealth(this.model.getPlayer().getHealth());
            if (this.model.getPlayer().getHealth() == 0) {
                stopArticulation();
                stage.setScene(null);
                TitleScreen.getInstance().setScene(stage);
                return;
            }
        }
        updateStatsLabel();
        if (output == null || (!output.equals("GAME OVER") && !output.equals("FORCED") && !output.equals("HELP"))) {
            updateScene(output);
            updateItems();
        } else if (output.equals("GAME OVER")) {
            updateScene("");
            updateItems();
            objectsInInventory.setVisible(false);
            this.inputTextField.setDisable(true);
            this.helpButton.setDisable(true);
            this.saveButton.setDisable(true);
            this.loadOrDeleteButton.setDisable(true);
            PauseTransition pause = new PauseTransition(Duration.seconds(10));
            pause.setOnFinished(event -> {
                Platform.exit();
            });
            pause.play();
        } else if (output.equals("FORCED")) {
            //write code here to handle "FORCED" events!
            //Your code will need to display the image in the
            //current room and pause, then transition to
            //the forced room.
            HandleForcedCommands();
        }
    }
    /**
     * Update Stats Label whenever room or object is updated
     * @author Harsh
     */
    private void  updateStatsLabel(){
        Node _x = stackPane.getChildren().remove(2);
        Label label = new Label(TranslatorExample.Translate(languagesMap.get(language),"en","Rooms visted: ")+(this.roomsList.size())+"/"+this.model.getRooms().size()+" , "+TranslatorExample.Translate(languagesMap.get(language),"en","Objects collected: ") +objectsCollected+"/"+ AdventureLoader.ReturnTotalObjects());
        label.setStyle("-fx-text-fill: white;");
        label.setTranslateX(380);
        label.setTranslateY(-375);
        stackPane.getChildren().add(2, label);
    }

    /**
     * When moves are
     * FORCED, a PauseTransition is played to pause the display for
     * several seconds (5 or 6 will do), and then transition automatically to the
     * room that lies in the FORCED direction.
     */
    private void HandleForcedCommands(){
        updateScene("");
        updateItems();
        this.settingsButton.setDisable(true);
        objectsInInventory.setVisible(false);
        this.inputTextField.setDisable(true);
        this.helpButton.setDisable(true);
        this.saveButton.setDisable(true);
        this.loadOrDeleteButton.setDisable(true);
        this.GamesButton.setDisable(true);
        PauseTransition pauseTransition = new PauseTransition(Duration.seconds(5));
        pauseTransition.setOnFinished(event -> {
            this.settingsButton.setDisable(false);
            this.inputTextField.setDisable(false);
            this.helpButton.setDisable(false);
            this.saveButton.setDisable(false);
            this.loadOrDeleteButton.setDisable(false);
            this.GamesButton.setDisable(false);
            objectsInInventory.setVisible(true);
            submitEvent("FORCED");
        });
        pauseTransition.play();
    }


    /**
     * showCommands
     * __________________________
     * update the text in the GUI (within roomDescLabel)
     * to show all the moves that are possible from the
     * current room.
     */
    private void showCommands() {
//        throw new UnsupportedOperationException("showCommands is not implemented!");
        String s = "The following directions are available:\n " + this.model.getPlayer().getCurrentRoom().getCommands();
        updateScene(s);
    }


    /**
     * updateScene
     * __________________________
     * Show the current room, and print some text below it.
     * If the input parameter is not null, it will be displayed
     * below the image.
     * Otherwise, the current room description will be displayed
     * below the image.
     * @param textToDisplay the text to display below the image.
     */
    public void updateScene(String textToDisplay) {

        stage.setScene(scene);
        getRoomImage(); //get the image of the current room
        formatText(textToDisplay); //format the text to display
        roomDescLabel.setPrefWidth(500);
        roomDescLabel.setFont(currentFont);
        roomDescLabel.setPrefHeight(500);
        roomDescLabel.setTextOverrun(OverrunStyle.CLIP);
        roomDescLabel.setWrapText(true);
        VBox roomPane = new VBox(roomImageView,roomDescLabel);
        roomPane.setPadding(new Insets(10));
        roomPane.setAlignment(Pos.TOP_CENTER);
        roomPane.setStyle("-fx-background-color: #000000;");

        gridPane.add(roomPane, 1, 1);
        stage.sizeToScene();

        //finally, articulate the description
        if (textToDisplay == null || textToDisplay.isBlank()) articulateRoomDescription();
    }

    /**
     * formatText
     * __________________________
     *
     * Format text for display.
     *
     * @param textToDisplay the text to be formatted for display.
     */
    private void formatText(String textToDisplay) {
        if (textToDisplay == null || textToDisplay.isBlank()) {
            String roomDesc = this.model.getPlayer().getCurrentRoom().getRoomDescription() + "\n";
            String objectString = this.model.getPlayer().getCurrentRoom().getObjectString();
            if (objectString != null && !objectString.isEmpty()){ roomDescLabel.setText(TranslatorExample.Translate(languagesMap.get(language),"en",roomDesc + "\n\nObjects in this room:\n" + objectString));
                roomDescLabel.setFont(currentFont);}
            else { roomDescLabel.setText(TranslatorExample.Translate(languagesMap.get(language),"en",roomDesc));
                roomDescLabel.setFont(currentFont);}
        } else{
            String translated = TranslatorExample.Translate(languagesMap.get(language),"en",textToDisplay);
            roomDescLabel.setText(translated);
            roomDescLabel.setFont(currentFont);


            TextToSpeech.Speak(this.model.getPlayer().getCurrentRoom().getRoomName(),translated,languagesMap.get(language));
            String musicFile;


            if(languagesMap.get(language)=="fa"){
                return;
            }
            else{
                musicFile=this.model.getPlayer().getCurrentRoom().getRoomName()+".mp3";}


            Media sound = new Media(new File(musicFile).toURI().toString());


            mediaPlayer = new MediaPlayer(sound);
            mediaPlayer.play();
            mediaPlaying = true;


        }
        roomDescLabel.setStyle("-fx-text-fill: white;");
        roomDescLabel.setFont(new Font("Arial", 16));
        roomDescLabel.setAlignment(Pos.CENTER);
    }


    /**
     * getRoomImage
     * __________________________
     * Get the image for the current room and place
     * it in the roomImageView
     */
    private void getRoomImage() {

        int roomNumber = this.model.getPlayer().getCurrentRoom().getRoomNumber();
        String roomImage = "file:"+ this.model.getDirectoryName() + "/room-images/" + roomNumber + ".png";

        Image roomImageFile = new Image(roomImage);
        roomImageView = new ImageView(roomImageFile);
        roomImageView.setPreserveRatio(true);
        roomImageView.setFitWidth(400);
        roomImageView.setFitHeight(400);

        //set accessible text
        roomImageView.setAccessibleRole(AccessibleRole.IMAGE_VIEW);
        roomImageView.setAccessibleText(this.model.getPlayer().getCurrentRoom().getRoomDescription());
        roomImageView.setFocusTraversable(true);
    }

    /**
     * updateItems
     * __________________________
     * This method is partially completed, but you are asked to finish it off.
     * The method should populate the objectsInRoom and objectsInInventory Vboxes.
     * Each Vbox should contain a collection of nodes (Buttons, ImageViews, you can decide)
     * Each node represents a different object.
     * Images of each object are in the assets
     * folders of the given adventure game.
     */
    public void updateItems() {

        objectsInRoom.getChildren().clear();
        objectsInInventory.getChildren().clear();

        ArrayList<AdventureObject> adventureObjects = this.model.getPlayer().getCurrentRoom().objectsInRoom;
        AddItems(adventureObjects, objectsInRoom ,0);

        ScrollPane scO = new ScrollPane(objectsInRoom);
        scO.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scO.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        scO.setPadding(new Insets(10));
        scO.setStyle("-fx-background: #000000; -fx-background-color:transparent;");
        scO.setFitToWidth(true);
        gridPane.add(scO,0,1);

        adventureObjects = this.model.getPlayer().inventory;
        AddItems(adventureObjects, objectsInInventory, 1);

        ScrollPane scI = new ScrollPane(objectsInInventory);
        scI.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scI.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        scI.setFitToWidth(true);
        scI.setStyle("-fx-background: #000000; -fx-background-color:transparent;");
        gridPane.add(scI,2,1);
    }

    /**
     * Displays the items in inventory and
     * in the room by adding the objects nodes in
     * the respective objectsInRoom, objectsInInventory list.
     *
     * MODIFICATIONS -- If Healing Potion is consumed, objects collected increase and the object cannot be used again.
     * ALSO          -- Screen updates to show Health has increased for 1 second, then again shows room description
     * @author Harsh
     */
    private void AddItems(ArrayList<AdventureObject> adventureObjects, VBox vBox, Integer check){
        for(AdventureObject adventureObject : adventureObjects){
            Image objectImage = new Image( "file:"+ this.model.getDirectoryName()+"/objectImages/"+adventureObject.getName()+".jpg");
            Label objectLabel = new Label(TranslatorExample.Translate(languagesMap.get(language),"en",adventureObject.getName()));
            objectLabel.setStyle("-fx-text-fill: black;");
            ImageView imageView = new ImageView(objectImage);
            imageView.setFitHeight(100);
            imageView.setFitWidth(100);
            VBox objectBox = new VBox(imageView,objectLabel);
            objectBox.setAlignment(Pos.CENTER);
            objectBox.setStyle("-fx-background-color: white;");
            objectBox.setPadding(new Insets(10));
            Button objectButton = new Button(TranslatorExample.Translate(languagesMap.get(language),"en",adventureObject.getName()),objectBox);
            makeButtonAccessible(objectButton,adventureObject.getName(),"This is " +adventureObject.getName()+ " object.",
                    "This is " +adventureObject.getName()+ " object. Click on it to remove it from inventory or take it from the room, depending on the position the object is currently in.");
            objectButton.setStyle("-fx-background-color: lightgray;");
            if(check==0){
                objectButton.setOnAction(event -> {
                    if(objectButton.getText().strip().equals("HEALING POTION")){
                        this.model.getPlayer().setHealth(this.model.getPlayer().getHealth()+1);
                        updateHealth(this.model.getPlayer().getHealth());
                        stopArticulation();
                        updateScene("Health potion has been consumed");

                        PauseTransition pauseTransition1 = new PauseTransition(Duration.seconds(1));
                        pauseTransition1.setOnFinished(event2 -> {
                            //After 1 SECOND, HIDE Room Objects and keep showing Inventory but GREYED OUT
                            updateScene("");
                        });
                        pauseTransition1.play();

                        this.model.getPlayer().getCurrentRoom().objectsInRoom.remove(adventureObject);
                        updateItems();
                        objectsCollected+=1;
                        updateStatsLabel();
                    }
                    else {
                        this.model.getPlayer().takeObject(adventureObject.getName());
                        updateItems();
                        updateStatsLabel();
                    }
                });
            }else{
                objectButton.setOnAction(event -> {
                    this.model.getPlayer().dropObject(adventureObject.getName());
                    updateItems();
                    updateStatsLabel();
                });
            }
            vBox.getChildren().add(objectButton);
        }
    }

    /**
     * Show the game instructions.
     * If helpToggle is FALSE:
     *  display the help text in the CENTRE of the gridPane (i.e. within cell 1,1)
     * -- use whatever GUI elements to get the job done!
     * -- set the helpToggle to TRUE
     * -- REMOVE whatever nodes are within the cell beforehand!
     * If helpToggle is TRUE:
     * -- redraw the room image in the CENTRE of the gridPane (i.e. within cell 1,1)
     * -- set the helpToggle to FALSE
     * -- Again, REMOVE whatever nodes are within the cell beforehand!
     * @author Harsh
     */
    public void showInstructions() {

        if(!this.helpToggle){
            this.helpToggle = true;
            Label instructionLabel = new Label(TranslatorExample.Translate(languagesMap.get(language),"en",this.model.getInstructions()));
            instructionLabel.setFont(currentFont);
            instructionLabel.setStyle("-fx-text-fill: white;");
            instructionLabel.setAlignment(Pos.CENTER);
            instructionLabel.setPrefWidth(630);
            instructionLabel.setWrapText(true);
            instructionLabel.setPadding(new Insets(10));
            updateItems();
            fontsToggle = false;
            VBox instructionBox = new VBox(instructionLabel);
            instructionBox.setAlignment(Pos.CENTER);
            instructionBox.setStyle("-fx-background-color: #000000;");
            ScrollPane instructionBoxScrollPane = new ScrollPane(instructionBox);
            instructionBoxScrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
            instructionBoxScrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
            RemoveIndexFromGridPane();
            gridPane.add(instructionBoxScrollPane,1,1);

        }
        else{
            this.helpToggle = false;
            RemoveIndexFromGridPane();
            updateScene("");
        }
    }
    /**
     * GRID PANE adjusted.
     * @author Harsh
     */
    public void RemoveIndexFromGridPane(){
        Node saved = null;

        for(Node node : gridPane.getChildren()){
            if(GridPane.getColumnIndex(node)==1 && GridPane.getRowIndex(node)==1){
                saved = node;
                break;
            }
        }

        if(saved!=null){
            gridPane.getChildren().remove(saved);
        }
    }
    /**
     * This method handles the event related to the
     * help button.
     * @author Harsh
     */
    public void addInstructionEvent() {
        helpButton.setOnAction(e -> {
            stopArticulation(); //if speaking, stop
            showInstructions();
        });
    }

    /**
     * This method handles the event related to the
     * save button.
     * @author Harsh
     */
    public void addSaveEvent() {
        saveButton.setOnAction(e -> {
            gridPane.requestFocus();
            stopArticulation();
            SaveView saveView = new SaveView(this);
        });
    }

    /**
     * This method handles the event related to the
     * load button.
     * @author Harsh
     */
    public void addLoadEvent() {
        loadOrDeleteButton.setOnAction(e -> {
            gridPane.requestFocus();
            stopArticulation();
            LoadView loadView = new LoadView(this);
        });
    }

    /**
     * This method handles the event related to the
     * change_game button.
     * @author Harsh
     */
    public void addGameView() {
        GamesButton.setOnAction(e -> {
            gridPane.requestFocus();
            stopArticulation();
            new ChangeGameView(this);
        });
    }
    /**
     * This method handles the event related to the
     * settings button.
     * @author Harsh
     */
    public void addSettingEvent() {
        settingsButton.setOnAction(e -> {
            gridPane.requestFocus();
            stopArticulation();
            SettingsMenu settings = new SettingsMenu(this);
        });
    }

//--------------------------------------------------------

    /**
     * This method articulates Room Descriptions
     */
    public void articulateRoomDescription() {
        String translated = TranslatorExample.Translate(languagesMap.get(language),"en",this.model.getPlayer().getCurrentRoom().getRoomDescription());
        TextToSpeech.Speak(this.model.getPlayer().getCurrentRoom().getRoomName(),translated,languagesMap.get(language));
        String musicFile;
        String adventureName = this.model.getDirectoryName();
        String roomName = this.model.getPlayer().getCurrentRoom().getRoomName();


        if(languagesMap.get(language)=="fa"){


            if (!this.model.getPlayer().getCurrentRoom().getVisited()) musicFile = "./" + adventureName + "/sounds/" + roomName.toLowerCase() + "-long.mp3" ;
            else musicFile = "./" + adventureName + "/sounds/" + roomName.toLowerCase() + "-short.mp3" ;
            musicFile = musicFile.replace(" ","-");}
        else{
            musicFile=this.model.getPlayer().getCurrentRoom().getRoomName()+".mp3";}


        Media sound = new Media(new File(musicFile).toURI().toString());


        mediaPlayer = new MediaPlayer(sound);
        mediaPlayer.play();
        mediaPlaying = true;
    }


    /**
     * This method stops articulations
     * (useful when transitioning to a new room or loading a new game)
     */
    public void stopArticulation() {
        if (mediaPlaying) {
            mediaPlayer.stop(); //shush!
            mediaPlaying = false;
        }
    }

    // ---------------------------------------------------------------------------------------------------------------

    // Setting button action

    // Setting menu frame creation



    // ---------------------------------------------------------------------------------------------------------------



}
