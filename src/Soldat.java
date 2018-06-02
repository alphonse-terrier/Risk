import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;

/**
 *
 */
public class Soldat extends Unite {

    /**
     * Default constructor
     */



    public Soldat(int positionx, int positiony, Territoire territoire) {

        super(positionx, positiony, territoire, new File("soldat.png"), 1, 1, 6, 2, 1, 2);
    }

}
