package strategies;

import Characters.Hero;

public class MagicAttack implements AttackStrategy {

    @Override
    public void attack(Hero attacker, Hero target) {
        int damage = (int)(attacker.getBaseAttack() * 1.2 + Math.random() * 10);
        target.takeDamage(damage);
        attacker.notifyObservers(attacker.getName() + " выпускает заклинание и наносит " + damage + " магического урона 🔮");
    }
}
