
import java.io.FileReader;
import java.util.*;
import java.io.BufferedReader;

/**
 * 
 */
public class Territoire {

    /**
     * Default constructor
     */
    public Territoire() {
    }

    /**
     * 
     */
    public String Nom;

    /**
     * 
     */
    // public list <Territoire> voisins;

    public static void checkIfConquestIsPossible (String countryToConquest) {
        try{
        String line;
        BufferedReader frontieres = new BufferedReader(new FileReader("frontieres.txt"));
        while ((line = frontieres.readLine()) != null) {
            String[] thatLine = line.split(";");
            System.out.println(thatLine.size());
            for (int x = 1; x<2; x++) {
                System.out.println(thatLine[x]);
            }
    }} catch (Exception e) {
            e.printStackTrace();
        }}


    /**
     * @param String s
     */
    public void setNom(String s) {
        // TODO implement here
    }

    /**
     * 
     */
    public void getNom() {
        // TODO implement here
    }

}