package sample;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.*;
import javafx.scene.media.*;
import javafx.scene.control.*;
import javafx.fxml.FXML;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.beans.binding.Bindings;
import javafx.beans.property.DoubleProperty;
import java.io.*;
import java.net.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.awt.event.ActionEvent;
import java.util.Set;
import java.util.logging.Logger;

import javafx.scene.paint.*;
import jdk.internal.org.objectweb.asm.Handle;

public class Controller implements Initializable
{
    @FXML private MediaView mediaV;
    @FXML private Button play;
    @FXML private MediaPlayer mp;
    @FXML private Media me;
    @FXML private TextField searchfield;
    @FXML private ListView<String> searchlist;
    @FXML private Slider volumeSlider;
    @FXML private Button pause;
    @FXML private  Button stop;
    @FXML private  Button speed;
    @FXML private ListView viwer;


    /////////////////////////////////////////////////////
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
        HandleSearch();
        HandleListofVideos();
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
        });
    }
    @FXML private void handlePlay() { mp.play(); }
    @FXML private void handleStop() { mp.stop(); }
    @FXML private void handlePause() { mp.pause(); }
    /**
     * Method to handle the search field in the GUI
     * @param
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


    @FXML
    public void HandleSearch() {
    // VideoLibrary VL = new VideoLibrary();
        String searchresult = "";
        int searchResultsFound = 0;
        ArrayList<String> arrayList = new ArrayList();
        searchresult = searchfield.getText();
        DB.selectSQL("SELECT COUNT(fldVideoTitle) from tblVideo WHERE (CHARINDEX('" + searchresult + "', fldVideoTitle) > 0 or CHARINDEX('" + searchresult + "', fldCategory) > 0 )");
        // COUNTS THE AMOUNT OF VIDEOS IN TBLVIDEO
        searchResultsFound = Integer.parseInt(DB.getData());
        System.out.println(searchResultsFound);
        // CLEARS BUFFER
       // cleardata();
        if (searchResultsFound > 0) {

            DB.selectSQL("SELECT fldVideoTitle FROM tblVideo WHERE (CHARINDEX('" + searchresult + "', fldVideoTitle) > 0 or CHARINDEX('" + searchresult + "', fldCategory) > 0)");
            //  displaySelectedData();
            do {
                String data = DB.getData();
                if (data.equals(DB.NOMOREDATA)) {
                    break;
                } else {
                    // WE ADD EACH ELEMENT TO THE ARRAY LIST
                    arrayList.add(data);
                    System.out.print(data);
                }
            } while (true);
        } else {
            System.out.println("Nothing Found, Check for Typos.");
            // maybe clear playlist here.
        }
        System.out.println(searchlist);
        searchlist.getItems().clear();

        for (int i = 0; i < arrayList.size(); i++) {
            searchlist.getItems().add(arrayList.get(i));
        }
        System.out.println(searchresult);
    }
    @FXML
    private void HandleCategoryOne()
    {
        ArrayList<String> arrayListOne = new ArrayList();
        DB.selectSQL("SELECT fldVideoTitle FROM tblVideo WHERE fldCategory = 'Entertainment'");
        Category(arrayListOne);
    }
    @FXML
    private void HandleCategoryTwo()
    {
        ArrayList<String> arrayListTwo = new ArrayList();
        DB.selectSQL("SELECT fldVideoTitle FROM tblVideo WHERE fldCategory = 'Knowledge'");
        Category(arrayListTwo);
    }
    @FXML
    private void HandleCategoryThree()
    {
        ArrayList<String> arrayListThree = new ArrayList();
        DB.selectSQL("SELECT fldVideoTitle FROM tblVideo WHERE fldCategory = 'News'");
        Category(arrayListThree);
    }
@FXML
    private void HandleListofVideos(){
        ArrayList<String> arrayListAllVideos = new ArrayList();
        DB.selectSQL("SELECT fldVideoTitle FROM tblVideo");
        Category(arrayListAllVideos);

        ObservableList<String> Listview = FXCollections.observableArrayList(arrayListAllVideos);
        searchlist.getItems().addAll(Listview);



    }


    private void Category(ArrayList<String> arrayList) {
        do {
            String data = DB.getData();
            if (data.equals(DB.NOMOREDATA)) {
                break;
            } else {
                // ADDS EACH ELEMENT TO THE ARRAY LIST
                arrayList.add(data);
                System.out.print(data);
            }
        } while (true);
        searchlist.getItems().clear();
        for (int i = 0; i < arrayList.size(); i++) {
            searchlist.getItems().add(arrayList.get(i));
        }
        System.out.println(searchlist);
    }





    }









