package sample.playlist;
import sample.DB;
import sample.Controller;

import java.util.ArrayList;
import sample.video.video;
/**
 *
 */

public class Playlists {
    protected String PlaylistName;
    protected int AmountofVideos;
   // protected ArrayList<>


    protected void displaySelectedData() {


        do {
            String data = DB.getData();
            if (data.equals(DB.NOMOREDATA)) {
                System.out.println("NO MORE DATA");
                break;
            } else {

                System.out.println("Added this song to the Song ArrayList: " + data);

                // WILL GIVE US THE FILENAME AND PASS IT TO THE PARAMETERS IN THE SONG OBJECT
                video video = new video(data);


                // WILL ADD THE SONG INCLUDING ITS PROPERTIES TO THE ARRAY LIST
                // songsFoundArrayList.add(video);

            }

        } while (true);
    }
}
