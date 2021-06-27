package sample;


import javafx.application.Platform;

import java.util.*;

public class Grille extends Observable{

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

    /**
     * Constructeur.
     * @param taille taille de la grille
     * @param nb_agent nombre d'agents
     */
    public Grille(final int taille, final int nb_agent) {
        this.taille = taille;
        this.nb_agent = nb_agent;
        this.grille = new int[taille*taille];
        this.agents = new Agent[nb_agent];
        initPositions();
    }

    /**
     * Renvoie l'agent d'après sa position.
     * @param idAgent identifiant unique de l'agent.
     * @return l'Agent s'il existe, null sinon
     */
    public Agent getAgent(int idAgent) {
        synchronized (this) {
            for(Agent agent: agents) {
                if(agent.getId() == idAgent) {
                    return agent;
                }
            }
            return null;
        }
    }

    /**
     * Getteur.
     * @return les agents
     */
    public Agent[] getAgents() {
        return agents;
    }

    /**
     * Démarre les agents.
     */
    public void startAgents() {
        for (Agent agent : agents) {
            agent.start();
        }
    }

    /**
     * Initialisation de la grille et des agents (aléatoire).
     */
    private void initPositions() {
        int[] tab_pos = new int[taille*taille];

        for (int i = 0; i < nb_agent; i++) {
            tab_pos[i] = i + 1;
        }
        for (int i = nb_agent; i < taille*taille; i++) {
            tab_pos[i] = 0;
        }

        /*for (int i = 0; i < nb_agent; i++) {
            random = (int)(Math.random()*(nb_agent-i-1));
            tab_pos[i] = tab_id[random];
            tab_id[random] = tab_id[nb_agent-i-1];
        }*/

        Random rand = new Random();
        for (int i = 0; i < tab_pos.length; i++) {
            int randomIndexToSwap = rand.nextInt(tab_pos.length);
            int temp = tab_pos[randomIndexToSwap];
            tab_pos[randomIndexToSwap] = tab_pos[i];
            tab_pos[i] = temp;
        }
        int j = 0;
        for (int i = 0; i < taille*taille; i++) {
            grille[i] = tab_pos[i];
            if(tab_pos[i] > 0) {
                agents[j] = new Agent(tab_pos[i]-1, i, this);
                j++;
            }
        }

    }

    /**
     * Getteur.
     * @param pos position sur la grille
     * @return l'identifiant de l'agent qui se trouve à cette position, 0 si aucun agent présent.
     */
    public int getPosGrille(final int pos){
        synchronized (this) {
            return grille[pos];
        }
    }

    /**
     * Setteur.
     * @param id identifiant de l'agent à placer.
     * @param pos nouvelle position de l'agent sur la grille.
     */
    public void setPosGrille(final int id, final int pos){
        synchronized (this) {
            grille[id] = pos;
        }
    }

    /**
     * Getteur.
     * @return taille de la grille (nombre de lignes ou nombre de colonnes)
     */
    public int getTaille() {
        return taille;
    }

    /**
     * Met à jour l'affichage en notifiant l'Observer
     */
    public void majAffichage() {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                setChanged();
                notifyObservers();
            }
        });
    }

    /**
     * Teste si la grille est reconsituée.
     * @return true si elle l'est, false sinon
     */
    public boolean estReconstituee() {
        synchronized (this) {
            for (Agent agent: this.agents) {
                if(agent.getPosition() != agent.getPositionFinale()) {
                    return false;
                }
            }
            return true;
        }
    }

    /**
     * Getteur.
     * @return la grille.
     */
    public int[] getGrille() {
        return grille;
    }

    /**
     * Retourne les cases non occupées autour d'une position
     * @param position position
     * @return liste des positions libres autour
     */
    public List<Integer>  getCaseLibreAutour(final int position) {
        List<Integer> autour = new ArrayList<>();
        //position n'est pas sur la première ligne
        if (position > taille) {
            if (grille[position - taille] == 0) {
                autour.add(position - taille);
            }
        }
        //position n'est pas sur la dernière ligne
        if (position < taille * (taille - 1)) {
            if (grille[position + taille] == 0) {
                autour.add(position + taille);
            }
        }
        //position n'est pas sur la première colonne
        if (position % taille != 0) {
            if (grille[position  - 1] == 0) {
                autour.add(position - 1);
            }
        }
        //position n'est pas sur la dernière colonne
        if (position % taille != taille - 1) {
            if (grille[position + 1] == 0) {
                autour.add(position + 1);
            }
        }
        Collections.shuffle(autour);
        return autour;
    }

    /**
     * Retourne les cases autour d'une position
     * @param position position
     * @return liste des positions autour
     */
    public List<Integer>  getCaseAutour(final int position) {
        List<Integer> libres = new ArrayList<>();
        //position n'est pas sur la première ligne
        if (position > taille) {
            libres.add(position - taille);
        }
        //position n'est pas sur la dernière ligne
        if (position < taille * (taille - 1)) {
            libres.add(position + taille);
        }
        //position n'est pas sur la première colonne
        if (position % taille != 0) {
            libres.add(position - 1);
        }
        //position n'est pas sur la dernière colonne
        if (position % taille != taille - 1) {
            libres.add(position + 1);
        }
        Collections.shuffle(libres);
        return libres;
    }

    /**
     * Retourne le nombre de lignes reconstituee.
     * @return nombre de lignes reconstituee.
     */
    public int ligneReconstituee() {
        int i, j;
        synchronized (grille) {
            for (i = 0; i < taille; i++) {
                for (j = 0; j < taille; j++) {
                    if (grille[i*taille+j] != (i*taille+j+1)) {
                        return i;
                    }
                }
            }
        }
        return taille;
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
