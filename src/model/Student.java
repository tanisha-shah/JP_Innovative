// File: src/model/Student.java
// Package: model
// Description: Represents a Student entity in the Smart Campus Placement System.

package model;

import java.util.List;
import java.util.ArrayList;

/**
 * Student model class.
 * Stores all information related to a student.
 * Used across service, GUI, and file handling layers.
 */
public class Student {

    // ─── Fields ───────────────────────────────────────────────

    private String id;        // Unique student ID (e.g., STU001)
    private String name;      // Full name of the student
    private String email;     // Email used for login
    private String password;  // Password for authentication
    private String branch;    // Branch / Department (e.g., CSE, ECE)
    private double cgpa;      // CGPA (e.g., 8.5)
    private List<String> skills; // List of technical/soft skills (e.g., ["Java", "SQL"])

    // ─── Constructors ─────────────────────────────────────────

    /**
     * Default constructor.
     * Initializes skills as an empty list.
     */
    public Student() {
        this.skills = new ArrayList<>();
    }

    /**
     * Parameterized constructor.
     * Use this to create a fully initialized Student object.
     *
     * @param id       Unique student ID
     * @param name     Full name
     * @param email    Email address
     * @param password Login password
     * @param branch   Academic branch/department
     * @param cgpa     Current CGPA
     * @param skills   List of skills
     */
    public Student(String id, String name, String email, String password,
                   String branch, double cgpa, List<String> skills) {
        this.id       = id;
        this.name     = name;
        this.email    = email;
        this.password = password;
        this.branch   = branch;
        this.cgpa     = cgpa;
        this.skills   = (skills != null) ? skills : new ArrayList<>();
    }

    // ─── Getters ──────────────────────────────────────────────

    /** @return Student's unique ID */
    public String getId() {
        return id;
    }

    /** @return Student's full name */
    public String getName() {
        return name;
    }

    /** @return Student's email address */
    public String getEmail() {
        return email;
    }

    /** @return Student's login password */
    public String getPassword() {
        return password;
    }

    /** @return Student's academic branch */
    public String getBranch() {
        return branch;
    }

    /** @return Student's CGPA */
    public double getCgpa() {
        return cgpa;
    }

    /** @return List of student's skills */
    public List<String> getSkills() {
        return skills;
    }

    // ─── Setters ──────────────────────────────────────────────

    /** @param id Unique student ID to set */
    public void setId(String id) {
        this.id = id;
    }

    /** @param name Full name to set */
    public void setName(String name) {
        this.name = name;
    }

    /** @param email Email address to set */
    public void setEmail(String email) {
        this.email = email;
    }

    /** @param password Login password to set */
    public void setPassword(String password) {
        this.password = password;
    }

    /** @param branch Academic branch to set */
    public void setBranch(String branch) {
        this.branch = branch;
    }

    /** @param cgpa CGPA value to set */
    public void setCgpa(double cgpa) {
        this.cgpa = cgpa;
    }

    /** @param skills List of skills to set */
    public void setSkills(List<String> skills) {
        this.skills = (skills != null) ? skills : new ArrayList<>();
    }

    // ─── Utility ──────────────────────────────────────────────

    /**
     * Returns a readable string representation of the Student.
     * Skills list is joined with a pipe "|" separator for easy file storage.
     * Example: STU001 | Alice | alice@email.com | CSE | 8.75 | Java|Python
     *
     * @return Formatted string of student data
     */
    @Override
    public String toString() {
        return "Student{" +
                "id='"       + id       + '\'' +
                ", name='"   + name     + '\'' +
                ", email='"  + email    + '\'' +
                ", branch='" + branch   + '\'' +
                ", cgpa="    + cgpa     +
                ", skills="  + skills   +
                '}';
    }
}