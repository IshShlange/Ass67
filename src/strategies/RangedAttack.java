package strategies;
import Characters.Hero;

public class RangedAttack implements AttackStrategy {

    @Override
    public void attack(Hero attacker, Hero target) {
        int damage = (int)(attacker.getBaseAttack() * 0.9 + Math.random() * 8);
        target.takeDamage(damage);
        attacker.notifyObservers(attacker.getName() + " стреляет издалека и наносит " + damage + " урона 🏹");
    }
}
