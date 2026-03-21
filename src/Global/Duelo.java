package Global;

import java.util.Random;

import javax.swing.JOptionPane;

public class Duelo {
    private Equipo local;
    private Equipo visitante;

    private int gVis = -1;
    private int gLoc = -1;
    public Equipo getlocal() {
        return local;
    }
    public void setlocal(Equipo local) {
        this.local = local;
    }
    public Equipo getvisitante() {
        return visitante;
    }
    public void setvisitante(Equipo visitante) {
        this.visitante = visitante;
    }
    public int getgVis() {
        return gVis;
    }
    public void setgVis(int gVis) {
        this.gVis = gVis;
    }
    public int getgLoc() {
        return gLoc;
    }
    public void setgLoc(int gLoc) {
        this.gLoc = gLoc;
    }
    public Duelo(Equipo local, Equipo visitante) {
        this.local = local;
        this.visitante = visitante;
    }

    public Duelo() {
        //TODO Auto-generated constructor stub
    }
    public static void intercambiarLocalia(Duelo duelo, Equipo loc, Equipo vis){
        duelo.setlocal(vis);
        duelo.setvisitante(loc);
    }

    public Duelo intercambiarLocalia(){
        Equipo vis = this.visitante;
        Equipo loc = this.local;

        Duelo duelo2 = new Duelo(vis, loc);
        
        return duelo2;
    }

    public boolean mismoDuelo(Duelo duelo){
        boolean repiteDuelo = false;
        if (this.local == duelo.local && this.visitante == duelo.visitante) {
            repiteDuelo = true;
        } else if (this.local == duelo.visitante && this.visitante == duelo.local) {
            repiteDuelo = true;
        }
        return repiteDuelo;
    }

    public String dueloText(){
        return this.local.getNombre() + " " + this.gLoc + " vs " +  this.gVis + " " + this.visitante.getNombre(); 
    }

    //Ronda elimDirecta
    public void asignarGanadorADuelo(int golesL, int golesV, Duelo sigDuelo, int posSigDuelo, int idxSigDl){//1 Local, 0 Visitante
        this.gLoc = golesL;
        this.gVis = golesV;
        if (this.gLoc > this.gVis) {
            //System.out.print("ganó " + this.local.getNombre());
            if (posSigDuelo == 0) {
                sigDuelo.local = this.local;
            } else if (posSigDuelo == 1) {
                sigDuelo.visitante = this.local;
            }
        } else if (this.gLoc < this.gVis) {
            //System.out.print("ganó " + this.visitante.getNombre());
            if (posSigDuelo == 0) {
                sigDuelo.local = this.visitante;
            } else if (posSigDuelo == 1) {
                sigDuelo.visitante = this.visitante;
            }
        } else {
            //System.out.println("Empate: " + this.local.getNombre() + " vs " + this.visitante.getNombre());
        }
    }

    public void asignarPerdedorA3Lugar(int golesL, int golesV, Duelo sigDuelo, int posSigDuelo, int idxSigDl){//1 Local, 0 Visitante
        this.gLoc = golesL;
        this.gVis = golesV;
        if (this.gLoc > this.gVis) {
            //System.out.print("ganó " + this.local.getNombre());
            if (posSigDuelo == 0) {
                sigDuelo.local = this.visitante;
            } else if (posSigDuelo == 1) {
                sigDuelo.visitante = this.visitante;
            }
        } else if (this.gLoc < this.gVis) {
            //System.out.print("ganó " + this.visitante.getNombre());
            if (posSigDuelo == 0) {
                sigDuelo.local = this.local;
            } else if (posSigDuelo == 1) {
                sigDuelo.visitante = this.local;
            }
        } else {
            //System.out.println("Empate: " + this.local.getNombre() + " vs " + this.visitante.getNombre());
        }
    }

    public int[] simularPartido() {
        Equipo eqA = this.local; 
        Equipo eqB = this.visitante;
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

        if (golesA == golesB) {
            final Random random = new Random();
            double u = random.nextDouble();
            if (u > 0.5) {
                golesA++;
            } else if (u < 0.5) {
                golesB++;
            }
            //JOptionPane.showMessageDialog(null, "Desempate para " + eqA.getNombre() + " vs " + eqB.getNombre());
        }

        System.out.println(String.format("%s %d - %d %s", eqA.getNombre(), golesA, golesB, eqB.getNombre()));

        int[] goles = {golesA,golesB};

        this.gVis = golesB;
        this.gLoc = golesA;

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
}
