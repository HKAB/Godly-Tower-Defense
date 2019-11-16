package mrmathami.thegame.net;

public class MPMain {
    public static void main(String[] args) {
        MPClient client = new MPClient("localhost", 1337);
        client.sendLine("Hey this is client");
        String data = client.recvLine();
        System.out.println(data);
        client.close();
    }
}
