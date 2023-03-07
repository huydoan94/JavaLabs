
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.ArrayList;
import javax.swing.JButton;
import javax.swing.JFrame;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author nhath
 */
public class TictactoeUI extends JFrame implements WindowListener {
    
    static final String DEFAULT_SYMBOL = "-";
    
    ChatClient chatClient;
    ArrayList<JButton> buttons;
    
    public TictactoeUI() {
        super("Tic Tac Toe");
        setSize(400, 400);
        setLayout(new GridLayout(3, 3)); // set the layout to a 3x3 grid
        addWindowListener(this);
        
        buttons = new ArrayList<>();
        // create buttons and add them to the frame
        for (int i = 0; i < 9; i++) {
            JButton button = new JButton(DEFAULT_SYMBOL);
            button.setFont(new Font("Arial", Font.PLAIN, 30));
            buttons.add(button);
            add(button);
            
            ClickOnCell clickOnCell = new ClickOnCell(button, i);
            button.addActionListener(clickOnCell);
        }
    }
    
    public void setChatClient(ChatClient chatClient) {
        this.chatClient = chatClient;
    }
    
    public void setActive(boolean isActive) {
        for (JButton button : buttons) {
            button.setEnabled(isActive);
        }
    }
    
    public void setButtonSymbol(int cell, String symbol) {
        buttons.get(cell).setText(symbol);
    }
    
    class ClickOnCell implements ActionListener {
        
        int cell;
        JButton button;
        
        public ClickOnCell(JButton button, int cell) {
            this.cell = cell;
            this.button = button;
        }
        
        @Override
        public void actionPerformed(ActionEvent e) {
            // Prevent clicking on button that has symbol
            if (!button.getText().equals(DEFAULT_SYMBOL)) {
                return;
            }
            
            chatClient.sendTicTacToeMove(cell);
        }
    }
    
    @Override
    public void windowClosing(WindowEvent e) {
        // Decline game if window closing
        chatClient.sendTicTacToeDecline();
    }
    
    @Override
    public void windowOpened(WindowEvent e) {
    }
    
    @Override
    public void windowClosed(WindowEvent e) {
    }
    
    @Override
    public void windowIconified(WindowEvent e) {
    }
    
    @Override
    public void windowDeiconified(WindowEvent e) {
    }
    
    @Override
    public void windowActivated(WindowEvent e) {
    }
    
    @Override
    public void windowDeactivated(WindowEvent e) {
    }
}
