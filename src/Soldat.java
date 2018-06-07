import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;

/**
 *
 */
public class Soldat extends Unite {




    final static int mvtParTourDefault = 2;

    public Soldat(int positionx, int positiony) {

        super(positionx, positiony, "Soldat.png", 1, 1, 6, 2, 1, 2, 0);
    }

}
