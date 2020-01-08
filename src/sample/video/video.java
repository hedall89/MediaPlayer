package sample.video;
import sample.DB;

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
        DB.selectSQL("SELECT fldVideo_Name FROM tblVideo WHERE fldVideoID = "+ Vid_ID);
        VideoName = DB.getData();

        DB.selectSQL("SELECT fldVideoFilepath FROM tblVideo WHERE fldVideoID = "+ Vid_ID);
        FilePath = DB.getData();

        DB.selectSQL("SELECT fldVideoCategory FROM tblVideo WHERE fldVideo = "+ Vid_ID);
        VideoCategory = DB.getData();
    }



}
