package engine;

import java.util.function.Consumer;

public class Event {
    private final String name;
    private final Consumer<Event> handler;

    public Event(String name, Consumer<Event> handler) {
        this.name = name;
        this.handler = handler;
    }

    public String getName() { return name; }
    public void handle() { handler.accept(this); }
}
