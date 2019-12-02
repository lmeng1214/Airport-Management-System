import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.InputMismatchException;

/**
 *
 * Response Listener -
 * a class that holds the functions for the reservation Client
 *
 * resources :
 * Show input dialog reference -
 * https://www.mkyong.com/swing/java-swing-joptionpane-showinputdialog-example/
 *
 * Message Type reference -
 * http://www.java2s.com/Tutorial/Java/0240__Swing/
 * TheJOptionPaneMessageTypeandIconArguments.htm
 *
 * JFrame textfield reference -
 * https://stackoverflow.com/questions/11927963/get-input-from-jframe
 *
 * JFrame png printing reference -
 * https://stackoverflow.com/questions/299495/how-to-add-an-image-to-a-jpanel
 *
 * JFrame layout tutorials -
 * https://docs.oracle.com/javase/tutorial/uiswing/layout/visual.html
 *
 * JFrame Size and position -
 * https://www.leepoint.net/GUI/containers/10windows/15framesize.html
 *
 * Dispose on button reference -
 * https://stackoverflow.com/questions/2352727/closing-jframe-with-button-click
 *
 * JFrame Elements reference -
 * http://web.mit.edu/6.005/www/sp14/psets/ps4/java-6-tutorial/components.html
 *
 * JComboBox action listener reference -
 * https://stackoverflow.com/questions/7069958/
 * adding-an-action-listener-to-a-jcombobox/7069984
 *
 * JTextField Reference -
 * https://stackoverflow.com/questions/5752307/
 * how-to-retrieve-value-from-jtextfield-in-java-swing
 *
 * JOptionPane confirm dialog constructor values reference -
 * https://stackoverflow.com/questions/8396870/joptionpane-yes-or-no-window
 *
 * JLabel additional formatting reference -
 * https://stackoverflow.com/questions/1090098/newline-in-jlabel
 *
 * KeyListener Refernce -
 * https://stackoverflow.com/questions/10876491/
 * how-to-use-keylistener
 *
 * Checking server status -
 * https://stackoverflow.com/questions/17147352
 * /checking-if-server-is-online-from-java-code
 *
 * Jlabel formatting -
 * https://stackoverflow.com/questions/6810581/
 * how-to-center-the-text-in-a-jlabel
 *
 * Checking if a string is an integer -
 * https://stackoverflow.com/questions/5439529/
 * determine-if-a-string-is-an-integer-in-java
 *
 * @author Janis Vuskalns jvuskaln@purdue.edu
 * @version 11/29/2019
 */

public class ResponseListener {
    private String hostName;
    private int portNumber;
    //variables to be sent or received
    private final String[] firstName = {""};
    private final String[] lastName = {""};
    private final int[] age = {0};
    private final Airline userAirline[] = {new Delta()};
    private  final BoardingPass passengerBoardingPass[] = {null};
    private final Passenger newPassenger[] = {null};
    private  final JLabel userBoardingPass[] = {null};
    private  String theGate[] = {""};

    public ResponseListener() {
        hostName = "localhost";
        portNumber = 0;
    }

    public void setHostName() {
        hostName = JOptionPane.showInputDialog//video first question
                (null,
                        "What is the hostname you'd like to connect to?",
                        "Hostname?", JOptionPane.QUESTION_MESSAGE);
    }
    public String getHostName() {
        return hostName;
    }

    public void setPortNumber() {
        portNumber = Integer.parseInt(JOptionPane.showInputDialog//video second question
                (null,
                        "What is the port you'd like to connect to?",
                        "Port?", JOptionPane.QUESTION_MESSAGE));
    }
    public int getPortNumber() {
        return portNumber;
    }

    public void run(ObjectOutputStream oos, ObjectInputStream ois) throws IOException {


        /**
         * declaration of the System frame
         */
        //the frame that holds the system
        JFrame flightSystem = new JFrame("Purdue University Flight Reservation System");
        flightSystem.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        int width = (640);
        int height = (480);
        flightSystem.setSize(width, height);
        flightSystem.setLocationRelativeTo(null);

        /**
         * the panels, one for each page in the example video
         */
        JPanel introductionPanel = new JPanel();
        JPanel bookPanel = new JPanel();
        JPanel airlineChoice = new JPanel();
        JPanel confirmFlight = new JPanel();
        JPanel informationPanel = new JPanel();
        JPanel flightStatus = new JPanel();
        /**
         * Everything that needs to be added to each Panel,
         * in order of panel declaration
         */
        //introductionPanel
        JLabel welcomeLabel = new JLabel("Welcome to the Purdue University Airline" +
                " Reservation Management System!");//text
        BufferedImage purdueLogo = ImageIO.read(new File
                ("PurudeLogo.png"));
        JLabel logoLabel = new JLabel(new ImageIcon(purdueLogo));//image
        JButton welcomeExit = new JButton("Exit");
        JButton bookButton = new JButton("Book a Flight");

        //bookPanel
        JLabel bookLabel = new JLabel("Do you want to book a flight today?");
        JButton bookExit = new JButton("Exit");
        JButton confirmBooking = new JButton("Yes, I want to book a flight");

        //airlineChoice panel
        JLabel flightPrompt = new JLabel("Choose a flight from the drop down menu.");
        JLabel info = new JLabel("<html><div style='text-align: center;'>" +
                new Delta().getIntroduction() + "</div></html>");
        JButton airlineChoiceExit = new JButton("Exit");
        JButton chosenFlight = new JButton("Choose this flight");

        //confirmFlight panel
        JButton confirmExit = new JButton("Exit");
        JButton deny = new JButton("No, I want a different flight.");
        JButton confirm = new JButton("Yes, I want this flight.");
        String airlineOptions[] = new String[] {"Delta", "Southwest", "Alaska"};
        JComboBox airlines = new JComboBox(airlineOptions);
        JLabel confirmPrompt = new JLabel("Are you sure you want to book a flight on " +
                userAirline[0].getName() + " Airlines?");

        //information panel
        JLabel infoPrompt = new JLabel("Please put your information below.");
        JLabel firstNamePrompt = new JLabel("What is your first name?");
        JLabel lastNamePrompt = new JLabel("What is your last name?");
        JLabel agePrompt = new JLabel("What is your age?");
        JTextField firstNameField = new JTextField();
        JTextField lastNameField = new JTextField();
        JTextField ageField = new JTextField();
        JButton exitInfo = new JButton("Exit");
        JButton nextInfo = new JButton("Next");

        //flightStatus panel
        JLabel thanksLabel = new JLabel();
        JButton statusExit = new JButton("Exit");
        JButton refresh = new JButton("Refresh Flight Status");
        JPanel passengerListPanel = new JPanel();

        /**
         * key listener actions
         */
        final boolean[] confirmHasKeyListener = {false};
        KeyListener flightStatusCheck = new KeyListener() {

            @Override
            public void keyTyped(KeyEvent keyEvent) {

            }

            @Override
            public void keyPressed(KeyEvent keyEvent) {
                    if (!hostAvailabilityCheck()) {
                        JOptionPane.showMessageDialog(null,
                                "We are sorry, the booking service is no" +
                                        " longer available. Please check back later.");
                        flightSystem.dispose();
                    }
                    try {
                        JFrame displayReservations = new JFrame();
                        displayReservations.setDefaultCloseOperation
                                (WindowConstants.DISPOSE_ON_CLOSE);
                        DefaultListModel airlinePassengers = new DefaultListModel();

                        oos.writeObject("getSouls");
                        oos.flush();
                        oos.writeObject(userAirline[0]);
                        oos.flush();
                        ArrayList<String> reservations = (ArrayList<String>) ois.readObject();
                        for (int i = 0; i < reservations.size(); i++) {
                            airlinePassengers.addElement(reservations.get(i));
                        }
                         JList passengerList = new JList(airlinePassengers);
                         passengerList.setLayoutOrientation(JList.VERTICAL);
                         passengerList.setVisibleRowCount(-1);
                         JScrollPane listScroller = new JScrollPane(passengerList);
                         listScroller.setPreferredSize(new Dimension(200, 100));
                         JButton exitReservations = new JButton("Exit");
                         exitReservations.addActionListener(new ActionListener() {
                             public void actionPerformed(ActionEvent actionEvent) {
                                 displayReservations.dispose();
                             }
                         });

                         int capacity = (int) ois.readObject();
                         JLabel airlineHeader = new JLabel(userAirline[0].getName() + " Airlines");
                         JLabel capacityStatus = new JLabel
                                 (capacity + " : "
                                         + userAirline[0].getCapacity());

                         displayReservations.setLayout
                                 (new BorderLayout());

                         JPanel topLabels = new JPanel();
                         topLabels.add(airlineHeader);
                         topLabels.add(capacityStatus);

                         displayReservations.add(topLabels, BorderLayout.PAGE_START);
                         displayReservations.add(listScroller, BorderLayout.CENTER);
                         displayReservations.add(exitReservations, BorderLayout.PAGE_END);
                         displayReservations.pack();
                         displayReservations.setVisible(true);
                    } catch (IOException | ClassNotFoundException e) {
                        e.printStackTrace();
                    }
                }

            @Override
            public void keyReleased(KeyEvent keyEvent) {

            }
        };


        /**
         * actionListeners, in the order of declaration,
         * by panel
         */
        //introductionPanel
        bookButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent actionEvent) {
                if (hostAvailabilityCheck()) {
                    introductionPanel.setVisible(false);
                    flightSystem.add(bookPanel);
                } else {
                    JOptionPane.showMessageDialog(null,
                            "We are sorry, the booking service is no" +
                                    " longer available. Please check back later.");
                    flightSystem.dispose();
                }
            }
        });
        welcomeExit.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent actionEvent) {
                flightSystem.dispose();
                try {
                    oos.writeObject("END_PROGRAM");
                    oos.flush();
                    oos.close();
                    ois.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        //book panel
        confirmBooking.addActionListener(actionEvent -> {
            if (hostAvailabilityCheck()) {
                bookPanel.setVisible(false);
                airlineChoice.addKeyListener(flightStatusCheck);
                airlineChoice.setFocusable(true);
                flightSystem.add(airlineChoice);
                airlineChoice.requestFocus();
            } else {
                JOptionPane.showMessageDialog(null,
                        "We are sorry, the booking service is no" +
                                " longer available. Please check back later.");
                flightSystem.dispose();
            }

        });
        bookExit.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent actionEvent) {
                try {
                    oos.writeObject("END_PROGRAM");
                    oos.flush();
                    oos.close();
                    ois.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                flightSystem.dispose();
            }
        });

        //airline choice
        airlineChoiceExit.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent actionEvent) {
                flightSystem.dispose();
                try {
                    oos.writeObject("END_PROGRAM");
                    oos.flush();
                    oos.close();
                    ois.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        boolean chosenFlightHasBeenSet = false;
        chosenFlight.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent actionEvent) {
                if (!hostAvailabilityCheck()) {
                    JOptionPane.showMessageDialog(null,
                            "We are sorry, the booking service is no" +
                                    " longer available. Please check back later.");
                    flightSystem.dispose();
                }

                if(airlines.getSelectedItem() == "Delta") {
                    userAirline[0] = new Delta();
                } else if (airlines.getSelectedItem() == "Southwest") {
                    userAirline[0] = new Southwest();
                } else {
                    userAirline[0] = new Alaska();
                }

                int currentNumPassengers = 0;
                try {
                    oos.writeObject("getNumPassengers");
                    oos.flush();
                    oos.writeObject(userAirline[0]);
                    currentNumPassengers = (int) ois.readObject();
                } catch (IOException | ClassNotFoundException e) {
                    e.printStackTrace();
                }
                if (currentNumPassengers >= userAirline[0].getCapacity()) {
                    JOptionPane.showMessageDialog(null,
                            "This flight is full, please choose another.");
                    airlineChoice.requestFocus();
                } else {
                    confirmPrompt.setText("Are you sure you want to book a flight on " +
                            userAirline[0].getName() + " Airlines?");
                    airlineChoice.setVisible(false);
                    if (!confirmHasKeyListener[0]) {
                        confirmFlight.addKeyListener(flightStatusCheck);
                        confirmHasKeyListener[0] = true;
                    }
                    confirmFlight.setFocusable(true);
                    confirmFlight.requestFocus();
                    flightSystem.add(confirmFlight);
                    confirmFlight.setVisible(true);
                    confirmFlight.requestFocus();
                    try {
                        oos.writeObject("getGate");
                        oos.writeObject(userAirline[0]);
                        oos.flush();
                        theGate[0] = (String) ois.readObject();

                        /**
                         * the top JLabel for flightStatus, requires initialization of theGate
                         */
                        thanksLabel.setText("<html>Flight data displaying for "
                                + userAirline[0].getName() + " Airlines" +
                                "<br/>Enjoy your flight!" +
                                "<br/>Flight is now boarding at Gate " + theGate[0] + "<html>");
                        thanksLabel.setHorizontalAlignment(SwingConstants.CENTER);
                    } catch (IOException | ClassNotFoundException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        airlines.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent actionEvent) {
                if (!hostAvailabilityCheck()) {
                    JOptionPane.showMessageDialog(null,
                            "We are sorry, the booking service is no" +
                                    " longer available. Please check back later.");
                    flightSystem.dispose();
                }

                JComboBox cb = (JComboBox) actionEvent.getSource();
                String choice = (String) cb.getSelectedItem();

                switch (choice) {
                    case "Delta" :
                        info.setText("<html><div style='text-align: center;'>" +
                                new Delta().getIntroduction() + "</div></html>");
                        userAirline[0] = new Delta();
                        airlineChoice.requestFocus();
                        break;
                    case "Southwest" :
                        info.setText("<html><div style='text-align: center;'>" +
                                new Southwest().getIntroduction() + "</div></html>");
                        userAirline[0] = new Southwest();
                        airlineChoice.requestFocus();
                        break;
                    case "Alaska" :
                        info.setText("<html><div style='text-align: center;'>" +
                                new Alaska().getIntroduction() + "</div></html>");
                        userAirline[0] = new Alaska();
                        airlineChoice.requestFocus();
                        break;
                }
            }
        });
        //confirmFlight
        confirmExit.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent actionEvent) {
                flightSystem.dispose();
                try {
                    oos.writeObject("END_PROGRAM");
                    oos.flush();
                    oos.close();
                    ois.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        deny.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent actionEvent) {
                if (!hostAvailabilityCheck()) {
                    JOptionPane.showMessageDialog(null,
                            "We are sorry, the booking service is no" +
                                    " longer available. Please check back later.");
                    flightSystem.dispose();
                }
                flightSystem.remove(confirmFlight);
                airlineChoice.setFocusable(true);
                airlineChoice.setVisible(true);
                airlineChoice.requestFocus();
            }
        });
        confirm.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent actionEvent) {
                if (!hostAvailabilityCheck()) {
                    JOptionPane.showMessageDialog(null,
                            "We are sorry, the booking service is no" +
                                    " longer available. Please check back later.");
                    flightSystem.dispose();
                }
                confirmFlight.setVisible(false);
                informationPanel.addKeyListener(flightStatusCheck);
                informationPanel.setFocusable(true);
                flightSystem.add(informationPanel);
                informationPanel.requestFocus();
            }
        });

        //Information panel
        exitInfo.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent actionEvent) {
                flightSystem.dispose();
                try {
                    oos.writeObject("END_PROGRAM");
                    oos.flush();
                    oos.close();
                    ois.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        nextInfo.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent actionEvent) {
                if (!hostAvailabilityCheck()) {
                    JOptionPane.showMessageDialog(null,
                            "We are sorry, the booking service is no" +
                                    " longer available. Please check back later.");
                    flightSystem.dispose();
                } else {
                    int currentNumPassengers = 0;
                    try {
                        oos.writeObject("getNumPassengers");
                        oos.flush();
                        oos.writeObject(userAirline[0]);
                        currentNumPassengers = (int) ois.readObject();
                    } catch (IOException | ClassNotFoundException e) {
                        e.printStackTrace();
                    }
                    if (currentNumPassengers >= userAirline[0].getCapacity()) {
                        JOptionPane.showMessageDialog(null,
                                "We apologise, but the flight has now been filled." +
                                        " Please choose another");
                        flightSystem.remove(informationPanel);
                        airlineChoice.setFocusable(true);
                        airlineChoice.setVisible(true);
                        airlineChoice.requestFocus();
                    } else {
                        if (firstNameField.getText() == null || lastNameField.getText() == null
                                || ageField.getText() == null) {
                            JOptionPane.showMessageDialog(null,
                                    "Please finish filling out your information");
                            informationPanel.requestFocus();
                        } else if (firstNameField.getText().isBlank() ||
                                firstNameField.getText().isEmpty() ||
                                lastNameField.getText().isEmpty() ||
                                lastNameField.getText().isBlank() ||
                                ageField.getText().isBlank() || ageField.getText().isEmpty()) {
                            JOptionPane.showMessageDialog(null,
                                    "Please finish filling out your information");
                            informationPanel.requestFocus();
                        } else if (isInteger(firstNameField.getText()) ||
                                isInteger(lastNameField.getText()) ||
                                !isInteger(ageField.getText())) {
                            JOptionPane.showMessageDialog(null,
                                    "Please check your information. The first and last names" +
                                            " should not have any numbers, and your age should only have numbers.");
                            informationPanel.requestFocus();
                        } else {
                            firstName[0] = firstNameField.getText();
                            lastName[0] = lastNameField.getText();
                            age[0] = Integer.parseInt(ageField.getText());
                            int check = JOptionPane.showConfirmDialog
                                    (null,
                                            "Are all the details you entered correct?\n" +
                                                    "The passenger's name is " + firstName[0] +
                                                    " " + lastName[0] + " and their age is " + age[0] + ".\n" +
                                                    "If all the information shown is correct, select the Yes " +
                                                    "button below, otherwise, select the No button.",
                                            null, JOptionPane.YES_NO_OPTION);
                            if (check == JOptionPane.YES_OPTION) {
                                informationPanel.setVisible(false);
                                flightSystem.add(flightStatus);
                                /**
                                * Generating Passenger
                                */
                                if (userAirline[0].getName() == new Delta().getName()) {
                                    userAirline[0] = new Delta();
                                } else if (userAirline[0].getName() == new Southwest().getName()) {
                                    userAirline[0] = new Southwest();
                                } else {
                                    userAirline[0] = new Alaska();
                                }
                                newPassenger[0] = new Passenger(Integer.toString(age[0]), firstName[0], lastName[0]);
                                passengerBoardingPass[0] = new BoardingPass(userAirline[0], newPassenger[0], theGate[0]);
                                userBoardingPass[0] = new JLabel(passengerBoardingPass[0].toString());
                                userAirline[0].incrementNumPassengers();
                                /**
                                * Adding passenger
                                */
                                try {
                                    oos.writeObject("addPassenger");
                                    oos.flush();
                                    oos.writeObject(userAirline[0]);
                                    oos.flush();
                                    oos.writeObject(newPassenger[0]);
                                    String addingResponse = (String) ois.readObject();
                                    if (addingResponse.equals("full")) {
                                        JOptionPane.showMessageDialog
                                                (null, "The flight is now full. " +
                                                        "Please restart and choose another flight");
                                        flightSystem.dispose();
                                    }
                                } catch (IOException | ClassNotFoundException e) {
                                    e.printStackTrace();
                                }

                                /**
                                * flight status elements
                                */
                                DefaultListModel passengers = new DefaultListModel();
                                try {
                                    oos.writeObject("getSouls");
                                    oos.flush();
                                    oos.writeObject(userAirline[0]);
                                    oos.flush();
                                    ArrayList<String> reservations = (ArrayList<String>) ois.readObject();
                                    currentNumPassengers = (int) ois.readObject();
                                    passengers.addElement(currentNumPassengers + " : "
                                            + userAirline[0].getCapacity());
                                    for (int i = 0; i < reservations.size(); i++) {
                                        passengers.addElement(reservations.get(i));
                                    }
                                    JLabel airlineHeader = new JLabel(userAirline[0] + " Airlines");
                                    JList passengerList = new JList(passengers);
                                    passengerList.setLayoutOrientation(JList.VERTICAL);
                                    passengerList.setVisibleRowCount(-1);
                                    JScrollPane listScroller = new JScrollPane(passengerList);
                                    listScroller.setPreferredSize(new Dimension(width - width / 5, height / 3));

                                    /**
                                    * flightStatus layout
                                    */
                                    flightStatus.setLayout(new BorderLayout());
                                    flightStatus.setAlignmentX(Component.LEFT_ALIGNMENT);

                                    JPanel botStatus = new JPanel();
                                    botStatus.setLayout(new BorderLayout());

                                    JPanel botButtons = new JPanel();
                                    botButtons.add(statusExit);
                                    botButtons.add(refresh);

                                    botStatus.add(userBoardingPass[0], BorderLayout.PAGE_START);
                                    botStatus.add(botButtons, BorderLayout.PAGE_END);

                                    passengerListPanel.add(listScroller);

                                    flightStatus.add(thanksLabel, BorderLayout.PAGE_START);
                                    flightStatus.add(passengerListPanel);
                                    flightStatus.add(botStatus, BorderLayout.PAGE_END);
                                } catch (IOException | ClassNotFoundException e) {
                                    e.printStackTrace();
                                }
                            } else {
                                //do nothing
                            }
                        }
                    }
                }
            }
        });

        /**
         * back to action listeners(flightStatus)
         */
        //action listeners
        statusExit.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent actionEvent) {
                flightSystem.dispose();
                try {
                    oos.writeObject("END_PROGRAM");
                    oos.flush();
                    oos.close();
                    ois.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        refresh.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent actionEvent) {
                if (!hostAvailabilityCheck()) {
                    JOptionPane.showMessageDialog(null,
                            "We are sorry, the booking service is no" +
                                    " longer available. Please check back later.");
                    flightSystem.dispose();
                }
                DefaultListModel passengers = new DefaultListModel();
                try {
                    oos.writeObject("getSouls");
                    oos.flush();
                    oos.writeObject(userAirline[0]);
                    oos.flush();
                    ArrayList<String> reservations = (ArrayList<String>) ois.readObject();
                    int currentNumPassengers = (int) ois.readObject();
                    passengers.addElement(currentNumPassengers + " : "
                            + userAirline[0].getCapacity());
                    for (int i = 0; i < reservations.size(); i++) {
                        passengers.addElement(reservations.get(i));
                    }
                    JList passengerList = new JList(passengers);
                    passengerList.setLayoutOrientation(JList.VERTICAL);
                    passengerList.setVisibleRowCount(-1);
                    JScrollPane listScroller = new JScrollPane(passengerList);
                    listScroller.setPreferredSize(new Dimension(width - width / 5, height / 3));
                    flightStatus.remove(passengerListPanel);
                    passengerListPanel.removeAll();
                    passengerListPanel.add(listScroller);
                    flightStatus.add(passengerListPanel);
                    flightStatus.revalidate();
                }
                catch (IOException e) {
                    e.printStackTrace();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }
        });

        /**
         * formatting by panel
         */
        //introduction panel
        introductionPanel.setLayout(new BorderLayout());

        JPanel welcomeCard = new JPanel();
        welcomeCard.add(welcomeLabel);
        JPanel logoCard = new JPanel();
        logoCard.add(logoLabel);
        JPanel buttonCard = new JPanel();
        buttonCard.add(welcomeExit);
        buttonCard.add(bookButton);

        introductionPanel.add(welcomeCard, BorderLayout.PAGE_START);
        introductionPanel.add(logoCard, BorderLayout.CENTER);
        introductionPanel.add(buttonCard, BorderLayout.PAGE_END);

        //book panel
        bookPanel.setLayout(new BorderLayout());

        JPanel questionCard = new JPanel();
        questionCard.add(bookLabel);
        JPanel panel2Buttons = new JPanel();
        panel2Buttons.add(bookExit);
        panel2Buttons.add(confirmBooking);

        bookPanel.add(questionCard, BorderLayout.PAGE_START);
        bookPanel.add(panel2Buttons, BorderLayout.PAGE_END);

        //airline choice
        airlineChoice.setLayout(new BoxLayout(airlineChoice, BoxLayout.Y_AXIS));

        JPanel airlineLabel = new JPanel();
        airlineLabel.add(flightPrompt);
        JPanel airlineComboBox = new JPanel();
        airlineComboBox.add(airlines);
        JPanel infoLabel = new JPanel();
        infoLabel.add(info);
        JPanel airlinesButtons = new JPanel();
        airlinesButtons.add(airlineChoiceExit);
        airlinesButtons.add(chosenFlight);

        airlineChoice.add(airlineLabel);
        airlineChoice.add(airlineComboBox);
        airlineChoice.add(infoLabel);
        airlineChoice.add(airlinesButtons);

        //confirmFlight
        confirmFlight.setLayout(new BorderLayout());

        JPanel confirmLabels = new JPanel();
        confirmLabels.add(confirmPrompt);
        JPanel confirmButtons = new JPanel();
        confirmButtons.add(confirmExit);
        confirmButtons.add(deny);
        confirmButtons.add(confirm);

        confirmFlight.add(confirmLabels, BorderLayout.PAGE_START);
        confirmFlight.add(confirmButtons, BorderLayout.PAGE_END);

        //information panel
        informationPanel.setLayout(new BorderLayout());

        JPanel entreeLabel = new JPanel();
        entreeLabel.add(infoPrompt);
        JPanel promptsAndFields = new JPanel();
        promptsAndFields.setLayout(new BoxLayout(promptsAndFields, BoxLayout.Y_AXIS));
        firstNamePrompt.setHorizontalAlignment(SwingConstants.LEFT);
        lastNamePrompt.setHorizontalAlignment(SwingConstants.LEFT);
        agePrompt.setHorizontalAlignment(SwingConstants.LEFT);
        promptsAndFields.add(firstNamePrompt);
        promptsAndFields.add(firstNameField);
        promptsAndFields.add(lastNamePrompt);
        promptsAndFields.add(lastNameField);
        promptsAndFields.add(agePrompt);
        promptsAndFields.add(ageField);
        JPanel infoButtons = new JPanel();
        infoButtons.add(exitInfo);
        infoButtons.add(nextInfo);

        informationPanel.add(entreeLabel, BorderLayout.PAGE_START);
        informationPanel.add(promptsAndFields, BorderLayout.CENTER);
        informationPanel.add(infoButtons, BorderLayout.PAGE_END);

        /**
         * setVisible and initiator
         */
        flightSystem.add(introductionPanel);
        flightSystem.setVisible(true);
    }

    public boolean isInteger(String stringToTest) throws InputMismatchException {
        try {
            for (int i = 0; i < stringToTest.length(); i++) {
                int numberTester = Integer.parseInt("" + stringToTest.charAt(i));
            }
            return true;
        } catch (InputMismatchException | NumberFormatException e) {
            return false;
        }
    }

    public boolean hostAvailabilityCheck() {
        try (Socket s = new Socket(hostName, portNumber)) {
            return true;
        } catch (IOException ex) {
            /* ignore */
        }
        return false;
    }
}
