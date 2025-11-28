package characters;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import engine.Room;

public class Potion extends Item {
    public enum Type {
        HEALING, POISON, FELIX_FELICIS
    }

    private final Type type;

    public Potion(String name) {
        super(name);
        // Randomly assign type
        int roll = ThreadLocalRandom.current().nextInt(10);
        if (roll < 8) {
            this.type = Type.HEALING; // 80% chance
        } else if (roll < 9) {
            this.type = Type.POISON; // 10% chance
        } else {
            this.type = Type.FELIX_FELICIS; // 10% chance
        }
    }

    public Type getType() { return type; }

    /**
     * When a character drinks the potion, apply an effect.
     */
    public void drink(Character character) {
        switch (type) {
            case HEALING:
                System.out.println(character.getName() + " drinks " + getName() + " and feels rejuvenated!");
                character.heal(25);
                break;
            case POISON:
                System.out.println(character.getName() + " drinks " + getName() + " and feels sick!");
                character.takeDamage(20);
                break;
            case FELIX_FELICIS:
                System.out.println(character.getName() + " drinks Felix Felicis and feels incredibly lucky!");
                // Move character to a room with a Horcrux fragment if possible
                List<Room> rooms = character.world.getRooms();
                for (Room r : rooms) {
                    for (Item i : r.getItems()) {
                        if (i instanceof HorcruxFragment) {
                            character.moveTo(r);
                            System.out.println(character.getName() + " is guided by luck to " + r.getName() + " containing a Horcrux fragment!");
                            return;
                        }
                    }
                }
                System.out.println("But there are no Horcrux fragments left to find!");
                break;
        }
    }
}
