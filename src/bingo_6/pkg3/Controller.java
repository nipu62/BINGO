package bingo_6.pkg3;

import InterfaceObserver.Game_Values;
import InterfaceObserver.Observer;
import InterfaceObserver.Subject;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import static InterfaceObserver.Game_Values.myTurn;
import static InterfaceObserver.Game_Values.playersScore;
import static InterfaceObserver.Game_Values.randomValues;
import static InterfaceObserver.Game_Values.updateDiagonalValues;
import static InterfaceObserver.Game_Values.updatedColumnValues;
import static InterfaceObserver.Game_Values.updatedRowValues;
import static InterfaceObserver.Game_Values.valuesForColumnTrack;
import static InterfaceObserver.Game_Values.valuesForRowTrack;
import java.io.IOException;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

public class Controller implements Game_Values,Subject{
    BingoGui b;
    Observer observer;
    Controller c;
    
    public Controller ()
    {

        generateButtons();
         initBoardValues();
         b =  new BingoGui();
         regesterObserver(b);
        // turnChangeIndicator();
         b.turnChangerGui();
    }
    public Controller(String str)
    {
        
    }

    public static void generateButtons()
    {
        int n;
        boolean visited[] = new boolean[25];
        for (int i = 0;i < 25;i++)
        {
            visited[i] = false;
        }
        Random random = new Random();
        for (int i = 0;i < 25;i++)
        {
            while ( visited[n = random.nextInt(25)] == true)
            {
            }
                randomValues[i] =n  + 1;
                visited[n] = true;
        }
    }
    
    public static void  initBoardValues()
    {
        for (int i = 0;i < 5;i++)
            {
                updatedRowValues[i] = 5;
                updatedColumnValues[i] = 5;
            }
        for (int i = 0;i < 2;i++)
        {
            updateDiagonalValues[i] = 5;
            updateDiagonalValues[i] = 5;
            playersScore[i] = 0;
            playersScore[i] = 0;
        }
        Bingo_Complete[0] = false;
        GameOver[0] = false;
 
        
        
    }
    
    public static void updatePlayerScore(int playerNum)
    {
        if (cont[0] == false)
            return;
        //updating score gui
        
        
        //----------
        for (int i = 0;i < 5;i++){
            //System.out.println("updated values " +" "+ updatedColumnValues[i]+" "+updatedRowValues[i]);
        if (updatedColumnValues[i] == 0)
        {
            //for test
            //System.out.println(i +"row full");
            updatedColumnValues[i] = -1;
            playersScore[playerNum]++;
        }
        if (updatedRowValues[i] == 0)
        {
            //System.out.println(i +"column full");
            updatedRowValues[i] = -1;
            playersScore[playerNum]++;
        }
        if (i < 2)
        {
            if (updateDiagonalValues[i] == 0)
            {
                 //System.out.println( "d1 full");
                 playersScore[playerNum]++;
                 updateDiagonalValues[i] = -1;
            }
            if (updateDiagonalValues[i] == 0)
            {
                //System.out.println("d2 full");
                playersScore[playerNum]++;
                updateDiagonalValues[i] = -1;
            }
            
        }
            
        
      }
        
        System.out.println("player "+playerNames[playerNum]+ " score"+playersScore[playerNum]);
        
    }
    
    
    //for testing purpose
    

    void showMessage(String message) {
        b.chatAppend(message);
        
        
    }

    public void updateSCore(String substring) {
        
      int score = Integer.parseInt(substring);
      playersScore[1] = score;

      notifyObserver();
  
    }
    

    void handleButtonMove(int value) {
        
        int i = valuesForRowTrack[value];
        int j = valuesForColumnTrack[value];

        try {
            b.changeButtonState(i,j);

        } catch (UnsupportedAudioFileException ex) {
            Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
        } catch (LineUnavailableException ex) {
            Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    

    public void turnChangeIndicator()
    {
        b.turnChangerGui();
    }

    @Override
    public void  regesterObserver(Observer observer) {
        this.observer = observer;
        
    }

    @Override
    public void removeObserver(Observer observer) {
        
    }

    @Override
    public void notifyObserver() {
        observer.updateScore_turn();
    }

    
    
}
