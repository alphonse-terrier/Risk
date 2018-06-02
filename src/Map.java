/**
 * Created by Alphonse on 14/05/2018.
 */

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.ImageObserver;


public class Map extends JPanel {

    public static int x_adapt = 15;
    public static int y_adapt = 15;

    public static ImageObserver imgobs = new ImageObserver() {
        @Override
        public boolean imageUpdate(Image img, int infoflags, int x, int y, int width, int height) {
            return false;
        }
    };

    public void paintComponent(final Graphics g) {


        Main.drawImage("countriesborders.png", x_adapt, y_adapt, g);

        /**
        this.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                int x = e.getX() - x_adapt;
                int y = e.getY() - y_adapt;

                //String countryClicked = Territoire.getCountryName(x, y);
                //System.out.println(countryClicked);
            }
        });
        */
    }


}


