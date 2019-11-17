package mrmathami.thegame.net;

public class MPMain {
    public static void main(String[] args) {
        MPServer server = new MPServer(1337);
        server.sendLine("Hey this is server");
        for (int i = 0; i < 20; i++) {
            System.out.println(i);
            String data = server.recvLine();
            if (!data.isBlank()) {
                System.out.println(data);
            }
            try {
                Thread.sleep(1000);
            } catch (Exception e) {
                e.printStackTrace();
                break;
            }
        }
        server.close();
    }
}
