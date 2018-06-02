
import java.io.File;
import java.util.*;

/**
 * 
 */
public class Canon extends Unite {

    /**
     * Default constructor
     */
    public Canon(int positionx, int positiony, Territoire territoire) {
        super(positionx, positiony, territoire, new File("canon.png"),7, 4,9, 3, 2, 1);
    }



}