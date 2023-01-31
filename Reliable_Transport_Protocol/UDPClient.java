import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.Scanner;

public class UDPClient {
    private static final int MAX_RETRIES = 5;
    private static final int TIMEOUT = 1000;
    private static int seqNum = 0;
    private DatagramSocket socket;
    private InetAddress serverAddress;
    private int serverPort;
    private PacketManager packetManager;

    public UDPClient(InetAddress serverAddress, int serverPort) {
        this.serverAddress = serverAddress;
        this.serverPort = serverPort;
        try {
            socket = new DatagramSocket();
            Window window = new Window(10);
            packetManager = new PacketManager(socket, TIMEOUT, window);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void start() {
        try {
            Scanner scanner = new Scanner(System.in);
            while (true) {
                System.out.print("Enter a message to send: ");
                String message = scanner.nextLine();
                if (message.equals("exit")) {
                    break;
                }

                // Create and send packet
                Packet packet = new Packet(seqNum, message.getBytes(), -1, 0, (byte)0, InetAddress.getLocalHost(), 6666);
                //packet.setAddress(InetAddress.getByName("127.0.0.1"));
                packet.setAddress(InetAddress.getLocalHost());
                packet.setPort(6666);
                packetManager.sendPacket(packet, serverAddress, serverPort);

                // Wait for acknowledgement
                for (int i = 0; i < MAX_RETRIES; i++) {
                    Packet receivedPacket = packetManager.receivePacket();
                    if (receivedPacket != null && receivedPacket.getSequenceNumber() == seqNum) {
                        System.out.println("Received acknowledgement for packet " + seqNum);
                        seqNum++;
                        break;
                    } else {
                        System.out.println("Resending packet " + seqNum);
                        packetManager.sendPacket(packet, serverAddress, serverPort);
                    }
                }
            }
            scanner.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (socket != null) {
                socket.close();
            }
        }
    }

    public static void main(String[] args) throws IOException {
        InetAddress serverAddress = InetAddress.getByName("localhost");
        int serverPort = 6667;
        //UDPClient client = new UDPClient(serverAddress, serverPort);
        UDPClient client = new UDPClient(serverAddress, serverPort);
        client.start();
    }
}

