package sample;
<<<<<<< HEAD

import javafx.beans.binding.Bindings;
import javafx.beans.property.DoubleProperty;
=======
>>>>>>> origin/master
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.*;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
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
import java.util.List;
import java.util.ResourceBundle;

public class Controller implements Initializable
{
    /*
    public static ObservableList<String> Display_Playlists;
    
    private List<Playlist> Playlists;
    */
     
    private List<video> Videos;
    private boolean hasFirstClicked; // JC
    
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
    private ListView<String> ListViewPlaylistOne; // JC
    @FXML
    private ListView<String> ListViewPlaylistTwo; // JC
    @FXML
    private ListView<String> ListViewPlaylistThree; // JC
    @FXML
    private Button button1;
    @FXML
    private Button addPlaylist; // can be deleted?
    @FXML
    private TextField txtFldEnterNewPlaylistName; // JC
    @FXML
    private TitledPane titledPaneOne; // JC
    @FXML
    private TitledPane titledPaneTwo; // JC
    @FXML
    private TitledPane titledPaneThree; // JC

    private TitledPane currentPane;


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
        String path = new File("src/sample/media/BONAPARTE_COMPUTER_IN_LOVE.mp4").getAbsolutePath();

        // Create new Media object (the actual media content)
        me = new Media(new File(path).toURI().toString());
        // Create new MediaPlayer and attach the media to be played
        mp = new MediaPlayer(me);
        //
        mediaV.setMediaPlayer(mp);
        // mp.setAutoPlay(true);
        // If autoplay is turned of the method play(), stop(), pause() etc controls how/when medias are played
        mp.setAutoPlay(false);
        hasFirstClicked = false;

       //  the following will get the mediaview to resize according to window
//        DoubleProperty width = mediaV.fitWidthProperty();
//        DoubleProperty height = mediaV.fitHeightProperty();
//        width.bind(Bindings.selectDouble(mediaV.sceneProperty(), "width"));
//        height.bind(Bindings.selectDouble(mediaV.sceneProperty(), "height"));

        showVideos(); // JC
        showStoredPlaylistsOnTitledPane(); // JC
    }



    /**
     * This method will show all videos in a listView, once the program opens.
     * @author: JC
     */
    @FXML
    private void showVideos() { // Try B is working

//Try B
        DB.selectSQL("SELECT fldVideoTitle FROM tblVideo");
        ObservableList<String> videoTitles = FXCollections.observableArrayList();

        do {
            String totalVideos = DB.getData();
            if (totalVideos.equals(DB.NOMOREDATA)) {
                break;
            } else {
                videoTitles.add(totalVideos);
                searchlist.setItems(videoTitles);
            }
        } while (true);

// Try A // is working for one video
        // find out how many videos there are stored = works
        //int totalVideosFound = 0;
        //DB.selectSQL("SELECT COUNT (*) FROM tblVideo");
        //totalVideosFound = Integer.parseInt(DB.getData());

        // create a new ArrayList
        //ArrayList<String> videoTitles = new ArrayList<String>();

        // loop through length of list of all videos = works "=15 x aaa"
        // and add each video to the ArrayList
        //for (int i = 0; i < totalVideosFound; i++) {
//            DB.selectSQL("SELECT fldVideoTitle FROM tblVideo");
//
//            videoTitles.add(DB.getData());  // stores resultset in variable
//
//        }
//        System.out.println(videoTitles);

        //ObservableList<String> obsVideoTitles = FXCollections.observableArrayList(videoTitles);
        //searchlist.setItems(obsVideoTitles);
    }

    @FXML
    /**
     * This method checks in the DB, if there are any playlists stored already and
     * shows them in the titledPanes once the program starts.
     * @author: JC
     */
    private void showStoredPlaylistsOnTitledPane() {
        // get Array of tblPlaylist content
        DB.selectSQL("SELECT fldPlaylistID FROM tblPlaylist");
        ArrayList<String> playlistNames = new ArrayList();

        boolean noMoreData = false;
        int counter = 1;
        do {
            String titleQuery = DB.getData();

            if (titleQuery.equals(DB.NOMOREDATA)) {
            noMoreData = true;
        } else {
                switch (counter) {
                    case 1:
                        titledPaneOne.setText(titleQuery);
                        break;
                    case 2:
                        titledPaneTwo.setText(titleQuery);
                        break;
                    case 3:
                        titledPaneThree.setText(titleQuery);
                        break;
                }
                counter++;
            }
        } while (!noMoreData && counter != 4);
    }

    @FXML
    public void handlePlay() {
        mp.play();
        mp.setRate(1); // normal speed
    }
    @FXML
    private void handleStop()
=======
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
>>>>>>> origin/master
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


    String newPlaylistName;

    @FXML
    /**
     *
     * @author: JC
     */
    private void handleAddPlaylist() {

        if (!hasFirstClicked) {
            setTextFieldEditable();
        } else {
            handleEnterNewPlaylist();
        }

    }
    /*
    This method changes the TextField from being uneditable to being editable and sets the cursor in to the textfield so the user can give input.
    */
    private void setTextFieldEditable() {
        hasFirstClicked = true;
        txtFldEnterNewPlaylistName.setEditable(true);
        txtFldEnterNewPlaylistName.requestFocus(); // sets the cursor in the textfield
    }

    @FXML
<<<<<<< HEAD
    /*
    Method takes the input from the user, stores it in a variable and with this information it calls the method to store the new playlist.
    Furthermore it will change the title of the TitledPane to the name of the entered playlist.
     */
    private void handleEnterNewPlaylist() {

        //take user input
        newPlaylistName = txtFldEnterNewPlaylistName.getText();

        String titleOfPaneOne = titledPaneOne.getText();
        String titleOfPaneTwo = titledPaneTwo.getText();
        String titleOfPaneThree = titledPaneThree.getText();

        if (titleOfPaneOne.equals("untitled")) {
            // sets the name of the playlist on the first titled pane
            titledPaneOne.setText(newPlaylistName);
            //
            handleStorePlaylist(newPlaylistName);
        } else if (titleOfPaneTwo.equals("untitled")) {
            titledPaneTwo.setText(newPlaylistName);
            handleStorePlaylist(newPlaylistName);
            } else if (titleOfPaneThree.equals( "untitled")) {
            titledPaneThree.setText(newPlaylistName);
            handleStorePlaylist(newPlaylistName);
        } else {
            System.out.println("Sorry, there are no spare playlists left, please delete one before creating a new.");
        }
    }


    /**
     * Method will store the new playlist in the database in the table tblPlaylist
     * @param newPlaylistName = the name of the new created playlist
     */
    private void handleStorePlaylist(String newPlaylistName) {

        // TODO add a try catch? to inform the user in case of duplicate lists.
        //  --if primary key violation message from DB -sout("This Playlist already exists, please choose another title.");
        //  maybe as pop up window?

        //  add SQL here to store a new playlist with 'newPlaylistName' in the DB
        DB.insertSQL("INSERT INTO tblPlaylist VALUES (' " + newPlaylistName + " '); ");

    }


    @FXML
    /**
     * This method will fetch the id of the titledpane, which got clicked and stores it as a string variable.
     * @author JC
     */
    private void handleMousePressedGetTitledPaneID(MouseEvent event) {
        //TRY A
        currentPane = (TitledPane) event.getSource();

////        titledPaneID = event.getPickResult().getIntersectedNode().getId(); //why does this not work?
////        System.out.println(source1); // shows details about the clicked field/node = works
////        System.out.println(titledPaneID);

        System.out.println("test1 " + currentPane.getText() );

//        String test = event.getTarget().toString(); // fetches the information of what kind of target got clicked
//        String test2 = event.getTarget().getId() // ???
//
//        System.out.println("should print the target" + test);
//
//        // TRY B -not beautiful, but it works to get the ID as a string.
//        String source1 = event.getSource().toString();
//        int idStart = source1.indexOf("=");
//        int idEnd = source1.indexOf(",");
//
//        titledPaneID = source1.substring(idStart + 1, idEnd);
//        System.out.println("test" + titledPaneID);
    }

    @FXML
    /**
     * This method will be invoked if titledPaneOne gets clicked.
     * It will ask the DB for the content of the chosen playlist and show it in the ListView below.
     */
    private void handleMouseClickShowPlaylist() {
        // which titled pane got clicked?

        String titledPaneID = currentPane.getId(); // get the title of the TitledPane, which at the same time is the PlaylistID

        ListView<String> ListViewToUse = new ListView<String>();
        if (titledPaneID.equals("titledPaneOne")) {
            ListViewToUse = ListViewPlaylistOne;
        } else if (titledPaneID.equals("titledPaneTwo")) {
            ListViewToUse = ListViewPlaylistTwo;
        } else if (titledPaneID.equals("titledPaneThree")) {
            ListViewToUse = ListViewPlaylistThree;
        }
//

        DB.selectSQL("SELECT fldVideoTitle " +
                "FROM tblVideo " +
                "WHERE fldVideoID IN " +
                "(" +
                "SELECT fldVideoID " +
                "FROM tblMediaPlayer " +
                "WHERE fldPlaylistID = '" + currentPane.getText() + "')"); //TODO solve errors: works in the sql server directly (see screenshot for comparison)
                                                            // Invalid column name 'are'.

        ObservableList<String> videoTitlesOfPlaylist = FXCollections.observableArrayList();

        do {
            String totalPlaylist = DB.getData();

            if (totalPlaylist.equals(DB.NOMOREDATA)) {
                break;
            } else {
                videoTitlesOfPlaylist.add(totalPlaylist);
                ListViewToUse.setItems(videoTitlesOfPlaylist); // hardcoded with titledPaneOne.setItems(videoTitlesOfPlaylist); works
            }
        } while (true);
    }

    public void HandleSearch()
=======
    private void HandleCategoryTwo()
>>>>>>> origin/master
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
<<<<<<< HEAD

    String foundVideoTitle = "";
    String queryVideoID = "";
    @FXML
    /**
     *
     * JC
     */
    private void addVideoToPlaylist() { // TODO fix this
        // how to:
        // 1 on Mouse pressed/dragged(ListView searchlist): select the VideoTitle
        // 2 find according videoID in DB
        // 3 store query1 result in variable
        // 4 on Mouse Released/dropped(TitledPane): select the PlaylistID
        // 5 store query2 result in variable
        // 6 SQL insert query1, query2 in tbl Mediaplayer.
        // (INSERT INTO tblMediaplayer(fldPlaylistID, fldVideoID)
        // VALUES ('query2', query1);) tested sql statement



//        foundVideoTitle = get videoTitle on Drag detected handleOnDragDetectedGetVideoTitle
//        DB.selectSQL("SELECT fldVideoID FROM tblVideo WHERE fldVideoTitle = 'foundVideoTitle'");
//        String queryVideoID = DB.getData();
//
//         handleOnDroppedGetPlaylistID();
//
//        DB.insertSQL("INSERT INTO tblMediaPlayer (fldPlaylistID, fldVideoID) VALUES ('foundPlaylistID', 'queryVideoID'); // correction of syntax
    }

    @FXML
    /**
     * JC
     */
    private void handleOnDroppedGetPlaylistID() {
        //foundPlaylistID = get playlistID on Dropped handleOnDroppedGetPlaylistID
    }



=======
>>>>>>> origin/master
}






