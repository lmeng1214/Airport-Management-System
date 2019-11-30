import java.io.Serializable;
/**
 * BoardingPass - generates a user's boarding pass
 *
 * @author Janis Vuskalns jvuskaln@purdue.edu
 * @version 11/21/2019
 *
 * @author Lenny Meng, meng110@purdue.edu
 * @version Nov 23 2019
 */
public class BoardingPass implements Serializable {
    //fields displayed on boarding pass
    private Airline airline;
    private String firstName;
    private String lastName;
    private int age;
    private String gateNumber;

    public BoardingPass(Airline airline, Passenger passenger, String gateNumber) {
        this.airline = airline;
        this.firstName = passenger.getFirstName();
        this.lastName = passenger.getLastName();
        this.age = passenger.getAge();
        this.gateNumber = gateNumber;
    }//without flightNumber, makes the
    //flightNumber what it is in the video

    public String toString() {
        //number of hyphens will be adjusted accordingly
        String pass = "<html>-----------------------------------<br/>";
        pass += "BOARDING PASS FOR FLIGHT 18000 WITH " +
                airline.getName() + " Airlines" + "<br/>";//line one
        pass += "PASSENGER FIRST NAME : " + firstName.toUpperCase() + "<br/>";
        pass += "PASSENGER LAST NAME : " + lastName.toUpperCase() + "<br/>";
        pass += "PASSENGER AGE : " + age + "<br/>";
        pass += "You can now begin boarding at gate " + gateNumber + "<br/>";
        pass += "-----------------------------------<html>";

        return pass;
    }
}
