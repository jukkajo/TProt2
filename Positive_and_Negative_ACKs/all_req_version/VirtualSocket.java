import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.io.IOException;
import java.util.Scanner;

class VirtualSocket {
    private DatagramSocket socket;
    private int port;
    private ReliabilityLayer relLayer = new ReliabilityLayer();
    private int transferMethod = 1; //1 for positive ACK, 2 for negative ACK, 3 for both
    private Scanner scanner = new Scanner(System.in);

    public VirtualSocket(int port) throws IOException {
        this.port = port;
        this.socket = new DatagramSocket(port);
    }

    public void setTransferMethod(int method) {
        this.transferMethod = method;
    }

    public void receive(DatagramPacket packet) throws IOException {
        socket.receive(packet);
    }

    public void send(DatagramPacket packet) throws IOException {
        switch (transferMethod) {
            case 1:
                //reliable transfer with positive ACKs
                reliableTransferPositiveACK(packet);
                break;
            case 2:
                //reliable transfer with negative ACKs
                reliableTransferNegativeACK(packet);
                break;
            case 3:
                //reliable transfer with both positive and negative ACKs
                reliableTransferBothACK(packet);
                break;
            default:
                // Invalid transfer method, do nothing
                break;
        }
    }

    public void showTransferMethodMenu(){
        //Show the menu for user to select the data transfer method
        System.out.println("Select data transfer method: ");
        System.out.println("1. Reliable data transfer with only positive ACKs");
        System.out.println("2. Reliable data transfer with only negative ACKs");
        System.out.println("3. Reliable data transfer with both positive and negative ACKs");
    }

    public int getTransferMethod(){
        // prompt the user to select a transfer method
        System.out.print("Select transfer method:");
        int method = scanner.nextInt();
        setTransferMethod(method);
        return method;
    }

    private void reliableTransferPositiveACK(DatagramPacket packet) throws IOException {
        //get the data from the packet
        byte[] data = packet.getData();
        data = relLayer.reliableTransferPositiveACK(data, socket, packet);
            // put the data back in the packet
    packet.setData(data);
    // send the packet
    socket.send(packet);
}

    private void reliableTransferNegativeACK(DatagramPacket packet) throws IOException {
        //get the data from the packet
        byte[] data = packet.getData();
        data = relLayer.reliableTransferNegativeACK(data, socket, packet);
        // put the data back in the packet
        packet.setData(data);
        // send the packet
        socket.send(packet);
    }

    private void reliableTransferBothACK(DatagramPacket packet) throws IOException {
        //get the data from the packet
        byte[] data = packet.getData();
        data = relLayer.reliableTransferBothACK(data, socket, packet);
        // put the data back in the packet
        packet.setData(data);
        // send the packet
        socket.send(packet);
    }
}

