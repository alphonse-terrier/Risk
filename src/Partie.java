import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Objects;

/**
 *
 */


public class Partie {
    public static int Konami = 0;
    public static String phasePartie = "Initialisation";
    public static ArrayList<Territoire> allTerritories;


    public static ArrayList<Joueur> initGame() {
        ImageIcon icone = new ImageIcon("iconenbjoueurs.png");
        String[] cbdejoueurs = {"2", "3", "4", "5", "6"};
        JOptionPane jop = new JOptionPane();
        String nombre = null;
        while (nombre == null) {
            nombre = (String) jop.showInputDialog(null,
                    "Veuillez saisir le nombre de joueurs ?",
                    "Choix du nombre de joueurs",
                    JOptionPane.QUESTION_MESSAGE,
                    icone,
                    cbdejoueurs,
                    cbdejoueurs[0]);
        }
        int nbJoueurs = Integer.parseInt(nombre);


        ArrayList<Color> couleurs = new ArrayList<Color>();
        couleurs.add(new Color(210, 21, 27));
        couleurs.add(new Color(255, 133, 0));
        couleurs.add(new Color(11, 100, 155));
        couleurs.add(new Color(11, 141, 9));
        couleurs.add(new Color(154, 68, 178));
        couleurs.add(new Color(102, 15, 51));
        ArrayList<Joueur> joueurs = new ArrayList<Joueur>();


        for (int x = 0; x < nbJoueurs; x++) {

            boolean remember = false;
            BufferedImage iconejoueur = Main.ImageReader("iconejoueur.png");
            iconejoueur = Main.changeColor(iconejoueur, couleurs.get(x));
            ImageIcon imageIcon = new ImageIcon(iconejoueur);
            JOptionPane jop2 = new JOptionPane();
            JCheckBox checkbox = new JCheckBox("IA");

            String[] nomJoueursDefault = {"Bill Gates", "Mark Zuckerberg", "Jeff Bezos", "Jimmy Wales", "Dara Khosrowshahi", "Larry Page"};

            Object[] msgContent = {checkbox, "Entrer le nom du joueur " + (x + 1) + ":"};
            String name = "";
            while ("".equals(name) || name == null) {
                name = (String) jop2.showInputDialog(null,
                        msgContent,
                        "Saisie des noms des joueurs", JOptionPane.QUESTION_MESSAGE,
                        imageIcon,
                        null,
                        nomJoueursDefault[x]);
                remember = checkbox.isSelected();
            }


            int nbUnites = 50 - 5 * nbJoueurs;
            if (remember) {
                joueurs.add(new IA(name, new ArrayList<Territoire>(), new ArrayList<Unite>(), nbUnites, couleurs.get(x), 0));

            } else {
                joueurs.add(new Joueur(name, new ArrayList<Territoire>(), new ArrayList<Unite>(), nbUnites, couleurs.get(x), 0));
            }
        }


        allTerritories = Territoire.getAllCountriesName();
        Collections.shuffle(allTerritories);


        int i = 0;
        while (i < allTerritories.size()) {
            for (Joueur joueur : joueurs) {
                if (i < allTerritories.size()) {
                    joueur.listTerritoires.add(allTerritories.get(i));
                    i += 1;

                }
            }
        }


        for (Joueur joueur : joueurs) {
            for (Territoire territoire : joueur.listTerritoires) {


                String territoireName = territoire.getName();


                try {
                    String line;
                    BufferedReader positionsInit = Main.readTextFile("./Terre/positionsinit.txt");
                    while ((line = positionsInit.readLine()) != null) {
                        String[] thatLine = line.split(";");

                        if (Objects.equals(thatLine[0], territoireName)) {

                            joueur.putUnite(new Soldat(Integer.parseInt(thatLine[1]), Integer.parseInt(thatLine[2])));


                        }

                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }


            }
        }


        phasePartie = "PoseUnites";


        return joueurs;
    }

    public static boolean attaque(Joueur joueurAttack, String countryToConquest) {

        if (Unite.SelectionUnite.size() > 3) {
            return false;
        }


        Joueur joueurDefense = null;
        Territoire territoire = null;

        for (Joueur joueur : Fenetre.joueurs) {
            for (Territoire territoireToConquest : joueur.listTerritoires) {
                if (Objects.equals(territoire.getName(), countryToConquest)) {
                    joueurDefense = joueur;
                    territoire = territoireToConquest;
                }
            }
        }


        ArrayList<Unite> allUnitsJoueurDefense = Unite.getAllUnitsinTerritoire(countryToConquest, joueurDefense.listUnites);

        ArrayList<Unite> unitsJoueurDefense = new ArrayList<Unite>();

        if (allUnitsJoueurDefense.size() > 2) {
            while (unitsJoueurDefense.size() < 2) {
                int iterator = 0;
                for (int i = 0; i < allUnitsJoueurDefense.size() - 1; i++) {
                    if (allUnitsJoueurDefense.get(i + 1).priorityDefense <= allUnitsJoueurDefense.get(i).priorityDefense) {
                        iterator = i + 1;
                    }
                }
                unitsJoueurDefense.add(allUnitsJoueurDefense.get(iterator));
                allUnitsJoueurDefense.remove(allUnitsJoueurDefense.get(iterator));
            }
        } else {
            unitsJoueurDefense = allUnitsJoueurDefense;
        }


        for (int i = 0; i < Unite.SelectionUnite.size(); i++) {
            Unite.SelectionUnite.get(i).actualPower = Unite.SelectionUnite.get(i).getPower();
        }

        for (int i = 0; i < unitsJoueurDefense.size(); i++) {
            unitsJoueurDefense.get(i).actualPower = unitsJoueurDefense.get(i).getPower();
        }


        ArrayList<Unite> unitsAttackToRemove = new ArrayList<Unite>();
        ArrayList<Unite> unitsDefenseToRemove = new ArrayList<Unite>();
        ArrayList<Unite> unitsAttackToSave = new ArrayList<Unite>();


        Collections.sort(unitsJoueurDefense, (Unit1, Unit2) -> Unit2.actualPower - Unit1.actualPower);

        Collections.sort(Unite.SelectionUnite, (Unit1, Unit2) -> Unit2.actualPower - Unit1.actualPower);


        for (int i = 0; i < Math.min(Unite.SelectionUnite.size(), unitsJoueurDefense.size()); i++) {
            if (Unite.SelectionUnite.get(i).actualPower == unitsJoueurDefense.get(i).actualPower) {
                if (Unite.SelectionUnite.get(i).priorityAttack > unitsJoueurDefense.get(i).priorityDefense) {
                    unitsDefenseToRemove.add(unitsJoueurDefense.get(i));
                    unitsAttackToSave.add(Unite.SelectionUnite.get(i));

                } else {
                    unitsAttackToRemove.add(Unite.SelectionUnite.get(i));


                }

            } else if (Unite.SelectionUnite.get(i).actualPower > unitsJoueurDefense.get(i).actualPower) {
                unitsDefenseToRemove.add(unitsJoueurDefense.get(i));
                unitsAttackToSave.add(Unite.SelectionUnite.get(i));

            } else {
                unitsAttackToRemove.add(Unite.SelectionUnite.get(i));

            }
        }


        for (int i = 0; i < unitsAttackToRemove.size(); i++) {
            for (int j = 0; j < joueurAttack.listUnites.size(); j++) {
                if (Objects.equals(unitsAttackToRemove.get(i), joueurAttack.listUnites.get(j))) {
                    joueurAttack.listUnites.remove(j);
                }
            }

        }


        for (int i = 0; i < unitsDefenseToRemove.size(); i++) {
            for (int j = 0; j < joueurDefense.listUnites.size(); j++) {
                if (Objects.equals(unitsDefenseToRemove.get(i), joueurDefense.listUnites.get(j))) {
                    joueurDefense.listUnites.remove(j);
                }
            }

        }


        allUnitsJoueurDefense = Unite.getAllUnitsinTerritoire(countryToConquest, joueurDefense.listUnites);

        if (allUnitsJoueurDefense.size() == 0) {
            joueurAttack.nbTerritoiresCapturéesTourPréc += 1;
            joueurAttack.listTerritoires.add(territoire);
            for (int i = 0; i < joueurDefense.listTerritoires.size(); i ++) {
                if(Objects.equals(territoire, joueurDefense.listTerritoires.get(i))) {
                    joueurDefense.listTerritoires.remove(i);
                }
            }



            for (int j = 0; j < Unite.SelectionUnite.size(); j++) {
                int[] XY = Territoire.getRandomXYOfACountry(territoire.getName());
                Unite.SelectionUnite.get(j).positionx = XY[0];
                Unite.SelectionUnite.get(j).positiony = XY[1];
                Unite.SelectionUnite.get(j).mvtParTour -= 1;
            }

            return true;
        }


        Unite.SelectionUnite = new ArrayList<Unite>();
        return false;

    }


    public static boolean checkIfWin(Joueur joueur) {
        if (joueur.listTerritoires.size() == 42) {
            return win(joueur);
        } else {
            return false;
        }
    }

    public static boolean win(Joueur joueur) {
        String rejouer = "Rejouer";
        String quitter = "Quitter";
        String[] bouton = {rejouer, quitter};
        BufferedImage iconejoueur = Main.ImageReader("iconejoueur.png");
        iconejoueur = Main.changeColor(iconejoueur, joueur.couleur);
        ImageIcon imageIcon = new ImageIcon(iconejoueur);
        JOptionPane jop = new JOptionPane();
        int rang = -1;
        while (rang == -1) {
            System.out.println(rang);
            rang = jop.showOptionDialog(null,
                    "Bravo " + joueur.getName() + ", tu as gagné !",
                    joueur.getName() + " a gagné !",
                    JOptionPane.YES_NO_CANCEL_OPTION,
                    JOptionPane.QUESTION_MESSAGE,
                    imageIcon,
                    bouton,
                    bouton[1]);
        }


        if (Objects.equals(rang, 1)) {
            System.exit(0);
        }

        if (Objects.equals(rang, 0)) {
            Fenetre.joueurs = Partie.initGame();
            Unite.SelectionUnite = new ArrayList<Unite>();

        }


        return true;
    }

    public static void KonamiCode(KeyEvent event) {
        String key = null;

        if (event.getKeyCode() == KeyEvent.VK_RIGHT ) {
            key = "RIGHT";
        } else if (event.getKeyCode() == KeyEvent.VK_LEFT ) {
            key = "LEFT";
        } else if (event.getKeyCode() == KeyEvent.VK_UP ) {
            key = "UP";
        } else if (event.getKeyCode() == KeyEvent.VK_DOWN ) {
            key = "DOWN";
        } else if (event.getKeyCode() == KeyEvent.VK_B) {
            key = "B";}

        if (Konami == 0 || Objects.equals(key, "UP")) {
            Konami +=1;
        } else if (Konami == 1 || Objects.equals(key, "UP")) {
            Konami +=1;
        } else if (Konami == 2 || Objects.equals(key, "DOWN")) {
            Konami +=1;
        } else if (Konami == 3 || Objects.equals(key, "DOWN")) {
            Konami +=1;
        } else if (Konami == 4 || Objects.equals(key, "DOWN")) {
            Konami +=1;
        } else if (Konami == 5 || Objects.equals(key, "LEFT")) {
            Konami +=1;
        } else if (Konami == 6 || Objects.equals(key, "LEFT")) {
            Konami +=1;
        } else if (Konami == 7 || Objects.equals(key, "RIGHT")) {
            Konami +=1;
        } else if (Konami == 8 || Objects.equals(key, "RIGHT")) {
            Konami +=1;
        } else if (Konami == 9 || Objects.equals(key, "B")) {
            Partie.win(Fenetre.currentJoueur);
        } else {
            Konami =0;
        }

    }

}
