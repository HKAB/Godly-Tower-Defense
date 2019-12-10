package mrmathami.thegame.net;

import java.net.*;
import java.io.*;

/**
 * Multi-player socket.
 * A wrapper around JavaSocket library to help us perform game actions easier.
 */
public class MPSocket {
    private Socket socket;
    private BufferedReader input;
    private PrintWriter output;

    public MPSocket(Socket socket) {
        this.socket = socket;
        try {
            this.input = new BufferedReader(new InputStreamReader(this.socket.getInputStream()));
            this.output = new PrintWriter(this.socket.getOutputStream());
        } catch (IOException e) {
            System.out.println("Error creating socket");
            e.printStackTrace();
        }
    }

    /**
     * Send a line to the remote host.
     * Replace checkError() with flush() if you don't need to check the error.
     * @param data data to send.
     * @return true if the data sent successfully, false otherwise.
     */
    public boolean sendLine(String data) {
        this.output.println(data);
        return !(output.checkError());
    }

    /**
     * Read a line sent from the remote host. If the buffer is empty, return an empty string.
     * @return A String if the input buffer is not empty, empty String otherwise.
     */
    public String receiveLine() {
        String data;
        try {
            if (input.ready()) {
                data = input.readLine();
            } else {
                data = "";
            }
        } catch (IOException e) {
            data = "";
        }
        return data;
    }

    /**
     * Close all currently opening socket or streams.
     */
    public void close() {
        try {
            input.close();
            output.close();
            socket.close();
        } catch (Exception e) {
            System.out.println("Cannot close connection");
        }
    }
}
