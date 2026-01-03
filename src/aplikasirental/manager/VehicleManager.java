package aplikasirental.manager;

import aplikasirental.model.*;
import java.util.*;

public class VehicleManager {
    private List<Vehicle> vehicles;
    private DataManager dataManager;
    
    public VehicleManager(DataManager dataManager) {
        this.dataManager = dataManager;
        this.vehicles = dataManager.loadVehicles();
    }
    
    public void addVehicle(Vehicle vehicle) {
        vehicles.add(vehicle);
        dataManager.saveVehicles(vehicles);
    }
    
    public void updateVehicle(int index, Vehicle vehicle) {
        if (index >= 0 && index < vehicles.size()) {
            vehicles.set(index, vehicle);
            dataManager.saveVehicles(vehicles);
        }
    }
    
    public void deleteVehicle(int index) {
        if (index >= 0 && index < vehicles.size()) {
            vehicles.remove(index);
            dataManager.saveVehicles(vehicles);
        }
    }
    
    public List<Vehicle> getAllVehicles() {
        return new ArrayList<>(vehicles);
    }
    
    public List<Vehicle> getAvailableVehicles() {
        List<Vehicle> available = new ArrayList<>();
        for (Vehicle v : vehicles) {
            if (v.isAvailable()) {
                available.add(v);
            }
        }
        return available;
    }
    
    public Vehicle getVehicleById(String vehicleId) {
        for (Vehicle v : vehicles) {
            if (v.getVehicleId().equals(vehicleId)) {
                return v;
            }
        }
        return null;
    }
    
    public String generateVehicleId() {
        int maxId = 0;
        for (Vehicle v : vehicles) {
            String id = v.getVehicleId();
            if (id != null && id.startsWith("VH")) {
                try {
                    int num = Integer.parseInt(id.substring(2));
                    if (num > maxId) {
                        maxId = num;
                    }
                } catch (NumberFormatException e) {
                    // Skip invalid IDs
                }
            }
        }
        return "VH" + String.format("%04d", maxId + 1);
    }
}
