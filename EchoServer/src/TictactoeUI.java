
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author nhath
 */
public class TictactoeUI extends JFrame{
    
    ChatClient chatClient;

    public ChatClient getChatClient() {
        return chatClient;
    }

    public void setChatClient(ChatClient chatClient) {
        this.chatClient = chatClient;
    }
    
    
    
    public TictactoeUI() {
        super("Tic Tac Toe");
        setSize(800, 800);
        setLayout(new GridLayout(3, 3)); // set the layout to a 3x3 grid

    // create buttons and add them to the frame
    for (int i = 1; i <= 9; i++) {
      JButton button = new JButton("Button " + i);
      add(button);
      
      ClickOnCell clickOnCell = new ClickOnCell(button, i);
      button.addActionListener(clickOnCell);
      
    }
    
    
    setVisible(true);
        
        
    }
    
    class ClickOnCell implements ActionListener {
        
        JButton button;
        int cell;

        public int getCell() {
            return cell;
        }

        public void setCell(int cell) {
            this.cell = cell;
        }

        public ClickOnCell(JButton button ,int cell) {
            this.cell = cell;
            this.button = button;
        }
        
        
        @Override
        public void actionPerformed(ActionEvent e) {
            
            this.button.setText("X");
            
        }
    }
}
