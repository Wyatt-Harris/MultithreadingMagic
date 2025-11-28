package characters;

import java.util.List;
import engine.GameWorld;
import engine.Room;

public class Hermione extends Character {
    private List<Character> allChars;
    public Hermione(String name, GameWorld world, List<Character> allChars) {
        super(name, world);
        this.allChars = allChars;
    }
    public void setAllChars(List<Character> allChars) {
        this.allChars = allChars;
    }

    @Override
    protected void performTurn() {
        System.out.println(name + ": Attempting to purify any Horcrux fragments...");
        // Find all characters in the same room
        for (Character c : this.allChars) {
            if (c.getRoom() == this.getRoom()) {
                for (int i = 0; i < c.inventory.length; i++) {
                    if (c.inventory[i] instanceof HorcruxFragment) {
                        HorcruxFragment h = (HorcruxFragment) c.inventory[i];
                        if (h.examine(this)) {
                            System.out.println(name + " solved a Horcrux fragment for " + c.getName() + "!");
                            c.inventory[i] = null; // Remove after solving
                        }
                    }
                }
            }
        }
        maybeFindPotion();
    }
}
