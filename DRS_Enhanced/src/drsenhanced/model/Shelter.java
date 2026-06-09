package drsenhanced.model;

/**
 * Represents an evacuation shelter in the Disaster Response System.
 * Shelter details are used for capacity and occupancy monitoring.
 */
public class Shelter {

    private int shelterId;
    private String name;
    private int capacity;
    private int currentOccupancy;

    /**
     * Default constructor.
     */
    public Shelter() {
    }

    /**
     * Creates a shelter with capacity details.
     *
     * @param shelterId the shelter ID
     * @param name the shelter name
     * @param capacity the maximum shelter capacity
     * @param currentOccupancy the current number of occupants
     */
    public Shelter(int shelterId, String name, int capacity,
            int currentOccupancy) {
        this.shelterId = shelterId;
        this.name = name;
        this.capacity = capacity;
        this.currentOccupancy = currentOccupancy;
    }

    public int getShelterId() {
        return this.shelterId;
    }

    public void setShelterId(int shelterId) {
        this.shelterId = shelterId;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCapacity() {
        return this.capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public int getCurrentOccupancy() {
        return this.currentOccupancy;
    }

    public void setCurrentOccupancy(int currentOccupancy) {
        this.currentOccupancy = currentOccupancy;
    }

    @Override
    public String toString() {
        return this.name + " : "
                + this.currentOccupancy + " / "
                + this.capacity;
    }
}