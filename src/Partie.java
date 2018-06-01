
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