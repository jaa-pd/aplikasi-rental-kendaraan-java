# Dokumentasi Aplikasi Rental Kendaraan

## Hasil Testing Aplikasi

```
=== Testing Vehicle Rental Application ===

Test 1: Creating vehicles...
✓ Added 3 vehicles
  - VH0001 - Toyota Avanza (2020) - B 1234 XYZ - Mobil (4 pintu, Manual)
  - VH0002 - Honda Jazz (2021) - B 5678 ABC - Mobil (4 pintu, Otomatis)
  - VH0003 - Yamaha NMAX (2022) - B 9012 DEF - Motor (155cc, Matic)

Test 2: Creating customers...
✓ Added 2 customers
  - CUS0001 - Budi Santoso (081234567890)
  - CUS0002 - Siti Nurhaliza (082345678901)

Test 3: Creating rentals...
✓ Created rental: RNT0001 - Budi Santoso - VH0001 - Toyota Avanza (2020)
  - Duration: 3 days
  - Total cost: Rp 900000
  - Status: Aktif

Test 4: Checking vehicle availability...
✓ Available vehicles: 2
  - VH0002 - Honda Jazz (2021) - B 5678 ABC
  - VH0003 - Yamaha NMAX (2022) - B 9012 DEF

Test 5: Completing rental...
✓ Rental completed
  - Vehicle VH0001 is now available: true

Test 6: Generating report...
✓ Report:
  - Total vehicles: 3
  - Total customers: 2
  - Total rentals: 1
  - Total revenue: Rp 900000

=== All tests completed successfully! ===
```

## Struktur Kode

### Model Classes (Package: aplikasirental.model)

1. **Vehicle.java** - Abstract base class untuk kendaraan
   - Properties: vehicleId, brand, model, year, licensePlate, dailyRate, available
   - Methods: getters/setters, abstract getVehicleType()

2. **Car.java** - Class untuk mobil (extends Vehicle)
   - Additional properties: numberOfDoors, transmissionType
   - Implements: getVehicleType() returns "Mobil"

3. **Motorcycle.java** - Class untuk motor (extends Vehicle)
   - Additional properties: engineCapacity, motorcycleType
   - Implements: getVehicleType() returns "Motor"

4. **Customer.java** - Class untuk data pelanggan
   - Properties: customerId, name, phoneNumber, address, idNumber

5. **Rental.java** - Class untuk transaksi rental
   - Properties: rentalId, customer, vehicle, startDate, endDate, days, totalCost, status

### Manager Classes (Package: aplikasirental.manager)

1. **DataManager.java** - Mengelola persistensi data
   - Menyimpan/memuat data ke/dari file menggunakan Java Serialization
   - Files: vehicles.dat, customers.dat, rentals.dat

2. **VehicleManager.java** - Mengelola data kendaraan
   - Methods: addVehicle, updateVehicle, deleteVehicle, getAllVehicles, getAvailableVehicles

3. **CustomerManager.java** - Mengelola data pelanggan
   - Methods: addCustomer, updateCustomer, deleteCustomer, getAllCustomers

4. **RentalManager.java** - Mengelola transaksi rental
   - Methods: addRental, completeRental, cancelRental, getAllRentals, getActiveRentals, getTotalRevenue

### GUI Classes (Package: aplikasirental.gui)

1. **MainFrame.java** - Main application window dengan 4 tabs:
   - **Tab Kendaraan**: CRUD operations untuk kendaraan
   - **Tab Pelanggan**: CRUD operations untuk pelanggan
   - **Tab Transaksi Rental**: Mengelola transaksi rental
   - **Tab Laporan**: Menampilkan statistik dan laporan

## Fitur-Fitur Aplikasi

### 1. Manajemen Kendaraan
- ✅ Tambah kendaraan baru (mobil atau motor)
- ✅ Edit informasi kendaraan
- ✅ Hapus kendaraan
- ✅ Lihat daftar semua kendaraan
- ✅ Status ketersediaan otomatis

### 2. Manajemen Pelanggan
- ✅ Tambah pelanggan baru
- ✅ Edit informasi pelanggan
- ✅ Hapus pelanggan
- ✅ Lihat daftar semua pelanggan

### 3. Transaksi Rental
- ✅ Buat transaksi rental baru
- ✅ Pilih pelanggan dan kendaraan tersedia
- ✅ Perhitungan otomatis total biaya
- ✅ Selesaikan rental (kendaraan kembali tersedia)
- ✅ Batalkan rental
- ✅ Tracking status rental (Aktif/Selesai/Dibatalkan)

### 4. Laporan
- ✅ Total kendaraan dan status ketersediaan
- ✅ Total pelanggan terdaftar
- ✅ Statistik transaksi rental
- ✅ Perhitungan total pendapatan

## Teknologi yang Digunakan

- **Java 8+** - Bahasa pemrograman
- **Java Swing** - GUI framework
- **Apache Ant** - Build tool
- **Java Serialization** - Data persistence
- **NetBeans** - IDE (compatible)

## File Konfigurasi NetBeans

- `build.xml` - Ant build script
- `manifest.mf` - JAR manifest file
- `nbproject/project.xml` - NetBeans project metadata
- `nbproject/project.properties` - Project properties
- `nbproject/build-impl.xml` - Build implementation

## Cara Menjalankan

### Option 1: Menggunakan NetBeans
1. Open Project di NetBeans
2. Klik Run Project (F6)

### Option 2: Menggunakan Command Line
```bash
# Compile
javac -d build/classes -sourcepath src src/aplikasirental/model/*.java src/aplikasirental/manager/*.java src/aplikasirental/gui/*.java

# Build JAR
jar cfm dist/AplikasiRentalKendaraan.jar manifest.mf -C build/classes .

# Run
java -jar dist/AplikasiRentalKendaraan.jar
```

### Option 3: Menggunakan Ant
```bash
ant compile
ant jar
ant run
```

## Data Persistence

Aplikasi menyimpan data secara otomatis dalam folder `data/`:
- `vehicles.dat` - Data kendaraan
- `customers.dat` - Data pelanggan  
- `rentals.dat` - Data transaksi rental

Data akan tetap tersimpan meskipun aplikasi ditutup dan dibuka kembali.

## Testing

File test tersedia di: `src/aplikasirental/test/TestApplication.java`

Menjalankan test:
```bash
javac -d build/classes -sourcepath src -cp build/classes src/aplikasirental/test/TestApplication.java
java -cp build/classes aplikasirental.test.TestApplication
```

## Status

✅ Semua fitur telah diimplementasi dan ditest
✅ Build berhasil tanpa error
✅ Test cases berjalan dengan sukses
✅ Aplikasi siap digunakan
