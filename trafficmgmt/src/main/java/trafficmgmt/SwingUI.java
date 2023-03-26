package trafficmgmt;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class SwingUI extends JFrame implements ActionListener{
    static JButton button;  // Global variable for the button
    static JLabel label;    // Global variable for the label
    static JPanel panel;    // Global variable for the panel
    public static void main(String[] args) {

        // below is the main frame of the UI
        JFrame frame = new JFrame();
        frame.setVisible(true);
        frame.setTitle("Traffic Management System");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800,800);

        // below are some visual aspects(Icon,background color) of the UI tbd

        // ImageIcon icon = new ImageIcon("place_holder_icon.png");
        // frame.setIconImage(icon.getImage());
        // frame.getContentPane().setBackground(new Color(0,0,0));

        // below is the button for the admin login
        button = new JButton();
        button.setBounds(100, 100, 100, 100);
        button.addActionListener(new SwingUI());
        button.setFocusable(false);
        button.setText("Admin Login");
        button.setHorizontalAlignment(JButton.CENTER);
        button.setVerticalAlignment(JButton.CENTER);
        button.setBorder(BorderFactory.createEtchedBorder());
        frame.add(button);
        
        // below is the label for the admin login
        label = new JLabel();

        // below is the panel for the admin login
        panel = new JPanel();
        panel.setBorder(BorderFactory.createEmptyBorder(300, 300, 100, 300));
        panel.setLayout(null);
        panel.add(button);
        frame.add(panel);
        }

        public void actionPerformed(ActionEvent e) {
            // place holder method for the admin login button
            if (e.getSource() == button) {
                System.out.println("Admin Login");
            }
        }
}
