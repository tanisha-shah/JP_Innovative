// Package name - this file belongs to the service package
package service;

// We need ArrayList to store all companies
import java.util.ArrayList;

// We need Company class from model package
import model.Company;

// CompanyService class - handles all logic related to companies
// like registering, logging in, and getting company list
public class CompanyService {

    // This ArrayList works as our database to store all companies
    private ArrayList<Company> companyList;

    // This counter helps us give unique ID to each new company
    private int idCounter;

    // --- Constructor ---
    public CompanyService() {
        companyList = new ArrayList<Company>();
        idCounter = 1; // first company will get ID = 1

        // Adding some sample companies so we can test without registering every time
        Company c1 = new Company(idCounter++, "Google", "google@gmail.com", "google123");
        companyList.add(c1);

        Company c2 = new Company(idCounter++, "TCS", "tcs@tcs.com", "tcs123");
        companyList.add(c2);

        Company c3 = new Company(idCounter++, "Infosys", "infosys@infosys.com", "infosys123");
        companyList.add(c3);
    }

    // --- Register a new company ---
    // This method takes company details and adds them to the list
    // Returns true if registration is successful
    // Returns false if email already exists
    public boolean registerCompany(String name, String email, String password) {

        // First check if email is already registered
        // We don't want two companies with same email
        for (int i = 0; i < companyList.size(); i++) {
            if (companyList.get(i).getEmail().equalsIgnoreCase(email)) {
                System.out.println("❌ Email already registered! Please use a different email.");
                return false;
            }
        }

        // Email is unique, so create new company and add to list
        Company newCompany = new Company(idCounter, name, email, password);
        idCounter++; // increase counter so next company gets a different ID
        companyList.add(newCompany);

        System.out.println("✅ Company registered successfully! Your ID is: " + newCompany.getId());
        return true;
    }

    // --- Login a company ---
    // This method checks email and password
    // Returns the Company object if login is successful
    // Returns null if email or password is wrong
    public Company loginCompany(String email, String password) {

        // Loop through all companies and check email + password
        for (int i = 0; i < companyList.size(); i++) {
            Company c = companyList.get(i);

            // Check if email matches
            if (c.getEmail().equalsIgnoreCase(email)) {

                // Email matched, now check password
                if (c.getPassword().equals(password)) {
                    System.out.println("✅ Login successful! Welcome, " + c.getName() + "!");
                    return c; // return the company object
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

    // --- Get all companies ---
    // This method returns the full list of companies
    // Used by Admin to see all registered companies
    public ArrayList<Company> getAllCompanies() {
        return companyList;
    }

    // --- Find a company by their ID ---
    // Returns the Company object if found
    // Returns null if no company has that ID
    public Company getCompanyById(int id) {

        for (int i = 0; i < companyList.size(); i++) {
            if (companyList.get(i).getId() == id) {
                return companyList.get(i);
            }
        }

        // Company with given ID not found
        return null;
    }

    // --- Find a company by their name ---
    // Returns the Company object if found
    // Returns null if no company has that name
    public Company getCompanyByName(String name) {

        for (int i = 0; i < companyList.size(); i++) {
            if (companyList.get(i).getName().equalsIgnoreCase(name)) {
                return companyList.get(i);
            }
        }

        // Company with given name not found
        return null;
    }

    // --- Display all companies ---
    // This method prints details of every company in the list
    // Useful for Admin panel
    public void displayAllCompanies() {

        // Check if there are any companies at all
        if (companyList.size() == 0) {
            System.out.println("No companies registered yet.");
            return;
        }

        System.out.println("\n===== ALL REGISTERED COMPANIES =====");

        // Loop through list and print each company's info
        for (int i = 0; i < companyList.size(); i++) {
            companyList.get(i).displayInfo();
        }
    }

    // --- Get total number of companies ---
    // Simple method to count how many companies are registered
    public int getTotalCompanies() {
        return companyList.size();
    }
}