package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.*;
import javafx.scene.media.*;
import javafx.scene.control.*;
import javafx.fxml.FXML;

import sample.video.video;

import java.io.*;
import java.net.*;
import java.util.List;
import java.util.ResourceBundle;
import sample.video.video;

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
    private TextField searchfield;
    @FXML
    private ListView<String> searchlist;
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



        new video();
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
    public void HandleSearch()
    {



        //Create search by foreach loop and arraylist
        //Connect to Database, search with title or category
        loadData();
    }


    /*
    public video search(String title, String Category) // TO DO
    {
        for (video video1 : Videos) {
            if (video.getVideoTitle() == title) { return video1;}
        }
        for (video video2 : Videos) {
            if (video.getVideoCategory() == Category) { return video2;}
        }
        return null;
    }

     */

    private void loadData ()
    {
        list.removeAll(list);
        ObservableList<String> names = FXCollections.observableArrayList(
                "Julia", "Ian", "Sue", "Matthew", "Hannah", "Stephan", "Denise");

        searchlist.getItems().addAll(names);

    }
    /*
    private void SelectVideo ()
    {
        DB.selectSQL("SELECT fldVideoID FROM tblVideo");
        do {
            String vid = DB.getData();
            if (vid.equals(DB.NOMOREDATA)) break;
            Videos.add(new video(Integer.parseInt(vid)));
        } while (true);
    }

     */
}






