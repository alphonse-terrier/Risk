
import java.io.File;
import java.util.*;


public class Cavalier extends Unite {


    public Cavalier(int positionx, int positiony, Territoire territoire) {
        super(positionx, positiony, territoire, new File("cavalier.png"),3, 2, 7, 1, 3, 3);
    }

}