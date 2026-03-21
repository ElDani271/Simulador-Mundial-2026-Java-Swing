package FaseGruposCore.biblioteca;

import java.awt.Graphics;
import java.awt.Image;
import javax.swing.ImageIcon;
import javax.swing.JPanel;

public class FondoPanel extends JPanel {
    private Image imagen;

    public FondoPanel(String ruta) {
        this.imagen = new ImageIcon(getClass().getResource(ruta)).getImage();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        // Dibuja la imagen cubriendo todo el panel
        g.drawImage(imagen, 0, 0, getWidth(), getHeight(), this);
    }
}
