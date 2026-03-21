package Global;

import java.awt.Image;
import java.util.LinkedList;
import java.util.Random;

import javax.swing.ImageIcon;

import FaseGruposCore.biblioteca.PantallaDelGrupo;

public class Equipo {
    private String nombre;
    private int ranking;
    private String confederacion;
    private int bombo;
    private int grupoOriginal;
    
    private ImageIcon img;
    private int partidosJugados = 0, partidosGanados = 0, partidosEmpatados = 0, partidosPerdidos = 0, golesFavor = 0, golesContra = 0, difGol = 0, puntos = 0;
    private int posActual = -1, posAnterior = -1;
    private ImageIcon estado;
    private LinkedList<ImageIcon> ultimosResultados = new LinkedList<>();

    public Equipo(){}
    
    public Equipo(String nombre, int ranking, String confederacion) {
        this.nombre = nombre;
        this.ranking = ranking;
        this.puntos = 0;
        this.confederacion = confederacion;
        ImageIcon iconoOriginal1 = new ImageIcon(PantallaDelGrupo.class.getResource("/FaseGruposCore/images/sinjugar.png"));
        Image imgEscalada1 = iconoOriginal1.getImage().getScaledInstance(13,13, Image.SCALE_SMOOTH);
        for (int i = 0; i < 5; i++) {
            this.ultimosResultados.add(new ImageIcon(imgEscalada1));
        }

        actualizarImg(nombre);
        if (this.img == null) {
            if (nombre.substring(0, 3).equals("FIFA")) {
                actualizarImg("fifa");
            } else if (nombre.substring(0, 3).equals("UEFA")) {
                actualizarImg("uefa");
            } else {
                System.out.println("No se encontró imagen para el equipo: " + nombre);
            }
        }
    }

    public Equipo(String nombre, int ranking, String confederacion, String ruta) {
        this.nombre = nombre;
        this.ranking = ranking;
        this.puntos = 0;
        this.confederacion = confederacion;

        ImageIcon iconoOriginal1 = new ImageIcon(PantallaDelGrupo.class.getResource("/FaseGruposCore/images/sinjugar.png"));
        Image imgEscalada1 = iconoOriginal1.getImage().getScaledInstance(13,13, Image.SCALE_SMOOTH);
        for (int i = 0; i < 5; i++) {
            this.ultimosResultados.add(new ImageIcon(imgEscalada1));
        }
        
        ImageIcon iconoOriginal = new ImageIcon(getClass().getResource(ruta));
        Image imgOriginal = iconoOriginal.getImage();
        int anchoOriginal = iconoOriginal.getIconWidth();
        int altoOriginal = iconoOriginal.getIconHeight();
        int nuevoAncho = 24;
        int nuevoAlto = (nuevoAncho * altoOriginal) / anchoOriginal;
        Image imgEscalada = imgOriginal.getScaledInstance(nuevoAncho, nuevoAlto, Image.SCALE_SMOOTH);
        this.img = new ImageIcon(imgEscalada);
    
    }

    public Equipo(String nombre, int puntos, int ranking) {
        this.nombre = nombre;
        this.puntos = puntos;
        this.ranking = ranking;
        ImageIcon iconoOriginal = new ImageIcon(PantallaDelGrupo.class.getResource("/FaseGruposCore/images/sinjugar.png"));
        Image imgEscalada = iconoOriginal.getImage().getScaledInstance(13,13, Image.SCALE_SMOOTH);
        for (int i = 0; i < 5; i++) {
            this.ultimosResultados.add(new ImageIcon(imgEscalada));
        }
        actualizarImg(nombre);
        if (this.img == null) {
            if (nombre.substring(0, 4).equals("FIFA")) {
                actualizarImg("fifa");
            } else if (nombre.substring(0, 4).equals("UEFA")) {
                actualizarImg("uefa");
            } else {
                System.out.println("No se encontró imagen para el equipo: " + nombre);
            }
        }
    }

    public Equipo(String nombre) {
        this.nombre = nombre;
    }

    public void actualizarImg(String nombre) {
        try {
            ImageIcon iconoOriginal = new ImageIcon(getClass().getResource("/FaseSorteoCore/images/banderas/" + nombre + ".png"));
            Image imgOriginal = iconoOriginal.getImage();
            int anchoOriginal = iconoOriginal.getIconWidth();
            int altoOriginal = iconoOriginal.getIconHeight();
            int nuevoAlto = 15;
            int nuevoAncho = (nuevoAlto * anchoOriginal) / altoOriginal;
            Image imgEscalada = imgOriginal.getScaledInstance(nuevoAncho, nuevoAlto, Image.SCALE_SMOOTH);
            this.img = new ImageIcon(imgEscalada);
        } catch (NullPointerException e) {
            this.img = null;
        }
    }

    
    public static void statsGoles(Equipo equipo, int goles, int golesContra) {
        equipo.golesFavor = equipo.golesFavor + goles;
        equipo.golesContra = equipo.golesContra + golesContra;
        equipo.difGol = equipo.golesFavor - equipo.golesContra;
    }

    public static int[] simularPartido(Equipo eqA, Equipo eqB) {
        // 1. Invertimos la diferencia: Si eqA es puesto 5 y eqB es puesto 50, 
        // la diferencia debe ser positiva para el equipo A (mejor ranking).
        int diferencia = eqB.getRanking() - eqA.getRanking();

        double ventajaA = 0;
        double ventajaB = 0;

        // 2. Aplicamos la ventaja al equipo con el número de ranking más bajo
        // He ajustado el divisor a 30. Con 15, un puesto 1 vs un puesto 31 
        // daría +2 goles de ventaja base, lo cual es mucho. Con 30 es más equilibrado.
        if (diferencia > 0) {
            // eqA es mejor (ej: 50 - 5 = 45 de ventaja)
            ventajaA = diferencia / 30.0; 
        } else {
            // eqB es mejor (ej: 5 - 50 = -45 -> ventaja de 45 para B)
            ventajaB = Math.abs(diferencia) / 30.0;
        }

        // 3. Generar goles
        int golesA = generarGoles(ventajaA);
        int golesB = generarGoles(ventajaB);

        /*String res = String.format("%s %d - %d %s", eqA.getNombre(), golesA, golesB, eqB.getNombre());
        System.out.println(res);*/

        int[] goles = {golesA,golesB};

        return goles;
    }

    private static int generarGoles(double ventaja) {
        // El factor 1.2 es la base de goles aleatorios + la ventaja por ranking
        double lambda = 1.1 + ventaja; 
        int goles = 0;
        double p = Math.exp(-lambda);
        double f = p;
        final Random random = new Random();
        double u = random.nextDouble();

        while (u > f) {
            goles++;
            p *= lambda / goles;
            f += p;
        }
        return goles;
    }


    public static void jugarPartido(Equipo local, Equipo visitante, int golesLocal, int golesVisitante) {
        local.partidosJugados++;
        visitante.partidosJugados++;
        if (golesLocal > golesVisitante) {
            local.partidosGanados++;
            visitante.partidosPerdidos++;
            local.puntos+=3;
            local.actualizarUltimosResultados("gano");
            visitante.actualizarUltimosResultados("perdio");
        } else if (golesLocal < golesVisitante) {
            visitante.partidosGanados++;
            local.partidosPerdidos++;
            visitante.puntos+=3;
            visitante.actualizarUltimosResultados("gano");
            local.actualizarUltimosResultados("perdio");
        } else {
            local.partidosEmpatados++;
            visitante.partidosEmpatados++;
            local.puntos++;
            visitante.puntos++;
            local.actualizarUltimosResultados("empato");
            visitante.actualizarUltimosResultados("empato");
        }
        statsGoles(local, golesLocal, golesVisitante);
        statsGoles(visitante, golesVisitante, golesLocal);
    }
    
    public void actualizarEstado() {
        if (this.partidosJugados != 0) {
            String rutaIcono;
            
            if (this.posActual < this.posAnterior) {
                rutaIcono = "/FaseGruposCore/images/subio.png";
            } else if (this.posActual > this.posAnterior) {
                rutaIcono = "/FaseGruposCore/images/bajo.png";
            } else {
                rutaIcono = "/FaseGruposCore/images/mantuvo.png";
            }
            
            // Crear ImageIcon directamente
            ImageIcon iconoOriginal = new ImageIcon(PantallaDelGrupo.class.getResource(rutaIcono));
            Image imgEscalada = iconoOriginal.getImage().getScaledInstance(13,13, Image.SCALE_SMOOTH);
            this.estado = new ImageIcon(imgEscalada);
        }
    }
    
    public void actualizarUltimosResultados(String resultadoActual) {
        String rutaIcono = "";
        boolean error = false;
        if (resultadoActual.equals("gano")) {
            rutaIcono = "/FaseGruposCore/images/gano.png";
        } else if (resultadoActual.equals("perdio")) {
            rutaIcono = "/FaseGruposCore/images/perdio.png";
        } else if (resultadoActual.equals("empato")) {
            rutaIcono = "/FaseGruposCore/images/empato.png";
        } else {
            error = true;
        }
        if (!error) {
            // Crear ImageIcon directamente
            ImageIcon iconoOriginal = new ImageIcon(PantallaDelGrupo.class.getResource(rutaIcono));
            Image imgEscalada = iconoOriginal.getImage().getScaledInstance(13,13, Image.SCALE_SMOOTH);
            this.ultimosResultados.add(new ImageIcon(imgEscalada));
            this.ultimosResultados.removeFirst();
        }
            
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getRanking() {
        return ranking;
    }

    public void setRanking(int ranking) {
        this.ranking = ranking;
    }

    public String getConfederacion() {
        return confederacion;
    }

    public void setConfederacion(String confederacion) {
        this.confederacion = confederacion;
    }

    public int getBombo() {
        return bombo;
    }

    public void setBombo(int bombo) {
        this.bombo = bombo;
    }

    public ImageIcon getImg() {
        return img;
    }

    public void setImg(ImageIcon img) {
        this.img = img;
    }
        
    public int getPartidosJugados() {
        return partidosJugados;
    }
    public void setPartidosJugados(int partidosJugados) {
        this.partidosJugados = partidosJugados;
    }
    public int getPartidosGanados() {
        return partidosGanados;
    }
    public void setPartidosGanados(int partidosGanados) {
        this.partidosGanados = partidosGanados;
    }
    public int getPartidosEmpatados() {
        return partidosEmpatados;
    }
    public void setPartidosEmpatados(int partidosEmpatados) {
        this.partidosEmpatados = partidosEmpatados;
    }
    public int getPartidosPerdidos() {
        return partidosPerdidos;
    }
    public void setPartidosPerdidos(int partidosPerdidos) {
        this.partidosPerdidos = partidosPerdidos;
    }
    public int getGolesFavor() {
        return golesFavor;
    }
    public void setGolesFavor(int golesFavor) {
        this.golesFavor = golesFavor;
    }
    public int getGolesContra() {
        return golesContra;
    }
    public void setGolesContra(int golesContra) {
        this.golesContra = golesContra;
    }
    public int getDifGol() {
        return difGol;
    }
    public void setDifGol(int difGol) {
        this.difGol = difGol;
    }
    public int getPuntos() {
        return puntos;
    }
    public void setPuntos(int puntos) {
        this.puntos = puntos;
    }
    public int getPosActual() {
        return posActual;
    }
    public void setPosActual(int posActual) {
        this.posActual = posActual;
    }
    public int getPosAnterior() {
        return posAnterior;
    }
    public void setPosAnterior(int posAnterior) {
        this.posAnterior = posAnterior;
    }
    public ImageIcon getEstado() {
        return estado;
    }
    public void setEstado(ImageIcon estado) {
        this.estado = estado;
    }
    public LinkedList<ImageIcon> getUltimosResultados() {
        return ultimosResultados;
    }
    public void setUltimosResultados(LinkedList<ImageIcon> ultimosResultados) {
        this.ultimosResultados = ultimosResultados;
    }
    public void setGrupoOriginal(int grupoOriginal) {
        this.grupoOriginal = grupoOriginal;
    }
    public int getGrupoOriginal() {
        return grupoOriginal;
    }
}
