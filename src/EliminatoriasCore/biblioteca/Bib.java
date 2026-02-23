package EliminatoriasCore.biblioteca;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import javax.swing.border.EmptyBorder;

import Global.Equipo;
import Global.FondoPanel;
import Global.Grupo;

public class Bib {
    public static int buscarIndiceEquipo(String nombre, ArrayList<Equipo> equipos) { //nombre del equipo que juega, se compara los nombres de los equipos registrados y se encuentra su índice
        for (int i = 0; i < equipos.size(); i++) {
            if (nombre == equipos.get(i).getNombre()) { // comparar contenido del String
                return i;
            }
        }
        return -1; // si no lo encuentra
    }

    public static void asignarGolesADuelo(Equipo local, Equipo visit, int fecha, int golesL, int golesV, ArrayList<ArrayList<Duelo>> calendario) { //nombre del equipo que juega, se compara los nombres de los equipos registrados y se encuentra su índice
        for (int i = 0; i < calendario.get(fecha).size(); i++) {
            if (calendario.get(fecha).get(i).getlocal() == local && calendario.get(fecha).get(i).getvisitante() == visit) {
                int indice = i;
                calendario.get(fecha).get(indice).setgLoc(golesL);
                calendario.get(fecha).get(indice).setgVis(golesV);
            }
        }
    }

    public static void botonFecha(ArrayList<Duelo> fechaListDuelos, JLabel[][] tablaLabels, JPanel tabla, int fecha, JButton[] botonesFechas, Grupo group){ //contenido al presionar el boton de la fecha
        JFrame nuevaFechaFrame = new JFrame("Jugar fecha " + (fecha + 1)); //Se crea la ventana de los partidos

        nuevaFechaFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        nuevaFechaFrame.setSize(350, 200);
        nuevaFechaFrame.setResizable(false);
        nuevaFechaFrame.setLocationRelativeTo(null);

        FondoPanel img = new FondoPanel("/EliminatoriasCore/images/fondo_fecha.png");
        nuevaFechaFrame.setContentPane(img);

        nuevaFechaFrame.setLayout(new BorderLayout());

        JPanel pPartidos = new JPanel();
        pPartidos.setLayout(new GridBagLayout());
        pPartidos.setBorder(new EmptyBorder(30,25,0,25));
        JPanel pSouth = new JPanel();;

        pPartidos.setOpaque(false);
        pSouth.setOpaque(false);

        pSouth.setLayout(new FlowLayout());

        GridBagConstraints rules = new GridBagConstraints();

        rules.gridx = 0;//en la casilla 0,0
        rules.gridy = 5;
        rules.gridheight = 1;// ocupa 1 casilla de alto y 5 de ancho
        rules.gridwidth = 5;
        rules.weightx = 1.0;
        rules.weighty = 1.0;
        JButton calcular = new JButton("Calcular");
        pSouth.add(calcular);

        nuevaFechaFrame.add(pSouth, BorderLayout.CENTER);

        JPanel[] partidosPanel1 = new JPanel[group.getEquipoList().size()/2];

        for (int i = 0; i < partidosPanel1.length; i++) {
            partidosPanel1[i] = new JPanel();
            partidosPanel1[i].setLayout(new GridLayout(1, 5, 0, 0));
            partidosPanel1[i].removeAll();
        }
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
                
                // Solo continuar si no hubo errores
                nuevaFechaFrame.removeAll();
                nuevaFechaFrame.setVisible(false);
                
                botonesFechas[fecha].setVisible(false);

                if (fecha < group.getCalendarioList().size()-1) { //si llegamos a la ultima fecha dejan de aparecer botones de nueva fecha
                    botonesFechas[fecha+1].setVisible(true);
                }
                group.pantalla.ordenarTabla(group.getEquipoList(), tablaLabels, tabla);
                InterfazGrupos.crearInterfazGrupos();
                InterfazGrupos.frame1.setVisible(true);
                group.pantalla.setVisible(true);

            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "Ingrese números válidos", "Error de entrada", JOptionPane.ERROR_MESSAGE);
            }
        
        
    });
        
    }

    public static Equipo eqRandom(ArrayList<Equipo> list) {
        int num = (int)(Math.random() * list.size());
        Equipo eq = list.get(num);
        return eq;
    }


    
}
