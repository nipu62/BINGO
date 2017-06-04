package Server;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import static InterfaceObserver.Game_Values.port;
import com.sun.corba.se.spi.activation.Server;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author Shaurav
 */
public class Bingo_Server {

    private static ServerSocket server = null;
    private static Socket clientSocket[] = null;
    private static int MAX_CLIENT = 2;
    private static ClientThread clientThread[] = new ClientThread[MAX_CLIENT];
    public static boolean cont = true;

    public Bingo_Server() throws IOException {

        try {
            System.out.println("" + port[0]);
            server = new ServerSocket(port[0]);
            System.out.println("server started ,waiting for clients at port " + port[0]);
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null, ex);
            Logger.getLogger(Bingo_Server.class.getName()).log(Level.SEVERE, null, ex);
        }
        clientSocket = new Socket[2];

        for (int i = 0; i < 2; i++) {
            System.out.println("waiting for client..");
            clientSocket[i] = server.accept();
            System.out.println("accepted");

        }
        for (int j = 0; j < 2; j++) {
            if (clientThread[j] == null) //herw j for detecting 1st player
            {
                clientThread[j] = new ClientThread(clientSocket[j], clientThread, j);
            }
            clientThread[j].start();
        }

        server.close();

    }

}
