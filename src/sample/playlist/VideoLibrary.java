package sample.playlist;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.*;
import sample.DB;

public class VideoLibrary extends Playlists {
    public void searchForSongs(TextField textField, ListView<String> listView) {
        // TAKES THE TYPED IN KEYWORD IN THE SEARCHFIELD FROM GUI
        String SearchField = textField.getText();
        DB.selectSQL("SELECT COUNT(fldVideoTitle) from tblVideo WHERE (CHARINDEX('" + SearchField + "', fldVideoTitle) > 0 or CHARINDEX('" + SearchField + "', fldVideoCategory) > 0 )");
        int searchResultsFound = 0;

        // COUNTS THE AMOUNT OF VIDEOS IN TBLVIDEO
        searchResultsFound = Integer.parseInt(DB.getData());
       // System.out.println("There was " + searchResultsFound + " videos found");

        // CLEARS BUFFER
        cleardata();
        if (searchResultsFound > 0) {

            DB.selectSQL("SELECT fldVideoTitle FROM tblVideo WHERE (CHARINDEX('" + SearchField + "', fldVideoTitle) > 0 or CHARINDEX('" + SearchField + "', fldVideoCategory) > 0)");
            displaySelectedData();
        } else {
            System.out.println("Nothing Found, Check for Typos.");
            // maybe clear playlist here.
        }
        //listView.removeAll(listView);
        ObservableList<String> names = FXCollections.observableArrayList(
                "Julia", "Ian", "Sue", "Matthew", "Hannah", "Stephan", "Denise");
        listView.getItems().addAll(names);
        //display
       //setVideoListView(listView);
    }

    // CLEARS POTENTIAL DATA IN BUFFER
    private static void cleardata() {
        do {
            String data = DB.getData();
            if (data.equals(DB.NOMOREDATA)) {
                break;
            }

        } while (true);
    }
}
