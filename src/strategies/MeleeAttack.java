package strategies;

import Characters.Hero;

public class MeleeAttack implements AttackStrategy {

    @Override
    public void attack(Hero attacker, Hero target) {
        int damage = attacker.getBaseAttack() + (int)(Math.random() * 5);
        target.takeDamage(damage);
        attacker.notifyObservers(attacker.getName() + " наносит ближний удар на " + damage + " урона ⚔️");
    }
}
