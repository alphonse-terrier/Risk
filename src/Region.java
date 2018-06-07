
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


    String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    private String name;
    private int weight;





}