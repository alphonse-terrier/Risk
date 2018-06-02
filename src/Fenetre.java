/**
 * Created by Alphonse on 14/05/2018.
 */

import javax.swing.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class Fenetre extends JFrame {

    public Fenetre() {

        this.setTitle("Jeu Risk par Aymeric Bès de Berc & Alphonse Terrier");

        this.setSize(1000, 690);

        this.setLocationRelativeTo(null);

        this.setResizable(false);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
        Map map = new Map();
        map.addMouseListener(new MyMouseListener() {
            @Override
            public void mouseClicked(MouseEvent event) {
                super.mouseClicked(event);
            }
        });
        this.setContentPane(map);


    }


}

abstract class MyMouseListener implements MouseListener {

    public void mouseClicked(MouseEvent event) {
        int x = event.getX();
        int y = event.getY();

        String countryClicked = Territoire.getCountryName(x, y);
        System.out.println(countryClicked);


    }

    public void mouseEntered(MouseEvent event) {
    }

    public void mouseExited(MouseEvent event) {
    }

    public void mousePressed(MouseEvent event) {
    }

    public void mouseReleased(MouseEvent event) {
    }
}


