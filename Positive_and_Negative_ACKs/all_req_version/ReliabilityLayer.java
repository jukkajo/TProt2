//@Jukka J
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.io.IOException;
import java.util.Random;

class ReliabilityLayer {
    public static final int POSITIVE_ACK = 1;
    public static final int NEGATIVE_ACK = 0;

    public byte[] addReliability(byte[] data) {
        // Calculate CRC8 of data
        byte crc = calculateCRC8(data);

        // Add CRC8 to end of data
        byte[] newData = new byte[data.length + 1];
        System.arraycopy(data, 0, newData, 0, data.length);
        newData[data.length] = crc;

        return newData;
    }

    public boolean checkData(byte[] data) {
        // Check if data is corrupted by comparing CRC8
        byte crc = calculateCRC8(data);
        return crc == data[data.length - 1];
    }

    public byte[] reliableTransferPositiveACK(byte[] data, DatagramSocket socket, DatagramPacket packet) throws IOException {
        // Implement reliable transfer with positive ACKs
        boolean received = false;
        while (!received) {
            // Send data
            socket.send(packet);

            // Wait for ACK
            byte[] r = new byte[1];
            DatagramPacket ackPacket = new DatagramPacket(r, r.length  );
            socket.receive(ackPacket);

            // Check if ACK is received
            if (r[0] == POSITIVE_ACK) {
                received = true;
            }
        }
        return data;
    }

    public byte[] reliableTransferNegativeACK(byte[] data, DatagramSocket socket, DatagramPacket packet) throws IOException {
        // Implement reliable transfer with negative ACKs
        boolean received = false;
        while (!received) {
            // Send data
            socket.send(packet);

            // Wait for ACK
            byte[] r = new byte[1];
            DatagramPacket ackPacket = new DatagramPacket(r, r.length);
            socket.receive(ackPacket);

            // Check if NACK is received
            if (r[0] == NEGATIVE_ACK) {
                received = false;
            } else {
                received = true;
            }
        }
        return data;
    }

    public byte[] reliableTransferBothACK(byte[] data, DatagramSocket socket, DatagramPacket packet) throws IOException {
        // Implement reliable transfer with both positive and negative ACKs
        boolean received = false;
        while (!received) {
            // Send data
            socket.send(packet);

            // Wait for ACK
            byte[] r = new byte[1];
            DatagramPacket ackPacket = new DatagramPacket(r, r.length);
            socket.receive(ackPacket);

            // Check if NACK is received
            if (r[0] == NEGATIVE_ACK) {
                //resend the data again
                continue;
            } else if (r[0] == POSITIVE_ACK) {
                received = true;
            }
        }
        return data;
    }

    private byte calculateCRC8(byte[] data) {
        // Implement CRC8 calculation here
        return 0;
    }
}

