import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;

public class Window {
    private int size;
    private int head;
    private int tail;
    private Packet[] buffer;
    private Queue<Integer> lostPackets;

    public Window(int size) {
        this.size = size;
        this.head = 0;
        this.tail = 0;
        this.buffer = new Packet[size];
        this.lostPackets = new LinkedList<>();
    }

    public int getHead() {
        return head;
    }

    public int getTail() {
        return tail;
    }

    public Packet getPacket(int index) {
        return buffer[index % size];
    }

    public boolean isPacketLost(int seqNum) {
        return lostPackets.contains(seqNum);
    }

    public void addPacket(Packet packet) {
        buffer[tail % size] = packet;
        tail++;
    }

    public void acknowledgePacket(int seqNum) {
        lostPackets.remove(seqNum);
        head = seqNum + 1;
    }

    public void markPacketLost(int seqNum) {
        if(seqNum >= head && seqNum < tail) {
            if (!lostPackets.contains(seqNum))
                lostPackets.add(seqNum);
        }
    }
}

