package sample.video;
import sample.DB;
import sample.Controller;

/**
 * Class to manage existing Videos or ADD new Videos to the SQL DATABASE
 */

/**
 * Class to manage videos stored, ADD or DELETE a video from and to the SQL Database
 */
public class ManageVideos
{
    public void Video_Add(String VidName, String FilePath, String VidCategory)
    {
        DB.insertSQL("INSERT INTO tblVideo (fldVideoTitle,fldVideoFilePath,fldCategory) VALUES (" + VidName + ", " + FilePath + ", " + VidCategory + ")");
    }

    /**
     * Method to Delete a selected Video from the tblVideo by videotitle
     * @param VidTitle
     */
    public void DeleteVideo(String VidTitle) { DB.deleteSQL("DELETE FROM tblVideo WHERE fldVideoTitle = " + VidTitle);}

    /**
     * Method to delete a selected Video from the tblVideo by VideoID
     * @param VidID
     */
    public void DeleteVideo(int VidID){ DB.deleteSQL("DELETE FROM tblVideo WHERE fldVideoID = " + VidID);}

}
