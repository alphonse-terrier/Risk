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
    public static String imgpath = "Soldat.png";

    public int getMvtParTourDefault() {
        return mvtParTourDefault;
    }

    static int mvtParTourDefault = 2;

    public Soldat(int positionx, int positiony) {

        super(positionx, positiony, imgpath, 1, 1, 6, 2, 1, 2);
    }

}
