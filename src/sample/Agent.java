package sample;

import javafx.util.Pair;

import java.util.*;
import java.util.concurrent.ConcurrentLinkedQueue;

public class Agent extends Thread {
    int position;
    final int position_finale;
    final Queue<Message> messagerie;
    final Grille grille;
    final List<Integer> last_expeditor;
    int etat_blocage;


    public Agent(int position_finale, int position, Grille grille) {
        this.position_finale = position_finale;
        this.position = position;
        this.messagerie = new ConcurrentLinkedQueue<>();
        this.last_expeditor = new ArrayList<>();
        this.etat_blocage = 0;
        this.grille = grille;
    }


    public void deplace(final int new_pos) {
        synchronized (grille.getGrille()){
            if(grille.getPosGrille(new_pos) == 0){
                grille.setPosGrille(position,0);
                grille.setPosGrille(new_pos, (int) getId());
                position = new_pos;
                grille.majAffichage();
                //System.out.println(grille.toString());
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

    /**
     * Demander à destinaire de libérer la case.
     * @param destinataire agent à qui demander de se déplacer.
     * @param caseLibere case à libérer.
     */
    public void envoieMessage(Agent destinataire, final int caseLibere){
        destinataire.messagerie.add(new Message(this.getId(), destinataire.getId(), caseLibere));
    }

    public boolean enEtatDeBlocage() {
        if (Chemin.getDistance(position, position_finale, grille.getTaille()) > 1) {
            return false;
        }
        if (grille.getPosGrille(position_finale) == 0) {
            return false;
        }
        return position == grille.getAgent(grille.getPosGrille(position_finale)).getPositionFinale();

       /* Magnifique travail de Hanoua mégnonne
       int i = position / grille.getTaille();
       int j = position % grille.getTaille();
       boolean blocage = false;
        if (i > 0 && i < grille.getTaille()-1) {
            if (j > 0 && j < grille.getTaille()-1) {
                pos = grille.getPosGrille((i+1)*grille.getTaille()+j);
                if (pos != 0) {
                    Agent haut = grille.getAgent(pos);
                    if (haut.position == position_finale && haut.position_finale == position)
                        blocage = true;

                }
                pos = grille.getPosGrille((i-1)*grille.getTaille()+j);
                if (!blocage && pos != 0 ) {
                    Agent bas = grille.getAgent(pos);
                    if (bas.position == position_finale && bas.position_finale == position)
                        blocage = true;
                }
                pos = grille.getPosGrille(i*grille.getTaille()+j+1);
                if (!blocage && pos != 0 ) {
                    Agent droite = grille.getAgent(pos);
                    if (droite.position == position_finale && droite.position_finale == position)
                        blocage = true;
                }
                pos = grille.getPosGrille(i*grille.getTaille()+j-1);
                if (!blocage && pos != 0 ) {
                    Agent gauche = grille.getAgent(pos);
                    if (gauche.position == position_finale && gauche.position_finale == position)
                        blocage = true;
                }

            }
        }*/

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

        int temp;
        if(messagerie.isEmpty()) {
            //System.out.println("Je suis le thread " + getId() + " et je n'ai pas de nouveau message");
            return;
        }
        Message dernierMessage = messagerie.element();
//        System.out.println(dernierMessage.getExpediteur() + " demande à " + dernierMessage.getDestinataire()
//                + " de libérer " + dernierMessage.getCaseLibere());

        if (position != dernierMessage.getCaseLibere()) {
            messagerie.remove(dernierMessage);
            return;
        }
        synchronized (grille.getGrille()) {
            List<Integer> libres = grille.getCaseAutour(position);
//            if (libres.size() == 0) {
//                libres = grille.getCaseAutour(position);
//                temp = libres.get(0);
//                envoieMessage(grille.getAgent(grille.getPosGrille(libres.get(0))), libres.get(0));
//                System.out.println("je suis le thread " + getId() + " j'envoie un sms à " + grille.getPosGrille(libres.get(0)));
//                messagerie.remove(dernierMessage);
//                return;
//            }

            // Distance la plus courte de la case d'arrivée -> boucles !!!
//            List<Pair<Integer, Integer>> position_distance = new ArrayList<>();
//            for (int i = 0; i < libres.size(); i++) {
//                position_distance.add(new Pair<>(libres.get(i),
//                                        Chemin.getDistance(position_finale, libres.get(i),
//                                                grille.getTaille()*grille.getTaille())));
//            }
//            position_distance.sort(Comparator.comparing(Pair::getValue));

            if(libres.contains(grille.getAgent((int) dernierMessage.getExpediteur()).position)) {
                int id = libres.indexOf(grille.getAgent((int) dernierMessage.getExpediteur()).position);
                libres.remove(id);
            }
            /*List<Integer>  libres_priorise = new ArrayList<>();
            for (Integer id : libres) {
                if (id / grille.getTaille() < position / grille.getTaille()) {
                    libres_priorise.add(id);
                }
            }
            */
            Collections.sort(libres);
            last_expeditor.add((int) dernierMessage.getExpediteur());
            temp = libres.get((int) (Math.random() * libres.size()));
            //temp = libres.get(libres.size() - 1);
            /*position_distance.sort(Comparator.comparing(Pair::getValue));
            if (position_distance.get(0).getValue() < position_distance.get(1).getValue()) {
                deplace(position_distance.get(0).getKey());
            }
            else {
                deplace(libres.get((int) (Math.random() * libres.size())));
            }*/
            libres = null;
        }

        deplace(temp);
        synchronized (grille.getGrille()) {
            if (position != temp) {
                if(grille.getAgent(grille.getPosGrille(temp)) != null) {
                    envoieMessage(grille.getAgent(grille.getPosGrille(temp)), temp);
                    //suspend();
                    //System.out.println("je suis le thread " + getId() + " j'envoie un sms à " + grille.getPosGrille(temp));
                } else {
                    deplace(temp);
                    //grille.getAgent((int) dernierMessage.getExpediteur()).resume();
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
        int nb = 2;
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
            if (grille.ligneReconstituee() == position_finale / grille.getTaille()) {
                if (position != position_finale) {
                    synchronized (grille.getGrille()) {
                        chemin = Chemin.chemin(position, position_finale, grille);
                        //System.out.println("mes chemins : " + chemin);

                        if (chemin.size() > 0) {
                            temp = chemin.get(0).getKey();
//                        System.out.println("je suis le thread " + getId() + ", je suis à " + position
//                                + " et je dois aller à " + temp);
                            deplace(temp);

                        /*while (position != temp && i < chemin.size()) {
                            System.out.println("je ne me suis pas déplacé");
                            temp = chemin.get(i).getKey();
                            deplace(temp);
                            i++;
                        }*/
                        }
                    }
                    if (position != position_finale &&
                            (chemin.size() == 0 || Chemin.cheminComplet(position,position_finale, grille) == null)) {
                        nb = 2;
                        while (nb > 0) {
                            chemin = Chemin.cheminOpt(position, position_finale, grille.getTaille());
                            Collections.shuffle(chemin);
                            //System.out.println("chemins opt : " + chemin);
                            if (chemin.size() > 0) {
                                temp = chemin.get(0).getKey();
                                deplace(temp);
                                if (position != temp) {
                                    synchronized (grille.getGrille()) {
                                        int id_Agent = grille.getPosGrille(temp);
                                        if(grille.getAgent(id_Agent) != null) {
                                            if (last_expeditor.isEmpty() || id_Agent != last_expeditor.get(last_expeditor.size() - 1)) {
                                                envoieMessage(grille.getAgent(id_Agent), temp);
                                                //suspend();
                                                //System.out.println("je suis le thread " + getId() + " j'envoie un sms à " + grille.getPosGrille(temp));
                                                //System.out.println(grille.getAgent(grille.getPosGrille(temp)).messagerie);
                                            }
                                        }
                                    }
                                    try {
                                        sleep((int)(100*Math.random()));
                                    } catch (InterruptedException e) {
                                        e.printStackTrace();
                                    }

                                    deplace(temp);
//                            long debut = System.currentTimeMillis();
//                            int random = (int) (500*Math.random());
//                            while(position != temp && System.currentTimeMillis() < debut + random) {
//
//                            }
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
