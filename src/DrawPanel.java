import java.awt.*;
import java.awt.event.MouseListener;
import java.awt.event.MouseEvent;
import javax.swing.JPanel;
import java.util.ArrayList;

public class DrawPanel extends JPanel {

    public DrawPanel()
    {


    }

    protected void paintComponent(Graphics g) {
        super.paintComponent(g);



        for (int r = 0; r < 30; r++) {
            for (int c = 0; c < 40; c++) {
                g.drawRect(c * (24), r * (24), 20, 20);
            }
        }
    }

}