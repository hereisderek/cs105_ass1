/*
 * Class which loads images
 * using a MediaTracker
 *
 * author  Adriana Ferraro
 * version   S2, 2012
 */

import java.awt.*; 
import javax.swing.*;


public class CardImageLoadUp {  
    private static final int CARDS_IN_EACH_SUIT = A1Constants.CARDS_IN_EACH_SUIT;
    private static final int TOTAL_NUMBER_OF_CARDS = A1Constants.TOTAL_NUMBER_OF_CARDS; 
    
    private static String[] cardImageFileNames;
    private static boolean imagesHaveBeenLoaded; 
    private static Dimension singleCardDimension; 
    
    public static boolean getImagesHaveBeenLoaded() {
        return imagesHaveBeenLoaded;
    }
    
    public static Dimension getDimensionOfSingleCard() {
        return singleCardDimension;
    } 
    
    public static Image getSingleCardImage(int index) {
        String curDir = System.getProperty("user.dir");
        String pathName = curDir + "/classic_cards/";
        //System.out.println("Current work dir is: " + curDir);
        Image pic = Toolkit.getDefaultToolkit().getImage(pathName + cardImageFileNames[index]);
        return pic;
    }
    
    public static Image getFaceDownCardImage() {
        String curDir = System.getProperty("user.dir");
        String pathName = curDir + "/classic_cards/";
        
        Image pic = Toolkit.getDefaultToolkit().getImage(pathName + cardImageFileNames[cardImageFileNames.length - 1]);
        return pic;
    } 
//-------------------------------------------------------------------
//-------- Loads all the cards into the MediaTracker ----------------
//------------------------------------------------------------------- 
    public static void loadAndSetUpAllCardImages(JComponent theJPanelInstance) {  
        imagesHaveBeenLoaded = false;
        
        MediaTracker tracker = new MediaTracker(theJPanelInstance);
        
        cardImageFileNames = getArrayOfCardFileNames();
        loadAllTheCardImagesFromFileNames(cardImageFileNames, tracker);
        
        String curDir = System.getProperty("user.dir");
        String pathName = curDir + "/classic_cards/";
        
        Image singlePic = Toolkit.getDefaultToolkit().getImage(pathName + cardImageFileNames[0]);
        singleCardDimension = new Dimension(singlePic.getWidth(theJPanelInstance), singlePic.getHeight(theJPanelInstance));
    }
//-------------------------------------------------------------------
//-------- Private helper methods which load the  -------------------
//-------- card images using a MediaTracker object ------------------
//------------------------------------------------------------------- 
    private static String[] getArrayOfCardFileNames() { 
        String[] cardImageFileNames = new String[TOTAL_NUMBER_OF_CARDS + 1];
        int suitNum = 0;
        int cardValue = 0;
        
        for (int i=0; i < cardImageFileNames.length; i++) {
            if( cardValue < 10 ) {
                cardImageFileNames[i] = new String("c" + suitNum + "_0" + cardValue + ".png");
            } else {
                cardImageFileNames[i] = new String("c" + suitNum + "_" + cardValue + ".png");
            }
            
            if( cardValue == CARDS_IN_EACH_SUIT - 1) {
                suitNum++;
            }
            
            cardValue = (cardValue + 1) % CARDS_IN_EACH_SUIT;   
        }
        
        cardImageFileNames[TOTAL_NUMBER_OF_CARDS] = new String("faceDown.png");
        
        return cardImageFileNames;  
    }
    
    private static void loadAllTheCardImagesFromFileNames(String[] cardImageFileNames, MediaTracker tracker) {
        String curDir = System.getProperty("user.dir");
        Image pic;
        
        for (int i=0; i < cardImageFileNames.length; i++) {
            pic = Toolkit.getDefaultToolkit().getImage(curDir + "/classic_cards/" + cardImageFileNames[i]);
            tracker.addImage(pic, i);
        }
        
        try {
            tracker.waitForAll();
            imagesHaveBeenLoaded = true;   
        } catch(java.lang.InterruptedException e) {
            
        }  
    }
} 


/*
 The Toolkit is a class in the java.awt package that 
 provides various resources and tools for the display system.
 
 One of the Toolkit methods is getImage().
 
 Before calling getImage(), one must have a reference to the Toolkit 
 instance in use. The static method Toolkit.getDefaultToolkit() 
 returns a reference to that Toolkit.
 
 
 
 Have you ever faced a problem such that even after loading your 
 images using Toolkit.getImage(), the picture you loaded is not 
 displayed when you call drawImage()? This is a common problem due 
 to the fact that Toolkit.getImage() returns immediately, even before 
 the image name that was passed is loaded. This is an optimization done 
 by java since the getImage() action will result in an I/O to disk and 
 blocking on such an I/O will slow things down terribly. To get past 
 the problem described above, you will need to use a MediaTracker 
 to track the images that you need to display.
 
 The MediaTracker class is a utility class to track the status 
 of a number of media objects.
 
 The JPanel accepts a list of image names and loads the images before 
 it is fully constructed. In the paintComponent method, it renders 
 these images. The main thing to look for is the usage of the MediaTracker 
 class. The waitForAll() method is used to make sure that 
 all images are loaded before rendering.
 
 */
