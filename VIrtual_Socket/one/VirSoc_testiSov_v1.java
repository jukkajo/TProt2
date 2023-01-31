import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.io.IOException;
import javax.swing.JFrame;
import javax.swing.JLabel;
import java.awt.Color;
import java.awt.BorderLayout;
//@jukka j
public class VirSoc_testiSov_v1 {
    private static VirtualSocket socket = null;
    private static JLabel tex_tLabel;

    public static void main(String[] args) throws IOException {
        // Create the GUI
        JFrame frame = new JFrame("VirSoc_testiSov_v1");
        frame.setLayout(new BorderLayout());
        tex_tLabel = new JLabel();
        frame.add(tex_tLabel, BorderLayout.PAGE_START);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(350, 250);
        frame.setVisible(true);
        frame.getContentPane().setBackground(new Color(0, 102, 51));
        tex_tLabel.setVerticalAlignment(JLabel.TOP);
        tex_tLabel.setForeground(Color.YELLOW);
        
        tex_tLabel.setText("<html>Note, last char of rendered text (tavu)<br> means checksum of CRC8-method</html>");
        boolean t_o_f = true;
        try {
            socket = new VitualSocket(6666);
            // Listen on the socket
            while (t_o_f) {
                byte[] r = new byte[256];
                DatagramPacket pket = new DatagramPacket(r, r.length-1);
                socket.receive(pket);
                String tex_t = new String(r, 0, pket.getLength());
                tex_tLabel.setText(tex_t);
            }
        } catch (IOException e) {
            t_o_f = false;
        }
    }
}

