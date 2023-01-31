//@Jukka J
/* usage:
byte[] data = {0x01, 0x02, 0x03};
int checksum = CRC8.calculate(data);
*/
public class CRC8 {
    private static int[] TABLE;
    
    /**
     * Calculates the CRC8 checksum for the given data.
     *
     * @param data the data to calculate the checksum for
     * @return the CRC8 checksum
     */
    public static int calculate(byte[] data) {
        // check if TABLE array is initialized for the given data length
        if (TABLE == null || TABLE.length != data.length) {
    // initialize TABLE array with pre-calculated values
    TABLE = new int[data.length];
    for (int i = 0; i < data.length; i++) {
        int crc = i;
        for (int j = 0; j < 8; j++) {
            if ((crc & 1) == 1) {
                crc = (crc >>> 1) ^ 0x8c;
            } else {
                crc = (crc >>> 1);
            }
        }
        TABLE[i] = crc;
    }

        
        int crc = 0;
        for (int i = 0; i < data.length; i++) {
            crc = TABLE[((crc & 0xff) ^ data[i]) & 0xff];
        }
        return crc;
    }
}
