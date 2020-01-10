package sample.video;
/**
 * Class to select a specific VIDEO straigth from the GUI, and then fetched in the DATABASE
 * BY SQL STATEMENTS,
 */

import javafx.fxml.FXML;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import sample.DB;
import sample.Controller;
import java.io.File;


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
    //GETTERS AND SETTERS

    public static String getVideoCategory() { return VideoCategory; }

    public static void setVideoCategory(String videoCategory) { VideoCategory = videoCategory; }

    public static String getVideoTitle() { return VideoTitle; }

    public static void setVideoTitle(String videoTitle) { VideoTitle = videoTitle; }

    public String getFilesource() { return filesource; }

    public void setFilesource(String filesource) { this.filesource = filesource; }

    public String getFilepath() { return filepath; }

    public void setFilepath(String filepath) { this.filepath = filepath; }

    public MediaPlayer getMp() { return mp; }

    public void setMp(MediaPlayer mp) { this.mp = mp; }

    public Media getMe() { return me; }

    public void setMe(Media me) { this.me = me; }

    public String getVideotitleDB(){
        DB.selectSQL("SELECT fldVideoTitle from tblVideo WHERE fldVideoFilePath ='" + this.filepath+"'");
        VideoTitle = DB.getData();
        return VideoTitle;
    }
    public String getVideoCategoryDB(){
        DB.selectSQL("SELECT fldVideoCategory from tblVideo WHERE fldVideoCategory ='" + this.filepath+"'");
        VideoCategory = DB.getData();
        return VideoCategory;
    }
    private static void cleardata() {
        do {
            String data = DB.getData();
            if (data.equals(DB.NOMOREDATA)) {
                break;
            }

        } while (true);
    }
}
