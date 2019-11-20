public class Driver {

    public static void main(String[] args) {
        int numberOfTests = 5;//adjust how many tests for each class

        //testing gate
        for (int i = 0; i < numberOfTests; i++) {
            System.out.println(new Gate().generateGate());
        }

        //testing boarding pass
        String gateForBoardingPass = new Gate().generateGate();
        System.out.println(new BoardingPass("Delta", "John",
                "Doe", 1, gateForBoardingPass));
        gateForBoardingPass = new Gate().generateGate();
        System.out.println(new BoardingPass("Southwest", "Jane",
                "Doo", 2, gateForBoardingPass));
        gateForBoardingPass = new Gate().generateGate();
        System.out.println(new BoardingPass("Alaska", "Jacob",
                "Fly", 3, gateForBoardingPass));
        gateForBoardingPass = new Gate().generateGate();
        System.out.println(new BoardingPass(1100000,"Delta", "James",
                "Smith", 4, gateForBoardingPass));
        gateForBoardingPass = new Gate().generateGate();
        System.out.println(new BoardingPass(2,"Southwest", "Firstname",
                "Lastname", 5, gateForBoardingPass));
        gateForBoardingPass = new Gate().generateGate();
        System.out.println(new BoardingPass(461298,"Alaska", "Last",
                "test", 6, gateForBoardingPass));

        //testing passenger
        Passenger passenger = new Passenger("1", "John", "Doe");
        gateForBoardingPass = new Gate().generateGate();
        BoardingPass boardingPass = new BoardingPass("AirlineExample", passenger.getFirstName(),
                passenger.getLastName(), passenger.getAge(), gateForBoardingPass);
        passenger.setBoardingPass(boardingPass.toString());
        System.out.println(passenger.getBoardingPass());
        System.out.println(passenger.getAge());
        System.out.println(passenger.getFirstName());
        System.out.println(passenger.getLastName());
        System.out.println(passenger.toString());

    }

    public void TestBoardingPass() {

    }
}
