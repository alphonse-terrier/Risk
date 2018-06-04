import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Objects;
import java.util.Random;

/**
 *
 */


public class Partie {
    public static String phasePartie = "Initialisation";


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


            BufferedImage iconejoueur = Main.ImageReader("iconejoueur.png");
            iconejoueur = Main.changeColor(iconejoueur, couleurs.get(x));
            ImageIcon imageIcon = new ImageIcon(iconejoueur);
            JOptionPane jop2 = new JOptionPane();
            String[] nomJoueursDefault = {"Bill Gates", "Mark Zuckerberg", "Jeff Bezos", "Jimmy Wales", "Dara Khosrowshahi", "Larry Page"};
            String name = "";
            while ("".equals(name) || name == null) {
                name = (String) jop2.showInputDialog(null,
                        "Entrer le nom du joueur " + (x + 1) + ":",
                        "Saisie des noms des joueurs", JOptionPane.QUESTION_MESSAGE,
                        imageIcon,
                        null,
                        nomJoueursDefault[x]);
            }

            int nbUnites = 50 - 5 * nbJoueurs;

            joueurs.add(new Joueur(name, new ArrayList<Territoire>(), new ArrayList<Unite>(), nbUnites, couleurs.get(x), 0));
        }


        ArrayList<Territoire> allTerritories = Territoire.getAllCountriesName();
        Collections.shuffle(allTerritories);


        int i = 0;
        while (i < allTerritories.size()) {
            for (int x = 0; x < nbJoueurs; x++) {
                if (i < allTerritories.size()) {
                    joueurs.get(x).listTerritoires.add(allTerritories.get(i));
                    i += 1;

                }
            }
        }


        for (int x = 0; x < joueurs.size(); x++) {
            for (int y = 0; y < joueurs.get(x).listTerritoires.size(); y++) {


                String territoireName = joueurs.get(x).listTerritoires.get(y).getName();


                try {
                    String line;
                    BufferedReader positionsInit = Main.readTextFile("./Terre/positionsinit.txt");
                    while ((line = positionsInit.readLine()) != null) {
                        String[] thatLine = line.split(";");

                        if (Objects.equals(thatLine[0], territoireName)) {

                            joueurs.get(x).putUnite(new Soldat(Integer.parseInt(thatLine[1]), Integer.parseInt(thatLine[2])));


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

        for (int i = 0; i < Fenetre.joueurs.size(); i++) {
            for (int j = 0; j < Fenetre.joueurs.get(i).listTerritoires.size(); j++) {
                if (Objects.equals(Fenetre.joueurs.get(i).listTerritoires.get(j).getName(), countryToConquest)) {
                    joueurDefense = Fenetre.joueurs.get(i);
                    territoire = Fenetre.joueurs.get(i).listTerritoires.get(j);
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
            System.out.println(Unite.SelectionUnite.get(i).actualPower);
        }
        System.out.println();
        for (int i = 0; i < unitsJoueurDefense.size(); i++) {
            unitsJoueurDefense.get(i).actualPower = unitsJoueurDefense.get(i).getPower();
            System.out.println(unitsJoueurDefense.get(i).actualPower);
        }
        System.out.println();


        ArrayList<Unite> unitsAttackToRemove = new ArrayList<Unite>();
        ArrayList<Unite> unitsDefenseToRemove = new ArrayList<Unite>();
        ArrayList<Unite> unitsAttackToSave = new ArrayList<Unite>();


        Collections.sort(unitsJoueurDefense, (Unit1, Unit2) -> Unit2.actualPower - Unit1.actualPower);
        for (int i = 0; i < unitsJoueurDefense.size(); i++) {
            System.out.println(unitsJoueurDefense.get(i).actualPower);
        }
        Collections.sort(Unite.SelectionUnite, (Unit1, Unit2) -> Unit2.actualPower - Unit1.actualPower);
        //trier UnitsJoueurDefense et Unite.SelectionUnite par actualPower


        for (int i = 0; i < Math.min(Unite.SelectionUnite.size(), unitsJoueurDefense.size()); i++) {
            if (Unite.SelectionUnite.get(i).actualPower == unitsJoueurDefense.get(i).actualPower) {
                if (Unite.SelectionUnite.get(i).priorityAttack > unitsJoueurDefense.get(i).priorityDefense) {
                    unitsDefenseToRemove.add(unitsJoueurDefense.get(i));
                    unitsAttackToSave.add(Unite.SelectionUnite.get(i));
                    Unite.SelectionUnite.get(i).positionx = unitsJoueurDefense.get(i).positionx;
                    Unite.SelectionUnite.get(i).positiony = unitsJoueurDefense.get(i).positiony;
                } else {
                    unitsAttackToRemove.add(Unite.SelectionUnite.get(i));


                }

            } else if (Unite.SelectionUnite.get(i).actualPower > unitsJoueurDefense.get(i).actualPower) {
                unitsDefenseToRemove.add(unitsJoueurDefense.get(i));
                unitsAttackToSave.add(Unite.SelectionUnite.get(i));
                Unite.SelectionUnite.get(i).positionx = unitsJoueurDefense.get(i).positionx;
                Unite.SelectionUnite.get(i).positiony = unitsJoueurDefense.get(i).positiony;
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

        allUnitsJoueurDefense = Unite.getAllUnitsinTerritoire(countryToConquest, joueurDefense.listUnites);
        if (unitsDefenseToRemove.size() == unitsJoueurDefense.size()) {




            for (int i = 0; i < allUnitsJoueurDefense.size(); i++) {
                for (int j = 0; j < joueurDefense.listUnites.size(); j++) {
                    if (Objects.equals(allUnitsJoueurDefense.get(i), joueurDefense.listUnites.get(j))) {
                        joueurDefense.listUnites.remove(j);
                    }
                }
            }
                    /*
            Random random = new Random();
            for (int i = 0; i < unitsAttackToSave.size(); i++) {
                int randx = unitsDefenseToRemove.get(0).positionx + random.nextInt(5 + 1 + 5) - 5;
                int randy = unitsDefenseToRemove.get(0).positiony + random.nextInt(5 + 1 + 5) - 5;
                while (Objects.equals(Territoire.getCountryName(unitsDefenseToRemove.get(0).positionx, unitsDefenseToRemove.get(0).positiony), Territoire.getCountryName(randx, randy))) {
                    unitsAttackToSave.get(i).positionx = randx;
                    unitsAttackToSave.get(i).positiony = randy;
                    randx = unitsDefenseToRemove.get(0).positionx + random.nextInt(5 + 1 + 5) - 5;
                    randy = unitsDefenseToRemove.get(0).positiony + random.nextInt(5 + 1 + 5) - 5;

                }
            } */

            //Changer position troupe restante
            joueurAttack.nbTerritoiresCapturéesTourPréc += 1;
            joueurAttack.listTerritoires.add(territoire);
            Unite.SelectionUnite = new ArrayList<Unite>();
            return true;
        } else {

            for (int i = 0; i < unitsDefenseToRemove.size(); i++) {
                for (int j = 0; j < joueurDefense.listUnites.size(); j++) {
                    if (Objects.equals(unitsDefenseToRemove.get(i), joueurDefense.listUnites.get(j))) {
                        joueurDefense.listUnites.remove(j);
                    }
                }

            }
        }

        Unite.SelectionUnite = new ArrayList<Unite>();



        return false;
/*








        for (int i = 0; i < unitsJoueurAttack.size(); i++) {
            unitsJoueurAttack.get(i).actualPower = unitsJoueurAttack.get(i).getPower();
        }

        for (int i = 0; i < unitsJoueurDefense.size(); i++) {
            unitsJoueurDefense.get(i).actualPower = unitsJoueurDefense.get(i).getPower();
        }



        ArrayList<Unite> unitsJoueurFightAttack = new ArrayList<Unite>();
        ArrayList<Unite> unitsJoueurFightDefense = new ArrayList<Unite>();


        if (unitsJoueurAttack.size() > 1) {
            while (unitsJoueurFightAttack.size() < 2) {
                int iterator = 0;
                for (int i = 0; i < unitsJoueurAttack.size() - 1; i++) {
                    if (unitsJoueurAttack.get(i + 1).priorityDefense >= unitsJoueurAttack.get(i).priorityDefense) {
                        iterator = i + 1;

                    }


                }
                unitsJoueurFightAttack.add(unitsJoueurAttack.get(iterator));
                unitsJoueurAttack.remove(unitsJoueurAttack.get(iterator));

            }
        }
        else {
            unitsJoueurFightAttack = unitsJoueurAttack;
        }


        if (unitsJoueurDefense.size() == 2) {
            if (Math.max(unitsJoueurDefense.get(0).actualPower, unitsJoueurDefense.get(1).actualPower) == unitsJoueurDefense.get(1).actualPower) {
                unitsJoueurFightDefense.add(unitsJoueurDefense.get(1));
                unitsJoueurFightDefense.add(unitsJoueurDefense.get(0));
            } else {
                unitsJoueurFightDefense.add(unitsJoueurDefense.get(0));
                unitsJoueurFightDefense.add(unitsJoueurDefense.get(1));
            }
        } else {
            unitsJoueurFightDefense = unitsJoueurDefense;
        }




*/

    }


    public static boolean checkIfWin(Joueur joueur) { //listRegions n'est pas fonctionnel

        if (joueur.listRegions.size() == 6) {

            return true;
        } else {
            return false;
        }
    }

}