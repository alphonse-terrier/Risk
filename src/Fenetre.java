/**
 * Created by Alphonse on 14/05/2018.
 */

import javax.swing.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.Objects;

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
                //System.out.println(countryClicked);
                //joueurs.get(0).listUnites.remove(0);
                for (int i = 0; i < joueurs.get(0).listTerritoires.size(); i++) {
                    String countryName = joueurs.get(0).listTerritoires.get(i).getName();
                    if(Objects.equals(countryName, countryClicked) && joueurs.get(0).nbUnites > 0) {
                        joueurs.get(0).putUnite(new Soldat(x, y));
                        System.out.println(joueurs.get(0).nbUnites);
                    }
                }
                //System.out.println("x : "+ x +", y : " + y);
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


