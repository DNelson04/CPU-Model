public class Main {
    public static void main(String[] args) {
        CPU cpu = new CPU("processes.csv", 2);
        cpu.run();
    }
}