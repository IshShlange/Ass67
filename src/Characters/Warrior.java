package Characters;
import strategies.*;

public class Warrior extends Hero {
    private boolean rageTriggered = false;
    private boolean rageUsed = false;
    private int totalDamageTaken = 0;

    public Warrior(String name) {
        super(name, 120, 20);
        this.attackStrategy = new MeleeAttack();
        this.dodgeChance = 0.05;
        this.blockChance = 0.3;
        this.blockValue = 0.6;
    }

    @Override
    public void attack(CharacterInterface target) {
        Hero heroTarget = (Hero) target;
            if (rageTriggered && !rageUsed) {

                AttackStrategy original = this.attackStrategy;
                this.attackStrategy = new MagicAttack();

                notifyObservers(name + " использует 'Ярость Воина' — наносит магический урон!");

                // применяем стратегию
                attackStrategy.attack(this, heroTarget);

                // лечим 10% от maxHealth
                int heal = (int) (maxHealth * 0.1);
                health = Math.min(health + heal, maxHealth);
                notifyObservers(name + " восстанавливает " + heal + " HP 🩸");

                // возвращаем исходную стратегию
                this.attackStrategy = original;

                rageUsed = true;
                rageTriggered = false;
            } else {
                // обычная атака
                attackStrategy.attack(this, heroTarget);
            }
            }


    @Override
    public void takeDamage(int damage) {
        super.takeDamage(damage);
        totalDamageTaken += damage;

        // Когда потеряно 30% здоровья — активируем состояние ярости
        if (!rageTriggered && totalDamageTaken >= maxHealth * 0.3) {
            rageTriggered = true;
            notifyObservers(name + " впадает в ярость! 💢 Следующая атака станет магической!");
        }
    }
}