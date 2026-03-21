package FaseGruposCore.biblioteca;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;

import FaseEliminacionCore.ElimDirectInterfaz;
import Global.Data;
import Global.Duelo;
import Global.Equipo;
import Global.Grupo;

public class InterfazGrupos {
    public static JFrame frame1 = new JFrame("Fase de grupos");
    public static JButton abrir3rosBtn = new JButton("3rds <");
    public static boolean pLateralAbierto = false;
    public static JButton jugarAutoBtn = new JButton("Jugar autom.");
    public static void inicializarInterfazGrupos(){
        abrir3rosBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
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

        Grupo t3ros = new Grupo("3eros", Data.getTerceros());
        
        FondoPanel pMain = new FondoPanel("/FaseGruposCore/images/fondo2.png");
        pMain.setLayout(new BorderLayout());
        JPanel pCentro = new JPanel(new BorderLayout());
        pCentro.setOpaque(false);

        JPanel pCentro1 = new JPanel(new GridLayout(3, 4));
        pCentro1.setOpaque(false);
        pCentro1.setBorder(new EmptyBorder(20, 20, 20, 50));
        pCentro.add(pCentro1, BorderLayout.CENTER);
        pMain.add(pCentro);
        frame1.add(pMain);
        
        anadirpCentro1(gruposEstaticos, pCentro1);
        
        JPanel pLateral = new JPanel(new BorderLayout());

        abrir3rosBtn.setBackground(new Color(193, 159, 85));
        abrir3rosBtn.setFocusPainted(false);

        if (!pLateralAbierto) {
            abrir3rosBtn.setText("3rds <");
            pLateral.setVisible(false);
        } else if (pLateralAbierto) {
            abrir3rosBtn.setText("3rds >");
            pLateral.setVisible(true);
        }

        JButton abrirElimDirect = new JButton("Eliminación directa");
        abrirElimDirect.setBackground(new Color(193, 159, 85));
        abrirElimDirect.setFocusPainted(false);
        abrirElimDirect.addActionListener(e->{
            ElimDirectInterfaz.ordenarElimDirect();
            ElimDirectInterfaz.bm.setVisible(true);
        });

        JButton informesBtn = new JButton("Informes");
        informesBtn.setBackground(new Color(193, 159, 85));
        informesBtn.setFocusPainted(false);
        informesBtn.addActionListener(e->{
            for (Grupo g : gruposEstaticos) {
                System.out.println("----- Grupo " + g.getNombre() + " -----");
                for (Equipo e1 : g.getEquipoList()) {
                    System.out.println(e1.getNombre());
                }
            }
            System.out.println("----- Grupo de terceros - DG -- Puntos --");
            for (Equipo e2 : Data.getTerceros()) {
                System.out.println(e2.getNombre() + "  " + e2.getDifGol() + "  " + e2.getPuntos());
            }
            System.out.println("----- 16avos de final en orden --");
            for (int i = 0; i < 16; i++) {
                System.out.println(Data.getDuelosElimDirect().get(i).getlocal().getNombre() + " vs " + Data.getDuelosElimDirect().get(i).getvisitante().getNombre());
            }
            System.out.println("--------");
            
        });
        
        jugarAutoBtn.setBackground(new Color(193, 159, 85));
        jugarAutoBtn.setFocusPainted(false);
        jugarAutoBtn.addActionListener(e->{
            for (Grupo gr : gruposEstaticos) {
                for (ArrayList<Duelo> fecha : gr.getCalendarioList()) {
                    for (Duelo dl : fecha) {
                        if (dl.getlocal().getPartidosJugados() < 3) {
                            int[] goles = Equipo.simularPartido(dl.getlocal(), dl.getvisitante());
                            Bib.asignarGolesADuelo(dl.getlocal(), dl.getvisitante(), gr.getCalendarioList().indexOf(fecha), goles[0], goles[1], gr.getCalendarioList());
                            Equipo.jugarPartido(dl.getlocal(), dl.getvisitante(), goles[0], goles[1]);
                        }
                    }
                }
                gr.pantalla.ordenarTabla(gr.getEquipoList());
            }
            //Reordenar terceros
            Data.getTerceros().clear();
            for (Grupo grupo2 : gruposEstaticos) {
                Data.getTerceros().add(grupo2.getEquipoList().get(2));
            }
            Grupo.ordenarEquiposBurbuja(Data.getTerceros());

            pCentro1.removeAll();
            pLateral.removeAll();
            anadirpCentro1(gruposEstaticos, pCentro1);
            anadirPLateral(gruposEstaticos, pLateral, t3ros);
            ElimDirectInterfaz.ordenarElimDirect();

            jugarAutoBtn.setEnabled(false);
            pMain.revalidate();
            pMain.repaint();
        });

        JPanel pNorth = new JPanel(new BorderLayout());
        pNorth.setOpaque(false);

        JPanel pNorth1 = new JPanel(new FlowLayout());
        pNorth1.add(abrirElimDirect);
        pNorth1.add(jugarAutoBtn);
        pNorth1.add(informesBtn);
        pNorth1.setOpaque(false);

        pNorth.add(abrir3rosBtn, BorderLayout.EAST);
        JLabel lTitle = new JLabel("FASE DE GRUPOS");
        lTitle.setFont(new Font("Arial", Font.BOLD, 16));
        lTitle.setForeground(Color.WHITE);

        pNorth.add(pNorth1, BorderLayout.CENTER);

        pNorth.add(lTitle, BorderLayout.WEST);
        pCentro.add(pNorth, BorderLayout.NORTH);

        abrir3rosBtn.addActionListener(e->{
            if (!pLateralAbierto) {
                pLateral.setVisible(true);
                abrir3rosBtn.setText("3rds >");
                pCentro1.setBorder(new EmptyBorder(20, 20, 20, 20));
                pLateralAbierto = true;
            } else if (pLateralAbierto) {
                pLateral.setVisible(false);
                abrir3rosBtn.setText("3rds <");
                pCentro1.setBorder(new EmptyBorder(20, 20, 20, 50));
                pLateralAbierto = false;
            }
        });

        Data.getTerceros().clear();
        
        for (Grupo grupo2 : gruposEstaticos) {
            Data.getTerceros().add(grupo2.getEquipoList().get(2));
        }

        Grupo.ordenarEquiposBurbuja(Data.getTerceros());

        anadirPLateral(gruposEstaticos, pLateral, t3ros);

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

    public static void anadirpCentro1(Grupo[] gruposEstaticos, JPanel pCentro1){
        for (Grupo grupo : gruposEstaticos) {
            JPanel panelGrupo = new JPanel(new GridLayout(5, 1));
            panelGrupo.setBackground(new Color(255,255,255,0)); //invisible
            panelGrupo.setOpaque(true);
            panelGrupo.setBorder(new EmptyBorder(5, 10, 5, 10));
            anadirGrupo(grupo, panelGrupo);
            for (Component c : panelGrupo.getComponents()) {
                if (c instanceof JLabel) {
                    // Obtener el texto del JLabel
                    JLabel label = (JLabel) c;
                    String texto = label.getText();
                    JButton groupBtn = new JButton(texto);
                    groupBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
                    groupBtn.setFont(new Font("Arial", Font.BOLD, 16));
                    groupBtn.setBackground(new Color(193, 159, 85));
                    groupBtn.setFocusPainted(false);

                    texto = String.valueOf(texto.charAt(6));//ej: "GRUPO A", "A" está en el espacio 6

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
            pCentro1.add(panelGrupo);
        }
    }

    public static void anadirPLateral(Grupo[] gruposEstaticos, JPanel pLateral, Grupo terceros){
        
        pLateral.setBackground(new Color(0,0,0,100));
        pLateral.setBorder(new EmptyBorder(30,30,30,30));

        JPanel pLateral1 = new JPanel(new GridLayout(15,1));
        JPanel pLateral2 = new JPanel(new GridLayout(15,2));
        pLateral1.setOpaque(false);
        pLateral2.setOpaque(false);

        anadirGrupo(terceros, pLateral1);
        UIManager.put("Label.foreground", Color.WHITE);
        pLateral2.add(new JLabel("DF"));
        pLateral2.add(new JLabel("Pun"));
        for (Equipo e : terceros.getEquipoList()) {
            JLabel l = new JLabel(String.valueOf(e.getDifGol()), SwingConstants.CENTER);
            l.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.white));
            pLateral2.add(l);
            JLabel l2 = new JLabel(String.valueOf(e.getPuntos()), SwingConstants.CENTER);
            l2.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.white));
            pLateral2.add(l2);
        }
        UIManager.put("Label.foreground", Color.BLACK);
        pLateral.add(pLateral1, BorderLayout.CENTER);
        pLateral.add(pLateral2, BorderLayout.EAST);

        Grupo.ordenarEquiposBurbuja(terceros.getEquipoList());
    }

    public static void anadirGrupo(Grupo grupo, JPanel panel){
        //Crear arreglo de Labels
        JLabel[] lEq = new JLabel[grupo.getEquipoList().size()];
        //Reutilizamos variable temp para el titulo y para mover el orden de los eqs en "orden intercalado"
        JLabel temp = new JLabel("GRUPO " + grupo.getNombre());
        if (grupo.getEquipoList().size() == 12) {
            temp.setText("GRUPO " + grupo.getNombre());
            temp.setForeground(Color.white);
        }
        
        panel.add(temp);

        //Añadir nombres de los equipos a los Labels
        for (int i = 0; i < lEq.length; i++) {
            lEq[i] = new JLabel("");
        }
        for (int i = 0; i < grupo.getEquipoList().size(); i++) {
            if (grupo.getEquipoList().get(i) != null) {
                lEq[i].setFont(new Font("Arial", Font.BOLD, 12));
                if (lEq.length == 4){
                    lEq[i].setText(grupo.getEquipoList().get(i).getNombre().toUpperCase());

                } else if (lEq.length == 12) {
                    lEq[i].setText(grupo.getEquipoList().get(i).getNombre().toUpperCase().substring(0,3));
                }
                lEq[i].setIcon(grupo.getEquipoList().get(i).getImg());
                lEq[i].setHorizontalAlignment(SwingConstants.LEFT);
                lEq[i].setOpaque(false);
                lEq[i].setForeground(Color.WHITE);
                frame1.revalidate();
                frame1.repaint();
            }
        }
        //Añadir Labels
        for (int i = 0; i < lEq.length; i++) {
            // 1. Creamos el borde vacío (el margen interno o padding)
            Image img = new ImageIcon(PantallaDelGrupo.class.getResource("/FaseGruposCore/images/sinjugar.png")).getImage().getScaledInstance(15, 15, Image.SCALE_SMOOTH);
            if (lEq.length == 4) {
                if (i == 0 || i == 1) {
                    img = new ImageIcon(PantallaDelGrupo.class.getResource("/FaseGruposCore/images/clasifica.png")).getImage().getScaledInstance(8,8, Image.SCALE_SMOOTH);
                } else if (i == 2) {
                    img = new ImageIcon(PantallaDelGrupo.class.getResource("/FaseGruposCore/images/repechaje.png")).getImage().getScaledInstance(8,8, Image.SCALE_SMOOTH);
                } else if (i == 3 ) {
                    img = new ImageIcon(PantallaDelGrupo.class.getResource("/FaseGruposCore/images/eliminado.png")).getImage().getScaledInstance(8,8, Image.SCALE_SMOOTH);
                }
            } else if (lEq.length == 12) {
                if (i >= 0 && i < 8) {
                    img = new ImageIcon(PantallaDelGrupo.class.getResource("/FaseGruposCore/images/clasifica.png")).getImage().getScaledInstance(8,8, Image.SCALE_SMOOTH);
                } else if (i >= 8 && i < 12) {
                    img = new ImageIcon(PantallaDelGrupo.class.getResource("/FaseGruposCore/images/eliminado.png")).getImage().getScaledInstance(8,8, Image.SCALE_SMOOTH);
                } 
            }
            
            JPanel p = new JPanel(new BorderLayout());
            Border lineaInferior = BorderFactory.createMatteBorder(0, 0, 1, 0, Color.white);
            Border margen = BorderFactory.createEmptyBorder(2,2,2,2);

            JLabel l = new JLabel(new ImageIcon(img));
            l.setText(" ");
            p.add(l, BorderLayout.WEST);
            p.setBorder(BorderFactory.createCompoundBorder(lineaInferior, margen));
            p.setOpaque(false);
            p.add(lEq[i], BorderLayout.CENTER);
            panel.add(p);
        }
    }

    
}
