package aplikasirental.model;

public class Car extends Vehicle {
    private static final long serialVersionUID = 1L;
    
    private int numberOfDoors;
    private String transmissionType;
    
    public Car() {
        super();
    }
    
    public Car(String vehicleId, String brand, String model, int year, 
               String licensePlate, double dailyRate, int numberOfDoors, 
               String transmissionType) {
        super(vehicleId, brand, model, year, licensePlate, dailyRate);
        this.numberOfDoors = numberOfDoors;
        this.transmissionType = transmissionType;
    }
    
    public Car(String vehicleId, String brand, String model, int year, 
               String licensePlate, double dailyRate, String kondisi, String keterangan,
               int numberOfDoors, String transmissionType) {
        super(vehicleId, brand, model, year, licensePlate, dailyRate, kondisi, keterangan);
        this.numberOfDoors = numberOfDoors;
        this.transmissionType = transmissionType;
    }
    
    public int getNumberOfDoors() {
        return numberOfDoors;
    }
    
    public void setNumberOfDoors(int numberOfDoors) {
        this.numberOfDoors = numberOfDoors;
    }
    
    public String getTransmissionType() {
        return transmissionType;
    }
    
    public void setTransmissionType(String transmissionType) {
        this.transmissionType = transmissionType;
    }
    
    @Override
    public String getVehicleType() {
        return "Mobil";
    }
    
    @Override
    public String toString() {
        return super.toString() + " - " + getVehicleType() + " (" + numberOfDoors + " pintu, " + transmissionType + ")";
    }
}
