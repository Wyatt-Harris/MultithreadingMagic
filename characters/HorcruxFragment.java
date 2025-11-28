package characters;

import java.util.concurrent.locks.ReentrantLock;

public class HorcruxFragment extends Item {
    private final ReentrantLock lock = new ReentrantLock();

    public HorcruxFragment(String name) { super(name); }

    public boolean examine(Character c) {
        if (lock.tryLock()) {
            try {
                System.out.println(c.getName() + " acquired lock on " + name + " and studies it.");
                try { Thread.sleep(600); } catch (InterruptedException e) { Thread.currentThread().interrupt(); }
                return true;
            } finally {
                lock.unlock();
            }
        } else {
            System.out.println(c.getName() + " tried to examine " + name + " but it's busy.");
            return false;
        }
    }
}
