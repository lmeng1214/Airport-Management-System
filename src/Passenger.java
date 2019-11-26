import java.io.Serializable;

public class Passenger implements Serializable {
    private int age;
    private String firstName;
    private String lastName;
    private String boardingPass;

    public Passenger(String age, String firstName, String lastName) {
        this.age = Integer.parseInt(age);
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public void setBoardingPass(String boardingPass) {
        this.boardingPass = boardingPass;
    }

    public int getAge() {
        return age;
    }
    public String getFirstName() {
        return firstName;
    }
    public String getLastName() {
        return lastName;
    }
    public String getBoardingPass() {
        return boardingPass;
    }

    public String toString() {
         return firstName.charAt(0) + ". " + lastName + ", " +  age + "\n";
    }
}
