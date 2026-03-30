// Package name - this file belongs to the service package
package service;

// We need ArrayList to store all students
import java.util.ArrayList;

// We need Student class from model package
import model.Student;

// StudentService class - handles all logic related to students
// like registering, logging in, and getting student list
public class StudentService {

    // This ArrayList works as our database to store all students
    private ArrayList<Student> studentList;

    // This counter helps us give unique ID to each new student
    private int idCounter;

    // --- Constructor ---
    public StudentService() {
        studentList = new ArrayList<Student>();
        idCounter = 1; // first student will get ID = 1

        // Adding some sample students so we can test without registering every time
        Student s1 = new Student(idCounter++, "Rahul Sharma", "rahul@gmail.com", "rahul123", "CSE", 8.5);
        s1.addSkill("Java");
        s1.addSkill("Python");
        studentList.add(s1);

        Student s2 = new Student(idCounter++, "Priya Patel", "priya@gmail.com", "priya123", "IT", 7.8);
        s2.addSkill("SQL");
        s2.addSkill("HTML");
        studentList.add(s2);
    }

    // --- Register a new student ---
    // This method takes student details and adds them to the list
    // Returns true if registration is successful
    // Returns false if email already exists
    public boolean registerStudent(String name, String email, String password, String branch, double cgpa) {

        // First check if email is already registered
        // We don't want two students with same email
        for (int i = 0; i < studentList.size(); i++) {
            if (studentList.get(i).getEmail().equalsIgnoreCase(email)) {
                System.out.println("❌ Email already registered! Please use a different email.");
                return false;
            }
        }

        // Email is unique, so create new student and add to list
        Student newStudent = new Student(idCounter, name, email, password, branch, cgpa);
        idCounter++; // increase counter so next student gets a different ID
        studentList.add(newStudent);

        System.out.println("✅ Student registered successfully! Your ID is: " + newStudent.getId());
        return true;
    }

    // --- Login a student ---
    // This method checks email and password
    // Returns the Student object if login is successful
    // Returns null if email or password is wrong
    public Student loginStudent(String email, String password) {

        // Loop through all students and check email + password
        for (int i = 0; i < studentList.size(); i++) {
            Student s = studentList.get(i);

            // Check if email matches
            if (s.getEmail().equalsIgnoreCase(email)) {

                // Email matched, now check password
                if (s.getPassword().equals(password)) {
                    System.out.println("✅ Login successful! Welcome, " + s.getName() + "!");
                    return s; // return the student object
                } else {
                    // Email found but password is wrong
                    System.out.println("❌ Wrong password! Please try again.");
                    return null;
                }
            }
        }

        // If we reach here, email was not found in the list
        System.out.println("❌ Email not found! Please register first.");
        return null;
    }

    // --- Get all students ---
    // This method returns the full list of students
    // Used by Admin to see all registered students
    public ArrayList<Student> getAllStudents() {
        return studentList;
    }

    // --- Find a student by their ID ---
    // Returns the Student object if found
    // Returns null if no student has that ID
    public Student getStudentById(int id) {

        for (int i = 0; i < studentList.size(); i++) {
            if (studentList.get(i).getId() == id) {
                return studentList.get(i);
            }
        }

        // Student with given ID not found
        return null;
    }

    // --- Display all students ---
    // This method prints details of every student in the list
    // Useful for Admin panel
    public void displayAllStudents() {

        // Check if there are any students at all
        if (studentList.size() == 0) {
            System.out.println("No students registered yet.");
            return;
        }

        System.out.println("\n===== ALL REGISTERED STUDENTS =====");

        // Loop through list and print each student's info
        for (int i = 0; i < studentList.size(); i++) {
            studentList.get(i).displayInfo();
        }
    }

    // --- Add skill to a student ---
    // This method finds student by ID and adds a skill to their profile
    public void addSkillToStudent(int studentId, String skill) {

        Student s = getStudentById(studentId);

        if (s == null) {
            System.out.println("❌ Student not found!");
            return;
        }

        // Check if student already has this skill
        if (s.hasSkill(skill)) {
            System.out.println("⚠️ You already have this skill: " + skill);
        } else {
            s.addSkill(skill);
            System.out.println("✅ Skill added successfully: " + skill);
        }
    }

    // --- Get total number of students ---
    // Simple method to count how many students are registered
    public int getTotalStudents() {
        return studentList.size();
    }
}