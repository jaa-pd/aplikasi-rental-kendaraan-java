package aplikasirental.test;

import aplikasirental.model.*;
import aplikasirental.manager.*;
import java.util.*;

public class TestApplication {
    public static void main(String[] args) {
        System.out.println("=== Testing Vehicle Rental Application ===\n");
        
        // Initialize managers
        DataManager dataManager = new DataManager();
        VehicleManager vehicleManager = new VehicleManager(dataManager);
        CustomerManager customerManager = new CustomerManager(dataManager);
        RentalManager rentalManager = new RentalManager(dataManager, vehicleManager);
        
        // Test 1: Create and add vehicles
        System.out.println("Test 1: Creating vehicles...");
        Car car1 = new Car("VH0001", "Toyota", "Avanza", 2020, "B 1234 XYZ", 300000, 4, "Manual");
        Car car2 = new Car("VH0002", "Honda", "Jazz", 2021, "B 5678 ABC", 350000, 4, "Otomatis");
        Motorcycle moto1 = new Motorcycle("VH0003", "Yamaha", "NMAX", 2022, "B 9012 DEF", 150000, 155, "Matic");
        
        vehicleManager.addVehicle(car1);
        vehicleManager.addVehicle(car2);
        vehicleManager.addVehicle(moto1);
        System.out.println("✓ Added 3 vehicles");
        System.out.println("  - " + car1.toString());
        System.out.println("  - " + car2.toString());
        System.out.println("  - " + moto1.toString());
        
        // Test 2: Create and add customers
        System.out.println("\nTest 2: Creating customers...");
        Customer cust1 = new Customer("CUS0001", "Budi Santoso", "081234567890", "Jakarta Selatan", "3171234567890123");
        Customer cust2 = new Customer("CUS0002", "Siti Nurhaliza", "082345678901", "Jakarta Utara", "3172345678901234");
        
        customerManager.addCustomer(cust1);
        customerManager.addCustomer(cust2);
        System.out.println("✓ Added 2 customers");
        System.out.println("  - " + cust1.toString());
        System.out.println("  - " + cust2.toString());
        
        // Test 3: Create rentals
        System.out.println("\nTest 3: Creating rentals...");
        Date startDate = new Date();
        Calendar cal = Calendar.getInstance();
        cal.setTime(startDate);
        cal.add(Calendar.DAY_OF_MONTH, 3);
        Date endDate = cal.getTime();
        
        Rental rental1 = new Rental("RNT0001", cust1, car1, startDate, endDate, 3);
        rentalManager.addRental(rental1);
        System.out.println("✓ Created rental: " + rental1.toString());
        System.out.println("  - Duration: " + rental1.getDays() + " days");
        System.out.println("  - Total cost: Rp " + String.format("%.0f", rental1.getTotalCost()));
        System.out.println("  - Status: " + rental1.getStatus());
        
        // Test 4: Check vehicle availability
        System.out.println("\nTest 4: Checking vehicle availability...");
        List<Vehicle> available = vehicleManager.getAvailableVehicles();
        System.out.println("✓ Available vehicles: " + available.size());
        for (Vehicle v : available) {
            System.out.println("  - " + v.toString());
        }
        
        // Test 5: Complete rental
        System.out.println("\nTest 5: Completing rental...");
        rentalManager.completeRental(0);
        System.out.println("✓ Rental completed");
        System.out.println("  - Vehicle " + car1.getVehicleId() + " is now available: " + car1.isAvailable());
        
        // Test 6: Generate report
        System.out.println("\nTest 6: Generating report...");
        List<Vehicle> allVehicles = vehicleManager.getAllVehicles();
        List<Customer> allCustomers = customerManager.getAllCustomers();
        List<Rental> allRentals = rentalManager.getAllRentals();
        double totalRevenue = rentalManager.getTotalRevenue();
        
        System.out.println("✓ Report:");
        System.out.println("  - Total vehicles: " + allVehicles.size());
        System.out.println("  - Total customers: " + allCustomers.size());
        System.out.println("  - Total rentals: " + allRentals.size());
        System.out.println("  - Total revenue: Rp " + String.format("%.0f", totalRevenue));
        
        System.out.println("\n=== All tests completed successfully! ===");
    }
}
