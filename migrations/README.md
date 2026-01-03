# Database Migrations

Folder ini berisi file-file migrasi database untuk aplikasi Rental Kendaraan.

## Daftar Migrasi

### 001_add_sample_customers.sql
**Tanggal**: 2026-01-03  
**Deskripsi**: Menambahkan 10 data pelanggan sample dengan NIK Indonesia yang valid (16 digit)

**Detail NIK:**
- Format: 16 digit sesuai regulasi Indonesia
- Struktur: `PPDDMMYYXXXXCCCC`
  - `PP` = Kode Provinsi (31 = DKI Jakarta)
  - `DD` = Kode Kabupaten/Kota
    - 71 = Jakarta Selatan
    - 72 = Jakarta Utara
    - 73 = Jakarta Barat
    - 74 = Jakarta Timur
    - 75 = Jakarta Pusat
  - `MMDDYY` = Tanggal Lahir (Bulan-Tanggal-Tahun 2 digit)
  - `XXXX` = Nomor Urut
  - `CCCC` = Kode Unik

**Data Pelanggan:**
1. Ahmad Wijaya - NIK: 3171012509850001 (Jakarta Selatan, 25 Sept 1985)
2. Siti Rahayu - NIK: 3172015608920002 (Jakarta Utara, 15 Aug 1992)
3. Budi Santoso - NIK: 3173021203880003 (Jakarta Barat, 12 Mar 1988)
4. Dewi Lestari - NIK: 3174030107950004 (Jakarta Timur, 01 Jul 1995)
5. Eko Prasetyo - NIK: 3175042511900005 (Jakarta Pusat, 25 Nov 1990)
6. Fitri Handayani - NIK: 3171056402930006 (Jakarta Selatan, 24 Feb 1993)
7. Gunawan Susanto - NIK: 3172061810870007 (Jakarta Utara, 18 Oct 1987)
8. Hani Maulida - NIK: 3173072203910008 (Jakarta Barat, 22 Mar 1991)
9. Irfan Hidayat - NIK: 3174081512940009 (Jakarta Timur, 15 Dec 1994)
10. Joko Widodo - NIK: 3175090608890010 (Jakarta Pusat, 06 Aug 1989)

## Cara Menjalankan Migrasi

### Opsi 1: Otomatis (Recommended)
Aplikasi akan otomatis menjalankan populasi data saat pertama kali dijalankan.

```bash
# Run aplikasi
java -jar dist/AplikasiRentalKendaraan.jar
```

### Opsi 2: Manual via MySQL Command Line
```bash
mysql -u root -p rental_kendaraan < migrations/001_add_sample_customers.sql
```

### Opsi 3: Manual via phpMyAdmin (XAMPP)
1. Buka phpMyAdmin (http://localhost/phpmyadmin)
2. Pilih database `rental_kendaraan`
3. Klik tab "SQL"
4. Copy-paste isi file migrasi
5. Klik "Go"

### Opsi 4: Manual via MySQL Workbench
1. Buka MySQL Workbench
2. Connect ke server
3. Pilih database `rental_kendaraan`
4. File → Open SQL Script
5. Pilih file migrasi
6. Execute (⚡ icon atau Ctrl+Shift+Enter)

## Verifikasi

Setelah menjalankan migrasi, verifikasi dengan:

```sql
-- Cek jumlah pelanggan
SELECT COUNT(*) FROM customers;

-- Lihat semua pelanggan
SELECT * FROM customers;

-- Cek pelanggan dengan NIK Jakarta Selatan (kode 3171)
SELECT * FROM customers WHERE id_number LIKE '3171%';
```

## Rollback

Untuk menghapus data pelanggan sample:

```sql
DELETE FROM customers WHERE customer_id IN (
    'CUS0001', 'CUS0002', 'CUS0003', 'CUS0004', 'CUS0005',
    'CUS0006', 'CUS0007', 'CUS0008', 'CUS0009', 'CUS0010'
);
```

## Catatan

- Migrasi menggunakan `INSERT IGNORE` sehingga aman dijalankan multiple kali
- Jika customer_id sudah ada, data tidak akan di-insert ulang
- NIK mengikuti format standar Indonesia (16 digit)
- Semua pelanggan berada di wilayah DKI Jakarta
- Alamat menggunakan jalan-jalan terkenal di Jakarta
