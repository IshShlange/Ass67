import Characters.Hero;
import Characters.Observer;
import java.util.*;

public class Battle {
    private List<Hero> heroes;
    private List<Observer> observers;

    public Battle() {
        this.heroes = new ArrayList<>();
        this.observers = new ArrayList<>();
    }

    public void addHero(Hero hero) {
        heroes.add(hero);
        // Подписываем героя на всех существующих наблюдателей
        for (Observer observer : observers) {
            hero.registerObserver(observer);
        }
    }

    public void addObserver(Observer observer) {
        observers.add(observer);
        // Подписываем наблюдателя на всех существующих героев
        for (Hero hero : heroes) {
            hero.registerObserver(observer);
        }
    }

    public void startBattle() {
        notifyAll("⚔️ БИТВА НАЧИНАЕТСЯ! ⚔️");

        // Выводим список участников
        StringBuilder participants = new StringBuilder("Участники: ");
        for (Hero hero : heroes) {
            participants.append(hero.getName()).append(" (").append(hero.getHealth()).append(" HP) ");
        }
        notifyAll(participants.toString());

        while (getAliveHeroes().size() > 1) {
            for (Hero hero : getAliveHeroes()) {
                if (!hero.isAlive()) continue;

                Hero target = getRandomAliveTarget(hero);
                if (target != null) {
                    notifyAll("\n--- " + hero.getName() + " атакует " + target.getName() + " ---");
                    hero.attack(target);

                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    }
                }
            }
        }

        List<Hero> alive = getAliveHeroes();
        if (alive.size() == 1) {
            notifyAll("🎉 ПОБЕДИТЕЛЬ: " + alive.get(0).getName() + "! 🎉");
        } else {
            notifyAll("💀 НИЧЬЯ! Все пали в бою. 💀");
        }
    }

    private List<Hero> getAliveHeroes() {
        List<Hero> alive = new ArrayList<>();
        for (Hero hero : heroes) {
            if (hero.isAlive()) alive.add(hero);
        }
        return alive;
    }

    private Hero getRandomAliveTarget(Hero attacker) {
        List<Hero> possibleTargets = new ArrayList<>();
        for (Hero hero : heroes) {
            if (hero.isAlive() && hero != attacker) {
                possibleTargets.add(hero);
            }
        }
        if (possibleTargets.isEmpty()) return null;
        return possibleTargets.get(new Random().nextInt(possibleTargets.size()));
    }

    private void notifyAll(String message) {
        for (Observer observer : observers) {
            observer.update(message);
        }
    }
}