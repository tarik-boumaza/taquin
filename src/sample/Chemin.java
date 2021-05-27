package sample;

import javafx.util.Pair;
import java.util.ArrayList;

public class Chemin {

    public static ArrayList<Pair<Integer, Integer>>cheminOpt(final int depart,
                                                             final int arrivee,
                                                             final int taille) {
        ArrayList<Pair<Integer, Integer>> chemins = new ArrayList<>();
        if (depart/taille == arrivee/taille) {
            if (depart < arrivee) {
                chemins.add(new Pair<>(depart+1, getDistance(depart,arrivee,taille)));
            }
            else if (depart > arrivee){
                chemins.add(new Pair<>(depart-1, getDistance(depart,arrivee,taille)));
            }
            else {
                chemins.add(new Pair<>(0, 0));
            }
        }
        else if (depart%taille == arrivee%taille) {
            if (depart/taille < arrivee/taille) {
                chemins.add(new Pair<>(depart+taille,  getDistance(depart,arrivee,taille)));
            }
            else if (depart/taille > arrivee/taille){
                chemins.add(new Pair<>(depart-taille, getDistance(depart,arrivee,taille)));
            }
        }
        else {
            if (depart/taille < arrivee/taille) {
                chemins.add(new Pair<>(depart + taille, getDistance(depart, arrivee, taille)));
            }
            else {
                chemins.add(new Pair<>(depart - taille, getDistance(depart, arrivee, taille)));
            }

            if (depart%taille < arrivee%taille) {
                chemins.add(new Pair<>(depart+1, getDistance(depart,arrivee,taille)));
            }
            else {
                chemins.add(new Pair<>(depart-1, getDistance(depart,arrivee,taille)));
            }
        }
        return chemins;
    }

    public static Integer getDistance(final int depart, final int arrivee, final int taille) {
        Pair <Integer, Integer> p1 = getIndices(depart, taille);
        Pair <Integer, Integer> p2 = getIndices(arrivee, taille);
        return Math.abs(p1.getKey() - p2.getKey())
                + Math.abs(p1.getValue() - p2.getValue());
    }

    public static Pair<Integer, Integer> getIndices(final int pos, final int taille) {
        return new Pair<>(pos / taille, pos % taille);
    }
}
