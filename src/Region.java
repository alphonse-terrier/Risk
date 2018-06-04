
import java.util.*;

import java.io.FileReader;

import java.io.BufferedReader;

/**
 *
 */
public class Region {

    public Region(String name, int weight) {
        this.name = name;
        this.weight = weight;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    private String name;
    private int weight;


    public static ArrayList<Region> getAllRegions() {
        ArrayList<Region> listAllRegions = new ArrayList<Region>();
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


    public static ArrayList<Region> getRegions(ArrayList<Territoire> listTerritoires) {
        ArrayList<Region> listRegions = null;

        ArrayList<Region> listAllRegions = getAllRegions();

        for (int i = 0; i < listAllRegions.size(); i++) {
            try {
                String line;
                BufferedReader continents = new BufferedReader(new FileReader("./Terre/continents.txt"));

                while ((line = continents.readLine()) != null) {
                    String[] thatLine = line.split(";");
                    int count = 0;
                    if (Objects.equals(thatLine[1], listAllRegions.get(i).getName())) {
                        for (int j = 2; j < thatLine.length; j++) {
                            for (int k = 0; k < listTerritoires.size(); k++) {
                                if (Objects.equals(thatLine[j], listTerritoires.get(k).getName())) {
                                    count += 1;
                                }

                            }
                        }
                        if (count == thatLine.length - 2) {
                            listRegions.add(listAllRegions.get(i));
                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return listRegions;
    }


}