
import java.util.*;


public class Unite {



    public int positionx;
    public int positiony;
    public int cost;
    public int minpower;
    public int maxpower;
    public int priorityAttack;
    public int priorityDefense;
    public int mvtParTour;
    public Territoire territoire;


    public Unite(int positionx, int positiony, Territoire territoire, int cost, int minpower, int maxpower, int priorityAttack, int priorityDefense, int mvtParTour) {
        this.positionx = positionx;
        this.positiony = positiony;
        this.territoire = territoire;
        this.cost = cost;
        this.minpower = minpower;
        this.maxpower = maxpower;
        this.priorityAttack = priorityAttack;
        this.priorityDefense = priorityDefense;
        this.mvtParTour = mvtParTour;
    }


    //ajouter la couleur






    public static Joueur attributionUnites(Joueur joueur) {
        int nbTerritoires = joueur.listTerritoires.size();
        joueur.nbUnites += nbTerritoires / 3;

        ArrayList<Region> regions = joueur.listRegions;
        for (int i = 0; i < regions.size(); i++) {
            joueur.nbUnites += regions.get(i).getWeight();
        }

        // Implémenter dernière ligne tableau (relire partie 3.2.1)

        return joueur;
    }

}