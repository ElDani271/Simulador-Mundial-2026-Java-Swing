package sorteoCore.biblioteca;
import java.util.ArrayList;

import Global.Equipo;
import Global.Grupo;

public class Data {
    private static ArrayList<Equipo> equipoList = new ArrayList<Equipo>();

    private static Bombo bombos[] = {
                new Bombo("1", new ArrayList<>()),
                new Bombo("2", new ArrayList<>()),
                new Bombo("3", new ArrayList<>()),
                new Bombo("4", new ArrayList<>()),
            };

    private static Grupo grupos[] = {
            new Grupo("A", new ArrayList<>()),
            new Grupo("B", new ArrayList<>()),
            new Grupo("C", new ArrayList<>()),
            new Grupo("D", new ArrayList<>()),
            new Grupo("E", new ArrayList<>()),
            new Grupo("F", new ArrayList<>()),
            new Grupo("G", new ArrayList<>()),
            new Grupo("H", new ArrayList<>()),
            new Grupo("I", new ArrayList<>()),
            new Grupo("J", new ArrayList<>()),
            new Grupo("K", new ArrayList<>()),
            new Grupo("L", new ArrayList<>()),
    };

    public static void inicializarGrupos(Grupo[] grAntiguos){
        for (int i = 0; i < grAntiguos.length; i++) {
            for (Equipo e : grAntiguos[i].getEquipoList()) {
                
                grupos[i].getEquipoList().add(e);
            }
        }
    }

    public static ArrayList<Equipo> getEquipoList() {
        return equipoList;
    }

    public static void setEquipoList(ArrayList<Equipo> equipoList) {
        Data.equipoList = equipoList;
    }

    public static Bombo[] getBombos() {
        return bombos;
    }

    public static Bombo getBombos(int indice) {
        if (bombos != null && indice >= 0 && indice < bombos.length) {
            return bombos[indice];
        }
        return null; // O lanzar una excepción
    }

    public static void setBombos(Bombo[] bombos) {
        Data.bombos = bombos;
    }

    public static Grupo[] getGrupos() {
        return grupos;
    }

    public static Grupo getGrupos(int indice) {
        if (grupos != null && indice >= 0 && indice < grupos.length) {
            return grupos[indice];
        }
        return null; // O lanzar una excepción
    }

    public static void setGrupos(Grupo[] grupos) {
        Data.grupos = grupos;
    }

        
}
