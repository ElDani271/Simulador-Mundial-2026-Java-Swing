package FaseSorteoCore;
import java.util.Collections;

import FaseGruposCore.MainEliminatorias;
import FaseSorteoCore.biblioteca.DataSorteo;
import FaseSorteoCore.biblioteca.Inicializar;
import Global.Grupo;

public class App {
    private static Grupo grupos[] = DataSorteo.getGrupos();
    
    public static void main(String[] args) throws Exception {
        Inicializar.inicio();
    }

    public static void seguir(){
        ordenarGruposFinal(grupos);
        MainEliminatorias.importarEqs(grupos);
    }

    public static void ordenarGruposFinal(Grupo grupos[]){
        for (Grupo grupo : grupos) {
            if (grupo.getNombre().equals("A") || grupo.getNombre().equals("D") || grupo.getNombre().equals("G") || grupo.getNombre().equals("J")) {
                Collections.swap(grupo.getEquipoList(), 2, 1);
            } else if (grupo.getNombre().equals("B") || grupo.getNombre().equals("E") || grupo.getNombre().equals("H") || grupo.getNombre().equals("K")) {
                Collections.swap(grupo.getEquipoList(), 1, 3);
            } else if (grupo.getNombre().equals("C") || grupo.getNombre().equals("F") || grupo.getNombre().equals("I") || grupo.getNombre().equals("L")) {
                Collections.swap(grupo.getEquipoList(), 2, 3);
            }
        }
    }
}
