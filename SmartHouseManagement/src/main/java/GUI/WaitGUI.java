package GUI;

import javax.swing.*;
import java.awt.*;

public class WaitGUI extends JFrame {
    public WaitGUI(String message) {
        setTitle("Please Wait");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(350, 150);
        setLayout(new BorderLayout(10, 10));
        setLocationRelativeTo(null); // Center on screen

        JLabel label = new JLabel(message, SwingConstants.CENTER);
        label.setBorder(BorderFactory.createEmptyBorder(20, 10, 10, 10));

        // Indeterminate progress bar (bounces back and forth)
        JProgressBar progressBar = new JProgressBar();
        progressBar.setIndeterminate(true);

        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(0, 20, 20, 20));
        panel.add(progressBar, BorderLayout.CENTER);

        add(label, BorderLayout.NORTH);
        add(panel, BorderLayout.CENTER);
    }
}
