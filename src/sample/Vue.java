package sample;


import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;

import java.util.Observable;
import java.util.Observer;

public class Vue implements Observer {
    GridPane gpane;
    ImageView[][] tab_images;
    Image[] rouge;
    Image[] vert;
    Image carre;
    Grille grille;

    public Vue(Grille grille) {
        this.grille = grille;
        this.gpane = new GridPane();
        tab_images = new ImageView[grille.getTaille()][grille.getTaille()];
        for (int i = 0; i < grille.getTaille(); i++) {
            for (int j = 0; j < grille.getTaille(); j++) {
                tab_images[i][j] = new ImageView();
            }
        }
        String filepath = "data/images/";
        carre = new Image(filepath + "carre.png");
        vert = new Image[grille.getTaille() * grille.getTaille()];
        rouge = new Image[grille.getTaille() * grille.getTaille()];
        for (int i = 1; i < grille.getTaille() * grille.getTaille(); i++) {
            vert[i] = new Image(filepath + i + "-vert.png");
            rouge[i] = new Image(filepath + i + "-rouge.png");
        }
        setGrille();

        for (int i = 0; i < grille.getTaille(); i++) {
            for (int j = 0; j < grille.getTaille(); j++) {
                gpane.add(tab_images[i][j], j,i);
            }
        }

    }

    public void setGrille() {
        int position;
        int id_thread;
        for (int i = 0; i < grille.getTaille(); i++) {
            for (int j = 0; j < grille.getTaille(); j++) {
                position = i* grille.getTaille()+j;
                id_thread = grille.getPosGrille(position);
                if (id_thread == 0) {
                    tab_images[i][j].setImage(carre);
                }
                else if (id_thread == position + 1) {
                    tab_images[i][j].setImage(vert[id_thread]);
                } else {
                    tab_images[i][j].setImage(rouge[id_thread]);
                }

                tab_images[i][j].setFitHeight(100);
                tab_images[i][j].setPreserveRatio(true);
            }
        }

    }

    @Override
    public void update(Observable o, Object arg) {
        setGrille();
    }
}
