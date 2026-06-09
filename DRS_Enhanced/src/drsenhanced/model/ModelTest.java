package drsenhanced.model;

public class ModelTest {

    public static void main(String[] args) {

        User user = new User();
        System.out.println("User created");

        Citizen citizen = new Citizen();
        System.out.println("Citizen created");

        CityManager cityManager = new CityManager();
        System.out.println("CityManager created");

        DisasterReport disasterReport = new DisasterReport();
        System.out.println("DisasterReport created");

        DispatchRequest dispatchRequest = new DispatchRequest();
        System.out.println("DispatchRequest created");

        EmergencyWorker emergencyWorker = new EmergencyWorker();
        System.out.println("EmergencyWorker created");

        Incident incident = new Incident();
        System.out.println("Incident created");

        Notification notification = new Notification();
        System.out.println("Notification created");

        Resource resource = new Resource();
        System.out.println("Resource created");

        Shelter shelter = new Shelter();
        System.out.println("Shelter created");

        StatusUpdate statusUpdate = new StatusUpdate();
        System.out.println("StatusUpdate created");

        System.out.println("All model classes created successfully.");
    }
}
