package mrmathami.thegame.net;

public class MPGameServer {
    private MPServer server;
    private static MPGameServer instance;

    private MPGameServer() {
        this.server = new MPServer(1337);
    }

    public static MPGameServer getInstance() {
        if (instance == null) {
            instance = new MPGameServer();
        }
        return instance;
    }
}
