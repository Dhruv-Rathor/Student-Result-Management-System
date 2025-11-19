/**
 * Result.java
 * Entity class representing a student's result for a specific subject.
 */
public class Result {
    private int resultId;
    private int studentId;
    private String subject;
    private double marks;

    public Result(int studentId, String subject, double marks) {
        this.studentId = studentId;
        this.subject = subject;
        this.marks = marks;
    }

    public Result(int resultId, int studentId, String subject, double marks) {
        this.resultId = resultId;
        this.studentId = studentId;
        this.subject = subject;
        this.marks = marks;
    }

    public int getResultId() { return resultId; }
    public void setResultId(int resultId) { this.resultId = resultId; }

    public int getStudentId() { return studentId; }
    public void setStudentId(int studentId) { this.studentId = studentId; }

    public String getSubject() { return subject; }
    public void setSubject(String subject) { this.subject = subject; }

    public double getMarks() { return marks; }
    public void setMarks(double marks) { this.marks = marks; }

    @Override
    public String toString() {
        return "[Result ID: " + resultId + ", Student ID: " + studentId + 
               ", Subject: " + subject + ", Marks: " + marks + "]";
    }
}
