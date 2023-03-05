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

public class ClientGUI extends JFrame implements ChatIF {

    final public static int DEFAULT_PORT = 5555;

    private final JButton closeB = new JButton("Close");
    private final JButton openB = new JButton("Open");
    private final JButton sendB = new JButton("Send");
    private final JButton quitB = new JButton("Quit");
    private final JButton loginB = new JButton("Login");
    private final JButton ticTacToeB = new JButton("Tic Tac Toe");
    private final JComboBox userListComboBox = new JComboBox();

    private final JTextField portTxF = new JTextField("5555");
    private final JTextField hostTxF = new JTextField("127.0.0.1");
    private final JTextField messageTxF = new JTextField("");
    private final JTextField loginTxF = new JTextField("");
    private final JLabel userListLB = new JLabel("User list: ", JLabel.RIGHT);
    private final JLabel portLB = new JLabel("Port: ", JLabel.RIGHT);
    private final JLabel hostLB = new JLabel("Host: ", JLabel.RIGHT);
    private final JLabel messageLB = new JLabel("Message: ", JLabel.RIGHT);
    private final JTextArea messageList = new JTextArea();

    private TictactoeUI ticTacToeUI;
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

        new ClientGUI();
    }

    public ClientGUI() {
        super("Simple Chat GUI");
        setSize(300, 400);
        setLayout(new BorderLayout(5, 5));
        JPanel bottom = new JPanel();
        add("Center", messageList);
        add("South", bottom);
        bottom.setLayout(new GridLayout(8, 2));
        bottom.add(hostLB);
        bottom.add(hostTxF);
        bottom.add(portLB);
        bottom.add(portTxF);
        bottom.add(messageLB);
        bottom.add(messageTxF);
        bottom.add(userListLB);
        bottom.add(userListComboBox);
        bottom.add(openB);
        bottom.add(sendB);
        bottom.add(ticTacToeB); //added tictactoe button
        bottom.add(closeB);
        bottom.add(loginB);
        bottom.add(loginTxF);
        bottom.add(quitB);
        setVisible(true);
        setButtonsBaseOnConnectionStatus(false);

        SendButtonAction sendButtonAction = new SendButtonAction();
        sendB.addActionListener(sendButtonAction);

        LoginAction loginAction = new LoginAction();
        loginB.addActionListener(loginAction);

        OpenConnectionAction openButtonAction = new OpenConnectionAction();
        openB.addActionListener(openButtonAction);

        CloseConnectionAction closeConnectionAction = new CloseConnectionAction();
        closeB.addActionListener(closeConnectionAction);

        QuitAction quitAction = new QuitAction();
        quitB.addActionListener(quitAction);
    }

    @Override
    public void display(String message) {
        if (message.indexOf("<USERLIST>") == 0) {
            String usersString = message.substring(10);
            String[] users = usersString.split("&");

            userListComboBox.removeAllItems();
            for (String user : users) {
                userListComboBox.addItem(user.replaceAll("&amp;", "&"));
            }
        } else {
            messageList.insert(message + "\n", 0);
        }

    }

    private void setButtonsBaseOnConnectionStatus(boolean isConnected) {
        hostTxF.setEditable(!isConnected);
        portTxF.setEditable(!isConnected);
        messageTxF.setEditable(isConnected);
        userListComboBox.setEnabled(isConnected);
        openB.setEnabled(!isConnected);
        sendB.setEnabled(isConnected);
        ticTacToeB.setEnabled(isConnected);
        closeB.setEnabled(isConnected);
        loginTxF.setEditable(!isConnected && chatClient != null);
        loginB.setEnabled(!isConnected && chatClient != null);
    }

    class SendButtonAction implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            if (chatClient != null && chatClient.isConnected()) {
                chatClient.handleMessageFromClientUI(messageTxF.getText());
            } else {
                display("<SYSTEM>Does not connect to server");
            }
        }
    }

    class LoginAction implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            chatClient.handleMessageFromClientUI("#login " + loginTxF.getText().trim());
            setButtonsBaseOnConnectionStatus(true);
        }
    }

    class OpenConnectionAction implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            if (chatClient == null) {
                try {
                    chatClient = new ChatClient(host, port, ClientGUI.this);
                    setButtonsBaseOnConnectionStatus(true);
                } catch (IOException ioe) {
                    System.out.println("Error: Can't setup connection!!!!"
                            + " Terminating client.");
                    System.out.println(ioe.getMessage());
                }
            } else if (!chatClient.isConnected()) {
                try {
                    chatClient.openConnection();
                    setButtonsBaseOnConnectionStatus(true);
                } catch (IOException ioe) {
                    System.out.println("Error: Can't setup connection!!!!"
                            + " Terminating client.");
                    System.out.println(ioe.getMessage());
                }
            }
        }
    }

    class CloseConnectionAction implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            if (chatClient != null && chatClient.isConnected()) {
                try {
                    chatClient.closeConnection();
                    setButtonsBaseOnConnectionStatus(false);
                } catch (IOException ex) {
                    System.out.println(ex.getMessage());
                }
            }
        }
    }

    class QuitAction implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            if (chatClient != null && chatClient.isConnected()) {
                try {
                    chatClient.closeConnection();

                } catch (IOException ex) {
                }
            }
            System.exit(0);
        }
    }

}