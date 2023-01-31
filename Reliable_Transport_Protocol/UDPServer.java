import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import javax.swing.JFrame;
import javax.swing.JTextArea;

public class UDPServer extends JFrame {
    private JTextArea textArea;
    private DatagramSocket socket;
    private PacketManager packetManager;
    private Window window;
    private SimpleMultiUseGUI gui;

public UDPServer(SimpleMultiUseGUI gui, int serverPort) {
    this.gui = gui;
    try {
        socket = new DatagramSocket(serverPort);
            window = new Window(10);
            packetManager = new PacketManager(socket, 1000, window);

            while (true) {
                // Receive packet
                Packet receivedPacket = packetManager.receivePacket();
                if (receivedPacket != null) {
                    // Print packet payload to text area
                    String message = new String(receivedPacket.getPayload());
                    //textArea.append("Received packet: " + message + "\n");
                    gui.setMsg(message);
                    // Send acknowledgement
                    int seqNum = receivedPacket.getSequenceNumber();
                    Packet ackPacket = new Packet(seqNum, "ACK".getBytes(), -1, 0, (byte)0, receivedPacket.getAddress(), receivedPacket.getPort());
                    packetManager.sendPacket(ackPacket, receivedPacket.getAddress(), receivedPacket.getPort());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (socket != null) {
                socket.close();
            }
        }
    }

    public static void main(String[] args) {
        SimpleMultiUseGUI gui = new SimpleMultiUseGUI();
        gui.setHeader("Packets received by server will be displayed here:");
        int[] white = new int[]{ 255,255,255 };
        int[] red = new int[]{ 255,0,0 }; 
        int[] green = new int[]{ 127,255,0 }; 
        gui.setBGColor(white);
        gui.setFontColor(green, 0);
        gui.setFontColor(red, 1);
        gui.frame.setVisible(true);
        //new UDPServer(gui);
        int serverPort = 6667;
        new UDPServer(gui, serverPort);
    }
}

