import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;

public class HelloSwing {
    public static void main(String[] args) {
        // All Swing work must run on the Event Dispatch Thread (EDT)
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Hello Swing");
            JLabel label = new JLabel("Welcome to Java Swing!", SwingConstants.CENTER);
            frame.add(label);                                     // goes to the content pane
            frame.setSize(400, 200);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // stop the JVM on close
            frame.setLocationRelativeTo(null);                    // centre on screen
            frame.setVisible(true);                               // always last
        });
    }
}