import javax.swing.*;
import java.awt.*;
import java.awt.image.ImageObserver;
import java.util.ArrayList;


public class Map extends JPanel {

    public static ImageObserver imgobs = new ImageObserver() {
        @Override
        public boolean imageUpdate(Image img, int infoflags, int x, int y, int width, int height) {

            return false;
        }
    };
    /*
     * Permet de dessiner la carte dans la JFrame
     */
    public ArrayList<Joueur> joueurs;
    public Partie partie;
    //coordonnées x et y du début de la carte
    public int x_adapt = 15;
    public int y_adapt = 15;


    public Map(ArrayList<Joueur> joueurs, Partie partie) {
        this.joueurs = joueurs;
        this.partie = partie;

    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        //lecture et placement de la carte des liaisons et de l'icone du joueur
        Main.drawImage(Main.ImageReader("./Terre/liaisons.png"), x_adapt, y_adapt, g);
        Main.drawImage(Main.ImageReader("./Terre/countriesborders.png"), x_adapt, y_adapt, g);
        Main.drawImage(Main.changeColor(Main.ImageReader("iconejoueur.png"), Fenetre.currentJoueur.couleur), 1050, 50, g);

        //affichage des unités sur le plateau
        for (Joueur joueur : joueurs) {
            if (!joueur.listUnites.isEmpty()) {
                for (Unite unite : joueur.listUnites) {
                    Main.drawImage(Main.changeColor(Main.ImageReader(unite.imgpath), joueur.couleur), unite.positionx, unite.positiony, g);
                }
            }
        }

        //affiche en blanc la ou les unité(s) sélectionnée(s)
        if (!partie.SelectionUnite.isEmpty()) {
            for (Unite unite : partie.SelectionUnite) {

                Main.drawImage(Main.changeColor(Main.ImageReader(unite.imgpath), new Color(255, 255, 255)), unite.positionx, unite.positiony, g);
            }

        }

    }


}


