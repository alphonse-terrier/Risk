/**
 * Created by Alphonse on 14/05/2018.
 */

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Objects;

public class Fenetre extends JFrame {
    public static ArrayList<Joueur> joueurs = Partie.initGame();
    public static String currentUnite = "Soldat";
    private static int numerojoueur = 0; //quand il clique sur passer le tour cette variable se change en (numerojoueur+1)%joueurs.size()
    public static Joueur currentJoueur = joueurs.get(numerojoueur);
    String tourOf;
    private JLabel joueurActif;
    private JLabel unitesRestantes;
    private JButton findutour;



    public Fenetre() {
        final int width = 1300;
        final int height = 690;

        this.setTitle("Jeu Risk par Aymeric Bès de Berc & Alphonse Terrier");

        this.setSize(width, height);

        this.setLocationRelativeTo(null);

        this.setResizable(false);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);


        Map map = new Map();
        this.setContentPane(map);
        joueurActif = new JLabel();
        unitesRestantes = new JLabel();
        findutour = new JButton();
        joueurActif.setText("C'est au tour de " + currentJoueur.getName() +".");
        joueurActif.setBounds(1000, 80, 170, 100);
        this.add(joueurActif);

        unitesRestantes.setText("Il reste " + currentJoueur.nbUnites + " unités à placer.");
        this.add(unitesRestantes);

        unitesRestantes.setBounds(1000, 150, 170, 100);
        findutour.setText("Finir mon tour");
        this.add(findutour);
        findutour.setBounds(1000, 250, 100, 40);


        findutour.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                if (currentJoueur.nbUnites == 0) {
                    numerojoueur = (numerojoueur + 1) % joueurs.size();
                    currentJoueur = joueurs.get(numerojoueur);
                    changeCursor("Soldat");
                    Partie.phasePartie = "Renforts";
                    joueurActif.setText("C'est au tour de " + currentJoueur.getName() + ".");
                }
            }
        });


        map.addMouseMotionListener(new NewMouseMotionListener() {
            @Override
            public void mouseMoved(MouseEvent event) {
                super.mouseMoved(event);
                int x = event.getX();
                if (Objects.equals(Partie.phasePartie, "Renforts")) {
                    if (x > 1000) {
                        setCursor(Cursor.getDefaultCursor());
                    } else {
                        changeCursor(currentUnite);

                    }
                }
            }
        });

        map.addMouseListener(new NewMouseListener() {
                                 @Override
                                 public void mouseClicked(MouseEvent event) {
                                     super.mouseClicked(event);
                                     int x = event.getX();
                                     int y = event.getY();

                                     if (x < 1000) {
                                         if (event.getButton() == MouseEvent.BUTTON1) {

                                             //Instaurer une condition pour passer en mode attaque (clique sur le bouton en bas à droite)


                                             if (Objects.equals(Partie.phasePartie, "Attaque")) {
                                                 repaint();


                                             }


                                             if (Objects.equals(Partie.phasePartie, "Déplacement")) {
                                                 String country = Territoire.getCountryName(x, y);
                                                 if (Territoire.checkIfThisIsOneOfMyCountry(currentJoueur, country) && Territoire.areTheseCountriesAdjacents(country, Territoire.getCountryName(Unite.SelectionUnite.get(0).positionx, Unite.SelectionUnite.get(0).positiony))) {

                                                     Unite.SelectionUnite.get(0).positionx = x - Map.x_adapt;
                                                     Unite.SelectionUnite.get(0).positiony = y - Map.x_adapt;
                                                     Unite.SelectionUnite.remove(0);
                                                     Partie.phasePartie = "NewSélection";
                                                     repaint();
                                                 }


                                             }

                                             if (Objects.equals(Partie.phasePartie, "Sélection")) {
                                                 if (Unite.checkIfDeplacementIsPossible(currentJoueur, x, y) != null) {
                                                     Partie.phasePartie = "Déplacement";
                                                 }

                                                 repaint();
                                             }

                                             if (Objects.equals(Partie.phasePartie, "NewSélection")) {
                                                 Partie.phasePartie = "Sélection";
                                             }


                                             if (Objects.equals(Partie.phasePartie, "Renforts")) {
                                                 if (Objects.equals("Soldat", currentUnite)) {
                                                     currentJoueur.putUnite(new Soldat(x, y));
                                                 }
                                                 if (Objects.equals("Canon", currentUnite)) {
                                                     currentJoueur.putUnite(new Canon(x, y));

                                                 }
                                                 if (Objects.equals("Cavalier", currentUnite)) {
                                                     currentJoueur.putUnite(new Cavalier(x, y));

                                                 }
                                                 System.out.println(currentJoueur.nbUnites);

                                                 unitesRestantes.setText("Il reste " + currentJoueur.nbUnites + " unités.");

                                                 repaint();

                                                 if (currentJoueur.nbUnites == 0) {
                                                     Partie.phasePartie = "Sélection";
                                                     setCursor(Cursor.getDefaultCursor());
                                                 }

                                             }

                                         }


                                         if (event.getButton() == MouseEvent.BUTTON3) {

                                             if (Objects.equals(Partie.phasePartie, "Renforts")) {
                                                 if (Objects.equals("Soldat", currentUnite)) {
                                                     changeCursor("Canon");
                                                 } else if (Objects.equals("Canon", currentUnite)) {
                                                     changeCursor("Cavalier");
                                                 } else if (Objects.equals("Cavalier", currentUnite)) {
                                                     changeCursor("Soldat");
                                                 }
                                             }
                                         }

                                     }
                                 }

                                 public void mouseMoved(MouseEvent event) {
                                     int x = event.getX();
                                     int y = event.getY();
                                     if (x > 1000) {
                                         setCursor(Cursor.getDefaultCursor());
                                     } else {
                                         changeCursor(currentUnite);

                                     }

                                 }

                             }


        );


    }

    public void changeCursor(String pathname) {
        BufferedImage icone = Main.ImageReader(pathname + ".png");
        icone = Main.changeColor(icone, currentJoueur.couleur);
        setCursor(Toolkit.getDefaultToolkit().createCustomCursor(new ImageIcon(icone).getImage(), new Point(0, 0), "Curseur"));
        currentUnite = pathname;
    }

}

abstract class NewMouseListener implements MouseListener {

    public void mouseClicked(MouseEvent event) {


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

abstract class NewMouseMotionListener implements MouseMotionListener {

    public void mouseMoved(MouseEvent event) {
    }

    public void mouseDragged(MouseEvent event) {
    }
}

