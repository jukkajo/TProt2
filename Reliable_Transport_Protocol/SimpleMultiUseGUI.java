import javax.swing.*;
import java.awt.*;
//@Jukka J
/*Very basic GUI, just for displaying text, i probably later add other methods for flexibility
and to justify name of class*/
public class SimpleMultiUseGUI {
    private JLabel label1;
    private JLabel label2;
    private String msg = "";
    private String header = "";
    public JFrame frame = new JFrame("SimpleMultiUseGUI");
    
    public SimpleMultiUseGUI() {
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 200);
    
        label1 = new JLabel("Use setHeader method to set this header");
        label2 = new JLabel("Use setMsg method to set this header");
        label2.setPreferredSize(new Dimension(350, 30));

        JPanel panel = new JPanel();
        panel.add(label1);
        panel.add(label2);
        frame.add(panel);

    }

    
    public void setMsg(String msg) {
        this.msg = msg;
        label2.setText(this.msg);
    }
    
    public void setHeader(String header) {
        this.header = header;
        label1.setText(this.header);
    }
    public void setBGColor(int[] rgblist) {
         frame.getContentPane().setBackground(new Color(rgblist[0], rgblist[1], rgblist[2]));
    }


    public void setFontColor(int[] rgblist, int labelnum) {
         if(labelnum == 1) { label1.setForeground(new Color(rgblist[0], rgblist[1], rgblist[2])); }
         else { label2.setForeground(new Color(rgblist[0], rgblist[1], rgblist[2])); }      
    }    
    
}

