/*
 * Comment
*/

import java.io.*;
import java.util.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class A1JPanel extends JPanel implements MouseListener, KeyListener {
    public static final int CARDS_IN_EACH_SUIT = A1Constants.CARDS_IN_EACH_SUIT;
    private static final int TOTAL_NUMBER_OF_CARDS = A1Constants.TOTAL_NUMBER_OF_CARDS;
    
    private static final int NUMBER_OF_ROWS = A1Constants.NUMBER_OF_ROWS;
    private static final int NUMBER_OF_COLS = A1Constants.NUMBER_OF_COLS;
    
    private static final Color BACKGROUND_COLOUR = new Color(233, 0, 211); 
    private static final int CARD_DISPLAY_LEFT = A1Constants.CARD_DISPLAY_LEFT;
    
    private ArrayList<Card> cardStack;
    private Card[][] cards;
    
    private Card userCard;
    //card which is face down
    //and covers the card stack
    private Card aFaceDownCard;
    
    private int cardWidth;
    private int cardHeight;
    
    private int numberRemoved; 
    
    private boolean noMoreTableCards;
    private boolean noMoreAvailableMoves;
//--------------------------------------------------------------------- 
//--------------------------------------------------------------------- 
// Stage 9  (10 Marks) variables used for scoring the game
//--------------------------------------------------------------------- 
//--------------------------------------------------------------------- 
    private int userScore;
    private int pointsToAdd;
    
    
    public A1JPanel() {
        setBackground(BACKGROUND_COLOUR);
        loadAllCardImagesAndSetUpCardDimensions();
        
        addKeyListener(this);
        addMouseListener(this);
        
        reset();
    }
    
    private void reset() {
        int randomPosition;
        cardStack = createTheFullPack();
        cards = getRandomTableCards(cardStack);
        
        setUpVisibleRowOfCards(cards);
        
        randomPosition = (int) (Math.random() * cardStack.size());
        userCard = cardStack.remove(randomPosition);
        setUpCardPosition(userCard, cardWidth * 8, cardHeight * 5, true);
        
        aFaceDownCard = new Card(0, 0);
        setUpCardPosition(aFaceDownCard, cardWidth * 6, cardHeight * 5, false);
        
        numberRemoved = 0; 
        
        noMoreTableCards = false;
        noMoreAvailableMoves = false;
        
    }
    
    private void setUpCardPosition(Card card, int x, int y, boolean isFaceUp) {
        card.setCardArea(x, y, cardWidth, cardHeight);
        card.setIsFaceUp(isFaceUp);  
    }
//--------------------------------------------------------------------- 
// Handle KeyEvents
// Stage 5  (6 Marks), 
// Stage 10 (2 Marks) 
//and 
// part of Stage 11 (8 Marks)
//--------------------------------------------------------------------- 
    public void keyPressed(KeyEvent e) {
    	// stage 5
    	if(e.getKeyChar() == 'N' || e.getKeyChar() == 'n')
    		reset();
    	
    	
        repaint();
    }
    
    public void keyReleased(KeyEvent e) {}
    public void keyTyped(KeyEvent e) {}
//--------------------------------------------------------------------- 
// Handle MouseEvents
//--------------------------------------------------------------------- 
    public void mousePressed(MouseEvent e) {  
        Card selectedCard;
        int selectedCardRow;
        int selectedCardCol;
        int randomPosition;
        
        if (noMoreTableCards || noMoreAvailableMoves) {
            return;
        }
        
        Point pressPt = e.getPoint();
        Point rowColOfSelectedCard = getRowColOfSelectedCard(pressPt);
        
        
        if (rowColOfSelectedCard != null) {
            selectedCardRow = rowColOfSelectedCard.x;
            selectedCardCol = rowColOfSelectedCard.y;
            selectedCard = cards[selectedCardRow][selectedCardCol];
            
            if (haveValueDifferenceOfOne(userCard, selectedCard)) {
                userCard.setValue(selectedCard.getValue());
                userCard.setSuit(selectedCard.getSuit());
                
                selectedCard.setHasBeenRemoved(true);
                numberRemoved++;
                
                cards[selectedCardRow][selectedCardCol] = null;
                revealNeighbouringCards(selectedCard.getCardArea(), selectedCardRow);
            }      
        }
        
        if (cardStack.size() > 0 && packCardHasBeenPressed(pressPt)) {
            randomPosition = (int) (Math.random() * cardStack.size());
            userCard = cardStack.remove(randomPosition); 
            
            setUpCardPosition(userCard, cardWidth * 8, cardHeight * 5, true);
        }
        
        if (noMoreTableCards()) {
            noMoreTableCards = true;
        } else if (userIsStillAbleToWin(userCard) == false) {
            noMoreAvailableMoves = true;
        }
        
        repaint();
    }
    
    private boolean haveValueDifferenceOfOne(Card userCard, Card selectedCard) {
        int userValue = userCard.getValue();
        int selectedValue = selectedCard.getValue();
        int diff = Math.abs(userValue - selectedValue);
        
        return diff == 1 || diff == 12;
    }
    
    public void mouseClicked(MouseEvent e) {}
    public void mouseReleased(MouseEvent e) {}
    public void mouseEntered(MouseEvent e) {}
    public void mouseExited(MouseEvent e) {} 
//--------------------------------------------------------------------- 
// Stage 6 (8 Marks)
//--------------------------------------------------------------------- 
    private Point getRowColOfSelectedCard(Point pressPt) {
/*
Stage 6 (8 Marks)
*/
        for (int i = 0; i < cards.length; i++){
        	for (int j = 0; j < cards[i].length; j++){
        		if (cards[i][j] != null && !cards[i][j].getHasBeenRemoved() && cards[i][j].getIsFaceUp() && cards[i][j].pressPointIsInsideCard(pressPt)){
        			return new Point(i,j);
        		}
        	}
        }
        return null;  
    }
    
    private boolean packCardHasBeenPressed(Point pressPt) {
        if (aFaceDownCard.pressPointIsInsideCard(pressPt)) {
            return true;
        }
        
        return false;  
    }
//--------------------------------------------------------------------- 
// Note that before this method is executed the selected
// card has been removed from the 2D cards array, i.e., the  
// position where the selected card was in the cards array is null.
// Stage 7 (20 Marks)
//--------------------------------------------------------------------- 
    private void revealNeighbouringCards(Rectangle removedCardArea, int rowOfRemovedCard) {
        int previousRow = rowOfRemovedCard - 1;
        int nextRow = rowOfRemovedCard + 1;
/*
Stage 7 (20 Marks)
*/  
        int checkRow;
        switch (rowOfRemovedCard){
        case 1:
        	checkRowOfNeighbours(removedCardArea, checkRow = 0, rowOfRemovedCard); break;
        case 3:
        	checkRowOfNeighbours(removedCardArea, checkRow = 4, rowOfRemovedCard); break;
        case 2:
        	checkRowOfNeighbours(removedCardArea, checkRow = 1, rowOfRemovedCard); 
        	checkRowOfNeighbours(removedCardArea, checkRow = 3, rowOfRemovedCard); 
        	break;
        }
    }
    
    private void checkRowOfNeighbours(Rectangle removedCardArea, int rowCurrentlyChecking, int rowNumberOfRemovedCard) {   
/*
helper for Stage 7 (20 Marks)
*/
    	for(int i = 0; i < cards[rowCurrentlyChecking].length; i++){
    		if (cards[rowCurrentlyChecking][i] != null && cards[rowCurrentlyChecking][i].getCardArea().intersects(removedCardArea)){
    			if (hasNoIntersectingNeighbourInRow(cards[rowCurrentlyChecking][i].getCardArea(), rowNumberOfRemovedCard)){
    				cards[rowCurrentlyChecking][i].setIsFaceUp(true);
    			}
    		}
    	}
    }
    
    private boolean hasNoIntersectingNeighbourInRow(Rectangle areaOfCardToCheck, int rowNumberOfRemovedCard) {
        Rectangle areaToCheck;
        Card cardToCheck;
/*
helper for Stage 7 (20 Marks)
*/  
        for (int i = 0; i < cards[rowNumberOfRemovedCard].length; i ++)
        	if (cards[rowNumberOfRemovedCard][i] != null && !cards[rowNumberOfRemovedCard][i].getHasBeenRemoved() && cards[rowNumberOfRemovedCard][i].getCardArea().intersects(areaOfCardToCheck))
        		return false;
        
        return true;
    } 
//--------------------------------------------------------------------- 
// Stage 8 (6 Marks)
//--------------------------------------------------------------------- 
    private boolean noMoreTableCards() {
/*
Stage 8 (6 Marks)
*/
    	//TODO: i suck at this game, so no chance to test if this methos works or not. maybe some other day
        for (int i = 0; i < cards.length; i++)
        	for (int j = 0; j < cards[i].length; j++)
        		if (cards[i][j] != null && !cards[i][j].getHasBeenRemoved())
        			return false;

        return false;
    }
//-------------------------------------------------------------------
//-------- Draw all the CARD objects --------------------------------
//-------- Do not draw any null cards  ------------------------------
//--------------------------------------------------------------------- 
// Stage 4 (6 Marks)
//--------------------------------------------------------------------- 
//--------------------------------------------------------------------- 
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        drawTableCards(g);  
        drawRestOfJPanelDisplay(g);  
    }
    
    private void drawTableCards(Graphics g) {
        //the order in which rows are drawn
        //i.e., row 0, then row 4, then row 1, 
        //etc.,
        int[] orderToBeDrawn = {0, 4, 1, 3, 2};
/*
Stage 4 (6 Marks)
*/
        for (int i:orderToBeDrawn){
        	drawRowOfCards(g,i);
        }
    } 
    
    private void drawRowOfCards(Graphics g, int whichRow) {  
/*
helper method for Stage 4 (6 Marks)
*/
        for (int i = 0; i < cards[whichRow].length; i++){
        	// TODO i dont full understand getComponentPopupMenu, but eclipse did that part for me ^.^
        	if (cards[whichRow][i] != null)
        		//cards[whichRow][i].drawCard(g, getComponentPopupMenu());
        		cards[whichRow][i].drawCard(g, this);	// i'll just stick to my own version, although the formal one also works and i dont see the point
        	
        }
    }
    
    private void drawRestOfJPanelDisplay(Graphics g) {
        userCard.drawCard(g, this);
        int numberLeftInPack = cardStack.size();
        if (numberLeftInPack > 0) {
            aFaceDownCard.drawCard(g, this);
            drawNumberInsideCardArea(g, aFaceDownCard);   
        }
        
        drawGameInformation(g); 
    }
    
    private void drawNumberInsideCardArea(Graphics g, Card aFaceDownCard) {  
        Rectangle cardArea = aFaceDownCard.getCardArea();
        int numberLeftInPack = cardStack.size();
        g.setFont(new Font("Times", Font.BOLD, 48));
        if (numberLeftInPack < 10) {
            g.drawString("" + numberLeftInPack, cardArea.x + cardArea.width / 3, cardArea.y + cardArea.height * 2 / 3);   
        } else {
            g.drawString("" + numberLeftInPack, cardArea.x + cardArea.width / 6, cardArea.y + cardArea.height * 2 / 3);   
        }
    }
    
    private void drawGameInformation(Graphics g) {
        g.setFont(new Font("Times", Font.BOLD, 48));
        g.setColor(Color.LIGHT_GRAY);
        String scoreMessage = " Score: " + userScore;
        
        if (noMoreTableCards) {
            scoreMessage = "No more table cards! " + scoreMessage;  
        } else if (noMoreAvailableMoves) {
            scoreMessage = "No more available moves! " + scoreMessage; 
        } else {
            scoreMessage = "Cards removed: " + numberRemoved + scoreMessage; 
        }
        g.drawString(scoreMessage, A1Constants.SCORE_POSITION.x, A1Constants.SCORE_POSITION.y);
    }
//-------------------------------------------------------------------
//-------- The parameter is a 2D array of CARD objects --------------
//-------- The method sets up the middle row as being visible -------
//--------------------------------------------------------------------- 
// Stage 3 (4 Marks)
//--------------------------------------------------------------------- 
//--------------------------------------------------------------------- 
    private void setUpVisibleRowOfCards(Card[][] cards) {
/*
Stage 3 (4 Marks)
*/
        for(Card tempCard:cards[2]){
        	tempCard.setIsFaceUp(true);
        }
    }
//-------------------------------------------------------------------
//-------- Create a 2D array of CARD objects and --------------------
//--- the parameter ArrayList will contain the cards which remain ---
//---  in the pack after the table cards are randomly selected ------
//--------------------------------------------------------------------- 
// Stage 2 (8 Marks)
//--------------------------------------------------------------------- 
//---------------------------------------------------------------------
    private Card[][] getRandomTableCards(ArrayList<Card> cardStack) {
        final int[] NON_NULL_CARDS_IN_EACH_ROW = {4, 8, 9, 8, 4};
        Card card;
        int randomArraylistPosition;
        Card[][] displayCards = new Card[NUMBER_OF_ROWS][NUMBER_OF_COLS];  //need to create the 2D array space
/*
Stage 2 (8 Marks) 
*/  
        for (int i = 0; i < displayCards.length; i++){
        	for (int j = 0; j < NON_NULL_CARDS_IN_EACH_ROW[i]; j++){
        		displayCards[i][j] = (cardStack.remove(((int)(Math.random() * cardStack.size()))));
        		//int randomNum = (int)(Math.random() * cardStack.size());
        		//displayCards[i][j] = cardStack.remove(randomNum);
        		setupIndividualCardPosition(displayCards[i][j], i, j);
        	}
        }
        return displayCards;
    }
    
    private void setupIndividualCardPosition(Card card, int rowNumber, int colNumber) {
        final int CARD_DISPLAY_TOP = 60;
        final int DISPLAY_GAP = 6;
        
        int leftPositionForRow = getLeftPositionForRow(rowNumber);
        
        int y = CARD_DISPLAY_TOP + (cardHeight * 3 / 4) * rowNumber;
        int x = CARD_DISPLAY_LEFT + leftPositionForRow + (cardWidth + DISPLAY_GAP - 1) * colNumber;
        
        if (rowNumber == 0 || rowNumber == NUMBER_OF_ROWS - 1) {
            x = CARD_DISPLAY_LEFT + leftPositionForRow + (cardWidth + DISPLAY_GAP - 1) * 2 * colNumber;
        }
        
        card.setCardArea(x, y, cardWidth, cardHeight);
    }
    
    private int getLeftPositionForRow(int rowNumber) {
        if (rowNumber == 0 || rowNumber == NUMBER_OF_ROWS - 1) {
            return CARD_DISPLAY_LEFT + cardWidth;
        }
        if (rowNumber == 1 || rowNumber == NUMBER_OF_ROWS - 2) {
            return CARD_DISPLAY_LEFT + cardWidth / 2;
        } 
        
        return CARD_DISPLAY_LEFT; 
    }
//---------------------------------------------------------------------
//-------- Write To File ----------------------------------------------
//--------------------------------------------------------------------- 
// Write the current game information to the SavedGame.txt file
// private Card aFaceDownCard; doesn't need to be stored
//--------------------------------------------------------------------- 
//--------------------------------------------------------------------- 
    private void writeToFile() {
        String fileName = "SavedGame.txt";
        PrintWriter pW = null;
        Card card; 
        try {
            pW = new PrintWriter(fileName);
            pW.println(cardWidth);
            pW.println(cardHeight);
            pW.println(userCard.getCardStatusInformation());
            
            for (int i=0; i < NUMBER_OF_ROWS; i++) {
                for (int j=0; j < NUMBER_OF_COLS; j++) {
                    if (cards[i][j] == null) {
                        pW.println("null");
                    } else {
                        pW.println(cards[i][j].getCardStatusInformation());
                    }
                }
            }
            
            pW.println(cardStack.size());
            
            for (int i = 0; i < cardStack.size(); i++) {
                card = cardStack.get(i);
                pW.println(card.getCardStatusInformation());
            }
            
            pW.println(userScore);
            pW.println(pointsToAdd);
            pW.println(noMoreTableCards);
            pW.println(noMoreAvailableMoves);
            pW.println(numberRemoved);
            
            pW.close();
        } catch(IOException e) {
            System.out.println("Error saving game to " + fileName);
        }
    }
//---------------------------------------------------------------------
//-------- Load From File ---------------------------------------------
//--------------------------------------------------------------------- 
// The createACard helper method has been defined and
// is useful when creating a Card object from the 
// information read from the file.
// Stage 11  (8 Marks)
//--------------------------------------------------------------------- 
//--------------------------------------------------------------------- 
    public void loadFromFile() {
        String fileName = "SavedGame.txt";
        Scanner scan = null;
        Card card; 
        String cardInfo;
        int value, suitIndex, xPos, yPos, cardStackSize;
        boolean hasBeenGuessed;
/* 
Stage 11  (8 Marks)
*/ 
        
        
        
        
        
        
    }
    
    private Card createACard(String info, int width, int height) {
        Card card;
        int suit, value, x, y;
        boolean removed, faceUp;
        
        Scanner scanInfo = new Scanner(info);
        value = scanInfo.nextInt();
        suit = scanInfo.nextInt();
        x = scanInfo.nextInt();
        y = scanInfo.nextInt();
        removed = scanInfo.nextBoolean();
        faceUp = scanInfo.nextBoolean();
        
        card = new Card(value, suit);
        
        card.setCardArea(x, y, width, height);
        card.setIsFaceUp(faceUp);
        card.setHasBeenRemoved(removed);
        
        return card;
    }
//-------------------------------------------------------------------
//------ Create an ArrayList of a full pack of CARD objects ---------
//-------------------------------------------------------------------
    private ArrayList<Card> createTheFullPack() {  
        ArrayList<Card> theCards = new ArrayList <Card> (TOTAL_NUMBER_OF_CARDS);
        int suitNum = A1Constants.CLUBS;
        int cardValue = 0;
        
        for (int i = 0; i < TOTAL_NUMBER_OF_CARDS; i++) {
            theCards.add(new Card(cardValue, suitNum));
            
            if( cardValue >= CARDS_IN_EACH_SUIT - 1) {
                suitNum++;
            }
            
            cardValue = (cardValue + 1) % (CARDS_IN_EACH_SUIT);  
        }
        
        return theCards;
    }
//-------------------------------------------------------------------
//-------- Load all the CARD images ---------------------------------
//-- Set up the width and height instance variables  ----------------
//-------------------------------------------------------------------
    private void loadAllCardImagesAndSetUpCardDimensions() {
        CardImageLoadUp.loadAndSetUpAllCardImages(this);
        
        Dimension d = CardImageLoadUp.getDimensionOfSingleCard();
        cardWidth = d.width;
        cardHeight = d.height;    
    }
//--------------------------------------------------------------------- 
// Checks if there are any more moves which can still be made
//--------------------------------------------------------------------- -
    private boolean userIsStillAbleToWin(Card userCard) {
        if (cardStack.size() > 0) {
            return true;
        }
        
        int userCardValue = userCard.getValue();
        int cardValue;
        int diff;
        
        for (int i = 0; i < NUMBER_OF_ROWS; i++) {
            for (int j = 0; j < NUMBER_OF_COLS; j++) {       
                if (cards[i][j] != null) {
                    cardValue = cards[i][j].getValue();
                    diff = Math.abs(cardValue - userCardValue);
                    if (diff == 1 || diff == 12) {
                        return true;
                    }     
                }      
            }
        }
        
        return false;
    }
}



