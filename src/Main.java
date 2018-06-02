/**
 * Created by Alphonse on 14/05/2018.
 */


import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;


public class Main {

    public static void main(String[] args) {

        Partie.initGame();
        

        // A FAIRE : Condition d'arrêt du jeu : Joueur.listRegions.size() == 6
    }

    public static BufferedImage changeColor(BufferedImage image, Color couleur) {
        Color black = new Color(0, 0, 0);
        final int blackRGB = black.getRGB();
        final int colorRGB = couleur.getRGB();
        for (int x = 0; x < image.getWidth(); x++) {
            for (int y = 0; y < image.getHeight(); y++) {
                if (image.getRGB(x, y) == blackRGB) {
                    image.setRGB(x, y, colorRGB);
                }
            }
        }

        return image;
    }

    public static BufferedImage ImageReader(String pathname) {
        BufferedImage img = null;
        try {

            File imageFile1 = new File(pathname);
            img = ImageIO.read(imageFile1);

        } catch (IOException e) {

            e.printStackTrace();

        }
        return img;

    }

    public static void drawImage(String pathname, int x, int y, Graphics g) {
        BufferedImage img = ImageReader(pathname);
        g.drawImage(img, x, y, Map.imgobs);
    }



}
