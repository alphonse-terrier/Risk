import java.awt.*;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Random;

/**
 * Created by Alphonse on 05/06/2018.
 */
public class IA extends Joueur {
    IA(String name, ArrayList<Territoire> listTerritoires, ArrayList<Unite> listUnites, int nbUnites, Color couleur, int nbTerritoiresCaptureesTourPrec) {
        super(name, listTerritoires, listUnites, nbUnites, couleur, nbTerritoiresCaptureesTourPrec);
    }


    public void play() {
        /*
        if (Objects.equals(Partie.phasePartie, "PoseUnites")) {
            Random random = new Random();
            int randomIndexCountry = random.nextInt(currentJoueur.listTerritoires.size());
            String countryName = currentJoueur.listTerritoires.get(randomIndexCountry).getName();
            int[] XY = Territoire.getRandomXYOfACountry(countryName);
            if (currentJoueur.putUnite(new Canon(XY[0], XY[1]))) {

            } else if (currentJoueur.putUnite(new Cavalier(XY[0], XY[1]))) {

            } else {
                currentJoueur.putUnite(new Soldat(XY[0], XY[1]));

            }

        }
        */


    }

}


