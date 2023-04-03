package trafficmgmt;

import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.Vector;
import java.util.concurrent.TimeUnit;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.Timer;

import java.awt.Graphics;

import javax.swing.SwingConstants;
import trafficmgmt.utility.intersectionType;
import trafficmgmt.utility.lightState;
import trafficmgmt.utility.crosswalkState;
import trafficmgmt.utility.direction;
import trafficmgmt.utility.timerlengthinformation;;

public class SwingUI extends JFrame {
    private static String[] intersectionOptions = { "Two-Way", "Three-Way", "Four-Way" };
    private static String ERROR_MESSAGE = "Error: Expected integers in range [0-999]";
    private static String SUCCESS_MESSAGE = "Success! View Optimization Below.";
    private static String ROAD_EXTENSION = " (2)";
    private static Color SUCCESS_COLOUR = new Color(144, 238, 144);
    private static Color FAILURE_COLOUR = new Color(255, 114, 118);
    private static String EDITOR_RANGE_TEXT = "0";
    private static sysadmin Admin = new sysadmin(0);

    // JFrame components
    private static JLabel intersectionTypeLabel;
    private static JLabel greenLightLabel;
    private static JLabel yellowLightLabel;
    private static JLabel leftLightLabel;
    private static JLabel crosswalkLightLabel;

    // Live intersection picker
    private static DefaultComboBoxModel intersectionListModel;
    private static JComboBox intersectionListOptions;
    private static Intersection currentIntersection;

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
        JPanel liveViewPanel = new JPanel();
        CardLayout cl = new CardLayout();
        initializeListJComboBox();
        threewayIntersection dummyIntersection = new threewayIntersection(25, 25, 10, "glue street", "paper street");
        currentIntersection = dummyIntersection;

        frameSetup(frame); // setting up the size, close operation, etc... of the frame

        // below sets up the panelContainer that will hold all the other panels,
        // so that we can switch between them
        panelContainer.setLayout(cl);
        panelContainer.add(loginPanel, "login");
        panelContainer.add(adminPanel, "admin");
        panelContainer.add(viewTimingPanel, "viewTiming");
        panelContainer.add(manualChangePanel, "manualChange");
        panelContainer.add(optimizeTimingPanel, "optimizeTiming");
        panelContainer.add(liveViewPanel, "liveView");
        cl.show(panelContainer, "login");
        frame.add(panelContainer);

        // below are the components of the login panel and admin panel
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
                // String ID = IDText.getText();
                String ID = "admin.1";
                // String password = String.valueOf(passwordText.getPassword());
                String password = "123456";
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
        panel.removeAll();
        panel.validate();
        panel.repaint();

        panel.setLayout(null);
        JButton liveViewButton = new JButton("Live View");
        liveViewButton.setBounds(250, 80, 250, 80);
        panel.add(liveViewButton);

        JButton trafficTimingButton = new JButton("View Traffic timings");
        trafficTimingButton.setBounds(250, 200, 250, 80);
        panel.add(trafficTimingButton);

        JButton manualChangeButton = new JButton("Create and Edit Intersections");
        manualChangeButton.setBounds(250, 320, 250, 80);
        panel.add(manualChangeButton);

        JButton createNewButton = new JButton("Optimize Traffic timings");
        createNewButton.setBounds(250, 440, 250, 80);
        panel.add(createNewButton);

        liveViewButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                cl.show(panelContainer, "liveView");
            }
        });

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

    private static void addToIntersectionListPicker(String name) {
        intersectionListModel.addElement(name);
        intersectionListOptions.setModel(intersectionListModel);
        intersectionListModel.setSelectedItem(name);
    }

    private static void initializeListJComboBox() {
        intersectionListModel = new DefaultComboBoxModel();
        intersectionListOptions = new JComboBox<>();
    }

    private static void trafficlightShapeChange(JLabel shape, trafficlight light) {
        lightState text = light.getCurrentLightState();
        if (text == lightState.LEFT_TURN || text == lightState.GREEN) { shape.setBackground(new Color(0, 255, 0)); }
        else if (text == lightState.YELLOW) { shape.setBackground(new Color(255, 255, 0)); }
        else if (text == lightState.RED) { shape.setBackground(new Color(255, 0, 0)); }
        shape.setText("" + text);
    }

    private static void crosswalkShapeChange(JLabel shape1, JLabel shape2, crosswalk light) {
        crosswalkState text = light.getCurrentCrossWalkState();
        if (text == crosswalkState.WALK) { 
            shape1.setBackground(new Color(255, 255, 255)); shape2.setBackground(new Color(255, 255, 255)); 
            shape1.setText("" + text); shape2.setText("" + text); }
        else if (text == crosswalkState.STOP) { 
            shape1.setBackground(new Color(255, 0, 0)); shape2.setBackground(new Color(255, 0, 0)); 
            shape1.setText("" + text); shape2.setText("" + text); }
        else if (text == crosswalkState.COUNTDOWN) { 
            shape1.setBackground(new Color(255, 100, 100)); shape2.setBackground(new Color(255, 100, 100));
            shape1.setText(Integer.toString(light.currentcrosswalkCountDownNumber)); shape2.setText(Integer.toString(light.currentcrosswalkCountDownNumber)); }
    }

    private static void liveViewPanelComponents(JPanel panel, CardLayout cl, JPanel panelContainer) {

        // dummyIntersection.stopIntersection();
        panel.setLayout(null);

        // Back button
        addBackButton(panel, panelContainer, cl, "admin");

        // Label
        JLabel viewTimingLabel = new JLabel("[Live Traffic Timings]");
        viewTimingLabel.setBounds(100, 100, 400, 25);
        panel.add(viewTimingLabel);

        // Label for choosing which intersection to display
        JLabel optionsLabel = new JLabel("Intersection to inspect/view: ");
        optionsLabel.setBounds(375, 100, 400, 25);
        panel.add(optionsLabel);

        // Dropdown box to list intersection options
        intersectionListOptions.setBounds(550, 100, 150, 25);
        panel.add(intersectionListOptions);

        // Label of timer to show the time left for the current direction
        JLabel totalLenLabel = new JLabel("");
        totalLenLabel.setBounds(100, 125, 200, 25);
        panel.add(totalLenLabel);

        // Label to show the type of intersection
        intersectionTypeLabel = new JLabel("Type of Intersection: " + currentIntersection.intersectionClassification);
        intersectionTypeLabel.setBounds(375, 125, 400, 25);
        panel.add(intersectionTypeLabel);

        JLabel dirOneStates = new JLabel("");
        dirOneStates.setBounds(100, 150, 1000, 25);
        panel.add(dirOneStates);

        JLabel dirOneCrosswalkStates = new JLabel("");
        dirOneCrosswalkStates.setBounds(100, 175, 1000, 25);
        panel.add(dirOneCrosswalkStates);

        JLabel dirTwoStates = new JLabel("");
        dirTwoStates.setBounds(100, 200, 1000, 25);
        panel.add(dirTwoStates);

        JLabel dirTwoCrosswalkStates = new JLabel("");
        dirTwoCrosswalkStates.setBounds(100, 225, 1000, 25);
        panel.add(dirTwoCrosswalkStates);


        // INTERSECTION SHAPES
        JLabel roadOneLight1Shape = new JLabel("");
        roadOneLight1Shape.setBounds(100, 425, 100, 50);
        roadOneLight1Shape.setBackground(new Color(0, 255, 0));
        roadOneLight1Shape.setOpaque(true); 
        panel.add(roadOneLight1Shape);
        roadOneLight1Shape.setVisible(false);
        
        JLabel roadOneLight2Shape = new JLabel("");
        roadOneLight2Shape.setBounds(600, 425, 100, 50);
        roadOneLight2Shape.setBackground(new Color(0, 255, 0));
        roadOneLight2Shape.setOpaque(true); 
        panel.add(roadOneLight2Shape);
        roadOneLight2Shape.setVisible(false);

        JLabel roadTwoLight1Shape = new JLabel("");
        roadTwoLight1Shape.setBounds(350, 675, 100, 50);
        roadTwoLight1Shape.setBackground(new Color(255, 0, 0));
        roadTwoLight1Shape.setOpaque(true); 
        panel.add(roadTwoLight1Shape);
        roadTwoLight1Shape.setVisible(false);

        JLabel roadTwoLight2Shape = new JLabel("");
        roadTwoLight2Shape.setBounds(350, 175, 100, 50);
        roadTwoLight2Shape.setBackground(new Color(255, 0, 0));
        roadTwoLight2Shape.setOpaque(true); 
        panel.add(roadTwoLight2Shape);
        roadTwoLight2Shape.setVisible(false);


        // Crosswalk DirOne Bottom Left
        JLabel dirOneCrossBotLShape = new JLabel("");
        dirOneCrossBotLShape.setBounds(325, 550, 50, 50);
        dirOneCrossBotLShape.setBackground(new Color(255, 0, 0));
        dirOneCrossBotLShape.setOpaque(true); 
        panel.add(dirOneCrossBotLShape);
        dirOneCrossBotLShape.setVisible(false);

        // Crosswalk DirOne Bottom Right
        JLabel dirOneCrossBotRShape = new JLabel("");
        dirOneCrossBotRShape.setBounds(425, 550, 50, 50);
        dirOneCrossBotRShape.setBackground(new Color(255, 0, 0));
        dirOneCrossBotRShape.setOpaque(true); 
        panel.add(dirOneCrossBotRShape);
        dirOneCrossBotRShape.setVisible(false);

        // Crosswalk DirOne Top Left
        JLabel dirOneCrossTopLShape = new JLabel("");
        dirOneCrossTopLShape.setBounds(325, 300, 50, 50);
        dirOneCrossTopLShape.setBackground(new Color(255, 0, 0));
        dirOneCrossTopLShape.setOpaque(true); 
        panel.add(dirOneCrossTopLShape);
        dirOneCrossTopLShape.setVisible(false);

        // Crosswalk DirOne Top Right
        JLabel dirOneCrossTopRShape = new JLabel("");
        dirOneCrossTopRShape.setBounds(425, 300, 50, 50);
        dirOneCrossTopRShape.setBackground(new Color(255, 0, 0));
        dirOneCrossTopRShape.setOpaque(true); 
        panel.add(dirOneCrossTopRShape);
        dirOneCrossTopRShape.setVisible(false);

        // Crosswalk DirTwo Bottom Left
        JLabel dirTwoCrossBotLShape = new JLabel("");
        dirTwoCrossBotLShape.setBounds(250, 475, 50, 50);
        dirTwoCrossBotLShape.setBackground(new Color(255, 0, 0));
        dirTwoCrossBotLShape.setOpaque(true); 
        panel.add(dirTwoCrossBotLShape);
        dirTwoCrossBotLShape.setVisible(false);

        // Crosswalk DirTwo Bottom Right
        JLabel dirTwoCrossBotRShape = new JLabel("");
        dirTwoCrossBotRShape.setBounds(500, 475, 50, 50);
        dirTwoCrossBotRShape.setBackground(new Color(255, 0, 0));
        dirTwoCrossBotRShape.setOpaque(true); 
        panel.add(dirTwoCrossBotRShape);
        dirTwoCrossBotRShape.setVisible(false);

        // Crosswalk DirTwo Top Left
        JLabel dirTwoCrossTopLShape = new JLabel("");
        dirTwoCrossTopLShape.setBounds(250, 375, 50, 50);
        dirTwoCrossTopLShape.setBackground(new Color(255, 0, 0));
        dirTwoCrossTopLShape.setOpaque(true); 
        panel.add(dirTwoCrossTopLShape);
        dirTwoCrossTopLShape.setVisible(false);

        // Crosswalk DirTwo Top Right
        JLabel dirTwoCrossTopRShape = new JLabel("");
        dirTwoCrossTopRShape.setBounds(500, 375, 50, 50);
        dirTwoCrossTopRShape.setBackground(new Color(255, 0, 0));
        dirTwoCrossTopRShape.setOpaque(true); 
        panel.add(dirTwoCrossTopRShape);
        dirTwoCrossTopRShape.setVisible(false);

        // RoadOne
        JLabel roadOneShape = new JLabel("");
        roadOneShape.setBounds(200, 400, 400, 100);
        roadOneShape.setBackground(new Color(100, 100, 100));
        roadOneShape.setOpaque(true);
        panel.add(roadOneShape);
        roadOneShape.setVisible(false);

        // RoadTwo Bottom
        JLabel roadTwo1Shape = new JLabel("");
        roadTwo1Shape.setBounds(350, 450, 100, 200);
        roadTwo1Shape.setBackground(new Color(100, 100, 100));
        roadTwo1Shape.setOpaque(true); 
        panel.add(roadTwo1Shape);
        roadTwo1Shape.setVisible(false);

        // RoadTwo Top
        JLabel roadTwo2Shape = new JLabel("");
        roadTwo2Shape.setBounds(350, 250, 100, 200);
        roadTwo2Shape.setBackground(new Color(100, 100, 100));
        roadTwo2Shape.setOpaque(true); 
        panel.add(roadTwo2Shape);
        roadTwo2Shape.setVisible(false);

        // Create background
        JLabel backgound1Shape = new JLabel("");
        backgound1Shape.setBounds(75, 75, 650, 675);
        backgound1Shape.setBackground(new Color(200, 200, 200));
        backgound1Shape.setOpaque(true); 
        panel.add(backgound1Shape);

        // Change intersection display
        intersectionListOptions.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                
                String option = (String) intersectionListOptions.getSelectedItem();
                currentIntersection = Admin.getHashedIntersection(option);
                typ = currentIntersection.intersectionClassification;

                if (typ == intersectionType.TWO_WAY) {
                    roadOneLight1Shape.setVisible(true);
                    roadOneLight2Shape.setVisible(true);
                    roadTwoLight1Shape.setVisible(false);
                    roadTwoLight2Shape.setVisible(false);
                    dirOneCrossBotLShape.setVisible(true);
                    dirOneCrossBotRShape.setVisible(true);
                    dirOneCrossTopLShape.setVisible(true);
                    dirOneCrossTopRShape.setVisible(true);
                    dirTwoCrossBotLShape.setVisible(true);
                    dirTwoCrossBotRShape.setVisible(true);
                    dirTwoCrossTopLShape.setVisible(true);
                    dirTwoCrossTopRShape.setVisible(true);
                    roadOneShape.setVisible(true);
                    roadTwo1Shape.setVisible(false);
                    roadTwo2Shape.setVisible(false); }
                
                else if (typ == intersectionType.THREE_WAY) {
                    roadOneLight1Shape.setVisible(true);
                    roadOneLight2Shape.setVisible(true);
                    roadTwoLight1Shape.setVisible(true);
                    roadTwoLight2Shape.setVisible(false);
                    dirOneCrossBotLShape.setVisible(true);
                    dirOneCrossBotRShape.setVisible(true);
                    dirOneCrossTopLShape.setVisible(false);
                    dirOneCrossTopRShape.setVisible(false);
                    dirTwoCrossBotLShape.setVisible(true);
                    dirTwoCrossBotRShape.setVisible(true);
                    dirTwoCrossTopLShape.setVisible(true);
                    dirTwoCrossTopRShape.setVisible(true);
                    roadOneShape.setVisible(true);
                    roadTwo1Shape.setVisible(true);
                    roadTwo2Shape.setVisible(false); }
                
                else if (typ == intersectionType.FOUR_WAY) {
                    roadOneLight1Shape.setVisible(true);
                    roadOneLight2Shape.setVisible(true);
                    roadTwoLight1Shape.setVisible(true);
                    roadTwoLight2Shape.setVisible(true);
                    dirOneCrossBotLShape.setVisible(true);
                    dirOneCrossBotRShape.setVisible(true);
                    dirOneCrossTopLShape.setVisible(true);
                    dirOneCrossTopRShape.setVisible(true);
                    dirTwoCrossBotLShape.setVisible(true);
                    dirTwoCrossBotRShape.setVisible(true);
                    dirTwoCrossTopLShape.setVisible(true);
                    dirTwoCrossTopRShape.setVisible(true);
                    roadOneShape.setVisible(true);
                    roadTwo1Shape.setVisible(true);
                    roadTwo2Shape.setVisible(true); }
            }
        });

        // Updates the values in live view
        ActionListener taskPerformer = new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (currentIntersection != null) {
                    intersectionTypeLabel.setText("Type of Intersection: " + currentIntersection.intersectionClassification);
                    typ = currentIntersection.intersectionClassification;

                    // Set roadOneLight1 and roadOneLight2 colours
                    trafficlightShapeChange(roadOneLight1Shape, currentIntersection.directionOneTrafficLights.get(0));
                    trafficlightShapeChange(roadOneLight2Shape, currentIntersection.directionOneTrafficLights.get(1)); 
                    
                    // Set dirOne1, dirTwo1, and dirTwo2 crosswalks
                    crosswalkShapeChange(dirOneCrossBotLShape, dirOneCrossBotRShape, currentIntersection.directionOneCrosswalks.get(0));
                    crosswalkShapeChange(dirTwoCrossBotLShape, dirTwoCrossTopLShape, currentIntersection.directionTwoCrosswalks.get(0));
                    crosswalkShapeChange(dirTwoCrossBotRShape, dirTwoCrossTopRShape, currentIntersection.directionTwoCrosswalks.get(1));

                    if (typ == intersectionType.TWO_WAY) {
                        crosswalkShapeChange(dirOneCrossTopLShape, dirOneCrossTopRShape, currentIntersection.directionOneCrosswalks.get(1)); }

                    else if (typ == intersectionType.THREE_WAY) {
                        trafficlightShapeChange(roadTwoLight1Shape, currentIntersection.directionTwoTrafficLights.get(0)); }

                    else if (typ == intersectionType.FOUR_WAY) { 
                        crosswalkShapeChange(dirOneCrossTopLShape, dirOneCrossTopRShape, currentIntersection.directionOneCrosswalks.get(1));
                        trafficlightShapeChange(roadTwoLight1Shape, currentIntersection.directionTwoTrafficLights.get(0));
                        trafficlightShapeChange(roadTwoLight2Shape, currentIntersection.directionTwoTrafficLights.get(1)); }
                    
                    totalLenLabel.setText("Direction Total Length Left: " + currentIntersection.curerntDirectionTiming);
                    


                    /*String output = "Direction One Light States -> "; int j = 0;
                    for (trafficlight i : currentIntersection.directionOneTrafficLights) {
                        j++; output += "#" + j + " [" + i.getCurrentLightState() + "] "; }
                    dirOneStates.setText(output);

                    output = "Direction Two Light States -> "; j = 0;
                    for (trafficlight i : currentIntersection.directionTwoTrafficLights) {
                        j++; output += "#" + j + " [" + i.getCurrentLightState() + "] "; }
                    dirTwoStates.setText(output);

                    output = "Direction One Crosswalk States -> "; j = 0;
                    for (crosswalk i : currentIntersection.directionOneCrosswalks) {
                        j++; output += "#" + j + " [State: " + i.getCurrentCrossWalkState();
                        if (i.getCurrentCrossWalkState() == crosswalkState.COUNTDOWN) {
                            output += " timer: " + i.currentcrosswalkCountDownNumber + "] "; } 
                        else { output += "] "; } }
                    dirOneCrosswalkStates.setText(output);

                    output = "Direction Two Crosswalk States -> "; j = 0;
                    for (crosswalk i : currentIntersection.directionTwoCrosswalks) {
                        j++; output += "#" + j + " [State: " + i.getCurrentCrossWalkState();
                        if (i.getCurrentCrossWalkState() == crosswalkState.COUNTDOWN) {
                            output += " timer: " + i.currentcrosswalkCountDownNumber + "]  "; } 
                        else { output += "] "; } }
                    dirTwoCrosswalkStates.setText(output);

                    output = "Direction Total Length Left: ";
                    output += currentIntersection.curerntDirectionTiming;
                    totalLenLabel.setText(output);
                    */
                }
            }

        };

        Timer timer = new Timer(1000, taskPerformer);
        timer.setRepeats(true);
        timer.start();

    }

    private static void updateTimingPanelComponents(Intersection currentlyDisplayingIntersection) {
        intersectionTypeLabel.setText("Type of Intersection: " + typ);
        greenLightLabel
                .setText("Intersection Current Green Light Length: " + currentlyDisplayingIntersection
                        .getLengthInformationFromCurrentDirection(timerlengthinformation.Direction_LENGTH));
        yellowLightLabel
                .setText("Intersection Current Yellow Light Length: " + currentlyDisplayingIntersection
                        .getLengthInformationFromCurrentDirection(timerlengthinformation.YELLOW_LIGHT_LENGTH));
        leftLightLabel
                .setText("Intersection Current Left Light Length: " + currentlyDisplayingIntersection
                        .getLengthInformationFromCurrentDirection(timerlengthinformation.LEFT_TURN_LENGTH));
        crosswalkLightLabel.setText("Crosswalk Countdown Length: " + currentlyDisplayingIntersection
                .getLengthInformationFromCurrentDirection(timerlengthinformation.CROSSWALK_COUTDOWN_LENGTH));
    }

    private static void viewTimingPanelComponents(JPanel panel, CardLayout cl, JPanel panelContainer) {

        // Set layout
        panel.setLayout(null);

        // Back button
        addBackButton(panel, panelContainer, cl, "admin");

        // Label to show traffic timings
        JLabel viewTimingLabel = new JLabel("View Traffic Timings");
        viewTimingLabel.setBounds(100, 100, 400, 25);
        panel.add(viewTimingLabel);

        // Label to choose which intersection to view
        JLabel optionsLabel = new JLabel("Intersection to inspect/view: ");
        optionsLabel.setBounds(100, 150, 400, 25);
        panel.add(optionsLabel);

        // Dropdown to choose the intersection
        JComboBox localJComboBox = new JComboBox<>();
        localJComboBox.setModel(intersectionListModel);
        localJComboBox.setBounds(325, 150, 150, 25);
        panel.add(localJComboBox);

        JLabel localIntersectionTypeLabel = new JLabel(
                "Type of Intersection: " + currentIntersection.intersectionClassification);
        localIntersectionTypeLabel.setBounds(100, 200, 400, 25);
        panel.add(localIntersectionTypeLabel);

        greenLightLabel = new JLabel("Intersection Current Green Light Length: " + currentIntersection
                .getLengthInformationFromCurrentDirection(timerlengthinformation.Direction_LENGTH));
        greenLightLabel.setBounds(100, 250, 400, 25);
        panel.add(greenLightLabel);

        yellowLightLabel = new JLabel("Intersection Current Yellow Light Length: " + currentIntersection
                .getLengthInformationFromCurrentDirection(timerlengthinformation.YELLOW_LIGHT_LENGTH));
        yellowLightLabel.setBounds(100, 300, 400, 25);
        panel.add(yellowLightLabel);

        leftLightLabel = new JLabel("Intersection Current Left Light Length: " + currentIntersection
                .getLengthInformationFromCurrentDirection(timerlengthinformation.LEFT_TURN_LENGTH));
        leftLightLabel.setBounds(100, 350, 400, 25);
        panel.add(leftLightLabel);

        crosswalkLightLabel = new JLabel("Crosswalk Light Length: " + currentIntersection
                .getLengthInformationFromCurrentDirection(timerlengthinformation.CROSSWALK_COUTDOWN_LENGTH));
        crosswalkLightLabel.setBounds(100, 400, 400, 25);
        panel.add(crosswalkLightLabel);

        // Create background
        JLabel backgound1Shape = new JLabel("");
        backgound1Shape.setBounds(75, 75, 625, 375);
        backgound1Shape.setBackground(new Color(255, 255, 255));
        backgound1Shape.setOpaque(true); 
        panel.add(backgound1Shape);

        localJComboBox.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String option = (String) intersectionListOptions.getSelectedItem();
                currentIntersection = Admin.getHashedIntersection(option);
                if (currentIntersection != null) {
                    localIntersectionTypeLabel.setText(
                            "Type of Intersection: " + currentIntersection.intersectionClassification);
                    greenLightLabel
                            .setText("Intersection Current Green Light Length: " + currentIntersection
                                    .getLengthInformationFromCurrentDirection(timerlengthinformation.Direction_LENGTH));
                    yellowLightLabel
                            .setText("Intersection Current Yellow Light Length: " + currentIntersection
                                    .getLengthInformationFromCurrentDirection(
                                            timerlengthinformation.YELLOW_LIGHT_LENGTH));
                    leftLightLabel.setText("Intersection Current Left Light Length: " + currentIntersection
                            .getLengthInformationFromCurrentDirection(timerlengthinformation.LEFT_TURN_LENGTH));
                    crosswalkLightLabel.setText("Crosswalk Countdown Length: " + currentIntersection
                            .getLengthInformationFromCurrentDirection(
                                    timerlengthinformation.CROSSWALK_COUTDOWN_LENGTH));
                }
            }
        });

    }

    private static void manualChangePanelComponents(JPanel panel, CardLayout cl, JPanel panelContainer) {
        panel.setLayout(null);

        // Create intersection header
        JLabel createLabel = new JLabel("[Create An Intersection]");
        createLabel.setBounds(100, 100, 200, 25);
        panel.add(createLabel);

        // Set intersection Name label and text area
        JLabel intersectionName = new JLabel("Intersection Name/ID:");
        intersectionName.setBounds(250, 100, 150, 25);
        panel.add(intersectionName);

        JTextField IDText = new JTextField(20);
        IDText.setBounds(450, 100, 150, 25);
        panel.add(IDText);

        // pick intersection type label and options
        JLabel typeOfIntersection = new JLabel("Intersection Type:");
        typeOfIntersection.setBounds(250, 150, 150, 25);
        panel.add(typeOfIntersection);

        JComboBox options = new JComboBox<>(intersectionOptions);
        options.setSelectedItem("Four-Way");
        options.setBounds(450, 150, 150, 25);
        panel.add(options);

        // submit intersection
        JButton submitButton = new JButton("Create New Intersection");
        submitButton.setSelected(false);
        submitButton.setBounds(250, 200, 200, 25);
        panel.add(submitButton);

        addBackButton(panel, panelContainer, cl, "admin");

        submitButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Admin.addNewIntersection(IDText.getText(), String.valueOf(options.getSelectedItem()));
                addToIntersectionListPicker(String.valueOf(IDText.getText()));
                // updateLiveViewPicker();
                // updatePickerInThisPageForUpdatingValues();
                updateTimingPanelComponents(Admin.getHashedIntersection(IDText.getText()));
            }
        });

        // update intersection
        JLabel manualChangeLabel = new JLabel("[Edit An Intersection]");
        manualChangeLabel.setBounds(100, 300, 200, 25);
        panel.add(manualChangeLabel);

        // Set intersection Name label and text area
        JLabel intersectionNameLabel = new JLabel("Intersection Name/ID:");
        intersectionNameLabel.setBounds(250, 300, 150, 25);
        panel.add(intersectionNameLabel);

        // Dropdown list of current intersections
        JComboBox intersectionNameDropdown = new JComboBox<>();
        intersectionNameDropdown.setModel(intersectionListModel);
        intersectionNameDropdown.setBounds(450, 300, 150, 25);
        panel.add(intersectionNameDropdown);

        // Change timings for directionOne traffic light
        JLabel greenLightOneLabel = new JLabel("Direction One Green Traffic Light Length:");
        greenLightOneLabel.setBounds(250, 350, 250, 25);
        panel.add(greenLightOneLabel);

        // Area to type new directionOne timings
        JTextField greenLightOneText = new JTextField(20);
        greenLightOneText.setBounds(550, 350, 50, 25);
        panel.add(greenLightOneText);

        // Change timings for directionOne left light
        JLabel leftLightOneLabel = new JLabel("Direction One Left Traffic Light Length:");
        leftLightOneLabel.setBounds(250, 375, 250, 25);
        panel.add(leftLightOneLabel);

        // Area to type new directionOne left timings
        JTextField leftLightOneText = new JTextField(20);
        leftLightOneText.setBounds(550, 375, 50, 25);
        panel.add(leftLightOneText);

        // Change timings for directionOne crosswalk countdown
        JLabel crosswalkCountdownOneLabel = new JLabel("Direction One Crosswalk Countdown Length:");
        crosswalkCountdownOneLabel.setBounds(250, 400, 300, 25);
        panel.add(crosswalkCountdownOneLabel);

        // Area to type new directionOne crosswalk countdown
        JTextField crosswalkCountdownOneText = new JTextField(20);
        crosswalkCountdownOneText.setBounds(550, 400, 50, 25);
        panel.add(crosswalkCountdownOneText);

        // Change timings for directionTwo traffic light
        JLabel greenLightTwoLabel = new JLabel("Direction Two Green Traffic Light Length:");
        greenLightTwoLabel.setBounds(250, 450, 250, 25);
        panel.add(greenLightTwoLabel);

        // Area to type new directionTwo timings
        JTextField greenLightTwoText = new JTextField(20);
        greenLightTwoText.setBounds(550, 450, 50, 25);
        panel.add(greenLightTwoText);

        // Change timings for directionTwo left light
        JLabel leftLightTwoLabel = new JLabel("Direction Two Left Traffic Light Length:");
        leftLightTwoLabel.setBounds(250, 475, 250, 25);
        panel.add(leftLightTwoLabel);

        // Area to type new directionTwo left timings
        JTextField leftLightTwoText = new JTextField(20);
        leftLightTwoText.setBounds(550, 475, 50, 25);
        panel.add(leftLightTwoText);

        // Change timings for directionTwo crosswalk countdown
        JLabel crosswalkCountdownTwoLabel = new JLabel("Direction Two Crosswalk Countdown Length:");
        crosswalkCountdownTwoLabel.setBounds(250, 500, 300, 25);
        panel.add(crosswalkCountdownTwoLabel);

        // Area to type new directionTwo crosswalk countdown
        JTextField crosswalkCountdownTwoText = new JTextField(20);
        crosswalkCountdownTwoText.setBounds(550, 500, 50, 25);
        panel.add(crosswalkCountdownTwoText);

        // Apply changes to intersection
        JButton applyChangesButton = new JButton("Apply Changes");
        applyChangesButton.setSelected(false);
        applyChangesButton.setBounds(250, 550, 200, 25);
        panel.add(applyChangesButton);
        applyChangesButton.setVisible(false); // Not visible until intersection is selected

        // Success message if user gives valid input
        JLabel successMsg = new JLabel("Changes have been applied");
        successMsg.setBounds(250, 575, 400, 50);
        panel.add(successMsg);
        successMsg.setVisible(false);

        // Error message if user gives invalid input
        JLabel errorMsg = new JLabel("<html>PLEASE INPUT INTEGER VALUES FOR TIMINGS<br>Left light length MUST be shorter than its green light length</html>");
        errorMsg.setBounds(250, 575, 400, 50);
        panel.add(errorMsg);
        errorMsg.setVisible(false);

        // Create background
        JLabel backgound1Shape = new JLabel("");
        backgound1Shape.setBounds(75, 75, 625, 175);
        backgound1Shape.setBackground(new Color(255, 255, 255));
        backgound1Shape.setOpaque(true); 
        panel.add(backgound1Shape);

        // Create background
        JLabel backgound2Shape = new JLabel("");
        backgound2Shape.setBounds(75, 275, 625, 375);
        backgound2Shape.setBackground(new Color(255, 255, 255));
        backgound2Shape.setOpaque(true); 
        panel.add(backgound2Shape);

        // Make the current selected intersection the one to edit values for
        intersectionNameDropdown.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

                successMsg.setVisible(false);
                errorMsg.setVisible(false);
                String option = (String) intersectionNameDropdown.getSelectedItem();
                currentIntersection = Admin.getHashedIntersection(option);
                typ = currentIntersection.intersectionClassification;

                if (typ == intersectionType.TWO_WAY) {
                    greenLightOneText.setVisible(true);
                    leftLightOneText.setVisible(false);
                    crosswalkCountdownOneText.setVisible(true);
               
                    greenLightTwoText.setVisible(false);
                    leftLightTwoText.setVisible(false);
                    crosswalkCountdownTwoText.setVisible(true);

                    applyChangesButton.setVisible(true); }
                
                else if (typ == intersectionType.THREE_WAY) {
                    greenLightOneText.setVisible(true);
                    leftLightOneText.setVisible(true);
                    crosswalkCountdownOneText.setVisible(true);

                    greenLightTwoText.setVisible(true);
                    leftLightTwoText.setVisible(false);
                    crosswalkCountdownTwoText.setVisible(true);

                    applyChangesButton.setVisible(true); }
                
                else if (typ == intersectionType.FOUR_WAY) {
                    greenLightOneText.setVisible(true);
                    leftLightOneText.setVisible(true);
                    crosswalkCountdownOneText.setVisible(true);

                    greenLightTwoText.setVisible(true);
                    leftLightTwoText.setVisible(true);
                    crosswalkCountdownTwoText.setVisible(true);

                    applyChangesButton.setVisible(true); }
            }
        });

        // Applies the changes to the current intersection, if valid
        applyChangesButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

                try {
                    String option = (String) intersectionNameDropdown.getSelectedItem();
                    currentIntersection = Admin.getHashedIntersection(option);
                    typ = currentIntersection.intersectionClassification;
                    successMsg.setVisible(false);
                    errorMsg.setVisible(false);

                    int green1 = Integer.parseInt(greenLightOneText.getText());
                    int cross1 = Integer.parseInt(crosswalkCountdownOneText.getText());
                    int cross2 = Integer.parseInt(crosswalkCountdownTwoText.getText());

                    if (typ == intersectionType.TWO_WAY) { }

                    else if (typ == intersectionType.THREE_WAY) {
                        int left1 = Integer.parseInt(leftLightOneText.getText());
                        int green2 = Integer.parseInt(greenLightTwoText.getText());

                        if (left1 >= green1) { throw new NumberFormatException(); }

                        currentIntersection.directionOneTrafficLights.get(0).setLightLength(lightState.LEFT_TURN, left1);
                        currentIntersection.directionTwoTrafficLights.get(0).setLightLength(lightState.GREEN, green2); }
                    
                    else if (typ == intersectionType.FOUR_WAY) {
                        int left1 = Integer.parseInt(leftLightOneText.getText());
                        int green2 = Integer.parseInt(greenLightTwoText.getText());
                        int left2 = Integer.parseInt(leftLightTwoText.getText());

                        if (left1 >= green1 || left2 >= green2) { throw new NumberFormatException(); }

                        for (trafficlight i : currentIntersection.directionOneTrafficLights) {
                            i.setLightLength(lightState.LEFT_TURN, left1); }
                        for (trafficlight i : currentIntersection.directionTwoTrafficLights) {
                            i.setLightLength(lightState.GREEN, green2); }
                        for (trafficlight i : currentIntersection.directionTwoTrafficLights) {
                            i.setLightLength(lightState.LEFT_TURN, left2); } }

                    for (trafficlight i : currentIntersection.directionOneTrafficLights) {
                        i.setLightLength(lightState.GREEN, green1); }
                    for (crosswalk i : currentIntersection.directionOneCrosswalks) {
                        i.setCrossWalkTiming(cross1); }
                    for (crosswalk i : currentIntersection.directionTwoCrosswalks) {
                        i.setCrossWalkTiming(cross2); }

                    successMsg.setVisible(true);

                } catch (NumberFormatException n) {
                    errorMsg.setVisible(true);
                }
            }
        });
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

        JLabel intersectionLabel = new JLabel("Intersection to inspect/view: ");
        intersectionLabel.setBounds(240, 100, 200, 25);

        JComboBox localJComboBox = new JComboBox<>();
        localJComboBox.setModel(intersectionListModel);
        localJComboBox.setBounds(425, 100, 200, 25);
        panel.add(localJComboBox);

        JCheckBox haveTrain = new JCheckBox("Train");
        haveTrain.setSelected(false);
        haveTrain.setBounds(100, 400, 200, 25);

        JLabel rangeInfo = newLabel("Car # Range: [0-999]");
        rangeInfo.setBounds(150, 400, 200, 25);

        JButton submitButton = new JButton("Submit");
        submitButton.setSelected(false);
        submitButton.setBounds(500, 400, 125, 25);

        JLabel statusLabel = new JLabel(SUCCESS_MESSAGE);
        statusLabel.setFont(new Font("Serif", Font.PLAIN, 12));
        statusLabel.setOpaque(true);
        statusLabel.setVisible(false);
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

        JLabel optimizedViewport = new JLabel();
        optimizedViewport.setFont(new Font("Serif", Font.PLAIN, 18));
        optimizedViewport.setBounds(105, 525, 395, 225);
        optimizedViewport.setVisible(false);
        optimizedViewport.setBackground(Color.WHITE);
        optimizedViewport.setHorizontalAlignment(SwingConstants.CENTER);

        JButton applyButton = new JButton("Apply");
        applyButton.setSelected(false);
        applyButton.setFont(new Font("Serif", Font.PLAIN, 18));
        applyButton.setBounds(500, 600, 150, 70);
        applyButton.setVisible(false);

        localJComboBox.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String option = (String) localJComboBox.getSelectedItem();
                currentIntersection = Admin.getHashedIntersection(option);

                if (currentIntersection.intersectionClassification == intersectionType.TWO_WAY) {
                    typ = intersectionType.TWO_WAY;

                    roadThree.setVisible(false);
                    roadThreeLane.setVisible(false);
                    roadThreeLeft.setVisible(false);
                    roadThreeRight.setVisible(false);

                    roadFour.setVisible(false);
                    roadFourLane.setVisible(false);
                    roadFourLeft.setVisible(false);
                    roadFourRight.setVisible(false);
                } else if (currentIntersection.intersectionClassification == intersectionType.THREE_WAY) {
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

        submitButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String roadNames[] = { roadOne.getText(), roadThree.getText() };
                String data[][] = {
                        { roadOneLane.getText(), roadOneLeft.getText(), roadOneRight.getText() },
                        { roadTwoLane.getText(), roadTwoLeft.getText(), roadTwoRight.getText() },
                        { roadThreeLane.getText(), roadThreeLeft.getText(), roadThreeRight.getText() },
                        { roadFourLane.getText(), roadFourLeft.getText(), roadFourRight.getText() }
                };

                // System.out.println(typ);
                try {
                    String result = Admin.inputData(currentIntersection, roadNames, typ, data);
                    statusLabel.setBackground(SUCCESS_COLOUR);
                    statusLabel.setText(SUCCESS_MESSAGE);
                    statusLabel.setVisible(true);
                    applyButton.setVisible(true);

                    optimizedViewport.setText(result);
                    optimizedViewport.setVisible(true);
                } catch (Exception except) {
                    applyButton.setVisible(false);
                    optimizedViewport.setVisible(false);

                    statusLabel.setBackground(FAILURE_COLOUR);
                    if (except.getMessage().contains("int")) {
                        statusLabel.setText("Input is not correct! Please double check them.");
                    } else {
                        statusLabel.setText(except.getMessage());
                        System.out.println(except.getMessage());
                    }
                    statusLabel.setVisible(true);
                }
            }
        });

        applyButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Admin.applyOptimization(currentIntersection);
                updateTimingPanelComponents(currentIntersection);
            }
        });

        panel.add(editor);
        panel.add(optimizedViewport);
        panel.add(intersectionLabel);
        panel.add(localJComboBox);
        panel.add(haveTrain);
        panel.add(submitButton);
        panel.add(statusLabel);
        panel.add(applyButton);
        panel.add(rangeInfo);

        addBackButton(panel, panelContainer, cl, "admin");
    }

}
