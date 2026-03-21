package FaseSorteoCore.biblioteca;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JLabel;

import Global.Equipo;
import Global.Grupo;

public class Logica {

    public static Equipo randomEq(Bombo bombo){
        int rand = (int)(Math.random() * (bombo.getEquipoList().size())); //Se randomiza un equipo para sortearlo
        Equipo eq = bombo.getEquipoList().get(rand);
        return eq;
        
    }

    public static String asignarEquipo(Bombo bombo, Equipo eq, int numIteracion, Grupo[] grupos, JLabel lMessage){
        
        String grupo = "-";

        //for (Bombo bombo : Data.bombos) { //1. Para cada bombo
            boolean hayHueco = false;
            int cantHuecos = 0;

            for (int j = 0; j < grupos.length; j++) { //2. Se recorre una casilla en cada grupo
                boolean comprobacion = false;

                if (hayHueco) { //hay huecos?
                    // Contar huecos previos
                    cantHuecos = 0;
                    for (int k = 0; k < j; k++) {
                        if (grupos[k].getEquipoList().size() < (numIteracion + 1)) {
                            cantHuecos++;
                        }
                    }
                    if (cantHuecos == 0) {
                        hayHueco = false;
                    }
                    // Priorizar llenar huecos
                    for (int k = 0; k < j && !comprobacion && cantHuecos > 0; k++) {
                        comprobacion = comprobarAnadible(grupos[k], eq, numIteracion, lMessage);
                        if (comprobacion) {
                            anadirAGrupo(grupos[k], eq, bombo.getEquipoList());
                            cantHuecos--;
                            grupo = grupos[k].getNombre();
                            break;
                        }
                    }
                }
                
                if (!comprobacion) {
                    comprobacion = comprobarAnadible(grupos[j], eq, numIteracion, lMessage);
                    if (comprobacion) {
                        anadirAGrupo(grupos[j], eq, bombo.getEquipoList());
                        grupo = grupos[j].getNombre();
                    }
                    
                }

                if (!comprobacion) {
                    // Si no cabe en el grupo actual, buscar en los siguientes
                    for (int k = j + 1; k < grupos.length && !comprobacion; k++) {
                        comprobacion = comprobarAnadible(grupos[k], eq, numIteracion, lMessage);
                        if (comprobacion) { //evitar repetir el mismo equipo en más grupos
                            anadirAGrupo(grupos[k], eq, bombo.getEquipoList());
                            grupo = grupos[k].getNombre();
                            hayHueco = true;//el equipo actual dejó un hueco previo
                            break;
                        }
                    }
                }
                if (!comprobacion) {
                    Grupo grupoIntercambiado = intercambiarEqs(eq, grupos[j], Interfaz.nBombo, grupos, DataSorteo.getBombos(), lMessage);
                    if (grupoIntercambiado != null) {
                        comprobacion = true;
                        grupo = grupoIntercambiado.getNombre();
                        lMessage.setText(lMessage.getText() + ", se intercambió por un grupo anterior");
                    }
                }
                if (comprobacion) {
                    break;
                }
            }
            numIteracion++;

            return grupo;
        //}
    }

    public static boolean comprobarAnadible(Grupo grupo, Equipo eq, int iter, JLabel lMessage){ //añadir equipo al grupo
        boolean repechaje = false;
        if (eq.getNombre().equals("FIFA 1") || eq.getNombre().equals("FIFA 2")) {
            repechaje = true;
        }
        
        iter++;
        boolean anadible = true;
        if (grupo.getEquipoList().size()+1 <= iter) {//El grupo no excede la cantidad de equipos?
            int cantUEFA = contarEuropeos(grupo);//Cuántos europeos hay en ese grupo?
            if (iter == 1) { //Regla de cuadros diferentes
                anadible = comprobarCuadrosDiferentes(grupo, eq, lMessage);
                if (!anadible) {
                    return anadible;
                }
            } 
            if (iter == 4 && cantUEFA == 0 ) {//El grupo quedaria sin europeos?
                if (!eq.getConfederacion().equals("UEFA")) {//El equipo que se intenta añadir no es europeo?
                    lMessage.setText("Error en grupo "+ grupo.getNombre() +": Mínimo un europeo por grupo");
                    anadible = false;
                    return anadible;
                }
            } else if (cantUEFA >= 2) { //hay 2 o mas sel. uefa en ese grupo?
                if (eq.getConfederacion().equals("UEFA")) {
                    lMessage.setText("Error en grupo "+ grupo.getNombre() +": No puede haber más de dos europeos por grupo");
                    anadible = false;
                    return anadible;
                } 
            }
            if (repechaje) {//si es repechaje es porque está en bombo 4
                if (eq.getNombre().equals("FIFA 1")) {
                    for (Equipo eq1 : grupo.getEquipoList()) {
                        if (eq1.getConfederacion().equals("CAF") || eq1.getConfederacion().equals("OFC") || eq1.getConfederacion().equals("CONCACAF")) { // son de la misma confederacion?
                            if (!eq.getConfederacion().equals("UEFA")) {// la confederacion no es UEFA?
                                lMessage.setText("Error en grupo "+ grupo.getNombre() +": No puede haber dos selecciones de la misma confederacion");
                                anadible = false;
                                return anadible;
                            }
                        }
                    }
                } else if (eq.getNombre().equals("FIFA 2")) {
                    for (Equipo eq1 : grupo.getEquipoList()) {
                        if (eq1.getConfederacion().equals("AFC") || /*eq1.getConfederacion().equals("CONMEBOL") ||*/ eq1.getConfederacion().equals("CONCACAF")) { // son de la misma confederacion?
                            if (!eq.getConfederacion().equals("UEFA")) {// la confederacion no es UEFA?
                                lMessage.setText("Error en grupo "+ grupo.getNombre() +": No puede haber dos selecciones de la misma confederacion");
                                anadible = false;
                                return anadible;
                            }
                        }
                    }
                }
            } else {
                    /*grupo.getEquipoList().add(eq); //INTENTO DE EVITAR REPETIR FIFA 2 CON LA MISMA CONFED (FALLIDO)
                    int index = 0;
                    int[] contador = {0,0};
                    Grupo[] gruposCopia = new Grupo[Data.getGrupos().length];
                    for (int i = 0; i < gruposCopia.length; i++) {
                        gruposCopia[i] = new Grupo(Data.getGrupos()[i].getNombre() + "(copia)", Data.getGrupos()[i].clone());
                        if (Data.getGrupos()[i].getNombre() == grupo.getNombre()) {
                            index = i;
                        }
                    }
                    gruposCopia[index].getEquipoList().add(eq);
                    //comprobar si al terminar el bombo 3 los repechajes quedan con opcion
                    contador = contarGruposForRepechaje(gruposCopia, contador);
                    System.out.println("fifa 1 puede entrar a estos grupos: " + contador[0]);
                    System.out.println("fifa 2 puede entrar a estos grupos: " + contador[1]);
                    grupo.getEquipoList().remove(eq);
                    
                    if (contador[0] == 0) {
                        lMessage.setText("Error: FIFA 1 quedaría sin opciones");
                        JOptionPane.showMessageDialog(null, "Se evitó algo");
                        anadible = false;
                        return anadible;
                    } 
                    if (contador[1] == 0) {
                        lMessage.setText("Error: FIFA 2 quedaría sin opciones");
                        JOptionPane.showMessageDialog(null, "Se evitó algo");
                        anadible = false;
                        return anadible;
                    }*/
                for (Equipo eq1 : grupo.getEquipoList()) {
                        if (eq1.getConfederacion().equals(eq.getConfederacion())) { // son de la misma confederacion?
                            if (!eq.getConfederacion().equals("UEFA")) {// la confederacion no es UEFA?
                                lMessage.setText("Error en grupo "+ grupo.getNombre() +": No puede haber dos selecciones de la misma confederacion");
                                anadible = false;
                                return anadible;
                            }
                        }
                    }
                return anadible;
            }
            
        } else {
            anadible = false;
            return anadible;
        }
        return anadible;
    }

    public static void anadirAGrupo(Grupo grupo, Equipo eq, ArrayList<Equipo> bombo){ 
        grupo.getEquipoList().add(eq);
        bombo.remove(eq);
    }

    public static Grupo intercambiarEqs(Equipo eq1, Grupo grupo1, int iter, Grupo[] grupos, Bombo[] bombos, JLabel lMessage){ //si un equipo no cabe en ningun equipo, se tiene que intercambiar con uno apto
        
        // Encontrar el índice del grupo actual
        int indexGrupo1 = -1;
        for (int j = 0; j < grupos.length; j++) {
            if (grupos[j] == grupo1) {
                indexGrupo1 = j;
                break;
            }
        }
        
        // Iterar desde el grupo actual hacia atrás
        Grupo grupo2 = comprobarIntercambio(bombos, grupos, grupo1, eq1, iter, indexGrupo1, lMessage);


        /*if (grupo2 == null) {
            grupo2 = comprobarIntercambio(bombos, grupos, grupo1, eq1, iter, grupos.length);
        }*/
        
        return grupo2;
    }

    public static Grupo comprobarIntercambio(Bombo[] bombos, Grupo[] grupos, Grupo grupo1, Equipo eq1, int iter, int contarDesde, JLabel lMessage){
        for (int i = contarDesde - 1; i >= 0; i--) {//ContarDesde representa el grupo desde el que se busca hacia atrás
            Grupo grupo2 = grupos[i];
            
            // Solo revisar grupos anteriores y si tienen equipo en la iteración actual
            if (grupo2 != grupo1 && iter < grupo2.getEquipoList().size()) {
                Equipo eq2 = grupo2.getEquipoList().get(iter);
                
                // Eliminar temporalmente eq2 para hacer la comprobación
                grupo2.getEquipoList().remove(eq2);
                
                // Comprobar si eq2 puede entrar en grupo1 y eq1 puede entrar en grupo2
                if (comprobarAnadible(grupo1, eq2, iter, lMessage) && comprobarAnadible(grupo2, eq1, iter, lMessage)) {
                    // Realizar el intercambio
                    anadirAGrupo(grupo1, eq2, bombos[iter].getEquipoList());
                    anadirAGrupo(grupo2, eq1, bombos[iter].getEquipoList());
                    lMessage = new JLabel("Se intercambió " + eq2.getNombre() + " del grupo " + grupo2.getNombre() + " por " + eq1.getNombre() + " en el grupo " + grupo1.getNombre());
                    return grupo2;
                } else {
                    // Si no se puede intercambiar, devolver eq2 al grupo2
                    grupo2.getEquipoList().add(eq2);
                }
            }
        }
        return null;
    }

    public static boolean comprobarCuadrosDiferentes(Grupo grupo, Equipo eq, JLabel lMessage){
        //asi se dividen los 4 cuadros de la tabla
        List<String> Q1 = List.of("E","F","I");
        List<String> Q2 = List.of("D","G","H");
        List<String> Q3 = List.of("A","C","L");
        List<String> Q4 = List.of("B","J","K");
        boolean anadible = true;
        switch (eq.getRanking()) {
            case 1://ESPAÑA (1)
                for (Grupo g : DataSorteo.getGrupos()) {
                    if (g.getEquipoList().size() > 0) {
                        //Si el equipo es (2)
                        if (g.getEquipoList().get(0).getRanking() == 2) {
                            //Si entra el mismo cuadro
                            if (Q1.contains(g.getNombre()) || Q2.contains(g.getNombre())) {
                                if (Q1.contains(grupo.getNombre()) || Q2.contains(grupo.getNombre())) {
                                    lMessage.setText("Error en grupo " + grupo.getNombre() + ": España debe ir en cuadros diferentes que Argentina");
                                    anadible = false;
                                    return anadible;
                                }
                            } else if (Q3.contains(g.getNombre()) || Q4.contains(g.getNombre())) {
                                if (Q3.contains(grupo.getNombre()) || Q4.contains(grupo.getNombre())) {
                                    lMessage.setText("Error en grupo " + grupo.getNombre() + ": España debe ir en cuadros diferentes que Argentina");
                                    anadible = false;
                                    return anadible;
                                }
                            }
                        //Si el equipo es (3) o (4)
                        }
                        if (g.getEquipoList().get(0).getRanking() == 3 || g.getEquipoList().get(0).getRanking() == 4) {
                            if (Q1.contains(g.getNombre()) && Q1.contains(grupo.getNombre())) {
                                lMessage.setText("Error en grupo " + grupo.getNombre() + ": España debe ir en cuadros diferentes que Francia o Inglaterra");
                                anadible = false;
                                return anadible;
                            } else if (Q2.contains(g.getNombre()) && Q2.contains(grupo.getNombre())) {
                                lMessage.setText("Error en grupo " + grupo.getNombre() + ": España debe ir en cuadros diferentes que Francia o Inglaterra");
                                anadible = false;
                                return anadible;
                            } else if (Q3.contains(g.getNombre()) && Q3.contains(grupo.getNombre())) {
                                lMessage.setText("Error en grupo " + grupo.getNombre() + ": España debe ir en cuadros diferentes que Francia o Inglaterra");
                                anadible = false;
                                return anadible;
                            } else if (Q4.contains(g.getNombre()) && Q4.contains(grupo.getNombre())) {
                                lMessage.setText("Error en grupo " + grupo.getNombre() + ": España debe ir en cuadros diferentes que Francia o Inglaterra");
                                anadible = false;
                                return anadible;
                            }
                        }
                    }
                }
                break;
            case 2://ARGENTINA (2)
                for (Grupo g : DataSorteo.getGrupos()) {
                    if (g.getEquipoList().size() > 0) {
                        //Si el equipo es (2)
                        if (g.getEquipoList().get(0).getRanking() == 1) {
                            //Si entra el mismo cuadro
                            if (Q1.contains(g.getNombre()) || Q2.contains(g.getNombre())) {
                                if (Q1.contains(grupo.getNombre()) || Q2.contains(grupo.getNombre())) {
                                    lMessage.setText("Error en grupo " + grupo.getNombre() + ": Argentina debe ir en cuadros diferentes que España");
                                    anadible = false;
                                    return anadible;
                                }
                            } else if (Q3.contains(g.getNombre()) || Q4.contains(g.getNombre())) {
                                if (Q3.contains(grupo.getNombre()) || Q4.contains(grupo.getNombre())) {
                                    lMessage.setText("Error en grupo " + grupo.getNombre() + ": Argentina debe ir en cuadros diferentes que España");
                                    anadible = false;
                                    return anadible;
                                }
                            }
                        //Si el equipo es (3) o (4)
                        }
                        if (g.getEquipoList().get(0).getRanking() == 3 || g.getEquipoList().get(0).getRanking() == 4) {
                            if (Q1.contains(g.getNombre()) && Q1.contains(grupo.getNombre())) {
                                lMessage.setText("Error en grupo " + grupo.getNombre() + ": Argentina debe ir en cuadros diferentes que Francia o Inglaterra");
                                anadible = false;
                                return anadible;
                            } else if (Q2.contains(g.getNombre()) && Q2.contains(grupo.getNombre())) {
                                lMessage.setText("Error en grupo " + grupo.getNombre() + ": Argentina debe ir en cuadros diferentes que Francia o Inglaterra");
                                anadible = false;
                                return anadible;
                            } else if (Q3.contains(g.getNombre()) && Q3.contains(grupo.getNombre())) {
                                lMessage.setText("Error en grupo " + grupo.getNombre() + ": Argentina debe ir en cuadros diferentes que Francia o Inglaterra");
                                anadible = false;
                                return anadible;
                            } else if (Q4.contains(g.getNombre()) && Q4.contains(grupo.getNombre())) {
                                lMessage.setText("Error en grupo " + grupo.getNombre() + ": Argentina debe ir en cuadros diferentes que Francia o Inglaterra");
                                anadible = false;
                                return anadible;
                            }
                        }
                    }
                }
                break;
            case 3://FRANCIA (3)
                for (Grupo g : DataSorteo.getGrupos()) {
                    if (g.getEquipoList().size() > 0) {
                        //Si el equipo es (4)
                        if (g.getEquipoList().get(0).getRanking() == 4) {
                            //Si entra el mismo cuadro
                            if (Q1.contains(g.getNombre()) || Q2.contains(g.getNombre())) {
                                if (Q1.contains(grupo.getNombre()) || Q2.contains(grupo.getNombre())) {
                                    lMessage.setText("Error en grupo " + grupo.getNombre() + ": Francia debe ir en cuadros diferentes que Inglaterra");
                                    anadible = false;
                                    return anadible;
                                }
                            } else if (Q3.contains(g.getNombre()) || Q4.contains(g.getNombre())) {
                                if (Q3.contains(grupo.getNombre()) || Q4.contains(grupo.getNombre())) {
                                    lMessage.setText("Error en grupo " + grupo.getNombre() + ": Francia debe ir en cuadros diferentes que Inglaterra");
                                    anadible = false;
                                    return anadible;
                                }
                            }
                        //Si el equipo es (1) o (2)
                        }
                        if (g.getEquipoList().get(0).getRanking() == 1 || g.getEquipoList().get(0).getRanking() == 2) {
                            if (Q1.contains(g.getNombre()) && Q1.contains(grupo.getNombre())) {
                                lMessage.setText("Error en grupo " + grupo.getNombre() + ": Francia debe ir en cuadros diferentes que España o Argentina");
                                anadible = false;
                                return anadible;
                            } else if (Q2.contains(g.getNombre()) && Q2.contains(grupo.getNombre())) {
                                lMessage.setText("Error en grupo " + grupo.getNombre() + ": Francia debe ir en cuadros diferentes que España o Argentina");
                                anadible = false;
                                return anadible;
                            } else if (Q3.contains(g.getNombre()) && Q3.contains(grupo.getNombre())) {
                                lMessage.setText("Error en grupo " + grupo.getNombre() + ": Francia debe ir en cuadros diferentes que España o Argentina");
                                anadible = false;
                                return anadible;
                            } else if (Q4.contains(g.getNombre()) && Q4.contains(grupo.getNombre())) {
                                lMessage.setText("Error en grupo " + grupo.getNombre() + ": Francia debe ir en cuadros diferentes que España o Argentina");
                                anadible = false;
                                return anadible;
                            }
                        }
                    }
                }
                break;
            case 4://INGLATERRA (4)
                for (Grupo g : DataSorteo.getGrupos()) {
                    if (g.getEquipoList().size() > 0) {
                        //Si el equipo es (3)
                        if (g.getEquipoList().get(0).getRanking() == 3) {
                            //Si entra el mismo cuadro
                            if (Q1.contains(g.getNombre()) || Q2.contains(g.getNombre())) {
                                if (Q1.contains(grupo.getNombre()) || Q2.contains(grupo.getNombre())) {
                                    lMessage.setText("Error en grupo " + grupo.getNombre() + ": Inglaterra debe ir en cuadros diferentes que Francia");
                                    anadible = false;
                                    return anadible;
                                }
                            } else if (Q3.contains(g.getNombre()) || Q4.contains(g.getNombre())) {
                                if (Q3.contains(grupo.getNombre()) || Q4.contains(grupo.getNombre())) {
                                    lMessage.setText("Error en grupo " + grupo.getNombre() + ": Inglaterra debe ir en cuadros diferentes que Francia");
                                    anadible = false;
                                    return anadible;
                                }
                            }
                        //Si el equipo es (1) o (2)
                        }
                        if (g.getEquipoList().get(0).getRanking() == 1 || g.getEquipoList().get(0).getRanking() == 2) {
                            if (Q1.contains(g.getNombre()) && Q1.contains(grupo.getNombre())) {
                                lMessage.setText("Error en grupo " + grupo.getNombre() + ": Inglaterra debe ir en cuadros diferentes que España o Argentina");
                                anadible = false;
                                return anadible;
                            } else if (Q2.contains(g.getNombre()) && Q2.contains(grupo.getNombre())) {
                                lMessage.setText("Error en grupo " + grupo.getNombre() + ": Inglaterra debe ir en cuadros diferentes que España o Argentina");
                                anadible = false;
                                return anadible;
                            } else if (Q3.contains(g.getNombre()) && Q3.contains(grupo.getNombre())) {
                                lMessage.setText("Error en grupo " + grupo.getNombre() + ": Inglaterra debe ir en cuadros diferentes que España o Argentina");
                                anadible = false;
                                return anadible;
                            } else if (Q4.contains(g.getNombre()) && Q4.contains(grupo.getNombre())) {
                                lMessage.setText("Error en grupo " + grupo.getNombre() + ": Inglaterra debe ir en cuadros diferentes que España o Argentina");
                                anadible = false;
                                return anadible;
                            }
                        }
                    }
                }
                break;
            default:
                break;
        }
        return anadible;
    }

    public static int contarEuropeos(Grupo grupo){
        int cantUEFA = 0;
        for (Equipo eq : grupo.getEquipoList()) {
            if (eq.getConfederacion().equals("UEFA")) {
                cantUEFA++;
            }
        }
        return cantUEFA;
    }

    public static int[] contarGruposForRepechaje(Grupo[] grupos, int[] contadores){
        contadores[0] = grupos.length;
        contadores[1] = grupos.length;
        for (Grupo gr : grupos) {
            for (Equipo eq : gr.getEquipoList()) {
                boolean romper = false;
                if (eq.getConfederacion().equals("AFC") || eq.getConfederacion().equals("CONMEBOL") || eq.getConfederacion().equals("CONCACAF")) { // son de la misma confederacion?
                    System.out.println("FIFA 2 no puede entrar al grupo " + gr.getNombre());
                    contadores[1]--;
                    romper = true;
                } 
                if (eq.getConfederacion().equals("CAF") || eq.getConfederacion().equals("OFC") || eq.getConfederacion().equals("CONCACAF")) { // son de la misma confederacion?
                    System.out.println("FIFA 1 no puede entrar al grupo " + gr.getNombre());
                    contadores[0]--;
                    romper = true;
                }
                if (romper) {
                    break;
                }
            }
        }
        return contadores;  
    }    
}
