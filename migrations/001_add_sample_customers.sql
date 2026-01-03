-- Migration: Add 10 Sample Customers
-- Date: 2026-01-03
-- Description: Menambahkan 10 data pelanggan sample dengan NIK Indonesia yang valid (16 digit)

-- ========================================
-- Migrasi: Tambah Data Pelanggan Sample
-- ========================================

-- Cek apakah data sudah ada
-- Jika sudah ada, skip insert

-- Insert 10 pelanggan dengan NIK Jakarta (DKI Jakarta = kode provinsi 31)
-- Format NIK Indonesia: 16 digit
-- Struktur: PPDDMMYYXXXXCCCC
--   PP = Provinsi (31 = DKI Jakarta)
--   DD = Kabupaten/Kota (71=Jakarta Selatan, 72=Jakarta Utara, 73=Jakarta Barat, 74=Jakarta Timur, 75=Jakarta Pusat)
--   MMDDYY = Tanggal Lahir (bulan, tanggal, tahun 2 digit)
--   XXXX = Nomor urut
--   CCCC = Kode unik

INSERT IGNORE INTO customers (customer_id, name, phone_number, address, id_number) VALUES
('CUS0001', 'Ahmad Wijaya', '081234567890', 'Jl. Sudirman No. 10, Jakarta Selatan', '3171012509850001'),
('CUS0002', 'Siti Rahayu', '082345678901', 'Jl. Gatot Subroto No. 25, Jakarta Pusat', '3172015608920002'),
('CUS0003', 'Budi Santoso', '083456789012', 'Jl. Thamrin No. 15, Jakarta Pusat', '3173021203880003'),
('CUS0004', 'Dewi Lestari', '084567890123', 'Jl. Kuningan Raya No. 8, Jakarta Selatan', '3174030107950004'),
('CUS0005', 'Eko Prasetyo', '085678901234', 'Jl. Rasuna Said No. 12, Jakarta Selatan', '3175042511900005'),
('CUS0006', 'Fitri Handayani', '086789012345', 'Jl. HR Rasuna Said No. 20, Jakarta Selatan', '3171056402930006'),
('CUS0007', 'Gunawan Susanto', '087890123456', 'Jl. Casablanca No. 5, Jakarta Selatan', '3172061810870007'),
('CUS0008', 'Hani Maulida', '088901234567', 'Jl. TB Simatupang No. 30, Jakarta Selatan', '3173072203910008'),
('CUS0009', 'Irfan Hidayat', '089012345678', 'Jl. Kemang Raya No. 7, Jakarta Selatan', '3174081512940009'),
('CUS0010', 'Joko Widodo', '081123456789', 'Jl. Senopati No. 18, Jakarta Selatan', '3175090608890010');

-- Verifikasi hasil insert
SELECT COUNT(*) as 'Total Customers' FROM customers;
SELECT * FROM customers;
