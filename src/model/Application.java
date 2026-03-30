package model;

public class Application {

    private int applicationId;
    private int studentId;
    private int driveId;
    private String status;

    // Constructor
    public Application(int aid, int sid, int did) {
        applicationId = aid;
        studentId = sid;
        driveId = did;
        status = "Pending";
    }

    // Getters

    public int getApplicationId() {
        return applicationId;
    }

    public int getStudentId() {
        return studentId;
    }

    public int getDriveId() {
        return driveId;
    }

    public String getStatus() {
        return status;
    }

    // Setters

    public void setApplicationId(int aid) {
        applicationId = aid;
    }

    public void setStudentId(int sid) {
        studentId = sid;
    }

    public void setDriveId(int did) {
        driveId = did;
    }

    public void setStatus(String stat) {
        status = stat;
    }

    // Helper method

    public void displayInfo() {
        System.out.println("-----------------------------");
        System.out.println("Application ID : " + applicationId);
        System.out.println("Student ID     : " + studentId);
        System.out.println("Drive ID       : " + driveId);
        System.out.println("Status         : " + status);
        System.out.println("-----------------------------");
    }
}