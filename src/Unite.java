import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;
import java.util.concurrent.ThreadLocalRandom;


public class Unite extends JPanel {


    public int positionx;
    public int positiony;
    public int cost;
    public int minpower;
    public int maxpower;
    public int priorityAttack;
    public int priorityDefense;
    public int mvtParTour;
    public String imgpath;
    public Territoire territoire;




    public Unite(int positionx, int positiony, String imgpath, int cost, int minpower, int maxpower, int priorityAttack, int priorityDefense, int mvtParTour) {
        this.positionx = positionx;
        this.positiony = positiony;
        //this.territoire = territoire; //dépend de x et y, coder la fonction pour get le Territoire
        this.imgpath = imgpath;
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

    public static ArrayList<Unite> getAllUnitsinTerritoire(String countryName, ArrayList<Unite> AllUnits) {
        ArrayList<Unite> AllUnitsinTerritoire = new ArrayList<Unite>();
        for (int i = 0; i < 0; i++) {
            if (Objects.equals(Territoire.getCountryName(AllUnits.get(i).positionx, AllUnits.get(i).positiony), countryName)) {
                AllUnitsinTerritoire.add(AllUnits.get(i));
            }
        }

        return AllUnitsinTerritoire;
    }

    public int getPower(int minpower, int maxpower) {
        int power = ThreadLocalRandom.current().nextInt(minpower, maxpower + 1);
        return power;
    }



}