package FaseGruposCore.biblioteca;

import java.util.ArrayList;

import Global.Duelo;
import Global.Equipo;

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
    
    public static Equipo eqRandom(ArrayList<Equipo> list) {
        int num = (int)(Math.random() * list.size());
        Equipo eq = list.get(num);
        return eq;
    }
}
