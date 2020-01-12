package sample.playlist;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ListView;
import sample.DB;
import sample.Controller;

import java.util.ArrayList;
import sample.video.video;
/**
 *
 */

public class Playlists {
    protected String PlaylistName;
    protected int NumbersofVideos;
    protected ArrayList<video> VideosFound = new ArrayList<video>();




    protected ObservableList<video> getVideos() {
        ObservableList<video> videos = FXCollections.observableArrayList();

        for (int i = 0; i < VideosFound.size(); i++) {
            videos.add(VideosFound.get(i));
        }
        return videos;

    }
}
