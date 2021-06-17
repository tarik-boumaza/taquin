package sample;

import javafx.util.Pair;
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


    public void deplace(final int new_pos){
        synchronized (grille){
            if(grille.getPosGrille(new_pos) == 0){
                grille.setPosGrille(position,0);
                grille.setPosGrille(new_pos, (int) getId());
                position = new_pos;
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
    public void litMessagerie() {
        int[] tabPos = new int[messagerie.size()];
        int i = 0;
        for (Message message : messagerie) {
            tabPos[i] = messagerie.element().getCaseLibere();
        }
        position = tabPos[(int) (Math.random()*(messagerie.size()))];
        messagerie.clear();
    }

    public void run(){
        /*System.out.println("je suis le thread : " + position_finale);
        deplace(position - 5);
        System.out.println(grille);*/
        while (position != position_finale) {
            List<Pair<Integer,Integer>> chemin = Chemin.cheminOpt(position, position_finale, grille.getTaille());
            int temp = chemin.get(0).getKey();
            int i = 1;
            System.out.println("je suis le thread " + getId() + ", je suis à " + position
                    + " et je dois aller à " + temp);
            deplace(temp);
            while(position != temp && i < chemin.size()) {
                chemin = Chemin.cheminOpt(position, position_finale, grille.getTaille());
                System.out.println("je ne me suis pas déplacé");
                temp = chemin.get(i).getKey();
                i++;
            }
            
        }
        try {
            this.interrupt();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
