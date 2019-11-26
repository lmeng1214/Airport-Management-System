import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

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
 * http://www.java2s.com/Tutorial/Java/0240__Swing/TheJOptionPaneMessageTypeandIconArguments.htm
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
 * https://stackoverflow.com/questions/7069958/adding-an-action-listener-to-a-jcombobox/7069984
 *
 * JTextField Reference -
 * https://stackoverflow.com/questions/5752307/how-to-retrieve-value-from-jtextfield-in-java-swing
 *
 * JOptionPane confirm dialog constructor values reference -
 * https://stackoverflow.com/questions/8396870/joptionpane-yes-or-no-window
 *
 * JLabel additional formatting reference -
 * https://stackoverflow.com/questions/1090098/newline-in-jlabel
 *
 * @author Janis Vuskalns jvuskaln@purdue.edu
 * @version 11/26/2019
 */

public class ResponseListener {
    private String hostName;
    private int portNumber;
    //variables to be sent or received
    private final String[] firstName = {""};
    private final String[] lastName = {""};
    private final int[] age = {0};
    private final String[] airline = {""};
    private final Airline userAirline[] = {null};
    private  final BoardingPass passengerBoardingPass[] = {null};
    private final Passenger newPassenger[] = {null};
    private Gate passengerGate = new Gate();
    private  final JLabel userBoardingPass[] = {null};
    private  String theGate = passengerGate.generateGate();

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
        int width = (Toolkit.getDefaultToolkit().getScreenSize().width / 2);
        int height = (Toolkit.getDefaultToolkit().getScreenSize().height / 2);
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
        JLabel info = new JLabel("Insert Delta info here");
        JButton airlineChoiceExit = new JButton("Exit");
        JButton chosenFlight = new JButton("Choose this flight");

        //confirmFlight panel
        JButton confirmExit = new JButton("Exit");
        JButton deny = new JButton("No, I want a different flight.");
        JButton confirm = new JButton("Yes, I want this flight.");
        String airlineOptions[] = new String[] {"Delta", "Southwest", "Alaska"};
        JComboBox airlines = new JComboBox(airlineOptions);
        JLabel confirmPrompt = new JLabel("Are you sure you want to book a flight on " +
                airline[0] + " Airlines?");

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
        JLabel thanksLabel = new JLabel("<html>Flight data displaying for "
                + airline[0] + " Airlines" +
                "<br/>Enjoy your flight!" +
                "<br/>Flight is now boarding at Gate " + theGate + "<html>");
        thanksLabel.setHorizontalAlignment(SwingConstants.CENTER);
        JButton statusExit = new JButton("Exit");
        JButton refresh = new JButton("Refresh Flight Status");

        /**
         * actionListeners, in the order of declaration,
         * by panel
         */
        //introductionPanel
        bookButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent actionEvent) {
                introductionPanel.setVisible(false);
                flightSystem.add(bookPanel);
            }
        });
        welcomeExit.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent actionEvent) {
                flightSystem.dispose();
                try {
                    oos.writeObject("END_PROGRAM");
                    oos.flush();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        //book panel
        confirmBooking.addActionListener(actionEvent -> {
            bookPanel.setVisible(false);
            flightSystem.add(airlineChoice);
        });
        bookExit.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent actionEvent) {
                flightSystem.dispose();
                try {
                    oos.writeObject("END_PROGRAM");
                    oos.flush();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        //airline choice
        airlineChoiceExit.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent actionEvent) {
                flightSystem.dispose();
                try {
                    oos.writeObject("END_PROGRAM");
                    oos.flush();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        chosenFlight.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent actionEvent) {
                if(airlines.getSelectedItem() == "Delta") {
                    airline[0] = "Delta";
                } else if (airlines.getSelectedItem() == "Southwest") {
                    airline[0] = "Southwest";
                } else {
                    airline[0] = "Alaska";
                }
                confirmPrompt.setText("Are you sure you want to book a flight on " +
                        airline[0] + " Airlines?");
                airlineChoice.setVisible(false);
                flightSystem.add(confirmFlight);
            }
        });
        airlines.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent actionEvent) {
                JComboBox cb = (JComboBox) actionEvent.getSource();
                String choice = (String) cb.getSelectedItem();

                switch (choice) {
                    case "Delta" :
                        info.setText("Inset Delta info here");
                        break;
                    case "Southwest" :
                        info.setText("Inset Southwest info here");
                        break;
                    case "Alaska" :
                        info.setText("Inset Alaska info here");
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
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        deny.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent actionEvent) {
                flightSystem.remove(confirmFlight);
                airlineChoice.setVisible(true);
            }
        });
        confirm.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent actionEvent) {
                confirmFlight.setVisible(false);
                flightSystem.add(informationPanel);
            }
        });

        //Information panel
        exitInfo.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent actionEvent) {
                flightSystem.dispose();
                try {
                    oos.writeObject("END_PROGRAM");
                    oos.flush();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        nextInfo.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent actionEvent) {
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
                    if (airline[0] == new Delta().getName()) {
                        userAirline[0] = new Delta();
                    } else if (airline[0] == new Southwest().getName()) {
                        userAirline[0] = new Southwest();
                    } else {
                        userAirline[0] = new Alaska();
                    }
                    newPassenger[0] = new Passenger(Integer.toString(age[0]), firstName[0], lastName[0]);
                    passengerBoardingPass[0] = new BoardingPass(userAirline[0], newPassenger[0], theGate);
                    userBoardingPass[0] = new JLabel(passengerBoardingPass[0].toString());
                    userAirline[0].incrementNumPassengers();
                    /**
                     * flight status elements
                     */
                    DefaultListModel passengers = new DefaultListModel();
                    passengers.addElement("" + userAirline[0].getCurrentNumPassengers()
                            + " : " + userAirline[0].getCapacity());
                    passengers.addElement(newPassenger[0].toString());
                    JList passengerList = new JList(passengers);
                    passengerList.setLayoutOrientation(JList.HORIZONTAL_WRAP);
                    passengerList.setVisibleRowCount(-1);
                    JScrollPane listScroller = new JScrollPane(passengerList);
                    listScroller.setPreferredSize(new Dimension(width, height/2));

                    /**
                     * flightStatus layout
                     */
                    flightStatus.setLayout(new GridLayout(4, 1));
                    flightStatus.setAlignmentX(Component.LEFT_ALIGNMENT);

                    JPanel statusButtons = new JPanel();
                    statusButtons.add(statusExit);
                    statusButtons.add(refresh);

                    flightStatus.add(thanksLabel);
                    flightStatus.add(passengerList);
                    flightStatus.add(userBoardingPass[0]);
                    flightStatus.add(statusButtons);
                } else {
                    //do nothing
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
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        refresh.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent actionEvent) {
                // TODO: 11/26/2019
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
}
