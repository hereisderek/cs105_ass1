/*
 * Purpose: stores the constants for
 * the A1 game.
 * 
 * Author: Adriana Ferraro
 * Date: S2 2012
 */

import java.awt.*;

public class A1Constants { 
//-------------------------------------------------------------------
//-------- Width and height of the JFrame ---------------------------
//-------------------------------------------------------------------
    public static final int JFRAME_AREA_WIDTH = 850;
    public static final int JFRAME_AREA_HEIGHT = 600;
    
//-------------------------------------------------------------------
//-------- Position for the game score and feedback -----------------
//------------------------------------------------------------------- 
    public static final Point SCORE_POSITION = new Point(10, 40);
    
//-------------------------------------------------------------------
//-------- Left position for the card display -----------------------
//-------------------------------------------------------------------
    public static final int CARD_DISPLAY_LEFT = 30;
    
//-------------------------------------------------------------------
//-------- Number of cards in the pack and number -------------------
//-------- of cards in each suit ------------------------------------
//-------------------------------------------------------------------  
    public static final int TOTAL_NUMBER_OF_CARDS = 52;
    public static final int CARDS_IN_EACH_SUIT = 13;
    
//-------------------------------------------------------------------
//-------- Number of columns and rows in the card display -----------
//------------------------------------------------------------------- 
    public static final int NUMBER_OF_ROWS = 5;
    public static final int NUMBER_OF_COLS = 9; 
    
//-------------------------------------------------------------------
//-------- A number to represent each suit in the pack --------------
//-------------------------------------------------------------------
    public static final int CLUBS = 0;
    public static final int DIAMONDS = CLUBS + 1;
    public static final int HEARTS = DIAMONDS + 1;
    public static final int SPADES = HEARTS + 1;
}
