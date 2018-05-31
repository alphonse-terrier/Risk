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

    public static int x_adapt = 15;
    public static int y_adapt = 15;

    public void paintComponent(Graphics g){

        try {

            File imageFile1 = new File("borders.png");
            BufferedImage img = ImageIO.read(imageFile1);



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
                    String countryClicked = Territoire.getCountryName(x, y);
                    System.out.println(countryClicked);
                }
            });



        } catch (IOException e) {

            e.printStackTrace();

        }



    }





}