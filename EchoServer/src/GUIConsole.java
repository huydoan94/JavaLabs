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

public class GUIConsole extends JFrame implements ChatIF {
    
    final public static int DEFAULT_PORT = 5555;
    
    private final JButton closeB = new JButton("Close");
    private final JButton openB = new JButton("Open");
    private final JButton sendB = new JButton("Send");
    private final JButton quitB = new JButton("Quit");
    private final JButton TicTacToe = new JButton("Tic Tac Toe");
    private final JComboBox userListComboBox = new JComboBox();
    
    private final JTextField portTxF = new JTextField("5555");
    private final JTextField hostTxF = new JTextField("127.0.0.1");
    private final JTextField messageTxF = new JTextField("");
    private final JLabel userListLB = new JLabel("User list: ", JLabel.RIGHT);
    private final JLabel portLB = new JLabel("Port: ", JLabel.RIGHT);
    private final JLabel hostLB = new JLabel("Host: ", JLabel.RIGHT);
    private final JLabel messageLB = new JLabel("Message: ", JLabel.RIGHT);
    private final JTextArea messageList = new JTextArea();
    
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
        bottom.add(userListLB);
        bottom.add(userListComboBox);
        bottom.add(openB);
        bottom.add(sendB);
        bottom.add(TicTacToe); //added tictactoe button
        
        bottom.add(closeB);
        
        bottom.add(quitB);
        setVisible(true);
        
        SendButtonAction sendButtonAction = new SendButtonAction();
        sendB.addActionListener(sendButtonAction);
        
        OpenConnectionAction openButtonAction = new OpenConnectionAction();
        openB.addActionListener(openButtonAction);
        
        QuitConnectionAction quitConnectionAction = new QuitConnectionAction();
        quitB.addActionListener(quitConnectionAction);
        
        new TictactoeUI();
    }
    
    
    
    
    @Override
    public void display(String message) {
        if(message.indexOf("<USERLIST>")==0){
          String user = message.substring(10);
          //userListComboBox.removeAllItems();
          userListComboBox.addItem(user);
          
        }
        else{
            messageList.insert(message + "\n", 0);
        }
        
    }
    
    class QuitConnectionAction implements ActionListener {
        
        @Override
        public void actionPerformed(ActionEvent e) {
            if (chatClient != null) {
                chatClient.quit();
            }
            
            System.exit(0);
        }
    }
    
    class OpenConnectionAction implements ActionListener {
        
        @Override
        public void actionPerformed(ActionEvent e) {
            if(chatClient != null && chatClient.isConnected()){
                return;
            }
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
