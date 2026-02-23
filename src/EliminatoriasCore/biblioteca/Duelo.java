package EliminatoriasCore.biblioteca;

import Global.Equipo;

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
    

}
