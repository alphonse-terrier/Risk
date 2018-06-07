public class Cavalier extends Unite {

    /**Classe du cavalier qui hérite de Unité*/

    final static int mvtParTourDefault = 3;

    public Cavalier(int positionx, int positiony) {
        super(positionx, positiony, "cavalier.png", 3, 2, 7, 1, 3, 3, 0);
    }

}