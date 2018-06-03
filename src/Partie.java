import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Objects;

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
        System.out.println(nbJoueurs);


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
            String name = "";
            while ("".equals(name) || name == null) {
                name = (String) jop2.showInputDialog(null,
                        "Entrer le nom du joueur " + (x + 1) + ":",
                        "Saisie des noms des joueurs", JOptionPane.QUESTION_MESSAGE,
                        imageIcon,
                        null,
                        "");
            }

            int nbUnites = 50 - 5 * nbJoueurs;

            joueurs.add(new Joueur(name, new ArrayList<Territoire>(), new ArrayList<Unite>(), nbUnites, new ArrayList<Region>(), couleurs.get(x)));
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





        for (int x = 0; x < joueurs.size(); x++) { //Tous les pions ne se mettent pas, à corriger
            //System.out.println(joueurs.get(x).listTerritoires.size());
            for (int y = 0; y < joueurs.get(x).listTerritoires.size(); y++) {


                String territoireName = joueurs.get(x).listTerritoires.get(y).getName();


                try {
                    String line;
                    BufferedReader positionsInit = Main.readTextFile("positionsinit.txt");
                    while ((line = positionsInit.readLine()) != null) {
                        String[] thatLine = line.split(";");

                        if (Objects.equals(thatLine[0], territoireName)) {

                            joueurs.get(x).putUnite(new Soldat(Integer.parseInt(thatLine[1]), Integer.parseInt(thatLine[2])));
                            if (Objects.equals(territoireName, "westeurope")) {
                                System.out.println(Integer.parseInt(thatLine[1]));
                                System.out.println(Integer.parseInt(thatLine[2]));
                            }

                        }

                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }



            }
        }

        //joueurs.get(0).listUnites.add(new Soldat(23, 24));



        phasePartie = "Renforts";


        return joueurs;
    }


    public static boolean Tour(Joueur joueur, int x, int y) {

        phasePartie = "Renforts";
        joueur = Unite.attributionUnites(joueur);
        while (joueur.nbUnites > 0) {

        }
        phasePartie = "Déplacement";


        phasePartie = "Attaque";




        if (checkIfWin(joueur)) {
            System.out.println("Le joueur " + joueur.getName() + " a gagné !" );
            return true;
        }

        else {
            return false;
        }


    }

    public static boolean checkIfWin(Joueur joueur) { //listRegions n'est pas fonctionnel

        if (joueur.listRegions.size() == 6) {

            return true;
        }
        else {
            return false;
        }
    }

}