
import java.util.*;


/**
 *
 */
public class Joueur {

    private String name;
    public ArrayList<Territoire> listTerritoires;
    public ArrayList<Unite> listUnites;
    public int nbUnites;
    public ArrayList<Region> listRegions;


    public Joueur(String name, ArrayList<Territoire> listTerritoires, int nbUnites, ArrayList<Unite> listUnites) {
        this.name = name;
        this.listTerritoires = listTerritoires;
        this.nbUnites = nbUnites;
        this.listUnites = listUnites;
        this.listRegions = Region.getRegions(listTerritoires);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


}