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

        // below is the initialization of frame ,panels, etc... of the UI
        JFrame frame = new JFrame("Traffic Management System");
        JPanel loginPanel = new JPanel();
        JPanel adminPanel = new JPanel();
        JPanel viewTimingPanel = new JPanel();
        JPanel manualChangePanel = new JPanel();
        JPanel optimizeTimingPanel = new JPanel();
        JPanel liveViewPanel = new JPanel();
        JPanel panelContainer = new JPanel();
        CardLayout cl = new CardLayout();
        threewayIntersection threewayDefault = new threewayIntersection(15, 10,10,"red","green");
        threewayDefault.startIntersection();

        frameSetup(frame);   //setting up the size, close operation, etc... of the frame

        //below sets up the panelContainer that will hold all the other panels,
        //so that we can switch between them
        panelContainer.setLayout(cl);                  
        panelContainer.add(loginPanel, "login");
        panelContainer.add(adminPanel, "admin");
        panelContainer.add(viewTimingPanel, "viewTiming");
        panelContainer.add(manualChangePanel, "manualChange");
        panelContainer.add(optimizeTimingPanel, "optimizeTiming");
        panelContainer.add(liveViewPanel, "liveView");
        cl.show(panelContainer, "login");
        frame.add(panelContainer);

        //below are the components of the login panel and admin panel
        loginPanelComponents(loginPanel, cl, panelContainer);
        adminPanelComponents(adminPanel, cl, panelContainer);
        viewTimingPanelComponents(viewTimingPanel, cl, panelContainer);
        manualChangePanelComponents(manualChangePanel, cl, panelContainer);
        optimizeTimingPanelComponents(optimizeTimingPanel, cl, panelContainer);
        liveViewPanelComponents(liveViewPanel, cl, panelContainer);
        // below are the Icon of the UI, tbd
        // ImageIcon icon = new ImageIcon("place_holder_icon.png");


        frame.setVisible(true);
        }




        private static void frameSetup(JFrame frame) {
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(800,800);
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






        private static void adminPanelComponents(JPanel panel, CardLayout cl, JPanel panelContainer) {
            panel.setLayout(null);

            JButton trafficTimingButton = new JButton("View Traffic timings");
            trafficTimingButton.setBounds(250, 200, 250, 80);
            panel.add(trafficTimingButton);

            JButton manualChangeButton = new JButton("Manual Change Traffic timings");
            manualChangeButton.setBounds(250, 320, 250, 80);
            panel.add(manualChangeButton);

            JButton createNewButton = new JButton("Optimize Traffic timings");
            createNewButton.setBounds(250, 440, 250, 80);
            panel.add(createNewButton);

            trafficTimingButton.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    cl.show(panelContainer, "viewTiming");
                }
            });
           
            manualChangeButton.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    cl.show(panelContainer, "manualChange");
                }
            });

            createNewButton.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    cl.show(panelContainer, "optimizeTiming");
                }
            });
        }





        private static void addBackButton(JPanel panel, JPanel panelContainer, CardLayout cl, String panelName) {
            JButton backButton = new JButton("Back");
            backButton.setBounds(10, 10, 80, 25);
            panel.add(backButton);

            backButton.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    cl.show(panelContainer, panelName);
                }
            });
        } 




        private static void viewTimingPanelComponents(JPanel panel, CardLayout cl, JPanel panelContainer) {
            panel.setLayout(null);

            JLabel viewTimingLabel = new JLabel("View Traffic Timings");
            viewTimingLabel.setBounds(300, 100, 200, 25);
            panel.add(viewTimingLabel);



            addBackButton(panel, panelContainer, cl, "admin");
        }

        private static void manualChangePanelComponents(JPanel panel, CardLayout cl, JPanel panelContainer) {
            panel.setLayout(null);

            JLabel manualChangeLabel = new JLabel("Manual Change Traffic Timings");
            manualChangeLabel.setBounds(300, 100, 200, 25);
            panel.add(manualChangeLabel);

            addBackButton(panel, panelContainer, cl, "admin");
        }

        private static void optimizeTimingPanelComponents(JPanel panel, CardLayout cl, JPanel panelContainer) {
            panel.setLayout(null);

            JLabel optimizeTimingLabel = new JLabel("Optimize Traffic Timings");
            optimizeTimingLabel.setBounds(300, 100, 200, 25);
            panel.add(optimizeTimingLabel);

            addBackButton(panel, panelContainer, cl, "admin");
        }

        private static void liveViewPanelComponents(JPanel panel, CardLayout cl, JPanel panelContainer) {
            panel.setLayout(null);

            JLabel liveViewLabel = new JLabel("Live View");
            liveViewLabel.setBounds(300, 100, 200, 25);
            panel.add(liveViewLabel);

            addBackButton(panel, panelContainer, cl, "admin");
        }

}

