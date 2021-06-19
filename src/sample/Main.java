package sample;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;


public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Grille grille = new Grille(5,2);
        Vue vue = new Vue(grille);
        grille.addObserver(vue);
        grille.startAgents();


        BorderPane root = new BorderPane();
        root.setTop(new Text("Jeu du Taquin \n "));
        root.setCenter(vue.gpane);
        primaryStage.setTitle("Hello World");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }


    public static void main(String[] args) throws InterruptedException {
        launch(args);
        /*Agent[] agents = new Agent[2];
        Grille grille = new Grille(5, agents);
        agents[0] = new Agent(0, 6, grille);
        agents[1] = new Agent(1,7, grille);
        grille.initExemple();

        Grille grille = new Grille(5,2);
        System.out.println(grille);
        grille.startAgents();

        while(!grille.estReconstituee()) {}
        System.out.println("fin");
        System.exit(0);*/
    }
}
