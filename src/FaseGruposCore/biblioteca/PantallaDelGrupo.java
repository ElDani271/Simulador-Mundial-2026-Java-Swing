package FaseGruposCore.biblioteca;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import Global.Duelo;
import Global.Equipo;
import Global.FondoPanel;
import Global.Grupo;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;


public class PantallaDelGrupo extends JFrame {
    public JButton mostrarMasMenosButton = new JButton("Ver +");
    public JPanel pCentral = new JPanel(new BorderLayout());
    // Arreglo 2D para los labels de la tabla
    public JLabel[][] tablaLabels = new JLabel[10][15];
    public JPanel pTabla = new JPanel();
    // Arreglo de botones para cada fecha
    public JButton[] botonesFechas = new JButton[3];

    public PantallaDelGrupo(){

    }

    public PantallaDelGrupo(Grupo gr){
        this.setTitle("Posiciones - Grupo " + gr.getNombre());
        FondoPanel panel = new FondoPanel("/FaseGruposCore/images/balon.png");
        this.setContentPane(panel);

        this.setLayout(new BorderLayout());
        
        pTabla.setLayout(new GridBagLayout());
        
        // Panel para los botones
        JPanel panelBotones = new JPanel();

            // Inicializar la tabla
            ordenarTabla(gr.getEquipoList());
            this.setSize(400,400);
            this.setLocationRelativeTo(null);
            this.repaint();
            
        // Crear botones "Nueva Fecha" para cada fecha
        for (int i = 0; i < botonesFechas.length; i++) {
            final int fecha = i;
            botonesFechas[i] = new JButton("Fecha " + (i + 1));
            botonesFechas[i].addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    botonFecha(gr.getCalendarioList().get(fecha), tablaLabels, pTabla, fecha, botonesFechas, gr);
                }
            });
            panelBotones.add(botonesFechas[i]);
            botonesFechas[i].setVisible(false);
        }

        //Boton de calendario
        JButton calendarioButton = new JButton("Calendario");
        panelBotones.add(calendarioButton);
        calendarioButton.addActionListener(__->{
            mostrarCalendario(gr.getCalendarioList());
        });
        botonesFechas[0].setVisible(true);

        //boton ver +/-
        mostrarMasMenosButton.addActionListener(e -> {
            verMasMenos(tablaLabels, pTabla, gr.getEquipoList());
        });

        JButton azarBtn = new JButton("Jugar auto.");
        
        panelBotones.add(azarBtn);
        azarBtn.addActionListener(e->{
            if (gr.getEquipoList().get(0).getPartidosJugados() == 3) {
                azarBtn.setEnabled(false);
                JOptionPane.showMessageDialog(null, "No se pueden Re-Jugar los partidos", "Excepcion de entrada", JOptionPane.ERROR_MESSAGE);
                
            } else {
                for (ArrayList<Duelo> fecha : gr.getCalendarioList()) {
                    for (Duelo dl : fecha) {
                        if (dl.getgLoc() == -1) {
                            int[] goles = Equipo.simularPartido(dl.getlocal(), dl.getvisitante());
                            Bib.asignarGolesADuelo(dl.getlocal(), dl.getvisitante(), gr.getCalendarioList().indexOf(fecha), goles[0], goles[1], gr.getCalendarioList());
                            Equipo.jugarPartido(dl.getlocal(), dl.getvisitante(), goles[0], goles[1]);
                            gr.pantalla.ordenarTabla(gr.getEquipoList());
                            InterfazGrupos.crearInterfazGrupos();
                            gr.pantalla.setVisible(true);
                            azarBtn.setEnabled(false);
                        }
                        botonesFechas[gr.getCalendarioList().indexOf(fecha)].setVisible(false);
                    }
                }
            }
        });

        //Panel sur
        JPanel pSouth = new JPanel();
            crearPanelSur(pSouth);
        
        // Agregar paneles al frame principal
        panelBotones.add(mostrarMasMenosButton);
        

        //pCentral.add(pColorsMain, BorderLayout.WEST);
        pCentral.add(pTabla, BorderLayout.CENTER);

        pTabla.setBackground(new Color(255,255,255, 150));
        pSouth.setBackground(new Color(0,0,0, 50));
        panelBotones.setOpaque(false);

        this.add(panelBotones, BorderLayout.NORTH);
        this.add(pCentral, BorderLayout.CENTER);
        this.add(pSouth, BorderLayout.SOUTH);
        
        this.setLocationRelativeTo(null);
    }

    public void mostrarCalendario(ArrayList<ArrayList<Duelo>> calendarioList){
        JFrame calendarioFrame = new JFrame("Calendario - Eliminatorias");
        calendarioFrame.setSize(250, 300);

        JPanel[] fechasPanel = new JPanel[calendarioList.size()];//cuantos cuadros de fecha se generan / cuantas fechas hay

        JPanel fechas = new JPanel(new GridLayout(fechasPanel.length,1));

        for (int i = 0; i < calendarioList.size(); i++) {
            fechasPanel[i] = new JPanel();
            fechasPanel[i].setLayout(new GridLayout(calendarioList.get(i).size()+1,1)); //se generan los partidos de la fecha + el titulo
            JLabel labelfecha = new JLabel("FECHA " + (i+1), SwingConstants.CENTER);
            labelfecha.setOpaque(true);
            labelfecha.setBackground(new Color(193,193,193));
            fechasPanel[i].add(labelfecha);
            
            for (Duelo duelo : calendarioList.get(i)) {
                if (duelo.getgLoc() == -1) {//goles de local -1 es el valor predeterminado antes de jugar el partido
                    fechasPanel[i].add(new JLabel(duelo.getlocal().getNombre() + " - " + duelo.getvisitante().getNombre(), SwingConstants.CENTER));
                } else {
                    if (duelo.getgLoc() > duelo.getgVis()) {
                        fechasPanel[i].add(new JLabel("👑 " + duelo.getlocal().getNombre() + " " + duelo.getgLoc() + " - " + duelo.getgVis() + " " + duelo.getvisitante().getNombre(), SwingConstants.CENTER));
                    } else if (duelo.getgLoc() < duelo.getgVis()) {
                        fechasPanel[i].add(new JLabel(duelo.getlocal().getNombre() + " " + duelo.getgLoc() + " - " + duelo.getgVis() + " " + duelo.getvisitante().getNombre() + " 👑", SwingConstants.CENTER));
                    } else {
                        fechasPanel[i].add(new JLabel(duelo.getlocal().getNombre() + " " + duelo.getgLoc() + " - " + duelo.getgVis() + " " + duelo.getvisitante().getNombre(), SwingConstants.CENTER));
                    }
                }
            }
            fechas.add(fechasPanel[i]);
        }
        JScrollPane scrollPane = new JScrollPane(fechas);
        calendarioFrame.add(scrollPane);
        calendarioFrame.setVisible(true);
        calendarioFrame.setLocationRelativeTo(null);
    }

    public void verMasMenos(JLabel[][] tablaLabels, JPanel pTabla, ArrayList<Equipo> equipos){
        if (this.mostrarMasMenosButton.getText().equals("Ver +")) {// intercambiar texto del boton
                this.mostrarMasMenosButton.setText("Ver -");
            } else if (this.mostrarMasMenosButton.getText().equals("Ver -")) {
                this.mostrarMasMenosButton.setText("Ver +");
            }
            this.mostrarTabla(tablaLabels, pTabla, equipos);
                pTabla.revalidate();
                pTabla.repaint();
                this.setLocationRelativeTo(null);
                this.repaint();

            if (this.mostrarMasMenosButton.getText().equals("Ver -")) {  
                this.setSize(500,400);
                this.setLocationRelativeTo(null);
                this.revalidate();
                this.repaint();
            } else if (this.mostrarMasMenosButton.getText().equals("Ver +")) {
                this.setSize(400,400); 
                this.setLocationRelativeTo(null);
                this.revalidate();
                this.repaint();
            }
    }

    public void botonFecha(ArrayList<Duelo> fechaListDuelos, JLabel[][] tablaLabels, JPanel tabla, int fecha, JButton[] botonesFechas, Grupo group){ //contenido al presionar el boton de la fecha
        JFrame nuevaFechaFrame = new JFrame("Jugar fecha " + (fecha + 1)); //Se crea la ventana de los partidos

        nuevaFechaFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        nuevaFechaFrame.setSize(350, 200);
        nuevaFechaFrame.setResizable(false);
        nuevaFechaFrame.setLocationRelativeTo(null);

        FondoPanel img = new FondoPanel("/FaseGruposCore/images/fondo_fecha.png");
        nuevaFechaFrame.setContentPane(img);

        nuevaFechaFrame.setLayout(new BorderLayout());

        JPanel pPartidos = new JPanel();
        pPartidos.setLayout(new GridBagLayout());
        pPartidos.setBorder(new EmptyBorder(30,25,0,25));
        JPanel pSouth = new JPanel();;

        pPartidos.setOpaque(false);
        pSouth.setOpaque(false);

        pSouth.setLayout(new FlowLayout());

        JButton calcular = new JButton("Calcular");

        GridBagConstraints rules = new GridBagConstraints();

        rules.gridx = 0;//en la casilla 0,0
        rules.gridy = 5;
        rules.gridheight = 1;// ocupa 1 casilla de alto y 5 de ancho
        rules.gridwidth = 5;
        rules.weightx = 1.0;
        rules.weighty = 1.0;
        
        pSouth.add(calcular);

        nuevaFechaFrame.add(pSouth, BorderLayout.CENTER);

        generarPartidos(pPartidos, calcular, fechaListDuelos, tablaLabels, tabla, nuevaFechaFrame, fecha, botonesFechas, rules, group);

        nuevaFechaFrame.add(pPartidos, BorderLayout.NORTH);
        nuevaFechaFrame.setVisible(true);
    }

    public static void generarPartidos(JPanel pPartidos, JButton calcular, ArrayList<Duelo> fechaNNpartidos, JLabel[][] tablaLabels, JPanel tabla, JFrame nuevaFechaFrame, int fecha, JButton[] botonesFechas, GridBagConstraints rules, Grupo group) {
        ArrayList<JSpinner> spinnersLocal = new ArrayList<>();
        ArrayList<JSpinner> spinnersVisitante = new ArrayList<>();
        ArrayList<String> nombresLocal = new ArrayList<>();
        ArrayList<String> nombresVisitante = new ArrayList<>();

        for (int i = 0; i < fechaNNpartidos.size(); i++) {
            rules.gridy = i;
            rules.gridheight = 1;
            rules.gridwidth = 1;
            rules.weighty = 1.0;
            
            // Equipo local
            rules.gridx = 0;
            rules.weightx = 1.0;
            rules.anchor = GridBagConstraints.EAST;
            String nombreLocal = fechaNNpartidos.get(i).getlocal().getNombre();
            Equipo eqImg = new Equipo();
            for (Equipo e : group.getEquipoList()) {
                if (e.getNombre().equals(nombreLocal)) {
                    eqImg = e;
                    break;
                }
            }
            JLabel lLocal = new JLabel(nombreLocal.substring(0,3).toUpperCase());
            lLocal.setIcon(eqImg.getImg());
            lLocal.setForeground(Color.WHITE);
            pPartidos.add(lLocal, rules);
            nombresLocal.add(nombreLocal);

            // Spinner goles local
            rules.gridx = 1;
            rules.weightx = 1.0;
            rules.anchor = GridBagConstraints.CENTER;
            JSpinner golesLocal = new JSpinner(new SpinnerNumberModel(0,0,15,1));
            pPartidos.add(golesLocal, rules);
            spinnersLocal.add(golesLocal);

            // Guión
            rules.gridx = 2;
            rules.weightx = 1.0;
            JLabel lGuion = new JLabel(" - ");
            lGuion.setForeground(Color.WHITE);
            pPartidos.add(lGuion, rules);

            // Spinner goles visitante
            rules.gridx = 3;
            rules.weightx = 1.0;
            JSpinner golesVisitante = new JSpinner(new SpinnerNumberModel(0,0,15,1));
            pPartidos.add(golesVisitante, rules);
            spinnersVisitante.add(golesVisitante);

            // Equipo visitante
            rules.gridx = 4;
            rules.weightx = 1.0;
            rules.anchor = GridBagConstraints.WEST;
            String nombreVisitante = fechaNNpartidos.get(i).getvisitante().getNombre();
            for (Equipo e : group.getEquipoList()) {
                if (e.getNombre().equals(nombreVisitante)) {
                    eqImg = e;
                    break;
                }
            }
            JLabel lVisit = new JLabel(nombreVisitante.substring(0,3).toUpperCase());
            lVisit.setIcon(eqImg.getImg());
            lVisit.setForeground(Color.WHITE); 
            pPartidos.add(lVisit, rules);
            nombresVisitante.add(nombreVisitante);
            
        }
        calcular.addActionListener(__ -> {
            try {
                Boolean continuar = true;
                if (group.getEquipoList().get(0).getPartidosJugados() < group.getCalendarioList().size()) {//PJ<3
                    // Procesar todos los partidos
                    for (int i = 0; i < fechaNNpartidos.size(); i++) {
                        int golesL = (Integer) spinnersLocal.get(i).getValue();
                        int golesV = (Integer) spinnersVisitante.get(i).getValue();
                        
                        // Buscar equipos
                        int idxLocal = Bib.buscarIndiceEquipo(nombresLocal.get(i), group.getEquipoList());
                        int idxVisit = Bib.buscarIndiceEquipo(nombresVisitante.get(i), group.getEquipoList());
                        
                        // Procesar partido
                        
                        Bib.asignarGolesADuelo(group.getEquipoList().get(idxLocal), group.getEquipoList().get(idxVisit), fecha, golesL, golesV, group.getCalendarioList());
                        Equipo.jugarPartido(group.getEquipoList().get(idxLocal), group.getEquipoList().get(idxVisit), golesL, golesV);
                    
                        // Resetear spinners
                        spinnersLocal.get(i).setValue(0);
                        spinnersVisitante.get(i).setValue(0);
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "No se pueden Re-Jugar los partidos", "Excepción de entrada", JOptionPane.ERROR_MESSAGE);
                    
                    continuar = false;
                }
                
                // Solo continuar si no hubo errores
                nuevaFechaFrame.removeAll();
                nuevaFechaFrame.setVisible(false);
                
                botonesFechas[fecha].setVisible(false);

                if (continuar) {
                    if (fecha < group.getCalendarioList().size()-1) { //si llegamos a la ultima fecha dejan de aparecer botones de nueva fecha
                        botonesFechas[fecha+1].setVisible(true);
                    }
                }

                group.pantalla.ordenarTabla(group.getEquipoList());
                InterfazGrupos.crearInterfazGrupos();
                InterfazGrupos.frame1.setVisible(true);
                group.pantalla.setVisible(true);

            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "Ingrese números válidos", "Error de entrada", JOptionPane.ERROR_MESSAGE);
            }
        });
        
    }

    public void ordenarTabla(ArrayList<Equipo> equipos) {
        // Guardar posición anterior
        for (int i = 0; i < equipos.size(); i++) {
            equipos.get(i).setPosAnterior(i);
        }
        Grupo.ordenarEquiposBurbuja(equipos);
        // Actualizar posiciones DESPUÉS de ordenar y asignar estado
        for (int i = 0; i < equipos.size(); i++) {
            equipos.get(i).setPosActual(i);
            equipos.get(i).actualizarEstado();
            
        }
        this.mostrarTabla(tablaLabels, pTabla, equipos);
        pTabla.revalidate();
        pTabla.repaint();
    }

    public void mostrarTabla(JLabel[][] tablaLabels, JPanel tabla, ArrayList<Equipo> equipos){
        
        GridBagConstraints rules = new GridBagConstraints();

        JLabel[] titulosLabel = new JLabel[10]; //son los titulos de las columnas
        tabla.removeAll(); //reordenar la tabla dinámica
        ordenarColores(equipos, tabla);

        rules.gridx = 0;
        rules.gridy = 0;
        rules.gridheight = 1;
        rules.gridwidth = 1;

        if (this.mostrarMasMenosButton.getText().equals("Ver -")) { //si el boton dice ver menos entonces esta seleccionada la tabla grande
                titulosLabel[0] = new JLabel("Equipo", 0);
                titulosLabel[1] = new JLabel("PJ", 0);
                titulosLabel[2] = new JLabel("PG", 0);
                titulosLabel[3] = new JLabel("PE", 0);
                titulosLabel[4] = new JLabel("PP", 0);
                titulosLabel[5] = new JLabel("GF", 0);
                titulosLabel[6] = new JLabel("GC", 0);
                titulosLabel[7] = new JLabel("DG", 0);
                titulosLabel[8] = new JLabel("Puntos", 0);
                titulosLabel[9] = new JLabel("Ultimos 5", 0);
            for (int i = 0; i < titulosLabel.length; i++) {
                if (i == 0) {
                    rules.gridx = i;
                    rules.gridwidth = 2;
                    rules.weightx = 1.0;
                    rules.fill = GridBagConstraints.BOTH;
                    titulosLabel[i].setBackground(new Color(75,75,75));
                    titulosLabel[i].setOpaque(true);
                    titulosLabel[i].setForeground(Color.white);
                    tabla.add(titulosLabel[i], rules);
                    rules.gridwidth = 1;
                } else if (i > 0 && i < 9) {
                    rules.gridx = i + 1;
                    rules.weightx = 1.0;
                    rules.fill = GridBagConstraints.BOTH;
                    titulosLabel[i].setBackground(new Color(75,75,75));
                    titulosLabel[i].setOpaque(true);
                    titulosLabel[i].setForeground(Color.white);
                    tabla.add(titulosLabel[i], rules);
                } else if (i == 9) {
                    rules.gridx = i + 1;
                    rules.gridwidth = 5;
                    rules.weightx = 1.0;
                    rules.fill = GridBagConstraints.BOTH;
                    titulosLabel[i].setBackground(new Color(75,75,75));
                    titulosLabel[i].setOpaque(true);
                    titulosLabel[i].setForeground(Color.white);
                    tabla.add(titulosLabel[i], rules);
                    rules.gridwidth = 1;
                }
                
            }

            for (int i = 0; i < equipos.size(); i++) {

            tablaLabels[i][0] = new JLabel(equipos.get(i).getEstado());

            tablaLabels[i][1] = new JLabel(equipos.get(i).getNombre());
            tablaLabels[i][1].setIcon(equipos.get(i).getImg());

            tablaLabels[i][2] = new JLabel(String.valueOf(equipos.get(i).getPartidosJugados()), 0); 
            tablaLabels[i][3] = new JLabel(String.valueOf(equipos.get(i).getPartidosGanados()), 0); 
            tablaLabels[i][4] = new JLabel(String.valueOf(equipos.get(i).getPartidosEmpatados()), 0); 
            tablaLabels[i][5] = new JLabel(String.valueOf(equipos.get(i).getPartidosPerdidos()), 0); 
            tablaLabels[i][6] = new JLabel(String.valueOf(equipos.get(i).getGolesFavor()), 0); 
            tablaLabels[i][7] = new JLabel(String.valueOf(equipos.get(i).getGolesContra()), 0); 
            tablaLabels[i][8] = new JLabel(String.valueOf(equipos.get(i).getDifGol()), 0); 
            tablaLabels[i][9] = new JLabel(String.valueOf(equipos.get(i).getPuntos()), 0);

            tablaLabels[i][10] = new JLabel(equipos.get(i).getUltimosResultados().get(0), 0);
            tablaLabels[i][11] = new JLabel(equipos.get(i).getUltimosResultados().get(1), 0);
            tablaLabels[i][12] = new JLabel(equipos.get(i).getUltimosResultados().get(2), 0);
            tablaLabels[i][13] = new JLabel(equipos.get(i).getUltimosResultados().get(3), 0);
            tablaLabels[i][14] = new JLabel(equipos.get(i).getUltimosResultados().get(4), 0);
            
            for (int j = 0; j < 15; j++) {
                rules.gridx = j;
                rules.gridwidth = 1;
                rules.gridheight = 1;
                if (j == 0) {
                    rules.anchor = GridBagConstraints.WEST;
                    rules.insets = new Insets(5, 10, 5, 5);
                    rules.gridy = i + 1;
                    rules.weightx = 1.0;
                    tabla.add(tablaLabels[i][j], rules);
                    rules.anchor = GridBagConstraints.CENTER;
                    rules.insets = new Insets(0,0,0,0);
                } else {
                    tabla.add(tablaLabels[i][j], rules);
                }
            }
            }
        } else if (this.mostrarMasMenosButton.getText().equals("Ver +")) { //si el boton dice ver mas entonces esta seleccionada la opcion de tabla pequeña
            titulosLabel[0] = new JLabel("Equipo", 0);
            titulosLabel[1] = new JLabel("DG", 0);
            titulosLabel[2] = new JLabel("Puntos", 0);
            titulosLabel[3] = new JLabel("Ultimos 5", 0);
            for (int i = 0; i < 4; i++) {
                if (i == 0) {
                    rules.gridx = i;
                    rules.gridwidth = 2;
                    rules.weightx = 1.0;
                    rules.fill = GridBagConstraints.BOTH;
                    titulosLabel[i].setBackground(new Color(75,75,75));
                    titulosLabel[i].setOpaque(true);
                    titulosLabel[i].setForeground(Color.white);
                    tabla.add(titulosLabel[i], rules);
                    rules.gridwidth = 1;
                } else if (i > 0 && i < 3) {
                    rules.gridx = i + 1;
                    rules.weightx = 1.0;
                    rules.fill = GridBagConstraints.BOTH;
                    titulosLabel[i].setBackground(new Color(75,75,75));
                    titulosLabel[i].setOpaque(true);
                    titulosLabel[i].setForeground(Color.white);
                    tabla.add(titulosLabel[i], rules);
                } else if (i == 3) {
                    rules.gridx = i + 1;
                    rules.gridwidth = 5;
                    rules.weightx = 1.0;
                    rules.fill = GridBagConstraints.BOTH;
                    titulosLabel[i].setBackground(new Color(75,75,75));
                    titulosLabel[i].setOpaque(true);
                    titulosLabel[i].setForeground(Color.white);
                    tabla.add(titulosLabel[i], rules);

                    rules.gridwidth = 1;
                }
            }
            for (int i = 0; i < equipos.size(); i++) {

            tablaLabels[i][0] = new JLabel(equipos.get(i).getEstado());

            tablaLabels[i][1] = new JLabel(equipos.get(i).getNombre());
            tablaLabels[i][1].setIcon(equipos.get(i).getImg());

            tablaLabels[i][2] = new JLabel(String.valueOf(equipos.get(i).getDifGol()), 0); 
            tablaLabels[i][3] = new JLabel(String.valueOf(equipos.get(i).getPuntos()), 0);

            tablaLabels[i][4] = new JLabel(equipos.get(i).getUltimosResultados().get(0), 0);
            tablaLabels[i][5] = new JLabel(equipos.get(i).getUltimosResultados().get(1), 0);
            tablaLabels[i][6] = new JLabel(equipos.get(i).getUltimosResultados().get(2), 0);
            tablaLabels[i][7] = new JLabel(equipos.get(i).getUltimosResultados().get(3), 0);
            tablaLabels[i][8] = new JLabel(equipos.get(i).getUltimosResultados().get(4), 0);
            
            for (int j = 0; j < 9; j++) {
                rules.gridx = j;
                rules.gridwidth = 1;
                rules.gridheight = 1;
                if (j == 0) {
                    rules.anchor = GridBagConstraints.WEST;
                    rules.insets = new Insets(5, 10, 5, 5);
                    rules.gridy = i + 1;
                    rules.weightx = 1.0;
                    tabla.add(tablaLabels[i][j], rules);
                    rules.anchor = GridBagConstraints.CENTER;
                    rules.insets = new Insets(0,0,0,0);
                } else {
                    tabla.add(tablaLabels[i][j], rules);
                }
                
            }
            }
        }
        pCentral.add(tabla, BorderLayout.CENTER);

        this.revalidate();
        this.repaint();
    }

    public void ordenarColores(ArrayList<Equipo> equipos, JPanel pTabla){

        JPanel[] pColors = new JPanel[equipos.size() + 1];

        GridBagConstraints rules = new GridBagConstraints();
        rules.gridx = 0;
        rules.gridy = 0;
        rules.gridwidth = 1;
        rules.gridheight = 1;
        rules.fill = GridBagConstraints.VERTICAL; // Llenar verticalmente
        rules.anchor = GridBagConstraints.WEST; // Pegar a la izquierda
        rules.weightx = 1.0;
        rules.weighty = 1.0;

        for (int i = 0; i < pColors.length; i++) {
            if (equipos.size() == 10) {
                rules.gridy = i;
                if (i == 0) {
                    pColors[i] = new JPanel();
                    pColors[i].setOpaque(true);
                    pColors[i].setBackground(new Color(75,75,75));
                    pTabla.add(pColors[i], rules);
                } else if (i > 0 && i < 7) {
                    pColors[i] = new JPanel();
                    pColors[i].setOpaque(true);
                    pColors[i].setBackground(new Color(0, 146, 255));
                    pTabla.add(pColors[i], rules);
                } else if (i == 7) {
                    pColors[i] = new JPanel();
                    pColors[i].setOpaque(true);
                    pColors[i].setBackground(new Color(255, 178, 0));
                    pTabla.add(pColors[i], rules);
                } else {
                    pColors[i] = new JPanel();
                    pColors[i].setOpaque(true);
                    pColors[i].setBackground(new Color(255, 0, 0)); 
                    pTabla.add(pColors[i], rules);  
                }
            } else if (equipos.size() == 4) {
                rules.gridy = i;
                if (i == 0) {
                    pColors[i] = new JPanel();
                    pColors[i].setOpaque(true);
                    pColors[i].setBackground(new Color(75,75,75));
                    pTabla.add(pColors[i], rules);
                } else if (i >= 0 && i < 3) {
                    pColors[i] = new JPanel();
                    pColors[i].setOpaque(true);
                    pColors[i].setBackground(new Color(0, 146, 255));
                    pTabla.add(pColors[i], rules);
                } else if (i == 3) {
                    pColors[i] = new JPanel();
                    pColors[i].setOpaque(true);
                    pColors[i].setBackground(new Color(255, 178, 0));
                    pTabla.add(pColors[i], rules);
                } else {
                    pColors[i] = new JPanel();
                    pColors[i].setOpaque(true);
                    pColors[i].setBackground(new Color(255, 0, 0)); 
                    pTabla.add(pColors[i], rules);  
                }
            }
        }
    }

    public void crearPanelSur(JPanel panel){
        panel.setLayout(new GridLayout(4, 2));
        panel.setBorder(new EmptyBorder(10, 10, 10,10));
        JLabel[] lbl = new JLabel[8];
        lbl[0] = new JLabel("Clasificación:");
        lbl[1] = new JLabel("Últimos 5 partidos:");
        Image img = new ImageIcon(PantallaDelGrupo.class.getResource("/FaseGruposCore/images/clasifica.png")).getImage().getScaledInstance(15, 15, Image.SCALE_SMOOTH);
        lbl[2] = new JLabel("Siguiente ronda");
        lbl[2].setIcon(new ImageIcon(img));
        img = new ImageIcon(PantallaDelGrupo.class.getResource("/FaseGruposCore/images/gano.png")).getImage().getScaledInstance(15, 15, Image.SCALE_SMOOTH);
        lbl[3] = new JLabel("Ganó");
        lbl[3].setIcon(new ImageIcon(img));
        img = new ImageIcon(PantallaDelGrupo.class.getResource("/FaseGruposCore/images/repechaje.png")).getImage().getScaledInstance(15, 15, Image.SCALE_SMOOTH);
        lbl[4] = new JLabel("Posible clasificación");
        lbl[4].setIcon(new ImageIcon(img));
        img = new ImageIcon(PantallaDelGrupo.class.getResource("/FaseGruposCore/images/perdio.png")).getImage().getScaledInstance(15, 15, Image.SCALE_SMOOTH);
        lbl[5] = new JLabel("Perdió");
        lbl[5].setIcon(new ImageIcon(img));
        img = new ImageIcon(PantallaDelGrupo.class.getResource("/FaseGruposCore/images/eliminado.png")).getImage().getScaledInstance(15, 15, Image.SCALE_SMOOTH);
        lbl[6] = new JLabel("Eliminado");
        lbl[6].setIcon(new ImageIcon(img));
        img = new ImageIcon(PantallaDelGrupo.class.getResource("/FaseGruposCore/images/empato.png")).getImage().getScaledInstance(15, 15, Image.SCALE_SMOOTH);
        lbl[7] = new JLabel("Empató");
        lbl[7].setIcon(new ImageIcon(img));
        for (JLabel jL : lbl) {
            jL.setForeground(Color.WHITE);
            panel.add(jL);
        }
    }
}
