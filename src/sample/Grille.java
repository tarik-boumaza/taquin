package sample;

import java.util.Arrays;

public class Grille {

    final private int taille;
    final private int nb_agent;
    private Agent[][] grille;

    public Grille(final int taille, final int nb_agent) {
        this.taille = taille;
        this.nb_agent = nb_agent;
        this.grille = new Agent[taille][taille];
        initPositions();
    }

    public Grille(final int taille, final int nb_agent, final Agent[][] grille) {
        this.taille = taille;
        this.nb_agent = nb_agent;
        this.grille = grille;
    }

    private void initPositions() {
        int random;
        int[] tab_id = new int[nb_agent];
        int[] tab_pos = new int[taille*taille];

        for (int i = 0; i < nb_agent; i++) {
            tab_id[i] = i + 1;
        }
        for (int i = 0; i < taille*taille; i++) {
            tab_pos[i] = 0;
        }

        for (int i = 0; i < nb_agent; i++) {
            random = (int)(Math.random()*(nb_agent-i-1));
            tab_pos[i] = tab_id[random];
            tab_id[random] = tab_id[nb_agent-i-1];
        }

        for (int i = 0; i < taille*taille; i++) {
            random = (int)(Math.random()*(taille*taille-i-1));
            grille[i/taille][i%taille] = new Agent(tab_pos[random], i);
            tab_pos[random] = tab_pos[(taille*taille)-i-1];
        }
    }

    @Override
    public String toString() {
        String str = "";
        int i, j;
        for (i = 0; i < taille; i++) {
            for (j = 0; j < taille; j++) {
                if (grille[i][j].getId() < 10) {
                    str += grille[i][j].getPositionFinale() + "  ";
                }
                else {
                    str += grille[i][j].getPositionFinale()+ " ";
                }
            }
            str += "\n";
        }
        return str;
    }


}
