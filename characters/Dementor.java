package characters;

public class Dementor {
    private final String name;
    public Dementor(String name) {
        this.name = name;
    }
    public String getName() { return name; }

    public void attack(Character target, Harry harry) {
        System.out.println(name + " targets " + target.getName() + " for the Kiss of Death!");
        boolean protectedByHarry = harry.castExpectoPatronum(target);
        if (!protectedByHarry) {
            System.out.println(name + " performs the Kiss of Death on " + target.getName() + "!");
            target.takeDamage(75);
        } else {
            System.out.println(target.getName() + " is saved from the Dementor's attack!");
        }
    }
}
