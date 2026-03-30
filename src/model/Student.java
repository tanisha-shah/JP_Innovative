package model;

import java.util.ArrayList;

public class Student {

    private int id;
    private String name;
    private String email;
    private String password;
    private String branch;
    private double cgpa;
    private ArrayList<String> skills;

    // Constructor
    public Student(int sid, String sname, String semail, String spassword, String sbranch, double scgpa) {
        id = sid;
        name = sname;
        email = semail;
        password = spassword;
        branch = sbranch;
        cgpa = scgpa;
        skills = new ArrayList<String>();
    }

    // Getters

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getBranch() {
        return branch;
    }

    public double getCgpa() {
        return cgpa;
    }

    public ArrayList<String> getSkills() {
        return skills;
    }

    // Setters

    public void setId(int sid) {
        id = sid;
    }

    public void setName(String sname) {
        name = sname;
    }

    public void setEmail(String semail) {
        email = semail;
    }

    public void setPassword(String spassword) {
        password = spassword;
    }

    public void setBranch(String sbranch) {
        branch = sbranch;
    }

    public void setCgpa(double scgpa) {
        cgpa = scgpa;
    }

    public void setSkills(ArrayList<String> sskills) {
        skills = sskills;
    }

    // Helper methods

    public void addSkill(String skill) {
        skills.add(skill);
    }

    public boolean hasSkill(String skill) {
        for (int i = 0; i < skills.size(); i++) {
            if (skills.get(i).equalsIgnoreCase(skill)) {
                return true;
            }
        }
        return false;
    }

    public void displayInfo() {
        System.out.println("-----------------------------");
        System.out.println("Student ID   : " + id);
        System.out.println("Name         : " + name);
        System.out.println("Email        : " + email);
        System.out.println("Branch       : " + branch);
        System.out.println("CGPA         : " + cgpa);
        System.out.print("Skills       : ");

        if (skills.size() == 0) {
            System.out.println("No skills added yet");
        } else {
            for (int i = 0; i < skills.size(); i++) {
                System.out.print(skills.get(i));
                if (i != skills.size() - 1) {
                    System.out.print(", ");
                }
            }
            System.out.println();
        }
        System.out.println("-----------------------------");
    }
}