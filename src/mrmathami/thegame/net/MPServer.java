package mrmathami.thegame.net;

import java.io.*;
import java.net.*;

public class MPServer {
    private Socket socket;
    private ServerSocket serverSocket;
    private BufferedReader input;
    private PrintWriter output;

    public MPServer(int port) {
        try {
            this.serverSocket = new ServerSocket(port);
            this.socket = serverSocket.accept();
            this.input = new BufferedReader(new InputStreamReader(this.socket.getInputStream()));
            this.output = new PrintWriter(this.socket.getOutputStream(), true);
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Error when creating serverSocket");
            System.exit(1);
        }
    }

    public void sendLine(String data) {
        this.output.println(data);
    }

    public String recvLine() {
        String data;
        try {
            if (this.input.ready()) data = this.input.readLine();
            else data = "";
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Return data is empty");
            data = "";
        }
        return data;
    }

    public void close() {
        try {
            input.close();
            output.close();
            socket.close();
            serverSocket.close();
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Cannot close connection");
            System.exit(1);
        }
    }
}
