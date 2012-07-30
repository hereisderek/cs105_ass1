/*
 * The class creates an application
 * used for a Card game.
 *
 * author Adriana Ferraro
 * version S2, 2012
 */

import java.awt.*;
import javax.swing.*;

public class CardTester {
    public static void main( String[] args ) {  
        JFrame cardsJPanel = new CardsTesterJFrame("CARD CLASS TESTER", 10, 10, 500, 400);
    }
}

class CardsTesterJFrame extends JFrame {
    
    public CardsTesterJFrame( String title, int x, int y, int width, int height ) {
        // Set the title, top left location, 
        //and close operation for the frame
        setTitle(title);
        setLocation(x, y);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        // Create an instance of the JPanel class, 
        //and set this to define the
        // content of the window
        JPanel frameContent = new CardsTesterJPanel();
        Container visibleArea = getContentPane();
        visibleArea.add(frameContent);
        
        // Set the size of the content pane of the window, 
        //resize and validate the
        // window to suit, obtain keyboard focus, 
        //and then make the window visible
        frameContent.setPreferredSize(new Dimension(width, height));
        pack();
        frameContent.requestFocusInWindow();
        setVisible(true);
    }
}