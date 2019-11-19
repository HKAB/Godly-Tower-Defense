package mrmathami.thegame.net;

import java.net.*;
import java.io.*;
import java.util.Arrays;
import java.util.List;

public class MPSocketController {
    private ServerSocket serverSocket = null;
    private MPSocket socket;
    private static MPSocketController currentInstance = null;

    /**
     * Constructor for listening
     */
    public MPSocketController() {
        try {
            this.serverSocket = new ServerSocket(MPConfig.DEFAULT_LISTEN_PORT);
            this.socket = new MPSocket(serverSocket.accept());
        } catch (IOException e) {
            e.printStackTrace();
        }
        currentInstance = this;
    }

    /**
     * Constructor for connecting
     * @param host Address to connect to
     * @param port Port to connect to
     */
    public MPSocketController(String host, int port) throws IOException {
        try {
            this.socket = new MPSocket(new Socket(host, port));
        } catch (IOException e) {
            e.printStackTrace();
            throw e;
        }
        currentInstance = this;
    }

    /**
     * Create an static instance, so we don't have to use DI everywhere.
     * @return current instance
     */
    public static MPSocketController getCurrentInstance() {
        if (currentInstance == null) {
            return new MPSocketController();
        } else {
            return currentInstance;
        }
    }

    public void sendCommand(List<String> command) {
        this.socket.sendLine(String.join(" ", command));
    }

    public List<String> getNextCommand() {
        String command = this.socket.recvLine();
        if (command.isBlank()) {
            return List.of();
        }
        return Arrays.asList(command.split(" "));
    }
}
