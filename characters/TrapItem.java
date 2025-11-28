package characters;

public class TrapItem extends Item {
    public TrapItem(String name) {
        super(name);
    }

    public void trigger(Character character) {
        System.out.println(character.getName() + " triggered a trap!");
        character.takeDamage(20); // Trap deals 20 damage
    }
}
