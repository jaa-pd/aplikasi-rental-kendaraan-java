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
            
            // Insert 10 customers with Indonesian NIK (16 digits)
            String[] customerNames = {
                "Ahmad Wijaya", "Siti Rahayu", "Budi Santoso", "Dewi Lestari", "Eko Prasetyo",
                "Fitri Handayani", "Gunawan Susanto", "Hani Maulida", "Irfan Hidayat", "Joko Widodo"
            };
            String[] phones = {
                "081234567890", "082345678901", "083456789012", "084567890123", "085678901234",
                "086789012345", "087890123456", "088901234567", "089012345678", "081123456789"
            };
            String[] addresses = {
                "Jl. Sudirman No. 10, Jakarta Selatan",
                "Jl. Gatot Subroto No. 25, Jakarta Pusat",
                "Jl. Thamrin No. 15, Jakarta Pusat",
                "Jl. Kuningan Raya No. 8, Jakarta Selatan",
                "Jl. Rasuna Said No. 12, Jakarta Selatan",
                "Jl. HR Rasuna Said No. 20, Jakarta Selatan",
                "Jl. Casablanca No. 5, Jakarta Selatan",
                "Jl. TB Simatupang No. 30, Jakarta Selatan",
                "Jl. Kemang Raya No. 7, Jakarta Selatan",
                "Jl. Senopati No. 18, Jakarta Selatan"
            };
            // Indonesian NIK format: 16 digits
            // Province code (31 = DKI Jakarta), District, Birth date (DDMMYY), Sequential number
            String[] niks = {
                "3171012509850001", // Jakarta Selatan, 25 Sept 1985
                "3172015608920002", // Jakarta Utara, 15 Aug 1992
                "3173021203880003", // Jakarta Barat, 12 Mar 1988
                "3174030107950004", // Jakarta Timur, 01 Jul 1995
                "3175042511900005", // Jakarta Pusat, 25 Nov 1990
                "3171056402930006", // Jakarta Selatan, 24 Feb 1993
                "3172061810870007", // Jakarta Utara, 18 Oct 1987
                "3173072203910008", // Jakarta Barat, 22 Mar 1991
                "3174081512940009", // Jakarta Timur, 15 Dec 1994
                "3175090608890010"  // Jakarta Pusat, 06 Aug 1989
            };
            
            for (int i = 0; i < 10; i++) {
                String customerId = String.format("CUS%04d", i + 1);
                String sql = String.format(
                    "INSERT INTO customers (customer_id, name, phone_number, address, id_number) " +
                    "VALUES ('%s', '%s', '%s', '%s', '%s')",
                    customerId, customerNames[i], phones[i], addresses[i], niks[i]
                );
                stmt.execute(sql);
            }
            
            System.out.println("Sample data populated: 10 customers");
            
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
            
            System.out.println("Sample data populated successfully: 10 customers, 10 cars and 10 motorcycles");
            
        } catch (SQLException e) {
            System.err.println("Error populating sample data: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
