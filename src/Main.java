public class Main {
    public static void main(String[] args) {
        Admin admin = new Admin();
        InputProccessor inputProccessor = new InputProccessor(admin);
        inputProccessor.run();
    }
}
