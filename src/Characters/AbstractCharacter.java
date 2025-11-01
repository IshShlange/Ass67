package Characters;
public abstract class AbstractCharacter implements CharacterInterface {
    protected String name;
    protected int health;
    protected int maxHealth;
    protected int level;
    protected int damage;
    protected int mana;
    protected int maxMana;


    public AbstractCharacter(String name, int health, int damage) {
        this.name = name;
        this.health = health;
        this.maxHealth = health;
        this.damage = damage;
        this.level = 1;
    }
    @Override
    public boolean isAlive() {
        return health > 0;
    }
    public void levelUp() {
        level++;
        maxHealth += 10;
        damage += 2;
        health = maxHealth;
        if (maxMana>0){}
    }
    public String getName() {
        return name;
    }
    @Override
    public void takeTurn(CharacterInterface target) {
        attack(target);
    }
}
