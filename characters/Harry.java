package characters;

import java.util.concurrent.ThreadLocalRandom;
import engine.GameWorld;
import engine.Room;

public class Harry extends Character {
    public Harry(String name, GameWorld world) { super(name, world); }

    /**
     * Harry casts Expecto Patronum to protect a character from a Dementor attack.
     * Returns true if successful, false if failed.
     */
    public boolean castExpectoPatronum(Character target) {
        System.out.println(name + " attempts to cast 'Expecto Patronum' to protect " + target.getName() + "!");
        // 20% chance to fail
        boolean success = java.util.concurrent.ThreadLocalRandom.current().nextInt(5) != 0;
        if (success) {
            System.out.println("Expecto Patronum succeeds! " + target.getName() + " is protected from the Dementor.");
        } else {
            System.out.println("Expecto Patronum fails! " + target.getName() + " is vulnerable to the Dementor.");
        }
        return success;
    }

    @Override
    protected void performTurn() {
        System.out.println(name + ": Searching for Horcrux fragments...");
        if (room instanceof engine.Room) {
            ((engine.Room) room).getItems().stream()
                .filter(i -> i instanceof HorcruxFragment)
                .findFirst()
                .ifPresent(i -> {
                    if (((engine.Room) room).removeItem(i)) {
                        for (int idx = 0; idx < inventory.length; idx++) {
                            if (inventory[idx] == null) {
                                inventory[idx] = i;
                                System.out.println(name + " picked up a Horcrux fragment!");
                                break;
                            }
                        }
                    }
                });
        }

        // Random chance to trip a trap (1 in 4)
        if (ThreadLocalRandom.current().nextInt(4) == 0 && room instanceof engine.Room) {
            ((engine.Room) room).getItems().stream()
                .filter(i -> i instanceof TrapItem)
                .findFirst()
                .ifPresent(i -> ((TrapItem)i).trigger(this));
        }

        // Drink a potion if health < 100 and have one
        for (int i = 0; i < inventory.length; i++) {
            if (health < 100 && inventory[i] instanceof Potion) {
                ((Potion)inventory[i]).drink(this);
                inventory[i] = null;
                break;
            }
        }

        // 1 in 3 chance to move to a new room
        if (ThreadLocalRandom.current().nextInt(3) == 0) {
            moveToRandomRoom();
        }
        maybeFindPotion();
    }
}
