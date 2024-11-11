public class Main {
    public static void main(String[] args) {
        CPU cpu = new CPU(args[0], Integer.parseInt(args[1]));
        cpu.start();
    }
}