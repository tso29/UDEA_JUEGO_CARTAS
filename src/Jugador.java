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
            if (contador >= 2) {
                hayGrupos = true;
                break;
            }
        }

        // obtener los grupos
        if (hayGrupos) {
            resultado = "Se hallaron los siguientes grupos:\n";
            int p = 0;
            for (int contador : contadores) {
                if (contador >= 2) {
                    String valorCarta = "";
                    for (Carta c : cartas) {
                        if (c.getNombre() == NombreCarta.values()[p]) {
                            valorCarta = c.getValorTexto();
                            break;
                        }
                    }
                    resultado += Grupo.values()[contador] + " de " + valorCarta + "\n";

                    // marcas las cartas que han sido usadas
                    for (int j = 0; j < cartas.length; j++) {
                        if (cartas[j].getNombre() == NombreCarta.values()[p]) {
                            cartasUsadas[j] = true;
                        }
                    }
                }
                p++;
            }
        }

        // Encontrar escalera
        String[] escaleras = new String[10];
        int totalEscaleras = 0;

        for (Pinta pinta : Pinta.values()) {
            int[] indicesPinta = new int[cartas.length];
            int count = 0; // cuenta cartas encontradas

            for (int i = 0; i < cartas.length; i++) {
                if (cartas[i].getPinta() == pinta) {
                    indicesPinta[count] = i; // guardo el índice
                    count++;
                }
            }

            // Ordenar por valor
            for (int i = 0; i < count - 1; i++) {
                for (int j = 0; j < count - i - 1; j++) {
                    int valor1 = cartas[indicesPinta[j]].getNombre().ordinal();
                    int valor2 = cartas[indicesPinta[j + 1]].getNombre().ordinal();
                    if (valor1 > valor2) {
                        int temp = indicesPinta[j];
                        indicesPinta[j] = indicesPinta[j + 1];
                        indicesPinta[j + 1] = temp;
                    }
                }
            }

            // Buscar secuencias consecutivas desde 2 pares
            int consecutivas = 1;

            for (int i = 1; i < count; i++) {
                int valorPrev = cartas[indicesPinta[i - 1]].getValor();
                int valorAct = cartas[indicesPinta[i]].getValor();

                if (valorAct == valorPrev + 1) {
                    consecutivas++;
                    if (consecutivas >= 2) {
                        escaleras[totalEscaleras] = Grupo.values()[consecutivas] + " de " + pinta +
                                " desde " + cartas[indicesPinta[i - consecutivas + 1]].getValorTexto() +
                                " hasta " + cartas[indicesPinta[i]].getValorTexto();
                        totalEscaleras++;
                    }
                } else {
                    consecutivas = 1;
                }
            }

        }

        if (totalEscaleras > 0) {
            resultado += "\n";
            if (totalEscaleras == 1) {
                resultado += "Se halló la siguiente escalera:\n";
            } else {
                resultado += "Se hallaron las siguientes escaleras:\n";
            }
            for (int i = 0; i < totalEscaleras; i++) {
                resultado += "- " + escaleras[i] + "\n";
            }
        } else {
            resultado += "No se encontraron escaleras.\n";
        }

        // Cartas que sobran
        Carta[] cartasSobrantes = new Carta[cartas.length]; // como máximo pueden sobrar todas
        int totalSobrantes = 0;

        for (int i = 0; i < cartas.length; i++) {
            if (!cartasUsadas[i]) {
                cartasSobrantes[totalSobrantes] = cartas[i];
                totalSobrantes++;
            }
        }

        if (totalSobrantes > 0) {
            resultado += "\n";
            resultado += "Cartas que sobran:\n";
            int puntosCartasSobrantes = 0;

            for (int i = 0; i < totalSobrantes; i++) {
                Carta c = cartasSobrantes[i];
                resultado += "- " + c.getValorTexto() + " de " + c.getPinta() + "\n";
                puntosCartasSobrantes += c.getValor(); // Aqui se suman los puntos
            }

            resultado += "Puntos: " + puntosCartasSobrantes + "\n";
        }

        return resultado;

    }

}