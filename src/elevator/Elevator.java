
package elevator;
import java.util.PriorityQueue;

public class Elevator {

    int current; 
    Direction direction;
    PriorityQueue<Person> up;
    PriorityQueue<Person> down;

    public Elevator(int current) {
        this.current = current;
        this.direction = Direction.IDLE;
        //min heap 
        up = new PriorityQueue<>((a, b) -> a.destination - b.destination);
        //max heap 
        down =  new PriorityQueue<>((a, b) -> b.destination - a.destination);
    } //end Elevator 

    public void upPerson(Person upPerson) {
    	//when person is not already in elevator 
        if (upPerson.location == Location.OUTSIDE) {
            up.offer(new Person(upPerson.current, upPerson.current, Direction.UP, Location.OUTSIDE));
            //System.out.println("added outside person currently on floor " + upPerson.current + ".");
        } //end if 
        //go to destination 
        up.offer(upPerson);
        //System.out.println("added person going to floor " + upPerson.destination + ".");
    } //end upPerson

    public void downPerson(Person downPerson) {
    	//when person is not already in elevator 
        if (downPerson.location == Location.OUTSIDE) {
            down.offer(new Person(downPerson.current, downPerson.current, Direction.DOWN, Location.OUTSIDE));
            //System.out.println("added outside person currently on floor " + downPerson.current + ".");
        } //end if 
        //go to destination 
        down.offer(downPerson);
        //System.out.println("added person going to floor " + downPerson.destination + ".");
    } //end downPerson

    public void run() {
    	//runs processes in queues when they're not empty 
        while (!up.isEmpty() || !down.isEmpty()) {
            processPersons();
        } //end while 
        //System.out.println("Finished all Requests.\n");
        this.direction = Direction.IDLE;
    } //end run 

    private void processPersons() {
    	//complete requests all in one direction first & then the other 
        if (this.direction == Direction.UP || this.direction == Direction.IDLE) {
            processUpPerson();
            processDownPerson();
        } else {
            processDownPerson();
            processUpPerson();
        } //end if else 
    } //processPersons

    private void processUpPerson() {
    	//drops off first person in queue
        while (!up.isEmpty()) {
            Person upPerson = up.poll();
            this.current = upPerson.destination;
            //System.out.println("Going Up: Elevator stopped at floor " + this.current + ".");
        } //end while
        //if there are down requests in the queue, change direction, otherwise idle 
        if (!down.isEmpty()) {
            this.direction = Direction.DOWN;
        } else {
            this.direction = Direction.IDLE;
        } //end if else 
    } //end processUpPerson

    private void processDownPerson() {
    	//drops off first person in queue
        while (!down.isEmpty()) {
            Person downPerson = down.poll();
            this.current = downPerson.destination;
            //System.out.println("Going Down: Elevator stopped at floor " + this.current + ".");
        } //end while
        //if there are up requests in the queue, change direction, otherwise idle 
        if (!up.isEmpty()) {
            this.direction = Direction.UP;
        } else {
            this.direction = Direction.IDLE;
        } //end else if
    } //end processDownPerson

    public static void main(String[] args) { 
    	//********************************************************************************* simplest case up
    	//1 elevator, 1 person, 2 floors
    	Elevator e = new Elevator(1);
    	Timer t = new Timer();
    	
    	Person person1 = new Person(1, 2, Direction.UP, Location.OUTSIDE);
    	
    	e.upPerson(person1);
    	
    	t.start();
    	e.run();
    	t.stop();
    	System.out.println(t.getTotal() + " nanoseconds total.\n");
    	
    	//********************************************************************************* simplest case down
    	//1 elevator, 1 person, 2 floors
    	e = new Elevator(2);
    	t = new Timer();
    	
    	Person person2 = new Person(2, 1, Direction.DOWN, Location.OUTSIDE);
    	
    	e.downPerson(person2);
    	
    	t.start();
    	e.run();
    	t.stop();
    	System.out.println(t.getTotal() + " nanoseconds total.\n");
    	
    	//********************************************************************************* case all going up 
    	// 1 elevator, 6 people, 5 floors 
        e = new Elevator(1);
        t = new Timer(); 
        
        Person p1 = new Person(4, 5, Direction.UP, Location.OUTSIDE);
        Person p2 = new Person(e.current, 1, Direction.UP, Location.INSIDE);
        Person p3 = new Person(e.current, 5, Direction.UP, Location.INSIDE);
        Person p4 = new Person(2, 3, Direction.UP, Location.OUTSIDE);
        Person p5 = new Person(2, 4, Direction.UP, Location.OUTSIDE);
        Person p6 = new Person(3, 5, Direction.UP, Location.INSIDE);
        
        e.upPerson(p1);
        e.upPerson(p2);
        e.upPerson(p3);
        e.upPerson(p4);
        e.upPerson(p5);
        e.upPerson(p6);
        
        t.start();
    	e.run();
    	t.stop();
    	System.out.println(t.getTotal() + " nanoseconds total.\n");
    	
        //********************************************************************************* case all going down   
    	// 1 elevator, 6 people, 5 floors 
        e = new Elevator(5);
        t = new Timer(); 
        
        Person per1 = new Person(2, 1, Direction.DOWN, Location.OUTSIDE);
        Person per2 = new Person(e.current, 1, Direction.DOWN, Location.INSIDE);
        Person per3 = new Person(e.current, 5, Direction.DOWN, Location.INSIDE);
        Person per4 = new Person(3, 2, Direction.DOWN, Location.OUTSIDE);
        Person per5 = new Person(4, 2, Direction.DOWN, Location.OUTSIDE);
        Person per6 = new Person(5, 3, Direction.DOWN, Location.OUTSIDE);
        
        e.downPerson(per1);
        e.downPerson(per2);
        e.downPerson(per3);
        e.downPerson(per4);
        e.downPerson(per5);
        e.downPerson(per6);
        
        t.start();
    	e.run();
    	t.stop();
    	System.out.println(t.getTotal() + " nanoseconds total.\n");
    	
    	//********************************************************************************* case with multiple directions
    	// 1 elevator, 6 people, 10 floors 
        Elevator e1 = new Elevator(1);
        Timer t1 = new Timer();

        //making people 
        Person up1 = new Person(e1.current, 5, Direction.UP, Location.INSIDE);
        Person up2 = new Person(5, 10, Direction.UP, Location.OUTSIDE);
        Person up3 = new Person(7, 8, Direction.UP, Location.OUTSIDE);
        Person down1 = new Person(10, 1, Direction.DOWN, Location.INSIDE);
        Person down2 = new Person(e1.current, 2, Direction.DOWN, Location.INSIDE);
        Person down3 = new Person(4, 0, Direction.DOWN, Location.OUTSIDE);
        
        e1.upPerson(up1);
        e1.upPerson(up2);
        e1.upPerson(up3);
        e1.downPerson(down1);
        e1.downPerson(down2);
        e1.downPerson(down3);
        
        t1.start();
        e1.run();
        t1.stop();
        System.out.println(t1.getTotal() + " nanoseconds total.\n");
        
        //********************************************************************************* case comparing last test with 2 elevators: one going up & one going down  
        //2 elevators, 6 people, 10 floors  
        e1 = new Elevator(1);
        Elevator e2 = new Elevator(10);
        t1 = new Timer();
        Timer t2 = new Timer();
        
        Person u1 = new Person(e1.current, 5, Direction.UP, Location.INSIDE);
        Person u2 = new Person(5, 10, Direction.UP, Location.OUTSIDE);
        Person u3 = new Person(7, 8, Direction.UP, Location.OUTSIDE);
        Person d1 = new Person(10, 1, Direction.DOWN, Location.INSIDE);
        Person d2 = new Person(e1.current, 2, Direction.DOWN, Location.INSIDE);
        Person d3 = new Person(4, 0, Direction.DOWN, Location.OUTSIDE);
        
        e1.upPerson(u1);
        e1.upPerson(u2);
        e1.upPerson(u3);
        e2.downPerson(d1);
        e2.downPerson(d2);
        e2.downPerson(d3);
   
        t1.start();
        e1.run();
        t1.stop();
        t2.start();
        e2.run();
        t2.stop();
        System.out.println((t1.getTotal() > t2.getTotal()? t1.getTotal() : t2.getTotal()) + " nanoseconds total.\n");
        
        //********************************************************************************* case comparing last test with 2 elevators
        //2 elevators, 5 people, 10 floors  
        e1 = new Elevator(1);
        e2 = new Elevator(10);
        t1 = new Timer();
        t2 = new Timer();
        
        Person upP1 = new Person(e1.current, 5, Direction.UP, Location.INSIDE);
        Person upP2 = new Person(5, 10, Direction.UP, Location.OUTSIDE);
        Person upP3 = new Person(7, 8, Direction.UP, Location.OUTSIDE);
        Person downP1 = new Person(10, 1, Direction.DOWN, Location.INSIDE);
        Person downP2 = new Person(e1.current, 2, Direction.DOWN, Location.INSIDE);
        Person downP3 = new Person(4, 0, Direction.DOWN, Location.OUTSIDE);
        
        e1.upPerson(upP1);
        e1.upPerson(upP3);
        e1.upPerson(downP2);
        e2.downPerson(upP2);
        e2.downPerson(downP1);
        e2.downPerson(downP3);
        
        t1.start();
        e1.run();
        t1.stop();
        t2.start();
        e2.run();
        t2.stop();
        System.out.println((t1.getTotal() > t2.getTotal()? t1.getTotal() : t2.getTotal()) + " nanoseconds total.\n");
        
    } //end main

} //end class