
import java.util.*;


/**
 *
 */
public class Joueur {

    private String name;
    public ArrayList<Territoire> listTerritoires;
    public ArrayList<Unite> listUnites;
    public int nbArmees;
    public ArrayList<Region> listRegions;


    public Joueur(String name, ArrayList<Territoire> listTerritoires, int nbArmees, ArrayList<Unite> listUnites) {
        this.name = name;
        this.listTerritoires = listTerritoires;
        this.nbArmees = nbArmees;
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