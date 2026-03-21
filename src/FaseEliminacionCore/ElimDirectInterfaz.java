package FaseEliminacionCore;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;

import FaseGruposCore.biblioteca.FondoPanel;
import Global.Data;
import Global.Duelo;
import Global.Equipo;
import Global.Grupo;

import java.awt.*;

public class ElimDirectInterfaz extends JFrame {

    public static ElimDirectInterfaz bm = new ElimDirectInterfaz();
    public static Color DORADO = new Color(209, 162, 94);

    

    public static void ordenarElimDirect() {
        for (Grupo gr : Data.getGrupos()) {
            for (Equipo e : gr.getEquipoList()) {
                if (e.getNombre().length() > 3) {
                    e.setNombre(e.getNombre().substring(0,3).toUpperCase());
                }
            }
        }
        ElimDirectInterfaz.bm.getContentPane().removeAll();
        PartidosClass.asignarDuelosElimDirect(Data.getGrupos(), Data.getDuelosElimDirect());
        bm.setTitle("Cuadro de Eliminación Directa - 32 Equipos");
        bm.setSize(1200, 700);
        bm.setLocationRelativeTo(null);
        bm.setLayout(new BorderLayout());
        
        // Cambiamos a GridBagLayout para todo el frame
        FondoPanel pPrincipal = new FondoPanel("/images/FondoEliminacionDirect.png");
        
        pPrincipal.setLayout(new GridBagLayout());
        bm.add(pPrincipal, BorderLayout.CENTER);
        GridBagConstraints gbc = new GridBagConstraints();

        
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;

        // Crear los 32 labels con tu lógica original de colores aleatorios
        JPanel[] pDuelos = new JPanel[32];

        for (int i = 0; i < pDuelos.length; i++) {
            JPanel p_1 = new JPanel(new GridLayout(2,1));
            JPanel p_2 = new JPanel(new GridLayout(2,1));
            p_1.setOpaque(false);
            p_2.setOpaque(false);

            int golesLoc = 0;
            int golesVis = 0;
            if (i < Data.getDuelosElimDirect().size()) {
                golesLoc = Math.max(0, Data.getDuelosElimDirect().get(i).getgLoc());
                golesVis = Math.max(0, Data.getDuelosElimDirect().get(i).getgVis());
            }

            p_2.add(new JSpinner(new SpinnerNumberModel(golesLoc,0,15,1)));
            p_2.add(new JSpinner(new SpinnerNumberModel(golesVis,0,15,1)));
            JPanel p = new JPanel(new BorderLayout());
            p.setOpaque(false);
            p.setBorder(new LineBorder(new Color(209, 162, 94), 3));
            Border b1 = BorderFactory.createEmptyBorder(10,10,10,10);//margen
            Border b2 = BorderFactory.createLineBorder(DORADO, 3);//linea borde
            Border bordeCompuesto = BorderFactory.createCompoundBorder(b1,b2);
            p.setBorder(bordeCompuesto);
            p.setFont(new Font("Arial", Font.BOLD, 16));
            //l.setBorder(new EmptyBorder(10,10,10,10));
            p.add(p_1, BorderLayout.CENTER);
            if (i < 8 || (i >= 16 && i < 20) || (i >= 24 && i < 26) || i == 28) {
                p.add(p_2, BorderLayout.EAST);
            } else {
                p.add(p_2, BorderLayout.WEST);
            }
            pDuelos[i] = p;
        }

        JButton saveBtn = new JButton("Save");
        saveBtn.setFocusPainted(false);
        saveBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        saveBtn.setBackground(DORADO);

        JButton autoPlayBtn = new JButton("Auto");
        autoPlayBtn.setFocusPainted(false);
        autoPlayBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        autoPlayBtn.setBackground(DORADO);

        JButton clearBtn = new JButton("Clear");
        clearBtn.setFocusPainted(false);
        clearBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        clearBtn.setBackground(DORADO);

        JSpinner[] spLocales = new JSpinner[32];
        JSpinner[] spVisitantes = new JSpinner[32];

        for (int i = 0; i < 32; i++) {
            JPanel primerSubPanel = (JPanel) pDuelos[i].getComponent(0);
            JPanel segundoSubPanel = (JPanel) pDuelos[i].getComponent(1);
            JSpinner spLocal = (JSpinner) segundoSubPanel.getComponent(0);
            JSpinner spVisit = (JSpinner) segundoSubPanel.getComponent(1);
            personalizarSpinner(spLocal, Color.WHITE, DORADO, Color.WHITE);
            personalizarSpinner(spVisit, Color.WHITE, DORADO, Color.WHITE);

            spLocales[i] = spLocal;
            spVisitantes[i] = spVisit;

            try {
                JLabel l1 = new JLabel(Data.getDuelosElimDirect().get(i).getlocal().getNombre());
                l1.setIcon(Data.getDuelosElimDirect().get(i).getlocal().getImg());
                l1.setForeground(Color.white);

                primerSubPanel.add(l1);

                JLabel l2 = new JLabel(Data.getDuelosElimDirect().get(i).getvisitante().getNombre());
                l2.setIcon(Data.getDuelosElimDirect().get(i).getvisitante().getImg());
                l2.setForeground(Color.white);

                primerSubPanel.add(l2);
            } catch (ArrayIndexOutOfBoundsException e) {
                // TODO: handle exception
            } catch (NullPointerException e) {
                // TODO: handle exception
            }
        }

        saveBtn.addActionListener(e -> {
            try {
                for (int iter = 0; iter < 32; iter++) {
                    int golesL = (Integer) spLocales[iter].getValue();
                    int golesV = (Integer) spVisitantes[iter].getValue();
                    int posSigDuelo = (iter % 2 == 0) ? 0 : 1;
                    int idxSig = indexSigDuelo(iter, 32);

                    Duelo dueloActual = Data.getDuelosElimDirect().get(iter);
                    dueloActual.setgLoc(golesL);
                    dueloActual.setgVis(golesV);

                    if (idxSig >= 0 && idxSig < Data.getDuelosElimDirect().size()) {
                        dueloActual.asignarGanadorADuelo(golesL, golesV,
                                Data.getDuelosElimDirect().get(idxSig), posSigDuelo, idxSig);
                    }

                    if (iter == 28 || iter == 29) { // para el tercer lugar
                        int idxPerdedor = 30;
                        if (idxPerdedor >= 0 && idxPerdedor < Data.getDuelosElimDirect().size()) {
                            dueloActual.asignarPerdedorA3Lugar(golesL, golesV,
                                    Data.getDuelosElimDirect().get(idxPerdedor), posSigDuelo, idxPerdedor);
                        }
                    }
                }

                for (int j = 0; j < 32; j++) {
                    System.out.println(Data.getDuelosElimDirect().get(j).dueloText());
                }
            } catch (ArrayIndexOutOfBoundsException e1) {
                // TODO: handle exception
            } catch (NullPointerException e1) {
                // TODO: handle exception
            }
            catch (IndexOutOfBoundsException e1) {
                // TODO: handle exception
            }

            ElimDirectInterfaz.ordenarElimDirect();
            pPrincipal.revalidate();
            pPrincipal.repaint();
        });

        autoPlayBtn.addActionListener(e -> {
            try {
                // Ejecuta todos los duelos en cadena y los propaga a la siguiente ronda.
                for (int iter = 0; iter < 32; iter++) {
                    Duelo dueloActual = Data.getDuelosElimDirect().get(iter);

                    // Simula el partido y actualiza los valores internos del duelo
                    int[] gls = dueloActual.simularPartido();
                    System.out.println(Data.getDuelosElimDirect().get(iter).dueloText());
                    spLocales[iter].setValue(gls[0]);
                    spVisitantes[iter].setValue(gls[1]);

                    // Actualiza el siguiente duelo de la llave si existe.
                    int idxSig = indexSigDuelo(iter, 32);
                    int posSigDuelo = (iter % 2 == 0) ? 0 : 1;
                    if (idxSig >= 0 && idxSig < Data.getDuelosElimDirect().size()) {
                        dueloActual.asignarGanadorADuelo(gls[0], gls[1],
                                Data.getDuelosElimDirect().get(idxSig), posSigDuelo, idxSig);
                    }

                    // Tercer lugar (28 y 29) también puede ser asignado en 30
                    if (iter == 28 || iter == 29) {
                        int idxTercer = 30;
                        if (idxTercer >= 0 && idxTercer < Data.getDuelosElimDirect().size()) {
                            dueloActual.asignarPerdedorA3Lugar(gls[0], gls[1],
                                    Data.getDuelosElimDirect().get(idxTercer), posSigDuelo, idxTercer);
                        }
                    }
                }

                for (int j = 0; j < Data.getDuelosElimDirect().size(); j++) {
                    //System.out.println(Data.getDuelosElimDirect().get(j).dueloText());
                }

                ElimDirectInterfaz.ordenarElimDirect();
                bm.revalidate();
                bm.repaint();

            } catch (ArrayIndexOutOfBoundsException e1) {
                // TODO: handle exception
            } catch (NullPointerException e1) {
                // TODO: handle exception
            } catch (IndexOutOfBoundsException e1) {
                // TODO: handle exception
            }
        });

        clearBtn.addActionListener(e -> {
            try {
                for (int i = 0; i < 32; i++) {
                    JPanel primerSubPanel = (JPanel) pDuelos[i].getComponent(0);
                    JPanel segundoSubPanel = (JPanel) pDuelos[i].getComponent(1);
                    JSpinner spLocal = (JSpinner) segundoSubPanel.getComponent(0);
                    JSpinner spVisit = (JSpinner) segundoSubPanel.getComponent(1);
                    spLocales[i] = spLocal;
                    spVisitantes[i] = spVisit;

                    int gLoc = (int) spLocal.getValue();
                    int gVis = (int) spVisit.getValue();

                    Data.getDuelosElimDirect().get(i).setgLoc(0);
                    Data.getDuelosElimDirect().get(i).setgVis(0);
                }
                for (int i = 16; i < 32; i++) {
                    Data.getDuelosElimDirect().get(i).setlocal(null);
                    Data.getDuelosElimDirect().get(i).setvisitante(null);
                }

                for (int j = 0; j < 32; j++) {
                    System.out.println(Data.getDuelosElimDirect().get(j).dueloText());
                }
            } catch (ArrayIndexOutOfBoundsException e1) {
                // TODO: handle exception
            } catch (NullPointerException e1) {
                // TODO: handle exception
            }
            catch (IndexOutOfBoundsException e1) {
                // TODO: handle exception
            }

            ElimDirectInterfaz.ordenarElimDirect();
            pPrincipal.revalidate();
            pPrincipal.repaint();
        });
        // --- LÓGICA DE POSICIONAMIENTO (10 columnas x 8 filas) ---

        // 16vos Lado 1 (Columna 0, cada uno ocupa 1 fila)
        gbc.gridx = 0;
        gbc.gridheight = 1;
        for (int i = 0; i < 8; i++) {
            gbc.gridy = i;
            pPrincipal.add(pDuelos[i], gbc);
        }

        // 8vos Lado 1 (Columna 1, cada uno ocupa 2 filas)
        gbc.gridx = 1;
        gbc.gridheight = 2;
        for (int i = 0; i < 4; i++) {
            gbc.gridy = i * 2;
            pPrincipal.add(pDuelos[16 + i], gbc); // Índices 16-19 según tu lógica
        }

        // 4tos Lado 1 (Columna 2, cada uno ocupa 4 filas)
        gbc.gridx = 2;
        gbc.gridheight = 4;
        for (int i = 0; i < 2; i++) {
            gbc.gridy = i * 4;
            pPrincipal.add(pDuelos[24 + i], gbc); // Índices 24-25
        }

        // Semis Lado 1 (Columna 3, ocupa las 8 filas)
        gbc.gridx = 3;
        gbc.gridheight = 8;
        gbc.gridy = 0;
        pPrincipal.add(pDuelos[28], gbc); // Índice 28

        gbc.gridx = 4;
        gbc.gridheight = 2;
        gbc.gridy = 0;
        pPrincipal.add(pDuelos[31], gbc); // Índice 31

        gbc.gridy = 6;
        pPrincipal.add(pDuelos[30], gbc); // Índice 30

        gbc.gridy = 5;
        JPanel pBtns = new JPanel(new GridLayout(3,1));
        gbc.gridheight = 2;
        pBtns.add(saveBtn);
        pBtns.add(autoPlayBtn);
        pBtns.add(clearBtn);
        pPrincipal.add(pBtns, gbc);

        // Reset gridwidth para el resto
        gbc.gridwidth = 1;

        // Semis Lado 2 (Columna 6, ocupa 8 filas)
        gbc.gridx = 6;
        gbc.gridheight = 8;
        gbc.gridy = 0;
        pPrincipal.add(pDuelos[29], gbc); // Índice 29

        // 4tos Lado 2 (Columna 7, cada uno ocupa 4 filas)
        gbc.gridx = 7;
        gbc.gridheight = 4;
        for (int i = 0; i < 2; i++) {
            gbc.gridy = i * 4;
            pPrincipal.add(pDuelos[26 + i], gbc); // Índices 26-27
        }

        // 8vos Lado 2 (Columna 8, cada uno ocupa 2 filas)
        gbc.gridx = 8;
        gbc.gridheight = 2;
        for (int i = 0; i < 4; i++) {
            gbc.gridy = i * 2;
            pPrincipal.add(pDuelos[20 + i], gbc); // Índices 20-23
        }

        // 16vos Lado 2 (Columna 9, cada uno ocupa 1 fila)
        gbc.gridx = 9;
        gbc.gridheight = 1;
        for (int i = 0; i < 8; i++) {
            gbc.gridy = i;
            pPrincipal.add(pDuelos[8 + i], gbc); // Índices 8-15
        }
        bm.revalidate();
        bm.repaint();

        
    }

    public static int indexSigDuelo(int dueloActual, int totalEquipos) {
        // Si es la final (30) o tercer puesto (31), no hay siguiente
        if (dueloActual >= totalEquipos - 2) return -1; 

        int partidosRondaActual = totalEquipos / 2;
        int inicioRondaActual = 0;
        
        // Encontrar en qué ronda estamos y dónde empieza la siguiente
        if (dueloActual < 28) {
            while (dueloActual >= inicioRondaActual + partidosRondaActual) {
                inicioRondaActual += partidosRondaActual;
                partidosRondaActual /= 2;
            }
        } else {
            if (dueloActual == 28 || dueloActual == 29) {
                return 31;
            }
        }
        
        
        int inicioSiguienteRonda = inicioRondaActual + partidosRondaActual;
        int posicionEnRonda = dueloActual - inicioRondaActual;
        
        return inicioSiguienteRonda + (posicionEnRonda / 2);
    }

    public static void personalizarSpinner(JSpinner spinner, Color colorTexto, Color colorBorde, Color colorBotones) {
        // 1. Transparencia y Texto
        spinner.setOpaque(false);
        JComponent editor = spinner.getEditor();
        editor.setOpaque(false);

        if (editor instanceof JSpinner.DefaultEditor) {
            JTextField txt = ((JSpinner.DefaultEditor) editor).getTextField();
            txt.setOpaque(false);
            txt.setForeground(colorTexto);
            txt.setBorder(BorderFactory.createLineBorder(colorBorde, 1));
        }

        // 2. Borde exterior del Spinner
        spinner.setBorder(BorderFactory.createLineBorder(colorBorde, 1));

        // 3. Personalización de los botones internos
        for (Component c : spinner.getComponents()) {
            if (c instanceof JButton) {
                JButton btn = (JButton) c;
                
                // Si quieres que el botón tenga un color sólido:
                btn.setOpaque(true); 
                btn.setBackground(colorBotones); 
                
                // Si prefieres que el botón sea transparente pero con color de flecha:
                // btn.setOpaque(false);
                // btn.setContentAreaFilled(false);
                
                btn.setForeground(colorTexto); // Color de la flecha (en la mayoría de L&F)
                btn.setBorder(BorderFactory.createLineBorder(colorBorde, 1));
            }
        }
    }
}