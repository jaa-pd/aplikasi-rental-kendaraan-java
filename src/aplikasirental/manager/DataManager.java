package aplikasirental.manager;

import aplikasirental.model.*;
import java.sql.*;
import java.util.*;

public class DataManager {
    
    public DataManager() {
        // Initialize database on first run
        DatabaseConnection.initializeDatabase();
        DatabaseConnection.populateSampleData();
    }
    
    // Save Vehicles
    public void saveVehicles(List<Vehicle> vehicles) {
        // This method is kept for compatibility but now saves to MySQL
        try (Connection conn = DatabaseConnection.getConnection()) {
            // Clear existing vehicles
            try (Statement stmt = conn.createStatement()) {
                stmt.execute("DELETE FROM vehicles");
            }
            
            // Insert all vehicles
            for (Vehicle v : vehicles) {
                saveVehicle(v);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    private void saveVehicle(Vehicle v) throws SQLException {
        try (Connection conn = DatabaseConnection.getConnection()) {
            String sql;
            if (v instanceof Car) {
                Car car = (Car) v;
                sql = "INSERT INTO vehicles (vehicle_id, vehicle_type, brand, model, year, license_plate, " +
                      "daily_rate, available, kondisi, keterangan, number_of_doors, transmission_type) " +
                      "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?) " +
                      "ON DUPLICATE KEY UPDATE brand=?, model=?, year=?, license_plate=?, daily_rate=?, " +
                      "available=?, kondisi=?, keterangan=?, number_of_doors=?, transmission_type=?";
                
                try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                    pstmt.setString(1, car.getVehicleId());
                    pstmt.setString(2, "Mobil");
                    pstmt.setString(3, car.getBrand());
                    pstmt.setString(4, car.getModel());
                    pstmt.setInt(5, car.getYear());
                    pstmt.setString(6, car.getLicensePlate());
                    pstmt.setDouble(7, car.getDailyRate());
                    pstmt.setBoolean(8, car.isAvailable());
                    pstmt.setString(9, car.getKondisi());
                    pstmt.setString(10, car.getKeterangan());
                    pstmt.setInt(11, car.getNumberOfDoors());
                    pstmt.setString(12, car.getTransmissionType());
                    // Duplicate key update
                    pstmt.setString(13, car.getBrand());
                    pstmt.setString(14, car.getModel());
                    pstmt.setInt(15, car.getYear());
                    pstmt.setString(16, car.getLicensePlate());
                    pstmt.setDouble(17, car.getDailyRate());
                    pstmt.setBoolean(18, car.isAvailable());
                    pstmt.setString(19, car.getKondisi());
                    pstmt.setString(20, car.getKeterangan());
                    pstmt.setInt(21, car.getNumberOfDoors());
                    pstmt.setString(22, car.getTransmissionType());
                    pstmt.executeUpdate();
                }
            } else if (v instanceof Motorcycle) {
                Motorcycle moto = (Motorcycle) v;
                sql = "INSERT INTO vehicles (vehicle_id, vehicle_type, brand, model, year, license_plate, " +
                      "daily_rate, available, kondisi, keterangan, engine_capacity, motorcycle_type) " +
                      "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?) " +
                      "ON DUPLICATE KEY UPDATE brand=?, model=?, year=?, license_plate=?, daily_rate=?, " +
                      "available=?, kondisi=?, keterangan=?, engine_capacity=?, motorcycle_type=?";
                
                try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                    pstmt.setString(1, moto.getVehicleId());
                    pstmt.setString(2, "Motor");
                    pstmt.setString(3, moto.getBrand());
                    pstmt.setString(4, moto.getModel());
                    pstmt.setInt(5, moto.getYear());
                    pstmt.setString(6, moto.getLicensePlate());
                    pstmt.setDouble(7, moto.getDailyRate());
                    pstmt.setBoolean(8, moto.isAvailable());
                    pstmt.setString(9, moto.getKondisi());
                    pstmt.setString(10, moto.getKeterangan());
                    pstmt.setInt(11, moto.getEngineCapacity());
                    pstmt.setString(12, moto.getMotorcycleType());
                    // Duplicate key update
                    pstmt.setString(13, moto.getBrand());
                    pstmt.setString(14, moto.getModel());
                    pstmt.setInt(15, moto.getYear());
                    pstmt.setString(16, moto.getLicensePlate());
                    pstmt.setDouble(17, moto.getDailyRate());
                    pstmt.setBoolean(18, moto.isAvailable());
                    pstmt.setString(19, moto.getKondisi());
                    pstmt.setString(20, moto.getKeterangan());
                    pstmt.setInt(21, moto.getEngineCapacity());
                    pstmt.setString(22, moto.getMotorcycleType());
                    pstmt.executeUpdate();
                }
            }
        }
    }
    
    // Load Vehicles
    public List<Vehicle> loadVehicles() {
        List<Vehicle> vehicles = new ArrayList<>();
        
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM vehicles ORDER BY vehicle_id")) {
            
            while (rs.next()) {
                String type = rs.getString("vehicle_type");
                String vehicleId = rs.getString("vehicle_id");
                String brand = rs.getString("brand");
                String model = rs.getString("model");
                int year = rs.getInt("year");
                String plate = rs.getString("license_plate");
                double rate = rs.getDouble("daily_rate");
                boolean available = rs.getBoolean("available");
                String kondisi = rs.getString("kondisi");
                String keterangan = rs.getString("keterangan");
                
                Vehicle vehicle;
                if ("Mobil".equals(type)) {
                    int doors = rs.getInt("number_of_doors");
                    String trans = rs.getString("transmission_type");
                    vehicle = new Car(vehicleId, brand, model, year, plate, rate, kondisi, keterangan, doors, trans);
                } else {
                    int engine = rs.getInt("engine_capacity");
                    String motoType = rs.getString("motorcycle_type");
                    vehicle = new Motorcycle(vehicleId, brand, model, year, plate, rate, kondisi, keterangan, engine, motoType);
                }
                vehicle.setAvailable(available);
                vehicles.add(vehicle);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return vehicles;
    }
    
    // Save Customers
    public void saveCustomers(List<Customer> customers) {
        try (Connection conn = DatabaseConnection.getConnection()) {
            // Clear existing
            try (Statement stmt = conn.createStatement()) {
                stmt.execute("DELETE FROM customers");
            }
            
            // Insert all
            String sql = "INSERT INTO customers (customer_id, name, phone_number, address, id_number) " +
                        "VALUES (?, ?, ?, ?, ?)";
            
            for (Customer c : customers) {
                try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                    pstmt.setString(1, c.getCustomerId());
                    pstmt.setString(2, c.getName());
                    pstmt.setString(3, c.getPhoneNumber());
                    pstmt.setString(4, c.getAddress());
                    pstmt.setString(5, c.getIdNumber());
                    pstmt.executeUpdate();
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    // Load Customers
    public List<Customer> loadCustomers() {
        List<Customer> customers = new ArrayList<>();
        
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM customers ORDER BY customer_id")) {
            
            while (rs.next()) {
                Customer customer = new Customer(
                    rs.getString("customer_id"),
                    rs.getString("name"),
                    rs.getString("phone_number"),
                    rs.getString("address"),
                    rs.getString("id_number")
                );
                customers.add(customer);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return customers;
    }
    
    // Save Rentals
    public void saveRentals(List<Rental> rentals) {
        try (Connection conn = DatabaseConnection.getConnection()) {
            // Clear existing
            try (Statement stmt = conn.createStatement()) {
                stmt.execute("DELETE FROM rentals");
            }
            
            // Insert all
            String sql = "INSERT INTO rentals (rental_id, customer_id, vehicle_id, start_date, " +
                        "end_date, days, total_cost, status) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
            
            for (Rental r : rentals) {
                try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                    pstmt.setString(1, r.getRentalId());
                    pstmt.setString(2, r.getCustomer().getCustomerId());
                    pstmt.setString(3, r.getVehicle().getVehicleId());
                    pstmt.setDate(4, new java.sql.Date(r.getStartDate().getTime()));
                    pstmt.setDate(5, new java.sql.Date(r.getEndDate().getTime()));
                    pstmt.setInt(6, r.getDays());
                    pstmt.setDouble(7, r.getTotalCost());
                    pstmt.setString(8, r.getStatus());
                    pstmt.executeUpdate();
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    // Load Rentals
    public List<Rental> loadRentals() {
        List<Rental> rentals = new ArrayList<>();
        
        // First load vehicles and customers for reference
        List<Vehicle> vehicles = loadVehicles();
        List<Customer> customers = loadCustomers();
        
        Map<String, Vehicle> vehicleMap = new HashMap<>();
        for (Vehicle v : vehicles) {
            vehicleMap.put(v.getVehicleId(), v);
        }
        
        Map<String, Customer> customerMap = new HashMap<>();
        for (Customer c : customers) {
            customerMap.put(c.getCustomerId(), c);
        }
        
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM rentals ORDER BY rental_id")) {
            
            while (rs.next()) {
                String rentalId = rs.getString("rental_id");
                Customer customer = customerMap.get(rs.getString("customer_id"));
                Vehicle vehicle = vehicleMap.get(rs.getString("vehicle_id"));
                
                if (customer != null && vehicle != null) {
                    Rental rental = new Rental(
                        rentalId,
                        customer,
                        vehicle,
                        new java.util.Date(rs.getDate("start_date").getTime()),
                        new java.util.Date(rs.getDate("end_date").getTime()),
                        rs.getInt("days")
                    );
                    rental.setStatus(rs.getString("status"));
                    rental.setTotalCost(rs.getDouble("total_cost"));
                    rentals.add(rental);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return rentals;
    }
}

