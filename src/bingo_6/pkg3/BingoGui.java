/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bingo_6.pkg3;



import InterfaceObserver.Game_Values;
import InterfaceObserver.Observer;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import static InterfaceObserver.Game_Values.isFirstPlayer;
import static InterfaceObserver.Game_Values.myTurn;
import static InterfaceObserver.Game_Values.playerNames;
import static InterfaceObserver.Game_Values.playersScore;
import static InterfaceObserver.Game_Values.randomValues;
import static InterfaceObserver.Game_Values.updateDiagonalValues;
import static InterfaceObserver.Game_Values.updatedColumnValues;
import static InterfaceObserver.Game_Values.updatedRowValues;
import static InterfaceObserver.Game_Values.valuesForColumnTrack;
import static InterfaceObserver.Game_Values.valuesForRowTrack;
import java.awt.Graphics;
import java.awt.Image;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.JOptionPane;

public class BingoGui implements Game_Values,Observer{
    private JPanel leftPanel;
    private JPanel midPanel;
    private JPanel rightPanel;
    private JFrame mainFrame;
    
    
    //--component for left panel
    private JLabel playersName[];
    private JLabel playersScoreLabel[];
    //private JLabel player2Name;
    private JLabel infoLabel;
   // private JTextField player1Score;
   // private JTextField player2Score;
     private JLabel player1Score;
     private JLabel player2Score;
     private JLabel playerStatsLabel[];
     
    
    //--component for mid panel
    private JLabel midInfo;
    private JPanel mid1Panel;
    private JPanel mid2Panel;
    private JPanel mid3Panel;
    private JButton[][] buttons;
    
    private JLabel turnIndicator1;
    private JLabel turnIndicator2;
    private JLabel tipText; 
    
    //--component for rightPanel
    private JLabel rightPanelInfo;
    private JTextArea chatArea;
    private JButton sendButton;
    public JTextField chatField;
    private JTextArea chatArea2;
    
    //--xheck foe game end
    int j = 1;
    int k = 1;
    
    //--constructor
    
    public BingoGui()
    {
        setFrame();
        setLeftPanel();
        try {
            setMidPanel();
        } catch (IOException ex) {
            Logger.getLogger(BingoGui.class.getName()).log(Level.SEVERE, null, ex);
        }
        setRightPanel();
        
        //for test
       // turnChangerGui();
        
        
        
        
        
    }
    public BingoGui(String str)
    {
        
    }

    private void setMidPanel() throws IOException {
        buttons = new JButton[5][5];
        mid1Panel = new JPanel(new FlowLayout());
        mid1Panel.setBackground(Color.red);
        midPanel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        
        //---setting panel1
        
        Image image = ImageIO.read(new File("Bingo_menu.jpeg"));
        mid1Panel = new JPanel()
                {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.drawImage(image, 0, 0,this.getWidth(), this.getHeight(), null);
            }
        };
        midInfo = new JLabel("B I N G O");
        midInfo.setSize(50, 10);
        mid1Panel.add(midInfo);
        gbc.gridy = 0;
        gbc.gridheight = 1;
        gbc.weightx = 1;
        gbc.weighty = 1;
        gbc.fill = GridBagConstraints.BOTH;
        midPanel.add(mid1Panel,gbc);
        mid1Panel.setBackground(Color.red);
        
       //---setting panel 2
        mid2Panel = new JPanel();
        mid2Panel.setLayout(new GridLayout(5, 5));
        mid2Panel.setBackground(Color.darkGray);
        //--adding button to grid
        int k = 0;
        for (int i = 0;i < 5;i++)
            for (int j = 0;j < 5;j++)
        {
            buttons[i][j] = new JButton();
            buttons[i][j].setBorder(BorderFactory.createLineBorder(Color.blue));
            buttons[i][j].setForeground(Color.black);
            buttons[i][j].setBackground(Color.yellow);
            buttons[i][j].setText( Integer.toString(randomValues[k]));
            //--for tracking randomValues with buttons
            
            valuesForColumnTrack[randomValues[k]] = j;
            //--for row values
            valuesForRowTrack[randomValues[k]] = i;
            //buttons[i].setBorder(null);
            buttons[i][j].setFont(new Font("Arial", Font.PLAIN, 20));
            buttons[i][j].setPreferredSize(new Dimension(60, 60));
            buttons[i][j].setMaximumSize(new Dimension(70, 70));
            buttons[i][j].addActionListener(new bingoButtonListener(i,j,randomValues[k]));
            //System.out.println("value "+randomValues[k]);
            //System.out.println("valus in "+ i + " " +j + " is "+ randomValues[k]);
            //System.out.println("value "+ randomValues[k] +" placed in " +valuesForRowTrack[randomValues[k]] +" " + valuesForColumnTrack[randomValues[k]]);
            k++;
            mid2Panel.add(buttons[i][j]);
            
        }
        gbc.gridy = 1;
        gbc.gridheight = 5;
        gbc.weightx = 2;
        gbc.weighty = 2;
        gbc.fill = GridBagConstraints.CENTER;
        midPanel.add(mid2Panel,gbc);
        
        //--setting Panel 3---need to update
        mid3Panel = new JPanel();
        gbc.gridy = 6;
        gbc.gridheight = 2;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = 1.5;
        gbc.weighty = 1.5;
        
        midPanel.add(mid3Panel,gbc);
        
        mid3Panel.setLayout(new BorderLayout());
        turnIndicator1 = new JLabel("   yours turn");
        turnIndicator1.setFont(new Font("Arial", Font.BOLD, 15));
        turnIndicator1.setSize(150, 50);
        mid3Panel.add(turnIndicator1,BorderLayout.LINE_START);
        turnIndicator2 = new JLabel("opponent's turn   ");
        turnIndicator2.setFont(new Font("Arial", Font.BOLD, 15));
        turnIndicator2.setSize(150, 50);
        mid3Panel.add(turnIndicator2,BorderLayout.LINE_END);
        tipText = new JLabel("                            N.B: in oppnent's turn ,you can only send message");
        mid3Panel.add(tipText,BorderLayout.PAGE_END);
        mid3Panel.setBackground(Color.cyan);
        
        mainFrame.setVisible(true);
        
        
        
    }

    private void setLeftPanel() {
        leftPanel.setBackground(Color.black);
        playersName = new JLabel[2];
        playersScoreLabel = new JLabel[2];
        
        
        leftPanel.setLayout(new GridBagLayout());
        GridBagConstraints gbc =  new GridBagConstraints();
        gbc.insets = new Insets(15, 15, 15, 15);
        //player1Name.setSize(200, 100);
        playersName[0] = new JLabel(playerNames[0]);
        playersName[0].setForeground(Color.white);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridheight = 1;
        gbc.gridwidth = 1;
        gbc.weightx = 1;
        gbc.weighty = 1;
        //gbc.fill = GridBagConstraints.HORIZONTAL;
        //gbc.anchor = GridBagConstraints.CENTER;
        leftPanel.add(playersName[0]);
        
        
        playersScoreLabel[0] = new JLabel("");
        playersScoreLabel[0].setForeground(Color.green);
        playersScoreLabel[0].setFont(new Font("Arial", Font.BOLD, 20));
        gbc.insets = new Insets(15, 15, 15, 15);
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.gridheight = 1;
        gbc.gridwidth = 1;
        gbc.weightx = 1;
        gbc.weighty = 1;
        //gbc.fill = GridBagConstraints.HORIZONTAL;
        leftPanel.add(playersScoreLabel[0],gbc);
        
        playersName[1] =  new JLabel(playerNames[1]);
        playersName[1].setForeground(Color.white);
        gbc.insets = new Insets(15, 15, 15, 15);
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridheight = 1;
        gbc.gridwidth = 1;
        gbc.weightx = 1;
        gbc.weighty = 1;
        //gbc.anchor = GridBagConstraints.FIRST_LINE_START;
        //gbc.fill = GridBagConstraints.HORIZONTAL;
        leftPanel.add(playersName[1],gbc);
        
        playersScoreLabel[1] = new JLabel("");
        playersScoreLabel[1].setForeground(Color.red);
        playersScoreLabel[1].setFont(new Font("Arial", Font.BOLD, 20));
        gbc.insets = new Insets(15, 15, 15, 15);
        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.gridheight = 2;
        gbc.gridwidth = 2;
        gbc.weightx = 1;
        gbc.weighty = 1;
        //gbc.anchor = GridBagConstraints.BOTH;
        gbc.fill = GridBagConstraints.BOTH;
        
        leftPanel.add(playersScoreLabel[1],gbc);
        
        //---starts from here
        playerStatsLabel = new JLabel[6];
        
        playerStatsLabel[0] = new JLabel(playerNames[0] + " :");
        gbc.gridx  = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 3;
        gbc.weightx = 1;
        gbc.weighty = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        //leftPanel.add(playerStatsLabel[0],gbc);
        playerStatsLabel[0].setForeground(Color.white);
        playerStatsLabel[0].setFont(new Font("Arial", Font.BOLD, 20));
        
        playerStatsLabel[1] = new JLabel("P/W/L :");
        gbc.gridx  = 0;
        gbc.gridy = 6;
        gbc.weightx = 1;
        gbc.weighty = 1;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.anchor = GridBagConstraints.CENTER;
        //leftPanel.add(playerStatsLabel[1],gbc);
        playerStatsLabel[1].setForeground(Color.white);
        //playerStatsLabel[1].setFont(new Font("Arial", Font.BOLD, 20));
        
        playerStatsLabel[2] = new JLabel("1");
        gbc.gridx  = 1;
        gbc.gridy = 6;
        gbc.weightx = 1;
        gbc.weighty = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        playerStatsLabel[2].setForeground(Color.white);
        playerStatsLabel[2].setFont(new Font("Arial", Font.BOLD, 20));
        //leftPanel.add(playerStatsLabel[2],gbc);
        
        playerStatsLabel[3] = new JLabel("2");
        gbc.gridx  = 2;
        gbc.gridy = 6;
        gbc.weightx = 1;
        gbc.weighty = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        playerStatsLabel[3].setForeground(Color.white);
        playerStatsLabel[3].setFont(new Font("Arial", Font.BOLD, 20));
        //leftPanel.add(playerStatsLabel[3],gbc);
        
        playerStatsLabel[4] = new JLabel("1");
        gbc.gridx  = 3;
        gbc.gridy = 6;
        gbc.weightx = 1;
        gbc.weighty = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        playerStatsLabel[4].setForeground(Color.white);
        playerStatsLabel[4].setFont(new Font("Arial", Font.BOLD, 20));
        //leftPanel.add(playerStatsLabel[4],gbc);
        
        
        mainFrame.setVisible(true);
        
        
        
        
        
    }

    private void setRightPanel() {
        rightPanelInfo = new JLabel("chat box ! ");
        //rightPanel = new JPanel();
        rightPanel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.NORTH;
        gbc.weightx = 1;
        gbc.weighty = 1;
        rightPanel.add(rightPanelInfo,gbc);
        
        chatArea = new JTextArea();
        chatArea.setBorder(BorderFactory.createLineBorder(Color.blue));
        chatArea.setEditable(false);
        gbc.gridy = 1;
        gbc.gridheight = 5;
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.fill = GridBagConstraints.BOTH;
        //gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.weightx = 3;
        gbc.weighty = 3;
        rightPanel.add(chatArea,gbc);
        
        
        chatField = new JTextField();
        chatField.setSize(100, 100);
        //chatArea2 = new JTextArea();
        gbc.gridy = 6;
        gbc.weightx = 1;
        gbc.weighty = 1;
        gbc.gridheight = 2;
        //gbc.gridwidth = 3;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        rightPanel.add(chatField,gbc);
        chatField.addActionListener(new chatFieldActionListener());
        
        sendButton = new JButton();
        sendButton.setText("send");
        gbc.gridx = 1;
        gbc.gridy = 7;
        gbc.gridheight = 1;
        gbc.anchor = GridBagConstraints.LAST_LINE_END;
        sendButton.addActionListener(new sendButtonActionListener());
        rightPanel.add(sendButton,gbc);
        
        mainFrame.setVisible(true);
       
        
        
        
    }

    private void setFrame() {
        mainFrame = new JFrame("BINGO !");
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.setSize(800, 600);
        mainFrame.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        
        leftPanel = new JPanel();
        leftPanel.setBackground(Color.black);
        gbc.gridx = 1;
        gbc.gridy = GridBagConstraints.REMAINDER;
        gbc.weightx = 1;
        gbc.weighty = 1;
        //gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.BOTH;
        mainFrame.add(leftPanel,gbc);
        
        
        midPanel = new JPanel();
        midPanel.setBackground(Color.darkGray);
        gbc.gridx = 2;
        gbc.gridy = GridBagConstraints.REMAINDER;
        gbc.weightx = 2;
        gbc.weighty = 2;
        gbc.gridwidth = 5;
        gbc.fill = GridBagConstraints.BOTH;
        mainFrame.add(midPanel,gbc);
        
        rightPanel = new JPanel();
        rightPanel.setBackground(Color.orange);
        gbc.gridx = GridBagConstraints.RELATIVE;
        gbc.gridy = GridBagConstraints.REMAINDER;
        gbc.weightx = 2;
        gbc.weighty = 2;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.BOTH;
        mainFrame.add(rightPanel,gbc);
        
       
        
        mainFrame.setVisible(true);
        
    }
   

    public void updateScoreLabel() {
        SwingUtilities.invokeLater(new Runnable() {
 
            @Override
            public void run() {
              for (int i = 0;i< 2;i++)
              {
                  if (playersScore[i] == 0)
                  {
                      playersScoreLabel[i].setText(" ");
                      
                      
                  }
                  else if (playersScore[i] == 1)
                  {
                      playersScoreLabel[i].setText("B ");
                      
                  }
                  else if (playersScore[i] == 2)
                  {
                      playersScoreLabel[i].setText("B I ");
                      
                  }
                  else if (playersScore[i] == 3)
                  {
                      playersScoreLabel[i].setText("B I N ");
                      
                  }
                  else if (playersScore[i] == 4)
                  {
                      playersScoreLabel[i].setText("B I N G ");
                      
                  }
                  else if (playersScore[i] >= 5)
                  {
                      playersScoreLabel[i].setText("B I N G O ");
                      try {
                          GuiChangeForWinningSimple();
                      } catch (UnsupportedAudioFileException ex) {
                          Logger.getLogger(BingoGui.class.getName()).log(Level.SEVERE, null, ex);
                      } catch (IOException ex) {
                          Logger.getLogger(BingoGui.class.getName()).log(Level.SEVERE, null, ex);
                      } catch (LineUnavailableException ex) {
                          Logger.getLogger(BingoGui.class.getName()).log(Level.SEVERE, null, ex);
                      }
                      
                  }
              }
            }
        });
    }

    //change button state based on client input
    void changeButtonState(int i, int j) throws UnsupportedAudioFileException, IOException, LineUnavailableException {
        
        String soundName = "buttonClick.wav";    
        AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File(soundName).getAbsoluteFile());
        Clip clip = AudioSystem.getClip();
        clip.open(audioInputStream);
        clip.setFramePosition(0);
        clip.start();
//        try {
//            Thread.sleep(2000);
//        } catch (InterruptedException ex) {
//            Logger.getLogger(BingoGui.class.getName()).log(Level.SEVERE, null, ex);
//        }
        
        
        
        //myTurn[0] = true;
        SwingUtilities.invokeLater(new Runnable() {
            //for sound
            

            
            JButton button = buttons[i][j];
            @Override
            public void run() {
                
                
            button.setEnabled(false);
            //button.setText("");
                
            updatedRowValues[i]--;
            updatedColumnValues[j]--;
            
           
            
            if (i == j)
            {
                updateDiagonalValues[0]--;
            }
            if ((i + j) ==4 )
            {
                updateDiagonalValues[1]--;
            }
            Controller.updatePlayerScore(0);
            
            button.setBackground(Color.red);
            //for updating own score
            
            //updateScoreLabel();------final change here
            //turnChangerGui();
            System.out.println("-------------score " + playersScore[0] + " " + playersScore[1]);
            
               
            }
        });
    }

    public  void turnChangerGui() {
        
      SwingUtilities.invokeLater(new Runnable() {

          @Override
          public void run() {
               if (myTurn[0] == true)
                {
                    turnIndicator1.setText("your turn");
                    turnIndicator2.setText("");
                    
                    
                }
                else 
              {
                  turnIndicator1.setText("");
                  turnIndicator2.setText("oponent's turn");
              }
                    
               
                
          }
      });
    }

    @Override
    public void updateScore_turn() {
        //System.out.println("observer called");
        updateScoreLabel();
        turnChangerGui();
    }

    private class chatFieldActionListener implements ActionListener {

        String message;
        public chatFieldActionListener() {
        }

        @Override
        public void actionPerformed(ActionEvent ae) {
         
           message = chatField.getText();
           Bingo_Client b = new Bingo_Client();
                   b.sendMessage("C"+playerNames[0]+ ": " + message);
            //System.out.println(message);
            chatField.setText(""); 
        }
        
    }

    private  class bingoButtonListener implements ActionListener {
           int i;
           int j;
           int k;
        public bingoButtonListener(int i,int j,int k) {
            this.i = i;
            this.j = j;
            this.k = k;
            
           
        }

        @Override
        public void actionPerformed(ActionEvent ae) {
            if (Bingo_Complete[0] != false)
                 return;
             if (myTurn[0] != true)
            {
                
                System.out.println("please wait for opponent's move");
                return;
            }
             //myTurn[0] = false;
             
            
            JButton button = (JButton) ae.getSource();
            button.setEnabled(false);
            //button.setText("");
            updatedRowValues[i]--;
            updatedColumnValues[j]--;
            
            if (i == j)
            {
                updateDiagonalValues[0]--;
            }
            if ((i + j) ==4 )
            {
                updateDiagonalValues[1]--;
            }
            
            Controller.updatePlayerScore(0);
            button.setBackground(Color.red);
            //test---
            Bingo_Client.sendMessage("M" +Integer.toString(isFirstPlayer[0]) +Integer.toString(k) );
            Bingo_Client.sendMessage("S"+Integer.toString(isFirstPlayer[0])+ Integer.toString(playersScore[0]));
            myTurn[0] = false;
//            turnChangerGui();
//            updateScoreLabel();
            updateScore_turn();
            
            
            
            //System.out.println(i + " " + j + "  " +  k );
            //*****-------need to update own score*****
            
            System.out.println("-------------score " + playersScore[0] + " " + playersScore[1]);
            
        }
    }

    private  class sendButtonActionListener implements ActionListener {

        String message;
        
        public sendButtonActionListener() {
            
        }

        @Override
        public void actionPerformed(ActionEvent ae) {
           message = chatField.getText();
           Bingo_Client b = new Bingo_Client();
                   b.sendMessage("C"+playerNames[0] + message);
            //System.out.println(message);
            chatField.setText("");
        }
    }
    
//    public  void chatAppend(String message)
//    {
//        SwingUtilities.invokeLater(new Runnable() {
//
//            @Override
//            public void run() {
//                System.out.println(message);
//                chatArea.append(message);
//            }
//        });
//        
//    }
    public void chatAppend(String message)
            
    {
        
        chatArea.append(message + "\n");
    }
    
    //gui change for winning
    public void GuiChangeForWinning(int i)
    {
        
        
        
        if (i == 0)
        {
            Bingo_Complete[0] = true;
            
            {
                if ((myTurn[0]) )
                {
                    //---still i have a turn
                    if ( playersScore[1] == 5)
                    {
                        tipText.setText("             oops match tied :)");
                        
                        JOptionPane.showMessageDialog(null, i, "Hello", i);
                        int result = JOptionPane.showConfirmDialog(null,"dg", "Hello!", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.WARNING_MESSAGE);
                        
                        Bingo_Client.sendMessage("S"+Integer.toString(isFirstPlayer[0])+ Integer.toString(playersScore[0]));
                        playersScore[0] = -4;
                        //cont[0] = false;
                    }
                    else if (j == 0) 
                    {
                        if ( playersScore[1] == 5)
                    {
//                        Bingo_Client.showDialog();
                        JOptionPane.showMessageDialog(null, i, "Hello", i);
                        tipText.setText("             oops match tied :)");
                       // Bingo_Client.sendMessage("S"+Integer.toString(isFirstPlayer[0])+ Integer.toString(playersScore[0]));
                        //playersScore[0] = -4;
                        //cont[0] = false;
                    }
                  
                        else {   
                        
                        
                            
                            tipText.setText("                      you won the game !!!");
                           // Bingo_Client.showDialog();
                            JOptionPane.showMessageDialog(null, i, "Hello", i);
                            Bingo_Client.sendMessage("S"+Integer.toString(isFirstPlayer[0])+ Integer.toString(playersScore[0]));
                        //cont[0] = false;}
                    }
                }
                
                
                
            }
            j--;
            
        }
        }
             
            //asking for play again
            //Controller controller = new Controller();
            //here to change for play again
            //mainFrame.dispose();
            
            
        
        else if (i == 1)
        {
        
            if (isFirstPlayer[0] == 2){
            if (myTurn[0] == false){
        
            tipText.setText("                  opponent won the game !!!");
            Bingo_Complete[0] = true;
            //cont[0] = false;
            }
            //cont[0]= false;
            //mainFrame.dispose();
            //here to change for play again
            //Controller controller = new Controller();
            }
          
             
             
        else 
        {
            tipText.setText("                  opponent won the game !!!");
            
            Bingo_Complete[0] = true;
        }
    }
    }
    
    
    public void GuiChangeForWinningSimple() throws UnsupportedAudioFileException, IOException, LineUnavailableException
    {
        if (!GameOver[0])
        {
           Bingo_Complete[0] = true;
        
        //if (i == 0)
        {
            if (myTurn[0] == true)
        {
            if (playersScore[1] >= 5 && playersScore[0] >= 5)
            {
                    //--for  sound playing
                String soundName = "yourSound.wav";    
                AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File(soundName).getAbsoluteFile());
                Clip clip = AudioSystem.getClip();
                clip.open(audioInputStream);
                clip.setFramePosition(0);
                clip.start();
                
                
                
                
                
                
                GameOver[0] = true;
                tipText.setText("                     oooooopppppps match is tied");
                //int result = JOptionPane.showConfirmDialog(null,"match tied", "play again!", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
                Bingo_Client.sendMessage("S"+Integer.toString(isFirstPlayer[0])+ Integer.toString(playersScore[0]));
                Bingo_Client.sendMessage("E");
                System.out.println("server closed");
                int result = JOptionPane.showConfirmDialog(null,"match tied", "play again!", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
                if (result == JOptionPane.NO_OPTION)
                 {
                     System.exit(0);
                 }
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException ex) {
                    Logger.getLogger(BingoGui.class.getName()).log(Level.SEVERE, null, ex);
                }
                
                cont[0] = false;
                mainFrame.dispose();
                
                 menu3 menu = new menu3();
                 menu.setVisible(true);
                
            }
            else if (playersScore[1] < 5)
            {
                //for sound
                String soundName = "yourSound.wav";    
                AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File(soundName).getAbsoluteFile());
                Clip clip = AudioSystem.getClip();
                clip.open(audioInputStream);
                clip.setFramePosition(0);
                clip.start();
                
                
                
                
                GameOver[0] = true;
                tipText.setText("----------you win");
                //int result = JOptionPane.showConfirmDialog(null,"you won !", "play again!", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
                Bingo_Client.sendMessage("S"+Integer.toString(isFirstPlayer[0])+ Integer.toString(playersScore[0]));
                 System.out.println("server closed");
                 int result = JOptionPane.showConfirmDialog(null,"you win !, play again !", "play again!", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
                 if (result == JOptionPane.NO_OPTION)
                 {
                     System.exit(0);
                 }
                 cont[0] = false;
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException ex) {
                    Logger.getLogger(BingoGui.class.getName()).log(Level.SEVERE, null, ex);
                }
                 
                 mainFrame.dispose();
                 menu3 menu = new menu3();
                 menu.setVisible(true);
            }
            else
            {
                //for sound
                String soundName = "yourSound.wav";    
                AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File(soundName).getAbsoluteFile());
                Clip clip = AudioSystem.getClip();
                clip.open(audioInputStream);
                clip.setFramePosition(0);
                clip.start();
                
                
                
                GameOver[0] = true;
                tipText.setText("----------you lose the game");
                //int result = JOptionPane.showConfirmDialog(null,"you lose !", "play again!", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
                Bingo_Client.sendMessage("S"+Integer.toString(isFirstPlayer[0])+ Integer.toString(playersScore[0]));
                 System.out.println("server closed");
                 
                 cont[0] = false;
                 int result = JOptionPane.showConfirmDialog(null,"you lose ! , play again!", "play again!", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
                
                 if (result == JOptionPane.NO_OPTION)
                 {
                     System.exit(0);
                 }
                 try {
                    Thread.sleep(2000);
                } catch (InterruptedException ex) {
                    Logger.getLogger(BingoGui.class.getName()).log(Level.SEVERE, null, ex);
                }
                 
                 mainFrame.dispose();
                 menu3 menu = new menu3();
                 menu.setVisible(true);
                 
            }
            
            
        }
            
        }
      
        
        
    }
    }
}
    
    
    
    

