import java.io.*;
import java.util.*;

// Abstract base class Person
abstract class Person {
    private String name;
    private int age;
    private String contactNumber;

    // Default constructor
    public Person() {
        this.name = "Unknown";
        this.age = 0;
        this.contactNumber = "Unknown";
    }

    // Parameterized constructor
    public Person(String name, int age, String contactNumber) {
        this.name = name;
        this.age = age;
        this.contactNumber = contactNumber;
    }

    // Getters and Setters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getContactNumber() {
        return contactNumber;
    }

    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }
}

// Employee class extending Person
class Employee extends Person {
    private String employeeID;
    private String role;

    // Default constructor
    public Employee() {
        super();
        this.employeeID = "Unknown";
        this.role = "Unknown";
    }

    // Parameterized constructor
    public Employee(String name, int age, String contactNumber, String employeeID, String role) {
        super(name, age, contactNumber);
        this.employeeID = employeeID;
        this.role = role;
    }

    // Getters and Setters
    public String getEmployeeID() {
        return employeeID;
    }

    public void setEmployeeID(String employeeID) {
        this.employeeID = employeeID;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}

// Visitor class extending Person
class Visitor extends Person {
    private String ticketID;
    private String visitDate;

    // Default constructor
    public Visitor() {
        super();
        this.ticketID = "Unknown";
        this.visitDate = "Unknown";
    }

    // Parameterized constructor
    public Visitor(String name, int age, String contactNumber, String ticketID, String visitDate) {
        super(name, age, contactNumber);
        this.ticketID = ticketID;
        this.visitDate = visitDate;
    }

    // Getters and Setters
    public String getTicketID() {
        return ticketID;
    }

    public void setTicketID(String ticketID) {
        this.ticketID = ticketID;
    }

    public String getVisitDate() {
        return visitDate;
    }

    public void setVisitDate(String visitDate) {
        this.visitDate = visitDate;
    }
}

// RideInterface definition
interface RideInterface {
    void addVisitorToQueue(Visitor visitor);
    void removeVisitorFromQueue(Visitor visitor);
    void printQueue();
    void runOneCycle();
    void addVisitorToHistory(Visitor visitor);
    boolean checkVisitorFromHistory(Visitor visitor);
    int numberOfVisitors();
    void printRideHistory();
}

// Ride class implementing RideInterface
class Ride implements RideInterface {
    private final String rideName;
    private final int capacity;
    private final Employee rideOperator;
    private final Queue<Visitor> queue;
    final LinkedList<Visitor> history;
    private final int maxRider;
    private int numOfCycles;

    // Default constructor
    public Ride() {
        this.rideName = "Unknown";
        this.capacity = 0;
        this.rideOperator = null;
        this.queue = new LinkedList<>();
        this.history = new LinkedList<>();
        this.maxRider = 1;
        this.numOfCycles = 0;
    }

    // Parameterized constructor
    public Ride(String rideName, int capacity, Employee rideOperator, int maxRider) {
        this.rideName = rideName;
        this.capacity = capacity;
        this.rideOperator = rideOperator;
        this.queue = new LinkedList<>();
        this.history = new LinkedList<>();
        this.maxRider = maxRider;
        this.numOfCycles = 0;
    }

    @Override
    public void addVisitorToQueue(Visitor visitor) {
        queue.add(visitor);
        System.out.println("Visitor " + visitor.getName() + " added to the queue.");
    }

    @Override
    public void removeVisitorFromQueue(Visitor visitor) {
        if (queue.remove(visitor)) {
            System.out.println("Visitor " + visitor.getName() + " removed from the queue.");
        } else {
            System.out.println("Visitor " + visitor.getName() + " not found in the queue.");
        }
    }

    @Override
    public void printQueue() {
        System.out.println("Current Queue:");
        for (Visitor visitor : queue) {
            System.out.println(visitor.getName() + " - Ticket: " + visitor.getTicketID());
        }
    }

    @Override
    public void runOneCycle() {
        if (rideOperator == null) {
            System.out.println("Ride cannot run. No operator assigned.");
            return;
        }

        if (queue.isEmpty()) {
            System.out.println("No visitors in queue. Ride cannot run.");
            return;
        }

        System.out.println("Running one cycle for ride: " + rideName);
        int count = 0;
        while (!queue.isEmpty() && count < maxRider) {
            Visitor visitor = queue.poll();
            addVisitorToHistory(visitor);
            System.out.println("Visitor " + visitor.getName() + " has taken the ride.");
            count++;
        }
        numOfCycles++;
    }

    @Override
    public void addVisitorToHistory(Visitor visitor) {
        history.add(visitor);
        System.out.println("Visitor " + visitor.getName() + " added to ride history.");
    }

    @Override
    public boolean checkVisitorFromHistory(Visitor visitor) {
        return history.contains(visitor);
    }

    @Override
    public int numberOfVisitors() {
        return history.size();
    }

    @Override
    public void printRideHistory() {
        System.out.println("Ride History:");
        for (Visitor visitor : history) {
            System.out.println(visitor.getName() + " - Ticket: " + visitor.getTicketID());
        }
    }

    public void exportRideHistory(String filename) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename))) {
            for (Visitor visitor : history) {
                writer.write(visitor.getName() + "," + visitor.getAge() + "," + visitor.getContactNumber() + "," + visitor.getTicketID() + "," + visitor.getVisitDate());
                writer.newLine();
            }
            System.out.println("Ride history exported to " + filename);
        } catch (IOException e) {
            System.out.println("Error exporting history: " + e.getMessage());
        }
    }

    public void importRideHistory(String filename) {
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] details = line.split(",");
                Visitor visitor = new Visitor(details[0], Integer.parseInt(details[1]), details[2], details[3], details[4]);
                addVisitorToHistory(visitor);
            }
            System.out.println("Ride history imported from " + filename);
        } catch (IOException | NumberFormatException e) {
            System.out.println("Error importing history: " + e.getMessage());
        }
    }

    public void getHistory(VisitorComparator visitorComparator) {
    }

    public int getNumOfCycles() {
        return numOfCycles;
    }

    public int getCapacity() {
        return capacity;
    }
}

// Comparator for Visitors
class VisitorComparator implements Comparator<Visitor> {
    @Override
    public int compare(Visitor v1, Visitor v2) {
        int ageComparison = Integer.compare(v1.getAge(), v2.getAge());
        return ageComparison != 0 ? ageComparison : v1.getName().compareTo(v2.getName());
    }
}

// AssignmentTwo class
public class AssignmentTwo {
    public static void main(String[] args) {
        AssignmentTwo assignment = new AssignmentTwo();
        assignment.partThree();
        assignment.partFourA();
        assignment.partFourB();
        assignment.partFive();
        assignment.partSix();
        assignment.partSeven();
    }

    public void partThree() {
        // Create Ride Object
        Ride rollerCoaster = new Ride("Roller Coaster", 5, new Employee("John", 30, "1234567890", "E001", "Operator"), 3);

        // Create Visitors
        Visitor v1 = new Visitor("Jack", 25, "123456789", "T001", "2024-12-13");
        Visitor v2 = new Visitor("Sharon", 28, "987654321", "T002", "2024-12-13");
        Visitor v3 = new Visitor("Benny", 22, "654321789", "T003", "2024-12-13");
        Visitor v4 = new Visitor("Leo", 30, "321987654", "T004", "2024-12-13");
        Visitor v5 = new Visitor("Nehemia", 27, "456789123", "T005", "2024-12-13");

        // Add Visitors to Queue
        rollerCoaster.addVisitorToQueue(v1);
        rollerCoaster.addVisitorToQueue(v2);
        rollerCoaster.addVisitorToQueue(v3);
        rollerCoaster.addVisitorToQueue(v4);
        rollerCoaster.addVisitorToQueue(v5);

        // Print Queue
        System.out.println("Queue before removing a visitor:");
        rollerCoaster.printQueue();

        // Remove a Visitor
        rollerCoaster.removeVisitorFromQueue(v3);

        // Print Queue After Removal
        System.out.println("Queue after removing a visitor:");
        rollerCoaster.printQueue();
    }

    public void partFourA() {
        // Create Ride Object
        Ride ferrisWheel = new Ride("Ferris Wheel", 4, new Employee("Jane", 35, "987654321", "E002", "Operator"), 3);

        // Create Visitors
        Visitor v1 = new Visitor("Tom", 30, "456123789", "T003", "2024-12-13");
        Visitor v2 = new Visitor("Sherly", 25, "789456123", "T004", "2024-12-14");
        Visitor v3 = new Visitor("Ben", 28, "456987123", "T005", "2024-12-14");
        Visitor v4 = new Visitor("David", 26, "123789456", "T006", "2024-12-15");
        Visitor v5 = new Visitor("Emily", 22, "654789123", "T007", "2024-12-16");

        // Add Visitors to History
        ferrisWheel.addVisitorToHistory(v1);
        ferrisWheel.addVisitorToHistory(v2);
        ferrisWheel.addVisitorToHistory(v3);
        ferrisWheel.addVisitorToHistory(v4);
        ferrisWheel.addVisitorToHistory(v5);

        // Check if a Visitor is in the Collection
        System.out.println("Is Ben in the collection? " + ferrisWheel.checkVisitorFromHistory(v3));

        // Print the Number of Visitors in the Collection
        System.out.println("Number of visitors in the collection: " + ferrisWheel.numberOfVisitors());

        // Print All Visitors in the Collection
        ferrisWheel.printRideHistory();
    }

    public void partFourB() {
        // Create Ride Object
        Ride rollerCoaster = new Ride("Roller Coaster", 5, new Employee("John", 30, "1234567890", "E001", "Operator"), 3);

        // Create Visitors
        Visitor v1 = new Visitor("Alice", 25, "123456789", "T001", "2024-12-13");
        Visitor v2 = new Visitor("Bob", 28, "987654321", "T002", "2024-12-14");
        Visitor v3 = new Visitor("Charlie", 22, "654321789", "T003", "2024-12-15");
        Visitor v4 = new Visitor("Diana", 30, "321987654", "T004", "2024-12-16");
        Visitor v5 = new Visitor("Eve", 27, "456789123", "T005", "2024-12-17");

        // Add Visitors to History
        rollerCoaster.addVisitorToHistory(v1);
        rollerCoaster.addVisitorToHistory(v2);
        rollerCoaster.addVisitorToHistory(v3);
        rollerCoaster.addVisitorToHistory(v4);
        rollerCoaster.addVisitorToHistory(v5);

        // Print All Visitors Before Sorting
        System.out.println("Visitors before sorting:");
        rollerCoaster.printRideHistory();

        // Sort the Collection
        rollerCoaster.history.sort(new VisitorComparator());

        // Print All Visitors After Sorting
        System.out.println("Visitors after sorting:");
        rollerCoaster.printRideHistory();
    }

    public void partFive() {
        // Create Ride Object
        Ride ferrisWheel = new Ride("Ferris Wheel", 10, new Employee("Jane", 35, "987654321", "E002", "Operator"), 4);

        // Create Visitors
        Visitor v1 = new Visitor("Alice", 25, "123456789", "T001", "2024-12-13");
        Visitor v2 = new Visitor("Bob", 28, "987654321", "T002", "2024-12-14");
        Visitor v3 = new Visitor("Charlie", 22, "654321789", "T003", "2024-12-15");
        Visitor v4 = new Visitor("Diana", 30, "321987654", "T004", "2024-12-16");
        Visitor v5 = new Visitor("Eve", 27, "456789123", "T005", "2024-12-17");
        Visitor v6 = new Visitor("Frank", 24, "789123456", "T006", "2024-12-18");
        Visitor v7 = new Visitor("Grace", 29, "123987456", "T007", "2024-12-19");
        Visitor v8 = new Visitor("Henry", 26, "987321654", "T008", "2024-12-20");
        Visitor v9 = new Visitor("Isabella", 23, "654987321", "T009", "2024-12-21");
        Visitor v10 = new Visitor("Jack", 31, "321654987", "T010", "2024-12-22");

        // Add Visitors to Queue
        ferrisWheel.addVisitorToQueue(v1);
        ferrisWheel.addVisitorToQueue(v2);
        ferrisWheel.addVisitorToQueue(v3);
        ferrisWheel.addVisitorToQueue(v4);
        ferrisWheel.addVisitorToQueue(v5);
        ferrisWheel.addVisitorToQueue(v6);
        ferrisWheel.addVisitorToQueue(v7);
        ferrisWheel.addVisitorToQueue(v8);
        ferrisWheel.addVisitorToQueue(v9);
        ferrisWheel.addVisitorToQueue(v10);

        // Print Queue Before Running Cycle
        System.out.println("Queue before running one cycle:");
        ferrisWheel.printQueue();

        // Run One Cycle
        ferrisWheel.runOneCycle();

        // Print Queue After Running Cycle
        System.out.println("Queue after running one cycle:");
        ferrisWheel.printQueue();

        // Print Ride History
        System.out.println("Ride history after one cycle:");
        ferrisWheel.printRideHistory();
    }

    public void partSix() {
        // Create Ride Object
        Ride rollerCoaster = new Ride("Roller Coaster", 5, new Employee("John", 30, "1234567890", "E001", "Operator"), 3);

        // Create Visitors
        Visitor v1 = new Visitor("Alice", 25, "123456789", "T001", "2024-12-13");
        Visitor v2 = new Visitor("Bob", 28, "987654321", "T002", "2024-12-14");
        Visitor v3 = new Visitor("Charlie", 22, "654321789", "T003", "2024-12-15");
        Visitor v4 = new Visitor("Diana", 30, "321987654", "T004", "2024-12-16");
        Visitor v5 = new Visitor("Eve", 27, "456789123", "T005", "2024-12-17");

        // Add Visitors to History
        rollerCoaster.addVisitorToHistory(v1);
        rollerCoaster.addVisitorToHistory(v2);
        rollerCoaster.addVisitorToHistory(v3);
        rollerCoaster.addVisitorToHistory(v4);
        rollerCoaster.addVisitorToHistory(v5);

        // Export Visitors to File
        String filename = "ride_history.txt";
        rollerCoaster.exportRideHistory(filename);
    }

    public void partSeven() {
        Ride importedRide = new Ride("Imported Ride", 5, null, 3);
        String filename = "ride_history.txt";
        importedRide.importRideHistory(filename);
        System.out.println("Number of visitors in history: " + importedRide.numberOfVisitors());
        importedRide.printRideHistory();
    }
}
