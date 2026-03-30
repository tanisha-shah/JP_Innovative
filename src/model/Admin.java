package model;

public class Admin {

    private int id;
    private String name;
    private String email;
    private String password;

    // Constructor
    public Admin(int aid, String aname, String aemail, String apassword) {
        id = aid;
        name = aname;
        email = aemail;
        password = apassword;
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

    // Setters

    public void setId(int aid) {
        id = aid;
    }

    public void setName(String aname) {
        name = aname;
    }

    public void setEmail(String aemail) {
        email = aemail;
    }

    public void setPassword(String apassword) {
        password = apassword;
    }

    // toString method

    public String toString() {
        return "Admin [ID: " + id + ", Name: " + name + ", Email: " + email + "]";
    }
}