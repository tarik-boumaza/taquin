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
        Grille grille = new Grille(5,21);
        Vue vue = new Vue(grille);
        grille.addObserver(vue);
        BorderPane root = new BorderPane();
        root.setCenter(vue.gpane);
        primaryStage.setTitle("Jeu du taquin");
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
    }
}
