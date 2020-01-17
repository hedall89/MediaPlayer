package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.geometry.Insets;
import javafx.scene.control.Button;

import javax.swing.text.html.ListView;





public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        primaryStage.setTitle("MediaPlayerDemo");
        primaryStage.setScene(new Scene(root, 500, 500));
        primaryStage.show();
        GridPane mediaplayer = new GridPane();


        //Image bk = new Image(getClass().getResourceAsStream("back.png"));
        //Image fw = new Image(getClass().getResourceAsStream("forward.png"));
        //Image st = new Image(getClass().getResourceAsStream("stop.png"));
        //Image pv = new Image(getClass().getResourceAsStream("playlist.png"));
        //Image pl = new Image(getClass().getResourceAsStream("play.png"));
        //Image ps = new Image(getClass().getResourceAsStream("pause.png"));
        //Button play = new Button("",new ImageView(pl));
        //Button pause = new Button("", new ImageView(ps));
        //Button forward = new Button("", new ImageView(fw));
        //Button stop = new Button("", new ImageView(st));
        //Button addplaylist = new Button("", new ImageView(pv));
        //Button backward = new Button("", new ImageView(bk));



    }

    public static void main(String[] args) {

        launch(args);

    }
}
