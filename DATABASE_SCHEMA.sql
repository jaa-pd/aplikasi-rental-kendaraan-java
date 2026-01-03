-- Database: rental_kendaraan
-- Encoding: UTF-8
-- Dibuat otomatis oleh aplikasi

-- ========================================
-- Tabel: vehicles (Kendaraan)
-- ========================================
CREATE TABLE IF NOT EXISTS vehicles (
    vehicle_id VARCHAR(20) PRIMARY KEY,
    vehicle_type VARCHAR(20) NOT NULL,           -- 'Mobil' atau 'Motor'
    brand VARCHAR(50) NOT NULL,                   -- Merk kendaraan
    model VARCHAR(50) NOT NULL,                   -- Model kendaraan
    year INT NOT NULL,                            -- Tahun produksi
    license_plate VARCHAR(20) NOT NULL,           -- Plat nomor
    daily_rate DOUBLE NOT NULL,                   -- Tarif harian (Rp)
    available BOOLEAN DEFAULT TRUE,               -- Status ketersediaan
    kondisi VARCHAR(50),                          -- Kondisi kendaraan
    keterangan TEXT,                              -- Keterangan tambahan
    -- Khusus Mobil
    number_of_doors INT,                          -- Jumlah pintu
    transmission_type VARCHAR(20),                -- Manual/Otomatis
    -- Khusus Motor
    engine_capacity INT,                          -- Kapasitas mesin (cc)
    motorcycle_type VARCHAR(20)                   -- Matic/Sport/Bebek
);

-- ========================================
-- Tabel: customers (Pelanggan)
-- ========================================
CREATE TABLE IF NOT EXISTS customers (
    customer_id VARCHAR(20) PRIMARY KEY,
    name VARCHAR(100) NOT NULL,                   -- Nama pelanggan
    phone_number VARCHAR(20) NOT NULL,            -- Nomor telepon
    address TEXT NOT NULL,                        -- Alamat
    id_number VARCHAR(50) NOT NULL                -- Nomor KTP
);

-- ========================================
-- Tabel: rentals (Transaksi Rental)
-- ========================================
CREATE TABLE IF NOT EXISTS rentals (
    rental_id VARCHAR(20) PRIMARY KEY,
    customer_id VARCHAR(20) NOT NULL,
    vehicle_id VARCHAR(20) NOT NULL,
    start_date DATE NOT NULL,                     -- Tanggal mulai
    end_date DATE NOT NULL,                       -- Tanggal selesai
    days INT NOT NULL,                            -- Jumlah hari
    total_cost DOUBLE NOT NULL,                   -- Total biaya (Rp)
    status VARCHAR(20) NOT NULL,                  -- Aktif/Selesai/Dibatalkan
    FOREIGN KEY (customer_id) REFERENCES customers(customer_id),
    FOREIGN KEY (vehicle_id) REFERENCES vehicles(vehicle_id)
);

-- ========================================
-- Data Awal: 10 Pelanggan (NIK Jakarta)
-- ========================================
-- NIK Indonesia: 16 digit (Format: PPDDMMYYXXXXCCCC)
-- PP = Kode Provinsi (31 = DKI Jakarta)
-- DD = Kode Kabupaten/Kota
-- MMDDYY = Tanggal Lahir
-- XXXX = Nomor Urut
-- CCCC = Kode Unik
INSERT INTO customers VALUES ('CUS0001', 'Ahmad Wijaya', '081234567890', 'Jl. Sudirman No. 10, Jakarta Selatan', '3171012509850001');
INSERT INTO customers VALUES ('CUS0002', 'Siti Rahayu', '082345678901', 'Jl. Gatot Subroto No. 25, Jakarta Pusat', '3172015608920002');
INSERT INTO customers VALUES ('CUS0003', 'Budi Santoso', '083456789012', 'Jl. Thamrin No. 15, Jakarta Pusat', '3173021203880003');
INSERT INTO customers VALUES ('CUS0004', 'Dewi Lestari', '084567890123', 'Jl. Kuningan Raya No. 8, Jakarta Selatan', '3174030107950004');
INSERT INTO customers VALUES ('CUS0005', 'Eko Prasetyo', '085678901234', 'Jl. Rasuna Said No. 12, Jakarta Selatan', '3175042511900005');
INSERT INTO customers VALUES ('CUS0006', 'Fitri Handayani', '086789012345', 'Jl. HR Rasuna Said No. 20, Jakarta Selatan', '3171056402930006');
INSERT INTO customers VALUES ('CUS0007', 'Gunawan Susanto', '087890123456', 'Jl. Casablanca No. 5, Jakarta Selatan', '3172061810870007');
INSERT INTO customers VALUES ('CUS0008', 'Hani Maulida', '088901234567', 'Jl. TB Simatupang No. 30, Jakarta Selatan', '3173072203910008');
INSERT INTO customers VALUES ('CUS0009', 'Irfan Hidayat', '089012345678', 'Jl. Kemang Raya No. 7, Jakarta Selatan', '3174081512940009');
INSERT INTO customers VALUES ('CUS0010', 'Joko Widodo', '081123456789', 'Jl. Senopati No. 18, Jakarta Selatan', '3175090608890010');

-- ========================================
-- Data Awal: 10 Mobil (Plat Jakarta - B)
-- ========================================
INSERT INTO vehicles VALUES ('VH0001', 'Mobil', 'Toyota', 'Avanza', 2019, 'B 1234 ABC', 300000, TRUE, 'Baik', 'Mobil keluarga Avanza dalam kondisi baik', 4, 'Manual', NULL, NULL);
INSERT INTO vehicles VALUES ('VH0002', 'Mobil', 'Honda', 'Jazz', 2020, 'B 2345 XYZ', 350000, TRUE, 'Sangat Baik', 'Mobil keluarga Jazz dalam kondisi sangat baik', 4, 'Otomatis', NULL, NULL);
INSERT INTO vehicles VALUES ('VH0003', 'Mobil', 'Daihatsu', 'Xenia', 2021, 'B 3456 DEF', 280000, TRUE, 'Baik', 'Mobil keluarga Xenia dalam kondisi baik', 4, 'Manual', NULL, NULL);
INSERT INTO vehicles VALUES ('VH0004', 'Mobil', 'Suzuki', 'Ertiga', 2022, 'B 4567 GHI', 320000, TRUE, 'Baik', 'Mobil keluarga Ertiga dalam kondisi baik', 4, 'Manual', NULL, NULL);
INSERT INTO vehicles VALUES ('VH0005', 'Mobil', 'Mitsubishi', 'Xpander', 2023, 'B 5678 JKL', 400000, TRUE, 'Sangat Baik', 'Mobil keluarga Xpander dalam kondisi sangat baik', 4, 'Otomatis', NULL, NULL);
INSERT INTO vehicles VALUES ('VH0006', 'Mobil', 'Nissan', 'Livina', 2019, 'B 6789 MNO', 300000, TRUE, 'Baik', 'Mobil keluarga Livina dalam kondisi baik', 4, 'Manual', NULL, NULL);
INSERT INTO vehicles VALUES ('VH0007', 'Mobil', 'Mazda', 'CX-5', 2020, 'B 7890 PQR', 500000, TRUE, 'Sangat Baik', 'Mobil keluarga CX-5 dalam kondisi sangat baik', 4, 'Otomatis', NULL, NULL);
INSERT INTO vehicles VALUES ('VH0008', 'Mobil', 'Toyota', 'Innova', 2021, 'B 8901 STU', 450000, TRUE, 'Baik', 'Mobil keluarga Innova dalam kondisi baik', 4, 'Manual', NULL, NULL);
INSERT INTO vehicles VALUES ('VH0009', 'Mobil', 'Honda', 'Brio', 2022, 'B 9012 VWX', 250000, TRUE, 'Baik', 'Mobil keluarga Brio dalam kondisi baik', 4, 'Otomatis', NULL, NULL);
INSERT INTO vehicles VALUES ('VH0010', 'Mobil', 'Daihatsu', 'Terios', 2023, 'B 0123 YZA', 350000, TRUE, 'Sangat Baik', 'Mobil keluarga Terios dalam kondisi sangat baik', 4, 'Manual', NULL, NULL);

-- ========================================
-- Data Awal: 10 Motor (Plat Jakarta - B)
-- ========================================
INSERT INTO vehicles VALUES ('VH0011', 'Motor', 'Yamaha', 'NMAX', 2020, 'B 5678 AAA', 150000, TRUE, 'Baik', 'Motor Matic NMAX 155cc', NULL, NULL, 155, 'Matic');
INSERT INTO vehicles VALUES ('VH0012', 'Motor', 'Honda', 'PCX', 2021, 'B 6789 BBB', 160000, TRUE, 'Sangat Baik', 'Motor Matic PCX 160cc', NULL, NULL, 160, 'Matic');
INSERT INTO vehicles VALUES ('VH0013', 'Motor', 'Suzuki', 'Address', 2022, 'B 7890 CCC', 100000, TRUE, 'Baik', 'Motor Matic Address 110cc', NULL, NULL, 110, 'Matic');
INSERT INTO vehicles VALUES ('VH0014', 'Motor', 'Kawasaki', 'Ninja 250', 2023, 'B 8901 DDD', 250000, TRUE, 'Baik', 'Motor Sport Ninja 250 250cc', NULL, NULL, 250, 'Sport');
INSERT INTO vehicles VALUES ('VH0015', 'Motor', 'Yamaha', 'Aerox', 2020, 'B 9012 EEE', 140000, TRUE, 'Sangat Baik', 'Motor Matic Aerox 155cc', NULL, NULL, 155, 'Matic');
INSERT INTO vehicles VALUES ('VH0016', 'Motor', 'Honda', 'Vario', 2021, 'B 0123 FFF', 120000, TRUE, 'Baik', 'Motor Matic Vario 125cc', NULL, NULL, 125, 'Matic');
INSERT INTO vehicles VALUES ('VH0017', 'Motor', 'Suzuki', 'NEX II', 2022, 'B 1234 GGG', 80000, TRUE, 'Sangat Baik', 'Motor Bebek NEX II 115cc', NULL, NULL, 115, 'Bebek');
INSERT INTO vehicles VALUES ('VH0018', 'Motor', 'Yamaha', 'MX King', 2023, 'B 2345 HHH', 130000, TRUE, 'Baik', 'Motor Sport MX King 150cc', NULL, NULL, 150, 'Sport');
INSERT INTO vehicles VALUES ('VH0019', 'Motor', 'Honda', 'BeAT', 2020, 'B 3456 III', 100000, TRUE, 'Baik', 'Motor Matic BeAT 110cc', NULL, NULL, 110, 'Matic');
INSERT INTO vehicles VALUES ('VH0020', 'Motor', 'Kawasaki', 'KLX', 2021, 'B 4567 JJJ', 200000, TRUE, 'Sangat Baik', 'Motor Sport KLX 150cc', NULL, NULL, 150, 'Sport');
