import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Objects;
import java.util.Random;



public class Partie {


    ArrayList < Unite > SelectionUnite = new ArrayList <> ();
    ArrayList < Territoire > allTerritories;

    static String getCountryName(int x, int y) {
        /*Renvoie le nom du territoire selon les coordonnées x et y d'un point sur la carte*/
        try {
            String line;
            BufferedReader countries = new BufferedReader(new FileReader("./Terre/countries.txt"));
            while ((line = countries.readLine()) != null) {
                String[] thatLine = line.split(";");

                if (Integer.parseInt(thatLine[0]) == x && Integer.parseInt(thatLine[1]) == y) {
                    return thatLine[2];
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ("C'est pas l'homme qui prend la mer, c'est la mer qui prend l'homme");


    }

    ArrayList < Joueur > initGame() {
        /* Permet l'initialisation du jeu */

        ImageIcon icone = new ImageIcon("iconenbjoueurs.png");
        String[] cbdejoueurs = {
                "2",
                "3",
                "4",
                "5",
                "6"
        };
        JOptionPane jop = new JOptionPane();
        String nombre = null;
        while (nombre == null) { //première fenètre
            nombre = (String) JOptionPane.showInputDialog(null,
                    "Veuillez saisir le nombre de joueurs ?",
                    "Choix du nombre de joueurs",
                    JOptionPane.QUESTION_MESSAGE,
                    icone,
                    cbdejoueurs,
                    cbdejoueurs[0]);
        }
        int nbJoueurs = Integer.parseInt(nombre);


        ArrayList < Color > couleurs = new ArrayList <> ();
        couleurs.add(new Color(210, 21, 27));
        couleurs.add(new Color(255, 133, 0));
        couleurs.add(new Color(11, 100, 155));
        couleurs.add(new Color(11, 141, 9));
        couleurs.add(new Color(154, 68, 178));
        couleurs.add(new Color(102, 15, 51));
        ArrayList < Joueur > joueurs = new ArrayList < > ();


        for (int x = 0; x < nbJoueurs; x++) {

            boolean remember = false;
            BufferedImage iconejoueur = Main.ImageReader("iconejoueur.png");
            iconejoueur = Main.changeColor(iconejoueur, couleurs.get(x));
            ImageIcon imageIcon = new ImageIcon(iconejoueur);
            JOptionPane jop2 = new JOptionPane();
            JCheckBox checkbox = new JCheckBox("IA");
            Object[] msgContent;
            String[] nomJoueursDefault = {
                    "Bill Gates",
                    "Mark Zuckerberg",
                    "Jeff Bezos",
                    "Jimmy Wales",
                    "Dara Khosrowshahi",
                    "Larry Page"
            };

            if (x > 0) {

                msgContent = new Object[] {
                        checkbox,
                        "Entrer le nom du joueur " + (x + 1) + ":"
                };
            } else {
                msgContent = new Object[] {
                        "Entrer le nom du joueur " + (x + 1) + ":"
                };
            }


            String name = "";
            while ("".equals(name) || name == null) { //deuxième fenêtre
                name = (String) JOptionPane.showInputDialog(null,
                        msgContent,
                        "Saisie des noms des joueurs", JOptionPane.QUESTION_MESSAGE,
                        imageIcon,
                        null,
                        nomJoueursDefault[x]);
                remember = checkbox.isSelected();
            }


            int nbUnites = 50 - 5 * nbJoueurs;

            //création des joueurs
            if (remember) {
                joueurs.add(new IA(name, new ArrayList < > (), new ArrayList < > (), nbUnites, couleurs.get(x), 0));

            } else {
                joueurs.add(new Joueur(name, new ArrayList < > (), new ArrayList < > (), nbUnites, couleurs.get(x), 0));
            }
        }


        allTerritories = getAllCountriesName();
        Collections.shuffle(allTerritories); //on mélange les territoires pour pouvoir ensuite les attribuer aléatoirement aux joueurs


        int i = 0;
        while (i < allTerritories.size()) {
            //attribution des territoires
            for (Joueur joueur: joueurs) {
                if (i < allTerritories.size()) {
                    joueur.listTerritoires.add(allTerritories.get(i));
                    i += 1;

                }
            }
        }


        for (Joueur joueur: joueurs) {
            for (Territoire territoire: joueur.listTerritoires) {


                String territoireName = territoire.getName();


                try {
                    String line;
                    BufferedReader positionsInit = Main.readTextFile("./Terre/positionsinit.txt");
                    while ((line = positionsInit.readLine()) != null) {
                        String[] thatLine = line.split(";");

                        if (Objects.equals(thatLine[0], territoireName)) {
                            //Placement des soldats selon le fichier positionsinit et les attributions précédentes
                            joueur.putUnite(new Soldat(Integer.parseInt(thatLine[1]), Integer.parseInt(thatLine[2])));


                        }

                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }


            }
        }


        return joueurs;
    }

    boolean attaque(Joueur joueurAttack, String countryToConquest, ArrayList < Joueur > joueurs) {
        /* Permet l'attaque d'un joueur sur un pays*/
        if (SelectionUnite.size() > 3) {
            return false;
        }


        Joueur joueurDefense = null;
        Territoire territoire = null;

        //On cherche à qui appartient le pays que l'on attaque
        for (Joueur joueur: joueurs) {
            for (Territoire territoireToConquest: joueur.listTerritoires) {
                if (Objects.equals(territoireToConquest.getName(), countryToConquest)) {
                    joueurDefense = joueur;
                    territoire = territoireToConquest;
                }
            }
        }


        //On cherche toutes les unités dans le pays en question
        ArrayList < Unite > allUnitsJoueurDefense = getAllUnitsinTerritoire(countryToConquest, joueurDefense.listUnites);

        ArrayList < Unite > unitsJoueurDefense = new ArrayList < > ();


        //On ne garde que les plus basses priorités défenses
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

        //On calcule les puissances des unités
        for (Unite unite: SelectionUnite) {
            unite.actualPower = unite.getPower();
        }

        for (Unite unite: unitsJoueurDefense) {
            unite.actualPower = unite.getPower();
        }


        ArrayList < Unite > unitsAttackToRemove = new ArrayList < > ();
        ArrayList < Unite > unitsDefenseToRemove = new ArrayList < > ();

        //On trie les unités selon leurs puissances
        Collections.sort(unitsJoueurDefense, (Unit1, Unit2) -> Unit2.actualPower - Unit1.actualPower);

        Collections.sort(SelectionUnite, (Unit1, Unit2) -> Unit2.actualPower - Unit1.actualPower);

        //On compare les puissances des unités et on mt de côté celles que l'on doit supprimer
        for (int i = 0; i < Math.min(SelectionUnite.size(), unitsJoueurDefense.size()); i++) {
            if (SelectionUnite.get(i).actualPower == unitsJoueurDefense.get(i).actualPower) {
                if (SelectionUnite.get(i).priorityAttack > unitsJoueurDefense.get(i).priorityDefense) {
                    unitsDefenseToRemove.add(unitsJoueurDefense.get(i));

                } else {
                    unitsAttackToRemove.add(SelectionUnite.get(i));


                }

            } else if (SelectionUnite.get(i).actualPower > unitsJoueurDefense.get(i).actualPower) {
                unitsDefenseToRemove.add(unitsJoueurDefense.get(i));

            } else {
                unitsAttackToRemove.add(SelectionUnite.get(i));

            }
        }

        //On supprime les unités à supprimer
        for (Unite uniteToRemove: unitsAttackToRemove) {
            for (int j = 0; j < joueurAttack.listUnites.size(); j++) {
                if (Objects.equals(uniteToRemove, joueurAttack.listUnites.get(j))) {
                    joueurAttack.listUnites.remove(j);
                }
            }

        }


        for (Unite uniteToRemove: unitsDefenseToRemove) {
            for (int j = 0; j < joueurDefense.listUnites.size(); j++) {
                if (Objects.equals(uniteToRemove, joueurDefense.listUnites.get(j))) {
                    joueurDefense.listUnites.remove(j);
                }
            }

        }


        allUnitsJoueurDefense = getAllUnitsinTerritoire(countryToConquest, joueurDefense.listUnites);

        //On regarde si on a réussi à conquérir le pays
        if (allUnitsJoueurDefense.size() == 0) {
            joueurAttack.nbTerritoiresCapturesTourPrec += 1;
            joueurAttack.listTerritoires.add(territoire);
            for (int i = 0; i < joueurDefense.listTerritoires.size(); i++) {
                if (Objects.equals(territoire, joueurDefense.listTerritoires.get(i))) {
                    joueurDefense.listTerritoires.remove(i);
                }
            }

            //On place aléatoirement les unités vivantes qui ont participé à l'attaque sur le pays conquis
            for (Unite unite: SelectionUnite) {
                assert territoire != null;
                int[] XY = getRandomXYOfACountry(territoire.getName());
                unite.positionx = XY[0];
                unite.positiony = XY[1];
                unite.mvtParTour -= 1;
            }

            return true;
        }

        //On efface la sélection
        SelectionUnite = new ArrayList < > ();
        return false;

    }

    boolean areTheseCountriesTheSame(String countryname1, String countryname2) {
        /*Compare deux territoires*/
        return Objects.equals(countryname1, countryname2);
    }

    boolean areTheseCountriesAdjacents(String countryname1, String countryname2) {
        /* Renvoie true si les deux pays sont adjaçents */

        if (areTheseCountriesTheSame(countryname1, countryname2)) {
            return false;
        }

        try {
            String line;
            BufferedReader frontieres = new BufferedReader(new FileReader("./Terre/frontieres.txt"));
            while ((line = frontieres.readLine()) != null) {
                String[] thatLine = line.split(";");
                if (Objects.equals(thatLine[0], countryname1)) {
                    for (int x = 1; x < thatLine.length; x++) {
                        if (Objects.equals(thatLine[x], countryname2)) {
                            return true;
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    private ArrayList < Territoire > getAllCountriesName() {
        /*Obtenir la liste de tous les pays*/
        File folder = new File("./Terre/countries_png");
        File[] listOfFiles = folder.listFiles();
        ArrayList listCountries = new ArrayList();
        for (File file: listOfFiles) {
            if (file.isFile()) {
                String filename = new File(String.valueOf(file)).getName().replaceFirst("[.][^.]+$", "");
                listCountries.add(new Territoire(filename));
            }
        }
        return listCountries;
    }

    boolean checkIfThisIsOneOfMyCountry(Joueur joueur, String country) {
        /* Renvoie true si country appartient au joueur*/
        for (Territoire territoire: joueur.listTerritoires) {
            if (Objects.equals(country, territoire.getName())) {
                return true;
            }
        }


        return false;
    }

    int[] getRandomXYOfACountry(String countryName) {
        /*Renvoie des coordonnées x et y aléatoires dans un pays*/
        int x = 0;
        int y = 0;
        Random rand = new Random();
        try {
            String line;
            BufferedReader positionsInit = Main.readTextFile("./Terre/positionsinit.txt");
            while ((line = positionsInit.readLine()) != null) {
                String[] thatLine = line.split(";");
                if (Objects.equals(thatLine[0], countryName)) {
                    while (!Objects.equals(getCountryName(x, y), countryName)) {
                        x = Integer.parseInt(thatLine[1]) + rand.nextInt((20 + 20) + 1) - 20;
                        y = Integer.parseInt(thatLine[2]) + rand.nextInt((20 + 20) + 1) - 20;

                    }
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        int[] xy = {
                x,
                y
        };
        return xy;
    }

    private ArrayList < Region > getAllRegions() {
        /*Obtenir la liste de toutes les régions*/
        ArrayList < Region > listAllRegions = new ArrayList < > ();
        try {
            String line;
            BufferedReader continents = new BufferedReader(new FileReader("./Terre/continents.txt"));
            while ((line = continents.readLine()) != null) {
                String[] thatLine = line.split(";");

                listAllRegions.add(new Region(thatLine[1], Integer.parseInt(thatLine[0])));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return listAllRegions;
    }


    private ArrayList < Region > getRegions(ArrayList < Territoire > listTerritoires) {
        /*Obtenir la liste des régions conquises selon une liste de territoires*/


        ArrayList < Region > listRegions = new ArrayList < > ();

        ArrayList < Region > listAllRegions = getAllRegions();

        for (Region region: listAllRegions) {
            try {
                String line;
                BufferedReader continents = new BufferedReader(new FileReader("./Terre/continents.txt"));

                while ((line = continents.readLine()) != null) {
                    String[] thatLine = line.split(";");
                    int count = 0;
                    if (Objects.equals(thatLine[1], region.getName())) {
                        for (int j = 2; j < thatLine.length; j++) {
                            for (Territoire territoire: listTerritoires) {
                                if (Objects.equals(thatLine[j], territoire.getName())) {
                                    count += 1;
                                }

                            }
                        }
                        if (count == thatLine.length - 2) {
                            listRegions.add(region);
                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return listRegions;
    }

    int totalNbUnites(ArrayList < Joueur > joueurs) {
        /*Compte le nombre d'unités non posées*/
        int n = 0;
        for (Joueur joueur: joueurs) {
            n += joueur.nbUnites;
        }
        return n;
    }

    Joueur attributionUnites(Joueur joueur) {
        /*Permet d'attribuer des unités au début de chaque tour */
        int nbTerritoires = joueur.listTerritoires.size();
        joueur.nbUnites += nbTerritoires / 3;

        ArrayList < Region > regions = getRegions(joueur.listTerritoires);
        if (regions.size() > 0) {
            for (Region region: regions) {
                joueur.nbUnites += region.getWeight();
            }
        }

        Random rand = new Random();
        for (int i = 0; i < joueur.nbTerritoiresCapturesTourPrec; i++) {
            if ((rand.nextInt(10) + 1) > 5) {
                joueur.nbUnites += 1;
            }
        }


        if (joueur.nbUnites < 2) {
            joueur.nbUnites = 2;
        }
        return joueur;
    }

    private ArrayList < Unite > getAllUnitsinTerritoire(String countryName, ArrayList < Unite > AllUnits) {
        /* Permet d'obtenir toutes les unités dans un territoire */
        ArrayList < Unite > AllUnitsinTerritoire = new ArrayList < > ();
        for (Unite unite: AllUnits) {
            if (Objects.equals(getCountryName(unite.positionx, unite.positiony), countryName)) {
                AllUnitsinTerritoire.add(unite);
            }
        }

        return AllUnitsinTerritoire;
    }

    Unite checkIfDeplacementIsPossible(Joueur joueur, int x, int y) {
        /*Vérifie si le pays sur lequel j'ai cliqué m'appartient ou non*/

        ArrayList < Unite > allUnits = getAllUnitsinTerritoire(getCountryName(x, y), joueur.listUnites);

        if (SelectionUnite.size() + 1 == allUnits.size()) {
            return null;
        }

        if (allUnits.size() > 1 && SelectionUnite.size() < 3) {

            for (Unite unite: allUnits) {

                String classOfUnite = unite.getClass().getName();
                int uniteX = unite.positionx;
                int uniteY = unite.positiony;
                BufferedImage imgOfUnite = Main.ImageReader(classOfUnite + ".png");

                int xToCheck = x - uniteX;
                int yToCheck = y - uniteY;

                if (0 <= xToCheck && xToCheck <= 32 && 0 <= yToCheck && yToCheck <= 32) {
                    Color color = new Color(imgOfUnite.getRGB(16, 16));
                    int red = color.getRed();
                    int green = color.getGreen();
                    int blue = color.getBlue();
                    if (red < 255 || green < 255 || blue < 255) {
                        for (Unite unitSelect: SelectionUnite) {
                            if (Objects.equals(unite, unitSelect)) {
                                return null;
                            }

                        }

                        SelectionUnite.add(unite);
                        return unite;
                    }
                }

            }
        }

        return null;


    }


    boolean checkIfWin(Joueur joueur) {
        /*Vérifie si un joueur a gagné*/
        return joueur.listTerritoires.size() == 42 && win(joueur);
    }

    boolean win(Joueur joueur) {
        /* Fonction qui annonce au joueur qu'il a gagné*/
        String rejouer = "Rejouer";
        String quitter = "Quitter";
        String[] bouton = {
                rejouer,
                quitter
        };
        BufferedImage iconejoueur = Main.ImageReader("iconejoueur.png");
        iconejoueur = Main.changeColor(iconejoueur, joueur.couleur);
        ImageIcon imageIcon = new ImageIcon(iconejoueur);
        JOptionPane jop = new JOptionPane();
        int rang = -1;
        while (rang == -1) {
            System.out.println(rang);
            rang = JOptionPane.showOptionDialog(null,
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

            SelectionUnite = new ArrayList < > ();

        }


        return true;
    }


 /*
     public static String OldgetCountryName(int x, int y) {
         File folder = new File("./Terre/countries_png");
         File[] listOfFiles = folder.listFiles();

         for (File file : listOfFiles) {
             if (file.isFile()) {
                 String filename = new File(String.valueOf(file)).getName().replaceFirst("[.][^.]+$", "");
                 try {
                     BufferedImage country = ImageIO.read(file);
                     Color color = new Color(country.getRGB(x-Map.x_adapt, y-Map.y_adapt));
                     int red = color.getRed();
                     int green = color.getGreen();
                     int blue = color.getBlue();
                     if (red < 255 || green < 255 || blue < 255) {
                         return (filename);
                     }
                 } catch (IOException e) {

                     e.printStackTrace();

                 }


             }
         }
         return ("C'est pas l'homme qui prend la mer, c'est la mer qui prend l'homme");


     }
 */
}