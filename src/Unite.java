import javax.swing.*;
import java.util.concurrent.ThreadLocalRandom;


public class Unite extends JPanel {



    int positionx;
    int positiony;
    int cost;
    private int minpower;
    private int maxpower;
    int priorityAttack;
    int priorityDefense;
    int mvtParTour;
    String imgpath;
    public Territoire territoire;
    int actualPower;


    public int mvtParTourDefault;


    public Unite(int positionx, int positiony, String imgpath, int cost, int minpower, int maxpower, int priorityAttack, int priorityDefense, int mvtParTour, int actualPower) {
        this.positionx = positionx;
        this.positiony = positiony;
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




    int getPower() {
        /*Obtenir la puissance d'une unité*/
        return ThreadLocalRandom.current().nextInt(this.minpower, this.maxpower + 1);

    }


}