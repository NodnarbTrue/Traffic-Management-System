package trafficmgmt;
import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class SwingUI extends JFrame{

    public static void main(String[] args) {

        // below is the main frame and panels of the UI
        JFrame frame = new JFrame("Traffic Management System");
        JPanel loginPanel = new JPanel();
        JPanel adminPanel = new JPanel();
        adminPanel.setBackground(Color.red);
        JPanel panelContainer = new JPanel();
        CardLayout cl = new CardLayout();

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800,800);
        panelContainer.setLayout(cl);
        panelContainer.add(loginPanel, "login");
        panelContainer.add(adminPanel, "admin");
        cl.show(panelContainer, "login");


        loginPanelComponents(loginPanel, cl, panelContainer);

        // below are some visual aspects(Icon,background color) of the UI tbd

        // ImageIcon icon = new ImageIcon("place_holder_icon.png");
        // frame.setIconImage(icon.getImage());
        // frame.getContentPane().setBackground(new Color(0,0,0));
        frame.add(panelContainer);
        frame.setVisible(true);
        }

        private static void loginPanelComponents(JPanel panel, CardLayout cl, JPanel panelContainer) {
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

            JLabel loginConfirmation = new JLabel("");
            loginConfirmation.setBounds(300, 450, 300, 25);
            panel.add(loginConfirmation);

            JButton loginButton = new JButton("Admin Login");
            loginButton.setBounds(300, 400, 160, 40);
            panel.add(loginButton);

            loginButton.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    String ID = IDText.getText();
                    String password = passwordText.getText();
                    Boolean admin2 = ID.equals("admin.2") && password.equals("123456");

                    if(ID.equals("admin.1") && password.equals("123456") || admin2) {
                        cl.show(panelContainer, "admin");
                        
                    }
                    else {
                        loginConfirmation.setText("Password or ID is incorrect");
                    }
                }
            });
        }

}

