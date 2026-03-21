package Global;

import java.util.ArrayList;
import java.util.Collections;

import FaseGruposCore.biblioteca.PantallaDelGrupo;

public class Grupo {
    private String nombre;
    private ArrayList<Equipo> equipoList = new ArrayList<>();
    private ArrayList<ArrayList<Duelo>> calendarioList = new ArrayList<>();
    public PantallaDelGrupo pantalla = new PantallaDelGrupo();
    

    public ArrayList<ArrayList<Duelo>> getCalendarioList() {
        return calendarioList;
    }
    public void setCalendarioList(ArrayList<ArrayList<Duelo>> calendarioList) {
        this.calendarioList = calendarioList;
    }
    public String getNombre() {
        return nombre;
    }
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    public ArrayList<Equipo> getEquipoList() {
        return equipoList;
    }
    public void setEquipoList(ArrayList<Equipo> equipoList) {
        this.equipoList = equipoList;
    }
    public Grupo(String nombre, ArrayList<Equipo> equipoList) {
        this.nombre = nombre;
        this.equipoList = equipoList;
    }

    public Grupo() {
        //TODO Auto-generated constructor stub
    }
    public void generarPartidos() {
            ArrayList<Duelo> fecha1 = new ArrayList<>();
            fecha1.add(new Duelo(this.getEquipoList().get(0), this.getEquipoList().get(1)));
            fecha1.add(new Duelo(this.getEquipoList().get(2), this.getEquipoList().get(3)));
            
            ArrayList<Duelo> fecha2 = new ArrayList<>();
            fecha2.add(new Duelo(this.getEquipoList().get(0), this.getEquipoList().get(2)));
            fecha2.add(new Duelo(this.getEquipoList().get(1), this.getEquipoList().get(3)));

            ArrayList<Duelo> fecha3 = new ArrayList<>();
            fecha3.add(new Duelo(this.getEquipoList().get(3), this.getEquipoList().get(0)));
            fecha3.add(new Duelo(this.getEquipoList().get(1), this.getEquipoList().get(2)));
            
            this.getCalendarioList().add(fecha1);
            this.getCalendarioList().add(fecha2);
            this.getCalendarioList().add(fecha3);
    }

    public static void ordenarEquiposBurbuja(ArrayList<Equipo> equipos) {
        int n = equipos.size();
        boolean intercambiado;
        
        for (int i = 0; i < n - 1; i++) {
            intercambiado = false;
            
            for (int j = 0; j < n - i - 1; j++) {
                Equipo equipo1 = equipos.get(j);
                Equipo equipo2 = equipos.get(j + 1);
                
                // Comparar por puntos (descendente)
                if (equipo1.getPuntos() < equipo2.getPuntos()) {
                    Collections.swap(equipos, j, j + 1);
                    intercambiado = true;
                } 
                // Si hay empate en puntos
                else if (equipo1.getPuntos() == equipo2.getPuntos()) {
                    
                    // Comparar por diferencia de goles (descendente)
                    if (equipo1.getDifGol() < equipo2.getDifGol()) {
                        Collections.swap(equipos, j, j + 1);
                        intercambiado = true;
                    }
                    // Si hay empate en diferencia de goles
                    else if (equipo1.getDifGol() == equipo2.getDifGol()) {
                        
                        // Comparar por goles a favor (descendente)
                        if (equipo1.getGolesFavor() < equipo2.getGolesFavor()) {
                            Collections.swap(equipos, j, j + 1);
                            intercambiado = true;
                        }
                        
                    }
                }
            }
            
            // Si no hubo intercambios, el array ya está ordenado
            if (!intercambiado) {
                break;
            }
        }
    }

    @Override
    public ArrayList<Equipo> clone(){
        ArrayList<Equipo> listCopy = new ArrayList<>();
        for (int i = 0; i < this.equipoList.size(); i++) {
            listCopy.add(new Equipo(this.equipoList.get(i).getNombre() + (" (copy)"), 0, this.equipoList.get(i).getConfederacion()));
        }
        return listCopy;
    }
    
    
}
