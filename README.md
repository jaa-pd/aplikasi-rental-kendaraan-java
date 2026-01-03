# Aplikasi Rental Kendaraan (Java)

Aplikasi rental kendaraan berbasis desktop menggunakan Java Swing untuk Apache NetBeans. Aplikasi ini memungkinkan pengelolaan rental mobil dan motor dengan fitur lengkap dan database MySQL.

## Fitur Utama

### 1. Manajemen Kendaraan
- Tambah, edit, dan hapus kendaraan (mobil dan motor)
- Informasi kendaraan: ID, merk, model, tahun, plat nomor, tarif harian
- **Kondisi kendaraan**: Sangat Baik, Baik, Cukup, Perlu Perbaikan
- **Keterangan**: Catatan tambahan untuk setiap kendaraan
- Informasi spesifik mobil: jumlah pintu, transmisi (Manual/Otomatis)
- Informasi spesifik motor: kapasitas mesin (cc), tipe motor (Matic/Sport/Bebek)
- Status ketersediaan kendaraan (Tersedia/Disewa)
- **Data awal: 10 mobil dan 10 motor dengan plat Jakarta (B)**

### 2. Manajemen Pelanggan
- Tambah, edit, dan hapus data pelanggan
- Informasi pelanggan: ID, nama, nomor telepon, alamat, nomor KTP

### 3. Transaksi Rental
- Buat transaksi rental baru
- Pilih pelanggan dan kendaraan tersedia
- Tentukan tanggal mulai dan durasi rental
- Perhitungan otomatis total biaya
- Selesaikan atau batalkan rental
- Status rental: Aktif, Selesai, Dibatalkan

### 4. Laporan
- Total kendaraan (tersedia dan disewa)
- Total pelanggan terdaftar
- Statistik rental (aktif, selesai, dibatalkan)
- Total pendapatan dari rental yang selesai

## Teknologi

- **Bahasa**: Java 8+
- **GUI**: Java Swing
- **IDE**: Apache NetBeans
- **Build Tool**: Apache Ant
- **Database**: MySQL 8.0+
- **JDBC Driver**: MySQL Connector/J 8.2.0

## Struktur Proyek

```
aplikasi-rental-kendaraan-java/
├── src/
│   └── aplikasirental/
│       ├── model/           # Model class (Vehicle, Car, Motorcycle, Customer, Rental)
│       ├── manager/         # Business logic (DataManager, VehicleManager, CustomerManager, RentalManager)
│       └── gui/             # GUI components (MainFrame)
├── nbproject/               # NetBeans project files
├── build.xml               # Ant build script
├── manifest.mf             # JAR manifest
└── README.md
```

## Cara Menggunakan

### Prasyarat

1. **Install XAMPP** (sudah include MySQL Server) atau **MySQL Server** standalone
2. **Java JDK 8 atau lebih baru**
3. **Apache NetBeans** (opsional)

### 📋 Alur Kerja - Setup MySQL dengan XAMPP

#### **Langkah 1: Install dan Jalankan XAMPP**

1. **Download XAMPP**
   - Kunjungi: https://www.apachefriends.org/
   - Download versi untuk sistem operasi Anda
   - Install XAMPP di lokasi default (biasanya `C:\xampp` di Windows)

2. **Jalankan XAMPP Control Panel**
   - Buka XAMPP Control Panel
   - Klik tombol **"Start"** pada modul **MySQL**
   - Pastikan status MySQL berubah menjadi **hijau/running**
   - Default port MySQL: **3306**

3. **Verifikasi MySQL Running**
   - Buka browser, akses: http://localhost/phpmyadmin
   - Jika berhasil, akan muncul halaman phpMyAdmin
   - Ini berarti MySQL sudah berjalan dengan baik

#### **Langkah 2: Konfigurasi Database (Otomatis)**

Aplikasi akan **otomatis** melakukan setup database saat pertama kali dijalankan:

1. **Database Connection Settings**
   - Host: `localhost`
   - Port: `3306`
   - Username: `root`
   - Password: ` ` (kosong/blank - default XAMPP)
   - Database: `rental_kendaraan`

2. **Auto-Create Schema**
   - Aplikasi akan membuat database `rental_kendaraan` jika belum ada
   - Membuat 3 tabel: `vehicles`, `customers`, `rentals`
   - Mengisi data sample:
     - 10 Pelanggan dengan NIK Jakarta
     - 10 Mobil dengan plat B (Jakarta)
     - 10 Motor dengan plat B (Jakarta)

> **Note**: Anda tidak perlu membuat database manual di phpMyAdmin. Aplikasi akan handle semuanya secara otomatis.

#### **Langkah 3: Jalankan Aplikasi**

##### **Opsi A: Via NetBeans (Recommended)**

1. **Buka Project**
   - Buka Apache NetBeans IDE
   - File → Open Project
   - Pilih folder `aplikasi-rental-kendaraan-java`

2. **Pastikan XAMPP MySQL Running**
   - Cek XAMPP Control Panel → MySQL harus **hijau**

3. **Run Aplikasi**
   - Klik kanan pada project → **Run** (atau tekan F6)
   - Aplikasi akan otomatis:
     - Connect ke MySQL
     - Create database `rental_kendaraan`
     - Create tables (vehicles, customers, rentals)
     - Populate sample data
     - Tampilkan GUI utama

4. **Verifikasi Data**
   - Buka tab **"Kendaraan"** → Harus ada 20 kendaraan
   - Buka tab **"Pelanggan"** → Harus ada 10 pelanggan
   - Buka tab **"Laporan"** → Lihat statistik

##### **Opsi B: Via Command Line**

```bash
# 1. Pastikan MySQL XAMPP running
# 2. Compile
javac -cp "lib/*" -d build/classes -sourcepath src src/aplikasirental/*/*.java

# 3. Run
java -cp "lib/*:build/classes" aplikasirental.gui.MainFrame
```

#### **Langkah 4: Verifikasi di phpMyAdmin (Opsional)**

1. **Buka phpMyAdmin**
   - Browser: http://localhost/phpmyadmin

2. **Lihat Database**
   - Klik database `rental_kendaraan` di sidebar kiri
   - Anda akan melihat 3 tabel:
     - `vehicles` (20 records)
     - `customers` (10 records)
     - `rentals` (0 records - belum ada transaksi)

3. **Cek Data**
   - Klik tabel `vehicles` → Browse
   - Akan muncul 10 mobil dan 10 motor dengan plat Jakarta (B)

### Troubleshooting XAMPP MySQL

#### **Problem 1: MySQL tidak bisa start di XAMPP**

**Penyebab**: Port 3306 sudah digunakan aplikasi lain

**Solusi**:
```
1. Buka XAMPP Control Panel
2. Klik tombol "Config" pada MySQL
3. Pilih "my.ini"
4. Cari baris: port=3306
5. Ganti dengan port lain, misal: port=3307
6. Save file
7. Start ulang MySQL
8. Update DatabaseConnection.java → ganti 3306 dengan 3307
```

#### **Problem 2: Error "Unable to find a suitable driver"**

**Solusi**: Lihat [TROUBLESHOOTING_DRIVER.md](TROUBLESHOOTING_DRIVER.md)

**Quick Fix**:
- Pastikan file `lib/mysql-connector-j-8.2.0.jar` ada
- Jangan run dari NetBeans Services (Database panel)
- Run dari project menu (klik kanan project → Run)

#### **Problem 3: Access Denied for user 'root'**

**Penyebab**: Password MySQL tidak kosong

**Solusi**:
```java
// Edit src/aplikasirental/manager/DatabaseConnection.java
// Line 13-14, ganti dengan password XAMPP Anda:
private static final String USER = "root";
private static final String PASSWORD = "your_password_here";  // Isi password Anda
```

#### **Problem 4: Database sudah ada dengan data lama**

**Solusi - Reset Database**:
```sql
-- Buka phpMyAdmin
-- Pilih database rental_kendaraan
-- Klik tab "SQL"
-- Jalankan query:

DROP DATABASE rental_kendaraan;

-- Kemudian run aplikasi lagi, database akan dibuat ulang dengan data fresh
```

### Manual Setup Database (Jika Diperlukan)

Jika ingin setup database manual (bukan otomatis):

1. **Buka phpMyAdmin**: http://localhost/phpmyadmin
2. **Klik tab "SQL"**
3. **Copy isi file**: [DATABASE_SCHEMA.sql](DATABASE_SCHEMA.sql)
4. **Paste dan Execute**
5. **Database siap digunakan**

Atau via MySQL Command Line:
```bash
mysql -u root -p < DATABASE_SCHEMA.sql
```

### Dokumentasi Lengkap

- **[MYSQL_SETUP.md](MYSQL_SETUP.md)** - Setup MySQL lengkap
- **[TROUBLESHOOTING_DRIVER.md](TROUBLESHOOTING_DRIVER.md)** - Fix driver issues
- **[PRESENTATION_GUIDE.md](PRESENTATION_GUIDE.md)** - Guide untuk presentasi/demo
- **[DATABASE_SCHEMA.sql](DATABASE_SCHEMA.sql)** - Database schema + sample data
- **[migrations/](migrations/)** - Database migration scripts

### Dengan NetBeans

1. Buka Apache NetBeans IDE
2. File → Open Project
3. Pilih folder `aplikasi-rental-kendaraan-java`
4. Klik kanan pada project → Run

### Dengan Command Line

#### Compile:
```bash
javac -cp "lib/*" -d build/classes -sourcepath src src/aplikasirental/model/*.java src/aplikasirental/manager/*.java src/aplikasirental/gui/*.java
```

#### Build JAR:
```bash
jar cfm dist/AplikasiRentalKendaraan.jar manifest.mf -C build/classes .
```

#### Run:
```bash
java -cp "lib/*:dist/AplikasiRentalKendaraan.jar" aplikasirental.gui.MainFrame
```

### Atau menggunakan Ant:
```bash
ant clean
ant compile
ant jar
ant run
```

## Cara Kerja Aplikasi

### 🚀 Quick Start Guide

1. **Start XAMPP MySQL** → Buka XAMPP Control Panel → Start MySQL
2. **Run Aplikasi** → Buka NetBeans → Klik kanan project → Run (F6)
3. **Mulai Gunakan** → GUI akan terbuka dengan data sample sudah terisi

### Workflow Penggunaan Aplikasi

### 1. Menambah Kendaraan
- Buka tab "Kendaraan"
- Klik tombol "Tambah Kendaraan"
- Pilih tipe kendaraan (Mobil/Motor)
- Isi semua informasi yang diperlukan:
  - Brand/Merk (contoh: Toyota)
  - Model (contoh: Avanza)
  - Tahun (contoh: 2023)
  - Plat Nomor (contoh: B 1234 XYZ)
  - Tarif Harian (contoh: 300000)
  - Kondisi (pilih: Sangat Baik/Baik/Cukup/Perlu Perbaikan)
  - Keterangan (contoh: Mobil keluarga, kondisi terawat)
  - **Untuk Mobil**: Jumlah Pintu, Transmisi (Manual/Otomatis)
  - **Untuk Motor**: Kapasitas Mesin (cc), Tipe (Matic/Sport/Bebek)
- Klik "Simpan"
- Kendaraan akan muncul di tabel dengan status "Tersedia"

### 2. Menambah Pelanggan
- Buka tab "Pelanggan"
- Klik tombol "Tambah Pelanggan"
- Isi data pelanggan:
  - Nama Lengkap
  - Nomor Telepon
  - Alamat Lengkap
  - NIK (16 digit sesuai regulasi Indonesia)
- Klik "Simpan"
- Pelanggan akan tersimpan dan muncul di tabel

### 3. Membuat Transaksi Rental
- Buka tab "Transaksi Rental"
- Klik tombol "Rental Baru"
- **Pilih Pelanggan** dari dropdown (list pelanggan terdaftar)
- **Pilih Kendaraan** dari dropdown (hanya kendaraan yang tersedia)
- Masukkan **Tanggal Mulai** rental
- Masukkan **Jumlah Hari** rental (contoh: 3)
- **Total biaya akan dihitung otomatis** (Tarif × Jumlah Hari)
- Klik "Simpan"
- Status rental: **"Aktif"**
- Kendaraan otomatis berubah status jadi **"Disewa"**

### 4. Menyelesaikan Rental
- Buka tab "Transaksi Rental"
- **Pilih rental yang aktif** di tabel
- Klik tombol **"Selesaikan Rental"**
- Konfirmasi penyelesaian
- Status rental berubah: **"Aktif" → "Selesai"**
- Kendaraan otomatis kembali: **"Disewa" → "Tersedia"**
- Total pendapatan bertambah

### 5. Membatalkan Rental
- Pilih rental yang aktif
- Klik tombol **"Batalkan Rental"**
- Konfirmasi pembatalan
- Status rental: **"Dibatalkan"**
- Kendaraan kembali tersedia

### 6. Melihat Laporan
- Buka tab "Laporan"
- Klik **"Refresh Laporan"** untuk update data terbaru
- Lihat statistik:
  - **Total Kendaraan** (Tersedia + Disewa)
  - **Kendaraan Tersedia**
  - **Kendaraan Disewa**
  - **Total Pelanggan** terdaftar
  - **Rental Aktif** (sedang berlangsung)
  - **Rental Selesai**
  - **Rental Dibatalkan**
  - **Total Pendapatan** (dari rental yang selesai)

## Penyimpanan Data

Data disimpan dalam **MySQL Database (XAMPP)** dengan struktur sebagai berikut:

### Database Info
- **Database Name**: `rental_kendaraan`
- **Host**: `localhost`
- **Port**: `3306` (default XAMPP)
- **User**: `root`
- **Password**: ` ` (kosong - default XAMPP)

### Tabel Database

#### 1. **vehicles** - Data Kendaraan
- `vehicle_id` - ID unik (VH0001, VH0002, ...)
- `vehicle_type` - Tipe (Mobil/Motor)
- `brand` - Merk kendaraan
- `model` - Model kendaraan
- `year` - Tahun produksi
- `license_plate` - Plat nomor
- `daily_rate` - Tarif harian (Rupiah)
- `available` - Status ketersediaan (true/false)
- `kondisi` - Kondisi kendaraan (Sangat Baik/Baik/Cukup/Perlu Perbaikan)
- `keterangan` - Keterangan tambahan
- **Mobil**: `number_of_doors`, `transmission_type`
- **Motor**: `engine_capacity`, `motorcycle_type`

#### 2. **customers** - Data Pelanggan
- `customer_id` - ID unik (CUS0001, CUS0002, ...)
- `name` - Nama lengkap
- `phone_number` - Nomor telepon
- `address` - Alamat lengkap
- `id_number` - NIK (16 digit)

#### 3. **rentals** - Data Transaksi Rental
- `rental_id` - ID unik (RNT0001, RNT0002, ...)
- `customer_id` - Foreign key ke customers
- `vehicle_id` - Foreign key ke vehicles
- `start_date` - Tanggal mulai rental
- `end_date` - Tanggal selesai rental
- `days` - Jumlah hari rental
- `total_cost` - Total biaya
- `status` - Status (Aktif/Selesai/Dibatalkan)

### Auto-Population

Aplikasi otomatis mengisi data awal saat pertama kali dijalankan:

**10 Pelanggan** dengan NIK Jakarta (16 digit):
- Ahmad Wijaya, Siti Rahayu, Budi Santoso, Dewi Lestari, Eko Prasetyo
- Fitri Handayani, Gunawan Susanto, Hani Maulida, Irfan Hidayat, Joko Widodo

**10 Mobil** dengan plat Jakarta (B):
- Toyota Avanza, Honda Jazz, Daihatsu Xenia, Suzuki Ertiga, Mitsubishi Xpander
- Nissan Livina, Mazda CX-5, Toyota Innova, Honda Brio, Daihatsu Terios

**10 Motor** dengan plat Jakarta (B):
- Yamaha NMAX, Honda PCX, Suzuki Address, Kawasaki Ninja 250, Yamaha Aerox
- Honda Vario, Suzuki NEX II, Yamaha MX King, Honda BeAT, Kawasaki KLX

### Akses Database via phpMyAdmin

1. Pastikan XAMPP MySQL running
2. Buka browser: **http://localhost/phpmyadmin**
3. Klik database **rental_kendaraan**
4. Browse tabel untuk lihat data

Data akan **tetap tersimpan** di MySQL server meskipun aplikasi ditutup.

## Sistem Requirements

- Java Development Kit (JDK) 8 atau lebih baru
- **MySQL Server 5.7 atau lebih baru**
- Apache NetBeans IDE (opsional, untuk development)
- Minimal 512 MB RAM
- 100 MB disk space (termasuk MySQL)

## Lisensi

Open Source - bebas digunakan untuk keperluan pembelajaran dan komersial.

## Kontributor

Aplikasi ini dibuat sebagai contoh aplikasi rental kendaraan menggunakan Java Swing dengan MySQL database.