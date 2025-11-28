package characters;

import engine.GameWorld;

public class Ron extends Character {
    public Ron(String name, GameWorld world) { super(name, world); }

    @Override
    protected void performTurn() {
        System.out.println(name + ": Scouting for traps...");
        // 50% chance Ron will try to disarm a trap
        boolean tryDisarm = java.util.concurrent.ThreadLocalRandom.current().nextBoolean();
        boolean foundTrap = false;
        if (tryDisarm) {
            for (Item i : room.getItems()) {
                if (i instanceof TrapItem) {
                    foundTrap = true;
                    System.out.println(name + " found and disarmed a trap in " + room.getName() + "!");
                    room.removeItem(i);
                    break;
                }
            }
        }
        if (!foundTrap) {
            System.out.println(name + " found no traps to disarm in " + room.getName() + ".");
        }
        maybeFindPotion();
    }
}
