package characters;

import engine.GameWorld;
import engine.Room;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public abstract class Character implements Runnable {
    protected final String name;
    public final Item[] inventory = new Item[10];
    protected Room room;
    protected final GameWorld world;
    public volatile boolean alive = true;
    protected int health = 100;

    public int getHealth() { return health; }
    public void heal(int amount) {
        health = Math.min(health + amount, 100);
        System.out.println(name + " heals for " + amount + " points. Health: " + health);
    }
    public void takeDamage(int amount) {
        health -= amount;
        System.out.println(name + " takes " + amount + " damage! Health: " + health);
        if (health <= 0) {
            alive = false;
            System.out.println(name + " has fallen!");
        }
    }

    public Character(String name, GameWorld world) {
        this.name = name;
        this.world = world;
        // place character in first room by default
        this.room = world.getRooms().get(0);
    }

    public String getName() { return name; }
    public Room getRoom() { return room; }

    protected void pickUpItem(Item item) {
        synchronized (room) {
            if (room.removeItem(item)) {
                for (int i = 0; i < inventory.length; i++) {
                    if (inventory[i] == null) {
                        inventory[i] = item;
                        System.out.println(name + " picked up " + item.getName());
                        return;
                    }
                }
                System.out.println(name + " inventory full, dropped " + item.getName());
                room.addItem(item);
            }
        }
    }

    protected void moveTo(Room r) {
        System.out.println(name + " moves from " + room.getName() + " to " + r.getName());
        this.room = r;
    }

    public void run() {
        while (!world.isGameOver() && alive) {
            performTurn();
            try { Thread.sleep(ThreadLocalRandom.current().nextInt(1000, 2000)); }
            catch (InterruptedException e) { Thread.currentThread().interrupt(); break; }
        }
        System.out.println(name + " thread exiting.");
    }

    protected void moveToRandomRoom() {
        // Move to a random room (not current)
        List<Room> allRooms = world.getRooms();
        if (allRooms.size() > 1) {
            Room newRoom;
            do {
                newRoom = allRooms.get(ThreadLocalRandom.current().nextInt(allRooms.size()));
            } while (newRoom == room);
            moveTo(newRoom);
        }
    }

    protected void maybeFindPotion() {
        // 20% chance to find a potion each turn
        if (ThreadLocalRandom.current().nextInt(5) == 0) {
            Potion potion = new Potion("Mysterious Potion");
            for (int i = 0; i < inventory.length; i++) {
                if (inventory[i] == null) {
                    inventory[i] = potion;
                    System.out.println(name + " found a " + potion.getName() + " and put it in their inventory.");
                    return;
                }
            }
            System.out.println(name + " found a potion but inventory is full.");
        }
    }

    protected abstract void performTurn();
}
