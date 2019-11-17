package mrmathami.thegame.net;

import java.util.*;

public class MPGameServer {
    private MPServer server;
    private static MPGameServer instance;

    private MPGameServer() {
        this.server = new MPServer(MPConfig.DEFAULT_LISTEN_PORT);
    }

    public static MPGameServer getInstance() {
        if (instance == null) {
            instance = new MPGameServer();
        }
        return instance;
    }

    public List<String> getNextCommand() {
        String command = this.server.recvLine();
        if (command.isBlank()) {
            return List.of();
        }
        return Arrays.asList(command.split(" "));
    }

    public boolean hasConnection() {
        return this.server != null;
    }
}
