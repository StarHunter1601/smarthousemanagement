package GUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class ChatGUI extends JFrame {
    private JTextArea console = new JTextArea();
    private JTextField input = new JTextField();
    private JButton sendBtn = new JButton("Send");
    private MessageSender sender;

    public interface MessageSender {
        void sendMessage(String message);
    }

    public ChatGUI(String title, MessageSender sender) {
        super(title);
        this.sender = sender;

        // Layout setup
        setLayout(new BorderLayout());
        console.setEditable(false);
        add(new JScrollPane(console), BorderLayout.CENTER);

        JPanel southPanel = new JPanel(new BorderLayout());
        southPanel.add(input, BorderLayout.CENTER);
        southPanel.add(sendBtn, BorderLayout.EAST);
        add(southPanel, BorderLayout.SOUTH);

        // Action Listeners
        ActionListener sendAction = e -> {
            String msg = input.getText();
            if (!msg.isEmpty()) {
                sender.sendMessage(msg);
                input.setText("");
            }
        };

        sendBtn.addActionListener(sendAction);
        input.addActionListener(sendAction);

        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

    public void display(String message) {
        console.append(message + "\n");
    }
}
