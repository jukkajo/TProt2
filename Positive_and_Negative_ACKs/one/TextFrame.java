import javax.swing.JFrame;
import javax.swing.JLabel;
import java.awt.Color;
import java.awt.BorderLayout;

public class TextFrame extends JFrame {
private JLabel textLabel;

public TextFrame(String title) {
    super(title);
    setLayout(new BorderLayout());
    textLabel = new JLabel();
    add(textLabel, BorderLayout.PAGE_START);
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setSize(350, 250);
    getContentPane().setBackground(new Color(0, 102, 51));
    textLabel.setVerticalAlignment(JLabel.TOP);
    textLabel.setForeground(Color.YELLOW);
}

public void setText(String text) {
    textLabel.setText(text);
}

public void setTextWithMsg(String text) {
    String msg = VirtualSocket_v2.msg;
    if (msg != null && !msg.isEmpty()) {
        textLabel.setText(text + "\n" + msg);
    } else {
        textLabel.setText(text);
    }
}

}
