package EliminatoriasCore.biblioteca;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;


import Global.Equipo;
import Global.Grupo;

public class InterfazGrupos {
    public static JFrame frame1 = new JFrame("Fase de grupos");
    public static JButton abrir3rosBtn = new JButton("Abrir mejores terceros");
    public static void inicializarInterfazGrupos(){
        frame1.setLayout(new BorderLayout());
        frame1.setSize(800, 650);
        frame1.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame1.setLocationRelativeTo(null);
        crearInterfazGrupos();
    }
    public static void crearInterfazGrupos(){
        
        Grupo[] gruposEstaticos = Data.getGrupos();

        // Limpiar el contenido anterior para evitar duplicaciones
        frame1.getContentPane().removeAll();
        
        FondoPanel pMain = new FondoPanel("/EliminatoriasCore/images/fondo_grupos.png");
        pMain.setLayout(new BorderLayout());
        JPanel pCentro = new JPanel(new GridLayout(3, 4));
        pCentro.setOpaque(false);
        pCentro.setBorder(new EmptyBorder(20, 20, 20, 20));
        pMain.add(pCentro);
        frame1.add(pMain);
        
        anadirPCentro(gruposEstaticos, pCentro);
        
        JPanel pLateral = new JPanel(new GridLayout(15,1));

        if (abrir3rosBtn.getText().equals("Abrir mejores terceros")) {
            pLateral.setVisible(false);
        } else if (abrir3rosBtn.getText().equals("Cerrar mejores terceros")) {
            pLateral.setVisible(true);
        }

        JPanel pNorth = new JPanel(new BorderLayout());
        pNorth.setOpaque(false);
        pNorth.add(abrir3rosBtn, BorderLayout.EAST);
        pMain.add(pNorth, BorderLayout.NORTH);

        abrir3rosBtn.addActionListener(e->{
            if (abrir3rosBtn.getText().equals("Abrir mejores terceros")) {
                pLateral.setVisible(true);
                abrir3rosBtn.setText("Cerrar mejores terceros");
            } else if (abrir3rosBtn.getText().equals("Cerrar mejores terceros")) {
                pLateral.setVisible(false);
                abrir3rosBtn.setText("Abrir mejores terceros");
            }
            
        });

        anadirPLateral(gruposEstaticos, pLateral);

        pMain.add(pLateral, BorderLayout.EAST);
        pMain.revalidate();
        pMain.repaint();
        pMain.add(pCentro, BorderLayout.CENTER);
    }

    public static void imprimirGrupo(Grupo gr){
        JFrame frame1 = new JFrame("Grupo " + gr.getNombre());
        frame1.setLayout(new GridLayout(4,1));
        frame1.setSize(200,300);
        frame1.setLocationRelativeTo(null);
        frame1.setVisible(true);
        for (Equipo e : gr.getEquipoList()) {
            frame1.add(new JLabel(e.getNombre()));
        }
    }

    public static void anadirPCentro(Grupo[] gruposEstaticos, JPanel pCentro){
        for (Grupo grupo : gruposEstaticos) {
            JPanel panelGrupo = new JPanel(new GridLayout(5, 1));
            panelGrupo.setBackground(new Color(0,0,0,180));
            panelGrupo.setOpaque(true);
            panelGrupo.setBorder(new EmptyBorder(5, 10, 5, 10));
            anadirGrupo(grupo, panelGrupo);
            for (Component c : panelGrupo.getComponents()) {
                if (c instanceof JLabel) {
                    // Obtener el texto del JLabel
                    JLabel label = (JLabel) c;
                    String texto = label.getText();
                    JButton groupBtn = new JButton(texto);
                    groupBtn.setFont(new Font("Arial", Font.BOLD, 16));

                    texto = String.valueOf(texto.charAt(6));//ej: GRUPO A, "A" está en el espacio 6

                    panelGrupo.remove(c);

                    panelGrupo.add(groupBtn, 0);
                    
                    for (int i = 0; i < gruposEstaticos.length; i++) {
                        if (gruposEstaticos[i].getNombre().equals(texto)) {
                            final int index = i;
                            groupBtn.addActionListener(__->{
                                gruposEstaticos[index].pantalla.setVisible(true);
                            });
                        }
                    }
                    panelGrupo.revalidate();
                    panelGrupo.repaint();
                    break;
                }
            }
            pCentro.add(panelGrupo);
        }
    }

    public static void anadirPLateral(Grupo[] gruposEstaticos, JPanel pLateral){
        pLateral.add(new JLabel("Grupo de 3eros"));
        pLateral.setBackground(new Color(255,255,255,100));
        pLateral.setBorder(new EmptyBorder(30,30,30,30));
                JPanel pp1 = new JPanel(new GridBagLayout());
                GridBagConstraints r1 = new GridBagConstraints();
                r1.gridx = 0;
                r1.gridy = 0;
                pp1.add(new JLabel("Equipo   "));
                r1.gridy = 1;
                pp1.add(new JLabel(String.valueOf("  DF "), 0));
                r1.gridy = 2;
                pp1.add(new JLabel(String.valueOf("  Pun"), SwingConstants.RIGHT));
                pLateral.add(pp1);
        
        Grupo terceros = new Grupo("terceros", new ArrayList<>());
        for (Grupo grupo2 : gruposEstaticos) {
            terceros.getEquipoList().add(grupo2.getEquipoList().get(2));
        }
        Grupo.ordenarEquiposBurbuja(terceros.getEquipoList());

        for (int i = 0; i < gruposEstaticos.length; i++) {
            JPanel pp = new JPanel(new GridBagLayout());
            GridBagConstraints r = new GridBagConstraints();
                r.gridx = 0;
                r.gridy = i;
            if (i >= 0 && i < 8) {
                pp.setBackground(new Color(67, 255, 0,150));
            } else {
                pp.setBackground(new Color(255,0,0,150));
            }
            pp.setBorder(new EmptyBorder(5,5,5,5));
                
            JLabel lName = new JLabel(terceros.getEquipoList().get(i).getNombre().substring(0,3).toUpperCase() + "  ");
            lName.setIcon(terceros.getEquipoList().get(i).getImg());
            pp.add(lName,r);
            r.gridx = 1;
            pLateral.add(pp);
            pp.add(new JLabel("  " + String.valueOf(terceros.getEquipoList().get(i).getDifGol()) + "  "),r);
            pLateral.add(pp);
            r.gridx = 2;
            pp.add(new JLabel("  " + String.valueOf(terceros.getEquipoList().get(i).getPuntos()), SwingConstants.RIGHT),r);
            pLateral.add(pp);
        }
    }

    public static void anadirGrupo(Grupo grupo, JPanel panel){
        //Crear arreglo de Labels
        JLabel[] lEq = new JLabel[4];
        //Reutilizamos variable temp para el titulo y para mover el orden de los eqs en "orden intercalado"
        JLabel temp = new JLabel("GRUPO " + grupo.getNombre());
        
        panel.add(temp);
        //Añadir nombres de los equipos a los Labels
        for (int i = 0; i < lEq.length; i++) {
            lEq[i] = new JLabel("");
        }
        for (int i = 0; i < grupo.getEquipoList().size(); i++) {
            if (grupo.getEquipoList().get(i) != null) {
                lEq[i].setFont(new Font("Arial", Font.BOLD, 12));
                lEq[i].setText(grupo.getEquipoList().get(i).getNombre());
                lEq[i].setIcon(grupo.getEquipoList().get(i).getImg());
                lEq[i].setHorizontalAlignment(SwingConstants.LEFT);
                lEq[i].setBackground(new Color(255,255,255,180));
                lEq[i].setOpaque(true);
                lEq[i].setForeground(Color.black);
                frame1.revalidate();
                frame1.repaint();
                if (grupo.getEquipoList().get(i).getPartidosJugados() != 0) { //atributo que indica que un grupo ya jugó
                    if (i == 0 || i == 1) {
                        lEq[i].setBackground(new Color(67, 255, 0,150));
                    } else if (i == 2) {
                        lEq[i].setBackground(new Color(255,218,0,150));
                    } else if (i == 3){
                        lEq[i].setBackground(new Color(255,0,0,150));
                    }
                }
                    
            }
        }
        //Añadir Labels
        for (JLabel jLabel : lEq) {
            // 1. Creamos el borde vacío (el margen interno o padding)
            JPanel p = new JPanel(new GridLayout(1,1));
            p.setBorder(BorderFactory.createEmptyBorder(2,2,2,2));
            p.setOpaque(false);
            p.add(jLabel);
            panel.add(p);
        }
    }

    
    
}
