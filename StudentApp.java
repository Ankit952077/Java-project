import java.util.*;   // Collection classes aur Scanner ke liye

// Student class – student ki basic details store karne ke liye
class Student {
    int id;       
   String name;  
    int marks;    
    // constructor to initialize student data
    Student(int id, String name, int marks) {
        this.id = id;
        this.name = name;
        this.marks = marks;
    }

    // student details display karne ke liye
    void display() {
        System.out.println(id + " | " + name + " | " + marks);
    }
}

// Main application class
public class StudentApp {

    // student list store karne ke liye
    static List<Student> students = new ArrayList<>();

    // input lene ke liye scanner
    static Scanner sc = new Scanner(System.in);

    // program execution yahin se start hota hai
    public static void main(String[] args) {

        // menu driven program
        while (true) {
            System.out.println("\n--- STUDENT MANAGEMENT ---");
            System.out.println("1. Add Student");
            System.out.println("2. View Students");
            System.out.println("3. Search Student");
            System.out.println("4. Update Marks");
            System.out.println("5. Delete Student");
            System.out.println("6. Exit");
            System.out.print("Choose option: ");

            int choice = sc.nextInt();   // user choice

            // switch expression use kiya gaya hai (Java 14+)
            switch (choice) {
                case 1 -> addStudent();
                case 2 -> viewStudents();
                case 3 -> searchStudent();
                case 4 -> updateMarks();
                case 5 -> deleteStudent();
                case 6 -> {
                    System.out.println("Bye 👋");
                    return;   // program terminate
                }
                default -> System.out.println("Invalid option");
            }
        }
    }

    // new student add karne ka method
    static void addStudent() {
        System.out.print("Enter ID: ");
        int id = sc.nextInt();

        // duplicate id check
        for (Student s : students) {
            if (s.id == id) {
                System.out.println("Student already exists!");
                return;
            }
        }

        sc.nextLine(); // buffer clear karne ke liye
        System.out.print("Enter Name: ");
        String name = sc.nextLine();

        System.out.print("Enter Marks: ");
        int marks = sc.nextInt();

        // student object create karke list me add
        students.add(new Student(id, name, marks));
        System.out.println("Student added successfully ✅");
    }

    // sabhi students ko display karne ke liye
    static void viewStudents() {
        if (students.isEmpty()) {
            System.out.println("No students found");
            return;
        }

        System.out.println("\nID | Name | Marks");
        for (Student s : students) {
            s.display();
        }
    }

    // student ko id ke basis par search karne ke liye
    static void searchStudent() {
        System.out.print("Enter ID to search: ");
        int id = sc.nextInt();

        for (Student s : students) {
            if (s.id == id) {
                System.out.println("Student found:");
                s.display();
                return;
            }
        }
        System.out.println("Student not found ❌");
    }

    // student ke marks update karne ke liye
    static void updateMarks() {
        System.out.print("Enter ID: ");
        int id = sc.nextInt();

        for (Student s : students) {
            if (s.id == id) {
                System.out.print("Enter new marks: ");
                s.marks = sc.nextInt();
                System.out.println("Marks updated ✅");
                return;
            }
        }
        System.out.println("Student not found ❌");
    }

    // student delete karne ke liye
    static void deleteStudent() {
        System.out.print("Enter ID: ");
        int id = sc.nextInt();

        // iterator use kiya gaya hai to avoid ConcurrentModificationException
        Iterator<Student> it = students.iterator();
        while (it.hasNext()) {
            if (it.next().id == id) {
                it.remove();
                System.out.println("Student deleted ✅");
                return;
            }
        }
        System.out.println("Student not found ❌");
    }
}
