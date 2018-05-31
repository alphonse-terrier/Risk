/**
 * Created by Alphonse on 14/05/2018.
 */


import java.util.*;
import javax.swing.*;

public class Main {

    public static void main(String[] args){

        Fenetre carte = new Fenetre();

        initGame();
        // Condition d'arrêt du jeu : Joueur.listRegions.size() == 6
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

        ArrayList<Joueur> joueurs = new ArrayList<Joueur>();
        for (int x = 0; x < nbJoueurs; x++) {
            System.out.println("Entrez un nom de joueur :");
            Scanner inputName = new Scanner(System.in);
            String name = inputName.nextLine();
            int nbArmees = 50 - 5 * nbJoueurs;

            joueurs.add(new Joueur(name, new ArrayList<Territoire>(), nbArmees, new ArrayList<Unite>()));
        }

        ArrayList<Territoire> allTerritories = Territoire.getAllCountriesName();
        Collections.shuffle(allTerritories);


        int i = 0;
        while(i < allTerritories.size()) {
            for (int x = 0; x < nbJoueurs; x++) {
                if (i < allTerritories.size()) {
                joueurs.get(x).listTerritoires.add(allTerritories.get(i));
                i+=1;

                }
            }
        }


    }
}
