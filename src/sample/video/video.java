package sample.video;
/**
 * Class to select a specific VIDEO straigth from the GUI, and then fetched in the DATABASE
 * BY SQL STATEMENTS,
 */

import sample.DB;
import sample.Controller;

public class video
{
    private int Vid_ID;
    private String VideoName, FilePath, VideoCategory;


    public video(int Vid_ID)
    {
        this.Vid_ID = Vid_ID;

        FetchVideos();
    }


    private void FetchVideos()
    {
        DB.selectSQL("SELECT fldVideoTitle FROM tblVideo WHERE fldVideoID = "+ Vid_ID);
        VideoName = DB.getData();

        DB.selectSQL("SELECT fldVideoFilepath FROM tblVideo WHERE fldVideoID = "+ Vid_ID);
        FilePath = DB.getData();

        DB.selectSQL("SELECT fldCategory FROM tblVideo WHERE fldVideo = "+ Vid_ID);
        VideoCategory = DB.getData();
    }

}
