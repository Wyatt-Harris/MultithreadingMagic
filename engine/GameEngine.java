package engine;

import characters.Character;
import characters.Dementor;
import characters.Harry;
import characters.Hermione;
import characters.HorcruxFragment;
import characters.Item;
import characters.Ron;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class GameEngine {
    public static void main(String[] args) throws InterruptedException {
        System.out.println("Starting Threads of Magic");

        GameWorld world = new GameWorld();

        EventDispatcher dispatcher = new EventDispatcher(world.getEventQueue());
        Thread dispatcherThread = new Thread(dispatcher, "EventDispatcher");
        dispatcherThread.start();

        Thread weatherThread = new Thread(new WeatherSystem(world), "WeatherSystem");
        weatherThread.setDaemon(true);
        weatherThread.start();

        Harry harry = new Harry("Harry", world);
        Ron ron = new Ron("Ron", world);
        Hermione hermione = new Hermione("Hermione", world, null); // placeholder
        java.util.List<Character> allChars = java.util.Arrays.asList(harry, hermione, ron);
        hermione.setAllChars(allChars);
        allChars = java.util.Arrays.asList(harry, hermione, ron);
        ExecutorService characters = Executors.newFixedThreadPool(3);
        characters.submit(harry);
        characters.submit(hermione);
        characters.submit(ron);

        Dementor dementor = new Dementor("Dementor");
        int dementorAttacks = 0;
        boolean dementorBanished = false;
        java.util.Set<String> horcruxTriggered = new java.util.HashSet<>();
        java.util.Map<Character, java.util.Set<String>> prevHorcruxes = new java.util.HashMap<>();

        int maxTurns = 30;
        int turn = 0;
        boolean win = false, loss = false;
        while (!world.isGameOver() && turn < maxTurns) {
            Thread.sleep(2000); // Slow down game loop for readability
            turn++;

            // Check if any character has found a new Horcrux fragment this turn
            String newHorcruxName = null;
            for (Character c : allChars) {
                java.util.Set<String> prev = prevHorcruxes.getOrDefault(c, new java.util.HashSet<>());
                for (Item i : c.inventory) {
                    if (i instanceof HorcruxFragment) {
                        String name = i.getName();
                        if (!prev.contains(name) && !horcruxTriggered.contains(name)) {
                            newHorcruxName = name;
                            horcruxTriggered.add(name);
                            break;
                        }
                    }
                }
                // Update prevHorcruxes for next turn
                java.util.Set<String> current = new java.util.HashSet<>();
                for (Item i : c.inventory) {
                    if (i instanceof HorcruxFragment) {
                        current.add(i.getName());
                    }
                }
                prevHorcruxes.put(c, current);
            }

            // If a new Horcrux is picked up and Dementor hasn't attacked 3 times, trigger Dementor
            if (newHorcruxName != null && dementorAttacks < 3 && !dementorBanished) {
                dementorAttacks++;
                System.out.println("A Horcrux fragment has been picked up! Dementor appears!");
                java.util.List<Character> possibleTargets = new java.util.ArrayList<>(allChars);
                Character target = possibleTargets.get(java.util.concurrent.ThreadLocalRandom.current().nextInt(possibleTargets.size()));
                boolean protectedByHarry = harry.castExpectoPatronum(target);
                if (protectedByHarry) {
                    System.out.println("The Dementor has been banished and will not attack again!");
                    dementorBanished = true;
                } else {
                    dementor.attack(target, harry);
                }
            }

            // Check win/loss conditions
            if (world.allHorcruxesSolved(allChars)) {
                win = true;
                world.setGameOver(true);
                break;
            }
            for (Character c : allChars) {
                if (!c.alive) {
                    loss = true;
                    world.setGameOver(true);
                    break;
                }
            }
        }

        System.out.println("Shutting down...");
        characters.shutdownNow();
        dispatcher.stop();
        dispatcherThread.interrupt();
        weatherThread.interrupt();

        if (win) {
            System.out.println("Victory! All Horcruxes have been solved!");
        } else if (loss) {
            System.out.println("Defeat! Someone has fallen.");
        } else {
            System.out.println("Defeat! Not all Horcruxes were solved in time.");
        }
    }
}

