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


        Main.drawImage(Main.ImageReader("./Terre/liaisons.png"), x_adapt, y_adapt, g);
        Main.drawImage(Main.ImageReader("./Terre/countriesborders.png"), x_adapt, y_adapt, g);
        Main.drawImage(Main.changeColor(Main.ImageReader("iconejoueur.png"), Fenetre.currentJoueur.couleur), 1050, 50, g);

        for (int x = 0; x < Fenetre.joueurs.size(); x++) {
            for (int y = 0; y < Fenetre.joueurs.get(x).listUnites.size(); y++) {
                if (!Fenetre.joueurs.get(x).listUnites.isEmpty()) {

                    Main.drawImage(Main.changeColor(Main.ImageReader(Fenetre.joueurs.get(x).listUnites.get(y).imgpath), Fenetre.joueurs.get(x).couleur), Fenetre.joueurs.get(x).listUnites.get(y).positionx, Fenetre.joueurs.get(x).listUnites.get(y).positiony, g);
                }
            }
        }




        for (int a = 0; a < Unite.SelectionUnite.size(); a++) {
            if (!Unite.SelectionUnite.isEmpty()) {
                Main.drawImage(Main.changeColor(Main.ImageReader(Unite.SelectionUnite.get(a).imgpath), new Color(255, 255, 255)), Unite.SelectionUnite.get(a).positionx, Unite.SelectionUnite.get(a).positiony, g);
            }

        }

    }


}


