import java.awt.*;
import java.util.ArrayList;
import java.util.Objects;


/**
 * Classe du joueur
 */
public class Joueur {

    public int nbTerritoiresCapturesTourPrec; //nombre de territoires capturés au tour précédent
    ArrayList < Territoire > listTerritoires; //liste des territoires du joueur
    ArrayList < Unite > listUnites; //liste de ses unités
    int nbUnites; //nombre d'unités qu'il a à poser
    Color couleur; //couleur associé au joueur
    private String name;
    public Joueur(String name, ArrayList < Territoire > listTerritoires, ArrayList < Unite > listUnites, int nbUnites, Color couleur, int nbTerritoiresCapturesTourPrec) {
        this.name = name;
        this.listTerritoires = listTerritoires;
        this.listUnites = listUnites;
        this.nbUnites = nbUnites;
        this.couleur = couleur;
        this.nbTerritoiresCapturesTourPrec = nbTerritoiresCapturesTourPrec;
    }

    String getName() {
        return name;
    }


    public void play(String phasePartie, Partie partie) {

    }


    boolean putUnite(Unite unit) {
        /* Permet de poser une unité sur le plateau (vérifie les conditions) */
        int positionx = unit.positionx;
        int positiony = unit.positiony;
        String countryname = Partie.getCountryName(positionx + 16, positiony + 16);
        boolean possible = false;
        for (Territoire territoire: listTerritoires) {

            if (Objects.equals(countryname, territoire.getName())) {
                possible = true;
            }
        }

        if (possible && (nbUnites - unit.cost) >= 0) {
            listUnites.add(unit);
            nbUnites -= unit.cost;
            return true;
        } else {
            return false;
        }
    }


}