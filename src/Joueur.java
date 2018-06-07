
import java.awt.*;
import java.util.*;


/**
 *
 */
public class Joueur {

    public Joueur(String name, ArrayList<Territoire> listTerritoires, ArrayList<Unite> listUnites, int nbUnites, Color couleur, int nbTerritoiresCapturesTourPrec) {
        this.name = name;
        this.listTerritoires = listTerritoires;
        this.listUnites = listUnites;
        this.nbUnites = nbUnites;
        this.couleur = couleur;
        this.nbTerritoiresCapturesTourPrec = nbTerritoiresCapturesTourPrec;
    }

    private String name;
    ArrayList<Territoire> listTerritoires;
    ArrayList<Unite> listUnites;
    int nbUnites;
    Color couleur;
    public int nbTerritoiresCapturesTourPrec;



    String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void play(String phasePartie, Partie partie) {

    }


    boolean putUnite(Unite unit) {

        int positionx = unit.positionx;
        int positiony = unit.positiony;
        String countryname = Partie.getCountryName(positionx+16, positiony+16);
        boolean possible = false;
        for (Territoire territoire : listTerritoires) {

            if (Objects.equals(countryname, territoire.getName())) {
                possible = true;
            }
        }

        if (possible && (nbUnites - unit.cost) >= 0) {
            listUnites.add(unit);
            nbUnites -= unit.cost;
            return true;
        }
        else {
            return false;
        }
    }


}