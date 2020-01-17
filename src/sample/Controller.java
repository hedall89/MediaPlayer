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
import javafx.scene.input.MouseEvent;
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
    @FXML private TextField txtFldEnterNewPlaylistName; // JC
    //ListView
    @FXML private ListView<String> searchlist;
    @FXML private ListView viwer;
    @FXML private ListView<String> ListViewPlaylistOne; // JC
    @FXML private ListView<String> ListViewPlaylistTwo; // JC
    @FXML private ListView<String> ListViewPlaylistThree; // JC
    //Buttons
    @FXML private Button play;
    @FXML private Button pause;
    @FXML private Button stop;
    @FXML private Button speed;
    @FXML private Button addPlaylist; //JC
    //Labels
    @FXML private Label currenttime;
    @FXML private Label totaltime;
    //Sliders
    @FXML private Slider time;
    @FXML private Slider volumeSlider;
    //TitledPanes
    @FXML private TitledPane titledPaneOne; // JC
    @FXML private TitledPane titledPaneTwo; // JC
    @FXML private TitledPane titledPaneThree; // JC
    private TitledPane currentPane;
    //Booleans
    private boolean hasFirstClicked; // JC
    //String
    String newPlaylistName; // JC

    public Controller(Button play, Button pause, Button stop, Button speed, Button addPlaylist) {
        this.play = play;
        this.pause = pause;
        this.stop = stop;
        this.speed = speed;
        this.addPlaylist = addPlaylist;
    }


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
        //showVideos(); //JC TODO: discuss which one to choose, I cant get "HandleListofVideos()" to work

        hasFirstClicked = false; // JC
        showStoredPlaylistsOnTitledPane(); // JC
    }

    /**
     * Method to handle the play Button.
     */
    @FXML private void handlePlay() { mp.play(); }

    /**
     * Method to handle the Stop button.
     */
    @FXML private void handleStop() { mp.stop(); }

    /**
     * Method to handle the Pause button.
     */
    @FXML private void handlePause() { mp.pause(); }


  // To be removed ??
    /*public void fast (ActionEvent event)
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
    }*/


    //TODO MAKE CONTROLVOLUME WORK WITH CURRENT MEDIA

    /**
     * Method to control volume, with a volume slider.
     */
    @FXML
    private void ControlVolume(){
        DoubleProperty width = mediaV.fitWidthProperty();
        DoubleProperty height = mediaV.fitHeightProperty();
        width.bind(Bindings.selectDouble(mediaV.sceneProperty(), "width"));
        height.bind(Bindings.selectDouble(mediaV.sceneProperty(), "height"));
        volumeSlider.setValue(mp.getVolume()*100);
        volumeSlider.valueProperty().addListener(new InvalidationListener() {
            /**
             * Method for setting the volume
             * @param observable
             */
            @Override
            public void invalidated(Observable observable)
            {
                mp.setVolume(volumeSlider.getValue()/100);
            }
        });
    }

    //TODO INSERT SEARCH FUNCTIONALITIES METHODS INTO VIDEO/MANAGEVIDEOS.JAVA CLASS*S

    /**
     * Method for handling the searchfield in the GUI.
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
     *Method for selecting a specific Category.
     */
    @FXML
    private void HandleCategoryOne()
    {
        ArrayList<String> arrayListOne = new ArrayList();
        DB.selectSQL("SELECT fldVideoTitle FROM tblVideo WHERE fldCategory = 'Travel'");
        GetFromDB(arrayListOne);
    }

    /**
     * Method for selecting a specific Category.
     */
    @FXML
    private void HandleCategoryTwo()
    {
        ArrayList<String> arrayListTwo = new ArrayList();
        DB.selectSQL("SELECT fldVideoTitle FROM tblVideo WHERE fldCategory = 'Sport'");
        GetFromDB(arrayListTwo);
    }

    /**
     * Method for selecting a specific Category.
     */
    @FXML
    private void HandleCategoryThree()
    {
        ArrayList<String> arrayListThree = new ArrayList();
        DB.selectSQL("SELECT fldVideoTitle FROM tblVideo WHERE fldCategory = 'Animals'");
        GetFromDB(arrayListThree);
    }

        @FXML
        /**
        * This method will show all videos in a listView, once the program opens.
        * Henrik
         */
        private void HandleListofVideos() {
        ArrayList<String> arrayListAllVideos = new ArrayList();
        DB.selectSQL("SELECT fldVideoTitle FROM tblVideo");
        GetFromDB(arrayListAllVideos);

        ObservableList<String> Listview = FXCollections.observableArrayList(arrayListAllVideos);
        searchlist.getItems().addAll(Listview);
        }

//    @FXML
//    /**
//     * This method will show all videos in a listView, once the program opens.
//     * JC
//     */
//    private void showVideos() {
//
//        DB.selectSQL("SELECT fldVideoTitle FROM tblVideo");
//        ObservableList<String> videoTitles = FXCollections.observableArrayList();
//
//        do {
//            String totalVideos = DB.getData();
//            if (totalVideos.equals(DB.NOMOREDATA)) {
//                break;
//            } else {
//                videoTitles.add(totalVideos);
//                searchlist.setItems(videoTitles);
//            }
//        } while (true);
//    }

    @FXML
    /**
     * This method checks in the DB, if there are any playlists stored already and
     * shows them in the titledPanes once the program starts.
     *  JC
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

    /**
     * This method retrieves information from the database, and stores it in an ArrayList.
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

    // CREATE PLAYLIST:
    @FXML
    /**
     * This method checks if the text field for creating a playlist is clicked already, if not it will call the method "setTextFieldEditable" to do so.
     * Otherwise it will call the method "handleEnterNewPlaylist".
     * JC
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
     * JC
    */
    private void setTextFieldEditable() {
        hasFirstClicked = true;
        txtFldEnterNewPlaylistName.setEditable(true);
        txtFldEnterNewPlaylistName.requestFocus(); // sets the cursor in the textfield
    }

    @FXML
    /*
    Method takes the input from the user, stores it in a variable and with this information it calls the method to store the new playlist.
    Furthermore it will change the title of the TitledPane to the name of the entered playlist.
    JC
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
        } else if (titleOfPaneThree.equals( "untitled")) {
            titledPaneThree.setText(newPlaylistName);
            handleStorePlaylist(newPlaylistName);
        } else {
            System.out.println("Sorry, there are no spare playlists left, please delete one before creating a new.");
            // TODO ? use a pop up window for this error message?
        }
    }

    /**
     * Method will store the new playlist in the database in the table tblPlaylist
     * @param newPlaylistName = the name of the new created playlist
     * JC
     */
    private void handleStorePlaylist(String newPlaylistName) {
        // SQL statement stores a new playlist with 'newPlaylistName' in the DB
        DB.insertSQL("INSERT INTO tblPlaylist VALUES (' " + newPlaylistName + " '); ");

        // TODO add a try catch? to inform the user in case of duplicate lists.
        //  --if primary key violation message from DB -sout("This Playlist already exists, please choose another title.");
        //  maybe as pop up window?
    }

    //PLAYLIST METHODS:
    @FXML
    /**
     * This method will fetch the text/PlaylistID of the TitledPane, which got clicked.
     * @author JC
     */
    private void handleMousePressedGetTitledPaneID(MouseEvent event) {

        currentPane = (TitledPane) event.getSource();
        System.out.println("test1 " + currentPane.getText());
    }

    @FXML
    /**
     * This method will be invoked if titledPaneOne gets clicked.
     * It will ask the DB for the content of the chosen playlist and show it in the ListView below.
     * JC
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
    }

    // EDIT/UPDATE PLAYLIST - ATTACH TO GUI:

    // TODO Add method addVideoToPlaylist() in progress - JC

    public void addVideoToPlaylist() {

    }

    // TODO add method delete VideoFromPlaylist()

    // TODO add method deletePlaylist()

    /**
     * Method for selecting a specific video from the listView, and adding it to the mediaview.
     */
    public void HandleSelection() {

        String SelectedSong = searchlist.getSelectionModel().getSelectedItem();
        String _FILEPATH;
        if (SelectedSong == null || SelectedSong.isEmpty()) {
            System.out.println("Nothing Selected");
        } else {
            System.out.println("You have selected: " + SelectedSong);
        }
        DB.selectSQL("SELECT fldVideoFilePath FROM tblVideo WHERE (fldVideoTitle = '" + SelectedSong + "')");
        _FILEPATH = new File(DB.getData()).getAbsolutePath();

        me = new Media(new File(_FILEPATH).toURI().toString());
        mp = new MediaPlayer(me);
        mediaV.setMediaPlayer(mp);
        mp.setAutoPlay(false);
        System.out.println(_FILEPATH);
    }
}






