import javafx.scene.shape.Box;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.*;
import javax.swing.*;
import javax.tools.Tool;

public class GUI {

    public static void main(String[] args) throws IOException {
        //variables
        final String[] firstName = {""};
        final String[] lastName = {""};
        final int[] age = {0};
        final String[] airline = {""};
        /**
         * The first two functions are getting hostname and port number
         */
        String hostName = JOptionPane.showInputDialog//video first question
                (null,
                        "What is the hostname you'd like to connect to?",
                        "Hostname?", JOptionPane.QUESTION_MESSAGE);
        int portNumber = Integer.parseInt(JOptionPane.showInputDialog//video second question
                (null,
                        "What is the port you'd like to connect to?",
                        "Port?", JOptionPane.QUESTION_MESSAGE));

        /**
         * declaration of the System frame
         */
        //the frame that holds the system
        JFrame system = new JFrame("Purdue University Flight Reservation System");
        system.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        int width = (Toolkit.getDefaultToolkit().getScreenSize().width / 2);
        int height = (Toolkit.getDefaultToolkit().getScreenSize().height / 2);
        system.setSize(width, height);
        system.setLocationRelativeTo(null);

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
         * everything for the introduction panel
         */
        introductionPanel.setLayout(new BorderLayout());
        //stuff to add to intro panel
        JLabel welcomeLabel = new JLabel("Welcome to the Purdue University Airline" +
                " Reservation Management System!");//text
        BufferedImage purdueLogo = ImageIO.read(new File
                ("C:\\Users\\janis_sfifymt\\PJ05-CS180\\Images\\PurudeLogo.png"));
        JLabel logoLabel = new JLabel(new ImageIcon(purdueLogo));//image
        JButton welcomeExit = new JButton("Exit");
        JButton bookButton = new JButton("Book a Flight");

        //action listeners
        bookButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent actionEvent) {
                introductionPanel.setVisible(false);
                system.add(bookPanel);
            }
        });
        welcomeExit.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent actionEvent) {
                system.dispose();
            }
        });


        //cards for layout
        JPanel welcomeCard = new JPanel();
        welcomeCard.add(welcomeLabel);
        JPanel logoCard = new JPanel();
        logoCard.add(logoLabel);
        JPanel buttonCard = new JPanel();
        buttonCard.add(welcomeExit);
        buttonCard.add(bookButton);

        //layout
        introductionPanel.add(welcomeCard, BorderLayout.PAGE_START);
        introductionPanel.add(logoCard, BorderLayout.CENTER);
        introductionPanel.add(buttonCard, BorderLayout.PAGE_END);

        /**
         * the book panel
         */
        bookPanel.setLayout(new BorderLayout());
        //stuff to add
        JLabel bookLabel = new JLabel("Do you want to book a flight today?");
        JButton bookExit = new JButton("Exit");
        JButton confirmBooking = new JButton("Yes, I want to book a flight");

        //action listeners
        confirmBooking.addActionListener(actionEvent -> {
            bookPanel.setVisible(false);
            system.add(airlineChoice);
        });
        bookExit.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent actionEvent) {
                system.dispose();
            }
        });

        //cards for layout
        JPanel questionCard = new JPanel();
        questionCard.add(bookLabel);
        JPanel panel2Buttons = new JPanel();
        panel2Buttons.add(bookExit);
        panel2Buttons.add(confirmBooking);

        //layout
        bookPanel.add(questionCard, BorderLayout.PAGE_START);
        bookPanel.add(panel2Buttons, BorderLayout.PAGE_END);

        /**
         * airlineChoice
         */
        //information Variables used
        //airline
        String airlineOptions[] = new String[] {"Delta", "Southwest", "Alaska"};
        //this is a variable used in confirmFlight, but in the case that a user chooses
        //to change their flight after they confirm it, it must be updated in the
        //action listener for the JComboBox that is declared in this section
        JLabel confirmPrompt = new JLabel("Are you sure you want to book a flight on " +
                airline[0] + " Airlines?");
        //layout
        airlineChoice.setLayout(new BoxLayout(airlineChoice, BoxLayout.Y_AXIS));
        //stuff to add to airlineChoice
        JLabel flightPrompt = new JLabel("Choose a flight from the drop down menu.");
        JComboBox airlines = new JComboBox(airlineOptions);
        JLabel info = new JLabel("Insert Delta info here");
        JButton airlineChoiceExit = new JButton("Exit");
        JButton chosenFlight = new JButton("Choose this flight");

        //actionListeners
        airlineChoiceExit.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent actionEvent) {
                system.dispose();
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
                system.add(confirmFlight);
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

        //cards for layout
        JPanel airlineLabel = new JPanel();
        airlineLabel.add(flightPrompt);
        JPanel airlineComboBox = new JPanel();
        airlineComboBox.add(airlines);
        JPanel infoLabel = new JPanel();
        infoLabel.add(info);

        JPanel airlinesButtons = new JPanel();
        airlinesButtons.add(airlineChoiceExit);
        airlinesButtons.add(chosenFlight);

        //layout
        airlineChoice.add(airlineLabel);
        airlineChoice.add(airlineComboBox);
        airlineChoice.add(infoLabel);
        airlineChoice.add(airlinesButtons);

        /**
         * confirm flight
         */
        confirmFlight.setLayout(new BorderLayout());
        //stuff to add to confirmFlight
        JButton confirmExit = new JButton("Exit");
        JButton deny = new JButton("No, I want a different flight.");
        JButton confirm = new JButton("Yes, I want this flight.");

        //action listeners
        confirmExit.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent actionEvent) {
                system.dispose();
            }
        });
        deny.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent actionEvent) {
                system.remove(confirmFlight);
                airlineChoice.setVisible(true);
            }
        });
        confirm.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent actionEvent) {
                confirmFlight.setVisible(false);
                system.add(informationPanel);
            }
        });

        //cards for layout
        JPanel confirmLabels = new JPanel();
        confirmLabels.add(confirmPrompt);
        JPanel confirmButtons = new JPanel();
        confirmButtons.add(confirmExit);
        confirmButtons.add(deny);
        confirmButtons.add(confirm);

        //layout
        confirmFlight.add(confirmLabels, BorderLayout.PAGE_START);
        confirmFlight.add(confirmButtons, BorderLayout.PAGE_END);

        /**
         * InformationPanel
         */
        //information variables used
        //first name
        //last name
        //age
        informationPanel.setLayout(new BorderLayout());
        //stuff to add
        JLabel infoPrompt = new JLabel("Please put your information below.");
        JLabel firstNamePrompt = new JLabel("What is your first name?");
        JLabel lastNamePrompt = new JLabel("What is your last name?");
        JLabel agePrompt = new JLabel("What is your age?");
        JTextField firstNameField = new JTextField();
        JTextField lastNameField = new JTextField();
        JTextField ageField = new JTextField();
        JButton exitInfo = new JButton("Exit");
        JButton nextInfo = new JButton("Next");

        //action listeners
        exitInfo.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent actionEvent) {
                system.dispose();
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
                     system.add(flightStatus);
                 } else {
                     //do nothing
                 }
            }
        });

        //cards for layout
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

        //layout
        informationPanel.add(entreeLabel, BorderLayout.PAGE_START);
        informationPanel.add(promptsAndFields, BorderLayout.CENTER);
        informationPanel.add(infoButtons, BorderLayout.PAGE_END);

        /**
         * flightStatus
         */

        system.add(introductionPanel);
        system.setVisible(true);
    }
}
