/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uigraph;

/**
 *
 * @author Leandro
 */

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class Server {
    static ArrayList<Integer> ar;
    public Server(){
        ar = new ArrayList<>();
    }
    public static void main(String[] args) throws IOException, ClassNotFoundException {
        System.out.println("Executando");
        ServerSocket server = new ServerSocket(7002);
        Maps mapa = new Maps(200,20);
        Server client = new Server();
        //Socket connection = server.accept();
        //Runnable serverThread1 = new ServerThread(server, mapa, ar);
        Runnable serverThread2 = new ServerThread(server, mapa, ar);
        //client.sending(mapa,connection);
        //Thread thread = new Thread(serverThread1);
        Thread thread2 = new Thread(serverThread2);
        while (true) {   
            //thread.start();
            //System.err.println("no meio");
            thread2.start();
        }
    }
    public Serializable receive (Socket connection) throws IOException, ClassNotFoundException {
        ObjectInputStream input = new ObjectInputStream(connection.getInputStream());        
        return (Serializable) input.readObject();
    }
    public void sending (Serializable object, Socket connection) throws IOException{
        try (ObjectOutputStream output = new ObjectOutputStream(connection.getOutputStream())) {
                output.flush();
                output.writeObject(object);
                output.flush();   
        }
    }  
}