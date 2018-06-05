import java.awt.*;
import java.util.ArrayList;
import java.util.Objects;

/**
 * Created by Alphonse on 05/06/2018.
 */
public class IA extends Joueur {
    public IA(String name, ArrayList<Territoire> listTerritoires, ArrayList<Unite> listUnites, int nbUnites, Color couleur, int nbTerritoiresCapturéesTourPréc) {
        super(name, listTerritoires, listUnites, nbUnites, couleur, nbTerritoiresCapturéesTourPréc);
    }


    public static void play(Joueur currentJoueur) {
        if (Objects.equals(Partie.phasePartie, "PoseUnites")) {


        }


    }

}


