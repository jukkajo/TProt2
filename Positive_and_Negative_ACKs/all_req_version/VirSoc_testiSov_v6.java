import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.io.IOException;
import javax.swing.JFrame;
import javax.swing.JLabel;
import java.awt.Color;
import java.awt.BorderLayout;
import java.util.Random;
import java.util.Arrays;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JTextField;
import java.awt.Dimension;


public class VirSoc_testiSov_v6 {
    private static VirtualSocket socket = null;
    private static ReliabilityLayer relLayer = new ReliabilityLayer();
    private static JLabel tex_tLabel;
    private static JButton button;
    private static JComboBox<String> comboBox;
    public static void main(String[] args) throws IOException {
        // Create the GUI
        JFrame frame = new JFrame("VirSoc_testiSov_v6(RDT 1.0)");
        frame.setLayout(new BorderLayout());
        
        JLabel noteLabel = new JLabel("<html>Note, last char of rendered text (byte) means<br>checksum of CRC8-method Textfield is<br>for setting port used, default is 6666</html>");
        frame.add(noteLabel, BorderLayout.PAGE_START);
        noteLabel.setBounds(10, 10, 200, 70);
        noteLabel.setVisible(true);
        noteLabel.setForeground(new Color(238, 130, 238));
        
        tex_tLabel = new JLabel();
        frame.add(tex_tLabel, BorderLayout.CENTER);
        button = new JButton("Set Method");
        frame.add(button, BorderLayout.PAGE_END);
        comboBox = new JComboBox<>();
        comboBox.addItem("Positive ACKs");
        comboBox.addItem("Negative ACKs");
        comboBox.addItem("Both ACKs");
        frame.add(comboBox, BorderLayout.PAGE_END);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(350, 250);
        frame.setVisible(true);
        frame.getContentPane().setBackground(new Color(0, 102, 51));
        tex_tLabel.setVerticalAlignment(JLabel.CENTER);
        tex_tLabel.setForeground(Color.YELLOW);
        JTextField socketNumberField = new JTextField();
        socketNumberField.setPreferredSize(new Dimension(120,20));
        //socketNumberField.setSize(100,20);
        frame.add(socketNumberField, BorderLayout.EAST);

        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedIndex = comboBox.getSelectedIndex();
                switch(selectedIndex) {
                    case 0:
                        socket.setTransferMethod(1);
                        break;
                    case 1:
                        socket.setTransferMethod(2);
                        break;
                    case 2:
                        socket.setTransferMethod(3);
                        break;
                }
            }
        });
        try {
            int socketNumber = 6666;
            try {
                socketNumber = Integer.parseInt(socketNumberField.getText());
                socket = new VirtualSocket(socketNumber);
            } catch (NumberFormatException e) {
                System.out.println("Invalid socket number, using default: 6666");
                socket = new VirtualSocket(socketNumber);
            }
            
            // Listen on the socket
            while (true) {
                byte[] r = new byte[256];
                DatagramPacket pket = new DatagramPacket(r, r.length);
                socket.receive(pket);
                byte[] data = pket.getData();
                byte[] reliableData = relLayer.addReliability(data);

                String tex_t = new String(reliableData);

                tex_tLabel.setText(tex_t);
            }
        } catch (IOException e) {
            System.out.println("Error receiving data: " + e);
        }
    }
}

