import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;


public class Unite extends JPanel {


    public static ArrayList<Unite> SelectionUnite = new ArrayList<Unite>();
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
    public int actualPower;


    public int mvtParTourDefault;



    public Unite(int positionx, int positiony, String imgpath, int cost, int minpower, int maxpower, int priorityAttack, int priorityDefense, int mvtParTour, int actualPower) {
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
        this.actualPower = actualPower;
    }


    //ajouter la couleur


    public static Joueur attributionUnites(Joueur joueur) {
        int nbTerritoires = joueur.listTerritoires.size();
        joueur.nbUnites += nbTerritoires / 3;

        ArrayList<Region> regions = Region.getRegions(joueur.listTerritoires);
        if (regions.size() > 0) {
        for (int i = 0; i < regions.size(); i++) {
            joueur.nbUnites += regions.get(i).getWeight();
        }}

        Random rand = new Random();
        for (int i=0; i < joueur.nbTerritoiresCapturéesTourPréc; i++){
            if ((rand.nextInt(10) + 1)>5) {
                joueur.nbUnites += 1;
            }
        }


        if (joueur.nbUnites < 2) {
            joueur.nbUnites = 2;
        }
        return joueur;
    }

    public static ArrayList<Unite> getAllUnitsinTerritoire(String countryName, ArrayList<Unite> AllUnits) {
        ArrayList<Unite> AllUnitsinTerritoire = new ArrayList<Unite>();
        for (int i = 0; i < AllUnits.size(); i++) {
            if (Objects.equals(Territoire.getCountryName(AllUnits.get(i).positionx, AllUnits.get(i).positiony), countryName)) {
                AllUnitsinTerritoire.add(AllUnits.get(i));
            }
        }

        return AllUnitsinTerritoire;
    }

    public static Unite checkIfDeplacementIsPossible(Joueur joueur, int x, int y) {

        ArrayList<Unite> allUnits = getAllUnitsinTerritoire(Territoire.getCountryName(x, y), joueur.listUnites);

        if (SelectionUnite.size()+1 == allUnits.size()) {
            return null;
        }

        if (allUnits.size() > 1 && SelectionUnite.size() < 3) {

            for (int i = 0; i < allUnits.size(); i++) {
                Unite unite = allUnits.get(i);
                String classOfUnite = unite.getClass().getName();
                int uniteX = unite.positionx;
                int uniteY = unite.positiony;
                BufferedImage imgOfUnite = Main.ImageReader(classOfUnite + ".png");
                //System.out.println("x - uniteX : " + (x - uniteX + Map.x_adapt) + ", y - uniteY : " + (y - uniteY + Map.y_adapt));

                int xToCheck = x - uniteX;
                int yToCheck = y - uniteY;

                if (0 <= xToCheck && xToCheck <= 32 && 0 <= yToCheck && yToCheck <= 32) {
                    Color color = new Color(imgOfUnite.getRGB(xToCheck, yToCheck));
                    int red = color.getRed();
                    int green = color.getGreen();
                    int blue = color.getBlue();
                    if (red < 255 || green < 255 || blue < 255) {
                        for (int j = 0; j < SelectionUnite.size(); j++) {
                            if (Objects.equals(unite, SelectionUnite.get(j))) {
                                return null;
                            }

                        }

                        SelectionUnite.add(unite);
                        return unite;
                    }
                }

            }
        }

        return null;


    }

    public int getPower() {
        int power = ThreadLocalRandom.current().nextInt(this.minpower, this.maxpower + 1);
        return power;
    }


}