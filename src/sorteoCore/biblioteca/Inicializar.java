package sorteoCore.biblioteca;

import java.util.Collections;

import Global.Equipo;
import Global.Grupo;

public class Inicializar {
    
    public static void ordenarGuposFinal(Grupo grupos[]){
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
    
    public static void inicio() {
        
            // Anfitriones
            Data.getEquipoList().add(new Equipo("USA", 0, "CONCACAF")); //Rank 0 por anfitrion
            Data.getEquipoList().add(new Equipo("México", 0, "CONCACAF"));
            Data.getEquipoList().add(new Equipo("Canadá", 0, "CONCACAF"));

            // Oceanía (OFC)
            Data.getEquipoList().add(new Equipo("Nueva Zelanda", 86, "OFC"));
            
            // Sudamérica (CONMEBOL)
            Data.getEquipoList().add(new Equipo("Argentina", 2, "CONMEBOL"));
            Data.getEquipoList().add(new Equipo("Ecuador", 23, "CONMEBOL"));
            Data.getEquipoList().add(new Equipo("Colombia", 13, "CONMEBOL"));
            Data.getEquipoList().add(new Equipo("Uruguay", 16, "CONMEBOL"));
            Data.getEquipoList().add(new Equipo("Brasil", 5, "CONMEBOL"));
            Data.getEquipoList().add(new Equipo("Paraguay", 39, "CONMEBOL"));
            
            // África (CAF)
            Data.getEquipoList().add(new Equipo("Marruecos", 11, "CAF"));
            Data.getEquipoList().add(new Equipo("Senegal", 19, "CAF"));
            Data.getEquipoList().add(new Equipo("Túnez", 40, "CAF"));
            Data.getEquipoList().add(new Equipo("Egipto", 34, "CAF"));
            Data.getEquipoList().add(new Equipo("Argelia", 35, "CAF"));
            Data.getEquipoList().add(new Equipo("Ghana", 72, "CAF"));
            Data.getEquipoList().add(new Equipo("Cabo Verde", 68, "CAF"));
            Data.getEquipoList().add(new Equipo("Sudáfrica", 61, "CAF"));
            Data.getEquipoList().add(new Equipo("Costa de Marfil", 42, "CAF"));
            
            // Asia (AFC)
            Data.getEquipoList().add(new Equipo("Japón", 18, "AFC"));
            Data.getEquipoList().add(new Equipo("Irán", 20, "AFC"));
            Data.getEquipoList().add(new Equipo("Corea del Sur", 22, "AFC"));
            Data.getEquipoList().add(new Equipo("Australia", 26, "AFC"));
            Data.getEquipoList().add(new Equipo("Qatar", 51, "AFC"));
            Data.getEquipoList().add(new Equipo("Arabia Saudita", 60, "AFC"));
            Data.getEquipoList().add(new Equipo("Uzbekistán", 50, "AFC"));
            Data.getEquipoList().add(new Equipo("Jordania", 66, "AFC"));
            
            // Europa (UEFA)
            Data.getEquipoList().add(new Equipo("España", 1, "UEFA"));
            Data.getEquipoList().add(new Equipo("Alemania", 9, "UEFA"));
            Data.getEquipoList().add(new Equipo("Francia", 3, "UEFA"));
            Data.getEquipoList().add(new Equipo("Bélgica", 8, "UEFA"));
            Data.getEquipoList().add(new Equipo("Inglaterra", 4, "UEFA"));
            Data.getEquipoList().add(new Equipo("Portugal", 6, "UEFA"));
            Data.getEquipoList().add(new Equipo("Países Bajos", 7, "UEFA"));
            Data.getEquipoList().add(new Equipo("Escocia", 36, "UEFA"));
            Data.getEquipoList().add(new Equipo("Suiza", 17, "UEFA"));
            Data.getEquipoList().add(new Equipo("Austria", 24, "UEFA"));
            Data.getEquipoList().add(new Equipo("Noruega", 29, "UEFA"));
            Data.getEquipoList().add(new Equipo("Croacia", 10, "UEFA"));
            
            // Norteamérica (ACAF) - Eliminatoria
            Data.getEquipoList().add(new Equipo("Panamá", 30, "CONCACAF"));
            Data.getEquipoList().add(new Equipo("Curazao", 82, "CONCACAF"));
            Data.getEquipoList().add(new Equipo("Haiti", 84, "CONCACAF"));

            // 
            Data.getEquipoList().add(new Equipo("UEFA 1", 99, "UEFA", "/sorteoCore/images/banderas/uefa.png"));
            Data.getEquipoList().add(new Equipo("UEFA 2", 99, "UEFA", "/sorteoCore/images/banderas/uefa.png"));
            Data.getEquipoList().add(new Equipo("UEFA 3", 99, "UEFA", "/sorteoCore/images/banderas/uefa.png"));
            Data.getEquipoList().add(new Equipo("UEFA 4", 99, "UEFA", "/sorteoCore/images/banderas/uefa.png"));
            Data.getEquipoList().add(new Equipo("FIFA 1", 99, "CAF", "/sorteoCore/images/banderas/fifa.png")); //CAF/OFC/CONCACAF
            Data.getEquipoList().add(new Equipo("FIFA 2", 99, "CONMEBOL", "/sorteoCore/images/banderas/fifa.png")); //AFC/CONMEBOL/CONCACAF


            Data.getEquipoList().sort((e1, e2) -> Integer.compare(e1.getRanking(), e2.getRanking()));

        //CREAR BOMBOS
        for (int j = 0; j < Data.getBombos().length; j++) {
            int start = j * 12;
            int end = Math.min(start + 12, Data.getEquipoList().size());
            Data.getBombos(j).getEquipoList().addAll(Data.getEquipoList().subList(start, end));
        }
        for (Bombo bombo : Data.getBombos()) {
            for (Equipo eq : bombo.getEquipoList()) {
                eq.setBombo(Integer.parseInt(bombo.getNombre()));
            }
        } 

        //CREAR GRUPOS
        
        //--------------

        Interfaz.mainFrame(Data.getGrupos(),Data.getBombos());

    }

    public static void anadirAnfitriones(){
        for (Equipo eq : Data.getBombos(0).getEquipoList()) {
            if (eq.getNombre().equals("México")) {
                Logica.anadirAGrupo(Data.getGrupos(0), Data.getBombos(0).getEquipoList().get(Data.getBombos(0).getEquipoList().indexOf(eq)), Data.getBombos(0).getEquipoList());
                break;
            }
        }
        for (Equipo eq : Data.getBombos(0).getEquipoList()) {
            if (eq.getNombre().equals("USA")) {
                Logica.anadirAGrupo(Data.getGrupos(3), Data.getBombos(0).getEquipoList().get(Data.getBombos(0).getEquipoList().indexOf(eq)), Data.getBombos(0).getEquipoList());
                break;
            }
        }
        for (Equipo eq : Data.getBombos(0).getEquipoList()) {
            if (eq.getNombre().equals("Canadá")) {
                Logica.anadirAGrupo(Data.getGrupos(1), Data.getBombos(0).getEquipoList().get(Data.getBombos(0).getEquipoList().indexOf(eq)), Data.getBombos(0).getEquipoList());
                break;
            }
        }
    }

}



