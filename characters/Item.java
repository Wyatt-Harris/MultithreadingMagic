package characters;

public abstract class Item {
    protected final String name;
    public Item(String name) { this.name = name; }
    public String getName() { return name; }
}
