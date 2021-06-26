package sample;

import javafx.util.Pair;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class Chemin {

    /**
     * Renvoie les chemins les plus courts entre deux positions, et leurs distances.
     * @param depart coordonnée 1D de départ.
     * @param arrivee coordonnée 1D de départ.
     * @param taille taille de la grille (x ou y)
     * @return Liste<Pair<position_suivante, distance_depart-arrivee>>
     */
    public static ArrayList<Pair<Integer, Integer>>cheminOpt(final int depart,
                                                             final int arrivee,
                                                             final int taille) {
        ArrayList<Pair<Integer, Integer>> chemins = new ArrayList<>();
        if (depart/taille == arrivee/taille) {
            if (depart < arrivee) {
                chemins.add(new Pair<>(depart+1, getDistance(depart + 1,arrivee,taille)));
            }
            else if (depart > arrivee){
                chemins.add(new Pair<>(depart-1, getDistance(depart - 1,arrivee,taille)));
            }
        }
        else if (depart%taille == arrivee%taille) {
            if (depart/taille < arrivee/taille) {
                chemins.add(new Pair<>(depart+taille,  getDistance(depart + taille,arrivee,taille)));
            }
            else if (depart/taille > arrivee/taille){
                chemins.add(new Pair<>(depart-taille, getDistance(depart - taille,arrivee,taille)));
            }
        }
        else {
            if (depart/taille < arrivee/taille) {
                chemins.add(new Pair<>(depart + taille, getDistance(depart + taille, arrivee, taille)));
            }
            else if (depart/taille > arrivee/taille) {
                chemins.add(new Pair<>(depart - taille, getDistance(depart - taille, arrivee, taille)));
            }

            if (depart%taille < arrivee%taille) {
                chemins.add(new Pair<>(depart+1, getDistance(depart + 1,arrivee,taille)));
            }
            else if (depart%taille > arrivee%taille) {
                chemins.add(new Pair<>(depart-1, getDistance(depart - 1,arrivee,taille)));
            }
        }
        return chemins;
    }

    /**
     * Renvoie les chemins possibles entre deux positions, et leurs distances.
     * @param depart coordonnée 1D de départ.
     * @param arrivee coordonnée 1D de départ.
     * @param grille grille courante
     * @return Liste<Pair<position_suivante, distance_depart-arrivee>>
     */
    public static List<Pair<Integer, Integer>>chemin(final int depart,
                                                          final int arrivee,
                                                          final Grille grille) {
        ArrayList<Pair<Integer, Integer>> chemins = new ArrayList<>();
        int taille = grille.getTaille();

        //depart est sur la 1ere ligne
        if (depart < taille) {
            //et depart est sur la premiere colonne
            if (depart % taille == 0) {
                if (grille.getPosGrille(depart + 1) == 0) {
                    chemins.add(new Pair<Integer, Integer>(depart + 1,
                            getDistance(depart + 1, arrivee, taille)));
                }
                if (grille.getPosGrille(depart + taille) == 0) {
                    chemins.add(new Pair<Integer, Integer>(depart + taille,
                            getDistance(depart + taille, arrivee, taille)));
                }
            }
            //et depart est sur la derniere colonne
            else if (depart % taille == taille - 1) {
                if (grille.getPosGrille(depart - 1) == 0) {
                    chemins.add(new Pair<Integer, Integer>(depart - 1,
                            getDistance(depart - 1, arrivee, taille)));
                }
                if (grille.getPosGrille(depart + taille) == 0) {
                    chemins.add(new Pair<Integer, Integer>(depart + taille,
                            getDistance(depart + taille, arrivee, taille)));
                }
            }
            else {
                //sur la première ligne mais pas sur la première ou dernière colonne
                if (grille.getPosGrille(depart + 1) == 0) {
                    chemins.add(new Pair<Integer, Integer>(depart + 1,
                            getDistance(depart + 1, arrivee, taille)));
                }
                if (grille.getPosGrille(depart + taille) == 0) {
                    chemins.add(new Pair<Integer, Integer>(depart + taille,
                            getDistance(depart + taille, arrivee, taille)));
                }
                if (grille.getPosGrille(depart - 1) == 0) {
                    chemins.add(new Pair<Integer, Integer>(depart - 1,
                            getDistance(depart - 1, arrivee, taille)));
                }
            }
        }
        //sinon depart est sur la derniere ligne
        else if (depart >= taille*(taille - 1)) {
            //et depart est sur la premiere colonne
            if (depart % taille == 0) {
                if (grille.getPosGrille(depart + 1) == 0) {
                    chemins.add(new Pair<Integer, Integer>(depart + 1,
                            getDistance(depart + 1, arrivee, taille)));
                }
                if (grille.getPosGrille(depart - taille) == 0) {
                    chemins.add(new Pair<Integer, Integer>(depart - taille,
                            getDistance(depart - taille, arrivee, taille)));
                }
            }
            //et depart est sur la derniere colonne
            else if (depart % taille == taille - 1) {
                if (grille.getPosGrille(depart - 1) == 0) {
                    chemins.add(new Pair<Integer, Integer>(depart - 1,
                            getDistance(depart - 1, arrivee, taille)));
                }
                if (grille.getPosGrille(depart - taille) == 0) {
                    chemins.add(new Pair<Integer, Integer>(depart - taille,
                            getDistance(depart - taille, arrivee, taille)));
                }
            }
            // et depart pas sur la premiere ou dernière colonne
            else {
                if (grille.getPosGrille(depart + 1) == 0) {
                    chemins.add(new Pair<Integer, Integer>(depart + 1,
                            getDistance(depart + 1, arrivee, taille)));
                }
                if (grille.getPosGrille(depart - taille) == 0) {
                    chemins.add(new Pair<Integer, Integer>(depart - taille,
                            getDistance(depart - taille, arrivee, taille)));
                }
                if (grille.getPosGrille(depart - 1) == 0) {
                    chemins.add(new Pair<Integer, Integer>(depart - 1,
                            getDistance(depart - 1, arrivee, taille)));
                }
            }
        }
        //sinon depart est sur la premiere colonne mais pas sur la premiere ou derniere ligne
        else if (depart % taille == 0) {
            if (grille.getPosGrille(depart - taille) == 0) {
                chemins.add(new Pair<Integer, Integer>(depart - taille,
                        getDistance(depart - taille, arrivee, taille)));
            }
            if (grille.getPosGrille(depart + taille) == 0) {
                chemins.add(new Pair<Integer, Integer>(depart + taille,
                        getDistance(depart + taille, arrivee, taille)));
            }
            if (grille.getPosGrille(depart + 1) == 0) {
                chemins.add(new Pair<Integer, Integer>(depart + 1,
                        getDistance(depart + 1, arrivee, taille)));
            }
        }
        //sinon depart est sur la derniere colonne mais pas sur la premiere ou derniere ligne
        else if (depart % taille == taille - 1) {
            if (grille.getPosGrille(depart - taille) == 0) {
                chemins.add(new Pair<Integer, Integer>(depart - taille,
                        getDistance(depart - taille, arrivee, taille)));
            }
            if (grille.getPosGrille(depart + taille) == 0) {
                chemins.add(new Pair<Integer, Integer>(depart + taille,
                        getDistance(depart + taille, arrivee, taille)));
            }
            if (grille.getPosGrille(depart - 1) == 0) {
                chemins.add(new Pair<Integer, Integer>(depart - 1,
                        getDistance(depart - 1, arrivee, taille)));
            }
        }
        else {
            if (grille.getPosGrille(depart - taille) == 0) {
                chemins.add(new Pair<Integer, Integer>(depart - taille,
                        getDistance(depart - taille, arrivee, taille)));
            }
            if (grille.getPosGrille(depart + taille) == 0) {
                chemins.add(new Pair<Integer, Integer>(depart + taille,
                        getDistance(depart + taille, arrivee, taille)));
            }
            if (grille.getPosGrille(depart - 1) == 0) {
                chemins.add(new Pair<Integer, Integer>(depart - 1,
                        getDistance(depart - 1, arrivee, taille)));
            }
            if (grille.getPosGrille(depart + 1) == 0) {
                chemins.add(new Pair<Integer, Integer>(depart + 1,
                        getDistance(depart + 1, arrivee, taille)));
            }
        }
        //Collections.sort(chemins, Comparator.comparing(p -> p.getKey()));
        Collections.sort(chemins, Comparator.comparing(p -> p.getValue()));

        List<Pair<Integer, Integer>> result = new ArrayList<>();
        int i = 0;
        while (i < chemins.size()) {
            if (chemins.get(0).getValue() >= chemins.get(i).getValue()) {
                result.add(chemins.get(i));
            }
            i++;
        }
        Collections.shuffle(result);
//        System.out.println("chemins : " + chemins);
//        System.out.println("result : " + result);
        return result;
    }

    public static Integer cheminOptPossible(final int depart,
                                                  final int arrivee,
                                                  final Grille grille) {
        List<Pair<Integer, Integer>> chemins = cheminOpt(depart, arrivee, grille.getTaille());
        Collections.shuffle(chemins);
        Collections.sort(chemins, Comparator.comparing(p -> p.getValue()));
        for (int i = 0; i < chemins.size(); i++) {
            if (grille.getPosGrille(chemins.get(i).getKey()) == 0) {
                return chemins.get(i).getKey();
            }
        }
        return null;
    }

    /**
     * Renvoie un des chemins optimaux possibles entre deux positions, et leurs distances.
     * Renvoie null s'il n'en existe pas
     * @param depart coordonnée 1D de départ.
     * @param arrivee coordonnée 1D de départ.
     * @param grille grille courante
     * @return Liste<Pair<position_suivante, distance_depart-arrivee>>
     */
    public static Pair<List<Integer>, Integer> cheminComplet(final int depart,
                                                            final int arrivee,
                                                            final Grille grille) {
        List<Integer> chemin = new ArrayList<>();
        int dep = depart;
        synchronized (grille) {
            while(dep != arrivee) {
                if (cheminOptPossible(dep, arrivee, grille) == null) {
                    return null;
                }
                dep = cheminOptPossible(dep, arrivee, grille);
                chemin.add(dep);
            }
        }
        return new Pair<>(chemin, getDistance(depart, arrivee, grille.getTaille()));
    }

    /**
     * Renvoie la distance entre deux points.
     * @param depart coordonnée 1D.
     * @param arrivee coordonnée 1D.
     * @param taille taille de la grille (x ou y).
     * @return entier.
     */
    public static Integer getDistance(final int depart, final int arrivee, final int taille) {
        Pair <Integer, Integer> p1 = getIndices(depart, taille);
        Pair <Integer, Integer> p2 = getIndices(arrivee, taille);
        return Math.abs(p1.getKey() - p2.getKey())
                + Math.abs(p1.getValue() - p2.getValue());
    }

    /**
     * Conversion de type de coordonnées.
     * @param pos coordonnée 1D.
     * @param taille taille de la grille (x ou y).
     * @return coordonnées 2D : Pait<i,j>.
     */
    public static Pair<Integer, Integer> getIndices(final int pos, final int taille) {
        return new Pair<>(pos / taille, pos % taille);
    }
}
