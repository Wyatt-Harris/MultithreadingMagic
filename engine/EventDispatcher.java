package engine;

import java.util.concurrent.BlockingQueue;

public class EventDispatcher implements Runnable {
    private final BlockingQueue<Event> queue;
    private volatile boolean running = true;

    public EventDispatcher(BlockingQueue<Event> q) { this.queue = q; }

    @Override
    public void run() {
        while (running && !Thread.currentThread().isInterrupted()) {
            try {
                Event e = queue.take();
                System.out.println("[EventDispatcher] Handling: " + e.getName());
                e.handle();
            } catch (InterruptedException ex) {
                Thread.currentThread().interrupt();
                break;
            }
        }
        System.out.println("EventDispatcher exiting.");
    }

    public void stop() { running = false; }
}
