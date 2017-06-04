package Server;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.nio.Buffer;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ClientThread extends Thread {

    public PrintWriter output;
    public BufferedReader input;
    public Socket socket;
    public ClientThread[] threads;
    private int playerNum;
    //public boolean cont;
    //private String message;

    public ClientThread(Socket socket, ClientThread[] threads, int playerNum) throws IOException {
        this.socket = new Socket();
        output = new PrintWriter(socket.getOutputStream(), true);
        input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        this.threads = threads;
        this.playerNum = playerNum;
        Bingo_Server.cont = true;
    }

    @Override
    public void run() {


        String m;
        try {
            m = input.readLine();
            if (playerNum == 0) {
                threads[1].output.println("1" + m);
            } else if (playerNum == 1) {
                threads[0].output.println("0" + m);
            }

        } catch (IOException ex) {
            Logger.getLogger(ClientThread.class.getName()).log(Level.SEVERE, null, ex);
        }

           //------------------
//           if (playerNum == 0)
//               output.println("0");
//           else if (playerNum == 1)
//                   output.println("1");
        while (Bingo_Server.cont) {
            String message;
            try {
                if ((message = input.readLine()) != null) {
                    if (message.charAt(1) == '2') {
                        threads[0].output.println(message);
                        threads[0].output.flush();
                    } else if (message.charAt(1) == '1') {
                        threads[1].output.println(message);
                        threads[1].output.flush();
                    } else if (message.charAt(0) == 'E') {
                        for (int i = 0; i < 2; i++) {
                            threads[i].output.println(message);
                            threads[i].output.flush();
                        }
                        Bingo_Server.cont = false;

                        output.close();
                        input.close();

                    } else {
                        for (int i = 0; i < 2; i++) {
                            threads[i].output.println(message);
                            threads[i].output.flush();
                        }

                    }
                }
            } catch (IOException ex) {
                System.exit(0);
                Bingo_Server.cont = false;
                output.close();
                try {
                    input.close();
                } catch (IOException ex1) {
                    Logger.getLogger(ClientThread.class.getName()).log(Level.SEVERE, null, ex1);
                }
                Logger.getLogger(ClientThread.class.getName()).log(Level.SEVERE, null, ex);
            }

        }

    }
}
