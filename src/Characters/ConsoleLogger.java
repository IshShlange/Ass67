package Characters;

public class ConsoleLogger implements Observer {
    @Override
    public void update(String event) {
        System.out.println("📢 " + event);
    }
}
