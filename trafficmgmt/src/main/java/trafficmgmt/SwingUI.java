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
import javax.swing.JTextField;

public class SwingUI extends JFrame implements ActionListener{
    static JButton loginButton;  // Global variable for the loginButton
    static JLabel idLabel;    // Global variable for the label

    public static void main(String[] args) {

        // below is the main frame and panel of the UI
        JFrame frame = new JFrame("Traffic Management System");
        JPanel panel = new JPanel();
        // frame.setTitle("Traffic Management System");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800,800);
        frame.add(panel);

        placeComponents(panel);

        // below are some visual aspects(Icon,background color) of the UI tbd

        // ImageIcon icon = new ImageIcon("place_holder_icon.png");
        // frame.setIconImage(icon.getImage());
        // frame.getContentPane().setBackground(new Color(0,0,0));


        frame.setVisible(true);
        }

        private static void placeComponents(JPanel panel) {
            panel.setLayout(null);

            JLabel IDLabel = new JLabel("ID");
            IDLabel.setBounds(270, 300, 80, 25);
            panel.add(IDLabel);

            JTextField IDText = new JTextField(20);
            IDText.setBounds(300, 300, 165, 25);
            panel.add(IDText);

            JLabel passwordLabel = new JLabel("Password");
            passwordLabel.setBounds(230, 350, 80, 25);
            panel.add(passwordLabel);

            JTextField passwordText = new JTextField(20);
            passwordText.setBounds(300, 350, 165, 25);
            panel.add(passwordText);
    
            loginButton = new JButton("Admin Login");
            loginButton.setBounds(300, 400, 160, 40);
            panel.add(loginButton);
        }

        public void actionPerformed(ActionEvent e) {
            // place holder method for the admin login loginButton
            if (e.getSource() == loginButton) {
                System.out.println("Admin Login");
            }
        }
}
