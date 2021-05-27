package sample;

import javafx.util.Pair;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

public class Agent extends Thread {
    int id;
    int position;
    Queue<Pair<Integer, String>> messagerie;


    public Agent(int id, int position) {
        this.id = id;
        this.position = position;
        messagerie = new ConcurrentLinkedQueue<>();
    }

    public void send_message(Agent to, String message){
        to.messagerie.add(new Pair<>(id, message));
    }

    public int getPosition() {
        return position;
    }
    
    public void run(){
        System.out.println("je suis le thread : " + id);
    }
}
