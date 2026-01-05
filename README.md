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

1. **Install MySQL Server** (lihat [MYSQL_SETUP.md](MYSQL_SETUP.md) untuk panduan lengkap)
2. **Java JDK 8 atau lebih baru**
3. **Apache NetBeans** (opsional)

### Setup Database

Aplikasi akan otomatis membuat database dan tabel saat pertama kali dijalankan. Pastikan:
- MySQL server sudah running
- Default user: `root`, password: kosong (atau sesuaikan di `DatabaseConnection.java`)

Lihat dokumentasi lengkap di [MYSQL_SETUP.md](MYSQL_SETUP.md)

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

### 1. Menambah Kendaraan
- Buka tab "Kendaraan"
- Klik tombol "Tambah Kendaraan"
- Pilih tipe kendaraan (Mobil/Motor)
- Isi semua informasi yang diperlukan
- Klik "Simpan"

### 2. Menambah Pelanggan
- Buka tab "Pelanggan"
- Klik tombol "Tambah Pelanggan"
- Isi data pelanggan (nama, telepon, alamat, no. KTP)
- Klik "Simpan"

### 3. Membuat Transaksi Rental
- Buka tab "Transaksi Rental"
- Klik tombol "Rental Baru"
- Pilih pelanggan dan kendaraan yang tersedia
- Masukkan tanggal mulai dan jumlah hari
- Total biaya akan dihitung otomatis
- Klik "Simpan"

### 4. Menyelesaikan Rental
- Buka tab "Transaksi Rental"
- Pilih rental yang aktif
- Klik "Selesaikan Rental"
- Kendaraan akan kembali tersedia

### 5. Melihat Laporan
- Buka tab "Laporan"
- Klik "Refresh Laporan"
- Lihat statistik dan total pendapatan

## Penyimpanan Data

Data disimpan dalam **MySQL Database** dengan struktur sebagai berikut:
- Database: `rental_kendaraan`
- Tabel: `vehicles`, `customers`, `rentals`

Aplikasi otomatis membuat database dan tabel saat pertama kali dijalankan, serta mengisi data awal:
- **10 Mobil** dengan plat Jakarta (B 1234 ABC, dst)
- **10 Motor** dengan plat Jakarta (B 5678 AAA, dst)

Data akan tetap tersimpan di MySQL server.

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