package sample;

import javafx.util.Pair;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

public class Agent extends Thread {
    int position;
    final int position_finale;
    final Queue<Message> messagerie;
    final Grille grille;


    public Agent(int position_finale, int position, Grille grille) {
        this.position_finale = position_finale;
        this.position = position;
        this.messagerie = new ConcurrentLinkedQueue<>();
        this.grille = grille;
    }


    public void deplace(final int new_pos) {
        synchronized (grille){
            if(grille.getPosGrille(new_pos) == 0){
                grille.setPosGrille(position,0);
                grille.setPosGrille(new_pos, (int) getId());
                position = new_pos;
                grille.majAffichage();
            }
        }
        System.out.println(grille.toString());
    }

    public int getPositionFinale() {
        return position_finale;
    }

    public int getPosition() {
        return position;
    }

    public long getId() {
        return position_finale + 1;
    }

    public void setPosition(int position) {
        this.position = position;
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
        /*int[] tabPos = new int[messagerie.size()];
        int i = 0;
        for (Message message : messagerie) {
            tabPos[i] = messagerie.element().getCaseLibere();
        }
        position = tabPos[(int) (Math.random()*(messagerie.size()))];
        messagerie.clear();*/
        if(messagerie.isEmpty()) {
            return;
        }
        Message dernierMessage = messagerie.element();
        System.out.println(dernierMessage.getExpediteur() + " demande à " + dernierMessage.getDestinataire()
                + " de libérer " + dernierMessage.getCaseLibere());
        synchronized (grille) {
            if (position != dernierMessage.getCaseLibere()) {
                messagerie.remove(dernierMessage);
                return;
            }
            List<Integer> libres = grille.getCaseLibreAutour(position);
            if (libres.size() == 0) {
                messagerie.remove(dernierMessage);
                return;
            }
            List<Pair<Integer, Integer>> position_distance = new ArrayList<>();
            for (int i = 0; i < libres.size(); i++) {
                position_distance.add(new Pair<>(libres.get(i),
                                        Chemin.getDistance(position_finale, libres.get(i),
                                                grille.getTaille()*grille.getTaille())));
            }
            /*position_distance.sort(Comparator.comparing(Pair::getValue));
            if (position_distance.get(0).getValue() < position_distance.get(1).getValue()) {
                deplace(position_distance.get(0).getKey());
            }
            else {
                deplace(libres.get((int) (Math.random() * libres.size())));
            }*/
            deplace(libres.get((int) (Math.random() * libres.size())));
            sleep(100);
        }
        messagerie.remove(dernierMessage);
    }

    public void run(){
        List<Pair<Integer,Integer>> chemin;

        int i = 1, temp;
        while (!grille.estReconstituee()) {
            try {
                sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            try {
                litMessagerie();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }


            if(position != position_finale) {
                chemin = Chemin.chemin(position, position_finale, grille);
                System.out.println("mes chemins : " + chemin);
                temp = chemin.get(0).getKey();

                System.out.println("je suis le thread " + getId() + ", je suis à " + position
                        + " et je dois aller à " + temp);
                deplace(temp);
                while(position != temp && i < chemin.size()) {
                    System.out.println("je ne me suis pas déplacé");
                    temp = chemin.get(i).getKey();
                    deplace(temp);
                    i++;
                }
                if(position != temp) {
                    envoieMessage(grille.getAgent(grille.getPosGrille(temp)), temp);
                    try {
                        sleep(10);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                deplace(temp);
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
