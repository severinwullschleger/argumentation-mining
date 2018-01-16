package GUI;

import javax.swing.*;
import java.awt.*;

/**
 * Created by LuckyP on 16.01.18.
 */
public class GUI {
    private JLabel LToolTitle;
    private JPanel mainPanel;

    public static void startGUI() {
        JFrame frame = new JFrame("Argumentation Mining HS2017");
        JScrollPane pane = new JScrollPane(new GUI().mainPanel);
        frame.setContentPane(pane);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        Dimension dim = new Dimension();
        dim.setSize(1000, 750);
        frame.setSize(dim);
        frame.setVisible(true);
    }
}
