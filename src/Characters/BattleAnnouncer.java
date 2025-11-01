package Characters;

public class BattleAnnouncer implements Observer {
    @Override
    public void update(String event) {
        System.out.println("🎤 " + event.toUpperCase());
    }
}
