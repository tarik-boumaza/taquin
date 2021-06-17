package sample;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Pair;

import java.util.List;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        BorderPane root = new BorderPane();
        root.setTop(new Text("Jeu du Taquin \n "));
        primaryStage.setTitle("Hello World");
        primaryStage.setScene(new Scene(root, 300, 275));
        primaryStage.show();
    }


    public static void main(String[] args) {
        //launch(args);
        /*Agent[] agents = new Agent[2];
        Grille grille = new Grille(5, agents);
        agents[0] = new Agent(1, 6, grille);
        agents[1] = new Agent(2,7, grille);
        grille.initExemple();
        System.out.println(grille);

        agents[0].start();
        agents[1].start();
        agents[0].interrupt();
        agents[1].interrupt();*/

        System.exit(0);
    }
}
