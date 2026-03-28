// File: src/model/Admin.java
// Package: model
// Description: Represents an Admin entity in the Smart Campus Placement System.

package model;

/**
 * Admin model class.
 * Stores all information related to a system administrator.
 * Admin has special privileges to manage students and companies.
 * Used across service, GUI, and file handling layers.
 */
public class Admin {

    // ─── Fields ───────────────────────────────────────────────

    private String id;       // Unique admin ID (e.g., ADM001)
    private String name;     // Full name of the admin
    private String email;    // Email used for login
    private String password; // Password for authentication

    // ─── Constructors ─────────────────────────────────────────

    /**
     * Default constructor.
     * Creates an empty Admin object.
     */
    public Admin() {
    }

    /**
     * Parameterized constructor.
     * Use this to create a fully initialized Admin object.
     *
     * @param id       Unique admin ID
     * @param name     Full name of the admin
     * @param email    Email address
     * @param password Login password
     */
    public Admin(String id, String name, String email, String password) {
        this.id       = id;
        this.name     = name;
        this.email    = email;
        this.password = password;
    }

    // ─── Getters ──────────────────────────────────────────────

    /** @return Admin's unique ID */
    public String getId() {
        return id;
    }

    /** @return Admin's full name */
    public String getName() {
        return name;
    }

    /** @return Admin's email address */
    public String getEmail() {
        return email;
    }

    /** @return Admin's login password */
    public String getPassword() {
        return password;
    }

    // ─── Setters ──────────────────────────────────────────────

    /** @param id Unique admin ID to set */
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

    // ─── Utility ──────────────────────────────────────────────

    /**
     * Returns a readable string representation of the Admin.
     * Example: Admin{id='ADM001', name='John Doe', email='admin@campus.com'}
     * Note: Password is intentionally excluded for security.
     *
     * @return Formatted string of admin data
     */
    @Override
    public String toString() {
        return "Admin{" +
                "id='"      + id      + '\'' +
                ", name='"  + name    + '\'' +
                ", email='" + email   + '\'' +
                '}';
        // Note: password is intentionally excluded from toString() for security
    }
}