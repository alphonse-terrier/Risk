/**
 * Created by Alphonse on 14/05/2018.
 */

import javax.swing.*;
import java.awt.event.MouseAdapter;
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
        this.setContentPane(new Map());


    }


}