package elevator;

import java.util.PriorityQueue;

public class Elevator {

    private int current;
    private Direction direction;
    private PriorityQueue<Person> up;
    private PriorityQueue<Person> down;

    public Elevator(int current) {
        this.current = current;
        this.direction = Direction.IDLE;
        up = new PriorityQueue<>((a, b) -> a.getDestination() - b.getDestination());
        down = new PriorityQueue<>((a, b) -> b.getDestination() - a.getDestination());
    }

    public int getCurrent() { return current; }

    public void upPerson(Person person) {
        if (person.getLocation() == Location.OUTSIDE) {
            up.offer(new Person(person.getCurrent(), person.getCurrent(), Direction.UP, Location.OUTSIDE));
        }
        up.offer(person);
    }

    public void downPerson(Person person) {
        if (person.getLocation() == Location.OUTSIDE) {
            down.offer(new Person(person.getCurrent(), person.getCurrent(), Direction.DOWN, Location.OUTSIDE));
        }
        down.offer(person);
    }

    public void run() {
        if (this.direction == Direction.UP || this.direction == Direction.IDLE) {
            processUpRequests();
            processDownRequests();
        } else {
            processDownRequests();
            processUpRequests();
        }
        this.direction = Direction.IDLE;
    }

    private void processUpRequests() {
        while (!up.isEmpty()) {
            Person person = up.poll();
            this.current = person.getDestination();
        }
        if (!down.isEmpty()) {
            this.direction = Direction.DOWN;
        }
    }

    private void processDownRequests() {
        while (!down.isEmpty()) {
            Person person = down.poll();
            this.current = person.getDestination();
        }
        if (!up.isEmpty()) {
            this.direction = Direction.UP;
        }
    }

    public static void main(String[] args) {

        // Test 1: 1 elevator, 1 person going UP, 2 floors
        Elevator e = new Elevator(1);
        Timer t = new Timer();

        e.upPerson(new Person(1, 2, Direction.UP, Location.OUTSIDE));

        t.start();
        e.run();
        t.stop();
        System.out.println("Test 1: " + t.getTotal() + " nanoseconds\n");

        // Test 2: 1 elevator, 1 person going DOWN, 2 floors
        e = new Elevator(2);
        t = new Timer();

        e.downPerson(new Person(2, 1, Direction.DOWN, Location.OUTSIDE));

        t.start();
        e.run();
        t.stop();
        System.out.println("Test 2: " + t.getTotal() + " nanoseconds\n");

        // Test 3: 1 elevator, 6 people all going UP, 5 floors
        e = new Elevator(1);
        t = new Timer();

        e.upPerson(new Person(4, 5, Direction.UP, Location.OUTSIDE));
        e.upPerson(new Person(1, 2, Direction.UP, Location.INSIDE));
        e.upPerson(new Person(1, 5, Direction.UP, Location.INSIDE));
        e.upPerson(new Person(2, 3, Direction.UP, Location.OUTSIDE));
        e.upPerson(new Person(2, 4, Direction.UP, Location.OUTSIDE));
        e.upPerson(new Person(3, 5, Direction.UP, Location.INSIDE));

        t.start();
        e.run();
        t.stop();
        System.out.println("Test 3: " + t.getTotal() + " nanoseconds\n");

        // Test 4: 1 elevator, 6 people all going DOWN, 5 floors
        e = new Elevator(5);
        t = new Timer();

        e.downPerson(new Person(2, 1, Direction.DOWN, Location.OUTSIDE));
        e.downPerson(new Person(5, 1, Direction.DOWN, Location.INSIDE));
        e.downPerson(new Person(5, 1, Direction.DOWN, Location.INSIDE));
        e.downPerson(new Person(3, 2, Direction.DOWN, Location.OUTSIDE));
        e.downPerson(new Person(4, 2, Direction.DOWN, Location.OUTSIDE));
        e.downPerson(new Person(5, 3, Direction.DOWN, Location.OUTSIDE));

        t.start();
        e.run();
        t.stop();
        System.out.println("Test 4: " + t.getTotal() + " nanoseconds\n");

        // Test 5: 1 elevator, 6 people, both directions, 10 floors
        Elevator e1 = new Elevator(1);
        Timer t1 = new Timer();

        e1.upPerson(new Person(1, 5, Direction.UP, Location.INSIDE));
        e1.upPerson(new Person(5, 10, Direction.UP, Location.OUTSIDE));
        e1.upPerson(new Person(7, 8, Direction.UP, Location.OUTSIDE));
        e1.downPerson(new Person(10, 1, Direction.DOWN, Location.INSIDE));
        e1.downPerson(new Person(1, 2, Direction.DOWN, Location.INSIDE));
        e1.downPerson(new Person(4, 1, Direction.DOWN, Location.OUTSIDE));

        t1.start();
        e1.run();
        t1.stop();
        System.out.println("Test 5: " + t1.getTotal() + " nanoseconds\n");

        // Test 6: 2 elevators, split directions, 10 floors
        e1 = new Elevator(1);
        Elevator e2 = new Elevator(10);
        t1 = new Timer();
        Timer t2 = new Timer();

        e1.upPerson(new Person(1, 5, Direction.UP, Location.INSIDE));
        e1.upPerson(new Person(5, 10, Direction.UP, Location.OUTSIDE));
        e1.upPerson(new Person(7, 8, Direction.UP, Location.OUTSIDE));
        e2.downPerson(new Person(10, 1, Direction.DOWN, Location.INSIDE));
        e2.downPerson(new Person(1, 2, Direction.DOWN, Location.INSIDE));
        e2.downPerson(new Person(4, 1, Direction.DOWN, Location.OUTSIDE));

        t1.start();
        e1.run();
        t1.stop();
        t2.start();
        e2.run();
        t2.stop();
        System.out.println("Test 6: " + Math.max(t1.getTotal(), t2.getTotal()) + " nanoseconds\n");

        // Test 7: 2 elevators, both serve any direction, 10 floors
        e1 = new Elevator(1);
        e2 = new Elevator(10);
        t1 = new Timer();
        t2 = new Timer();

        e1.upPerson(new Person(1, 5, Direction.UP, Location.INSIDE));
        e1.upPerson(new Person(7, 8, Direction.UP, Location.OUTSIDE));
        e1.downPerson(new Person(1, 2, Direction.DOWN, Location.INSIDE));
        e2.upPerson(new Person(5, 10, Direction.UP, Location.OUTSIDE));
        e2.downPerson(new Person(10, 1, Direction.DOWN, Location.INSIDE));
        e2.downPerson(new Person(4, 1, Direction.DOWN, Location.OUTSIDE));

        t1.start();
        e1.run();
        t1.stop();
        t2.start();
        e2.run();
        t2.stop();
        System.out.println("Test 7: " + Math.max(t1.getTotal(), t2.getTotal()) + " nanoseconds\n");
    }

}