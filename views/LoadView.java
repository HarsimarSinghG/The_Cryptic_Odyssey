package views;


import AdventureModel.AdventureGame;
import AdventureModel.AdventureLoader;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Modality;
import javafx.stage.Stage;


import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;




/**
 * Class LoadView.
 *
 * Loads Serialized adventure games.
 */
public class LoadView {


    private AdventureGameView adventureGameView;
    private Label selectGameLabel;
    private Button selectGameButton;
    private Button closeWindowButton;


    private ListView<String> GameList;
    private String filename = null;


    public LoadView(AdventureGameView adventureGameView){


        //note that the buttons in this view are not accessible!!
        this.adventureGameView = adventureGameView;
        selectGameLabel = new Label(String.format(""));


        GameList = new ListView<>(); //to hold all the file names


        final Stage dialog = new Stage(); //dialogue box
        dialog.initModality(Modality.APPLICATION_MODAL);
        dialog.initOwner(adventureGameView.stage);


        VBox dialogVbox = new VBox(20);
        dialogVbox.setPadding(new Insets(20, 20, 20, 20));
        dialogVbox.setStyle("-fx-background-color: #121212;");
        selectGameLabel.setId("CurrentGame"); // DO NOT MODIFY ID
        GameList.setId("GameList");  // DO NOT MODIFY ID
        GameList.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        getFiles(GameList); //get files for file selector
        selectGameButton = new Button(TranslatorExample.Translate(AdventureGameView.languagesMap.get(adventureGameView.language),"en","Change Game"));
        selectGameButton.setId("ChangeGame"); // DO NOT MODIFY ID
        AdventureGameView.makeButtonAccessible(selectGameButton, "select game", "This is the button to select a game", "Use this button to indicate a game file you would like to load.");




        Button deleteSaveFileButton = new Button(TranslatorExample.Translate(AdventureGameView.languagesMap.get(adventureGameView.language),"en","Delete Save File"));
        deleteSaveFileButton.setId("DeleteSaveFile");
        AdventureGameView.makeButtonAccessible(deleteSaveFileButton, "delete save file", "This is the button to delete a save file", "Use this button to indicate a game file you would like to delete.");




        closeWindowButton = new Button("Close Window");
        closeWindowButton.setId("closeWindowButton"); // DO NOT MODIFY ID
        closeWindowButton.setStyle("-fx-background-color: #17871b; -fx-text-fill: white;");
        closeWindowButton.setPrefSize(200, 50);
        closeWindowButton.setFont(new Font(16));
        closeWindowButton.setOnAction(e -> dialog.close());
        AdventureGameView.makeButtonAccessible(closeWindowButton, "close window", "This is a button to close the load game window", "Use this button to close the load game window.");


        //on selection, do something
        selectGameButton.setOnAction(e -> {
            try {
                selectGame(selectGameLabel, GameList);
                dialog.close();
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });


        deleteSaveFileButton.setOnAction(event -> {
            try {
                Path path = Paths.get("Games/Saved/"+GameList.getSelectionModel().getSelectedItem());
                Files.delete(path);
                dialog.close();
                new LoadView(adventureGameView);
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });


        VBox selectGameBox = new VBox(10, selectGameLabel, GameList, selectGameButton, deleteSaveFileButton);


        // Default styles which can be modified
        GameList.setPrefHeight(100);
        selectGameLabel.setStyle("-fx-text-fill: #e8e6e3");
        selectGameLabel.setFont(new Font(16));
        selectGameButton.setStyle("-fx-background-color: #17871b; -fx-text-fill: white;");
        selectGameButton.setPrefSize(200, 50);
        selectGameButton.setFont(new Font(16));




        deleteSaveFileButton.setStyle("-fx-background-color: #17871b; -fx-text-fill: white;");
        deleteSaveFileButton.setPrefSize(200, 50);
        deleteSaveFileButton.setFont(new Font(16));


        selectGameBox.setAlignment(Pos.CENTER);
        dialogVbox.getChildren().add(selectGameBox);
        Scene dialogScene = new Scene(dialogVbox, 400, 400);
        dialog.setScene(dialogScene);
        dialog.show();
    }


    /**
     * Get Files to display in the on screen ListView
     * Populate the listView attribute with .ser file names
     * Files will be located in the Games/Saved directory
     *
     * @param listView the ListView containing all the .ser files in the Games/Saved directory.
     */
    private void getFiles(ListView<String> listView) {
//        throw new UnsupportedOperationException("getFiles is not implemented");
        File file = new File("Games/Saved");
        File[] files = file.listFiles();
        if(files!=null) {
            for (File file1 : files) {
                if (file1.isFile() && file1.getName().endsWith(".ser")) {
                    listView.getItems().add(file1.getName());
                }
            }
        }
    }


    /**
     * Select the Game
     * Try to load a game from the Games/Saved
     * If successful, stop any articulation and put the name of the loaded file in the selectGameLabel.
     * If unsuccessful, stop any articulation and start an entirely new game from scratch.
     * In this case, change the selectGameLabel to indicate a new game has been loaded.
     *
     * @param selectGameLabel the label to use to print errors and or successes to the user.
     * @param GameList the ListView to populate
     */
    private void selectGame(Label selectGameLabel, ListView<String> GameList) throws IOException {
        //saved games will be in the Games/Saved folder!
//        throw new UnsupportedOperationException("selectGame is not implemented");
        this.adventureGameView.stopArticulation();
        String name = GameList.getSelectionModel().getSelectedItem();
        String alternateSetGameLabelText = TranslatorExample.Translate(AdventureGameView.languagesMap.get(adventureGameView.language),"en","A new game was loaded.");
        if(name==null){
            this.adventureGameView.model = new AdventureGame(this.adventureGameView.model.getDirectoryName().substring(6));
            selectGameLabel.setText(alternateSetGameLabelText);
        }
        else{
            try {
                AdventureGame adventureGame = loadGame("Games/Saved/"+name);
                this.adventureGameView.model = adventureGame;
                selectGameLabel.setText(name);
            }
            catch (ClassNotFoundException e) {
                this.adventureGameView.model = new AdventureGame(this.adventureGameView.model.getDirectoryName().substring(6));
                selectGameLabel.setText(alternateSetGameLabelText);

            }
            catch (StreamCorruptedException e){
                this.adventureGameView.model = new AdventureGame(this.adventureGameView.model.getDirectoryName().substring(6));
                selectGameLabel.setText(alternateSetGameLabelText);
            }}

        adventureGameView.updateScene("");
        adventureGameView.updateItems();
    }






    /**
     * Load the Game from a file
     *
     * @param GameFile file to load
     * @return loaded Tetris Model
     */
    public AdventureGame loadGame(String GameFile) throws IOException, ClassNotFoundException {
        // Reading the object from a file
        FileInputStream file = null;
        ObjectInputStream in = null;
        try {
            file = new FileInputStream(GameFile);
            in = new ObjectInputStream(file);
            return (AdventureGame) in.readObject();
        } finally {
            if (in != null) {
                in.close();
                file.close();
            }
        }
    }


}







