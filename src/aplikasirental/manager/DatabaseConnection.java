package aplikasirental.manager;

import java.sql.*;

public class DatabaseConnection {
    private static final String URL = "jdbc:mysql://localhost:3306/rental_kendaraan?useSSL=false&serverTimezone=UTC";
    private static final String USER = "root";
    private static final String PASSWORD = "";
    
    public static Connection getConnection() throws SQLException {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            return DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (ClassNotFoundException e) {
            throw new SQLException("MySQL JDBC Driver not found", e);
        }
    }
    
    public static void initializeDatabase() {
        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement()) {
            
            // Create database if not exists
            stmt.execute("CREATE DATABASE IF NOT EXISTS rental_kendaraan");
            stmt.execute("USE rental_kendaraan");
            
            // Create vehicles table
            stmt.execute("CREATE TABLE IF NOT EXISTS vehicles (" +
                    "vehicle_id VARCHAR(20) PRIMARY KEY," +
                    "vehicle_type VARCHAR(20) NOT NULL," +
                    "brand VARCHAR(50) NOT NULL," +
                    "model VARCHAR(50) NOT NULL," +
                    "year INT NOT NULL," +
                    "license_plate VARCHAR(20) NOT NULL," +
                    "daily_rate DOUBLE NOT NULL," +
                    "available BOOLEAN DEFAULT TRUE," +
                    "kondisi VARCHAR(50)," +
                    "keterangan TEXT," +
                    "number_of_doors INT," +
                    "transmission_type VARCHAR(20)," +
                    "engine_capacity INT," +
                    "motorcycle_type VARCHAR(20)" +
                    ")");
            
            // Create customers table
            stmt.execute("CREATE TABLE IF NOT EXISTS customers (" +
                    "customer_id VARCHAR(20) PRIMARY KEY," +
                    "name VARCHAR(100) NOT NULL," +
                    "phone_number VARCHAR(20) NOT NULL," +
                    "address TEXT NOT NULL," +
                    "id_number VARCHAR(50) NOT NULL" +
                    ")");
            
            // Create rentals table
            stmt.execute("CREATE TABLE IF NOT EXISTS rentals (" +
                    "rental_id VARCHAR(20) PRIMARY KEY," +
                    "customer_id VARCHAR(20) NOT NULL," +
                    "vehicle_id VARCHAR(20) NOT NULL," +
                    "start_date DATE NOT NULL," +
                    "end_date DATE NOT NULL," +
                    "days INT NOT NULL," +
                    "total_cost DOUBLE NOT NULL," +
                    "status VARCHAR(20) NOT NULL," +
                    "FOREIGN KEY (customer_id) REFERENCES customers(customer_id)," +
                    "FOREIGN KEY (vehicle_id) REFERENCES vehicles(vehicle_id)" +
                    ")");
            
            System.out.println("Database initialized successfully");
            
        } catch (SQLException e) {
            System.err.println("Error initializing database: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    public static void populateSampleData() {
        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement()) {
            
            stmt.execute("USE rental_kendaraan");
            
            // Check if data already exists
            ResultSet rs = stmt.executeQuery("SELECT COUNT(*) FROM vehicles");
            rs.next();
            if (rs.getInt(1) > 0) {
                System.out.println("Sample data already exists");
                return;
            }
            
            // Insert 10 cars with Jakarta plates
            String[] carBrands = {"Toyota", "Honda", "Daihatsu", "Suzuki", "Mitsubishi", "Nissan", "Mazda", "Toyota", "Honda", "Daihatsu"};
            String[] carModels = {"Avanza", "Jazz", "Xenia", "Ertiga", "Xpander", "Livina", "CX-5", "Innova", "Brio", "Terios"};
            String[] transmissions = {"Manual", "Otomatis", "Manual", "Manual", "Otomatis", "Manual", "Otomatis", "Manual", "Otomatis", "Manual"};
            int[] doors = {4, 4, 4, 4, 4, 4, 4, 4, 4, 4};
            double[] carRates = {300000, 350000, 280000, 320000, 400000, 300000, 500000, 450000, 250000, 350000};
            String[] kondisi = {"Baik", "Sangat Baik", "Baik", "Baik", "Sangat Baik", "Baik", "Sangat Baik", "Baik", "Baik", "Sangat Baik"};
            
            for (int i = 0; i < 10; i++) {
                String vehicleId = String.format("VH%04d", i + 1);
                String plate = String.format("B %d%d%d%d %s", 
                    (i+1), (i+2)%10, (i+3)%10, (i+4)%10, 
                    new String[]{"ABC", "XYZ", "DEF", "GHI", "JKL", "MNO", "PQR", "STU", "VWX", "YZA"}[i]);
                int year = 2019 + (i % 5);
                String keterangan = "Mobil keluarga " + carModels[i] + " dalam kondisi " + kondisi[i].toLowerCase();
                
                String sql = String.format(
                    "INSERT INTO vehicles (vehicle_id, vehicle_type, brand, model, year, license_plate, " +
                    "daily_rate, available, kondisi, keterangan, number_of_doors, transmission_type) " +
                    "VALUES ('%s', 'Mobil', '%s', '%s', %d, '%s', %.0f, TRUE, '%s', '%s', %d, '%s')",
                    vehicleId, carBrands[i], carModels[i], year, plate, carRates[i], 
                    kondisi[i], keterangan, doors[i], transmissions[i]
                );
                stmt.execute(sql);
            }
            
            // Insert 10 motorcycles with Jakarta plates
            String[] motoBrands = {"Yamaha", "Honda", "Suzuki", "Kawasaki", "Yamaha", "Honda", "Suzuki", "Yamaha", "Honda", "Kawasaki"};
            String[] motoModels = {"NMAX", "PCX", "Address", "Ninja 250", "Aerox", "Vario", "NEX II", "MX King", "BeAT", "KLX"};
            String[] motoTypes = {"Matic", "Matic", "Matic", "Sport", "Matic", "Matic", "Bebek", "Sport", "Matic", "Sport"};
            int[] engines = {155, 160, 110, 250, 155, 125, 115, 150, 110, 150};
            double[] motoRates = {150000, 160000, 100000, 250000, 140000, 120000, 80000, 130000, 100000, 200000};
            
            for (int i = 0; i < 10; i++) {
                String vehicleId = String.format("VH%04d", i + 11);
                String plate = String.format("B %d%d%d%d %s", 
                    (i+5), (i+6)%10, (i+7)%10, (i+8)%10,
                    new String[]{"AAA", "BBB", "CCC", "DDD", "EEE", "FFF", "GGG", "HHH", "III", "JJJ"}[i]);
                int year = 2020 + (i % 4);
                String kondisiMoto = kondisi[i];
                String keterangan = "Motor " + motoTypes[i] + " " + motoModels[i] + " " + engines[i] + "cc";
                
                String sql = String.format(
                    "INSERT INTO vehicles (vehicle_id, vehicle_type, brand, model, year, license_plate, " +
                    "daily_rate, available, kondisi, keterangan, engine_capacity, motorcycle_type) " +
                    "VALUES ('%s', 'Motor', '%s', '%s', %d, '%s', %.0f, TRUE, '%s', '%s', %d, '%s')",
                    vehicleId, motoBrands[i], motoModels[i], year, plate, motoRates[i],
                    kondisiMoto, keterangan, engines[i], motoTypes[i]
                );
                stmt.execute(sql);
            }
            
            System.out.println("Sample data populated successfully: 10 cars and 10 motorcycles");
            
        } catch (SQLException e) {
            System.err.println("Error populating sample data: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
