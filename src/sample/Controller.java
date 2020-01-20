package sample;
import javafx.collections.*;
import javafx.fxml.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.media.*;
import javafx.scene.control.*;
import javafx.fxml.FXML;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.beans.binding.Bindings;
import javafx.beans.property.DoubleProperty;
import sample.playlist.*;
import java.io.*;
import java.net.*;
import java.util.*;

/**
 * A Media Player Program that can
 * play/pause/stop a Selected Video, based on the search results, create a playlist
 * of selected videos, furthermore the user is able
 * to EDIT the user created playlists.
 * Additionally //Play a Playlist
 *
 *
 * @author Casper, Henrik, Iza and Jette - D19
 */



public class Controller implements Initializable {

    //Media
    @FXML
    private MediaView mediaV;
    @FXML
    private MediaPlayer mp;
    @FXML
    private Media me;
    //TextFields
    @FXML
    private TextField searchfield;
    @FXML
    private TextField txtFldEnterNewPlaylistName;
    //ListView
    @FXML
    private ListView<String> searchlist;
    @FXML
    private ListView<String> ListViewPlaylistOne;
    @FXML
    private ListView<String> ListViewPlaylistTwo;
    @FXML
    private ListView<String> ListViewPlaylistThree;
    //Buttons
    @FXML
    private Button addPlaylist;
    @FXML
    private Button pauseBtn;
    @FXML
    private Button playBtn;
    @FXML
    private Button stopBtn;
    //Sliders
    @FXML
    private Slider time;
    @FXML
    private Slider volumeSlider;
    //TitledPanes
    @FXML
    private TitledPane titledPaneOne;
    @FXML
    private TitledPane titledPaneTwo;
    @FXML
    private TitledPane titledPaneThree;
    private TitledPane currentPane;
    //MenuButtons
    @FXML
    private MenuButton menuButton;
    //MenuItems
    @FXML
    private MenuItem menuItem1;
    @FXML
    private MenuItem menuItem2;
    @FXML
    private MenuItem menuItem3;


    // NON FXML
    //Booleans
    private boolean hasFirstClicked;
    //Strings
    String newPlaylistName;
    String _FILEPATH;
    String selectedVideoID;
    String useThisVideoID;
    short shortSelectedVideoID;

    private String PlaylistOneVid;
    private String PlaylistTwoVid;
    private String PlaylistThreeVid;
    List<String> players = new ArrayList<>();
    /////////////////////////////////////////////////////

    /**
     * This method is invoked automatically in the beginning. Used for initializing, loading data etc.
     *
     * @param location
     * @param resources
     */
    @FXML
    public void initialize(URL location, ResourceBundle resources) {

        // Build the path to the location of the media file
        String path = new File("src/sample/media/file_example_MP4_640_3MG.mp4").getAbsolutePath();
        // Create new Media object (the actual media content)
        me = new Media(new File(path).toURI().toString());
        // Create new MediaPlayer and attach the media to be played
        mp = new MediaPlayer(me);
        //
        mediaV.setMediaPlayer(mp);
        mp.setAutoPlay(false);
        HandleListofVideos();

        hasFirstClicked = false;

        showStoredPlaylistsOnTitledPane();
    }

    /**
     * Method to handle the play Button.
     */
    @FXML
    private void handlePlay() {
        mp.play();

    }

    /**
     * Method to handle the Stop button.
     */
    @FXML
    private void handleStop() {
        mp.stop();
    }

    /**
     * Method to handle the Pause button.
     */
    @FXML
    private void handlePause() {
        mp.pause();
    }

    /**
     * Method to handle the duration slider of a video
     */
    @FXML
    private void handleDuration() {
        // DURATION MANAGEMENT
        // Will listen to the slider and update it continuously
        mp.currentTimeProperty().addListener(new InvalidationListener() {
            @Override
            public void invalidated(Observable observable) {
                //
            }
        });
        // Inorder to jump to the certain part of the media
        // Will change the values if the user moves the slider to a new position
        time.valueProperty().addListener(new InvalidationListener() {
            public void invalidated(Observable ov) {
                if (time.isPressed()) { // It would set the time


                    // as specified by user by pressing
                    mp.seek(mp.getMedia().getDuration().multiply(time.getValue() / 100));
                }
            }
        });
    }
    /**
     * Method to control volume, with a volume slider.
     */
    @FXML
    private void ControlVolume() {
        DoubleProperty width = mediaV.fitWidthProperty();
        DoubleProperty height = mediaV.fitHeightProperty();
        width.bind(Bindings.selectDouble(mediaV.sceneProperty(), "width"));
        height.bind(Bindings.selectDouble(mediaV.sceneProperty(), "height"));
        volumeSlider.setValue(mp.getVolume() * 100);
        volumeSlider.valueProperty().addListener(new InvalidationListener() {
            /**
             * Method for setting the volume
             * @param observable
             */
            @Override
            public void invalidated(Observable observable) {
                mp.setVolume(volumeSlider.getValue() / 100);
            }
        });
    }
    /**
     * Method for handling the searchfield in the GUI.
     * Takes input from the TextField and searches the DATABASE
     * based on the inserted CHAR
     */
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

    /**
     * Method for selecting a specific Category.
     */
    @FXML
    private void HandleCategoryOne() {
        ArrayList<String> arrayListOne = new ArrayList();
        DB.selectSQL("SELECT fldVideoTitle FROM tblVideo WHERE fldCategory = 'Travel'");
        GetFromDB(arrayListOne);
    }

    /**
     * Method for selecting a specific Category.
     */
    @FXML
    private void HandleCategoryTwo() {
        ArrayList<String> arrayListTwo = new ArrayList();
        DB.selectSQL("SELECT fldVideoTitle FROM tblVideo WHERE fldCategory = 'Sport'");
        GetFromDB(arrayListTwo);
    }

    /**
     * Method for selecting a specific Category.
     */
    @FXML
    private void HandleCategoryThree() {
        ArrayList<String> arrayListThree = new ArrayList();
        DB.selectSQL("SELECT fldVideoTitle FROM tblVideo WHERE fldCategory = 'Animals'");
        GetFromDB(arrayListThree);
    }

    @FXML
    /**
     * This method will show all videos in a listView, once the program opens.
     *
     */
    private void HandleListofVideos() {
        ArrayList<String> arrayListAllVideos = new ArrayList();
        DB.selectSQL("SELECT fldVideoTitle FROM tblVideo");
        GetFromDB(arrayListAllVideos);

        ObservableList<String> Listview = FXCollections.observableArrayList(arrayListAllVideos);
        searchlist.getItems().addAll(Listview);
    }

    @FXML
    /**
     * This method checks in the DB, if there are any playlists stored already and
     * shows them in the titledPanes once the program starts.
     *
     */
    public void showStoredPlaylistsOnTitledPane() {
        // get Array of tblPlaylist content
        DB.selectSQL("SELECT fldPlaylistID FROM tblPlaylist");
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
                        menuItem1.setText(titleQuery);
                        break;
                    case 2:
                        titledPaneTwo.setText(titleQuery);
                        menuItem2.setText(titleQuery);
                        break;
                    case 3:
                        titledPaneThree.setText(titleQuery);
                        menuItem3.setText(titleQuery);
                        break;
                }
                counter++;
            }
        } while (!noMoreData && counter < 4);

    }

    /**
     * This method retrieves information from the database, and stores it in an ArrayList.
     *
     * @param arrayList
     */
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

    @FXML
    /**
     * This method checks if the text field for creating a playlist is clicked already, if not it will call the method "setTextFieldEditable" to do so.
     * Otherwise it will call the method "handleEnterNewPlaylist".
     *
     */
    private void handleAddPlaylist() {

        if (!hasFirstClicked) {
            setTextFieldEditable();
        } else {
            handleEnterNewPlaylist();
        }
    }

    /**
     * This method changes the TextField from being uneditable to being editable and sets the cursor in to the textfield so the user can give input.
     */
    private void setTextFieldEditable() {
        hasFirstClicked = true;
        txtFldEnterNewPlaylistName.setEditable(true);
        txtFldEnterNewPlaylistName.requestFocus(); // sets the cursor in the textfield
    }

    @FXML
    /**
     Method takes the input from the user, stores it in a variable and with this information it calls the method to store the new playlist.
     Furthermore it will change the title of the TitledPane to the name of the entered playlist.
     */
    private void handleEnterNewPlaylist() {

        newPlaylistName = txtFldEnterNewPlaylistName.getText(); //take user input

        String titleOfPaneOne = titledPaneOne.getText();
        String titleOfPaneTwo = titledPaneTwo.getText();
        String titleOfPaneThree = titledPaneThree.getText();

        if (titleOfPaneOne.equals("untitled")) {
            // sets the name of the playlist on the first titled pane
            titledPaneOne.setText(newPlaylistName);
            handleStorePlaylist(newPlaylistName);
        } else if (titleOfPaneTwo.equals("untitled")) {
            titledPaneTwo.setText(newPlaylistName);
            handleStorePlaylist(newPlaylistName);
        } else if (titleOfPaneThree.equals("untitled")) {
            titledPaneThree.setText(newPlaylistName);
            handleStorePlaylist(newPlaylistName);
        } else {
            System.out.println("Sorry, there are no spare playlists left, please delete one before creating a new.");
        }
    }

    /**
     * Method will store the new playlist in the database in the table tblPlaylist
     *
     * @param newPlaylistName = the name of the new created playlist
     */
    private void handleStorePlaylist(String newPlaylistName) {
        // SQL statement stores a new playlist with 'newPlaylistName' in the DB
        DB.insertSQL("INSERT INTO tblPlaylist VALUES ('" + newPlaylistName + "')");
    }

    //PLAYLIST METHODS:
    @FXML
    /**
     * This method will fetch the text/PlaylistID of the TitledPane, which got clicked.
     *
     */
    private void handleMousePressedGetTitledPaneID(MouseEvent event) {
        currentPane = (TitledPane) event.getSource();
        System.out.println("test1 " + currentPane.getText());
    }

    @FXML
    /**
     * This method will be invoked if titledPaneOne gets clicked.
     * It will ask the DB for the content of the chosen playlist and show it in the ListView below.
     *
     */
    private void handleMouseClickShowPlaylist() {
        // finds out which TitledPane got clicked, by getting the id
        String titledPaneID = currentPane.getId();
        ListView<String> ListViewToUse = new ListView<String>();
        if (titledPaneID.equals("titledPaneOne")) {
            ListViewToUse = ListViewPlaylistOne;
        } else if (titledPaneID.equals("titledPaneTwo")) {
            ListViewToUse = ListViewPlaylistTwo;
        } else if (titledPaneID.equals("titledPaneThree")) {
            ListViewToUse = ListViewPlaylistThree;
        }
        DB.selectSQL("SELECT fldVideoTitle " +
                "FROM tblVideo " +
                "WHERE fldVideoID IN " +
                "(" +
                "SELECT fldVideoID " +
                "FROM tblMediaPlayer " +
                "WHERE fldPlaylistID = '" + currentPane.getText() + "')");

        ObservableList<String> videoTitlesOfPlaylist = FXCollections.observableArrayList();
        do {
            String totalPlaylist = DB.getData();
            if (totalPlaylist.equals(DB.NOMOREDATA)) {
                break;
            } else {
                videoTitlesOfPlaylist.add(totalPlaylist);
                ListViewToUse.setItems(videoTitlesOfPlaylist);
            }
        } while (true);
        System.out.println("----------------------");
        System.out.println("ListView to use: " + ListViewToUse);
        System.out.println("playlist contains: " + videoTitlesOfPlaylist);


    }

    /**
     * Method for selecting a specific video from the listView, and adding it to the mediaview.
     */
    public void HandleSelection() {

        String SelectedSong = searchlist.getSelectionModel().getSelectedItem();

        if (SelectedSong == null || SelectedSong.isEmpty()) {
            System.out.println("Nothing Selected");
        } else {
            System.out.println("You have selected: " + SelectedSong);
        }
        DB.selectSQL("SELECT fldVideoFilePath FROM tblVideo WHERE (fldVideoTitle = '" + SelectedSong + "')");
        _FILEPATH = new File(DB.getData()).getPath();

        //adds the chosen video to the media view
        me = new Media(new File(_FILEPATH).toURI().toString());
        mp = new MediaPlayer(me);
        mediaV.setMediaPlayer(mp);
        mp.setAutoPlay(false);
        System.out.println(_FILEPATH);
    }

    @FXML
    /**
     * This method takes the filepath of the clicked on video, and fetches the corresponding VideoID from the database.
     * It stores the result in a string variable.
     *
     */
    public void handleChooseVideoToAddToPlaylist() {

        DB.selectSQL("SELECT fldVideoID FROM tblVideo WHERE fldVideoFilepath = '" + _FILEPATH + "'");

        do {
            selectedVideoID = DB.getData();
            if (selectedVideoID.equals(DB.NOMOREDATA)) {
                break;
            } else {
                useThisVideoID = selectedVideoID;
            }
        } while (true);

        shortSelectedVideoID = Short.valueOf(useThisVideoID); //changes selectedVideoID from string to short (because of data type smallint in DB)
        System.out.println("test - fetched videoID: " + useThisVideoID);

    }

    @FXML
    /**
     * Method that inserts a selected video from the listview to the database based
     * on the selected playlist in the menu
     */
    public void handleGetPlaylistToStoreIn1() {
        String playlistOnMenuItem1 = menuItem1.getText();

        DB.insertSQL("INSERT INTO tblMediaplayer (fldPlaylistID, fldVideoID) " +
                "VALUES ('" + playlistOnMenuItem1 + "','" + shortSelectedVideoID + "')");
    }

    @FXML
    /**
     * Method gets the Text/playlist of the first menuItem and stores it in a variable.
     * Afterwards it inserts the playlist and selected video in the tblMediaplayer in the DB.
     *
     */
    public void handleGetPlaylistToStoreIn2() {
        String playlistOnMenuItem2 = menuItem2.getText();

        DB.insertSQL("INSERT INTO tblMediaplayer (fldPlaylistID, fldVideoID) " +
                "VALUES ('" + playlistOnMenuItem2 + "','" + shortSelectedVideoID + "')");
    }

    @FXML
    /**
     * Method that inserts a selected video from the listview to the database based
     * on the selected playlist in the menu
     */
    public void handleGetPlaylistToStoreIn3() {
        String playlistOnMenuItem3 = menuItem3.getText();

        DB.insertSQL("INSERT INTO tblMediaplayer (fldPlaylistID, fldVideoID) " +
                "VALUES ('" + playlistOnMenuItem3 + "','" + shortSelectedVideoID + "')");
    }

    /**
     * Methods that takes in the selected Video or Playlist from the GUI
     * And deletes them in the Database when Method is called by the "Delete" Button
     */
    @FXML
    private void DeleteSelectedVID() {

        ManagePlaylists MP = new ManagePlaylists();

        PlaylistOneVid = ListViewPlaylistOne.getSelectionModel().getSelectedItem();
        PlaylistTwoVid = ListViewPlaylistTwo.getSelectionModel().getSelectedItem();
        PlaylistThreeVid = ListViewPlaylistThree.getSelectionModel().getSelectedItem();
        MP.set_PLAYLIST(currentPane.getText());
        String titledPaneID = currentPane.getId();

        if (titledPaneID.equals("titledPaneOne")) {
            MP.set_PLAYLISTVID(PlaylistOneVid);
            PlaylistTwoVid = null;
            PlaylistThreeVid = null;
            PlaylistOneVid = null;
        } else if (titledPaneID.equals("titledPaneTwo")) {
            MP.set_PLAYLISTVID(PlaylistTwoVid);
            PlaylistTwoVid = null;
            PlaylistThreeVid = null;
            PlaylistOneVid = null;
        } else if (titledPaneID.equals("titledPaneThree")) {
            MP.set_PLAYLISTVID(PlaylistThreeVid);
            PlaylistTwoVid = null;
            PlaylistThreeVid = null;
            PlaylistOneVid = null;
        }
        System.out.println("The Selected Video: " + MP.get_PLAYLISTVID() + " has been deleted from Playlist: " + MP.get_PLAYLIST());
        DB.deleteSQL("DELETE FROM tblMediaPlayer WHERE fldVideoID = ANY(SELECT fldVideoID FROM tblVideo WHERE fldVideoTitle= '" + MP.get_PLAYLISTVID() + "') AND fldPlaylistID = '" + MP.get_PLAYLIST() + "'");

        showStoredPlaylistsOnTitledPane();
        handleMouseClickShowPlaylist();
    }

    /**
     * Method to DELETE a SELECTED Playlist from TitledPanes
     */
    @FXML
    private void DeleteSelectedPL() {

        ManagePlaylists MP = new ManagePlaylists();
        MP.set_PLAYLISTID(currentPane.getText());
        System.out.println("--------");
        System.out.println(MP.get_PLAYLISTID());

        DB.deleteSQL("DELETE FROM tblPlaylist WHERE fldPlaylistID = '" + MP.get_PLAYLISTID() + "'");
        showStoredPlaylistsOnTitledPane();
    }


    /*
    Observablelist<String> videoTitlesOfPlaylist from method handleMouseClickShowPlaylist()
    contains the videos in that specific listview that is chosen on the GUI
    Tried to add each element to List<MediaPlayer> players but each element gets added again
    when you click a titledpane (playlist) on the GUI.
     */
    private void PlayEachSong() {
        List<MediaPlayer> players = new ArrayList<MediaPlayer>();
        for (int i = 0; i < players.size(); i++) {
            final MediaPlayer player = players.get(i);
            final MediaPlayer nextPlayer = players.get((i + 1) % players.size());
            player.setOnEndOfMedia(new Runnable() {
                @Override
                public void run() {
                    mediaV.setMediaPlayer(nextPlayer);
                    nextPlayer.play();
                }
            });
        }


    }

    @FXML
    private void PlayListSelection() {
        ArrayList<MediaPlayer> player = new ArrayList<>();

        String SelectedSsong = ListViewPlaylistTwo.getSelectionModel().getSelectedItem();
        if (SelectedSsong == null || SelectedSsong.isEmpty()) {
            System.out.println("Nothing Selected");
        } else {
            System.out.println("You have selected: " + SelectedSsong);
        }
        DB.selectSQL("SELECT fldVideoFilePath FROM tblVideo WHERE (fldVideotitle = '" + SelectedSsong + "')");
        _FILEPATH = new File(DB.getData()).getPath();

        //adds the chosen video to the media view
        me = new Media(new File(_FILEPATH).toURI().toString());
        mp = new MediaPlayer(me);
        mediaV.setMediaPlayer(mp);
        mp.setAutoPlay(false);
        System.out.println(_FILEPATH);
    }
}






