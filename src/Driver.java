public class Driver {
    public static void main(String[] args) {
        BoardingPass boardingPass = new BoardingPass(new Delta(), new Passenger("19", "Janis", "Vuskalns"), new Gate().generateGate());
        System.out.print(boardingPass.toString());
    }
}
