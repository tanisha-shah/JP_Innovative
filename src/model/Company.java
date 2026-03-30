package model;

public class Company {

    private int id;
    private String name;
    private String email;
    private String password;

    // Constructor
    public Company(int cid, String cname, String cemail, String cpassword) {
        id = cid;
        name = cname;
        email = cemail;
        password = cpassword;
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

    public void setId(int cid) {
        id = cid;
    }

    public void setName(String cname) {
        name = cname;
    }

    public void setEmail(String cemail) {
        email = cemail;
    }

    public void setPassword(String cpassword) {
        password = cpassword;
    }

    // Helper method

    public void displayInfo() {
        System.out.println("-----------------------------");
        System.out.println("Company ID   : " + id);
        System.out.println("Name         : " + name);
        System.out.println("Email        : " + email);
        System.out.println("-----------------------------");
    }
}