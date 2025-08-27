import javax.swing.JFrame;
import javax.swing.WindowConstants;

import java.awt.Color;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;


public class FrmJuego extends JFrame {
    
    private JPanel pnlJugador1, pnlJugador2;

    public FrmJuego() {

        setTitle("Juego de Cartas");
        setSize(600, 300);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setLayout(null);

        JButton btnRepartir = new JButton("Repartir");
        btnRepartir.setBounds(10, 10, 100, 25);
        getContentPane().add(btnRepartir);

        JButton btnVerificar = new JButton("Verificar");
        btnVerificar.setBounds(120, 10, 100, 25);
        getContentPane().add(btnVerificar);
    
        pnlJugador1 = new JPanel();
        pnlJugador1.setBackground(new Color(150, 255, 50));

        pnlJugador2 = new JPanel();
        pnlJugador2.setBackground(new Color(0, 255, 255));

        JTabbedPane tpJugadores = new JTabbedPane();
        tpJugadores.addTab("Marin Estrada Contreras", pnlJugador1);
        tpJugadores.addTab("Marin Estrada Contreras", pnlJugador2);

        tpJugadores.setBounds(10, 40, 550, 200);
        getContentPane().add(tpJugadores);

    }

}