package Characters;

import strategies.*;
import java.util.*;

public abstract class Hero {
    protected String name;
    protected int health;
    protected int maxHealth;
    protected int baseAttack;
    protected AttackStrategy attackStrategy;
    protected List<Observer> observers = new ArrayList<>();

    protected double dodgeChance = 0.0;
    protected double blockChance = 0.0;
    protected double blockValue = 0.5;
    protected Random random = new Random();

    public Hero(String name, int maxHealth, int baseAttack) {
        this.name = name;
        this.maxHealth = maxHealth;
        this.health = maxHealth;
        this.baseAttack = baseAttack;
    }

    public void attack(Hero target) {
        attackStrategy.attack(this, target);
    }

    public abstract void attack(CharacterInterface target);

    public void takeDamage(int damage) {
        if (random.nextDouble() < dodgeChance) {
            notifyObservers(name + " уклоняется от атаки 💨");
            return;
        }

        if (random.nextDouble() < blockChance) {
            int blocked = (int) (damage * blockValue);
            damage -= blocked;
            notifyObservers(name + " блокирует " + blocked + " урона 🛡️");
        }

        health -= damage;
        if (health < 0) health = 0;
        notifyObservers(name + " получает " + damage + " урона. Осталось HP: " + health);
    }

    public boolean isAlive() {
        return health > 0;
    }

    public String getName() {
        return name;
    }

    public int getBaseAttack() {
        return baseAttack;
    }

    public int getMaxHealth() {
        return maxHealth;
    }

    public void setAttackStrategy(AttackStrategy attackStrategy) {
        this.attackStrategy = attackStrategy;
    }

    // Наблюдатели
    public void registerObserver(Observer observer) {
        observers.add(observer);
    }

    public void removeObserver(Observer observer) {
        observers.remove(observer);
    }

    public void notifyObservers(String event) {
        for (Observer observer : observers) {
            observer.update(event);
        }
    }

    public int getHealth() {
        return health;
    }
}