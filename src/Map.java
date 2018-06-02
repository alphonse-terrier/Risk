/**
 * Created by Alphonse on 14/05/2018.
 */

import javax.swing.*;
import java.awt.*;
import java.awt.image.ImageObserver;


public class Map extends JPanel {

    public static int x_adapt = 15;
    public static int y_adapt = 15;

    public static ImageObserver imgobs = new ImageObserver() {
        @Override
        public boolean imageUpdate(Image img, int infoflags, int x, int y, int width, int height) {
            return false;
        }
    };

    public void paintComponent(Graphics g) {
        super.paintComponent(g);


        Main.drawImage("countriesborders.png", x_adapt, y_adapt, g);
/**/
        for (int x = 0; x < Fenetre.joueurs.size(); x++) {
            for (int y = 0; y < Fenetre.joueurs.get(x).listUnites.size(); y++) {
                if (Fenetre.joueurs.get(x).listUnites.isEmpty() == false){

                    Main.drawImage(Fenetre.joueurs.get(x).listUnites.get(y).imgpath, Fenetre.joueurs.get(x).listUnites.get(y).positionx, Fenetre.joueurs.get(x).listUnites.get(y).positiony, g);
                }
            }
        }


    }
}


