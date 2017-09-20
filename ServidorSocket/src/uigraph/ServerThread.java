/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uigraph;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Leandro
 */
public class ServerThread implements Runnable{
    private ServerSocket serverSocket;
    private Socket socket;
    private Serializable object;
    private static  ArrayList<Integer> arrayPlayed;
    private static int i =0;
    
    public ServerThread(ServerSocket socket, Serializable object, ArrayList<Integer> ar){
        this.arrayPlayed = ar;
        this.serverSocket = socket;
        this.object = object;
        try{
            this.socket = this.serverSocket.accept();
        }catch(Exception ex){}
    }
    @Override
    public void run() {
        while(true){
            try {
                //ServerThread.sending(this.object, this.socket);
                Integer sr =(Integer) ServerThread.receive(this.socket);
                this.arrayPlayed.add(sr);
                System.err.println(sr);
                //System.out.println(sr);
            } catch (Exception ex) {
                ex.printStackTrace();
            } 
        }
    }
    public static Serializable receive (Socket socket) throws IOException, ClassNotFoundException {
        ObjectInputStream input = new ObjectInputStream(socket.getInputStream());        
        return (Serializable) input.readObject();
    }
    public static void sending (Serializable object, Socket connection) throws IOException{
        try (ObjectOutputStream output = new ObjectOutputStream(connection.getOutputStream())) {
                output.flush();
                output.writeObject(object);
                output.flush();   
        }
    }
}
