package sorteoCore.biblioteca;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class SorteoMundialFrame extends JFrame {
    // Colores inspirados en el ambiente de sorteo
    private static final Color COLOR_FONDO = new Color(10, 25, 47);     // Azul noche
    private static final Color COLOR_PANEL_LATERAL = new Color(20, 40, 70);
    private static final Color COLOR_PANEL_CENTRAL = new Color(15, 30, 55);
    private static final Color COLOR_PANEL_INFERIOR = new Color(25, 50, 85);
    private static final Color COLOR_TEXTO = new Color(240, 240, 240);
    private static final Color COLOR_DESTACADO = new Color(255, 204, 0); // Dorado
    private static final Color COLOR_BOTON = new Color(0, 100, 200);
    
    // Componentes principales
    private JPanel panelLateral;
    private JPanel panelCentral;
    private JPanel panelInferior;
    
    // Listas simuladas (tú las reemplazarás con datos reales)
    private List<String> equiposDisponibles;
    private List<String> grupos;
    private List<String> historialSorteo;
    
    public SorteoMundialFrame() {
        // Configuración básica del frame
        setTitle("SORTEO MUNDIAL DE FÚTBOL");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH); // Pantalla completa
        setLayout(new BorderLayout());
        getContentPane().setBackground(COLOR_FONDO);
        
        // Inicializar datos de ejemplo
        inicializarDatosEjemplo();
        
        // Crear paneles principales
        crearPanelLateral();
        crearPanelCentral();
        crearPanelInferior();
        
        // Mostrar frame
        setVisible(true);
    }
    
    private void inicializarDatosEjemplo() {
        equiposDisponibles = new ArrayList<>();
        // Añadir algunos equipos de ejemplo
        equiposDisponibles.add("ARGENTINA");
        equiposDisponibles.add("BRASIL");
        equiposDisponibles.add("ALEMANIA");
        equiposDisponibles.add("ESPAÑA");
        equiposDisponibles.add("FRANCIA");
        equiposDisponibles.add("INGLATERRA");
        equiposDisponibles.add("ITALIA");
        equiposDisponibles.add("HOLANDA");
        equiposDisponibles.add("PORTUGAL");
        equiposDisponibles.add("BÉLGICA");
        equiposDisponibles.add("URUGUAY");
        equiposDisponibles.add("MÉXICO");
        
        grupos = new ArrayList<>();
        grupos.add("GRUPO A: - - - -");
        grupos.add("GRUPO B: - - - -");
        grupos.add("GRUPO C: - - - -");
        grupos.add("GRUPO D: - - - -");
        grupos.add("GRUPO E: - - - -");
        grupos.add("GRUPO F: - - - -");
        grupos.add("GRUPO G: - - - -");
        grupos.add("GRUPO H: - - - -");
        
        historialSorteo = new ArrayList<>();
        historialSorteo.add("▶ Inicio del sorteo...");
    }
    
    private void crearPanelLateral() {
        panelLateral = new JPanel();
        panelLateral.setBackground(COLOR_PANEL_LATERAL);
        panelLateral.setPreferredSize(new Dimension(300, 0)); // Ancho fijo
        panelLateral.setLayout(new BorderLayout(10, 10));
        panelLateral.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        // Título del panel lateral
        JLabel tituloEquipos = new JLabel("EQUIPOS POR SORTEAR");
        tituloEquipos.setFont(new Font("Arial", Font.BOLD, 24));
        tituloEquipos.setForeground(COLOR_DESTACADO);
        tituloEquipos.setHorizontalAlignment(SwingConstants.CENTER);
        panelLateral.add(tituloEquipos, BorderLayout.NORTH);
        
        // Lista de equipos disponibles
        DefaultListModel<String> modeloLista = new DefaultListModel<>();
        for (String equipo : equiposDisponibles) {
            modeloLista.addElement(equipo);
        }
        
        JList<String> listaEquipos = new JList<>(modeloLista);
        listaEquipos.setFont(new Font("Arial", Font.PLAIN, 18));
        listaEquipos.setForeground(COLOR_TEXTO);
        listaEquipos.setBackground(COLOR_PANEL_LATERAL);
        listaEquipos.setSelectionBackground(COLOR_BOTON);
        listaEquipos.setSelectionForeground(Color.WHITE);
        
        JScrollPane scrollEquipos = new JScrollPane(listaEquipos);
        scrollEquipos.setBorder(BorderFactory.createLineBorder(COLOR_DESTACADO, 1));
        panelLateral.add(scrollEquipos, BorderLayout.CENTER);
        
        // Contador de equipos
        JLabel contadorLabel = new JLabel("Equipos restantes: " + equiposDisponibles.size());
        contadorLabel.setFont(new Font("Arial", Font.BOLD, 16));
        contadorLabel.setForeground(COLOR_TEXTO);
        contadorLabel.setHorizontalAlignment(SwingConstants.CENTER);
        panelLateral.add(contadorLabel, BorderLayout.SOUTH);
        
        add(panelLateral, BorderLayout.WEST);
    }
    
    private void crearPanelCentral() {
        panelCentral = new JPanel();
        panelCentral.setBackground(COLOR_PANEL_CENTRAL);
        panelCentral.setLayout(new BorderLayout());
        panelCentral.setBorder(BorderFactory.createEmptyBorder(20, 20, 10, 20));
        
        // Encabezado central
        JLabel tituloCentral = new JLabel("SORTEO EN VIVO");
        tituloCentral.setFont(new Font("Arial", Font.BOLD, 36));
        tituloCentral.setForeground(COLOR_DESTACADO);
        tituloCentral.setHorizontalAlignment(SwingConstants.CENTER);
        panelCentral.add(tituloCentral, BorderLayout.NORTH);
        
        // Área de la balota (simulación)
        JPanel panelBalota = new JPanel();
        panelBalota.setLayout(new GridBagLayout());
        panelBalota.setBackground(COLOR_PANEL_CENTRAL);
        panelBalota.setBorder(BorderFactory.createEmptyBorder(50, 50, 50, 50));
        
        // Balota circular (simulada con un JLabel)
        JLabel balotaLabel = new JLabel("?");
        balotaLabel.setFont(new Font("Arial", Font.BOLD, 120));
        balotaLabel.setForeground(COLOR_DESTACADO);
        balotaLabel.setOpaque(true);
        balotaLabel.setBackground(new Color(200, 50, 50)); // Rojo balota
        balotaLabel.setHorizontalAlignment(SwingConstants.CENTER);
        balotaLabel.setPreferredSize(new Dimension(300, 300));
        balotaLabel.setBorder(BorderFactory.createLineBorder(Color.WHITE, 5));
        
        // Hacer la balota circular
        balotaLabel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(Color.WHITE, 5),
            BorderFactory.createEmptyBorder(10, 10, 10, 10)
        ));
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(20, 20, 20, 20);
        panelBalota.add(balotaLabel, gbc);
        
        // Panel de control del sorteo
        JPanel panelControl = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        panelControl.setBackground(COLOR_PANEL_CENTRAL);
        
        JButton btnSortear = new JButton("SORTEAR EQUIPO");
        estilizarBoton(btnSortear, 24);
        btnSortear.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Aquí irá la lógica del sorteo
                simularSorteo(balotaLabel);
            }
        });
        
        JButton btnReiniciar = new JButton("REINICIAR SORTEO");
        estilizarBoton(btnReiniciar, 18);
        
        panelControl.add(btnSortear);
        panelControl.add(btnReiniciar);
        
        // Panel para el historial del sorteo
        JPanel panelHistorial = new JPanel(new BorderLayout());
        panelHistorial.setBackground(COLOR_PANEL_CENTRAL);
        panelHistorial.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(COLOR_DESTACADO, 2),
            "HISTORIAL DEL SORTEO",
            0, 0,
            new Font("Arial", Font.BOLD, 16),
            COLOR_DESTACADO
        ));
        
        DefaultListModel<String> modeloHistorial = new DefaultListModel<>();
        for (String evento : historialSorteo) {
            modeloHistorial.addElement(evento);
        }
        
        JList<String> listaHistorial = new JList<>(modeloHistorial);
        listaHistorial.setFont(new Font("Arial", Font.PLAIN, 16));
        listaHistorial.setForeground(COLOR_TEXTO);
        listaHistorial.setBackground(new Color(10, 20, 35));
        
        JScrollPane scrollHistorial = new JScrollPane(listaHistorial);
        scrollHistorial.setPreferredSize(new Dimension(0, 150));
        panelHistorial.add(scrollHistorial, BorderLayout.CENTER);
        
        // Organizar componentes en el panel central
        JPanel contenedorCentral = new JPanel(new BorderLayout());
        contenedorCentral.setBackground(COLOR_PANEL_CENTRAL);
        contenedorCentral.add(panelBalota, BorderLayout.CENTER);
        contenedorCentral.add(panelControl, BorderLayout.SOUTH);
        
        panelCentral.add(contenedorCentral, BorderLayout.CENTER);
        panelCentral.add(panelHistorial, BorderLayout.SOUTH);
        
        add(panelCentral, BorderLayout.CENTER);
    }
    
    private void crearPanelInferior() {
        panelInferior = new JPanel();
        panelInferior.setBackground(COLOR_PANEL_INFERIOR);
        panelInferior.setPreferredSize(new Dimension(0, 250)); // Alto fijo
        panelInferior.setLayout(new GridLayout(2, 4, 15, 15));
        panelInferior.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        // Título del panel inferior
        JLabel tituloGrupos = new JLabel("CONFORMACIÓN DE GRUPOS");
        tituloGrupos.setFont(new Font("Arial", Font.BOLD, 28));
        tituloGrupos.setForeground(COLOR_DESTACADO);
        tituloGrupos.setHorizontalAlignment(SwingConstants.CENTER);
        
        // Panel que envuelve todo el inferior
        JPanel contenedorInferior = new JPanel(new BorderLayout());
        contenedorInferior.setBackground(COLOR_PANEL_INFERIOR);
        contenedorInferior.add(tituloGrupos, BorderLayout.NORTH);
        contenedorInferior.add(panelInferior, BorderLayout.CENTER);
        
        // Crear paneles para cada grupo
        for (int i = 0; i < grupos.size(); i++) {
            JPanel panelGrupo = crearPanelGrupo(grupos.get(i), i);
            panelInferior.add(panelGrupo);
        }
        
        add(contenedorInferior, BorderLayout.SOUTH);
    }
    
    private JPanel crearPanelGrupo(String nombreGrupo, int numeroGrupo) {
        JPanel panelGrupo = new JPanel();
        panelGrupo.setLayout(new BorderLayout());
        panelGrupo.setBackground(new Color(40, 70, 120));
        panelGrupo.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(COLOR_DESTACADO, 2),
            BorderFactory.createEmptyBorder(10, 10, 10, 10)
        ));
        
        // Nombre del grupo
        JLabel labelGrupo = new JLabel(nombreGrupo);
        labelGrupo.setFont(new Font("Arial", Font.BOLD, 20));
        labelGrupo.setForeground(COLOR_DESTACADO);
        labelGrupo.setHorizontalAlignment(SwingConstants.CENTER);
        panelGrupo.add(labelGrupo, BorderLayout.NORTH);
        
        // Lista de equipos del grupo (vacía inicialmente)
        DefaultListModel<String> modeloEquipos = new DefaultListModel<>();
        // Inicialmente vacío, se llenará durante el sorteo
        
        JList<String> listaEquiposGrupo = new JList<>(modeloEquipos);
        listaEquiposGrupo.setFont(new Font("Arial", Font.PLAIN, 16));
        listaEquiposGrupo.setForeground(COLOR_TEXTO);
        listaEquiposGrupo.setBackground(new Color(30, 60, 110));
        
        JScrollPane scrollEquiposGrupo = new JScrollPane(listaEquiposGrupo);
        panelGrupo.add(scrollEquiposGrupo, BorderLayout.CENTER);
        
        // Etiqueta para el contador de equipos en el grupo
        JLabel contadorEquipos = new JLabel("0/4 equipos");
        contadorEquipos.setFont(new Font("Arial", Font.ITALIC, 12));
        contadorEquipos.setForeground(COLOR_TEXTO);
        contadorEquipos.setHorizontalAlignment(SwingConstants.RIGHT);
        panelGrupo.add(contadorEquipos, BorderLayout.SOUTH);
        
        return panelGrupo;
    }
    
    private void estilizarBoton(JButton boton, int tamañoFuente) {
        boton.setFont(new Font("Arial", Font.BOLD, tamañoFuente));
        boton.setForeground(Color.WHITE);
        boton.setBackground(COLOR_BOTON);
        boton.setFocusPainted(false);
        boton.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(COLOR_DESTACADO, 2),
            BorderFactory.createEmptyBorder(10, 20, 10, 20)
        ));
        boton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        // Efecto hover
        boton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                boton.setBackground(COLOR_BOTON.brighter());
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                boton.setBackground(COLOR_BOTON);
            }
        });
    }
    
    private void simularSorteo(JLabel balotaLabel) {
        // Animación simple de la balota
        Timer timer = new Timer(100, new ActionListener() {
            private int contador = 0;
            private String[] equiposTemporales = {"...", "SORTEANDO", "...", "EQUIPO", "...", "BALOTA"};
            
            @Override
            public void actionPerformed(ActionEvent e) {
                if (contador < 10) {
                    balotaLabel.setText(equiposTemporales[contador % equiposTemporales.length]);
                    contador++;
                } else {
                    ((Timer) e.getSource()).stop();
                    // Aquí pondrías el equipo sorteado real
                    balotaLabel.setText("BRASIL");
                    balotaLabel.setForeground(Color.WHITE);
                    balotaLabel.setBackground(new Color(0, 150, 0)); // Verde éxito
                    
                    // Aquí añadirías la lógica real del sorteo
                    JOptionPane.showMessageDialog(SorteoMundialFrame.this,
                        "¡Equipo sorteado: BRASIL!\nAsignado al Grupo A",
                        "SORTEO COMPLETADO",
                        JOptionPane.INFORMATION_MESSAGE);
                }
            }
        });
        timer.start();
    }
    
    public static void main(String[] args) {
        // Ejecutar en el Event Dispatch Thread
        SwingUtilities.invokeLater(() -> {
            try {
                // Establecer look and feel
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            } catch (Exception e) {
                e.printStackTrace();
            }
            new SorteoMundialFrame();
        });
    }
}
