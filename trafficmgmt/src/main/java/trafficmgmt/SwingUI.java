package trafficmgmt;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.lang.constant.Constable;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.text.StyleConstants.FontConstants;

import trafficmgmt.utility.entitytype;
import trafficmgmt.utility.intersectionType;

public class SwingUI extends JFrame {
    private static String[] intersectionOptions = { "Two-Way", "Three-Way", "Four-Way" };
    private static String SUCCESS_MESSAGE = "Success! View Optimization Below.";
    private static String ROAD_EXTENSION = " (2)";
    private static Color SUCCESS_COLOUR = new Color(144, 238, 144);
    private static String EDITOR_RANGE_TEXT = "0";
    static intersectionType typ = intersectionType.FOUR_WAY;

    public static void main(String[] args) {

        // below is the initialization of frame ,panels, etc... of the UI
        JFrame frame = new JFrame("Traffic Management System");
        JPanel loginPanel = new JPanel();
        JPanel adminPanel = new JPanel();
        JPanel viewTimingPanel = new JPanel();
        JPanel manualChangePanel = new JPanel();
        JPanel optimizeTimingPanel = new JPanel();
        JPanel panelContainer = new JPanel();
        CardLayout cl = new CardLayout();

        frameSetup(frame); // setting up the size, close operation, etc... of the frame

        // below sets up the panelContainer that will hold all the other panels,
        // so that we can switch between them
        panelContainer.setLayout(cl);
        panelContainer.add(loginPanel, "login");
        panelContainer.add(adminPanel, "admin");
        panelContainer.add(viewTimingPanel, "viewTiming");
        panelContainer.add(manualChangePanel, "manualChange");
        panelContainer.add(optimizeTimingPanel, "optimizeTiming");
        cl.show(panelContainer, "login");
        frame.add(panelContainer);

        // below are the components of the login panel and admin panel
        loginPanelComponents(loginPanel, cl, panelContainer);
        adminPanelComponents(adminPanel, cl, panelContainer);
        viewTimingPanelComponents(viewTimingPanel, cl, panelContainer);
        manualChangePanelComponents(manualChangePanel, cl, panelContainer);
        optimizeTimingPanelComponents(optimizeTimingPanel, cl, panelContainer);
        // below are the Icon of the UI, tbd
        // ImageIcon icon = new ImageIcon("place_holder_icon.png");

        frame.setVisible(true);
    }

    private static void frameSetup(JFrame frame) {
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 800);
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

        JPasswordField passwordText = new JPasswordField(20);
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
                String password = String.valueOf(passwordText.getPassword());
                Boolean admin2 = ID.equals("admin.2") && password.equals("123456");

                if (ID.equals("admin.1") && password.equals("123456") || admin2) {
                    cl.show(panelContainer, "admin");

                } else {
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

    private static JLabel newLabel(String message) {
        JLabel field = new JLabel(message);
        field.setHorizontalAlignment(SwingConstants.CENTER);
        return field;
    }

    private static JTextField newField(String message) {
        JTextField field = new JTextField(message);
        field.setHorizontalAlignment(SwingConstants.CENTER);
        return field;
    }

    private static void optimizeTimingPanelComponents(JPanel panel, CardLayout cl, JPanel panelContainer) {
        panel.setLayout(null);

        JLabel intersectionLabel = new JLabel("Intersection Type");
        intersectionLabel.setBounds(300, 100, 200, 25);

        JComboBox options = new JComboBox<>(intersectionOptions);
        options.setSelectedItem("Four-Way");
        options.setBounds(425, 100, 200, 25);

        JCheckBox haveTrain = new JCheckBox("Train");
        haveTrain.setSelected(false);
        haveTrain.setBounds(100, 400, 200, 25);

        JLabel rangeInfo = newLabel("Car # Range: [0-999]");
        rangeInfo.setBounds(150, 400, 200, 25);

        JButton submitButton = new JButton("Submit");
        submitButton.setSelected(false);
        submitButton.setBounds(500, 400, 125, 25);

        JLabel statusLabel = new JLabel(SUCCESS_MESSAGE);
        statusLabel.setFont(new Font("Serif", Font.PLAIN, 24));
        statusLabel.setOpaque(true);
        statusLabel.setBackground(SUCCESS_COLOUR);
        statusLabel.setForeground(new Color(255, 255, 255));
        statusLabel.setHorizontalAlignment(SwingConstants.CENTER);
        statusLabel.setBounds(105, 430, 515, 75);

        JPanel editor = new JPanel();
        editor.setBounds(105, 150, 515, 225);
        editor.setBackground(Color.WHITE);

        JLabel roadLabel = newLabel("Road \\ # Cars");
        JLabel laneLabel = newLabel("Lane");
        JLabel leftTurn = newLabel("Left Turn");
        JLabel rightTurn = newLabel("Right Turn");

        JTextField roadOne = newField("Road A");
        JTextField roadOneLane = newField(EDITOR_RANGE_TEXT);
        JTextField roadOneLeft = newField(EDITOR_RANGE_TEXT);
        JTextField roadOneRight = newField(EDITOR_RANGE_TEXT);

        JLabel roadTwo = newLabel(roadOne.getText() + ROAD_EXTENSION);
        JTextField roadTwoLane = newField(EDITOR_RANGE_TEXT);
        JTextField roadTwoLeft = newField(EDITOR_RANGE_TEXT);
        JTextField roadTwoRight = newField(EDITOR_RANGE_TEXT);

        JTextField roadThree = newField("Road B");
        JTextField roadThreeLane = newField(EDITOR_RANGE_TEXT);
        JTextField roadThreeLeft = newField(EDITOR_RANGE_TEXT);
        JTextField roadThreeRight = newField(EDITOR_RANGE_TEXT);

        JLabel roadFour = newLabel(roadThree.getText() + ROAD_EXTENSION);
        JTextField roadFourLane = newField(EDITOR_RANGE_TEXT);
        JTextField roadFourLeft = newField(EDITOR_RANGE_TEXT);
        JTextField roadFourRight = newField(EDITOR_RANGE_TEXT);

        editor.setLayout(new GridLayout(5, 4));
        // row 1
        editor.add(roadLabel);
        editor.add(laneLabel);
        editor.add(leftTurn);
        editor.add(rightTurn);

        // row 2
        editor.add(roadOne);
        editor.add(roadOneLane);
        editor.add(roadOneLeft);
        editor.add(roadOneRight);

        // row 3
        editor.add(roadTwo);
        editor.add(roadTwoLane);
        editor.add(roadTwoLeft);
        editor.add(roadTwoRight);

        // row 4
        editor.add(roadThree);
        editor.add(roadThreeLane);
        editor.add(roadThreeLeft);
        editor.add(roadThreeRight);

        // row 5
        editor.add(roadFour);
        editor.add(roadFourLane);
        editor.add(roadFourLeft);
        editor.add(roadFourRight);

        JPanel optimizedViewport = new JPanel();
        optimizedViewport.setBounds(105, 525, 395, 225);
        optimizedViewport.setBackground(Color.WHITE);

        JButton applyButton = new JButton("Apply");
        applyButton.setSelected(false);
        applyButton.setFont(new Font("Serif", Font.PLAIN, 18));
        applyButton.setBounds(500, 600, 150, 70);

        options.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String option = (String) options.getSelectedItem();
                if (option.equals(intersectionOptions[0])) {
                    typ = intersectionType.TWO_WAY;

                    roadThree.setVisible(false);
                    roadThreeLane.setVisible(false);
                    roadThreeLeft.setVisible(false);
                    roadThreeRight.setVisible(false);

                    roadFour.setVisible(false);
                    roadFourLane.setVisible(false);
                    roadFourLeft.setVisible(false);
                    roadFourRight.setVisible(false);
                } else if (option.equals(intersectionOptions[1])) {
                    typ = intersectionType.THREE_WAY;

                    roadThree.setVisible(true);
                    roadThreeLane.setVisible(true);
                    roadThreeLeft.setVisible(true);
                    roadThreeRight.setVisible(true);

                    roadFour.setVisible(false);
                    roadFourLane.setVisible(false);
                    roadFourLeft.setVisible(false);
                    roadFourRight.setVisible(false);
                } else {
                    typ = intersectionType.FOUR_WAY;

                    roadThree.setVisible(true);
                    roadThreeLane.setVisible(true);
                    roadThreeLeft.setVisible(true);
                    roadThreeRight.setVisible(true);

                    roadFour.setVisible(true);
                    roadFourLane.setVisible(true);
                    roadFourLeft.setVisible(true);
                    roadFourRight.setVisible(true);
                }
            }
        });

        roadOne.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                roadTwo.setText(roadOne.getText() + ROAD_EXTENSION);
            }
        });

        roadThree.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                roadFour.setText(roadThree.getText() + ROAD_EXTENSION);
            }
        });

        panel.add(editor);
        panel.add(optimizedViewport);
        panel.add(intersectionLabel);
        panel.add(options);
        panel.add(haveTrain);
        panel.add(submitButton);
        panel.add(statusLabel);
        panel.add(applyButton);
        panel.add(rangeInfo);

        addBackButton(panel, panelContainer, cl, "admin");
    }

}
