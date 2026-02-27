package EliminatoriasCore.biblioteca;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

public class BracketMundial extends JFrame {

    public BracketMundial(){

    }

    public static void BracketMundial1(BracketMundial bm) {
        bm.setTitle("Cuadro de Eliminación Directa - 32 Equipos");
        bm.setSize(750, 450);
        bm.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        bm.setLocationRelativeTo(null);
        bm.setLayout(new GridLayout(1,7));

        JPanel p16vos1 = new JPanel(new GridLayout(8,1));
        JPanel p8vos1 = new JPanel(new GridLayout(4,1));
        JPanel p4tos1 = new JPanel(new GridLayout(2,1));
        JPanel pSemi1 = new JPanel(new GridLayout(1,1));
        JPanel pFinal = new JPanel(new GridLayout(3,1));
        JPanel pSemi2 = new JPanel(new GridLayout(1,1));
        JPanel p4tos2 = new JPanel(new GridLayout(2,1));
        JPanel p8vos2 = new JPanel(new GridLayout(4,1));
        JPanel p16vos2 = new JPanel(new GridLayout(8,1));

        bm.add(p16vos1);
        bm.add(p8vos1);
        bm.add(p4tos1);
        bm.add(pSemi1);
        bm.add(pFinal);
        bm.add(pSemi2);
        bm.add(p4tos2);
        bm.add(p8vos2);
        bm.add(p16vos2);

        p16vos1.setBackground(Color.ORANGE);
        p8vos1.setBackground(Color.GREEN);
        p4tos1.setBackground(Color.RED);
        pSemi1.setBackground(Color.BLUE);
        pFinal.setBackground(Color.YELLOW);
        pSemi2.setBackground(Color.BLUE);
        p4tos2.setBackground(Color.RED);
        p8vos2.setBackground(Color.GREEN);
        p16vos2.setBackground(Color.ORANGE);

        JLabel[] duelos = new JLabel[32];
        for (int i = 0; i < duelos.length; i++) {
            Random r = new Random();
            Random r1 = new Random();
            Random r2 = new Random();

            int i1 = (int) r.nextInt(256); 
            int i2 = (int) r1.nextInt(256); 
            int i3 = (int) r2.nextInt(256); 

            JLabel l = new JLabel(String.valueOf(i+1), SwingConstants.CENTER);
            l.setOpaque(true);
            l.setBackground(new Color(i1,i2,i3));
            duelos[i] = l;
        }

        //16vos
        for (int i = 0; i < 8; i++) {
            p16vos1.add(duelos[i]);
        }
        for (int i = 8; i < 16; i++) {
            p16vos2.add(duelos[i]);
        }
        //8vos
        for (int i = 16; i < 20; i++) {
            p8vos1.add(duelos[i]);
        }
        for (int i = 20; i < 24; i++) {
            p8vos2.add(duelos[i]);
        }
        //4tos
        for (int i = 24; i < 26; i++) {
            p4tos1.add(duelos[i]);
        }
        for (int i = 26; i < 28; i++) {
            p4tos2.add(duelos[i]);
        }
        //semis
        for (int i = 28; i < 29; i++) {
            pSemi1.add(duelos[i]);
        }
        for (int i = 29; i < 30; i++) {
            pSemi2.add(duelos[i]);
        }

        //final
        pFinal.add(new JLabel("Ultima y nos vamos"));
        for (int i = 31; i > 29; i--) {
            pFinal.add(duelos[i]);
        }


    }


    public static void main(String[] args) {
        BracketMundial bm = new BracketMundial();
        BracketMundial1(bm);
        bm.setVisible(true);
    }
}