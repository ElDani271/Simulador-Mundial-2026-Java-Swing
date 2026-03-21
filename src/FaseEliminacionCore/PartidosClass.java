package FaseEliminacionCore;

import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

import Global.Data;
import Global.Duelo;
import Global.Equipo;
import Global.Grupo;

public class PartidosClass {
    public static void asignarDuelosElimDirect(Grupo[] grupos, List<Duelo> duelosElimDirect) {
        Equipo eqAux = new Equipo("3er eq");
        // 1: 1E vs 3ABCDF
        duelosElimDirect.get(0).setlocal(grupos[4].getEquipoList().get(0));
        duelosElimDirect.get(0).setvisitante(eqAux); //

        // 2: 1I vs 3CDFGH
        duelosElimDirect.get(1).setlocal(grupos[8].getEquipoList().get(0));
        duelosElimDirect.get(1).setvisitante(eqAux); //

        // 3: 2A vs 2B
        duelosElimDirect.get(2).setlocal(grupos[0].getEquipoList().get(1));
        duelosElimDirect.get(2).setvisitante(grupos[1].getEquipoList().get(1));

        // 4: 1F vs 2C
        duelosElimDirect.get(3).setlocal(grupos[5].getEquipoList().get(0));
        duelosElimDirect.get(3).setvisitante(grupos[2].getEquipoList().get(1));

        // 5: 2K vs 2L
        duelosElimDirect.get(4).setlocal(grupos[10].getEquipoList().get(1));
        duelosElimDirect.get(4).setvisitante(grupos[11].getEquipoList().get(1));

        // 6: 1H vs 2J
        duelosElimDirect.get(5).setlocal(grupos[7].getEquipoList().get(0));
        duelosElimDirect.get(5).setvisitante(grupos[9].getEquipoList().get(1));

        // 7: 1D vs 3BEFIJ
        duelosElimDirect.get(6).setlocal(grupos[3].getEquipoList().get(0));
        duelosElimDirect.get(6).setvisitante(eqAux); //

        // 8: 1G vs 3AEHIJ
        duelosElimDirect.get(7).setlocal(grupos[6].getEquipoList().get(0));
        duelosElimDirect.get(7).setvisitante(eqAux); //

        // 9: 1C vs 2F
        duelosElimDirect.get(8).setlocal(grupos[2].getEquipoList().get(0));
        duelosElimDirect.get(8).setvisitante(grupos[5].getEquipoList().get(1));

        // 10: 2E vs 2I
        duelosElimDirect.get(9).setlocal(grupos[4].getEquipoList().get(1));
        duelosElimDirect.get(9).setvisitante(grupos[8].getEquipoList().get(1));

        // Partido 11: Local 1A (G:0) vs 3CEFHI (Índices: 2, 4, 5, 7, 8)
        duelosElimDirect.get(10).setlocal(grupos[0].getEquipoList().get(0));
        duelosElimDirect.get(10).setvisitante(eqAux); //

        // 12: 1L vs 3EHIJK
        duelosElimDirect.get(11).setlocal(grupos[11].getEquipoList().get(0));
        duelosElimDirect.get(11).setvisitante(eqAux); //

        // 13: 1J vs 2H
        duelosElimDirect.get(12).setlocal(grupos[9].getEquipoList().get(0));
        duelosElimDirect.get(12).setvisitante(grupos[7].getEquipoList().get(1));

        // 14: 2D vs 2G
        duelosElimDirect.get(13).setlocal(grupos[3].getEquipoList().get(1));
        duelosElimDirect.get(13).setvisitante(grupos[6].getEquipoList().get(1));

        // 15: 1B vs 3EFGIJ
        duelosElimDirect.get(14).setlocal(grupos[1].getEquipoList().get(0));
        duelosElimDirect.get(14).setvisitante(eqAux); //

        // Partido 16: Local 1K (G:10) vs 3DEIJL (Índices: 3, 4, 8, 9, 11)
        duelosElimDirect.get(15).setlocal(grupos[10].getEquipoList().get(0));
        duelosElimDirect.get(15).setvisitante(eqAux); //

        //SIGUIENTES RONDAS
        /*try {
            for (int i = 0; i < duelosElimDirect.size(); i++) {
                int posSigDuelo = -1;
                if (i % 2 == 0) {
                    posSigDuelo = 0;
                } else {
                    posSigDuelo = 1;
                }
                Data.getDuelosElimDirect().get(i).asignarGanadorADuelo(Data.getDuelosElimDirect().get(i).getgLoc(), Data.getDuelosElimDirect().get(i).getgVis(), Data.getDuelosElimDirect().get(ElimDirectInterfaz.indexSigDuelo(i, 32)), posSigDuelo, ElimDirectInterfaz.indexSigDuelo(i, 32));
            }
        } catch (NullPointerException e) {
            // TODO: handle exception
        }*/
        
        asignarTerceros(grupos, duelosElimDirect);
    }

    public static void asignarTerceros(Grupo[] grupos, List<Duelo> duelosElimDirect) {
        ArrayList<Duelo> partidosEsperando = new ArrayList<>();
            partidosEsperando.add(duelosElimDirect.get(0));// Partido 1: Local 1E (4) vs 3ABCDF (0,1,2,3,5)
            partidosEsperando.add(duelosElimDirect.get(1));// Partido 2: Local 1I (8) vs 3CDFGH (2,3,5,6,7)
            partidosEsperando.add(duelosElimDirect.get(6));// Partido 7: Local 1D (3) vs 3BEFIJ (1,4,5,8,9)
            partidosEsperando.add(duelosElimDirect.get(7));// Partido 8: Local 1G (6) vs 3AEHIJ (0,4,7,8,9)
            partidosEsperando.add(duelosElimDirect.get(10));// Partido 11: Local 1A (0) vs 3CEFHI (2,4,5,7,8)
            partidosEsperando.add(duelosElimDirect.get(11));// Partido 12: Local 1L (11) vs 3EHIJK (4,7,8,9,10)
            partidosEsperando.add(duelosElimDirect.get(14));// Partido 15: Local 1B (1) vs 3EFGIJ (4,5,6,8,9)
            partidosEsperando.add(duelosElimDirect.get(15));// Partido 16: Local 1K (10) vs 3DEIJL (3,4,8,9,11)

        List<Equipo> mejores8 = Data.getTerceros().subList(0, 8);

        List<Equipo> poolTerceros = new ArrayList<>(mejores8);

        // 3. Proceso de descarte
        try {
            for (Duelo dl : partidosEsperando) {
            boolean comprobarRival = false;
            for (int i = 0; i < poolTerceros.size(); i++) {
                Equipo candidato = poolTerceros.get(i);
                comprobarRival = comprobarTercero(dl.getlocal().getGrupoOriginal(), candidato.getGrupoOriginal());
                // REGLA DE ORO: Un tercero no puede jugar contra el líder de su propio grupo
                if (comprobarRival) {
                    // Asignar al partido
                    dl.setvisitante(candidato);
                    // Quitar del pool para que no se repita
                    poolTerceros.remove(i);
                    break; // Pasar al siguiente partido
                }
                // Si es el mismo grupo, el 'for' continúa al siguiente candidato (descarte)
            } 
            if (!comprobarRival) {
                System.out.println("Error al asignar un equipo para jugar contra " + dl.getlocal().getNombre());
                //backtracking
                for (Equipo equipoSinAsignar : poolTerceros) {
                    for (Duelo dueloYaAsignado : partidosEsperando) {
                        if (dueloYaAsignado.getlocal() != null && !dueloYaAsignado.getvisitante().getNombre().equals("Mejor 3er")) {
                            comprobarRival = comprobarTercero(dueloYaAsignado.getlocal().getGrupoOriginal(), equipoSinAsignar.getGrupoOriginal());
                            if (comprobarRival) {
                                Equipo equipoAsignado = dueloYaAsignado.getvisitante();
                                comprobarRival = comprobarTercero(dl.getlocal().getGrupoOriginal(), equipoAsignado.getGrupoOriginal());
                                if (comprobarRival) {
                                    System.out.println(dueloYaAsignado.getlocal().getNombre() + " vs " + dueloYaAsignado.getvisitante().getNombre() + " <||> " + dl.getlocal().getNombre() + " vs " + dl.getvisitante().getNombre());
                                    // Intercambiar equipos
                                    dueloYaAsignado.setvisitante(equipoSinAsignar);
                                    // Asignar al partido
                                    dl.setvisitante(equipoAsignado);
                                    System.out.println(dueloYaAsignado.getlocal().getNombre() + " vs " + dueloYaAsignado.getvisitante().getNombre() + " <||> " + dl.getlocal().getNombre() + " vs " + dl.getvisitante().getNombre());
                                    break; // Pasar al siguiente partido
                                }
                            }
                        }
                    }
                    if (comprobarRival) {
                        // Quitar del pool para que no se repita
                        poolTerceros.remove(equipoSinAsignar);
                        break;
                    }
                    System.out.println("sin asignar: " + equipoSinAsignar.getNombre());
                }
            }
        }
        } catch (NullPointerException e) {
            JOptionPane.showMessageDialog(null, "no se asignó un equipo en eliminatoria");
        }
        
    }

    public static boolean comprobarTercero(int indexGrupoLocal, int indexGrupoVisitante){
        boolean puede = false;
        switch (indexGrupoLocal) {
            case 4: // Partido 1: Local 1E vs 3ABCDF
                if (indexGrupoVisitante == 0 || indexGrupoVisitante == 1 || indexGrupoVisitante == 2 || 
                    indexGrupoVisitante == 3 || indexGrupoVisitante == 5) {
                    puede = true;
                }
                break;
            case 8: // Partido 2: Local 1I vs 3CDFGH
                if (indexGrupoVisitante == 2 || indexGrupoVisitante == 3 || indexGrupoVisitante == 5 || 
                    indexGrupoVisitante == 6 || indexGrupoVisitante == 7) {
                    puede = true;
                }
                break;
            case 3: // Partido 7: Local 1D vs 3BEFIJ
                if (indexGrupoVisitante == 1 || indexGrupoVisitante == 4 || indexGrupoVisitante == 5 || 
                    indexGrupoVisitante == 8 || indexGrupoVisitante == 9) {
                    puede = true;
                }
                break;
            case 6: // Partido 8: Local 1G vs 3AEHIJ
                if (indexGrupoVisitante == 0 || indexGrupoVisitante == 4 || indexGrupoVisitante == 7 || 
                    indexGrupoVisitante == 8 || indexGrupoVisitante == 9) {
                    puede = true;
                }
                break;
            case 0: // Partido 11: Local 1A vs 3CEFHI
                if (indexGrupoVisitante == 2 || indexGrupoVisitante == 4 || indexGrupoVisitante == 5 || 
                    indexGrupoVisitante == 7 || indexGrupoVisitante == 8) {
                    puede = true;
                }
                break;
            case 11: // Partido 12: Local 1L vs 3EHIJK
                if (indexGrupoVisitante == 4 || indexGrupoVisitante == 7 || indexGrupoVisitante == 8 || 
                    indexGrupoVisitante == 9 || indexGrupoVisitante == 10) {
                    puede = true;
                }
                break;
            case 1: // Partido 15: Local 1B vs 3EFGIJ
                if (indexGrupoVisitante == 4 || indexGrupoVisitante == 5 || indexGrupoVisitante == 6 || 
                    indexGrupoVisitante == 8 || indexGrupoVisitante == 9) {
                    puede = true;
                }
                break;
            case 10: // Partido 16: Local 1K vs 3DEIJL
                if (indexGrupoVisitante == 3 || indexGrupoVisitante == 4 || indexGrupoVisitante == 8 || 
                    indexGrupoVisitante == 9 || indexGrupoVisitante == 11) {
                    puede = true;
                }
                break;
            default:
                puede = false;
                break;
        }
        return puede;
    }
}
