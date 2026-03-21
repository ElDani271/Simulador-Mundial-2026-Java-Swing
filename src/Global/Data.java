package Global;

import java.util.ArrayList;
import java.util.List;

import FaseGruposCore.biblioteca.PantallaDelGrupo;

public class Data {
    private static ArrayList<Equipo> terceros = new ArrayList<>();
    private static ArrayList<ArrayList<Duelo>> calendarioList = new ArrayList<>();

    private static List<Duelo> duelosElimDirect = new ArrayList<>();

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
                if (e.getNombre().equals("México")) {
                    e.setRanking(15);
                } else if (e.getNombre().equals("USA")) {
                    e.setRanking(14);
                } else if (e.getNombre().equals("Canadá")) {
                    e.setRanking(27);
                } else if (e.getNombre().substring(0,4).equals("UEFA")) {
                    e.setRanking(45);
                } else if (e.getNombre().substring(0,4).equals("FIFA")) {
                    e.setRanking(88);
                }
                grupos[i].getEquipoList().add(e);
                e.setGrupoOriginal(i);
            }
        }
        for (Grupo grupo : grupos) {
            grupo.generarPartidos();
            grupo.pantalla = new PantallaDelGrupo(grupo);
        }
        for (int i = 0; i < 32; i++) {
            duelosElimDirect.add(new Duelo());
        }
        for (Grupo g : grupos) {
            terceros.add(g.getEquipoList().get(2));
        }
    }
    
    public static Grupo[] getGrupos() {
        return grupos;
    }
    public static void setGrupos(Grupo[] grupos) {
        Data.grupos = grupos;
    }
    public static ArrayList<Equipo> getTerceros() {
        return terceros;
    }
    public static void setTerceros(ArrayList<Equipo> terceros) {
        Data.terceros = terceros;
    }
    public static ArrayList<ArrayList<Duelo>> getCalendarioList() {
        return calendarioList;
    }
    public static void setCalendarioList(ArrayList<ArrayList<Duelo>> calendarioList) {
        Data.calendarioList = calendarioList;
    }

    

    public static List<Duelo> getDuelosElimDirect() {
        return duelosElimDirect;
    }

    public static void setDuelosElimDirect(ArrayList<Duelo> duelosElimDirect) {
        Data.duelosElimDirect = duelosElimDirect;
    }
}
