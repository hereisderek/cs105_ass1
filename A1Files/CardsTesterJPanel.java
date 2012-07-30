/*
 * Class which defines a
 * JPanel which used to
 * test the Card class.
 *
 * author Adriana Ferraro
 * version S2, 2012
 */


import java.util.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.applet.Applet;

public class CardsTesterJPanel extends JPanel implements ActionListener, MouseListener {
    private static final int[] VALUES = {2, 5, 7, 10, 12, 1, 4, 11, 1};
    private static final int[] SUITS = {0, 1, 2, 3, 3, 3, 0, 2, 1};
    private static final int[] X_VALUES = {30, 130, 230, 30, 130, 230, 30, 130, 230};
    private static final int[] Y_VALUES = {80, 80, 80, 180, 180, 180, 280, 280, 280};
    private static final boolean[] IS_FACE_UP = {true, false, true, false, true, false, true, true, true};
    private static final boolean[] HAS_BEEN_REMOVED = {true, false, false, false, true, false, true, false, false};
    
    private JTextField toStringT;
    private int cardWidth;
    private int cardHeight; 
    private Card[] cards;
    
    public CardsTesterJPanel() {
        loadAllCardImagesAndSetUpCardDimensions();
        addPanelsOfComponents();
        
        addMouseListener(this);
        reset();  
    }
//-------------------------------------------------------------------
//-------- Respond to ActionEvents ----------------------------------
//-------------------------------------------------------------------
    public void actionPerformed(ActionEvent e) {   
        requestFocusInWindow();
        repaint();  
    }
//-------------------------------------------------------------------
//-------- Create the random array of CARD objects ------------------
//-------------------------------------------------------------------
    private void reset() {    
        cards = getCardsArray();
        toStringT.setText(""); 
    }
//---------------------------------------------------------------------
//-------- Respond to MouseEvents -------------------------------------
//---------------------------------------------------------------------
    public void mousePressed(MouseEvent e) {
        boolean isFaceUp, hasBeenRemoved;
        String information = "";
        Rectangle cardArea;
        
        Point pressPt = e.getPoint();
        
        for (int i = 0; i < cards.length; i++) {
            cardArea = cards[i].getCardArea();
            if (cards[i].pressPointIsInsideCard(pressPt) || cardArea.contains(pressPt)) {
                isFaceUp = cards[i].getIsFaceUp();
                hasBeenRemoved = cards[i].getHasBeenRemoved();
                information = information + cards[i].toString();
                if (hasBeenRemoved) {
                    cards[i].setIsFaceUp(true);
                    cards[i].setHasBeenRemoved(false);
                    information = information + " has now reappeared";
                } else if (isFaceUp) {
                    cards[i].setIsFaceUp(false);
                    information = information + " is now face down";
                } else {
                    cards[i].setHasBeenRemoved(true);
                    information = information + " has now disappeared";
                }
            }
        }
        
        toStringT.setText(information);
        repaint();
    }
    
    
    public void mouseReleased(MouseEvent e) {}
    public void mouseEntered(MouseEvent e) {}
    public void mouseExited(MouseEvent e) {}
    public void mouseClicked(MouseEvent e) {} 
    
//-------------------------------------------------------------------
//-------- Draw all the CARD objects --------------------------------
//-------- Do not draw the blank cards in the last row --------------
//------------------------------------------------------------------- 
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        
        for (int i=0; i < cards.length; i++) {
            cards[i].drawCard(g, this);      
        }  
    }
//-------------------------------------------------------------------
//-------- Create a 1D array of CARD objects ------------------------
//------------------------------------------------------------------- 
    private Card[] getCardsArray() {
        Card[] cards = new Card[VALUES.length];
        
        for (int i=0; i < cards.length; i++) {
            cards[i] = new Card(VALUES[i], SUITS[i]);
            cards[i].setCardArea(X_VALUES[i], Y_VALUES[i], cardWidth, cardHeight);
            cards[i].setIsFaceUp(IS_FACE_UP[i]);
            cards[i].setHasBeenRemoved(HAS_BEEN_REMOVED[i]);     
        }  
        
        return cards;
    }
//-------------------------------------------------------------------
//-------- Load up all the CARD images ------------------------------
//-------- Set up the width and height of a single CARD image  ------
//-------------------------------------------------------------------
    private void loadAllCardImagesAndSetUpCardDimensions() {
        Dimension d;
        
        CardImageLoadUp.loadAndSetUpAllCardImages(this);
        d = CardImageLoadUp.getDimensionOfSingleCard();
        cardWidth = d.width;
        cardHeight = d.height;    
    }
//-------------------------------------------------------------------
//-------- Returns a JPanel of components ---------------------------
//-------------------------------------------------------------------
    private void addPanelsOfComponents() {
        JPanel panel = getPanelOfComponents();
        add(panel);
    }
    private JPanel getPanelOfComponents() {
        JPanel panel = new JPanel();
        
        toStringT = new JTextField(30);
        toStringT.setFont(new Font("COURIER", Font.BOLD, 20));
        panel.add(toStringT);
        
        return panel;
    }
}

