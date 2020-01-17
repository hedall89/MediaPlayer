package sample.playlist;
import sample.Controller;
import sample.DB;


public class VideoLibrary extends Playlists {

    public VideoLibrary(){
        super();
    }

   //TODO
    public void resetVideoList(){

    }
    /*
    public void countvideos(){

        DB.selectSQL("SELECT COUNT(fldVideoTitle) FROM tblVideo");

        int numbersofvideos = Integer.parseInt(DB.getData());

        System.out.println(numbersofvideos);

        cleardata();
    }

    public void retrieveVideos(){
        countvideos();

        DB.selectSQL("SELECT fldVideoTitle FROM tblVideo");

        if (NumbersofVideos > 0){


        }else{
            System.out.println("nothing");
        }
    }

     */
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
