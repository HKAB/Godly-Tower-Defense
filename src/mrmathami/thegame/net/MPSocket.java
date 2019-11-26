package mrmathami.thegame.net;

import java.net.*;
import java.io.*;

public class MPSocket {
    private Socket socket;
    private BufferedReader input;
    private PrintWriter output;

    public MPSocket(Socket socket) {
        this.socket = socket;
        try {
            this.input = new BufferedReader(new InputStreamReader(this.socket.getInputStream()));
            this.output = new PrintWriter(this.socket.getOutputStream(), true);
        } catch (IOException e) {
            System.out.println("Error creating socket");
            e.printStackTrace();
        }
    }

    /**
     * Send a line to the remote host.
     * @param data data to send.
     */
    public void sendLine(String data) {
        this.output.println(data);
    }

    /**
     * Read a line sent from the remote host. If the buffer is empty, return an empty string.
     * @return A String if the input buffer is not empty, empty String otherwise.
     */
    public String recvLine() {
        String data;
        try {
            if (this.input.ready()) {
                data = this.input.readLine();
            } else {
                data = "";
            }
        } catch (IOException e) {
            e.printStackTrace();
            data = "";
        }
        return data;
    }

    /**
     * Close all currently open socket or streams.
     */
    public void close() {
        try {
            input.close();
            output.close();
            socket.close();
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Cannot close connection");
        }
    }
}
