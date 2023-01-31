import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;

import javax.swing.JFrame;
import javax.swing.JLabel;
import java.awt.Color;
import java.awt.BorderLayout;

public class VirSoc_testiSov_v4 {
    private static VirtualSocket_v3 socket = null;
    private static JLabel tex_tLabel;

    public static void main(String[] args) throws IOException {
            
        TextFrame frame = new TextFrame("VirSoc_testiSov_v3");
        //open empty window
        frame.setVisible(true);
        boolean t_o_f = true;
        try {
            socket = new VirtualSocket_v3(6666);
            // Listen on the socket
            while (t_o_f) {
                byte[] r = new byte[256];
                DatagramPacket pket = new DatagramPacket(r, r.length-1);
                socket.receive(pket);
                String tex_t = new String(r, 0, pket.getLength());
                if(!socket.msg.equals("")) {
                    frame.setText(socket.msg);
                    socket.msg = "";
                }
                else {
                    frame.setText(tex_t);
                }
            }
        } catch (IOException e) {
            t_o_f = false;
        }
    }
}

