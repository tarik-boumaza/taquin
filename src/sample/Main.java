package sample;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;


public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Grille grille = new Grille(5,20);
        System.out.println(grille.toString());
        Vue vue = new Vue(grille);
        grille.addObserver(vue);

        for (int i = 0; i < grille.getAgents().length; i++) {
            System.out.println(grille.getAgents()[i]);
        }

        BorderPane root = new BorderPane();
        root.setTop(new Text("Jeu du Taquin \n "));
        root.setCenter(vue.gpane);
        primaryStage.setTitle("Hello World");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
        primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent t) {
                Platform.exit();
                System.exit(0);
            }
        });

        grille.startAgents();


    }


    public static void main(String[] args) throws InterruptedException {
        launch(args);
        /*Grille grille = new Grille(5,20);
        System.out.println(grille.toString());
        grille.startAgents();

        while(!grille.estReconstituee()) {}
        System.out.println(grille.toString());
        System.out.println("fin");
        System.exit(0);*/
    }
}
