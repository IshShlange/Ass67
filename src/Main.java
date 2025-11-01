import Characters.*;
import strategies.*;
import java.util.*;

public class Main {
    private static Scanner scanner = new Scanner(System.in);
    private static List<Hero> allHeroes = new ArrayList<>();

    public static void main(String[] args) {
        initializeHeroes();
        Hero playerHero = choosePlayerHero();
        List<Hero> enemies = chooseEnemies(playerHero);
        startInteractiveBattle(playerHero, enemies);
    }

    private static void initializeHeroes() {
        allHeroes.add(new Warrior("Гордый Воин Рико"));
        allHeroes.add(new Archer("Крысявый Лучник Шкипер"));
        allHeroes.add(new Mage("Капец Умный Маг Кавальский"));
    }

    private static Hero choosePlayerHero() {
        System.out.println("\n=== ВЫБЕРИ СВОЕГО ГЕРОЯ ===");
        for (int i = 0; i < allHeroes.size(); i++) {
            Hero hero = allHeroes.get(i);
            System.out.println((i + 1) + ". " + hero.getName() +
                    " (HP: " + hero.getMaxHealth() +
                    ", Атака: " + hero.getBaseAttack() + ")");
        }

        int choice;
        while (true) {
            System.out.print("Твой выбор (1-" + allHeroes.size() + "): ");
            if (scanner.hasNextInt()) {
                choice = scanner.nextInt();
                if (choice >= 1 && choice <= allHeroes.size()) {
                    Hero selected = allHeroes.get(choice - 1);
                    System.out.println("🎯 Ты выбрал: " + selected.getName());
                    return selected;
                }
            }
            scanner.nextLine();
            System.out.println("❌ Неверный выбор! Попробуй снова.");
        }
    }

    private static List<Hero> chooseEnemies(Hero playerHero) {
        List<Hero> availableEnemies = new ArrayList<>();
        List<Hero> selectedEnemies = new ArrayList<>();

        for (Hero hero : allHeroes) {
            if (hero != playerHero) {
                availableEnemies.add(hero);
            }
        }

        System.out.println("\n=== ВЫБЕРИ ПРОТИВНИКОВ ===");
        System.out.println("Сколько противников хочешь? (1-" + availableEnemies.size() + ")");

        int enemyCount;
        while (true) {
            System.out.print("Количество: ");
            if (scanner.hasNextInt()) {
                enemyCount = scanner.nextInt();
                if (enemyCount >= 1 && enemyCount <= availableEnemies.size()) {
                    break;
                }
            }
            scanner.nextLine();
            System.out.println("❌ Неверное количество! Попробуй снова.");
        }

        // Случайно выбираем врагов
        Collections.shuffle(availableEnemies);
        for (int i = 0; i < enemyCount; i++) {
            selectedEnemies.add(availableEnemies.get(i));
        }

        System.out.println("\n⚔️ Твои противники:");
        for (Hero enemy : selectedEnemies) {
            System.out.println("• " + enemy.getName());
        }

        return selectedEnemies;
    }

    private static void startInteractiveBattle(Hero player, List<Hero> enemies) {
        ConsoleLogger logger = new ConsoleLogger();
        player.registerObserver(logger);
        for (Hero enemy : enemies) {
            enemy.registerObserver(logger);
        }

        List<Hero> allParticipants = new ArrayList<>();
        allParticipants.add(player);
        allParticipants.addAll(enemies);

        System.out.println("\n⚔️ БИТВА НАЧИНАЕТСЯ! ⚔️");

        int round = 1;
        while (getAliveHeroes(allParticipants).size() > 1 && player.isAlive()) {
            System.out.println("\n=== РАУНД " + round + " ===");

            playerTurn(player, enemies);

            for (Hero enemy : getAliveHeroes(enemies)) {
                if (player.isAlive()) {
                    Hero target = getPlayerTarget(enemy, allParticipants);
                    if (target != null) {
                        System.out.println("\n--- " + enemy.getName() + " атакует ---");
                        enemy.attack(target);
                    }
                }
            }

            round++;

        }

        if (player.isAlive()) {
            System.out.println("\n🎉 ПОБЕДА! Ты победил всех противников! 🎉");
        } else {
            System.out.println("\n💀 ПОРАЖЕНИЕ! Ты пал в бою. 💀");
        }
    }
    private static void playerTurn(Hero player, List<Hero> enemies) {
        System.out.println("\n--- ТВОЙ ХОД ---");
        System.out.println("Твое HP: " + player.getHealth() + "/" + player.getMaxHealth());

        List<Hero> aliveEnemies = getAliveHeroes(enemies);

        System.out.println("Доступные цели:");
        for (int i = 0; i < aliveEnemies.size(); i++) {
            Hero enemy = aliveEnemies.get(i);
            System.out.println((i + 1) + ". " + enemy.getName() +
                    " (HP: " + enemy.getHealth() + "/" + enemy.getMaxHealth() + ")");
        }

        int choice;
        while (true) {
            System.out.print("Выбери цель для атаки (1-" + aliveEnemies.size() + "): ");
            if (scanner.hasNextInt()) {
                choice = scanner.nextInt();
                scanner.nextLine(); // очистка буфера
                if (choice >= 1 && choice <= aliveEnemies.size()) {
                    Hero target = aliveEnemies.get(choice - 1);
                    player.attack(target);
                    break;
                }
            } else {
                scanner.nextLine(); // очистка буфера
            }
            System.out.println("❌ Неверный выбор! Попробуй снова.");
        }
    }

    private static Hero getPlayerTarget(Hero attacker, List<Hero> allParticipants) {
        List<Hero> possibleTargets = new ArrayList<>();
        for (Hero hero : allParticipants) {
            if (hero.isAlive() && hero != attacker) {
                possibleTargets.add(hero);
            }
        }
        if (possibleTargets.isEmpty()) return null;
        return possibleTargets.get(new Random().nextInt(possibleTargets.size()));
    }

    private static List<Hero> getAliveHeroes(List<Hero> heroes) {
        List<Hero> alive = new ArrayList<>();
        for (Hero hero : heroes) {
            if (hero.isAlive()) alive.add(hero);
        }
        return alive;
    }
}