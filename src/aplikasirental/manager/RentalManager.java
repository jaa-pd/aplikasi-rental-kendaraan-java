package aplikasirental.manager;

import aplikasirental.model.*;
import java.util.*;

public class RentalManager {
    private List<Rental> rentals;
    private DataManager dataManager;
    private VehicleManager vehicleManager;
    
    public RentalManager(DataManager dataManager, VehicleManager vehicleManager) {
        this.dataManager = dataManager;
        this.vehicleManager = vehicleManager;
        this.rentals = dataManager.loadRentals();
    }
    
    public void addRental(Rental rental) {
        rentals.add(rental);
        rental.getVehicle().setAvailable(false);
        dataManager.saveRentals(rentals);
        dataManager.saveVehicles(vehicleManager.getAllVehicles());
    }
    
    public void updateRental(int index, Rental rental) {
        if (index >= 0 && index < rentals.size()) {
            rentals.set(index, rental);
            dataManager.saveRentals(rentals);
        }
    }
    
    public void completeRental(int index) {
        if (index >= 0 && index < rentals.size()) {
            Rental rental = rentals.get(index);
            rental.setStatus("Selesai");
            rental.getVehicle().setAvailable(true);
            dataManager.saveRentals(rentals);
            dataManager.saveVehicles(vehicleManager.getAllVehicles());
        }
    }
    
    public void cancelRental(int index) {
        if (index >= 0 && index < rentals.size()) {
            Rental rental = rentals.get(index);
            rental.setStatus("Dibatalkan");
            rental.getVehicle().setAvailable(true);
            dataManager.saveRentals(rentals);
            dataManager.saveVehicles(vehicleManager.getAllVehicles());
        }
    }
    
    public List<Rental> getAllRentals() {
        return new ArrayList<>(rentals);
    }
    
    public List<Rental> getActiveRentals() {
        List<Rental> active = new ArrayList<>();
        for (Rental r : rentals) {
            if (r.getStatus().equals("Aktif")) {
                active.add(r);
            }
        }
        return active;
    }
    
    public String generateRentalId() {
        int maxId = 0;
        for (Rental r : rentals) {
            String id = r.getRentalId();
            if (id != null && id.startsWith("RNT")) {
                try {
                    int num = Integer.parseInt(id.substring(3));
                    if (num > maxId) {
                        maxId = num;
                    }
                } catch (NumberFormatException e) {
                    // Skip invalid IDs
                }
            }
        }
        return "RNT" + String.format("%04d", maxId + 1);
    }
    
    public double getTotalRevenue() {
        double total = 0;
        for (Rental r : rentals) {
            if (r.getStatus().equals("Selesai")) {
                total += r.getTotalCost();
            }
        }
        return total;
    }
}
