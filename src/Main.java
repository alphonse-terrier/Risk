/**
 * Created by Alphonse on 14/05/2018.
 */


import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Fenetre carte = new Fenetre();
        Territoire france = new Territoire("france");

        Partie.choixNbJoueurs();
        /**Partie.initGame();*/

        // A FAIRE : Condition d'arrêt du jeu : Joueur.listRegions.size() == 6
    }



}
