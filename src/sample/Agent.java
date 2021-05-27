package sample;

import javafx.util.Pair;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

public class Agent extends Thread {
    int position;
    final int position_finale;
    Queue<Pair<Integer, String>> messagerie;


    public Agent(int position_finale, int position) {
        this.position_finale = position_finale;
        this.position = position;
        messagerie = new ConcurrentLinkedQueue<>();
    }


    public int getPositionFinale() {
        return position_finale;
    }

    public int getPosition() {
        return position;
    }

    public void send_message(Agent to, String message){
        to.messagerie.add(new Pair<>(position_finale, message));
    }


    
    public void run(){
        System.out.println("je suis le thread : " + position_finale);
    }
}
