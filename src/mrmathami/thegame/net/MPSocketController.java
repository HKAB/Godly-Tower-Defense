package mrmathami.thegame.net;

import java.net.*;
import java.io.*;
import java.util.Arrays;
import java.util.List;

/**
 * Socket controller.
 * Connect directly with the game flow to send and receive commands.
 *
 *      Usage: MPSocketController.setCurrentInstance(new MPSocketController(ip, port))
 *
 * This implementation helps us add multi-player to the game without having to pass the socket object everywhere.
 * Peace!
 */
public class MPSocketController {
    private MPSocket socket;
    private static MPSocketController currentInstance = null;

    /**
     * Constructor for listening.
     * @param port Port to listen on.
     */
    public MPSocketController(int port) throws IOException {
        ServerSocket serverSocket = new ServerSocket(port);
        this.socket = new MPSocket(serverSocket.accept());
    }

    /**
     * Constructor for connecting.
     * @param host Address to connect to.
     * @param port Port to connect to.
     */
    public MPSocketController(String host, int port) throws IOException {
        Socket socket = new Socket();
        socket.connect(new InetSocketAddress(host, port), MPConfig.DEFAULT_CONNECTION_TIMEOUT);
        this.socket = new MPSocket(socket);
    }

    /**
     * Close connection.
     * Super important when player want to start another MP game, as the old connection still exist and take that port.
     */
    public void closeConnection() {
        socket.close();
        setCurrentInstance(null);
    }

    /**
     * Return an static instance, so we don't have to implement DI everywhere.
     * @return current instance.
     */
    public static MPSocketController getCurrentInstance() {
        return currentInstance;
    }

    /**
     * Set the new instance.
     * @param instance MPSocketController instance to use till next set.
     */
    public static void setCurrentInstance(MPSocketController instance) {
        currentInstance = instance;
    }

    /**
     * Check if the current instance is initialized.
     * @return false if the currentInstance is null, true otherwise.
     */
    public static boolean hasInstance() {
        return currentInstance != null;
    }

    /**
     * Send a command to remote host from a List of Strings.
     * @param command remote command to send.
     */
    private void sendCommand(List<String> command) {
        this.socket.sendLine(String.join(" ", command));
    }

    /**
     * Get the next line and split it by spaces.
     * @return List of String which is space-separated from a line received from socket.
     */
    public List<String> getNextCommand() {
        String command = this.socket.receiveLine();
        if (command.isBlank()) {
            return List.of();
        }
        return Arrays.asList(command.split(" "));
    }

    /**
     * PLACE command sender. Used to place a tower on remote host.
     * @param type type of tower.
     * @param posX X position on game field.
     * @param posY Y position on game field.
     */
    public void sendPlace(int type, double posX, double posY) {
        sendCommand(List.of("PLACE", String.format("%d", type),
                String.format("%.0f", posX), String.format("%.0f", posY)));
    }

    /**
     * UPGRADE command sender. Used to upgrade tower on the remote host.
     * @param posX X position on game field.
     * @param posY Y position on game field.
     */
    public void sendUpgrade(double posX, double posY) {
        sendCommand(List.of("UPGRADE", String.format("%.0f", posX), String.format("%.0f", posY)));
    }

    /**
     * SELL command sender. Used to sell tower on the remote host.
     * @param posX X position on game field.
     * @param posY Y position on game field.
     */
    public void sendSell(double posX, double posY) {
        sendCommand(List.of("SELL", String.format("%.0f", posX), String.format("%.0f", posY)));
    }

    /**
     * STATE command sender. Used to send player state to the remote host when it changes.
     * @param health Current health of the player.
     */
    public void sendState(long health) {
        sendCommand(List.of("STATE", Long.toString(health)));
    }
}
