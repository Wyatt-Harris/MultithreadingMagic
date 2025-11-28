package engine;

import java.util.*;
import java.util.concurrent.ConcurrentLinkedQueue;
import characters.Item;

public class Room {
    private final String name;
    private final ConcurrentLinkedQueue<Item> items = new ConcurrentLinkedQueue<>();

    public Room(String name) { this.name = name; }
    public String getName() { return name; }
    public Collection<Item> getItems() { return items; }
    public void addItem(Item i) { items.add(i); }
    public boolean removeItem(Item i) { return items.remove(i); }
}
