package sample;


import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;

import java.util.Observable;
import java.util.Observer;

public class Vue implements Observer {
    GridPane gpane;
    ImageView[][] tab_images;
    Grille grille;

    public Vue(Grille grille) {
        this.grille = grille;
        this.gpane = new GridPane();
        tab_images = new ImageView[grille.getTaille()][grille.getTaille()];
        setGrille();
    }

    public void setGrille() {
        String filepath = "data/images/";
        Image rouge = new Image(filepath + "rouge.png");
        Image vert = new Image(filepath + "vert.png");
        Image carre = new Image(filepath + "carre.png");

        int position;
        int id_thread;
        for (int i = 0; i < grille.getTaille(); i++) {
            for (int j = 0; j < grille.getTaille(); j++) {
                position = i* grille.getTaille()+j;
                id_thread = grille.getPosGrille(position);
                if (id_thread == 0) {
                    tab_images[i][j] = new ImageView(carre);
                }
                else if (id_thread == position + 1) {
                    tab_images[i][j] = new ImageView(vert);
                } else {
                    tab_images[i][j] = new ImageView(rouge);
                }

                tab_images[i][j].setFitHeight(100);
                tab_images[i][j].setPreserveRatio(true);
                gpane.add(tab_images[i][j], j,i);
            }
        }

    }

    @Override
    public void update(Observable o, Object arg) {
        setGrille();
    }
}
