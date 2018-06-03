/**
 * Created by Alphonse on 14/05/2018.
 */

import javax.swing.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

public class Fenetre extends JFrame {
    public static ArrayList<Joueur> joueurs = Partie.initGame();
    public Fenetre() {


        this.setTitle("Jeu Risk par Aymeric Bès de Berc & Alphonse Terrier");

        this.setSize(1000, 690);

        this.setLocationRelativeTo(null);

        this.setResizable(false);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);


        Map map = new Map();
        this.setContentPane(map);
        map.addMouseListener(new NewMouseListener() {
            @Override
            public void mouseClicked(MouseEvent event) {
                super.mouseClicked(event);
                int x = event.getX()- Map.x_adapt;
                int y = event.getY() - Map.y_adapt;
                String countryClicked = Territoire.getCountryName(x, y);
                System.out.println(countryClicked);
                //joueurs.get(0).listUnites.remove(0);
                joueurs.get(0).putUnite(new Soldat(x, y));
                System.out.println("x : "+ x +", y : " + y);
                // Implémenter une fonction qui agit au clic et qui modifie la liste de toutes les units sous certaines conditions (la liste permettra la lecture des images dans Map)
                // https://stackoverflow.com/questions/35299786/draw-circle-on-jpanel-after-mouse-click
                repaint();

            }
        });



    }


}

abstract class NewMouseListener implements MouseListener {

    public void mouseClicked(MouseEvent event) {
        int x = event.getX()- Map.x_adapt;
        int y = event.getY() - Map.y_adapt;




    }

    public void mouseEntered(MouseEvent event) {
    }

    public void mouseExited(MouseEvent event) {
    }

    public void mousePressed(MouseEvent event) {
    }

    public void mouseReleased(MouseEvent event) {
    }
}


