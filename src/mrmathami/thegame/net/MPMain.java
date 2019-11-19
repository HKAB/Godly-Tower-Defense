package mrmathami.thegame.net;

import java.io.IOException;
import java.net.Socket;
import java.util.List;

public class MPMain {
    public static void main(String[] args) {
        MPSocket socket = null;
        try {
            socket = new MPSocket(new Socket("localhost", 1337));
        } catch (IOException e) {
            System.exit(1);
        }
        socket.sendLine("Hi im server");
        System.out.println(socket.recvLine());
        System.exit(2);
    }
}
