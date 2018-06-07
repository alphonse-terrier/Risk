import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;



public class Main {


    public static void main(String[] args) {

        Fenetre carte = new Fenetre();

    }

    static BufferedImage changeColor(BufferedImage image, Color couleur) {
        /* Permet de changer la couleur d'une image noire */
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

    static BufferedImage ImageReader(String pathname) {
        /* Permet de lire une image*/

        BufferedImage img = null;
        try {

            File imageFile1 = new File(pathname);
            img = ImageIO.read(imageFile1);

        } catch (IOException e) {

            e.printStackTrace();

        }
        return img;

    }


    static void drawImage(BufferedImage img, int x, int y, Graphics g) {
        /* Permet de dessiner une image sur le JPanel */


        g.drawImage(img, x, y, Map.imgobs);
    }

    static BufferedReader readTextFile(String pathname) {
        /* Permet de lire un fichier texte */
        BufferedReader txt = null;
        try {
            txt = new BufferedReader(new FileReader(pathname));


        } catch (Exception e) {
            e.printStackTrace();
        }
        return txt;
    }



}
