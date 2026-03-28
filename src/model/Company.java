// File: src/model/Company.java
// Package: model
// Description: Represents a Company entity in the Smart Campus Placement System.

package model;

/**
 * Company model class.
 * Stores all information related to a recruiting company.
 * Used across service, GUI, and file handling layers.
 */
public class Company {

    // ─── Fields ───────────────────────────────────────────────

    private String id;       // Unique company ID (e.g., COM001)
    private String name;     // Name of the company (e.g., Google, TCS)
    private String email;    // Email used for login
    private String password; // Password for authentication

    // ─── Constructors ─────────────────────────────────────────

    /**
     * Default constructor.
     * Creates an empty Company object.
     */
    public Company() {
    }

    /**
     * Parameterized constructor.
     * Use this to create a fully initialized Company object.
     *
     * @param id       Unique company ID
     * @param name     Company name
     * @param email    Email address
     * @param password Login password
     */
    public Company(String id, String name, String email, String password) {
        this.id       = id;
        this.name     = name;
        this.email    = email;
        this.password = password;
    }

    // ─── Getters ──────────────────────────────────────────────

    /** @return Company's unique ID */
    public String getId() {
        return id;
    }

    /** @return Company's name */
    public String getName() {
        return name;
    }

    /** @return Company's email address */
    public String getEmail() {
        return email;
    }

    /** @return Company's login password */
    public String getPassword() {
        return password;
    }

    // ─── Setters ──────────────────────────────────────────────

    /** @param id Unique company ID to set */
    public void setId(String id) {
        this.id = id;
    }

    /** @param name Company name to set */
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
     * Returns a readable string representation of the Company.
     * Example: Company{id='COM001', name='Google', email='google@hire.com'}
     *
     * @return Formatted string of company data
     */
    @Override
    public String toString() {
        return "Company{" +
                "id='"      + id      + '\'' +
                ", name='"  + name    + '\'' +
                ", email='" + email   + '\'' +
                '}';
        // Note: password is intentionally excluded from toString() for security
    }
}