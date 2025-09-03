import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Random;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.ImageIcon;

public class Carta {
    private int indice;

    public Carta(Random r) {
        indice = r.nextInt(52) + 1;
    }

    public void mostrar(JPanel pnl, int x, int y) {
        JLabel lblCarta= new JLabel();
        String archivoImagen = "imagenes/CARTA" + indice + ".jpg";
        ImageIcon imgCarta = new ImageIcon(getClass().getResource(archivoImagen));
        lblCarta.setIcon(imgCarta);
        lblCarta.setBounds(x , y, imgCarta.getIconWidth(), imgCarta.getIconHeight());
        pnl.add(lblCarta);

        lblCarta.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent me) {
                JOptionPane.showMessageDialog(null, getNombre() + " de " + getPinta());
            }
        });
    }

    public Pinta getPinta() {
        if(indice <= 13) {
            return Pinta.TREBOL;
        } else if(indice <= 26) {
            return Pinta.PICA;
        } else if(indice <= 39) {
            return Pinta.CORAZON;
        } else {
            return Pinta.DIAMANTE;
        }
        
    }

    public NombreCarta getNombre(){
        int residuo = indice % 13;
        int posicion = residuo == 0? 12: residuo - 1;
        
        /*
        * if (residuo == 0) {
        *    posicion = 12;
        * } else {
        *     posicion = residuo - 1;
        *}
        */

        return NombreCarta.values()[posicion];
    }
}