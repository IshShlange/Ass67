import Characters.*;
import strategies.*;

public class GameOnProgramm {
    public static void main(String[] args) {
        Battle battle = new Battle();
        Characters.ConsoleLogger logger = new Characters.ConsoleLogger();
        battle.addObserver(logger);


        Warrior warrior = new Warrior("Воин");
        Archer archer = new Archer("Лучник");
        Mage mage = new Mage("Маг");

        battle.addHero(warrior);
        battle.addHero(archer);
        battle.addHero(mage);
        Archer archer2 = new Archer("Леголас");
        battle.addHero(archer2);
        Archer archer3 = new Archer("Ахилес");
        battle.addHero(archer3);


        battle.startBattle();
    }
}
