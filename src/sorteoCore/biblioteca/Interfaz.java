package sorteoCore.biblioteca;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

import Global.Equipo;
import Global.FondoPanel;
import Global.Grupo;
import sorteoCore.App;




public class Interfaz {
    public static int nBombo = 0;
    public static JFrame frame1 = new JFrame("Sorteo");
    public static void frame(Grupo[] grupos, Bombo[] bombos) {
        frame1.setLayout(new BorderLayout());
        frame1.setSize(1200, 650);
        frame1.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame1.setLocationRelativeTo(null);
        frame1.setVisible(true);

        //crear 3 paneles principales

        JPanel panelLateral = new JPanel();
        panelLateral.setBackground(new Color(0, 1, 39));
        panelLateral.setLayout(new GridLayout(13,1)); //12 equipos en el bombo mostrado
        panelLateral.add(new JLabel("BOMBO: "));
        panelLateral.removeAll();
        anadirBombo(bombos[0], panelLateral);

        FondoPanel panelCentro = new FondoPanel("/sorteoCore/images/sorteo.png");
        
        
        
        //panelCentro.setBackground(new Color(146, 218, 243));
        panelCentro.setLayout(new BorderLayout());

        

        JPanel panelAbajo = new JPanel();
        panelAbajo.setBackground(new Color(22, 37, 94));
        panelAbajo.setLayout(new GridLayout(1,12));

        //subpaneles para panelAbajo, estos son los grupos

        JPanel panelAbajo_1[] = {new JPanel(new GridLayout(5,1)), new JPanel(new GridLayout(5,1)), new JPanel(new GridLayout(5,1)), new JPanel(new GridLayout(5,1)), new JPanel(new GridLayout(5,1)), new JPanel(new GridLayout(5,1)), new JPanel(new GridLayout(5,1)), new JPanel(new GridLayout(5,1)), new JPanel(new GridLayout(5,1)), new JPanel(new GridLayout(5,1)), new JPanel(new GridLayout(5,1)), new JPanel(new GridLayout(5,1))};
        
        for (int i = 0; i < panelAbajo_1.length; i++) {
            if (i%2 == 0){
                panelAbajo_1[i].setBackground(new Color(3, 10, 54));
            } else {
                panelAbajo_1[i].setOpaque(false);
            }
            
            panelAbajo.add(panelAbajo_1[i]);

        }
        

        JLabel labelCentro = new JLabel("Bienvenido al sorteo");
        labelCentro.setForeground(Color.white);
        JButton startButton = new JButton("Iniciar sorteo");
        startButton.setCursor(new Cursor(Cursor.HAND_CURSOR));

        JPanel panelCentro_1 = new JPanel(new FlowLayout()); //SUBPANEL 1
        panelCentro_1.setBackground(new Color(255,255,255,50));
        
        panelCentro_1.add(labelCentro);
        panelCentro_1.add(startButton);

        JPanel panelCentro_2 = new JPanel(new FlowLayout());
        

        panelCentro_2.setOpaque(false);

        panelCentro.add(panelCentro_1, BorderLayout.NORTH);
        panelCentro.add(panelCentro_2, BorderLayout.CENTER);

        //añadir paneles

        frame1.add(panelLateral, BorderLayout.WEST);
        frame1.add(panelCentro, BorderLayout.CENTER);
        frame1.add(panelAbajo, BorderLayout.SOUTH);

        actualizarGrupos(grupos, panelAbajo_1);
        

        
        startButton.addActionListener(action->{ //al presionar el boton para iniciar
            panelLateral.removeAll();
            anadirBombo(bombos[nBombo], panelLateral); //se actualiza el bombo actual
            Inicializar.anadirAnfitriones();
            resaltarEquipo(grupos[0].getEquipoList().get(0), panelLateral);//Resaltar A1 (mex)
            resaltarEquipo(grupos[1].getEquipoList().get(0), panelLateral);//Resaltar B1 (can)
            resaltarEquipo(grupos[3].getEquipoList().get(0), panelLateral);//Resaltar D1 (usa)
            resaltarEquipo(Data.getEquipoList().get(20), panelLateral);//Resaltar otro al azar para dejar a los anfitriones descartados
            


            labelCentro.setText("BOMBO " + bombos[nBombo].getNombre() + " listo para sortear"); //se actualiza el central

            JLabel lMessage = new JLabel("");
            
            JButton sigEqButton = new JButton("Sortear siguiente equipo");
            JButton autoSortButton = new JButton("Sorteo automático");
            sigEqButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
            autoSortButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
            panelCentro_1.remove(startButton);
            panelCentro_1.add(sigEqButton);
            panelCentro_1.add(autoSortButton);

            panelLateral.revalidate();
            panelCentro_1.revalidate();

            actualizarGrupos(grupos, panelAbajo_1);

            sigEqButton.addActionListener(e1 -> { // al presionar el boton sigEquipo
                //sigEqButton.setEnabled(false);
                
                Equipo eqElegido = Logica.randomEq(bombos[nBombo]);
                if (nBombo == 3) {
                    for (Equipo e : bombos[nBombo].getEquipoList()) {
                        if (e.getNombre().equals("FIFA 2")) {
                            eqElegido = e;
                            break;
                        }
                    }
                }

                JButton asignButton = new JButton("Asignar equipo");
                asignButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
                prepararVistaEquipo(eqElegido, panelCentro_2, panelCentro);
                

                resaltarEquipo(eqElegido, panelLateral);
                mostrarEquipoConBoton(eqElegido, asignButton, panelCentro_2, panelCentro);

                //asignButton.addActionListener(_ -> { //comentar para no tener que confirmar el equipo al grupo
                    //sigEqButton.setEnabled(true);
                    String grupoName = Logica.asignarEquipo(bombos[nBombo], eqElegido, Interfaz.nBombo, grupos, lMessage);
                    int indexGrupo = -1;
                    for (int i = 0; i < grupos.length; i++) {
                        if (grupoName.equals(grupos[i].getNombre())) {
                            indexGrupo = i;
                            break;
                        }
                    }
                    actualizarGrupos(grupos, panelAbajo_1);
                    if (indexGrupo != -1) {
                        resaltarEquipo(eqElegido, panelAbajo_1[indexGrupo]);
                    }
                    
                    mostrarMensajeAsignacion(eqElegido, grupoName, panelCentro_2, panelCentro, lMessage);
                    
                    if (bombos[nBombo].getEquipoList().isEmpty()) {
                        procesarFinBombo(labelCentro, startButton, bombos, panelCentro_1, sigEqButton, autoSortButton, panelCentro_2, panelCentro, panelLateral, panelAbajo, grupos, frame1);
                    }
                //});
            });

            autoSortButton.addActionListener(e -> {
                int n = 0;
                while (n < bombos.length) {
                    int repeat = 0;
                    int error = 0;
                    while (!bombos[nBombo].getEquipoList().isEmpty()) {
                        Equipo eqElegido = Logica.randomEq(bombos[nBombo]);
                        Logica.asignarEquipo(bombos[nBombo], eqElegido, Interfaz.nBombo, grupos, lMessage);
                        
                        actualizarGrupos(grupos, panelAbajo_1);
                        repeat++;
                        if (repeat > 60) {
                            System.out.println("Error al asignar " + eqElegido.getNombre());
                            JOptionPane.showMessageDialog(null, "Es imposible asignar a: " + eqElegido.getNombre() + ". Si luego de 3 intentos no funciona, preferiblemente reinicie el programa");
                            
                            for (Grupo grupo : grupos) {
                                System.out.println("------" + " INICIO " + "-------");
                                System.out.println("GRUPO " + grupo.getNombre() + " (" + Logica.contarEuropeos(grupo) + ")");
                                for (Equipo eq : grupo.getEquipoList()){
                                    System.out.println(eq.getNombre() + " (" + eq.getConfederacion() + ")");
                                }
                                System.out.println("------" + " Falta " + eqElegido.getNombre() + "-------");
                            }
                            if (error > 2) {
                                break;
                            }
                            for (Grupo grupo : grupos) {
                                if (grupo.getEquipoList().size()==4) {
                                    bombos[3].getEquipoList().add(grupo.getEquipoList().get(3));
                                    grupo.getEquipoList().remove(3);
                                }
                            }
                            
                            repeat = 0;
                            error++;
                            
                            
                        }
                    }
                    procesarFinBombo(labelCentro, startButton, bombos, panelCentro_1, sigEqButton, autoSortButton, panelCentro_2, panelCentro, panelLateral, panelAbajo, grupos, frame1);
                    n++;
                    
                }
            });
        });
    }

    public static void mainFrame(Grupo[] grupos, Bombo[] bombos){
        UIManager.put("Label.foreground", Color.WHITE); // Cambia RED por el color que desees
        JFrame mainFrame = new JFrame("Sorteo (Main)");
        mainFrame.setLayout(new GridLayout(1,1));
        FondoPanel p = new FondoPanel("/sorteoCore/images/mainBackground.png");
        p.setLayout(new BorderLayout());
        mainFrame.setSize(500, 400);
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.setLocationRelativeTo(null);
        
        mainFrame.add(p);

        JPanel welcomePanel = new JPanel();
        welcomePanel.setOpaque(false);
        welcomePanel.setLayout(new FlowLayout());
        JLabel label = new JLabel("Bienvenido al sorteo");
        JButton startButton = new JButton("Iniciar sorteo");
        startButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        welcomePanel.add(label);
        welcomePanel.add(startButton);

        JPanel mainPanel = new JPanel();
        mainPanel.setOpaque(false);
        mainPanel.setLayout(new GridLayout(1,4));

        JPanel[] bombosPanel = new JPanel[4];

        for (int i = 0; i < bombosPanel.length; i++) {//añadir los 4 bombos al mainFrame
            bombosPanel[i] = new JPanel();
            bombosPanel[i].setOpaque(false);
            bombosPanel[i].setLayout(new GridLayout(13,1));
            bombosPanel[i].setBorder(new EmptyBorder(0,10,0,10));
            anadirBombo(bombos[i], bombosPanel[i]);
            mainPanel.add(bombosPanel[i]);
        }

        p.add(welcomePanel, BorderLayout.SOUTH);
        p.add(mainPanel);

        startButton.addActionListener(e->{
            frame(grupos,bombos);
            mainFrame.setVisible(false);
        });
        mainFrame.setVisible(true);
    }

        /**
     * Prepara la interfaz para mostrar un equipo seleccionado
     */
    private static void prepararVistaEquipo(Equipo equipo, JPanel panelCentro_2, JPanel panelCentro) {
        panelCentro_2.removeAll();
        panelCentro.revalidate();
        panelCentro.repaint();
    }

    /**
     * Resalta el equipo en el panel lateral y oscurece los demás
     */
    private static void resaltarEquipo(Equipo equipo, JPanel panelLateral) {
    for (Component comp : panelLateral.getComponents()) {
        // 1. Ahora buscamos el JPanel contenedor, no el JLabel directamente
        if (comp instanceof JPanel) {
            JPanel contenedor = (JPanel) comp;
            
            // 2. Obtenemos el JLabel que está dentro (asumiendo que es el único componente)
            // Usamos getComponent(0) porque el JLabel es el primer hijo del panel
            JLabel lbl = (JLabel) contenedor.getComponent(0);

            if (lbl.getText().equals(equipo.getNombre())) {//Resaltar equipo seleccionado
                // 3. Pintamos el CONTENEDOR, no el label
                lbl.setBackground(Color.WHITE);
                lbl.setForeground(Color.BLACK);
                lbl.setOpaque(true); // El label debe ser transparente para que se vea el fondo del panel
            } else if (lbl.getBackground().equals(Color.WHITE)) {//Un equipo anteriormente seleccionado se convierte en uno descartado
                lbl.setBackground(new Color(137, 157, 222, 60));
                lbl.setOpaque(true);
                lbl.setForeground(new Color(255,255,255, 60));
            } else if (lbl.getBackground().equals(new Color(137, 157, 222, 60))) {//Un equipo descartado sigue descartado
                lbl.setBackground(new Color(137, 157, 222, 60));
                lbl.setOpaque(true);
                lbl.setForeground(new Color(255,255,255, 60));
            } else {//Si no ha sido seleccionado ni descartado sigue disponible
                lbl.setBackground(new Color(137, 157, 222, 100));
                lbl.setOpaque(true);
                lbl.setForeground(Color.WHITE);
            }
        }
    }
        // 4. Refrescar la UI para asegurar que los colores se apliquen al instante
        panelLateral.repaint();
    }

    /**
     * Muestra el equipo en el panel central con su botón de asignación
     */
    private static void mostrarEquipoConBoton(Equipo equipo, JButton botonAsignar, JPanel panelCentro_2, JPanel panelCentro) {
        JLabel lbl = new JLabel(equipo.getNombre(), equipo.getImg(), JLabel.LEFT);
        lbl.setForeground(Color.lightGray);
        panelCentro_2.add(lbl);
        panelCentro_2.add(botonAsignar);
        panelCentro.revalidate();
    }

    /**
     * Muestra mensaje de confirmación de asignación
     */
    private static void mostrarMensajeAsignacion(Equipo equipo, String grupo, JPanel panelCentro_2, JPanel panelCentro, JLabel lMessage) {
        panelCentro_2.removeAll();
        JLabel lbl2 = new JLabel("<html>" + equipo.getNombre().toUpperCase() + " asignado al grupo " + grupo + "<br>" + (lMessage.getText()) + "</html>");
        lbl2.setForeground(Color.GREEN);
        lMessage.setText("");
        panelCentro_2.add(lbl2);
        panelCentro.revalidate();
        panelCentro.repaint();
    }

    /**
     * Procesa la finalización de un bombo (compartido entre ambos listeners)
     */
    private static void procesarFinBombo(JLabel labelCentro, JButton startButton, Bombo[] bombos, JPanel panelCentro_1, JButton sigEqButton, JButton autoSortButton, JPanel panelCentro_2, FondoPanel panelCentro, JPanel panelLateral, JPanel panelAbajo, Grupo[] grupos, JFrame frame1) {
        if (nBombo != 3) {
            labelCentro.setText("BOMBO " + bombos[nBombo].getNombre() + " completado");
            startButton.setText("SIGUIENTE BOMBO");

            panelCentro_1.remove(sigEqButton);
            panelCentro_1.remove(autoSortButton);
            panelCentro_1.add(startButton);
            panelCentro_1.revalidate();

            panelCentro_2.removeAll();
            panelCentro.revalidate();
            panelCentro.repaint();
            panelLateral.removeAll();
            panelLateral.revalidate();
            panelLateral.repaint();

            nBombo++;
        } else {
            // Finalizar sorteo
            panelCentro_1.removeAll();
            panelCentro_2.removeAll();
            labelCentro.setText("Sorteo completado");
            labelCentro.setForeground(Color.GREEN);
            frame1.remove(panelLateral);
            
            JButton endBtn = new JButton("Comenzar torneo");
            endBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
            
            panelCentro_1.add(labelCentro);
            panelCentro_1.add(endBtn);
            
            panelAbajo.removeAll();

            panelCentro_2.setLayout(new GridLayout(3, 4));
            panelCentro_2.setBorder(new EmptyBorder(20, 20, 20, 20));
            
            for (Grupo grupo : grupos) {
                JPanel panelGrupo = new JPanel(new GridLayout(5, 1));
                panelGrupo.setOpaque(false);
                panelGrupo.setBorder(new EmptyBorder(5, 10, 5, 10));
                anadirGrupo(grupo, panelGrupo);
                panelCentro_2.add(panelGrupo);
            }
            panelCentro.setImagen("/sorteoCore/images/mainBackground.png");
            frame1.revalidate();
            frame1.repaint();

            endBtn.addActionListener(e->{
                UIManager.put("Label.foreground", Color.BLACK); // Cambia RED por el color que desees
                frame1.setVisible(false);
                App.seguir();
            });
        }
    }


    public static void anadirBombo(Bombo bombo, JPanel panel){
        panel.add(new JLabel("BOMBO " + bombo.getNombre()));
        for (Equipo eq : bombo.getEquipoList()) {
            JPanel p = new JPanel(new GridLayout(1,1));
            JLabel l = new JLabel(eq.getNombre(), eq.getImg(), JLabel.LEFT);
            l.setBackground(new Color(137, 157, 222, 100));
            p.setOpaque(false);
            l.setOpaque(true);
            p.setBorder(BorderFactory.createEmptyBorder(2,2,2,2));
            p.add(l);
            panel.add(p);
        }
    }
    public static void anadirGrupo(Grupo grupo, JPanel panel){
        //Crear arreglo de Labels
        JLabel[] lEq = new JLabel[4];
        //Reutilizamos variable temp para el titulo y para mover el orden de los eqs en "orden intercalado"
        JLabel temp = new JLabel("GRUPO " + grupo.getNombre());
        if (nBombo == 3) {
            temp.setForeground(new Color(255,255,255,200));
        }
        
        panel.add(temp);
        //Añadir nombres de los equipos a los Labels
        for (int i = 0; i < lEq.length; i++) {
            lEq[i] = new JLabel("");

            //(M1) Primero se añade solo el margen

            if (i == nBombo) {
                //Si el label corresponde al bombo que está seleccionado se cambia el texto
                lEq[i] = new JLabel("BOMBO " + (nBombo + 1), SwingConstants.CENTER);
                lEq[i].setFont(new Font("Arial", Font.BOLD, 9));
                //Se añade una linea de borde para indicar que está seleccionado
                lEq[i].setBorder(BorderFactory.createLineBorder(new Color(213, 213, 213, 60), 2));
                //Se cambia el color de la letra
                lEq[i].setForeground(new Color(255,255,255,50));
            }
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
            }
        }
        //Orden intercalado como en el mundial
        if (grupo.getNombre().equals("A") || grupo.getNombre().equals("D") || grupo.getNombre().equals("G") || grupo.getNombre().equals("J")) {
            temp = lEq[2]; // 1. Guardamos el primero
            lEq[2] = lEq[1];      // 2. Pasamos el segundo al lugar del primero
            lEq[1] = temp;        // 3. Ponemos el guardado en el lugar del segundo
        } else if (grupo.getNombre().equals("B") || grupo.getNombre().equals("E") || grupo.getNombre().equals("H") || grupo.getNombre().equals("K")) {
            temp = lEq[1]; // 1. Guardamos el primero
            lEq[1] = lEq[3];      // 2. Pasamos el segundo al lugar del primero
            lEq[3] = temp;        // 3. Ponemos el guardado en el lugar del segundo
        } else if (grupo.getNombre().equals("C") || grupo.getNombre().equals("F") || grupo.getNombre().equals("I") || grupo.getNombre().equals("L")) {
            temp = lEq[2]; // 1. Guardamos el primero
            lEq[2] = lEq[3];      // 2. Pasamos el segundo al lugar del primero
            lEq[3] = temp;        // 3. Ponemos el guardado en el lugar del segundo
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
    public static void actualizarGrupos(Grupo[] grupos, JPanel[] panel) {
        for (int i = 0; i < 12; i++) {
            panel[i].removeAll();
            anadirGrupo(grupos[i], panel[i]);
            panel[i].revalidate();
        }
    }

    

    
}
