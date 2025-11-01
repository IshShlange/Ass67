package Characters;
import strategies.*;
import java.util.Random;
public class Archer extends Hero {

    private boolean empoweredShotReady = false; // Сила Орла
    private boolean hunterRage = false;         // Ярость Охотника
    private boolean extraTurnGranted = false;   // Дополнительный ход при добивании

    public Archer(String name) {
        super(name, 100, 18);
        this.attackStrategy = new RangedAttack();
        this.dodgeChance = 0.25;
        this.blockChance = 0.05;
    }

    @Override
    public void attack(CharacterInterface target) {
        Hero heroTarget = (Hero) target;

        if (empoweredShotReady) {
            int baseDamage = getBaseAttack();
            int damage = (int) (baseDamage * 1.2);
            heroTarget.takeDamage(damage);
            notifyObservers(name + " выпускает магическую стрелу Силы Орла и наносит " + damage + " урона! 💥");

            if (!heroTarget.isAlive()) {
                empoweredShotReady = true;
                extraTurnGranted = true;
                notifyObservers(name + " поражает врага насмерть и продлевает Силу Орла! 🦅");
            } else {
                empoweredShotReady = false;
                setAttackStrategy(new RangedAttack());
                notifyObservers(name + " возвращается к обычным атакам 🏹");
            }
            return;
        }

        if (hunterRage) {
            int baseDamage = getBaseAttack()+4;
            int damage = (int) (baseDamage * 1.5 + heroTarget.getMaxHealth() * 0.03);
            heroTarget.takeDamage(damage);
            hunterRage = false;
            setAttackStrategy(new RangedAttack());
            notifyObservers(name + " стреляет в Ярости Охотника, наносит " + damage + " урона!");
            return;
        }

        if (extraTurnGranted) {
            extraTurnGranted = false;
            notifyObservers(name + " получает дополнительный ход благодаря Силе Орла!");
            return;
        }
        super.attack(heroTarget);
        attackStrategy.attack(this, heroTarget);
        if (!heroTarget.isAlive()) {
            choosePower();
        }
    }

    private void choosePower()
    {
        notifyObservers("\n" + name + " добивает врага! 💀");
        //notifyObservers("Выберите способность:");
        //notifyObservers("1 Сила Орла (магическая стрела, можно продлить при убийстве)");
        //notifyObservers("2 Ярость Охотника (магическая стрела но сильнее)");

        int choice= new Random().nextInt(2) + 1;
        //while (true) {
        //    System.out.print("Ваш выбор (1 или 2): ");
        //    if (scanner.hasNextInt()) {
        //        choice = scanner.nextInt();
        //        if (choice == 1 || choice == 2) break;
        //    }
        //    scanner.nextLine();
        //    System.out.println("Некорректный ввод! Попробуйте снова.");
        //}

        if (choice == 1) {
            empoweredShotReady = true;
            setAttackStrategy(new MagicAttack());
            notifyObservers(name + " получает Силу Орла! 🦅");
        } else {
            hunterRage = true;
            setAttackStrategy(new MagicAttack());
            notifyObservers(name + " впадает в Ярость Охотника! 🏹");
        }
    }
}