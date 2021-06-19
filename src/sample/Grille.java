package sample;

import java.util.ArrayList;
import java.util.List;

public class Grille {

    /**
     * Taille de la grille (x ou y).
     */
    final private int taille;

    /**
     * Nombre total d'agents.
     */
    final private int nb_agent;

    /**
     * Tableau contenant tous les agents.
     */
    private final Agent[] agents;

    /**
     * Grille contenant les identifiants tous les agents.
     * Chaque case contient l'identifiant unique de l'agent présent, 0 si aucun agent présent.
     */
    private final int[] grille;

    public Grille(final int taille, final int nb_agent) {
        this.taille = taille;
        this.nb_agent = nb_agent;
        this.grille = new int[taille*taille];
        this.agents = new Agent[nb_agent];
        initPositions();
    }

    public Grille(final int taille, final Agent[] agents) {
        this.taille = taille;
        this.nb_agent = agents.length;
        this.grille = new int[taille*taille];
        this.agents = agents;
    }

    /**
     * Exemple test.
     */
    public void initExemple() {
        for(int i=0; i<taille*taille; i++){
            for(int j=0; j<nb_agent; j++){
                if(i == agents[j].getPosition()){
                    grille[i] = agents[j].getPositionFinale();
                }
            }
        }
    }

    public Agent getAgent(int idAgent) {
        for(Agent agent: agents) {
            if(agent.getId() == idAgent) {
                return agent;
            }
        }
        return null;
    }

    public void startAgents() {
        for (Agent agent : agents) {
            agent.start();
        }
    }

    public void interruptAgent() {
        for (Agent agent : agents) {
            agent.interrupt();
        }
    }

    /**
     * Initialisation de la grille et des agents (aléatoire).
     */
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
            grille[i] = tab_pos[random];
            if(tab_pos[random] > 0) {
                agents[random] = new Agent(tab_pos[random] - 1, i, this);
            }
            tab_pos[random] = tab_pos[(taille*taille)-i-1];
        }

    }

    public int getPosGrille(final int pos){
        return grille[pos];
    }

    public void setPosGrille(final int id, final int pos){
        grille[id] = pos;
    }

    public int getTaille() {
        return taille;
    }

    public boolean estReconstituee() {
        for (Agent agent: this.agents) {
            if(agent.getPosition() != agent.getPositionFinale()) {
                return false;
            }
        }
        return true;
    }

    public List<Integer>  getCaseLibreAutour(final int position) {
        List<Integer> libres = new ArrayList<>();
        //position n'est pas sur la première ligne
        if (position > taille) {
            if (grille[position - taille] == 0) {
                libres.add(position - taille);
            }
        }
        //position n'est pas sur la dernière ligne
        if (position < taille * (taille - 1)) {
            if (grille[position + taille] == 0) {
                libres.add(taille * (taille - 1));
            }
        }
        //position n'est pas sur la première colonne
        if (position % taille != 0) {
            if (grille[position  - 1] == 0) {
                libres.add(position - 1);
            }
        }
        //position n'est pas sur la première colonne
        if (position % taille != taille) {
            if (grille[position + 1] == 0) {
                libres.add(position + 1);
            }
        }
        return libres;
    }

    @Override
    public String toString() {
        synchronized (this){
            String str = "";
            for (int i = 0; i < taille; i++) {
                str += "---";
            }
            for (int i = 0; i < taille*taille; i++) {
                if (i % taille == 0) {
                    str += "\n";
                }
                if (grille[i] < 10) {
                    str += grille[i] + "  ";
                }
                else if (grille[i] > 0){
                    str += grille[i] + " ";
                }
                else {
                    str += "  ";
                }
            }
            str += "\n";
            for (int i = 0; i < taille; i++) {
                str += "---";
            }
            return str;
        }
    }

}
