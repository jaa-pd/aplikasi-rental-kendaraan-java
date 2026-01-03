# Setup MySQL Database

Aplikasi ini menggunakan MySQL sebagai database. Ikuti langkah-langkah berikut untuk setup:

## 1. Install MySQL Server

### Windows:
- Download MySQL dari https://dev.mysql.com/downloads/mysql/
- Jalankan installer dan ikuti petunjuk
- Catat password root yang Anda buat

### Linux (Ubuntu/Debian):
```bash
sudo apt update
sudo apt install mysql-server
sudo mysql_secure_installation
```

### macOS:
```bash
brew install mysql
brew services start mysql
```

## 2. Konfigurasi Database

Aplikasi akan otomatis membuat database dan tabel saat pertama kali dijalankan.

**Default Configuration:**
- Host: localhost
- Port: 3306
- Database: rental_kendaraan
- Username: root
- Password: (kosong)

Jika Anda perlu mengubah konfigurasi, edit file:
`src/aplikasirental/manager/DatabaseConnection.java`

```java
private static final String URL = "jdbc:mysql://localhost:3306/rental_kendaraan?useSSL=false&serverTimezone=UTC";
private static final String USER = "root";
private static final String PASSWORD = "your_password";
```

## 3. Data Awal

Aplikasi akan otomatis mengisi database dengan 20 kendaraan sample saat pertama kali dijalankan:
- **10 Mobil** dengan plat nomor Jakarta (B)
- **10 Motor** dengan plat nomor Jakarta (B)

Setiap kendaraan memiliki:
- Kondisi (Baik, Sangat Baik, dll)
- Keterangan lengkap

## 4. Struktur Database

### Tabel: vehicles
- vehicle_id (VARCHAR 20) - Primary Key
- vehicle_type (VARCHAR 20) - "Mobil" atau "Motor"
- brand (VARCHAR 50) - Merk kendaraan
- model (VARCHAR 50) - Model kendaraan
- year (INT) - Tahun produksi
- license_plate (VARCHAR 20) - Plat nomor
- daily_rate (DOUBLE) - Tarif harian
- available (BOOLEAN) - Status ketersediaan
- kondisi (VARCHAR 50) - Kondisi kendaraan
- keterangan (TEXT) - Keterangan tambahan
- number_of_doors (INT) - Jumlah pintu (untuk mobil)
- transmission_type (VARCHAR 20) - Tipe transmisi (untuk mobil)
- engine_capacity (INT) - Kapasitas mesin cc (untuk motor)
- motorcycle_type (VARCHAR 20) - Tipe motor

### Tabel: customers
- customer_id (VARCHAR 20) - Primary Key
- name (VARCHAR 100) - Nama pelanggan
- phone_number (VARCHAR 20) - Nomor telepon
- address (TEXT) - Alamat
- id_number (VARCHAR 50) - Nomor KTP

### Tabel: rentals
- rental_id (VARCHAR 20) - Primary Key
- customer_id (VARCHAR 20) - Foreign Key ke customers
- vehicle_id (VARCHAR 20) - Foreign Key ke vehicles
- start_date (DATE) - Tanggal mulai
- end_date (DATE) - Tanggal selesai
- days (INT) - Jumlah hari
- total_cost (DOUBLE) - Total biaya
- status (VARCHAR 20) - Status rental

## 5. Troubleshooting

### Error: Access denied for user 'root'@'localhost'
Ubah password di DatabaseConnection.java sesuai dengan password MySQL Anda.

### Error: Communications link failure
Pastikan MySQL server sudah running:
```bash
# Linux/Mac
sudo service mysql start
# atau
sudo systemctl start mysql

# Windows: Start MySQL service dari Services
```

### Error: Database 'rental_kendaraan' doesn't exist
Aplikasi akan membuat database secara otomatis. Pastikan user memiliki hak CREATE DATABASE.

### Membuat user MySQL dengan privileges:
```sql
CREATE USER 'rental_user'@'localhost' IDENTIFIED BY 'password123';
GRANT ALL PRIVILEGES ON rental_kendaraan.* TO 'rental_user'@'localhost';
FLUSH PRIVILEGES;
```

## 6. Backup dan Restore

### Backup Database:
```bash
mysqldump -u root -p rental_kendaraan > backup.sql
```

### Restore Database:
```bash
mysql -u root -p rental_kendaraan < backup.sql
```

## 7. Query Manual (Opsional)

Untuk mengakses database secara manual:
```bash
mysql -u root -p
USE rental_kendaraan;
SELECT * FROM vehicles;
SELECT * FROM customers;
SELECT * FROM rentals;
```
