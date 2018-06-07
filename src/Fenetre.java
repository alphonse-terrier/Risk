import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Objects;

class Fenetre extends JFrame {
    public static Joueur currentJoueur;
    private ArrayList<Joueur> joueurs;
    private String currentUnite = "Soldat";
    private int Konami = 0;
    private Partie partie;
    private Map map;
    private String phasePartie;
    private int numerojoueur;
    private JLabel joueurActif;
    private JLabel unitesRestantes;
    private JButton findutour;
    private JButton unselection;


    Fenetre() {
        //Initialisation du jeu
        partie = new Partie();
        joueurs = partie.initGame();


        //Initialisation fen^tre
        final int width = 1300;
        final int height = 690;
        this.setTitle("Jeu Risk par Aymeric Bès de Berc & Alphonse Terrier");
        this.setSize(width, height);
        this.setLocationRelativeTo(null);
        this.setResizable(false);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
        phasePartie = "PoseUnites";
        numerojoueur = 0;
        currentJoueur = joueurs.get(numerojoueur);
        changeCursor("Soldat");

        //Création de la carte
        map = new Map(joueurs, partie);
        this.setContentPane(map);
        map.setLayout(null);

        //Création des données de la sidebar
        joueurActif = new JLabel();
        unitesRestantes = new JLabel();
        findutour = new JButton();
        unselection = new JButton();


        joueurActif.setFont(new Font("Verdana", 1, 13));
        joueurActif.setBounds(1000, 100, 240, 100);
        this.add(joueurActif);


        unitesRestantes.setFont(new Font("Verdana", 1, 13));
        unitesRestantes.setBounds(1000, 140, 200, 100);
        this.add(unitesRestantes);
        updateJLabel();
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
                //Listener pour le code Konami
                System.out.println(event.getKeyCode());
                konamiCode(event);


            }

        });

        findutour.addActionListener(new ActionListener() {
            //Listener du bouton fin de tour
            public void actionPerformed(ActionEvent ae) {
                if (partie.checkIfWin(currentJoueur)) {
//Teste si le joueur a gagné
                    repaint();

                }

                if (currentJoueur.nbUnites == 0) {

                    //permet d'éliminer un joueur
                    while (currentJoueur.listTerritoires.size() == 0) {
                        currentJoueur = changePlayer();
                    }


                    phasePartie = "Renforts"; //Changement de phase de jeu
                    currentJoueur = changePlayer(); //Changmeent de joueur

                    updateJLabel();

                    //On remet les mouvements par tour à leurs valeurs par défaut
                    if (currentJoueur.listUnites.size() > 0) {
                        for (Unite unite : currentJoueur.listUnites) {
                            if (Objects.equals(unite.getClass().getName(), "Soldat")) {
                                unite.mvtParTour = Soldat.mvtParTourDefault;
                            } else if (Objects.equals(unite.getClass().getName(), "Cavalier")) {
                                unite.mvtParTour = Cavalier.mvtParTourDefault;
                            } else {
                                unite.mvtParTour = Canon.mvtParTourDefault;
                            }

                        }
                    }

                    //On attribue les unités au joueur
                    currentJoueur = partie.attributionUnites(currentJoueur);
                    currentJoueur.nbTerritoiresCapturesTourPrec = 0;
                    unitesRestantes.setText("Il reste " + currentJoueur.nbUnites + " unités.");
                    joueurActif.setText("C'est au tour de " + currentJoueur.getName() + ".");
                    System.out.println(currentJoueur.getName());

                    updateJLabel();
                    repaint();


                }
            }
        });

        unselection.addActionListener(new ActionListener() {
            //Listener qui permet de désélectionner des unités
            public void actionPerformed(ActionEvent ae) {
                if (partie.SelectionUnite.size() > 0) {
                    for (int i = 0; i < partie.SelectionUnite.size(); i++) {
                        partie.SelectionUnite.remove(i);
                        unselection.setVisible(false);
                        phasePartie = "Sélection";

                        repaint();
                    }
                }

            }
        });


        map.addMouseMotionListener(new NewMouseMotionListener() {
            @Override
            public void mouseMoved(MouseEvent event) {
                //Listener qui détecte le mouvement de la souris
                super.mouseMoved(event);
                int x = event.getX();
                if (Objects.equals(phasePartie, "Renforts") || Objects.equals(phasePartie, "PoseUnites")) {
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

                                             if (Objects.equals(phasePartie, "Déplacement")) {
                                                 if (partie.checkIfDeplacementIsPossible(currentJoueur, x, y) == null) {
                                                     for (int i = partie.SelectionUnite.size() - 1; i >= 0; i--) {

                                                         String countryToConquest = Partie.getCountryName(x, y);
                                                         String countryOfTheUnit = Partie.getCountryName(partie.SelectionUnite.get(i).positionx, partie.SelectionUnite.get(i).positiony);
                                                         if (partie.areTheseCountriesAdjacents(countryToConquest, countryOfTheUnit)) {
                                                             if (partie.checkIfThisIsOneOfMyCountry(currentJoueur, countryToConquest)) { //Déplacement
                                                                 if (partie.SelectionUnite.get(i).mvtParTour > 0) {
                                                                     partie.SelectionUnite.get(i).positionx = x - map.x_adapt;
                                                                     partie.SelectionUnite.get(i).positiony = y - map.x_adapt;
                                                                     partie.SelectionUnite.get(i).mvtParTour -= 1;
                                                                 }
                                                             } else { //Attaque si le pays cliqué n'appartient pas au joueur
                                                                 if (partie.attaque(currentJoueur, countryToConquest, joueurs)) {
                                                                     partie.SelectionUnite = new ArrayList<>();

                                                                 }

                                                                 break;
                                                             }


                                                         }

                                                         if (partie.areTheseCountriesTheSame(countryToConquest, Partie.getCountryName(partie.SelectionUnite.get(0).positionx, partie.SelectionUnite.get(0).positiony))) {

                                                             partie.SelectionUnite.get(i).positionx = x - map.x_adapt;
                                                             partie.SelectionUnite.get(i).positiony = y - map.x_adapt;

                                                         }


                                                         partie.SelectionUnite.remove(i);


                                                     }
                                                     phasePartie = "NewSélection"; //On démarre une nouvelle sélection
                                                     System.out.println(phasePartie);
                                                     updateJLabel();

                                                 }


                                                 repaint();
                                             }

                                             if (Objects.equals(phasePartie, "Sélection")) {
                                                 if (partie.checkIfDeplacementIsPossible(currentJoueur, x, y) != null) {//On regarde si l'unité cliquée peut se déplacer
                                                     unselection.setVisible(true);
                                                     phasePartie = "Déplacement";
                                                 }

                                                 repaint();
                                             }

                                             if (Objects.equals(phasePartie, "NewSélection")) {
                                                 phasePartie = "Sélection";
                                             }

                                             if (Objects.equals(phasePartie, "Renforts")) { //Phase de renforts
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


                                                 updateJLabel();

                                                 repaint();

                                                 if (currentJoueur.nbUnites == 0) { //Si plus d'unités : Sélection
                                                     phasePartie = "Sélection";
                                                     findutour.setVisible(true);
                                                     setCursor(Cursor.getDefaultCursor());
                                                 }

                                             }

                                             if (Objects.equals(phasePartie, "PoseUnites") && partie.checkIfThisIsOneOfMyCountry(currentJoueur, Partie.getCountryName(x, y))) {//PoseUnites est la phase au premier tour de jeu où chacun pose successivement un pion
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


                                                 currentJoueur = changePlayer();
                                                 changeCursor(currentUnite);

                                                 //On vérifie q'il reste des unités à poser
                                                 int nbUnitesTotal = 0;
                                                 for (Joueur joueur : joueurs) {
                                                     nbUnitesTotal += joueur.nbUnites;
                                                 }

                                                 if (nbUnitesTotal == 0) {
                                                     findutour.setVisible(true);
                                                     phasePartie = "Sélection";
                                                     setCursor(Cursor.getDefaultCursor());
                                                 } else {
                                                     while (currentJoueur.nbUnites == 0) {
                                                         currentJoueur = changePlayer();
                                                         changeCursor(currentUnite);
                                                     }
                                                 }

                                                 repaint();

                                             }

                                         }

                                         //gère les changements de curseur au clic droit
                                         if (event.getButton() == MouseEvent.BUTTON3) {

                                             if (Objects.equals(phasePartie, "Renforts") || Objects.equals(phasePartie, "PoseUnites")) {
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

                             }


        );


    }

    private void changeCursor(String pathname) {
        /*permet de changer de curseur*/
        BufferedImage icone = Main.ImageReader(pathname + ".png");
        icone = Main.changeColor(icone, currentJoueur.couleur);
        setCursor(Toolkit.getDefaultToolkit().createCustomCursor(new ImageIcon(icone).getImage(), new Point(0, 0), "Curseur"));
        currentUnite = pathname;
    }

    private Joueur changePlayer() {
        /*permet de changer de joueur*/
        //repaint();
        numerojoueur = (numerojoueur + 1) % joueurs.size();
        currentJoueur = joueurs.get(numerojoueur);
        updateJLabel();
        repaint();
        if (Objects.equals(currentJoueur.getClass().getName(), "IA")) {
            currentJoueur.play(phasePartie, partie);
            if (partie.totalNbUnites(joueurs) == 0) {
                phasePartie = "Sélection";
            }
            changePlayer();
        }
        //repaint();

        return currentJoueur;
    }

    private void updateJLabel() {
        /*Met à jour les JLabel*/
        unitesRestantes.setText("Il reste " + currentJoueur.nbUnites + " unités.");
        joueurActif.setText("C'est au tour de " + currentJoueur.getName() + ".");

    }

    private void konamiCode(KeyEvent event) {
        /*Fait fonctionner le cheatcode*/
        String key = null;

        if (event.getKeyCode() == KeyEvent.VK_RIGHT) {
            key = "RIGHT";
        } else if (event.getKeyCode() == KeyEvent.VK_LEFT) {
            key = "LEFT";
        } else if (event.getKeyCode() == KeyEvent.VK_UP) {
            key = "UP";
        } else if (event.getKeyCode() == KeyEvent.VK_DOWN) {
            key = "DOWN";
        } else if (event.getKeyCode() == KeyEvent.VK_B) {
            key = "B";
        }

        if (Konami == 0 || Objects.equals(key, "UP")) {
            Konami += 1;
        } else if (Konami == 1 || Objects.equals(key, "UP")) {
            Konami += 1;
        } else if (Konami == 2 || Objects.equals(key, "DOWN")) {
            Konami += 1;
        } else if (Konami == 3 || Objects.equals(key, "DOWN")) {
            Konami += 1;
        } else if (Konami == 4 || Objects.equals(key, "DOWN")) {
            Konami += 1;
        } else if (Konami == 5 || Objects.equals(key, "LEFT")) {
            Konami += 1;
        } else if (Konami == 6 || Objects.equals(key, "LEFT")) {
            Konami += 1;
        } else if (Konami == 7 || Objects.equals(key, "RIGHT")) {
            Konami += 1;
        } else if (Konami == 8 || Objects.equals(key, "RIGHT")) {
            Konami += 1;
        } else if (Konami == 9 || Objects.equals(key, "B")) {
            partie.win(currentJoueur);
        } else {
            Konami = 0;
        }

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

