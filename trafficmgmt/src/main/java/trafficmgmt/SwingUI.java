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

import javax.swing.SwingConstants;
import trafficmgmt.utility.intersectionType;
import trafficmgmt.utility.crosswalkState;
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
    private static DefaultComboBoxModel liveViewIntersectionModel;
    private static JComboBox liveViewIntersectionOptions;
    private static Intersection currentlyDisplayingIntersection;

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
        Admin.getIntersection().startIntersection();
        initializeLiveViewJComboBox();
        threewayIntersection dummyIntersection = new threewayIntersection(25, 25, 10, "glue street", "paper street");
        currentlyDisplayingIntersection = dummyIntersection;

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
                //String ID = IDText.getText();
                String ID = "admin.1";
                //String password = String.valueOf(passwordText.getPassword());
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

    private static void addToLiveViewIntersectionPicker(String name) {
        liveViewIntersectionModel.addElement(name);
        liveViewIntersectionOptions.setModel(liveViewIntersectionModel);
        liveViewIntersectionModel.setSelectedItem(name);
    }

    private static void initializeLiveViewJComboBox() { 
        liveViewIntersectionModel = new DefaultComboBoxModel();
        liveViewIntersectionOptions = new JComboBox<>();        
    }



    
    private static void liveViewPanelComponents(JPanel panel, CardLayout cl, JPanel panelContainer) {
        
        //dummyIntersection.stopIntersection();
    
        panel.setLayout(null);
        
        JLabel viewTimingLabel = new JLabel("Live Traffic Timings");
        viewTimingLabel.setBounds(100, 100, 400, 25);
        panel.add(viewTimingLabel);

        JLabel optionsLabel = new JLabel("Intersection to inspect/view: ");
        optionsLabel.setBounds(100, 150, 400, 25);
        panel.add(optionsLabel);
        


        liveViewIntersectionOptions.setBounds(325, 150, 150, 25);
        panel.add(liveViewIntersectionOptions);



        intersectionTypeLabel = new JLabel("Type of Intersection: " + currentlyDisplayingIntersection.intersectionClassification);
        intersectionTypeLabel.setBounds(100, 200, 400, 25);
        panel.add(intersectionTypeLabel);

        JLabel dirOneStates = new JLabel("");
        dirOneStates.setBounds(100, 250, 1000, 25);
        panel.add(dirOneStates);

        JLabel dirOneCrosswalkStates = new JLabel("");
        dirOneCrosswalkStates.setBounds(100, 300, 1000, 25);
        panel.add(dirOneCrosswalkStates);

        JLabel dirTwoStates = new JLabel("");
        dirTwoStates.setBounds(100, 350, 1000, 25);
        panel.add(dirTwoStates);

        JLabel dirTwoCrosswalkStates = new JLabel("");
        dirTwoCrosswalkStates.setBounds(100, 400, 1000, 25);
        panel.add(dirTwoCrosswalkStates);

        JLabel totalLen = new JLabel("");
        totalLen.setBounds(100, 500, 1000, 25);
        panel.add(totalLen);

        addBackButton(panel, panelContainer, cl, "admin");

        ActionListener taskPerformer = new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (currentlyDisplayingIntersection != null) { 
                    intersectionTypeLabel.setText("Type of Intersection: " + currentlyDisplayingIntersection.intersectionClassification);

                    String output = "Direction One Light States -> ";
                    int j = 0;
                    for (trafficlight i : currentlyDisplayingIntersection.directionOneTrafficLights) {
                        j++;
                        output += "#"+ j + " [" + i.getCurrentLightState() + "] ";
                    }
                    dirOneStates.setText(output);

                    output = "Direction Two Light States -> ";
                    j = 0;
                    for (trafficlight i : currentlyDisplayingIntersection.directionTwoTrafficLights) {
                        j++;
                        output +=  "#"+ j + " [" + i.getCurrentLightState() + "] ";
                    }
                    dirTwoStates.setText(output);

                    output = "Direction One Crosswalk States -> ";
                    j = 0;
                    for (crosswalk i : currentlyDisplayingIntersection.directionOneCrosswalks) {
                        j++;
                        output +=  "#" + j + " [State: " + i.getCurrentCrossWalkState();
                        if (i.getCurrentCrossWalkState() == crosswalkState.COUNTDOWN) { 
                            output += " timer: " + i.currentcrosswalkCountDownNumber + "] ";
                        } else { 
                            output += "] ";
                        }
                    }
                    dirOneCrosswalkStates.setText(output);

                    output = "Direction Two Crosswalk States -> ";
                    j = 0;
                    for (crosswalk i : currentlyDisplayingIntersection.directionTwoCrosswalks) {
                        j++;
                        output +=  "#" + j + " [State: " + i.getCurrentCrossWalkState();
                        if (i.getCurrentCrossWalkState() == crosswalkState.COUNTDOWN) { 
                            output += " timer: " + i.currentcrosswalkCountDownNumber + "]  ";
                        } else { 
                            output += "] ";
                        }
                    }
                    dirTwoCrosswalkStates.setText(output);

                    output = "Direction Total Length Left: ";
                    output += currentlyDisplayingIntersection.curerntDirectionTiming;
                    totalLen.setText(output);
                }
            }
            
        };

        liveViewIntersectionOptions.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String option = (String) liveViewIntersectionOptions.getSelectedItem();
                currentlyDisplayingIntersection = Admin.getHashedIntersection(option);
            }
        });

        Timer timer = new Timer(700, taskPerformer);
        timer.setRepeats(true);
        timer.start();

    }





    private static void updateTimingPanelComponents() {
        intersectionTypeLabel.setText("Type of Intersection: " + typ);
        greenLightLabel.setText("Intersection Current Green Light Length: " + Admin.getIntersection()
                .getLengthInformationFromCurrentDirection(timerlengthinformation.Direction_LENGTH));
        yellowLightLabel.setText("Intersection Current Yellow Light Length: " + Admin.getIntersection()
                .getLengthInformationFromCurrentDirection(timerlengthinformation.YELLOW_LIGHT_LENGTH));
        leftLightLabel.setText("Intersection Current Left Light Length: " + Admin.getIntersection()
                .getLengthInformationFromCurrentDirection(timerlengthinformation.LEFT_TURN_LENGTH));
        crosswalkLightLabel.setText("Crosswalk Countdown Length: " + Admin.getIntersection()
                .getLengthInformationFromCurrentDirection(timerlengthinformation.CROSSWALK_COUTDOWN_LENGTH));
    }






    private static void viewTimingPanelComponents(JPanel panel, CardLayout cl, JPanel panelContainer) {
        panel.setLayout(null);

        JLabel viewTimingLabel = new JLabel("View Traffic Timings");
        viewTimingLabel.setBounds(100, 100, 400, 25);
        panel.add(viewTimingLabel);

        JLabel optionsLabel = new JLabel("Intersection to inspect/view: ");
        optionsLabel.setBounds(100, 150, 400, 25);
        panel.add(optionsLabel);

        JComboBox localJComboBox = new JComboBox<>();
        localJComboBox.setModel(liveViewIntersectionModel);
        localJComboBox.setBounds(325, 150, 150, 25);
        panel.add(localJComboBox);

        JLabel localIntersectionTypeLabel = new JLabel("Type of Intersection: " + currentlyDisplayingIntersection.intersectionClassification);
        localIntersectionTypeLabel.setBounds(100, 200, 400, 25);
        panel.add(localIntersectionTypeLabel);

        greenLightLabel = new JLabel("Intersection Current Green Light Length: " + currentlyDisplayingIntersection
                .getLengthInformationFromCurrentDirection(timerlengthinformation.Direction_LENGTH));
        greenLightLabel.setBounds(100, 250, 400, 25);
        panel.add(greenLightLabel);

        yellowLightLabel = new JLabel("Intersection Current Yellow Light Length: " + currentlyDisplayingIntersection
                .getLengthInformationFromCurrentDirection(timerlengthinformation.YELLOW_LIGHT_LENGTH));
        yellowLightLabel.setBounds(100, 300, 400, 25);
        panel.add(yellowLightLabel);

        leftLightLabel = new JLabel("Intersection Current Left Light Length: " + currentlyDisplayingIntersection
                .getLengthInformationFromCurrentDirection(timerlengthinformation.LEFT_TURN_LENGTH));
        leftLightLabel.setBounds(100, 350, 400, 25);
        panel.add(leftLightLabel);

        crosswalkLightLabel = new JLabel("Crosswalk Light Length: " + currentlyDisplayingIntersection
                .getLengthInformationFromCurrentDirection(timerlengthinformation.CROSSWALK_COUTDOWN_LENGTH));
        crosswalkLightLabel.setBounds(100, 400, 400, 25);
        panel.add(crosswalkLightLabel);

        addBackButton(panel, panelContainer, cl, "admin");

        localJComboBox.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String option = (String) liveViewIntersectionOptions.getSelectedItem();
                currentlyDisplayingIntersection = Admin.getHashedIntersection(option);
                if (currentlyDisplayingIntersection != null) { 
                    localIntersectionTypeLabel.setText("Type of Intersection: " + currentlyDisplayingIntersection.intersectionClassification);
                    greenLightLabel.setText("Intersection Current Green Light Length: " + currentlyDisplayingIntersection
                            .getLengthInformationFromCurrentDirection(timerlengthinformation.Direction_LENGTH));
                    yellowLightLabel.setText("Intersection Current Yellow Light Length: " + currentlyDisplayingIntersection
                            .getLengthInformationFromCurrentDirection(timerlengthinformation.YELLOW_LIGHT_LENGTH));
                    leftLightLabel.setText("Intersection Current Left Light Length: " + currentlyDisplayingIntersection
                            .getLengthInformationFromCurrentDirection(timerlengthinformation.LEFT_TURN_LENGTH));
                    crosswalkLightLabel.setText("Crosswalk Countdown Length: " + currentlyDisplayingIntersection
                            .getLengthInformationFromCurrentDirection(timerlengthinformation.CROSSWALK_COUTDOWN_LENGTH));
                }
            }
        });

    }





    private static void manualChangePanelComponents(JPanel panel, CardLayout cl, JPanel panelContainer) {
        panel.setLayout(null);

        // Create intersection header
        JLabel createLabel = new JLabel("[Create An Intersection]");
        createLabel.setBounds(75, 100, 200, 25);
        panel.add(createLabel);
        

        // Set intersection Name label and text area
        JLabel intersectionName = new JLabel("Intersection Name/ID:");
        intersectionName.setBounds(225, 100, 150, 25);
        panel.add(intersectionName);
        
        JTextField IDText = new JTextField(20);
        IDText.setBounds(425, 100, 150, 25);
        panel.add(IDText);


        // pick intersection type label and options
        JLabel intersectionType = new JLabel("Intersection Type:");
        intersectionType.setBounds(225, 150, 150, 25);
        panel.add(intersectionType);

        JComboBox options = new JComboBox<>(intersectionOptions);
        options.setSelectedItem("Four-Way");
        options.setBounds(425, 150, 150, 25);
        panel.add(options);
        

        // submit intersection
        JButton submitButton = new JButton("Create New Intersection");
        submitButton.setSelected(false);
        submitButton.setBounds(225, 200, 200, 25);
        panel.add(submitButton);

        addBackButton(panel, panelContainer, cl, "admin");

        submitButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Admin.addNewIntersection(IDText.getText(), String.valueOf(options.getSelectedItem()));
                addToLiveViewIntersectionPicker(String.valueOf(IDText.getText()));
                //updateLiveViewPicker();
                //updatePickerInThisPageForUpdatingValues();
                updateTimingPanelComponents();
            }
        });


        // update intersection
        JLabel manualChangeLabel = new JLabel("[Change/Edit An Intersection]");
        manualChangeLabel.setBounds(75, 300, 200, 25);
        panel.add(manualChangeLabel);

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

        submitButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String roadNames[] = { roadOne.getText(), roadThree.getText() };
                String data[][] = {
                        { roadOneLane.getText(), roadOneLeft.getText(), roadOneRight.getText() },
                        { roadTwoLane.getText(), roadTwoLeft.getText(), roadTwoRight.getText() },
                        { roadThreeLane.getText(), roadThreeLeft.getText(), roadThreeRight.getText() },
                        { roadFourLane.getText(), roadFourLeft.getText(), roadFourRight.getText() }
                };

                try {
                    String result = Admin.inputData(roadNames, typ, data);
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
                    }
                    statusLabel.setVisible(true);
                }
            }
        });

        applyButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Admin.applyOptimization();
                updateTimingPanelComponents();
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
