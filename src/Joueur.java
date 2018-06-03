
import java.awt.*;
import java.util.*;


/**
 *
 */
public class Joueur {

    public Joueur(String name, ArrayList<Territoire> listTerritoires, ArrayList<Unite> listUnites, int nbUnites, ArrayList<Region> listRegions, Color couleur) {
        this.name = name;
        this.listTerritoires = listTerritoires;
        this.listUnites = listUnites;
        this.nbUnites = nbUnites;
        this.listRegions = listRegions;
        this.couleur = couleur;
    }

    private String name;
    public ArrayList<Territoire> listTerritoires;
    public ArrayList<Unite> listUnites;
    public int nbUnites;
    public ArrayList<Region> listRegions;
    public Color couleur;





    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }




    public void putUnite(Unite unit) {

        int positionx = unit.positionx;
        int positiony = unit.positiony;
        String countryname = Territoire.getCountryName(positionx, positiony);
        boolean possible = false;
        for (int i = 0; i < listTerritoires.size(); i++) {
            //System.out.println(countryname);
            //System.out.println(listTerritoires.get(i).getName());

            if (Objects.equals(countryname, listTerritoires.get(i).getName())) {
                possible = true;
            }
        }

        if (possible && (nbUnites - unit.cost) >= 0) {
            listUnites.add(unit);
            nbUnites -= unit.cost;
        }
    }


}