package Characters;
public interface CharacterInterface {
    void attack(CharacterInterface target);
    void takeDamage(int damage);
    boolean isAlive();
    String getName();
    int getHealth();
    int getBaseAttack();
    int getMaxHealth();
    CharacterType getType();
    void takeTurn(CharacterInterface target);

    void registerObserver(Observer observer);
    void removeObserver(Observer observer);
    void notifyObservers(String event);

}
