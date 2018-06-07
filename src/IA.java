import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Objects;
import java.util.Random;

/**
 * Created by Alphonse on 05/06/2018.
 */
public class IA extends Joueur {
    IA(String name, ArrayList<Territoire> listTerritoires, ArrayList<Unite> listUnites, int nbUnites, Color couleur, int nbTerritoiresCaptureesTourPrec) {
        super(name, listTerritoires, listUnites, nbUnites, couleur, nbTerritoiresCaptureesTourPrec);
    }


    public void play(String phasePartie, Partie partie) {



        if (Objects.equals(phasePartie, "Renforts")) {
            partie.attributionUnites(this);
            while (nbUnites != 0 ) {
                putAnIaUnite(partie);
            }

        }

        if (Objects.equals(phasePartie, "PoseUnites")) {

            putAnIaUnite(partie);

        }




    }


    public void putAnIaUnite(Partie partie) {
        Random random = new Random();
        int randomIndexCountry = random.nextInt(listTerritoires.size());
        String countryName = listTerritoires.get(randomIndexCountry).getName();
        int[] XY = partie.getRandomXYOfACountry(countryName);
        ArrayList<Unite> unites = new ArrayList<>() ;
        unites.add(new Canon(XY[0], XY[1]));
        unites.add(new Cavalier(XY[0], XY[1]));
        unites.add(new Cavalier(XY[0], XY[1]));
        Collections.shuffle(unites);
        if (putUnite(unites.get(0))) {

        } else if (putUnite(unites.get(1))) {

        } else {
            putUnite(unites.get(2));
        }

    }

}


