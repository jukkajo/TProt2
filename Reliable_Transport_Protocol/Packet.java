import java.io.Serializable;
import java.util.zip.CRC32;
import java.net.InetAddress;
import java.util.Arrays;
//import java.net.SocketTimeoutException;

public class Packet implements Serializable{
    private static final long serialVersionUID = 1L;
    private int sequenceNumber;
    private byte[] payload;
    private long checksum;
    private InetAddress address;
    private int port;
    private int ackNumber;
    private byte flag;
    
    public Packet(int sequenceNumber, byte[] payload, int ackNumber, long checksum, byte flag, InetAddress address, int port) {
        this.sequenceNumber = sequenceNumber;
        this.payload = payload;
        this.ackNumber = ackNumber;
        this.checksum = checksum;
        this.flag = flag;
        this.address = address;
        this.port = port;
    }

    public InetAddress getAddress() {
        return address;
    }

    public int getPort() {
        return port;
    }

    public int getSequenceNumber() {
        return sequenceNumber;
    }

    public byte[] getPayload() {
        return payload;
    }

    public long getChecksum() {
        return checksum;
    }
    
    public void setAddress(InetAddress address) {
        this.address = address;
    }
    
    public void setPort(int port) {
        this.port = port;
    }
    
    public long calculateChecksum() {
        CRC32 crc = new CRC32();
        crc.update(payload);
        return crc.getValue();
    }

    public boolean isValid() {
        return calculateChecksum() == checksum;
    }
    
    public byte[] toByteArray(){
        byte[] seqArr = intToByteArray(sequenceNumber);
        byte[] checkArr = longToByteArray(checksum);
        byte[] finalArr = new byte[seqArr.length + payload.length + checkArr.length];
        System.arraycopy(seqArr, 0, finalArr, 0, seqArr.length);
        System.arraycopy(payload, 0, finalArr, seqArr.length, payload.length);
        System.arraycopy(checkArr, 0, finalArr, seqArr.length + payload.length, checkArr.length);
        return finalArr;
    }
    public static Packet fromByteArray(byte[] data, InetAddress address, int port) {
        int sequenceNumber = byteArrayToInt(Arrays.copyOfRange(data, 0, 4));
        byte[] payload = Arrays.copyOfRange(data, 4, data.length - 8);
        long checksum = byteArrayToLong(Arrays.copyOfRange(data, data.length - 8, data.length));
        return new Packet(sequenceNumber, payload, -1, checksum, (byte)0, address, port);
    }


    private byte[] intToByteArray(int a){
        return new byte[] {
            (byte) ((a >> 24) & 0xFF),
            (byte) ((a >> 16) & 0xFF),   
            (byte) ((a >> 8) & 0xFF),   
            (byte) (a & 0xFF)
        };
    }
    
    private byte[] longToByteArray(long value) {
        return new byte[] {
            (byte)(value >>> 56 & 0xff),
            (byte)(value >>> 48 & 0xff),
            (byte)(value >>> 40 & 0xff),
            (byte)(value >>> 32 & 0xff),
            (byte)(value >>> 24 & 0xff),
            (byte)(value >>> 16 & 0xff),
            (byte)(value >>> 8 & 0xff),
            (byte)(value & 0xff)
        };
    }

public static int byteArrayToInt(byte[] b) {
    return   b[3] & 0xFF |
            (b[2] & 0xFF) << 8 |
            (b[1] & 0xFF) << 16 |
            (b[0] & 0xFF) << 24;
}

public static long byteArrayToLong(byte[] b) {
    return (b[7] & 0xFFL) + ((b[6] & 0xFFL) << 8) + ((b[5] & 0xFFL) << 16) + ((b[4] & 0xFFL) << 24)
            + ((b[3] & 0xFFL) << 32) + ((b[2] & 0xFFL) << 40) + ((b[1] & 0xFFL) << 48) + ((b[0] & 0xFFL) << 56);
}


    
}

