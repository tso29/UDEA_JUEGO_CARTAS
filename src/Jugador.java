import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Random;
import javax.swing.JPanel;

public class Jugador {

    private final int TOTAL_CARTAS = 10;
    private final int MARGEN = 10;
    private final int SEPARACION = 40;
    private Carta[] cartas = new Carta[TOTAL_CARTAS];
    private Random r = new Random();

    public void repartir() {
        for (int i = 0; i < TOTAL_CARTAS; i++) {
            cartas[i] = new Carta(r);
        }
    }

    public void mostrar(JPanel pnl) {
        pnl.removeAll();
        /*
         * for (int i = 0; i < TOTAL_CARTAS; i++) {
         * Carta carta = cartas[i];
         * carta.mostrar(pnl, );
         * }
         */
        int posicion = MARGEN + TOTAL_CARTAS * SEPARACION;
        for (Carta carta : cartas) {
            carta.mostrar(pnl, posicion, MARGEN);
            posicion -= SEPARACION;
        }
        pnl.repaint();
    }

    public String getGrupos() {
        String resultado = "No se encontraron grupos";
        // Calcular los contadores de las cartas
        int[] contadores = new int[NombreCarta.values().length];
        boolean[] cartasUsadas = new boolean[cartas.length];

        for (Carta carta : cartas) {
            contadores[carta.getNombre().ordinal()]++;
        }

        // validar si hubo grupos
        boolean hayGrupos = false;
        for (int contador : contadores) {
            if(contador >= 2){
                hayGrupos = true;
                break;
            }
        }

        // obtener los grupos
        if(hayGrupos) {
            resultado = "Se hallaron los siguientes grupos:\n";
            int p = 0;
            for (int contador : contadores) {
                if (contador >= 2) {
                    resultado += Grupo.values()[contador] + " de " + NombreCarta.values()[p] + "\n";
                
                    // marcas las cartas que han sido usadas
                    for (int j = 0; j < cartas.length; j++) {
                        if (cartas[j].getNombre() == NombreCarta.values()[p]) {
                            cartasUsadas[j] = true;
                        }
                    }
                }
                p++;
            }

            // for (int i =0; i < contadores.length; i++) {
            //     int contador = contadores[i];
            //     if (contador >= 2){
            //         resultado += Grupo.values()[contador] + " de " + NombreCarta.values()[i] + "\n";
            //     }
            // }
        }

        // Encontrar escalera
        List<String> escaleras = new ArrayList<>();

        for (Pinta pinta : Pinta.values()) {
            // Filtrar cartas de una misma pinta
            List<Carta> cartasPinta = new ArrayList<>();
            for (Carta c : cartas) {
                if (c.getPinta() == pinta) {
                    cartasPinta.add(c);
                }
            }

            // Ordenar por valor
            cartasPinta.sort(Comparator.comparingInt(c -> c.getNombre().ordinal()));

            // Buscar secuencias consecutivas desde 2 pares
            int consecutivas = 1;            

            for (int i = 1; i < cartasPinta.size(); i++) {
                int valorPrev = cartasPinta.get(i - 1).getNombre().ordinal();
                int valorAct = cartasPinta.get(i).getNombre().ordinal();

                if (valorAct == valorPrev + 1) {
                    consecutivas++;
                    if (consecutivas >= 2) {
                        escaleras.add(Grupo.values()[consecutivas] + " de " + pinta +
                        " desde " + cartasPinta.get(i - consecutivas + 1).getNombre() +
                        " hasta " + cartasPinta.get(i).getNombre());
                    
                        // marcar cartas usadas
                        for(int j = i - consecutivas +1; j <= i; j++) {
                            Carta cartaUsada = cartasPinta.get(j);
                            for (int k = 0; k < cartas.length; k++) {
                                if (cartas[k] == cartaUsada) {
                                    cartasUsadas[k] = true;
                                }
                            }
                        }
                    }
                } else {
                    consecutivas = 1;
                }
            }

        }

        if (!escaleras.isEmpty()) {
            if (escaleras.size() == 1) {
                resultado += "Se halló la siguiente escalera:\n";
            } else {
                resultado += "Se hallaron las siguientes escaleras:\n";
            }
            for (String e : escaleras) {
                resultado += "- " + e + "\n";
            }
        }

        if (escaleras.isEmpty()) {
            resultado += "No es encontraron escaleras.";
        }

        // Cartas que sobran
        List<Carta> cartasSobrantes = new ArrayList<>();
        for (int i = 0; i < cartas.length; i++) {
            if (!cartasUsadas[i]) {
                cartasSobrantes.add(cartas[i]);
            }
        }

        if (!cartasSobrantes.isEmpty()) {
            resultado += "Cartas que sobran:\n";
            int puntosCartasSobrantes = 0;

            for (Carta c : cartasSobrantes) {
                resultado += "- " + c.getNombre() + " de " + c.getPinta() +"\n";
                puntosCartasSobrantes += getValorCarta(c.getNombre()); // Aqui se suman los puntos
            }

            resultado += "Puntos: " + puntosCartasSobrantes + "\n";
        }        

        return resultado;

        
    }

    public int getValorCarta(NombreCarta nombre) {
        switch (nombre) {
            case AS:
            case JACK:
            case QUEEN:
            case KING:
                return 10;        
            default:
                // Mantener las demás cartas
                return nombre.ordinal() + 1;
        }
    }
}
