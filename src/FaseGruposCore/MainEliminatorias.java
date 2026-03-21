package FaseGruposCore;


import java.util.ArrayList;

import FaseGruposCore.biblioteca.*;
import Global.Data;
import Global.Equipo;
import Global.Grupo;

public class MainEliminatorias {
    
    public static void main(String[] args) {

        eqsOriginales();    

        InterfazGrupos.inicializarInterfazGrupos();
        InterfazGrupos.frame1.setVisible(true);
    }

    public static void eqsOriginales() {
        Grupo[] gruposN = {
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
        // GRUPO A
        gruposN[0].getEquipoList().add(new Equipo("México", 0, 15));
        gruposN[0].getEquipoList().add(new Equipo("Sudáfrica", 0, 61));
        gruposN[0].getEquipoList().add(new Equipo("Corea del Sur", 0, 22));
        gruposN[0].getEquipoList().add(new Equipo("UEFA 4", 0, 99)); // Dinamarca/Macedonia/Rep.Checa/Irlanda

        // GRUPO B
        gruposN[1].getEquipoList().add(new Equipo("Canadá", 0, 27));
        gruposN[1].getEquipoList().add(new Equipo("UEFA 1", 0, 99)); // Italia/Irlanda Norte/Gales/Bosnia
        gruposN[1].getEquipoList().add(new Equipo("Qatar", 0, 51));
        gruposN[1].getEquipoList().add(new Equipo("Suiza", 0, 17));

        // GRUPO C
        gruposN[2].getEquipoList().add(new Equipo("Brasil", 0, 5));
        gruposN[2].getEquipoList().add(new Equipo("Marruecos", 0, 11));
        gruposN[2].getEquipoList().add(new Equipo("Haiti", 0, 84));
        gruposN[2].getEquipoList().add(new Equipo("Escocia", 0, 36));

        // GRUPO D
        gruposN[3].getEquipoList().add(new Equipo("USA", 0, 14));
        gruposN[3].getEquipoList().add(new Equipo("Paraguay", 0, 39));
        gruposN[3].getEquipoList().add(new Equipo("Australia", 0, 26));
        gruposN[3].getEquipoList().add(new Equipo("UEFA 3", 0, 99)); // Turquía/Rumania/Eslovaquia/Kosovo

        // GRUPO E
        gruposN[4].getEquipoList().add(new Equipo("Alemania", 0, 9));
        gruposN[4].getEquipoList().add(new Equipo("Curazao", 0, 82));
        gruposN[4].getEquipoList().add(new Equipo("Costa de Marfil", 0, 42));
        gruposN[4].getEquipoList().add(new Equipo("Ecuador", 0, 23));

        // GRUPO F
        gruposN[5].getEquipoList().add(new Equipo("Países Bajos", 0, 7));
        gruposN[5].getEquipoList().add(new Equipo("Japón", 0, 18));
        gruposN[5].getEquipoList().add(new Equipo("UEFA 2", 0, 99)); // Ucrania/Suecia/Polonia/Albania
        gruposN[5].getEquipoList().add(new Equipo("Túnez", 0, 40));

        // GRUPO G
        gruposN[6].getEquipoList().add(new Equipo("Bélgica", 0, 8));
        gruposN[6].getEquipoList().add(new Equipo("Egipto", 0, 34));
        gruposN[6].getEquipoList().add(new Equipo("Irán", 0, 20));
        gruposN[6].getEquipoList().add(new Equipo("Nueva Zelanda", 0, 86));

        // GRUPO H
        gruposN[7].getEquipoList().add(new Equipo("España", 0, 1));
        gruposN[7].getEquipoList().add(new Equipo("Cabo Verde", 0, 68));
        gruposN[7].getEquipoList().add(new Equipo("Arabia Saudita", 0, 60));
        gruposN[7].getEquipoList().add(new Equipo("Uruguay", 0, 16));

        // GRUPO I
        gruposN[8].getEquipoList().add(new Equipo("Francia", 0, 3));
        gruposN[8].getEquipoList().add(new Equipo("Senegal", 0, 19));
        gruposN[8].getEquipoList().add(new Equipo("FIFA 2", 0, 99)); // Bolivia/Surinam/Irak
        gruposN[8].getEquipoList().add(new Equipo("Noruega", 0, 29));

        // GRUPO J
        gruposN[9].getEquipoList().add(new Equipo("Argentina", 0, 2));
        gruposN[9].getEquipoList().add(new Equipo("Argelia", 0, 35));
        gruposN[9].getEquipoList().add(new Equipo("Austria", 0, 24));
        gruposN[9].getEquipoList().add(new Equipo("Jordania", 0, 66));

        // GRUPO K
        gruposN[10].getEquipoList().add(new Equipo("Portugal", 0, 6));
        gruposN[10].getEquipoList().add(new Equipo("FIFA 1", 0, 99)); // Jamaica/Nueva Caledonia/RD Congo
        gruposN[10].getEquipoList().add(new Equipo("Uzbekistán", 0, 50));
        gruposN[10].getEquipoList().add(new Equipo("Colombia", 0, 13));

        // GRUPO L
        gruposN[11].getEquipoList().add(new Equipo("Inglaterra", 0, 4));
        gruposN[11].getEquipoList().add(new Equipo("Croacia", 0, 10));
        gruposN[11].getEquipoList().add(new Equipo("Ghana", 0, 72));
        gruposN[11].getEquipoList().add(new Equipo("Panamá", 0, 30));

        Data.inicializarGrupos(gruposN);
        
    }

    public static void importarEqs(Grupo[] grs){

        Data.inicializarGrupos(grs);

        InterfazGrupos.inicializarInterfazGrupos();
        InterfazGrupos.frame1.setVisible(true);
    }

}
