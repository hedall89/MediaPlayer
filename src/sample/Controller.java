package sample;

import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.beans.binding.Bindings;
import javafx.beans.property.DoubleProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.*;
import javafx.scene.media.*;
import javafx.scene.control.*;
import javafx.fxml.FXML;

import sample.video.video;

import java.awt.event.ActionEvent;
import java.io.*;
import java.net.*;
import java.util.List;
import java.util.ResourceBundle;
import sample.video.*;
import sample.playlist.*;
public class Controller implements Initializable
{

    ObservableList list = FXCollections.observableArrayList();
    private static List<video> Videos;
    @FXML
    private MediaView mediaV;
    @FXML
    private Button play;
    @FXML
    private MediaPlayer mp;
    @FXML
    private Media me;
    @FXML
    private TextField textfield;
    @FXML
    private ListView<String> songlist;
    @FXML
    private Button button1;
    @FXML Slider volumeSlider;

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

        mediaV.setMediaPlayer(mp);
        // mp.setAutoPlay(true);
        // If autoplay is turned of the method play(), stop(), pause() etc controls how/when medias are played
        mp.setAutoPlay(false);
        DoubleProperty width = mediaV.fitWidthProperty();
        DoubleProperty height = mediaV.fitHeightProperty();
        width.bind(Bindings.selectDouble(mediaV.sceneProperty(), "width"));
        height.bind(Bindings.selectDouble(mediaV.sceneProperty(), "height"));
        volumeSlider.setValue(mp.getVolume()*100);
        volumeSlider.valueProperty().addListener(new InvalidationListener() {
            @Override
            public void invalidated(Observable observable)
            {
                mp.setVolume(volumeSlider.getValue()/100);
            }
        })
        new video();
        HandleSearch();
    }

    @FXML
    public void handlePlay() { mp.play(); }
    @FXML
    private void handleStop() { mp.stop(); }
    @FXML
    private void handlePause() { mp.pause(); }
    @FXML
    /**
     * Method to handle the search field in the GUI
     */
    public void play(ActionEvent event)
    {
        mp.play();
    }
    public void pause (ActionEvent event)
    {
        mp.pause();
    }
    public void fast (ActionEvent event)
    {
        mp.setRate(2);
    }
    public void  slow (ActionEvent event)
    {
        mp.setRate(0.5);
    }
    public void reload(ActionEvent event)
    {
        mp.seek(mp.getStartTime());
        mp.play();
    }
    public void start(ActionEvent event)
    {
        mp.seek(mp.getStartTime());
        mp.stop();
    }
    public void last(ActionEvent event)
    {
        mp.seek(mp.getTotalDuration());
        mp.stop();
    }
    public void HandleSearch()
    {
        VideoLibrary VL = new VideoLibrary();
        //Create search by foreach loop and arraylist
        //Connect to Database, search with title or category
        loadData();
       // VL.searchForSongs(textfield, searchlist);
    }
    private void loadData ()
    {
        list.removeAll(list);
        ObservableList<String> names = FXCollections.observableArrayList(
                "Julia", "Ian", "Sue", "Matthew", "Hannah", "Stephan", "Denise");

        songlist.getItems().addAll(names);
    }
}






