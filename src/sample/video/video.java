package sample.video;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import sample.Controller;
import sample.DB;
import java.io.File;


/**
 * Class to select a specific VIDEO straigth from the GUI, and then fetched in the DATABASE
 * BY SQL STATEMENTS,
 */

public class video
{
   // private static int Vid_ID;
    private static String VideoCategory;
    private static String VideoTitle;


    private String filesource;
    private String filepath;

    private MediaPlayer mp;
    private Media me;


    public video(String filepath)
    {
        this.filepath = filepath;

        String path = new File("src/sample/media/" + filepath).getAbsolutePath();

        filesource = "src/sample/media/" + filepath;

        me = new Media(new File(path).toURI().toString());

        mp = new MediaPlayer(me);
    }

    public video() {

    }


    //GETTERS//

    public static String getVideoCategory() { return VideoCategory; }
    public static String getVideoTitle() { return VideoTitle; }
    public String getFilesource() { return filesource; }
    public String getFilepath() { return filepath; }
    public MediaPlayer getMp() { return mp; }
    public Media getMe() { return me; }

    //SETTERS//
    public static void setVideoCategory(String videoCategory) { VideoCategory = videoCategory; }
    public static void setVideoTitle(String videoTitle) { VideoTitle = videoTitle; }
    public void setFilesource(String filesource) { this.filesource = filesource; }
    public void setFilepath(String filepath) { this.filepath = filepath; }
    public void setMp(MediaPlayer mp) { this.mp = mp; }
    public void setMe(Media me) { this.me = me; }


    //DATABASE GETTERS //
    public String getVideotitleDB(){
        DB.selectSQL("SELECT fldVideoTitle from tblVideo WHERE fldVideoFilePath ='" + this.filepath+"'");
        VideoTitle = DB.getData();
        cleardata();
        return VideoTitle;

    }
    public String getVideoCategoryDB(){
        DB.selectSQL("SELECT fldVideoCategory from tblVideo WHERE fldVideoCategory ='" + this.filepath+"'");
        VideoCategory = DB.getData();
        cleardata();
        return VideoCategory;
    }

    /**
     * Method for clearing buffer from the database.
     */
    private static void cleardata() {


        do {
            String data = DB.getData();
            if (data.equals(DB.NOMOREDATA)) {
                break;
            }

        } while (true);
    }
}
