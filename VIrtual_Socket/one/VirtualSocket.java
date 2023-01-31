import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.util.Random;

public class VirtualSocket extends DatagramSocket {

    private static double drop_p = 0.45;
    public static String msg = "";
    public static int cnt = 0;

    //constructor to open without port
    public VirtualSocket() throws SocketException {
        super();
    }

    //constructor to open with port
    public VirtualSocket(int port) throws SocketException {
        super(port);
    }

    //constructor to open without port and address used to identify the device or host
    //that is running the virtual socket
    public VirtualSocket(int port, InetAddress laddr) throws SocketException {
        super(port, laddr);
    }

    //may be useful later
    public void send(DatagramPacket packet) throws IOException {
        // implementation to send a VirtualPacket using the DatagramSocket's send method
    }

    public void receive(DatagramPacket packet) throws IOException {
        while(true) {
            super.receive(packet);
            Random rand_obj = new Random();
            double d = rand_obj.nextDouble();
            if (d <= drop_p) {
                cnt++;
                if(cnt > 0) {
                    msg = "Packet dropped! (" + Integer.toString(cnt) +")";
                }
                else {
                    msg = "Packet dropped!";
                }
            }
            else {
                return;
            }
        }
    }
}

