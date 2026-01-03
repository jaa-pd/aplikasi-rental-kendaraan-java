package aplikasirental.model;

public class Motorcycle extends Vehicle {
    private static final long serialVersionUID = 1L;
    
    private int engineCapacity;
    private String motorcycleType;
    
    public Motorcycle() {
        super();
    }
    
    public Motorcycle(String vehicleId, String brand, String model, int year, 
                      String licensePlate, double dailyRate, int engineCapacity, 
                      String motorcycleType) {
        super(vehicleId, brand, model, year, licensePlate, dailyRate);
        this.engineCapacity = engineCapacity;
        this.motorcycleType = motorcycleType;
    }
    
    public Motorcycle(String vehicleId, String brand, String model, int year, 
                      String licensePlate, double dailyRate, String kondisi, String keterangan,
                      int engineCapacity, String motorcycleType) {
        super(vehicleId, brand, model, year, licensePlate, dailyRate, kondisi, keterangan);
        this.engineCapacity = engineCapacity;
        this.motorcycleType = motorcycleType;
    }
    
    public int getEngineCapacity() {
        return engineCapacity;
    }
    
    public void setEngineCapacity(int engineCapacity) {
        this.engineCapacity = engineCapacity;
    }
    
    public String getMotorcycleType() {
        return motorcycleType;
    }
    
    public void setMotorcycleType(String motorcycleType) {
        this.motorcycleType = motorcycleType;
    }
    
    @Override
    public String getVehicleType() {
        return "Motor";
    }
    
    @Override
    public String toString() {
        return super.toString() + " - " + getVehicleType() + " (" + engineCapacity + "cc, " + motorcycleType + ")";
    }
}
