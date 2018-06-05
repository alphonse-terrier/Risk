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
    public static Joueur currentJoueur;
    private static int numerojoueur; //quand il clique sur passer le tour cette variable se change en (numerojoueur+1)%joueurs.size()
    private JLabel joueurActif;
    private JLabel unitesRestantes;
    private JButton findutour;
    private JButton unselection;


    public Fenetre() {
        final int width = 1300;
        final int height = 690;
        this.setTitle("Jeu Risk par Aymeric Bès de Berc & Alphonse Terrier");
        this.setSize(width, height);
        this.setLocationRelativeTo(null);
        this.setResizable(false);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);

        numerojoueur = 0;
        currentJoueur = joueurs.get(numerojoueur);
        changeCursor("Soldat");

        Map map = new Map();
        this.setContentPane(map);
        map.setLayout(null);
        joueurActif = new JLabel();
        unitesRestantes = new JLabel();
        findutour = new JButton();
        unselection = new JButton();

        joueurActif.setText("C'est au tour de " + currentJoueur.getName() + ".");
        joueurActif.setFont(new Font("Verdana", 1, 13));
        joueurActif.setBounds(1000, 100, 240, 100);
        this.add(joueurActif);

        unitesRestantes.setText("Il reste " + currentJoueur.nbUnites + " unités à placer.");
        unitesRestantes.setFont(new Font("Verdana", 1, 13));
        unitesRestantes.setBounds(1000, 140, 200, 100);
        this.add(unitesRestantes);

        findutour.setText("Finir mon tour");

        this.add(findutour);
        findutour.setBounds(1000, 320, 150, 40);
        findutour.setVisible(false);

        unselection.setText("Désélectionner");
        unselection.setBounds(1000, 220, 150, 40);
        unselection.setVisible(false);
        this.add(unselection);
        
        try {
            Robot r = new Robot();
            r.keyPress(KeyEvent.VK_ENTER);
        } catch (AWTException e) {
            e.printStackTrace();
        }


        this.addKeyListener(new NewKeyListener() {
            @Override
            public void keyPressed(KeyEvent event) {

                Partie.KonamiCode(event);


            }

        });

        findutour.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                if (Partie.checkIfWin(currentJoueur)) {
                    repaint();
                }

                if (currentJoueur.nbUnites == 0) {
                    currentJoueur = changePlayer(currentJoueur);
                    Partie.phasePartie = "Renforts";

                    for (int i = 0; i < currentJoueur.listUnites.size(); i++) {
                        if (currentJoueur.listUnites.get(i).getClass().getName() == "Soldat") {
                            currentJoueur.listUnites.get(i).mvtParTour = Soldat.mvtParTourDefault;
                        } else if (currentJoueur.listUnites.get(i).getClass().getName() == "Cavalier") {
                            currentJoueur.listUnites.get(i).mvtParTour = Cavalier.mvtParTourDefault;
                        } else {
                            currentJoueur.listUnites.get(i).mvtParTour = Canon.mvtParTourDefault;
                        }

                    }


                    currentJoueur = Unite.attributionUnites(currentJoueur);
                    currentJoueur.nbTerritoiresCapturéesTourPréc = 0;

                    unitesRestantes.setText("Il reste " + currentJoueur.nbUnites + " unités à placer.");
                    joueurActif.setText("C'est au tour de " + currentJoueur.getName() + ".");
                    repaint();


                }
            }
        });

        unselection.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                if (Unite.SelectionUnite.size() > 0) {
                    for (int i = 0; i < Unite.SelectionUnite.size(); i++) {
                        Unite.SelectionUnite.remove(i);
                        unselection.setVisible(false);
                        Partie.phasePartie = "Sélection";
                        repaint();
                    }
                }

            }
        });


        map.addMouseMotionListener(new NewMouseMotionListener() {
            @Override
            public void mouseMoved(MouseEvent event) {
                super.mouseMoved(event);
                int x = event.getX();
                if (Objects.equals(Partie.phasePartie, "Renforts") || Objects.equals(Partie.phasePartie, "PoseUnites")) {
                    if (x > 960) {
                        setCursor(Cursor.getDefaultCursor());
                    } else {
                        changeCursor(currentUnite);
                    }
                }
            }
        });

        map.addMouseListener(new NewMouseListener() {
                                 @Override
                                 public void mousePressed(MouseEvent event) {
                                     super.mouseClicked(event);
                                     int x = event.getX();
                                     int y = event.getY();

                                     if (x < 1000) {
                                         if (event.getButton() == MouseEvent.BUTTON1) {

                                             if (Objects.equals(Partie.phasePartie, "Déplacement")) {
                                                 if (Unite.checkIfDeplacementIsPossible(currentJoueur, x, y) == null) {
                                                     for (int i = Unite.SelectionUnite.size() - 1; i >= 0; i--) {

                                                         String countryToConquest = Territoire.getCountryName(x, y);
                                                         String countryOfTheUnit = Territoire.getCountryName(Unite.SelectionUnite.get(i).positionx, Unite.SelectionUnite.get(i).positiony);
                                                         if (Territoire.areTheseCountriesAdjacents(countryToConquest, countryOfTheUnit)) {
                                                             if (Territoire.checkIfThisIsOneOfMyCountry(currentJoueur, countryToConquest)) {
                                                                 if (Unite.SelectionUnite.get(i).mvtParTour > 0) {
                                                                     Unite.SelectionUnite.get(i).positionx = x - Map.x_adapt;
                                                                     Unite.SelectionUnite.get(i).positiony = y - Map.x_adapt;
                                                                     Unite.SelectionUnite.get(i).mvtParTour -= 1;
                                                                 }
                                                             } else {
                                                                 if (Partie.attaque(currentJoueur, countryToConquest)) {
                                                                     for (int j = 0; j < Unite.SelectionUnite.size(); j++) {
                                                                         Unite.SelectionUnite.get(j).positionx = x - Map.x_adapt;
                                                                         Unite.SelectionUnite.get(j).positiony = y - Map.x_adapt;
                                                                         Unite.SelectionUnite.get(j).mvtParTour -= 1;
                                                                     }
                                                                     Unite.SelectionUnite = new ArrayList<Unite>();
                                                                 }

                                                                 break;
                                                             }


                                                         }

                                                         if (Territoire.areTheseCountriesTheSame(countryToConquest, Territoire.getCountryName(Unite.SelectionUnite.get(0).positionx, Unite.SelectionUnite.get(0).positiony))) {

                                                             Unite.SelectionUnite.get(i).positionx = x - Map.x_adapt;
                                                             Unite.SelectionUnite.get(i).positiony = y - Map.x_adapt;

                                                         }


                                                         Unite.SelectionUnite.remove(i);


                                                     }
                                                     Partie.phasePartie = "NewSélection";
                                                     System.out.println(Partie.phasePartie);
                                                     unitesRestantes.setText("Il reste " + currentJoueur.nbUnites + " unités à placer.");
                                                     joueurActif.setText("C'est au tour de " + currentJoueur.getName() + ".");

                                                 }


                                                 repaint();
                                             }

                                             if (Objects.equals(Partie.phasePartie, "Sélection")) {
                                                 if (Unite.checkIfDeplacementIsPossible(currentJoueur, x, y) != null) {
                                                     unselection.setVisible(true);
                                                     Partie.phasePartie = "Déplacement";
                                                 }

                                                 repaint();
                                             }

                                             if (Objects.equals(Partie.phasePartie, "NewSélection")) {
                                                 Partie.phasePartie = "Sélection";
                                             }

                                             if (Objects.equals(Partie.phasePartie, "Renforts")) {
                                                 unselection.setVisible(false);
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
                                                     findutour.setVisible(true);
                                                     setCursor(Cursor.getDefaultCursor());
                                                 }

                                             }

                                             if (Objects.equals(Partie.phasePartie, "PoseUnites") && Territoire.checkIfThisIsOneOfMyCountry(currentJoueur, Territoire.getCountryName(x, y))) {
                                                 unselection.setVisible(false);
                                                 int wasAnUnitPut = 0;
                                                 if (currentJoueur.nbUnites != 0) {
                                                     while (wasAnUnitPut == 0) {
                                                         if (Objects.equals("Soldat", currentUnite)) {
                                                             if (currentJoueur.putUnite(new Soldat(x, y))) {
                                                                 wasAnUnitPut = 1;
                                                             }
                                                         }
                                                         if (Objects.equals("Canon", currentUnite)) {
                                                             if (currentJoueur.putUnite(new Canon(x, y))) {
                                                                 wasAnUnitPut = 1;
                                                             } else {
                                                                 changeCursor("Soldat");
                                                             }

                                                         }
                                                         if (Objects.equals("Cavalier", currentUnite)) {
                                                             if (currentJoueur.putUnite(new Cavalier(x, y))) {
                                                                 wasAnUnitPut = 1;
                                                             } else {
                                                                 changeCursor("Soldat");
                                                             }

                                                         }
                                                     }

                                                 } else {
                                                     currentUnite = "Soldat";
                                                 }

                                                 currentJoueur = changePlayer(currentJoueur);
                                                 changeCursor(currentUnite);
                                                 int nbUnitesTotal = 0;
                                                 for (int i = 0; i < joueurs.size(); i++) {
                                                     nbUnitesTotal += joueurs.get(i).nbUnites;
                                                 }

                                                 if (nbUnitesTotal == 0) {
                                                     findutour.setVisible(true);
                                                     Partie.phasePartie = "Sélection";
                                                     setCursor(Cursor.getDefaultCursor());
                                                 } else {
                                                     while (currentJoueur.nbUnites == 0) {
                                                         currentJoueur = changePlayer(currentJoueur);
                                                         changeCursor(currentUnite);
                                                     }
                                                 }

                                                 repaint();

                                             }

                                         }


                                         if (event.getButton() == MouseEvent.BUTTON3) {

                                             if (Objects.equals(Partie.phasePartie, "Renforts") || Objects.equals(Partie.phasePartie, "PoseUnites")) {
                                                 findutour.setVisible(false);


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

    public Joueur changePlayer(Joueur currentJoueur) {
        numerojoueur = (numerojoueur + 1) % joueurs.size();
        currentJoueur = joueurs.get(numerojoueur);
        unitesRestantes.setText("Il reste " + currentJoueur.nbUnites + " unités.");
        joueurActif.setText("C'est au tour de " + currentJoueur.getName() + ".");
        return currentJoueur;
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

abstract class NewKeyListener implements KeyListener {

    public void keyTyped(KeyEvent e) {

    }

    public void keyPressed(KeyEvent e) {

    }

    public void keyReleased(KeyEvent e) {

    }
}

