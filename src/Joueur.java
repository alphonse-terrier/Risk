
import java.awt.*;
import java.util.*;


/**
 *
 */
public class Joueur {

    public Joueur(String name, ArrayList<Territoire> listTerritoires, ArrayList<Unite> listUnites, int nbUnites, Color couleur, int nbTerritoiresCapturéesTourPréc) {
        this.name = name;
        this.listTerritoires = listTerritoires;
        this.listUnites = listUnites;
        this.nbUnites = nbUnites;
        this.couleur = couleur;
        this.nbTerritoiresCapturéesTourPréc = nbTerritoiresCapturéesTourPréc;
    }

    private String name;
    public ArrayList<Territoire> listTerritoires;
    public ArrayList<Unite> listUnites;
    public int nbUnites;
    public Color couleur;
    public int nbTerritoiresCapturéesTourPréc;



    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }




    public boolean putUnite(Unite unit) {

        int positionx = unit.positionx;
        int positiony = unit.positiony;
        String countryname = Territoire.getCountryName(positionx+16, positiony+16);
        boolean possible = false;
        for (int i = 0; i < listTerritoires.size(); i++) {

            if (Objects.equals(countryname, listTerritoires.get(i).getName())) {
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