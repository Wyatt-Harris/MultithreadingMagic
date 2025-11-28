package engine;

import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.*;
import characters.Character;
import characters.Item;
import characters.HorcruxFragment;
import characters.TrapItem;

public class GameWorld {
    private final List<Room> rooms = new ArrayList<>();
    private final BlockingQueue<Event> eventQueue = new LinkedBlockingQueue<>();
    private final AtomicBoolean gameOver = new AtomicBoolean(false);
    private final AtomicReference<String> weather = new AtomicReference<>("Clear");

    public GameWorld() {
        Room hall = new Room("Great Hall");
        Room library = new Room("Library");
        Room dungeon = new Room("Dungeon");
        Room forrest = new Room("Forbidden Forest");
        Room commons = new Room("Gryffindor Commons");
        Room boathouse = new Room("Boathouse");
        rooms.add(hall);
        rooms.add(library);
        rooms.add(dungeon);
        rooms.add(forrest);
        rooms.add(commons);
        rooms.add(boathouse);

        Random rand = new Random();
        String[] horcruxNames = {"Fragment-1", "Fragment-2", "Fragment-3"};
        List<Room> shuffledRooms = new ArrayList<>(rooms);
        Collections.shuffle(shuffledRooms, rand);
        for (int i = 0; i < horcruxNames.length; i++) {
            HorcruxFragment hf = new HorcruxFragment(horcruxNames[i]);
            shuffledRooms.get(i).addItem(hf);
        }

        for (Room r : rooms) {
            r.addItem(new TrapItem("Trap"));
        }
    }

    public List<Room> getRooms() { return Collections.unmodifiableList(rooms); }
    public BlockingQueue<Event> getEventQueue() { return eventQueue; }
    public boolean isGameOver() { return gameOver.get(); }
    public void setGameOver(boolean v) { gameOver.set(v); }
    public String getWeather() { return weather.get(); }
    public void setWeather(String w) { weather.set(w); }

    public boolean allHorcruxesSolved(List<Character> characters) {
        for (Room r : rooms) {
            for (Item i : r.getItems()) {
                if (i instanceof HorcruxFragment) return false;
            }
        }
        for (Character c : characters) {
            for (Item i : c.inventory) {
                if (i instanceof HorcruxFragment) return false;
            }
        }
        return true;
    }
}
