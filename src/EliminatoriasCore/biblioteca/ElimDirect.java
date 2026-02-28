package EliminatoriasCore.biblioteca;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;

import java.awt.*;
import java.util.Random;

public class ElimDirect extends JFrame {

    public ElimDirect() {
    }

    public static void BracketMundial1(ElimDirect bm) {
        bm.setTitle("Cuadro de Eliminación Directa - 32 Equipos");
        bm.setSize(1200, 700);
        bm.setLocationRelativeTo(null);
        
        // Cambiamos a GridBagLayout para todo el frame
        bm.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;

        // Crear los 32 labels con tu lógica original de colores aleatorios
        JPanel[] pDuelos = new JPanel[32];
        Random r = new Random(); // Un solo objeto Random para eficiencia

        for (int i = 0; i < pDuelos.length; i++) {
            int i1 = r.nextInt(256);
            int i2 = r.nextInt(256);
            int i3 = r.nextInt(256);

            JPanel p = new JPanel(new GridLayout(2,1));
            p.setOpaque(true);
            //l.setBackground(new Color(i1, i2, i3));
            Border b1 = BorderFactory.createEmptyBorder(10,10,10,10);//margen
            Border b2 = BorderFactory.createLineBorder(Color.BLACK);//linea borde
             Border bordeCompuesto = BorderFactory.createCompoundBorder(b1,b2);
            p.setBorder(bordeCompuesto);
            p.setFont(new Font("Arial", Font.BOLD, 16));
            //l.setBorder(new EmptyBorder(10,10,10,10));
            pDuelos[i] = p;
        }

        for (JPanel jPanel : pDuelos) {
            jPanel.add(new JLabel("Local"));
            jPanel.add(new JLabel("Visitante"));
        }



        // --- LÓGICA DE POSICIONAMIENTO (10 columnas x 8 filas) ---

        // 16vos Lado 1 (Columna 0, cada uno ocupa 1 fila)
        gbc.gridx = 0;
        gbc.gridheight = 1;
        for (int i = 0; i < 8; i++) {
            gbc.gridy = i;
            bm.add(pDuelos[i], gbc);
        }

        // 8vos Lado 1 (Columna 1, cada uno ocupa 2 filas)
        gbc.gridx = 1;
        gbc.gridheight = 2;
        for (int i = 0; i < 4; i++) {
            gbc.gridy = i * 2;
            bm.add(pDuelos[16 + i], gbc); // Índices 16-19 según tu lógica
        }

        // 4tos Lado 1 (Columna 2, cada uno ocupa 4 filas)
        gbc.gridx = 2;
        gbc.gridheight = 4;
        for (int i = 0; i < 2; i++) {
            gbc.gridy = i * 4;
            bm.add(pDuelos[24 + i], gbc); // Índices 24-25
        }

        // Semis Lado 1 (Columna 3, ocupa las 8 filas)
        gbc.gridx = 3;
        gbc.gridheight = 8;
        gbc.gridy = 0;
        bm.add(pDuelos[28], gbc); // Índice 28

        // COLUMNA CENTRAL: FINAL (Columnas 4 y 5)
        gbc.gridheight = 2; // Título
        gbc.gridx = 4;
        gbc.gridwidth = 2;
        gbc.gridy = 1;
        bm.add(new JLabel("MI BOMBOO", SwingConstants.CENTER), gbc);
        
        gbc.gridy = 3;
        bm.add(pDuelos[31], gbc); // Índice 31
        
        gbc.gridy = 5;
        bm.add(pDuelos[30], gbc); // Índice 30
        
        // Reset gridwidth para el resto
        gbc.gridwidth = 1;

        // Semis Lado 2 (Columna 6, ocupa 8 filas)
        gbc.gridx = 6;
        gbc.gridheight = 8;
        gbc.gridy = 0;
        bm.add(pDuelos[29], gbc); // Índice 29

        // 4tos Lado 2 (Columna 7, cada uno ocupa 4 filas)
        gbc.gridx = 7;
        gbc.gridheight = 4;
        for (int i = 0; i < 2; i++) {
            gbc.gridy = i * 4;
            bm.add(pDuelos[26 + i], gbc); // Índices 26-27
        }

        // 8vos Lado 2 (Columna 8, cada uno ocupa 2 filas)
        gbc.gridx = 8;
        gbc.gridheight = 2;
        for (int i = 0; i < 4; i++) {
            gbc.gridy = i * 2;
            bm.add(pDuelos[20 + i], gbc); // Índices 20-23
        }

        // 16vos Lado 2 (Columna 9, cada uno ocupa 1 fila)
        gbc.gridx = 9;
        gbc.gridheight = 1;
        for (int i = 0; i < 8; i++) {
            gbc.gridy = i;
            bm.add(pDuelos[8 + i], gbc); // Índices 8-15
        }
    }

    public static void main(String[] args) {
        ElimDirect bm = new ElimDirect();
        BracketMundial1(bm);
        bm.setVisible(true);
    }
}