package sample;

import javafx.util.Pair;

import java.util.*;
import java.util.concurrent.ConcurrentLinkedQueue;

public class Agent extends Thread {
    /**
     * Position actuelle
     */
    int position;
    /**
     * Position finale
     */
    final int position_finale;
    /**
     * Messagerie
     */
    final Queue<Message> messagerie;
    /**
     * L'agent a connaissance de la grille
     */
    final Grille grille;
    /**
     * Derniers agents à avoir envoyé un message à this
     */
    final List<Integer> last_expeditor;

    /**
     * Constructeur.
     * @param position_finale position finale de l'agent
     * @param position position actuelle de l'agent
     * @param grille grille de taquin
     */
    public Agent(int position_finale, int position, Grille grille) {
        this.position_finale = position_finale;
        this.position = position;
        this.messagerie = new ConcurrentLinkedQueue<>();
        this.last_expeditor = new ArrayList<>();
        this.grille = grille;
    }

    /**
     * Déplace l'agent si la position visée est libre.
     * @param new_pos nouvelle position de l'agent
     */
    public void deplace(final int new_pos) {
        synchronized (grille.getGrille()){
            if(grille.getPosGrille(new_pos) == 0){
                grille.setPosGrille(position,0);
                grille.setPosGrille(new_pos, (int) getId());
                position = new_pos;
                grille.majAffichage();
            }
        }
    }

    /**
     * Getteur.
     * @return position finale de l'agent
     */
    public int getPositionFinale() {
        return position_finale;
    }

    /**
     * Getteur.
     * @return position actuelle de l'agent
     */
    public int getPosition() {
        return position;
    }

    /**
     * Getteur.
     * @return identifiant unique de l'agent
     */
    public long getId() {
        return position_finale + 1;
    }

    /**
     * Demander à destinaire de libérer la case.
     * @param destinataire agent à qui demander de se déplacer.
     * @param caseLibere case à libérer.
     */
    public void envoieMessage(Agent destinataire, final int caseLibere){
        destinataire.messagerie.add(new Message(this.getId(), destinataire.getId(), caseLibere));
    }

    /**
     * Lire la messagerie.
     *
     */
    public void litMessagerie() throws InterruptedException {
        int temp;
        if(messagerie.isEmpty()) {
            return;
        }
        Message dernierMessage = messagerie.element();
        if (position != dernierMessage.getCaseLibere()) {
            messagerie.remove(dernierMessage);
            return;
        }
        synchronized (grille.getGrille()) {
            List<Integer> libres = grille.getCaseAutour(position);
            if(libres.contains(grille.getAgent((int) dernierMessage.getExpediteur()).position)) {
                int id = libres.indexOf(grille.getAgent((int) dernierMessage.getExpediteur()).position);
                libres.remove(id);
            }
            Collections.sort(libres);
            last_expeditor.add((int) dernierMessage.getExpediteur());
            temp = libres.get((int) (Math.random() * libres.size()));
            libres = null;
        }
        deplace(temp);
        synchronized (grille.getGrille()) {
            if (position != temp) {
                if(grille.getAgent(grille.getPosGrille(temp)) != null) {
                    envoieMessage(grille.getAgent(grille.getPosGrille(temp)), temp);
                } else {
                    deplace(temp);
                }
            }
        }
        deplace(temp);
        messagerie.remove(dernierMessage);
        try {
            sleep((int)(100*Math.random()));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void run(){
        List<Pair<Integer,Integer>> chemin;

        int i = 1, temp;
        int nb;
        while (!grille.estReconstituee()) {
            try {
                sleep((int) (500 * Math.random()));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            try {
                litMessagerie();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            //seule la première ligne non reconstituée travaille, les autres agents coopérent seulement
            if (grille.ligneReconstituee() >= position_finale / grille.getTaille()) {
                if (position != position_finale) {
                    synchronized (grille.getGrille()) {
                        chemin = Chemin.chemin(position, position_finale, grille);
                        if (chemin.size() > 0) {
                            temp = chemin.get(0).getKey();
                            deplace(temp);
                        }
                    }
                    if (position != position_finale &&
                            (chemin.size() == 0 || Chemin.cheminComplet(position,position_finale, grille) == null)) {
                        nb = 2;
                        while (nb > 0) {
                            chemin = Chemin.cheminOpt(position, position_finale, grille.getTaille());
                            Collections.shuffle(chemin);
                            if (chemin.size() > 0) {
                                temp = chemin.get(0).getKey();
                                deplace(temp);
                                if (position != temp) {
                                    synchronized (grille.getGrille()) {
                                        int id_Agent = grille.getPosGrille(temp);
                                        if(grille.getAgent(id_Agent) != null) {
                                            if (last_expeditor.isEmpty() || id_Agent != last_expeditor.get(last_expeditor.size() - 1)) {
                                                envoieMessage(grille.getAgent(id_Agent), temp);
                                            }
                                        }
                                    }
                                    try {
                                        sleep((int)(100*Math.random()));
                                    } catch (InterruptedException e) {
                                        e.printStackTrace();
                                    }

                                    deplace(temp);
                                }
                            }
                            nb--;
                        }
                    }
                }
            }
            else if (position_finale / grille.getTaille() > position / grille.getTaille()) {
                if (grille.getPosGrille(position + grille.getTaille()) == 0) {
                    deplace(position + grille.getTaille());
                }
            }
        }
        try {
            this.interrupt();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public String toString() {
        return "Agent{" +
                "position=" + position +
                ", position_finale=" + position_finale +
                '}';
    }
}
