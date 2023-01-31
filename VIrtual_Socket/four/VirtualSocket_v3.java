import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.util.Random;

public class VirtualSocket_v3 extends DatagramSocket {

    private static double drop_p = 0.45; // probability of dropping a packet
    private static double delay_p = 0.45; // probability of delaying a packet
    private static int min_delay = 1000; // minimum delay time in milliseconds
    private static int max_delay = 5000; // maximum delay time in milliseconds
    private static double error_p = 0.1; // probability of generating a bit error
    public static String msg = "";
    public static int cnt = 0;

    //constructor to open without port
    public VirtualSocket_v3() throws SocketException {
        super();
    }

    //constructor to open with port
    public VirtualSocket_v3(int port) throws SocketException {
        super(port);
    }

    //constructor to open without port and address used to identify the device or host
    //that is running the virtual socket
    public VirtualSocket_v3(int port, InetAddress laddr) throws SocketException {
        super(port, laddr);
    }

    //may be useful later
    public void send(DatagramPacket packet) throws IOException {
        // implementation to send a VirtualPacket using the DatagramSocket's send method
    }

    public void receive(DatagramPacket packet) throws IOException {
        while(true) {
            // implementation to receive a VirtualPacket using the DatagramSocket's receive method
            super.receive(packet);
            Random rand_obj = new Random();
            double d = rand_obj.nextDouble();
            if (d <= delay_p) {
                // randomly delay the packet
                int delay = rand_obj.nextInt((max_delay - min_delay) + 1) + min_delay;
                try {
                    Thread.sleep(delay);
                } catch (InterruptedException e) {
                    // handle the exception here
                }
            }
            d = rand_obj.nextDouble();
            if (d <= drop_p) {
                // drop the packet
                cnt++;
                if(cnt > 0) {
                    msg = "Packet dropped! (" + Integer.toString(cnt) +")";
                }
                else {
                    msg = "Packet dropped!";
                }
            }
            else {
                d = rand_obj.nextDouble();
                if (d <= error_p) {
                    // generate a bit error
                    byte[] data = packet.getData();
                    int index = rand_obj.nextInt(data.length);
                    data[index] ^= (1 << rand_obj.nextInt(8));
                }
                return;
            }
        }
    }

}

