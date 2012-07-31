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
        // 31,july, part1
    	hasBeenRemoved = false;
    	isFaceUp = false;
    	cardArea = new Rectangle(0, 0, 0, 0);
    }  
//-------------------------------------------------------------------
//-------- Accessor and mutator methods -----------------------------
//-------------------------------------------------------------------  
    public int getSuit() {    
        return suit; //You may need to change this   
    }
    
    public int getValue() {  
        return value; //You may need to change this    
    }
    
    public void setSuit(int suit) {  
        this.suit = suit;
    }
    
    public void setValue(int value) {  
        this.value= value;
    }
    
    public boolean getHasBeenRemoved() {  
        return hasBeenRemoved; //You may need to change this    
    }
    
    public void setHasBeenRemoved(boolean removed) {  
        this.hasBeenRemoved = removed;
    }
    
    public boolean getIsFaceUp() {  
        return isFaceUp; //You may need to change this    
    }
    
    public void setIsFaceUp(boolean faceUp) {  
        this.isFaceUp = faceUp;
    }
    
    public void setCardArea(int x, int y, int w, int h) {  
        cardArea = new Rectangle(x, y, w, h);
    }
    
    public Rectangle getCardArea() {  
        return cardArea; //You may need to change this    
    }
//-------------------------------------------------------------------
//-------- Returns true if the parameter Point object ---------------
//-------- is inside the Card area. --------------------------------
//-------------------------------------------------------------------  
    public boolean pressPointIsInsideCard(Point pressPt) {  
        // part 1, seems this method should be named: checkPressPoint()
    	return (cardArea.contains(pressPt) && !hasBeenRemoved ? true : false);
        //return false;  //You may need to change this   
    }
//-------------------------------------------------------------------
//-------- Get String describing the card suit and value ------------
//-------------------------------------------------------------------
    public String getCardStatusInformation() { 
        String cardInfo = "";
        // card value, card suit, x-position of the card, y-position  of the card, the card has been removed boolean and, lastly, the card is face up boolean
        cardInfo = value + " " + suit + " " + cardArea.x + " " + cardArea.y + " " + hasBeenRemoved + " " + isFaceUp;
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
