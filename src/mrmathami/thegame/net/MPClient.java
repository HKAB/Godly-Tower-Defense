package mrmathami.thegame.net;

import java.io.*;
import java.net.*;

public class MPClient {
    private Socket socket;
    private BufferedReader input;
    private PrintWriter output;

    public MPClient(String address, int port) {
        try {
            this.socket = new Socket(address, port);
            this.input = new BufferedReader(new InputStreamReader(this.socket.getInputStream()));
            this.output = new PrintWriter(socket.getOutputStream(), true);
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Error connecting to " + address + " " + port);
            System.exit(0);
        }
    }

    public void close() {
        try {
            input.close();
            output.close();
            socket.close();
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Cannot close connection");
            System.exit(1);
        }
    }

    public void sendLine(String data) {
        this.output.println(data);
    }

    public String recvLine() {
        String data;
        try {
            data = this.input.readLine();
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Return data is empty");
            data = "";
        }
        return data;
    }
}
