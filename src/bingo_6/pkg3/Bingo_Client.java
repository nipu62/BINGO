/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bingo_6.pkg3;

import InterfaceObserver.Game_Values;
import InterfaceObserver.Observer;
import InterfaceObserver.Subject;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import static InterfaceObserver.Game_Values.cont;
import static InterfaceObserver.Game_Values.isFirstPlayer;
import static InterfaceObserver.Game_Values.myTurn;
import Server.Bingo_Server;
import java.util.Arrays;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class Bingo_Client implements Game_Values, Subject {

    public Controller controller;
    private static Socket socket;
    private static BufferedReader input;
    public static PrintWriter output;

    //trying observer
    private ArrayList<Observer> observers = new ArrayList<Observer>();


    public static void main(String[] args) throws IOException {

        {

            menu3 menu = new menu3();

            menu.setVisible(true);

        }

    }

    public void StartGame(String ip, JFrame frame) throws IOException {

        try {
            //playerNames[0] = g.getName();
            System.out.println("" + port[0]);
            System.out.println("trying in port " + port[0] + " ip " + ip);
            socket = new Socket(ip, port[0]);
            System.out.println("connected in port " + port[0] + " ip " + ip);
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null, ex);
            Logger.getLogger(Bingo_Client.class.getName()).log(Level.SEVERE, null, ex);
        }
        System.out.println("waiting for another player");

        input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        output = new PrintWriter(socket.getOutputStream(), true);
        cont[0] = true;
        output.println(playerNames[0]);
        //trying with name-------

        String message = input.readLine();
        {
            if ((message.charAt(0)) == '0') {
                isFirstPlayer[0] = 1;
                myTurn[0] = true;
                playerNames[1] = message.substring(1);
                System.out.println(playerNames[1]);
                if (playerNames[1] == null) {
                    playerNames[1] = "player2";
                }
                System.out.println(playerNames[1]);
                System.out.println("you're the 1st player");
                            //output.println("N" +"1"+playerNames[0]);

            } else {
                isFirstPlayer[0] = 2;
                myTurn[0] = false;
                System.out.println("you're the 2nd player");
                playerNames[1] = message.substring(1);
                if (playerNames[1] == null) {
                    playerNames[1] = "player2";
                }
            }

        }

        //---------------------------
        Bingo_Client b = new Bingo_Client();
        ;
        frame.dispose();
        b.startGui();
        //b.regesterObserver(controller);
        b.receiveMessage();
        //String message = input.readLine();
        //System.out.println(message.charAt(0) + " " + message.charAt(1));

        while (cont[0]) {

        }
        input.close();
        output.close();
        socket.close();

    }

    /**
     * for sending message
     *
     * @param message
     */

    public static void sendMessage(String message) {

        output.println(message);
        output.flush();

    }

    /**
     * thread for receiving message
     *
     */
    private void receiveMessage() {
        Thread t = new Thread(new Runnable() {

            @Override
            public void run() {
                while (cont[0]) {

                    try {
                        //Controller controller = new Controller();
                        String message = input.readLine();
                        if (message.charAt(0) == 'C') {
                            controller.showMessage(message.substring(1));
                        } //handle button value
                        else if (message.charAt(0) == 'M') {
                        //change here
                            // myTurn[0] = true;
                            int value = Integer.parseInt(message.substring(2));
                            System.out.println(message.substring(1));
                            //System.out.println();
                            controller.handleButtonMove(value);

                        } else if (message.charAt(0) == 'S') {
                            myTurn[0] = true;
                            controller.updateSCore(message.substring(2));

                        } else if (message.charAt(0) == 'E') {
                            cont[0] = false;
                            input.close();
                            output.close();
                            socket.close();

                        }

                    } catch (IOException ex) {
                        cont[0] = false;
                        JOptionPane.showMessageDialog(null, ex);
                        Logger.getLogger(Bingo_Client.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        });
        t.start();
    }

    /**
     * calling controller class for initiate GUI
     *
     */
    private void startGui() {
        controller = new Controller();

//throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    /**
     * Registering for notify specific class
     *
     * @param observer
     */
    @Override
    public void regesterObserver(Observer observer) {

        observers.add(observer);
    }

    @Override
    public void removeObserver(Observer observer) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    /**
     * notifying observer about change
     *
     */
    @Override
    public void notifyObserver() {
        for (Observer ob : observers) {
            ob.updateScore_turn();
        }
    }

    /**
     * start the server
     *
     */
    void startServer() {
        new Thread(new Runnable() {

            @Override
            public void run() {
                try {
                    Bingo_Server b = new Bingo_Server();
                } catch (IOException ex) {
                    JOptionPane.showInputDialog("error in connection , please close and start again");
                    Logger.getLogger(menu3.class.getName()).log(Level.SEVERE, null, ex);
                }
                //To change body of generated methods, choose Tools | Templates.
            }
        }).start();//To change body of generated methods, choose Tools | Templates.
    }
}
