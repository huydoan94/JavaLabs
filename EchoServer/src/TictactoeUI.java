
import java.awt.BorderLayout;
import java.awt.GridLayout;
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
    public TictactoeUI() {
        super("Tic Tac Toe");
        setSize(800, 800);
        setLayout(new GridLayout(3, 3)); // set the layout to a 3x3 grid

    // create buttons and add them to the frame
    for (int i = 1; i <= 9; i++) {
      JButton button = new JButton("Button " + i);
      add(button);
    }

    
    setVisible(true);
        
        
    }
}
