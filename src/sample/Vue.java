package sample;


import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Vue {
    ImageView[][] tab_images;
    Grille grille;

    public Vue(Grille grille) {
        this.grille = grille;
        Image rouge = new Image("data/images/rouge.png");
        Image vert = new Image("data/images/vert.png");
        Image carre = new Image("data/images/carre.png");

        tab_images = new ImageView[grille.getTaille()][grille.getTaille()];
        int position;
        int id_thread;
        for (int i = 0; i < grille.getTaille(); i++) {
            for (int j = 0; j < grille.getTaille(); j++) {
                position = i* grille.getTaille()+j;
                id_thread = grille.getPosGrille(position);

                if (id_thread == 0) {
                   tab_images[i][j] = new ImageView(carre);
                } else {
                    Agent agent = grille.getAgent(id_thread);
                    if (agent.getPosition() == agent.getPositionFinale()) {
                        tab_images[i][j] = new ImageView(vert);
                    } else {
                        tab_images[i][j] = new ImageView(rouge);
                    }
                }
            }
        }
    }
}
