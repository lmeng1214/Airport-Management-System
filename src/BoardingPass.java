import java.io.Serializable;

public class BoardingPass implements Serializable {
    //fields displayed on boarding pass
    private int flightNumber;
    private String airline;
    private String firstName;
    private String lastName;
    private int age;
    private String gateNumber;

    public BoardingPass(String airline, String firstName,
                        String lastName, int age, String gateNumber) {
        flightNumber = 180000;
        this.airline = airline;
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
        this.gateNumber = gateNumber;
    }//without flightNumber, makes the
    //flightNumber what it is in the video

    public BoardingPass(int flightNumber, String airline, String firstName,
                        String lastName, int age, String gateNumber) {
        this.flightNumber = flightNumber;
        this.airline = airline;
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
        this.gateNumber = gateNumber;
    }//with flightNumber, if it is supposed to be unique per flight

    public String toString() {
        //number of hyphens will be adjusted accordingly
        String pass = "-----------------------------------\n";
        pass += "BOARDING PASS FOR FLIGHT " + flightNumber + " WITH " +
                airline + " airlines\n";//line one
        pass += "PASSENGER FIRST NAME : " + firstName.toUpperCase() + "\n";
        pass += "PASSENGER LAST NAME : " + lastName.toUpperCase() + "\n";
        pass += "PASSENGER AGE : " + age + "\n";
        pass += "You can now begin boarding at gate " + gateNumber + "\n";
        pass += "-----------------------------------";

        return pass;
    }
}
