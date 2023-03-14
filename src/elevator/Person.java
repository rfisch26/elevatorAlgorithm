package elevator;

public class Person {

    int current;
    int destination;
    Direction direction;
    Location location;

    public Person(int current, int destination, Direction direction, Location location) {
        this.current = current;
        this.destination = destination;
        this.direction = direction;
        this.location = location;
    } //end Request 
    
    public Person(Person p) {
    	this.current = p.current;
    	this.destination = p.destination;
    	this.direction = p.direction;
    	this.location = p.location;
    } //end Request
    
} //end class
