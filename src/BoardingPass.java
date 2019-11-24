import java.io.Serializable;

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
        String pass = "-----------------------------------\n";
        pass += "BOARDING PASS FOR FLIGHT 18000 WITH " +
                airline.getName() + "\n";//line one
        pass += "PASSENGER FIRST NAME : " + firstName.toUpperCase() + "\n";
        pass += "PASSENGER LAST NAME : " + lastName.toUpperCase() + "\n";
        pass += "PASSENGER AGE : " + age + "\n";
        pass += "You can now begin boarding at gate " + gateNumber + "\n";
        pass += "-----------------------------------";

        return pass;
    }
}
