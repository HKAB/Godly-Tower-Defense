package mrmathami.thegame.net;

import java.util.Arrays;
import java.util.List;

public class MPGameClient {
    MPClient client;
    private static MPGameClient currentInstance;

    public MPGameClient(String address, int port) {
        this.client = new MPClient(address, port);
        if (client.hasConnection()) {
            currentInstance = this;
        }
    }

    public static MPGameClient getCurrentInstance() {
        return currentInstance;
    }

    public void sendCommand(List<String> command) {
        this.client.sendLine(String.join(" ", command));
    }

    public List<String> getNextCommand() {
        String command = this.client.recvLine();
        if (command.isBlank()) {
            return null;
        }
        return Arrays.asList(command.split(" "));
    }

    public boolean hasConnection() {
        return currentInstance != null;
    }
}
