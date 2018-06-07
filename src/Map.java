/**
 * Created by Alphonse on 14/05/2018.
 */

import javax.swing.*;
import java.awt.*;
import java.awt.image.ImageObserver;
import java.util.ArrayList;


public class Map extends JPanel {
    public Joueur currentJoueur;
    public ArrayList<Joueur> joueurs;
    public Partie partie;

    public Map(ArrayList<Joueur> joueurs, Joueur currentJoueur, Partie partie) {
        this.joueurs = joueurs;
        this.currentJoueur = currentJoueur;
        this.partie = partie;

    }


    public int x_adapt = 15;
    public int y_adapt = 15;


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
        System.out.println(currentJoueur.getName());
        System.out.println(currentJoueur.couleur);
        Main.drawImage(Main.changeColor(Main.ImageReader("iconejoueur.png"), Fenetre.currentJoueur.couleur), 1050, 50, g);

        for (Joueur joueur : joueurs) {
            if (!joueur.listUnites.isEmpty()) {
            for (Unite unite : joueur.listUnites) {
                    Main.drawImage(Main.changeColor(Main.ImageReader(unite.imgpath), joueur.couleur), unite.positionx, unite.positiony, g);
                }
            }
        }


        if (!partie.SelectionUnite.isEmpty()) {
        for (Unite unite : partie.SelectionUnite) {

                Main.drawImage(Main.changeColor(Main.ImageReader(unite.imgpath), new Color(255, 255, 255)), unite.positionx, unite.positiony, g);
            }

        }

    }


}


