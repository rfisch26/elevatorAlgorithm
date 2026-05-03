package elevator;

public class Person {

    private int current;
    private int destination;
    private Direction direction;
    private Location location;

    public Person(int current, int destination, Direction direction, Location location) {
        this.current = current;
        this.destination = destination;
        this.direction = direction;
        this.location = location;
    }

    public Person(Person p) {
        this.current = p.current;
        this.destination = p.destination;
        this.direction = p.direction;
        this.location = p.location;
    }

    public int getCurrent()          { return current; }
    public int getDestination()      { return destination; }
    public Direction getDirection()  { return direction; }
    public Location getLocation()    { return location; }

}