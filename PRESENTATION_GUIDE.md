# Dokumentasi Presentasi Aplikasi Rental Kendaraan

## 📋 Ringkasan Aplikasi

**Nama Aplikasi**: Aplikasi Rental Kendaraan  
**Platform**: Desktop Application (Java Swing)  
**Database**: MySQL 8.0+  
**IDE**: Apache NetBeans  
**Bahasa**: Java 8+  

Aplikasi rental kendaraan berbasis desktop yang memungkinkan pengelolaan rental mobil dan motor dengan fitur lengkap termasuk manajemen kendaraan, pelanggan, transaksi rental, dan pelaporan.

---

## 🏗️ Arsitektur Aplikasi

Aplikasi ini menggunakan arsitektur **3-Layer (Three-Tier Architecture)**:

```
┌─────────────────────────────────────┐
│     Presentation Layer (GUI)        │
│   - MainFrame.java (Swing UI)       │
└────────────┬────────────────────────┘
             │
             ▼
┌─────────────────────────────────────┐
│      Business Logic Layer           │
│   - VehicleManager.java             │
│   - CustomerManager.java            │
│   - RentalManager.java              │
│   - DataManager.java                │
└────────────┬────────────────────────┘
             │
             ▼
┌─────────────────────────────────────┐
│       Data Access Layer             │
│   - DatabaseConnection.java         │
│   - MySQL Database                  │
└─────────────────────────────────────┘
             │
             ▼
┌─────────────────────────────────────┐
│          Model Layer                │
│   - Vehicle.java (Abstract)         │
│   - Car.java                        │
│   - Motorcycle.java                 │
│   - Customer.java                   │
│   - Rental.java                     │
└─────────────────────────────────────┘
```

---

## 📦 Struktur Proyek

```
aplikasi-rental-kendaraan-java/
├── src/
│   └── aplikasirental/
│       ├── model/              # Model/Entity Classes
│       │   ├── Vehicle.java    # Abstract class untuk kendaraan
│       │   ├── Car.java        # Model mobil (extends Vehicle)
│       │   ├── Motorcycle.java # Model motor (extends Vehicle)
│       │   ├── Customer.java   # Model pelanggan
│       │   └── Rental.java     # Model transaksi rental
│       ├── manager/            # Business Logic Layer
│       │   ├── DatabaseConnection.java  # Koneksi & init DB
│       │   ├── DataManager.java         # Koordinator data
│       │   ├── VehicleManager.java      # CRUD kendaraan
│       │   ├── CustomerManager.java     # CRUD pelanggan
│       │   └── RentalManager.java       # CRUD rental
│       ├── gui/                # Presentation Layer
│       │   └── MainFrame.java  # UI utama aplikasi
│       └── test/               # Testing
│           └── TestApplication.java
├── lib/                        # External Libraries
│   └── mysql-connector-j-8.2.0.jar
├── nbproject/                  # NetBeans Configuration
├── migrations/                 # Database Migrations
│   ├── 001_add_sample_customers.sql
│   └── README.md
└── Documentation Files
    ├── README.md
    ├── MYSQL_SETUP.md
    ├── DOCUMENTATION.md
    ├── TROUBLESHOOTING_DRIVER.md
    └── DATABASE_SCHEMA.sql
```

---

## 🎯 Fitur Utama Aplikasi

### 1. **Manajemen Kendaraan**
- ✅ Tambah kendaraan (mobil/motor)
- ✅ Edit data kendaraan
- ✅ Hapus kendaraan
- ✅ Lihat daftar kendaraan
- ✅ Filter berdasarkan status (tersedia/disewa)
- ✅ Tracking kondisi kendaraan
- ✅ Keterangan detail setiap kendaraan

### 2. **Manajemen Pelanggan**
- ✅ Tambah pelanggan baru
- ✅ Edit data pelanggan
- ✅ Hapus pelanggan
- ✅ Lihat daftar pelanggan
- ✅ Validasi NIK Indonesia (16 digit)

### 3. **Transaksi Rental**
- ✅ Buat transaksi rental baru
- ✅ Pilih pelanggan dan kendaraan
- ✅ Tentukan durasi rental
- ✅ Perhitungan otomatis biaya
- ✅ Selesaikan rental
- ✅ Batalkan rental
- ✅ Update status kendaraan otomatis

### 4. **Laporan & Statistik**
- ✅ Total kendaraan (tersedia & disewa)
- ✅ Total pelanggan terdaftar
- ✅ Statistik rental (aktif, selesai, dibatalkan)
- ✅ Total pendapatan

---

## 📊 Detail Komponen Kode

### **1. Model Layer** (Package: `aplikasirental.model`)

#### **Vehicle.java** - Class Abstract untuk Kendaraan
```java
public abstract class Vehicle implements Serializable
```

**Fungsi:**
- Base class untuk semua jenis kendaraan
- Menyimpan atribut umum: ID, brand, model, tahun, plat nomor, tarif, status
- **Atribut baru:** kondisi (Baik/Sangat Baik/Cukup/Perlu Perbaikan), keterangan
- Method abstract `getType()` untuk polimorfisme

**Kegunaan:**
- Implementasi inheritance (pewarisan) OOP
- Menghindari duplikasi kode
- Memudahkan penambahan jenis kendaraan baru

#### **Car.java** - Model Mobil
```java
public class Car extends Vehicle
```

**Atribut Spesifik:**
- `numberOfDoors` - Jumlah pintu mobil
- `transmissionType` - Manual/Otomatis

**Fungsi:**
- Representasi mobil dalam sistem
- Override method `getType()` mengembalikan "Mobil"

#### **Motorcycle.java** - Model Motor
```java
public class Motorcycle extends Vehicle
```

**Atribut Spesifik:**
- `engineCapacity` - Kapasitas mesin (cc)
- `motorcycleType` - Matic/Sport/Bebek

**Fungsi:**
- Representasi motor dalam sistem
- Override method `getType()` mengembalikan "Motor"

#### **Customer.java** - Model Pelanggan
```java
public class Customer implements Serializable
```

**Atribut:**
- `customerId` - ID unik pelanggan (CUS0001, CUS0002, ...)
- `name` - Nama lengkap
- `phoneNumber` - Nomor telepon
- `address` - Alamat lengkap
- `idNumber` - NIK (16 digit sesuai regulasi Indonesia)

**Fungsi:**
- Menyimpan data pelanggan
- Validasi NIK Indonesia
- toString() untuk display di ComboBox

#### **Rental.java** - Model Transaksi Rental
```java
public class Rental implements Serializable
```

**Atribut:**
- `rentalId` - ID unik rental (RNT0001, RNT0002, ...)
- `customerId` - Foreign key ke Customer
- `vehicleId` - Foreign key ke Vehicle
- `startDate` - Tanggal mulai rental
- `endDate` - Tanggal selesai rental
- `days` - Jumlah hari rental
- `totalCost` - Total biaya
- `status` - Aktif/Selesai/Dibatalkan

**Fungsi:**
- State machine untuk status rental
- Perhitungan otomatis total biaya
- Tracking transaksi rental

---

### **2. Manager Layer** (Package: `aplikasirental.manager`)

#### **DatabaseConnection.java** - Koneksi & Inisialisasi Database

**Fungsi Utama:**

1. **`getConnection()`** - Mendapatkan koneksi MySQL
```java
private static final String URL = "jdbc:mysql://localhost:3306/rental_kendaraan?useSSL=false&serverTimezone=UTC";
private static final String USER = "root";
private static final String PASSWORD = "";
```

**Kegunaan:**
- Singleton pattern untuk koneksi database
- Load JDBC driver otomatis
- Handle exception dengan baik

2. **`initializeDatabase()`** - Membuat Database & Tabel
```sql
CREATE DATABASE IF NOT EXISTS rental_kendaraan
CREATE TABLE vehicles (...)
CREATE TABLE customers (...)
CREATE TABLE rentals (...)
```

**Kegunaan:**
- Auto-create database saat pertama kali run
- Define schema dengan foreign keys
- Idempoten (aman dijalankan berkali-kali)

3. **`populateSampleData()`** - Mengisi Data Sample
```java
- 10 Pelanggan dengan NIK Jakarta (16 digit)
- 10 Mobil dengan plat B (Jakarta)
- 10 Motor dengan plat B (Jakarta)
```

**Kegunaan:**
- Demo/testing data
- Cek duplikasi sebelum insert
- Data realistic untuk presentasi

#### **DataManager.java** - Koordinator Data

**Fungsi:**
- **Singleton Pattern** - Hanya 1 instance
- Load semua data dari database ke memory saat startup
- Koordinasi antar manager (Vehicle, Customer, Rental)

**Method Utama:**
```java
getInstance()           - Get singleton instance
loadAllData()          - Load semua data dari DB
getVehicleManager()    - Get VehicleManager
getCustomerManager()   - Get CustomerManager
getRentalManager()     - Get RentalManager
```

**Kegunaan:**
- Central point untuk akses data
- Caching data di memory untuk performa
- Dependency injection untuk manager

#### **VehicleManager.java** - CRUD Kendaraan

**Method Utama:**
```java
addVehicle(Vehicle)           - Tambah kendaraan baru
updateVehicle(int, Vehicle)   - Update kendaraan existing
deleteVehicle(int)            - Hapus kendaraan
getVehicles()                 - Get semua kendaraan
getAvailableVehicles()        - Get kendaraan tersedia
generateVehicleId()           - Generate ID unik
```

**Algoritma ID Generation:**
```java
// Scan semua vehicle ID, cari max number, increment
VH0001 → VH0002 → VH0003 ... VH9999
```

**Kegunaan:**
- CRUD operations untuk kendaraan
- Auto-generate ID dengan format VH0001
- Prevent ID collision setelah delete
- Sync ke database MySQL

#### **CustomerManager.java** - CRUD Pelanggan

**Method Utama:**
```java
addCustomer(Customer)         - Tambah pelanggan
updateCustomer(int, Customer) - Update pelanggan
deleteCustomer(int)           - Hapus pelanggan
getCustomers()                - Get semua pelanggan
generateCustomerId()          - Generate ID unik
```

**Algoritma ID Generation:**
```java
// Format: CUS0001, CUS0002, ...
```

**Kegunaan:**
- CRUD operations untuk pelanggan
- Validasi NIK 16 digit
- Sync ke database MySQL

#### **RentalManager.java** - CRUD Transaksi Rental

**Method Utama:**
```java
createRental(Rental)          - Buat rental baru
completeRental(int)           - Selesaikan rental
cancelRental(int)             - Batalkan rental
getRentals()                  - Get semua rental
getActiveRentals()            - Get rental aktif
getTotalRevenue()             - Hitung total pendapatan
generateRentalId()            - Generate ID unik
```

**Business Logic:**
```java
createRental():
  1. Generate RNT0001, RNT0002, ...
  2. Set status = "Aktif"
  3. Update vehicle.available = false
  4. Save ke database

completeRental():
  1. Update status = "Selesai"
  2. Update vehicle.available = true
  3. Save ke database

cancelRental():
  1. Update status = "Dibatalkan"
  2. Update vehicle.available = true
  3. Save ke database
```

**Kegunaan:**
- State management untuk rental
- Auto-update vehicle availability
- Calculate revenue
- Transaction integrity

---

### **3. GUI Layer** (Package: `aplikasirental.gui`)

#### **MainFrame.java** - Main User Interface

**Komponen UI:**

1. **Tab 1: Kendaraan**
   - JTable untuk display kendaraan
   - Kolom: ID, Merk, Model, Tahun, Plat, Tipe, Kondisi, Keterangan, Tarif, Status
   - Button: Tambah, Edit, Hapus
   - Modal dialog untuk input

2. **Tab 2: Pelanggan**
   - JTable untuk display pelanggan
   - Kolom: ID, Nama, Telepon, Alamat, NIK
   - Button: Tambah, Edit, Hapus
   - Validasi NIK 16 digit

3. **Tab 3: Transaksi Rental**
   - JTable untuk display rental
   - Kolom: ID, Pelanggan, Kendaraan, Tanggal, Hari, Biaya, Status
   - Button: Buat Rental, Selesaikan, Batalkan
   - ComboBox untuk pilih pelanggan & kendaraan
   - Auto-calculate total cost

4. **Tab 4: Laporan**
   - Display statistik:
     - Total kendaraan
     - Kendaraan tersedia
     - Kendaraan disewa
     - Total pelanggan
     - Rental aktif/selesai/dibatalkan
     - Total pendapatan
   - Button: Refresh

**Event Handling:**
```java
addVehicleButton.addActionListener() → showAddVehicleDialog()
editVehicleButton.addActionListener() → showEditVehicleDialog()
deleteVehicleButton.addActionListener() → confirmDelete()
createRentalButton.addActionListener() → showCreateRentalDialog()
completeRentalButton.addActionListener() → completeRental()
```

**Kegunaan:**
- User interaction point
- Data validation
- Real-time update tampilan
- Modal dialogs untuk input
- Error handling & user feedback

---

## 🗄️ Database Schema

### **Tabel: vehicles**
```sql
vehicle_id VARCHAR(20) PRIMARY KEY
vehicle_type VARCHAR(20)        -- 'Mobil' atau 'Motor'
brand VARCHAR(50)
model VARCHAR(50)
year INT
license_plate VARCHAR(20)
daily_rate DOUBLE
available BOOLEAN
kondisi VARCHAR(50)             -- Status kondisi
keterangan TEXT                 -- Keterangan detail
number_of_doors INT             -- Khusus mobil
transmission_type VARCHAR(20)   -- Khusus mobil
engine_capacity INT             -- Khusus motor
motorcycle_type VARCHAR(20)     -- Khusus motor
```

### **Tabel: customers**
```sql
customer_id VARCHAR(20) PRIMARY KEY
name VARCHAR(100)
phone_number VARCHAR(20)
address TEXT
id_number VARCHAR(50)           -- NIK 16 digit
```

### **Tabel: rentals**
```sql
rental_id VARCHAR(20) PRIMARY KEY
customer_id VARCHAR(20) FOREIGN KEY
vehicle_id VARCHAR(20) FOREIGN KEY
start_date DATE
end_date DATE
days INT
total_cost DOUBLE
status VARCHAR(20)              -- 'Aktif'/'Selesai'/'Dibatalkan'
```

---

## 🔄 Alur Kerja Aplikasi

### **Alur Startup:**
```
1. MainFrame.main()
2. DatabaseConnection.initializeDatabase()
   - Create database rental_kendaraan
   - Create tables: vehicles, customers, rentals
3. DatabaseConnection.populateSampleData()
   - Insert 10 customers
   - Insert 10 cars
   - Insert 10 motorcycles
4. DataManager.getInstance().loadAllData()
   - Load vehicles from DB
   - Load customers from DB
   - Load rentals from DB
5. MainFrame window displayed
```

### **Alur Tambah Kendaraan:**
```
1. User klik button "Tambah Kendaraan"
2. Show dialog input:
   - Pilih tipe (Mobil/Motor)
   - Input: brand, model, year, plate, rate, kondisi, keterangan
   - Input spesifik: doors/transmission (mobil) atau engine/type (motor)
3. User klik "Simpan"
4. Validasi input
5. VehicleManager.generateVehicleId() → VH0021
6. Create object Car/Motorcycle
7. VehicleManager.addVehicle()
   - Add ke ArrayList
   - INSERT ke database MySQL
8. refreshVehicleTable()
9. Show success message
```

### **Alur Buat Rental:**
```
1. User klik button "Buat Rental"
2. Show dialog:
   - ComboBox pilih pelanggan
   - ComboBox pilih kendaraan (hanya available)
   - DatePicker start date
   - Input jumlah hari
3. Auto-calculate:
   - end_date = start_date + days
   - total_cost = daily_rate × days
4. User klik "Simpan"
5. RentalManager.generateRentalId() → RNT0001
6. Create Rental object dengan status "Aktif"
7. RentalManager.createRental()
   - Add rental ke ArrayList
   - INSERT ke rentals table
   - UPDATE vehicles SET available=false
8. refreshRentalTable()
9. refreshVehicleTable()
10. Show success message
```

### **Alur Selesaikan Rental:**
```
1. User pilih rental aktif di tabel
2. User klik "Selesaikan Rental"
3. Confirm dialog
4. RentalManager.completeRental(index)
   - UPDATE rentals SET status='Selesai'
   - UPDATE vehicles SET available=true
5. refreshRentalTable()
6. refreshVehicleTable()
7. refreshReportPanel()
8. Show success message
```

---

## 🎨 Desain Pattern yang Digunakan

### 1. **Singleton Pattern**
**Digunakan di:** DataManager, DatabaseConnection

**Kegunaan:**
- Hanya 1 instance di seluruh aplikasi
- Hemat memory
- Centralized access point

**Implementasi:**
```java
private static DataManager instance;

public static DataManager getInstance() {
    if (instance == null) {
        instance = new DataManager();
    }
    return instance;
}
```

### 2. **Factory Pattern**
**Digunakan di:** Vehicle creation (Car/Motorcycle)

**Kegunaan:**
- Encapsulation object creation
- Flexibility dalam membuat object
- Easy to extend

### 3. **MVC (Model-View-Controller)**
**Implementasi:**
- **Model:** Vehicle, Car, Motorcycle, Customer, Rental
- **View:** MainFrame (Swing components)
- **Controller:** VehicleManager, CustomerManager, RentalManager

### 4. **Repository Pattern**
**Digunakan di:** Manager classes

**Kegunaan:**
- Abstraction layer untuk data access
- Separation of concerns
- Easy to switch database

---

## 🔐 Fitur Keamanan

### 1. **Validasi Input**
- NIK harus 16 digit
- Nomor telepon format valid
- Tanggal rental valid
- Tarif harian > 0

### 2. **Database Connection**
- Prepared statements untuk prevent SQL injection
- Connection pooling
- Auto-close resources (try-with-resources)

### 3. **Error Handling**
- Try-catch di semua database operations
- User-friendly error messages
- Logging error ke console

---

## 📈 Statistik Kode

```
Total Java Files: 12
Total Lines of Code: ~1,600 lines
Total Classes: 12
- Model: 5 classes
- Manager: 5 classes
- GUI: 1 class
- Test: 1 class

Database Tables: 3
Sample Data:
- 10 Customers
- 10 Cars
- 10 Motorcycles
```

---

## 🚀 Cara Menjalankan Aplikasi

### **Metode 1: Via NetBeans**
```
1. Buka NetBeans
2. File → Open Project
3. Pilih folder aplikasi
4. Klik kanan project → Run (F6)
```

### **Metode 2: Via Command Line**
```bash
# Compile
javac -cp "lib/*" -d build/classes -sourcepath src src/aplikasirental/*/*.java

# Build JAR
jar cfm dist/AplikasiRentalKendaraan.jar manifest.mf -C build/classes .

# Run
java -cp "lib/*:dist/AplikasiRentalKendaraan.jar" aplikasirental.gui.MainFrame
```

---

## 🎯 Keunggulan Aplikasi

### **1. User-Friendly Interface**
- GUI intuitif dengan tabbed interface
- Modal dialogs untuk input
- Real-time validation
- Clear error messages

### **2. Robust Architecture**
- 3-tier architecture
- Separation of concerns
- Design patterns implementation
- Clean code structure

### **3. Data Integrity**
- Foreign key constraints
- Transaction management
- Auto-update related data
- Duplicate prevention

### **4. Comprehensive Features**
- Complete CRUD operations
- Reporting & statistics
- Sample data included
- Migration scripts

### **5. Professional Documentation**
- README.md
- MYSQL_SETUP.md
- TROUBLESHOOTING_DRIVER.md
- DATABASE_SCHEMA.sql
- Migration docs

---

## 📝 Skenario Demo Presentasi

### **Skenario 1: Tambah Kendaraan Baru**
```
1. Buka tab "Kendaraan"
2. Klik "Tambah Kendaraan"
3. Pilih "Mobil"
4. Input:
   - Brand: Toyota
   - Model: Fortuner
   - Tahun: 2024
   - Plat: B 4321 XYZ
   - Tarif: 500000
   - Kondisi: Sangat Baik
   - Keterangan: Mobil SUV baru
   - Pintu: 4
   - Transmisi: Otomatis
5. Simpan
6. Lihat kendaraan baru di tabel
```

### **Skenario 2: Buat Transaksi Rental**
```
1. Buka tab "Transaksi Rental"
2. Klik "Buat Rental"
3. Pilih pelanggan: Ahmad Wijaya
4. Pilih kendaraan: Toyota Avanza (tersedia)
5. Tanggal mulai: Hari ini
6. Durasi: 3 hari
7. System auto-calculate: Rp 900,000
8. Simpan
9. Lihat rental baru dengan status "Aktif"
10. Lihat kendaraan berubah status jadi "Disewa"
```

### **Skenario 3: Selesaikan Rental**
```
1. Pilih rental yang aktif
2. Klik "Selesaikan Rental"
3. Konfirmasi
4. Status berubah jadi "Selesai"
5. Kendaraan kembali "Tersedia"
6. Lihat laporan → Total pendapatan bertambah
```

### **Skenario 4: Lihat Laporan**
```
1. Buka tab "Laporan"
2. Lihat statistik:
   - Total kendaraan: 21
   - Tersedia: 20
   - Disewa: 1
   - Total pelanggan: 10
   - Rental aktif: 1
   - Rental selesai: 1
   - Total pendapatan: Rp 900,000
3. Klik "Refresh" untuk update real-time
```

---

## 🔧 Teknologi & Tools

| Komponen | Teknologi |
|----------|-----------|
| **Bahasa** | Java 8+ |
| **GUI Framework** | Java Swing |
| **Database** | MySQL 8.0+ |
| **JDBC Driver** | MySQL Connector/J 8.2.0 |
| **IDE** | Apache NetBeans |
| **Build Tool** | Apache Ant |
| **Version Control** | Git/GitHub |

---

## 📚 Referensi Dokumentasi

1. **README.md** - Panduan umum & quick start
2. **MYSQL_SETUP.md** - Setup database MySQL
3. **DOCUMENTATION.md** - Dokumentasi teknis lengkap
4. **TROUBLESHOOTING_DRIVER.md** - Troubleshooting koneksi driver
5. **DATABASE_SCHEMA.sql** - Schema & sample data
6. **migrations/** - Database migration scripts

---

## 🎓 Konsep OOP yang Diimplementasikan

### **1. Encapsulation**
- Private attributes dengan getter/setter
- Data hiding
- Access control

### **2. Inheritance**
```
Vehicle (abstract)
    ├── Car
    └── Motorcycle
```

### **3. Polymorphism**
```java
Vehicle v = new Car(...);
String type = v.getType(); // "Mobil"
```

### **4. Abstraction**
```java
abstract class Vehicle {
    abstract String getType();
}
```

---

## 💡 Pembelajaran dari Project

### **Technical Skills:**
- Java Swing GUI development
- MySQL database integration
- JDBC programming
- Design patterns implementation
- Exception handling
- File I/O operations

### **Software Engineering:**
- 3-tier architecture
- MVC pattern
- Repository pattern
- Singleton pattern
- Clean code practices
- Documentation

### **Database:**
- SQL DDL & DML
- Foreign key relationships
- Transaction management
- Data migration
- Sample data generation

---

## ✅ Kesimpulan

Aplikasi Rental Kendaraan ini adalah aplikasi desktop yang **lengkap**, **robust**, dan **professional** dengan implementasi:

✅ **Arsitektur 3-Layer** yang clean dan maintainable  
✅ **MySQL Database** untuk persistence  
✅ **Design Patterns** (Singleton, MVC, Repository)  
✅ **Complete CRUD** operations  
✅ **User-friendly GUI** dengan Swing  
✅ **Business Logic** yang solid  
✅ **Sample Data** untuk demo  
✅ **Comprehensive Documentation**  
✅ **Error Handling** yang baik  
✅ **Code Quality** yang tinggi  

Aplikasi ini siap untuk **presentasi**, **demo**, dan **production use**.

---

**Dibuat dengan ❤️ menggunakan Java & MySQL**
