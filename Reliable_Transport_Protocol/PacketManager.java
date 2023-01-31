import java.io.ByteArrayInputStream;
import java.io.ObjectInputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketTimeoutException;
import java.io.IOException;
import java.util.Arrays;

public class PacketManager {
    private DatagramSocket socket;
    private int timeout;
    private Window window;

    public PacketManager(DatagramSocket socket, int timeout, Window window) {
        this.socket = socket;
        this.timeout = timeout;
        this.window = window;
    }

    public void sendPacket(Packet packet, InetAddress address, int port) throws IOException {
        byte[] data = packet.toByteArray();
        DatagramPacket datagramPacket = new DatagramPacket(data, data.length, packet.getAddress(), packet.getPort());
        socket.send(datagramPacket);
        window.addPacket(packet);
        System.out.println("Sent packet " + packet.getSequenceNumber());
    }


    public Packet receivePacket() throws Exception {
        byte[] recvData = new byte[1024];
        DatagramPacket recvPacket = new DatagramPacket(recvData, recvData.length);
        socket.setSoTimeout(timeout);
        while (true) {
            try {
                socket.receive(recvPacket);
                break;
            } catch (SocketTimeoutException e) {
                resend();
            }
        }
        Packet receivedPacket = fromByteArray(recvPacket.getData());
        if(receivedPacket.isValid() && window.isPacketLost(receivedPacket.getSequenceNumber())) {
            window.acknowledgePacket(receivedPacket.getSequenceNumber());
            return receivedPacket;
        } else {
            return null;
        }
    }

    public void resend() throws Exception {
        int baseSeq = window.getHead();
        for (int i = baseSeq; i < window.getTail(); i++) {
            Packet packet = window.getPacket(i);
            if(packet != null) {
                byte[] sendData = packet.toByteArray();
                DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, packet.getAddress(), packet.getPort());
                socket.send(sendPacket);
            }
        }
    }
    
    public static Packet fromByteArray(byte[] data, InetAddress address, int port) {
        int sequenceNumber = byteArrayToInt(Arrays.copyOfRange(data, 0, 4));
        byte[] payload = Arrays.copyOfRange(data, 4, data.length - 8);
        long checksum = byteArrayToLong(Arrays.copyOfRange(data, data.length - 8, data.length));
        return new Packet(sequenceNumber, payload, -1, checksum, (byte)0, address, port);
    }
}

