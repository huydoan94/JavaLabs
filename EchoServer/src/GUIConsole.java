/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Huy
 */
import javax.swing.*;
import java.awt.event.*;
import java.awt.*;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class GUIConsole extends JFrame implements ChatIF {

    final public static int DEFAULT_PORT = 5555;

    private final JButton closeB = new JButton("Close");
    private final JButton openB = new JButton("Open");
    private final JButton sendB = new JButton("Send");
    private final JButton quitB = new JButton("Quit");
    private final JTextField portTxF = new JTextField("5555");
    private final JTextField hostTxF = new JTextField("127.0.0.1");
    private final JTextField messageTxF = new JTextField("");
    private final JLabel portLB = new JLabel("Port: ", JLabel.RIGHT);
    private final JLabel hostLB = new JLabel("Host: ", JLabel.RIGHT);
    private final JLabel messageLB = new JLabel("Message: ", JLabel.RIGHT);
    private final JTextArea messageList = new JTextArea();
    JComboBox test = new JComboBox();

    private ChatClient chatClient;

    static String host = "";
    static int port = 0;  //The port number

    public static void main(String[] args) {
        try {
            host = args[0];
            port = Integer.parseInt(args[1]);
        } catch (ArrayIndexOutOfBoundsException e) {
            host = "localhost";
            port = DEFAULT_PORT;
        }

        new GUIConsole();
    }

    public GUIConsole() {
        super("Simple Chat GUI");
        setSize(300, 400);
        setLayout(new BorderLayout(5, 5));
        JPanel bottom = new JPanel();
        add("Center", messageList);
        add("South", bottom);
        bottom.setLayout(new GridLayout(5, 2, 5, 5));
        bottom.add(hostLB);
        bottom.add(hostTxF);
        bottom.add(portLB);
        bottom.add(portTxF);
        bottom.add(messageLB);
        bottom.add(messageTxF);
        bottom.add(openB);
        bottom.add(sendB);
        bottom.add(closeB);
        bottom.add(quitB);
        setVisible(true);

        SendButtonAction sendButtonAction = new SendButtonAction();
        sendB.addActionListener(sendButtonAction);

        OpenConnectionAction openButtonAction = new OpenConnectionAction();
        openB.addActionListener(openButtonAction);

        QuitConnectionAction quitConnectionAction = new QuitConnectionAction();
        quitB.addActionListener(quitConnectionAction);

        CloseConnectionAction closeConnectionAction = new CloseConnectionAction();
        closeB.addActionListener(closeConnectionAction);
    }

    @Override
    public void display(String message) {
        messageList.insert(message + "\n", 0);
    }

    class CloseConnectionAction implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            if (chatClient != null && chatClient.isConnected()) {
                try {
                    chatClient.closeConnection();
                } catch (IOException ex) {
                    System.out.println(ex.getMessage());
                }
            }
        }
    }

    class QuitConnectionAction implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            if (chatClient != null) {
                chatClient.quit();
            }
        }
    }

    class OpenConnectionAction implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                chatClient = new ChatClient(host, port, GUIConsole.this);
            } catch (IOException ioe) {
                System.out.println("Error: Can't setup connection!!!!"
                        + " Terminating client.");
                System.out.println(ioe.getMessage());
                System.exit(1);
            }
        }
    }

    class SendButtonAction implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            if (chatClient != null) {
                chatClient.handleMessageFromClientUI(messageTxF.getText());
            } else {
                display("<SYSTEM>Does not connect to server");
            }
        }
    }
}
