package trafficmgmt;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;

public class SwingUI extends JFrame implements ActionListener{
    static JButton button;  // Global variable for the button
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
        button.setBounds(100, 100, 200, 40);
        button.addActionListener(new SwingUI());
        button.setFocusable(false);
        button.setText("Admin Login");
        frame.add(button);
        }

        public void actionPerformed(ActionEvent e) {
            // place holder method for the admin login
            if (e.getSource() == button) {
                System.out.println("Admin Login");
            }
        }
}
