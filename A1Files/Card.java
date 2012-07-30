/*
 * Comment
 */

/*
 8 Marks
 */

import java.awt.*; 
import javax.swing.*;


public class Card {    
    private int suit;
    private int value;
    private Rectangle cardArea;
    private boolean hasBeenRemoved;
    private boolean isFaceUp;
    
    public Card(int value, int suit) {  
        
    }  
//-------------------------------------------------------------------
//-------- Accessor and mutator methods -----------------------------
//-------------------------------------------------------------------  
    public int getSuit() {    
        return -1; //You may need to change this   
    }
    
    public int getValue() {  
        return -1; //You may need to change this    
    }
    
    public void setSuit(int suit) {  
        
    }
    
    public void setValue(int value) {  
        
    }
    
    public boolean getHasBeenRemoved() {  
        return false; //You may need to change this    
    }
    
    public void setHasBeenRemoved(boolean removed) {  
        
    }
    
    public boolean getIsFaceUp() {  
        return false; //You may need to change this    
    }
    
    public void setIsFaceUp(boolean faceUp) {  
        
    }
    
    public void setCardArea(int x, int y, int w, int h) {  
        
    }
    
    public Rectangle getCardArea() {  
        return null; //You may need to change this    
    }
//-------------------------------------------------------------------
//-------- Returns true if the parameter Point object ---------------
//-------- is inside the Card area. --------------------------------
//-------------------------------------------------------------------  
    public boolean pressPointIsInsideCard(Point pressPt) {  
        
        return false;  //You may need to change this   
    }
//-------------------------------------------------------------------
//-------- Get String describing the card suit and value ------------
//-------------------------------------------------------------------
    public String getCardStatusInformation() { 
        String cardInfo = "";
        
        return cardInfo;
    }
//-------------------------------------------------------------------
//-------- Draw the Card object. ------------------------------------
//-------------------------------------------------------------------  
    public void drawCard(Graphics g, JComponent theJPanelInstance) {  
        Image cardPic;
        int fileNumber;
        
        if (hasBeenRemoved) {
            return;
        } 
        
        if (isFaceUp) {
            fileNumber = suit * A1JPanel.CARDS_IN_EACH_SUIT + value;
            cardPic = CardImageLoadUp.getSingleCardImage(fileNumber);   
        } else {
            cardPic = CardImageLoadUp.getFaceDownCardImage();
        }
        
        
        g.drawImage(cardPic, cardArea.x, cardArea.y, theJPanelInstance);    
    }  
//-------------------------------------------------------------------
//-------- Get String describing the card suit and value ------------
//-------------------------------------------------------------------
    public String toString() { 
        final String[] SUITS = {"CLUBS", "DIAMONDS", "HEARTS", "SPADES"};
        if (value == 0) {
            return "A" + " " + SUITS[suit];
        } else if (value == 12) {
            return "K" + " " + SUITS[suit];
        } else if (value == 11) {
            return "Q" + " " + SUITS[suit];
        } else if (value == 10) {
            return "J" + " " + SUITS[suit];
        }
        
        return (value + 1)  + " " + SUITS[suit];
    } 
} 
