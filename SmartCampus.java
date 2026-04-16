import java.util.*;

// Custom Exception
class InvalidFeeException extends Exception {
    public InvalidFeeException(String msg) {
        super(msg);
    }
}

// Student Class
class Student {
    int id;
    String name;
    String email;

    Student(int id, String name, String email) {
        this.id = id;
        this.name = name;
        this.email = email;
    }

    public String toString() {
        return id + " - " + name + " - " + email;
    }
}

// Course Class
class Course {
    int courseId;
    String courseName;
    double fee;

    Course(int courseId, String courseName, double fee) throws InvalidFeeException {
        if (fee < 0) {
            throw new InvalidFeeException("Fee cannot be negative!");
        }
        this.courseId = courseId;
        this.courseName = courseName;
        this.fee = fee;
    }

    public String toString() {
        return courseId + " - " + courseName + " - " + fee;
    }
}

// Thread Class
class EnrollmentThread extends Thread {
    public void run() {
        System.out.println("Processing enrollment...");
        try {
            Thread.sleep(2000);
        } catch (Exception e) {}
        System.out.println("Enrollment completed!");
    }
}

public class SmartCampus {

    static HashMap<Integer, Student> students = new HashMap<>();
    static HashMap<Integer, Course> courses = new HashMap<>();
    static HashMap<Integer, ArrayList<Course>> enrollments = new HashMap<>();

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        int choice;

        do {
            System.out.println("\n===== Smart Campus Menu =====");
            System.out.println("1. Add Student");
            System.out.println("2. Add Course");
            System.out.println("3. Enroll Student");
            System.out.println("4. View Students");
            System.out.println("5. View Enrollments");
            System.out.println("6. Process Enrollment (Thread)");
            System.out.println("7. Exit");
            System.out.print("Enter choice: ");

            choice = sc.nextInt();

            try {
                switch (choice) {

                    case 1:
                        System.out.print("Enter Student ID: ");
                        int sid = sc.nextInt();
                        sc.nextLine();
                        System.out.print("Enter Name: ");
                        String name = sc.nextLine();
                        System.out.print("Enter Email: ");
                        String email = sc.nextLine();

                        students.put(sid, new Student(sid, name, email));
                        System.out.println("Student Added!");
                        break;

                    case 2:
                        System.out.print("Enter Course ID: ");
                        int cid = sc.nextInt();
                        sc.nextLine();
                        System.out.print("Enter Course Name: ");
                        String cname = sc.nextLine();
                        System.out.print("Enter Fee: ");
                        double fee = sc.nextDouble();

                        courses.put(cid, new Course(cid, cname, fee));
                        System.out.println("Course Added!");
                        break;

                    case 3:
                        System.out.print("Enter Student ID: ");
                        int esid = sc.nextInt();
                        System.out.print("Enter Course ID: ");
                        int ecid = sc.nextInt();

                        if (!students.containsKey(esid) || !courses.containsKey(ecid)) {
                            System.out.println("Invalid Student or Course ID!");
                            break;
                        }

                        enrollments.putIfAbsent(esid, new ArrayList<>());
                        enrollments.get(esid).add(courses.get(ecid));

                        System.out.println("Enrollment Successful!");
                        break;

                    case 4:
                        System.out.println("\n--- Students ---");
                        for (Student s : students.values()) {
                            System.out.println(s);
                        }
                        break;

                    case 5:
                        System.out.println("\n--- Enrollments ---");
                        for (int id : enrollments.keySet()) {
                            System.out.println("Student ID: " + id);
                            for (Course c : enrollments.get(id)) {
                                System.out.println("   " + c);
                            }
                        }
                        break;

                    case 6:
                        EnrollmentThread t = new EnrollmentThread();
                        t.start();
                        break;

                    case 7:
                        System.out.println("Exiting...");
                        break;

                    default:
                        System.out.println("Invalid choice!");
                }

            } catch (InvalidFeeException e) {
                System.out.println("Error: " + e.getMessage());
            } catch (InputMismatchException e) {
                System.out.println("Invalid input!");
                sc.nextLine(); // clear buffer
            }

        } while (choice != 7);

        sc.close();
    }
}