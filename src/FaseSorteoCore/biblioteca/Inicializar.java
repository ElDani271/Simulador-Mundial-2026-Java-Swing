package FaseSorteoCore.biblioteca;

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
            DataSorteo.getEquipoList().add(new Equipo("USA", 0, "CONCACAF")); //Rank 0 por anfitrion
            DataSorteo.getEquipoList().add(new Equipo("México", 0, "CONCACAF"));
            DataSorteo.getEquipoList().add(new Equipo("Canadá", 0, "CONCACAF"));

            // Oceanía (OFC)
            DataSorteo.getEquipoList().add(new Equipo("Nueva Zelanda", 86, "OFC"));
            
            // Sudamérica (CONMEBOL)
            DataSorteo.getEquipoList().add(new Equipo("Argentina", 2, "CONMEBOL"));
            DataSorteo.getEquipoList().add(new Equipo("Ecuador", 23, "CONMEBOL"));
            DataSorteo.getEquipoList().add(new Equipo("Colombia", 13, "CONMEBOL"));
            DataSorteo.getEquipoList().add(new Equipo("Uruguay", 16, "CONMEBOL"));
            DataSorteo.getEquipoList().add(new Equipo("Brasil", 5, "CONMEBOL"));
            DataSorteo.getEquipoList().add(new Equipo("Paraguay", 39, "CONMEBOL"));
            
            // África (CAF)
            DataSorteo.getEquipoList().add(new Equipo("Marruecos", 11, "CAF"));
            DataSorteo.getEquipoList().add(new Equipo("Senegal", 19, "CAF"));
            DataSorteo.getEquipoList().add(new Equipo("Túnez", 40, "CAF"));
            DataSorteo.getEquipoList().add(new Equipo("Egipto", 34, "CAF"));
            DataSorteo.getEquipoList().add(new Equipo("Argelia", 35, "CAF"));
            DataSorteo.getEquipoList().add(new Equipo("Ghana", 72, "CAF"));
            DataSorteo.getEquipoList().add(new Equipo("Cabo Verde", 68, "CAF"));
            DataSorteo.getEquipoList().add(new Equipo("Sudáfrica", 61, "CAF"));
            DataSorteo.getEquipoList().add(new Equipo("Costa de Marfil", 42, "CAF"));
            
            // Asia (AFC)
            DataSorteo.getEquipoList().add(new Equipo("Japón", 18, "AFC"));
            DataSorteo.getEquipoList().add(new Equipo("Irán", 20, "AFC"));
            DataSorteo.getEquipoList().add(new Equipo("Corea del Sur", 22, "AFC"));
            DataSorteo.getEquipoList().add(new Equipo("Australia", 26, "AFC"));
            DataSorteo.getEquipoList().add(new Equipo("Qatar", 51, "AFC"));
            DataSorteo.getEquipoList().add(new Equipo("Arabia Saudita", 60, "AFC"));
            DataSorteo.getEquipoList().add(new Equipo("Uzbekistán", 50, "AFC"));
            DataSorteo.getEquipoList().add(new Equipo("Jordania", 66, "AFC"));
            
            // Europa (UEFA)
            DataSorteo.getEquipoList().add(new Equipo("España", 1, "UEFA"));
            DataSorteo.getEquipoList().add(new Equipo("Alemania", 9, "UEFA"));
            DataSorteo.getEquipoList().add(new Equipo("Francia", 3, "UEFA"));
            DataSorteo.getEquipoList().add(new Equipo("Bélgica", 8, "UEFA"));
            DataSorteo.getEquipoList().add(new Equipo("Inglaterra", 4, "UEFA"));
            DataSorteo.getEquipoList().add(new Equipo("Portugal", 6, "UEFA"));
            DataSorteo.getEquipoList().add(new Equipo("Países Bajos", 7, "UEFA"));
            DataSorteo.getEquipoList().add(new Equipo("Escocia", 36, "UEFA"));
            DataSorteo.getEquipoList().add(new Equipo("Suiza", 17, "UEFA"));
            DataSorteo.getEquipoList().add(new Equipo("Austria", 24, "UEFA"));
            DataSorteo.getEquipoList().add(new Equipo("Noruega", 29, "UEFA"));
            DataSorteo.getEquipoList().add(new Equipo("Croacia", 10, "UEFA"));
            
            // Norteamérica (ACAF) - Eliminatoria
            DataSorteo.getEquipoList().add(new Equipo("Panamá", 30, "CONCACAF"));
            DataSorteo.getEquipoList().add(new Equipo("Curazao", 82, "CONCACAF"));
            DataSorteo.getEquipoList().add(new Equipo("Haiti", 84, "CONCACAF"));

            // 
            DataSorteo.getEquipoList().add(new Equipo("UEFA 1", 99, "UEFA", "/FaseSorteoCore/images/banderas/uefa.png"));
            DataSorteo.getEquipoList().add(new Equipo("UEFA 2", 99, "UEFA", "/FaseSorteoCore/images/banderas/uefa.png"));
            DataSorteo.getEquipoList().add(new Equipo("UEFA 3", 99, "UEFA", "/FaseSorteoCore/images/banderas/uefa.png"));
            DataSorteo.getEquipoList().add(new Equipo("UEFA 4", 99, "UEFA", "/FaseSorteoCore/images/banderas/uefa.png"));
            DataSorteo.getEquipoList().add(new Equipo("FIFA 1", 99, "CAF", "/FaseSorteoCore/images/banderas/fifa.png")); //CAF/OFC/CONCACAF
            DataSorteo.getEquipoList().add(new Equipo("FIFA 2", 99, "CONMEBOL", "/FaseSorteoCore/images/banderas/fifa.png")); //AFC/CONMEBOL/CONCACAF


            DataSorteo.getEquipoList().sort((e1, e2) -> Integer.compare(e1.getRanking(), e2.getRanking()));

        //CREAR BOMBOS
        for (int j = 0; j < DataSorteo.getBombos().length; j++) {
            int start = j * 12;
            int end = Math.min(start + 12, DataSorteo.getEquipoList().size());
            DataSorteo.getBombos(j).getEquipoList().addAll(DataSorteo.getEquipoList().subList(start, end));
        }
        for (Bombo bombo : DataSorteo.getBombos()) {
            for (Equipo eq : bombo.getEquipoList()) {
                eq.setBombo(Integer.parseInt(bombo.getNombre()));
            }
        } 

        //CREAR GRUPOS
        
        //--------------

        Interfaz.mainFrame(DataSorteo.getGrupos(),DataSorteo.getBombos());

    }

    public static void anadirAnfitriones(){
        for (Equipo eq : DataSorteo.getBombos(0).getEquipoList()) {
            if (eq.getNombre().equals("México")) {
                Logica.anadirAGrupo(DataSorteo.getGrupos(0), DataSorteo.getBombos(0).getEquipoList().get(DataSorteo.getBombos(0).getEquipoList().indexOf(eq)), DataSorteo.getBombos(0).getEquipoList());
                break;
            }
        }
        for (Equipo eq : DataSorteo.getBombos(0).getEquipoList()) {
            if (eq.getNombre().equals("USA")) {
                Logica.anadirAGrupo(DataSorteo.getGrupos(3), DataSorteo.getBombos(0).getEquipoList().get(DataSorteo.getBombos(0).getEquipoList().indexOf(eq)), DataSorteo.getBombos(0).getEquipoList());
                break;
            }
        }
        for (Equipo eq : DataSorteo.getBombos(0).getEquipoList()) {
            if (eq.getNombre().equals("Canadá")) {
                Logica.anadirAGrupo(DataSorteo.getGrupos(1), DataSorteo.getBombos(0).getEquipoList().get(DataSorteo.getBombos(0).getEquipoList().indexOf(eq)), DataSorteo.getBombos(0).getEquipoList());
                break;
            }
        }
    }

}



