import java.util.List;
import java.util.Scanner;

/**
 * StudentResultManagementSystem.java
 * Main class providing a menu-driven interface for CRUD operations.
 */
public class StudentResultManagementSystem {
    private static DatabaseManager dbManager = new DatabaseManager();
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        System.out.println("--- Student Result Management System ---");
        int choice;
        do {
            printMenu();
            if (scanner.hasNextInt()) {
                choice = scanner.nextInt();
                scanner.nextLine();
                handleChoice(choice);
            } else {
                System.out.println("\n*** Invalid input. Please enter a number. ***\n");
                scanner.nextLine();
                choice = -1;
            }
        } while (choice != 0);
        
        System.out.println("Exiting application. Goodbye!");
    }

    private static void printMenu() {
        System.out.println("\n-------------------------------------");
        System.out.println("1. Add New Student");
        System.out.println("2. View All Students");
        System.out.println("3. Update Student Details");
        System.out.println("4. Delete Student (and all results)");
        System.out.println("5. Add Student Result");
        System.out.println("6. View Student Results by ID");
        System.out.println("0. Exit");
        System.out.println("-------------------------------------");
        System.out.print("Enter your choice: ");
    }

    private static void handleChoice(int choice) {
        switch (choice) {
            case 1: addStudent(); break;
            case 2: viewAllStudents(); break;
            case 3: updateStudent(); break;
            case 4: deleteStudent(); break;
            case 5: addResult(); break;
            case 6: viewResults(); break;
            case 0: break; 
            default: System.out.println("\n*** Invalid choice. Please try again. ***\n");
        }
    }

    private static void addStudent() {
        System.out.print("Enter student name: ");
        String name = scanner.nextLine();
        System.out.print("Enter student age: ");
        int age = scanner.nextInt();
        scanner.nextLine(); 

        Student student = new Student(name, age);
        if (dbManager.addStudent(student)) {
            System.out.println("\n[SUCCESS] Student added successfully with ID: " + student.getStudentId());
        } else {
            System.out.println("\n[FAILURE] Failed to add student.");
        }
    }

    private static void viewAllStudents() {
        List<Student> students = dbManager.getAllStudents();
        if (students.isEmpty()) {
            System.out.println("\n[INFO] No students found in the database.");
            return;
        }
        System.out.println("\n--- LIST OF ALL STUDENTS (" + students.size() + ") ---");
        for (Student s : students) {
            System.out.println(s);
        }
    }

    private static void updateStudent() {
        System.out.print("Enter Student ID to update: ");
        int id = scanner.nextInt();
        scanner.nextLine(); 

        Student student = dbManager.getStudentById(id);
        if (student == null) {
            System.out.println("\n[ERROR] Student with ID " + id + " not found.");
            return;
        }

        System.out.println("Current details: " + student);
        System.out.print("Enter new name (or press Enter to keep '" + student.getName() + "'): ");
        String newName = scanner.nextLine();
        if (!newName.isEmpty()) {
            student.setName(newName);
        }

        System.out.print("Enter new age (or 0 to keep " + student.getAge() + "): ");
        if (scanner.hasNextInt()) {
            int newAge = scanner.nextInt();
            if (newAge != 0) {
                student.setAge(newAge);
            }
        }
        scanner.nextLine(); 

        if (dbManager.updateStudent(student)) {
            System.out.println("\n[SUCCESS] Student ID " + id + " updated successfully.");
        } else {
            System.out.println("\n[FAILURE] Failed to update student.");
        }
    }

    private static void deleteStudent() {
        System.out.print("Enter Student ID to delete: ");
        if (!scanner.hasNextInt()) {
            System.out.println("\n[ERROR] Invalid ID format.");
            scanner.nextLine();
            return;
        }
        int id = scanner.nextInt();
        scanner.nextLine(); 

        if (dbManager.deleteStudent(id)) {
            System.out.println("\n[SUCCESS] Student ID " + id + " and all associated results deleted successfully.");
        } else {
            System.out.println("\n[FAILURE] Failed to delete student or student not found.");
        }
    }

    private static void addResult() {
        System.out.print("Enter Student ID to add result for: ");
        int studentId = scanner.nextInt();
        scanner.nextLine(); 
        
        if (dbManager.getStudentById(studentId) == null) {
            System.out.println("\n[ERROR] Student with ID " + studentId + " not found. Cannot add result.");
            return;
        }

        System.out.print("Enter subject name: ");
        String subject = scanner.nextLine();
        System.out.print("Enter marks: ");
        double marks = scanner.nextDouble();
        scanner.nextLine(); 

        Result result = new Result(studentId, subject, marks);
        if (dbManager.addResult(result)) {
            System.out.println("\n[SUCCESS] Result added successfully (Result ID: " + result.getResultId() + ")");
        } else {
            System.out.println("\n[FAILURE] Failed to add result.");
        }
    }

    private static void viewResults() {
        System.out.print("Enter Student ID to view results: ");
        int studentId = scanner.nextInt();
        scanner.nextLine(); 
        
        List<Result> results = dbManager.getResultsByStudentId(studentId);
        
        if (dbManager.getStudentById(studentId) == null) {
             System.out.println("\n[ERROR] Student with ID " + studentId + " not found.");
             return;
        }

        if (results.isEmpty()) {
            System.out.println("\n[INFO] No results found for Student ID: " + studentId);
            return;
        }
        
        System.out.println("\n--- RESULTS FOR STUDENT ID " + studentId + " ---");
        for (Result r : results) {
            System.out.println(r);
        }
    }
}
