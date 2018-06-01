
import javax.swing.*;
import java.awt.*;
import java.util.*;

/**
 * 
 */

public class Partie {

    /**
     * Default constructor
     */
    public Partie() {
    }


    /**
     * 
     */
    public boolean Victoire;


    /**
     * @param Territoire t 
     * @param Joueur j 
     * @param Mission m 
     * @param Region r
     */

    public static void choixNbJoueurs() {
        ImageIcon icone = new ImageIcon ("iconenbjoueurs.png");
        String[] cbdejoueurs = {"deux", "trois", "quatre","cinq", "six"};
        JOptionPane jop = new JOptionPane();
        String nombre = (String)jop.showInputDialog(null,
                "Veuillez saisir le nombre de joueurs ?",
                "Choix du nombre de joueurs",
                JOptionPane.QUESTION_MESSAGE,
                icone,
                cbdejoueurs,
                cbdejoueurs[4]);
        int nbJoueurs = 0;

        if (nombre == "deux") {
            nbJoueurs = 2;
        }
        else if (nombre == "trois") {
            nbJoueurs = 3;
        }
        else if (nombre == "quatre") {
            nbJoueurs = 4;
        }
        else if (nombre == "cinq") {
            nbJoueurs = 5;
        }
        else if (nombre == "six"){
            nbJoueurs = 6;
        }
        else{
            System.out.println("ERROR");
        }
        System.out.println(nbJoueurs);

        ArrayList<Color> couleurs = new ArrayList<Color>();
        couleurs.add(new Color(210, 21, 27));
        couleurs.add(new Color(255, 133, 0));
        couleurs.add(new Color(11, 100, 155));
        couleurs.add(new Color(11, 141, 9));
        couleurs.add(new Color(154, 68, 178));
        couleurs.add(new Color(102, 15, 51));
        ArrayList<Joueur> joueurs = new ArrayList<Joueur>();


        for (int x = 1; x < nbJoueurs+1; x++) {
            JOptionPane jop2 = new JOptionPane();
            String name = jop2.showInputDialog(null, "Entrer le nom du joueur " + x + ":", "Saisie des noms des joueurs", JOptionPane.QUESTION_MESSAGE);
            int nbUnites = 50 - 5 * nbJoueurs;
            joueurs.add(new Joueur(name, new ArrayList<Territoire>(), new ArrayList<Unite>(), nbUnites, new ArrayList<Region>(), couleurs.get(x)));
            System.out.println(name);
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

    }




    public static void initGame() {
        Scanner saisieNombreJoueurs = new Scanner(System.in);
        System.out.println("Veuillez saisir le nombre de joueurs (entre 2 et 6) :");
        int nbJoueurs = 0;

        nbJoueurs = Integer.parseInt(saisieNombreJoueurs.next());
        while (nbJoueurs < 2 || nbJoueurs > 6) {
            System.out.println("Entrez un nombre de joueurs valide");
            nbJoueurs = Integer.parseInt(saisieNombreJoueurs.next());
        }

        ArrayList<Color> couleurs = new ArrayList<Color>();
        couleurs.add(new Color(210, 21, 27));
        couleurs.add(new Color(255, 133, 0));
        couleurs.add(new Color(11, 100, 155));
        couleurs.add(new Color(11, 141, 9));
        couleurs.add(new Color(154, 68, 178));
        couleurs.add(new Color(102, 15, 51));
        ArrayList<Joueur> joueurs = new ArrayList<Joueur>();
        for (int x = 0; x < nbJoueurs; x++) {
            System.out.println("Entrez un nom de joueur :");
            Scanner inputName = new Scanner(System.in);
            String name = inputName.nextLine();
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

        // A FAIRE : Manque le placement des unités sur les territoires (partie 3.1.4)


    }

}