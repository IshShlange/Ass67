package Characters;
import strategies.*;

import java.util.List;

public class Mage extends Hero {
    private int mana;
    private int maxMana;
    private int shieldCharges = 0;
    public Mage(String name) {
        super(name, 90, 25);
        this.attackStrategy = new MagicAttack();
        this.dodgeChance = 0.1;
        this.blockChance = 0.0;
        this.mana = 120;
        this.maxMana = 120;
    }
    public void castStoneStorm(List<Hero> enemies) {
        if (mana < 40) {
            notifyObservers(name + " не хватает маны для Каменного шквала!");
            return;
        }

        mana -= 40;
        for (Hero enemy : enemies) {
            enemy.takeDamage(40);
        }
        shieldCharges = 2;
        notifyObservers(name + " вызывает Каменный шквал! 🪨");
    }

    @Override
    public void attack(CharacterInterface target) {

    }

    @Override
    public void takeDamage(int dmg) {
        if (shieldCharges > 0) {
            notifyObservers(name + " защищен щитом. Удар заблокирован!");
            shieldCharges--;
            return;
        }
        super.takeDamage(dmg);
    }
}
