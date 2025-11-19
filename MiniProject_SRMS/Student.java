/**
 * Student.java
 * Entity class representing a Student record.
 * This class applies basic OOP principles.
 */
public class Student {
    private int studentId;
    private String name;
    private int age;

    public Student(String name, int age) {
        this.name = name;
        this.age = age;
    }

    public Student(int studentId, String name, int age) {
        this.studentId = studentId;
        this.name = name;
        this.age = age;
    }

    public int getStudentId() { return studentId; }
    public void setStudentId(int studentId) { this.studentId = studentId; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public int getAge() { return age; }
    public void setAge(int age) { this.age = age; }

    @Override
    public String toString() {
        return "ID: " + studentId + ", Name: " + name + ", Age: " + age;
    }
}
