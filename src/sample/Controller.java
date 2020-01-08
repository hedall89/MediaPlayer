package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.*;
import javafx.scene.media.*;
import javafx.scene.control.*;
import javafx.fxml.FXML;

import java.io.*;
import java.net.*;
import java.util.ResourceBundle;

public class Controller implements Initializable
{
    @FXML
    private MediaView mediaV;
    @FXML
    public Button play;
    @FXML
    public MediaPlayer mp;
    public Media me;
    @FXML
    public TextField searchfield;
    @FXML
    private ListView<?> searchlist;
    @FXML
    private Button button1;
    /**
     * This method is invoked automatically in the beginning. Used for initializing, loading data etc.
     *
     * @param location
     * @param resources
     */
    @FXML
    public void initialize(URL location, ResourceBundle resources)
    {
        // Build the path to the location of the media file
        String path = new File("src/sample/media/file_example_MP4_640_3MG.mp4").getAbsolutePath();
        // Create new Media object (the actual media content)
        me = new Media(new File(path).toURI().toString());
        // Create new MediaPlayer and attach the media to be played
        mp = new MediaPlayer(me);
        //
        mediaV.setMediaPlayer(mp);
        // mp.setAutoPlay(true);
        // If autoplay is turned of the method play(), stop(), pause() etc controls how/when medias are played
        mp.setAutoPlay(false);
    }
    /**
     * Handler for the play button
     */
    @FXML
    public void handlePlay()
    {
        // Play the mediaPlayer with the attached media
        mp.play();
    }

    @FXML

    private void handleStop()
    {
        mp.stop();
    }

    @FXML

    private void handlePause()
    {
        mp.pause();
    }

    @FXML
    /**
     *
     * Method to be able to search
     * for videos by title or category
     */
    public void HandleSearch()
    {

    }
    @FXML
    private void handleTestButton(ActionEvent event) {
        ObservableList<String> names = FXCollections.observableArrayList(
                "Julia", "Ian", "Sue", "Matthew", "Hannah", "Stephan", "Denise");
        ListView<String> searchlist = new ListView<String>(names);
        searchlist.getItems().setAll(names);
        searchlist.setItems(names);
        System.out.println(names);
    }
}


