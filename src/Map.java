/**
 * Created by Alphonse on 14/05/2018.
 */

import java.awt.*;


import java.awt.event.MouseAdapter;
import java.awt.image.BufferedImage;
import java.io.File;



import java.io.IOException;

import javax.imageio.ImageIO;

import javax.swing.*;

import java.awt.event.MouseEvent;



public class Map extends JPanel {

    private int x_adapt = 15;
    private int y_adapt = 15;

    public void paintComponent(Graphics g){

        try {

            super.paintComponent(g);
            File imageFile1 = new File("borders.png");
            BufferedImage img = ImageIO.read(imageFile1);
            System.out.println("Bonjour");

            setBackground(new Color(149, 193,168));

            File folder = new File("countries_png");
            File[] listOfFiles = folder.listFiles();

            for (File file : listOfFiles) {
                if (file.isFile()) {
                    BufferedImage country = ImageIO.read(file);
                    g.drawImage(country, x_adapt, y_adapt, this);
                }
            }



            g.drawImage(img, x_adapt, y_adapt, this);

            this.addMouseListener(new MouseAdapter() {
                @Override
                public void mousePressed(MouseEvent e) {
                    int x = e.getX();
                    int y = e.getY();
                    System.out.println(x);
                    System.out.println(y);
                    String countryClicked = getCountry(x, y);
                    System.out.println(countryClicked);
                }
            });



        } catch (IOException e) {

            e.printStackTrace();

        }



    }

    public String getCountry(int x, int y)  {
        File folder = new File("countries_png");
        File[] listOfFiles = folder.listFiles();

        for (File file : listOfFiles) {
            if (file.isFile()) {
                String filename = new File(String.valueOf(file)).getName().replaceFirst("[.][^.]+$", "");
                try {
                BufferedImage country = ImageIO.read(file);
                Color color = new Color(country.getRGB(x - x_adapt, y - y_adapt));
                int red = color.getRed();
                int green = color.getGreen();
                int blue = color.getBlue();
                if (red < 255 || green < 255 || blue < 255) {
                    return(filename);
                }} catch (IOException e) {

                    e.printStackTrace();

                }


            }
        }
        return("C'est pas l'homme qui prend la mer, c'est la mer qui prend l'homme");




    }



}