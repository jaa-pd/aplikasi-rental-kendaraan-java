# Solusi Error: Unable to find a suitable driver

## Error yang Terjadi
```
Connection failed: Unable to find a suitable driver 
(jdbc:mysql://localhost:3306/rental_kendaraan?zeroDateTimeBehavior=CONVERT_TO_NULL 
using com.mysql.cj.jdbc.Driver)
```

## Penyebab
Error ini terjadi karena NetBeans Services panel tidak menemukan MySQL JDBC driver di repository driver-nya sendiri.

## Solusi Lengkap

### Solusi 1: Tambahkan MySQL Driver ke NetBeans (Recommended)

1. **Download MySQL Connector/J** (jika belum ada):
   - File sudah tersedia di folder `lib/mysql-connector-j-8.2.0.jar`

2. **Tambahkan Driver ke NetBeans:**
   - Buka NetBeans
   - Pilih menu **Tools** → **Options** (atau **NetBeans** → **Preferences** di Mac)
   - Pilih tab **Java** → **Libraries**
   - Klik **Add Library...** atau **New Library...**
   - Beri nama: `MySQL Connector J 8.2.0`
   - Klik **Add JAR/Folder...**
   - Navigasi ke folder project → `lib` → pilih `mysql-connector-j-8.2.0.jar`
   - Klik **OK**

3. **Atau, tambahkan driver langsung di Services panel:**
   - Buka tab **Services** (Ctrl+5 atau Cmd+5)
   - Expand **Databases**
   - Klik kanan pada **Drivers** → **New Driver...**
   - Klik **Add...**
   - Pilih file `lib/mysql-connector-j-8.2.0.jar` dari folder project
   - Driver Class akan otomatis terdeteksi: `com.mysql.cj.jdbc.Driver`
   - Beri nama: `MySQL Connector/J 8.2`
   - Klik **OK**

4. **Buat Koneksi Database:**
   - Klik kanan pada **Drivers** → cari driver yang baru ditambahkan
   - Klik kanan → **Connect Using...**
   - Database URL: `jdbc:mysql://localhost:3306/rental_kendaraan`
   - Username: `root`
   - Password: (kosongkan atau sesuai XAMPP)
   - Klik **Test Connection**
   - Jika berhasil, klik **OK**

### Solusi 2: Jalankan Aplikasi Langsung (Tidak perlu koneksi manual di NetBeans)

Aplikasi sudah dikonfigurasi dengan benar. Anda bisa langsung menjalankan aplikasi:

1. **Pastikan MySQL/XAMPP sudah running**:
   - Buka XAMPP Control Panel
   - Start MySQL

2. **Run aplikasi dari NetBeans**:
   - Klik kanan pada project → **Run**
   - Atau tekan **F6**

3. **Aplikasi akan otomatis**:
   - Membuat database `rental_kendaraan` jika belum ada
   - Membuat tabel-tabel yang diperlukan
   - Mengisi 20 kendaraan sample (10 mobil + 10 motor)

### Solusi 3: Jalankan dari JAR File

Jika ingin menjalankan aplikasi tanpa NetBeans:

```bash
# Build dulu
cd /path/to/project
javac -cp "lib/*" -d build/classes -sourcepath src src/aplikasirental/*/*.java
jar cfm dist/AplikasiRentalKendaraan.jar manifest.mf -C build/classes .

# Copy library ke folder dist
cp -r lib dist/

# Run
cd dist
java -jar AplikasiRentalKendaraan.jar
```

Atau jika ada error classpath:
```bash
java -cp "AplikasiRentalKendaraan.jar:lib/*" aplikasirental.gui.MainFrame
```

## Verifikasi Konfigurasi

### Cek 1: File JAR ada?
```bash
ls -lh lib/mysql-connector-j-8.2.0.jar
```
Harus menampilkan file sekitar 2.4 MB.

### Cek 2: Project properties sudah benar?
File `nbproject/project.properties` harus mengandung:
```properties
file.reference.mysql-connector-j-8.2.0.jar=lib/mysql-connector-j-8.2.0.jar
javac.classpath=\
    ${file.reference.mysql-connector-j-8.2.0.jar}
run.classpath=${javac.classpath}:${build.classes.dir}
```

### Cek 3: Manifest sudah benar?
File `manifest.mf` harus mengandung:
```
Class-Path: lib/mysql-connector-j-8.2.0.jar
```

## Troubleshooting

### Error: ClassNotFoundException: com.mysql.cj.jdbc.Driver
**Penyebab**: Driver tidak ada di classpath saat runtime.

**Solusi**:
1. Pastikan folder `lib/` ada di folder yang sama dengan JAR aplikasi
2. Jalankan dengan: `java -cp "AplikasiRentalKendaraan.jar:lib/*" aplikasirental.gui.MainFrame`

### Error: Access denied for user 'root'@'localhost'
**Penyebab**: Password MySQL salah.

**Solusi**:
1. Buka `src/aplikasirental/manager/DatabaseConnection.java`
2. Update line 8:
   ```java
   private static final String PASSWORD = "password_anda";
   ```

### Error: Communications link failure
**Penyebab**: MySQL server tidak running.

**Solusi**:
1. Buka XAMPP Control Panel
2. Start MySQL
3. Pastikan port 3306 tidak digunakan aplikasi lain

### Database tidak dibuat otomatis
**Penyebab**: User MySQL tidak punya privilege CREATE DATABASE.

**Solusi**:
1. Buat database manual di phpMyAdmin atau MySQL Workbench:
   ```sql
   CREATE DATABASE rental_kendaraan;
   ```
2. Atau jalankan query dari file `DATABASE_SCHEMA.sql`

## Catatan Penting

- **Untuk Testing di NetBeans Services Panel**: Perlu tambahkan driver ke NetBeans (Solusi 1)
- **Untuk Menjalankan Aplikasi**: Tidak perlu tambah driver ke NetBeans, cukup run aplikasi (Solusi 2)
- **MySQL Connector/J 8.2.0 kompatibel dengan**:
  - MySQL 5.7, 8.0, 8.1, 8.2
  - MariaDB 10.x, 11.x (yang digunakan XAMPP)

## Kontak

Jika masih ada masalah, sertakan:
1. Error message lengkap
2. Versi NetBeans yang digunakan
3. Versi MySQL/XAMPP yang digunakan
4. Hasil dari perintah: `java -version`
