package sample;

import javafx.util.Pair;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

public class Agent extends Thread {
    int position;
    final int position_finale;
    final Queue<Pair<Integer, String>> messagerie;
    final Grille grille;


    public Agent(int position_finale, int position, Grille grille) {
        this.position_finale = position_finale;
        this.position = position;
        messagerie = new ConcurrentLinkedQueue<>();
        this.grille = grille;
    }


    public void deplace(final int new_pos){
        synchronized (grille){
            if(grille.getPosGrille(new_pos) == 0){
                grille.setPosGrille(position,0);
                grille.setPosGrille(new_pos, position_finale);
                setPosition(new_pos);

            }
        }

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

    public void send_message(Agent to, String message){
        to.messagerie.add(new Pair<>(position_finale, message));
    }

    
    public void run(){
        System.out.println("je suis le thread : " + position_finale);
        deplace(position - 5);
        System.out.println(grille);

    }
}
