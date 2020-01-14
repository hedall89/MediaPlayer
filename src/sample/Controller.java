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
import javafx.event.ActionEvent;
import java.awt.event.MouseEvent;
import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.ResourceBundle;



public class Controller implements Initializable
{
    //Media
    @FXML private MediaView mediaV;
    @FXML private MediaPlayer mp;
    @FXML private Media me;
    //TextFields
    @FXML private TextField searchfield;
    //ListView
    @FXML private ListView<String> searchlist;
    @FXML private ListView viwer;
    //Buttons
    @FXML private Button play;
    @FXML private Button pause;
    @FXML private Button stop;
    @FXML private Button speed;
    //Labels
    @FXML private Label currenttime;
    @FXML private Label totaltime;
    //Sliders
    @FXML private Slider time;
    @FXML private Slider volumeSlider;


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
        HandleListofVideos();
        HandleSearch();

    }
    @FXML private void handlePlay() { mp.play(); }
    @FXML private void handleStop() { mp.stop(); }
    @FXML private void handlePause() { mp.pause(); }
    /**
     * Method to handle the search field in the GUI
     * @param
     */
    //TODO CLEAN UP METHODS
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
    //TODO MAKE CONTROLVOLUME WORK WITH CURRENT MEDIA
    private void ControlVolume(){
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

    //TODO INSERT SEARCH FUNCTIONALITIES METHODS INTO VIDEO/MANAGEVIDEOS.JAVA CLASS*S
    @FXML
    public void HandleSearch() {

        String searchresult;
        int searchResultsFound;
        ArrayList<String> arrayList = new ArrayList();
        searchresult = searchfield.getText();
        DB.selectSQL("SELECT COUNT(fldVideoTitle) from tblVideo WHERE (CHARINDEX('" + searchresult + "', fldVideoTitle) > 0 or CHARINDEX('" + searchresult + "', fldCategory) > 0 )");

        searchResultsFound = Integer.parseInt(DB.getData());
        System.out.println(searchResultsFound);

        if (searchResultsFound > 0) {

            DB.selectSQL("SELECT fldVideoTitle FROM tblVideo WHERE (CHARINDEX('" + searchresult + "', fldVideoTitle) > 0 or CHARINDEX('" + searchresult + "', fldCategory) > 0)");

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

        }
        System.out.println(searchlist);
        searchlist.getItems().clear();

        for (String s : arrayList) {
            searchlist.getItems().add(s);
        }
        System.out.println(searchresult);
    }
    @FXML
    private void HandleCategoryOne()
    {
        ArrayList<String> arrayListOne = new ArrayList();
        DB.selectSQL("SELECT fldVideoTitle FROM tblVideo WHERE fldCategory = 'Entertainment'");
        GetFromDB(arrayListOne);
    }
    @FXML
    private void HandleCategoryTwo()
    {
        ArrayList<String> arrayListTwo = new ArrayList();
        DB.selectSQL("SELECT fldVideoTitle FROM tblVideo WHERE fldCategory = 'Knowledge'");
        GetFromDB(arrayListTwo);
    }
    @FXML
    private void HandleCategoryThree()
    {
        ArrayList<String> arrayListThree = new ArrayList();
        DB.selectSQL("SELECT fldVideoTitle FROM tblVideo WHERE fldCategory = 'News'");
        GetFromDB(arrayListThree);
    }

        @FXML
        private void HandleListofVideos() {
        ArrayList<String> arrayListAllVideos = new ArrayList();
        DB.selectSQL("SELECT fldVideoTitle FROM tblVideo");
        GetFromDB(arrayListAllVideos);

        ObservableList<String> Listview = FXCollections.observableArrayList(arrayListAllVideos);
        searchlist.getItems().addAll(Listview);
        }

        private void GetFromDB(ArrayList<String> arrayList) {
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
            for (String s : arrayList) {
                searchlist.getItems().add(s);
            }
        System.out.println(searchlist);
    }
    //TODO ATTACH _FILEPATH TO MP.PLAY() TO PLAY CURRENT VIDEO.
    // CONSTRUCT PLAYLIST METHODS.
    // CREATE PLAYLIST
    // EDIT/UPDATE PLAYLIST - ATTACH TO GUI.
    public void HandleSelection() {
        String SelectedSong = searchlist.getSelectionModel().getSelectedItem();
        String _FILEPATH;
        if(SelectedSong==null || SelectedSong.isEmpty()){
            System.out.println("Nothing Selected");
        }else{
            System.out.println("You have selected: " + SelectedSong);
        }
        DB.selectSQL("SELECT fldVideoFilePath FROM tblVideo WHERE (fldVideoTitle = '"+ SelectedSong +"')");
        _FILEPATH = DB.getData();
        System.out.println(_FILEPATH);
    }
}






