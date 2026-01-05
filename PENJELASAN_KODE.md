# Penjelasan Kode Aplikasi Rental Kendaraan

Dokumen ini menjelaskan secara rinci dan detail setiap komponen kode dalam Aplikasi Rental Kendaraan untuk keperluan demonstrasi.

---

## Daftar Isi

1. [Arsitektur Aplikasi](#arsitektur-aplikasi)
2. [Model (Data Model)](#model-data-model)
3. [Manager (Business Logic)](#manager-business-logic)
4. [GUI (User Interface)](#gui-user-interface)
5. [NetBeans JForm Implementation](#netbeans-jform-implementation)
6. [Alur Kerja Aplikasi](#alur-kerja-aplikasi)

---

## Arsitektur Aplikasi

Aplikasi ini menggunakan arsitektur **3-Layer** (Three-Tier Architecture):

```
┌─────────────────────────────────────┐
│   GUI Layer (Presentation)          │
│   - MainFrame                        │
│   - VehiclePanel, CustomerPanel     │
│   - RentalPanel, ReportPanel        │
└─────────────────────────────────────┘
              ↓↑
┌─────────────────────────────────────┐
│   Manager Layer (Business Logic)    │
│   - VehicleManager                   │
│   - CustomerManager                  │
│   - RentalManager                    │
│   - DataManager                      │
└─────────────────────────────────────┘
              ↓↑
┌─────────────────────────────────────┐
│   Model Layer (Data)                │
│   - Vehicle, Car, Motorcycle        │
│   - Customer                         │
│   - Rental                           │
└─────────────────────────────────────┘
```

**Keuntungan Arsitektur 3-Layer:**
- **Separation of Concerns**: Setiap layer memiliki tanggung jawab yang jelas
- **Maintainability**: Mudah untuk melakukan perubahan tanpa mempengaruhi layer lain
- **Reusability**: Komponen dapat digunakan kembali
- **Testability**: Setiap layer dapat ditest secara terpisah

---

## Model (Data Model)

Model merepresentasikan struktur data dalam aplikasi. Semua class model berada di package `aplikasirental.model`.

### 1. Vehicle.java (Abstract Class)

**Tujuan**: Class abstract yang menjadi parent untuk semua jenis kendaraan.

**Atribut Utama**:
```java
private String vehicleId;      // ID unik kendaraan (contoh: VH0001)
private String brand;           // Merk kendaraan (Toyota, Honda, dll)
private String model;           // Model kendaraan (Avanza, NMAX, dll)
private int year;              // Tahun pembuatan
private String licensePlate;   // Plat nomor (B 1234 XYZ)
private double dailyRate;      // Tarif sewa per hari (Rupiah)
private boolean available;     // Status ketersediaan (true/false)
private String kondisi;        // Kondisi kendaraan (Sangat Baik, Baik, dll)
private String keterangan;     // Catatan tambahan
```

**Method Utama**:
```java
// Abstract method - harus diimplementasikan oleh subclass
public abstract String getVehicleType();

// Getter dan Setter untuk semua atribut
public String getVehicleId() { return vehicleId; }
public void setVehicleId(String vehicleId) { this.vehicleId = vehicleId; }
// ... dst

// toString untuk menampilkan informasi kendaraan
@Override
public String toString() {
    return String.format("%s %s (%d) - %s", brand, model, year, licensePlate);
}
```

**Penjelasan**:
- Menggunakan **encapsulation** dengan atribut private dan accessor methods
- Class abstract memastikan tidak bisa dibuat instance Vehicle langsung
- Method abstract `getVehicleType()` memaksa subclass untuk implementasi

### 2. Car.java (Mobil)

**Tujuan**: Merepresentasikan kendaraan jenis mobil dengan atribut spesifik.

**Atribut Tambahan**:
```java
private int numberOfDoors;        // Jumlah pintu (2, 4, dst)
private String transmissionType;  // Tipe transmisi (Manual/Otomatis)
```

**Constructor**:
```java
public Car(String vehicleId, String brand, String model, int year, 
           String licensePlate, double dailyRate, String kondisi, 
           String keterangan, int numberOfDoors, String transmissionType) {
    // Memanggil constructor parent (Vehicle)
    super(vehicleId, brand, model, year, licensePlate, dailyRate, kondisi, keterangan);
    this.numberOfDoors = numberOfDoors;
    this.transmissionType = transmissionType;
}
```

**Implementasi Method Abstract**:
```java
@Override
public String getVehicleType() {
    return "Mobil";  // Menandakan ini adalah mobil
}
```

**Penjelasan**:
- Mewarisi semua atribut dan method dari Vehicle
- Menambahkan atribut khusus untuk mobil
- Implementasi polymorphism melalui method `getVehicleType()`

### 3. Motorcycle.java (Motor)

**Tujuan**: Merepresentasikan kendaraan jenis motor dengan atribut spesifik.

**Atribut Tambahan**:
```java
private int engineCapacity;      // Kapasitas mesin dalam cc
private String motorcycleType;   // Tipe motor (Matic/Sport/Bebek)
```

**Constructor**:
```java
public Motorcycle(String vehicleId, String brand, String model, int year,
                  String licensePlate, double dailyRate, String kondisi,
                  String keterangan, int engineCapacity, String motorcycleType) {
    super(vehicleId, brand, model, year, licensePlate, dailyRate, kondisi, keterangan);
    this.engineCapacity = engineCapacity;
    this.motorcycleType = motorcycleType;
}
```

**Implementasi Method Abstract**:
```java
@Override
public String getVehicleType() {
    return "Motor";  // Menandakan ini adalah motor
}
```

### 4. Customer.java

**Tujuan**: Merepresentasikan data pelanggan yang melakukan rental.

**Atribut**:
```java
private String customerId;    // ID unik pelanggan (CUS0001)
private String name;          // Nama lengkap
private String phoneNumber;   // Nomor telepon
private String address;       // Alamat
private String idNumber;      // Nomor KTP
```

**Constructor dan Methods**:
```java
public Customer(String customerId, String name, String phoneNumber, 
                String address, String idNumber) {
    this.customerId = customerId;
    this.name = name;
    this.phoneNumber = phoneNumber;
    this.address = address;
    this.idNumber = idNumber;
}

// Getter dan Setter untuk semua atribut
// toString untuk ComboBox display
@Override
public String toString() {
    return name + " (" + customerId + ")";
}
```

**Penjelasan**:
- Method `toString()` digunakan untuk menampilkan pelanggan di ComboBox
- Semua atribut private dengan accessor methods (encapsulation)

### 5. Rental.java

**Tujuan**: Merepresentasikan transaksi rental kendaraan.

**Atribut**:
```java
private String rentalId;      // ID rental (RNT0001)
private Customer customer;    // Pelanggan yang rental
private Vehicle vehicle;      // Kendaraan yang dirental
private Date startDate;       // Tanggal mulai rental
private Date endDate;         // Tanggal selesai rental
private int days;            // Durasi rental (hari)
private double totalCost;    // Total biaya rental
private String status;       // Status (Aktif/Selesai/Dibatalkan)
```

**Constructor**:
```java
public Rental(String rentalId, Customer customer, Vehicle vehicle,
              Date startDate, Date endDate, int days) {
    this.rentalId = rentalId;
    this.customer = customer;
    this.vehicle = vehicle;
    this.startDate = startDate;
    this.endDate = endDate;
    this.days = days;
    
    // Hitung total biaya otomatis
    this.totalCost = days * vehicle.getDailyRate();
    
    // Set status awal
    this.status = "Aktif";
    
    // Set kendaraan menjadi tidak tersedia
    vehicle.setAvailable(false);
}
```

**Method Penting**:
```java
// Menyelesaikan rental
public void completeRental() {
    this.status = "Selesai";
    vehicle.setAvailable(true);  // Kembalikan kendaraan
}

// Membatalkan rental
public void cancelRental() {
    this.status = "Dibatalkan";
    vehicle.setAvailable(true);  // Kembalikan kendaraan
}
```

**Penjelasan**:
- Constructor otomatis menghitung total cost
- Method `completeRental()` dan `cancelRental()` mengubah status dan ketersediaan kendaraan
- Menggunakan komposisi (has-a relationship) dengan Customer dan Vehicle

---

## Manager (Business Logic)

Manager layer menangani logika bisnis dan manipulasi data. Semua class manager berada di package `aplikasirental.manager`.

### 1. DataManager.java

**Tujuan**: Mengelola data dalam memori (in-memory storage) untuk semua entitas.

**Atribut**:
```java
private List<Vehicle> vehicles;      // List semua kendaraan
private List<Customer> customers;    // List semua pelanggan
private List<Rental> rentals;        // List semua rental
```

**Constructor**:
```java
public DataManager() {
    vehicles = new ArrayList<>();
    customers = new ArrayList<>();
    rentals = new ArrayList<>();
    initializeData();  // Inisialisasi data awal
}
```

**Method initializeData()**:
```java
private void initializeData() {
    // Inisialisasi 10 mobil dengan data lengkap
    vehicles.add(new Car("VH0001", "Toyota", "Avanza", 2020, "B 1234 AB", 
                        350000, "Sangat Baik", "Unit baru, terawat", 4, "Manual"));
    // ... 9 mobil lainnya
    
    // Inisialisasi 10 motor dengan data lengkap
    vehicles.add(new Motorcycle("VH0011", "Yamaha", "NMAX", 2022, "B 5001 XY",
                                150000, "Baik", "Unit terawat", 155, "Matic"));
    // ... 9 motor lainnya
}
```

**Penjelasan**:
- Menggunakan ArrayList untuk storage dinamis
- Method `initializeData()` memberikan data dummy untuk testing
- Semua data disimpan dalam memori (tidak persistent)

### 2. VehicleManager.java

**Tujuan**: Mengelola operasi CRUD untuk kendaraan.

**Constructor**:
```java
public VehicleManager(DataManager dataManager) {
    this.dataManager = dataManager;
}
```

**Method CRUD**:
```java
// CREATE - Menambah kendaraan baru
public void addVehicle(Vehicle vehicle) {
    dataManager.getVehicles().add(vehicle);
}

// READ - Mendapatkan semua kendaraan
public List<Vehicle> getAllVehicles() {
    return dataManager.getVehicles();
}

// READ - Mendapatkan kendaraan tersedia saja
public List<Vehicle> getAvailableVehicles() {
    return dataManager.getVehicles().stream()
            .filter(Vehicle::isAvailable)
            .collect(Collectors.toList());
}

// UPDATE - Mengupdate kendaraan
public void updateVehicle(int index, Vehicle vehicle) {
    dataManager.getVehicles().set(index, vehicle);
}

// DELETE - Menghapus kendaraan
public void deleteVehicle(int index) {
    dataManager.getVehicles().remove(index);
}
```

**Method Helper**:
```java
// Generate ID otomatis untuk kendaraan baru
public String generateVehicleId() {
    int count = dataManager.getVehicles().size() + 1;
    return String.format("VH%04d", count);  // Format: VH0001, VH0002, dst
}
```

**Penjelasan**:
- Menggunakan Stream API untuk filtering (Java 8+)
- Method `generateVehicleId()` membuat ID dengan format konsisten
- Semua operasi delegasi ke DataManager

### 3. CustomerManager.java

**Tujuan**: Mengelola operasi CRUD untuk pelanggan.

**Method Utama**:
```java
// CREATE
public void addCustomer(Customer customer) {
    dataManager.getCustomers().add(customer);
}

// READ
public List<Customer> getAllCustomers() {
    return dataManager.getCustomers();
}

// UPDATE
public void updateCustomer(int index, Customer customer) {
    dataManager.getCustomers().set(index, customer);
}

// DELETE
public void deleteCustomer(int index) {
    dataManager.getCustomers().remove(index);
}

// HELPER - Generate ID
public String generateCustomerId() {
    int count = dataManager.getCustomers().size() + 1;
    return String.format("CUS%04d", count);  // Format: CUS0001, CUS0002
}
```

### 4. RentalManager.java

**Tujuan**: Mengelola transaksi rental dan perhitungan revenue.

**Constructor**:
```java
public RentalManager(DataManager dataManager, VehicleManager vehicleManager) {
    this.dataManager = dataManager;
    this.vehicleManager = vehicleManager;
}
```

**Method Operasi Rental**:
```java
// CREATE - Membuat rental baru
public void addRental(Rental rental) {
    dataManager.getRentals().add(rental);
}

// READ - Mendapatkan semua rental
public List<Rental> getAllRentals() {
    return dataManager.getRentals();
}

// READ - Mendapatkan rental aktif
public List<Rental> getActiveRentals() {
    return dataManager.getRentals().stream()
            .filter(r -> r.getStatus().equals("Aktif"))
            .collect(Collectors.toList());
}

// UPDATE - Menyelesaikan rental
public void completeRental(int index) {
    Rental rental = dataManager.getRentals().get(index);
    rental.completeRental();  // Ubah status dan kembalikan kendaraan
}

// UPDATE - Membatalkan rental
public void cancelRental(int index) {
    Rental rental = dataManager.getRentals().get(index);
    rental.cancelRental();  // Ubah status dan kembalikan kendaraan
}
```

**Method Perhitungan**:
```java
// Menghitung total revenue dari rental yang selesai
public double getTotalRevenue() {
    return dataManager.getRentals().stream()
            .filter(r -> r.getStatus().equals("Selesai"))
            .mapToDouble(Rental::getTotalCost)
            .sum();
}

// Generate ID rental
public String generateRentalId() {
    int count = dataManager.getRentals().size() + 1;
    return String.format("RNT%04d", count);  // Format: RNT0001, RNT0002
}
```

**Penjelasan**:
- Menggunakan Stream API untuk filtering dan aggregation
- Method `getTotalRevenue()` hanya menghitung rental dengan status "Selesai"
- Dependency injection dengan VehicleManager untuk akses data kendaraan

---

## GUI (User Interface)

GUI layer menangani tampilan dan interaksi user. Menggunakan **NetBeans JForm** untuk visual design.

### 1. MainFrame.java

**Tujuan**: Window utama aplikasi yang mengkoordinasikan semua panel.

**Struktur**:
```java
public class MainFrame extends JFrame {
    // Manager instances
    private DataManager dataManager;
    private VehicleManager vehicleManager;
    private CustomerManager customerManager;
    private RentalManager rentalManager;
    
    // GUI Panels
    private JTabbedPane tabbedPane;
    private VehiclePanel vehiclePanel;
    private CustomerPanel customerPanel;
    private RentalPanel rentalPanel;
    private ReportPanel reportPanel;
}
```

**Constructor**:
```java
public MainFrame() {
    // 1. Initialize managers
    dataManager = new DataManager();
    vehicleManager = new VehicleManager(dataManager);
    customerManager = new CustomerManager(dataManager);
    rentalManager = new RentalManager(dataManager, vehicleManager);
    
    // 2. Setup frame
    setTitle("Aplikasi Rental Kendaraan");
    setSize(1000, 600);
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setLocationRelativeTo(null);  // Center di screen
    
    // 3. Create tabbed pane
    tabbedPane = new JTabbedPane();
    
    // 4. Create panels dengan dependency injection
    vehiclePanel = new VehiclePanel(this, vehicleManager);
    customerPanel = new CustomerPanel(this, customerManager);
    rentalPanel = new RentalPanel(this, rentalManager, vehicleManager, customerManager);
    reportPanel = new ReportPanel(this, vehicleManager, customerManager, rentalManager);
    
    // 5. Set callback untuk sinkronisasi
    rentalPanel.setOnRentalChanged(() -> vehiclePanel.refreshTable());
    
    // 6. Add tabs
    tabbedPane.addTab("Kendaraan", vehiclePanel);
    tabbedPane.addTab("Pelanggan", customerPanel);
    tabbedPane.addTab("Transaksi Rental", rentalPanel);
    tabbedPane.addTab("Laporan", reportPanel);
    
    add(tabbedPane);
    
    // 7. Load initial data
    vehiclePanel.refreshTable();
    customerPanel.refreshTable();
    rentalPanel.refreshTable();
}
```

**Main Method**:
```java
public static void main(String[] args) {
    try {
        // Set Look and Feel sesuai sistem operasi
        UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
    } catch (Exception e) {
        e.printStackTrace();
    }
    
    // Jalankan aplikasi di Event Dispatch Thread
    SwingUtilities.invokeLater(() -> {
        MainFrame frame = new MainFrame();
        frame.setVisible(true);
    });
}
```

**Penjelasan**:
- Menggunakan **Dependency Injection** untuk pass managers ke panels
- **Callback pattern** untuk sinkronisasi (rental changes → refresh vehicle table)
- `SwingUtilities.invokeLater()` untuk thread safety di Swing
- `setLocationRelativeTo(null)` untuk center window

### 2. VehiclePanel.java (dengan JForm)

**Tujuan**: Panel untuk manajemen kendaraan (CRUD operations).

**Struktur Class**:
```java
public class VehiclePanel extends javax.swing.JPanel {
    // Business logic
    private VehicleManager vehicleManager;
    private DefaultTableModel vehicleTableModel;
    private JFrame parentFrame;
    
    // GUI Components (generated by NetBeans)
    private javax.swing.JLabel titleLabel;
    private javax.swing.JTable vehicleTable;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JPanel buttonPanel;
    private javax.swing.JButton btnTambah;
    private javax.swing.JButton btnEdit;
    private javax.swing.JButton btnHapus;
    private javax.swing.JButton btnRefresh;
}
```

**Constructor**:
```java
public VehiclePanel(JFrame parentFrame, VehicleManager vehicleManager) {
    this.parentFrame = parentFrame;
    this.vehicleManager = vehicleManager;
    initComponents();      // Generated by NetBeans Form Editor
    setupTableModel();     // Custom setup
}
```

**Setup Table Model**:
```java
private void setupTableModel() {
    // Get model yang sudah dibuat oleh initComponents() dari .form file
    vehicleTableModel = (DefaultTableModel) vehicleTable.getModel();
    vehicleTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
}
```

**Refresh Table**:
```java
public void refreshTable() {
    if (vehicleTableModel == null || vehicleManager == null) return;
    
    // Clear existing data
    vehicleTableModel.setRowCount(0);
    
    // Load data dari manager
    List<Vehicle> vehicles = vehicleManager.getAllVehicles();
    
    // Populate table
    for (Vehicle v : vehicles) {
        Object[] row = {
            v.getVehicleId(),
            v.getBrand(),
            v.getModel(),
            v.getYear(),
            v.getLicensePlate(),
            v.getVehicleType(),
            v.getKondisi() != null ? v.getKondisi() : "Baik",
            v.getKeterangan() != null ? v.getKeterangan() : "",
            String.format("Rp %.0f", v.getDailyRate()),
            v.isAvailable() ? "Tersedia" : "Disewa"
        };
        vehicleTableModel.addRow(row);
    }
}
```

**Event Handler - Tambah Kendaraan**:
```java
private void btnTambahActionPerformed(java.awt.event.ActionEvent evt) {
    showAddVehicleDialog();
}

private void showAddVehicleDialog() {
    // 1. Create dialog
    JDialog dialog = new JDialog(parentFrame, "Tambah Kendaraan", true);
    dialog.setSize(500, 450);
    dialog.setLocationRelativeTo(parentFrame);
    
    // 2. Create form panel dengan GridLayout
    JPanel formPanel = new JPanel(new GridLayout(0, 2, 10, 10));
    
    // 3. Add fields
    JComboBox<String> typeCombo = new JComboBox<>(new String[]{"Mobil", "Motor"});
    JTextField idField = new JTextField(vehicleManager.generateVehicleId());
    idField.setEditable(false);  // Auto-generated, tidak bisa diedit
    JTextField brandField = new JTextField();
    JTextField modelField = new JTextField();
    // ... field lainnya
    
    // 4. Dynamic fields berdasarkan tipe
    typeCombo.addActionListener(e -> {
        boolean isCar = typeCombo.getSelectedItem().equals("Mobil");
        // Show/hide car-specific atau motor-specific fields
        doorsLabel.setVisible(isCar);
        doorsField.setVisible(isCar);
        engineLabel.setVisible(!isCar);
        engineField.setVisible(!isCar);
    });
    
    // 5. Save button logic
    JButton saveButton = new JButton("Simpan");
    saveButton.addActionListener(e -> {
        try {
            // Validasi input
            if (brand.isEmpty() || model.isEmpty() || plate.isEmpty()) {
                JOptionPane.showMessageDialog(dialog, "Semua field harus diisi!");
                return;
            }
            
            // Create vehicle object berdasarkan tipe
            Vehicle vehicle;
            if (typeCombo.getSelectedItem().equals("Mobil")) {
                vehicle = new Car(id, brand, model, year, plate, rate, 
                                kondisi, keterangan, doors, trans);
            } else {
                vehicle = new Motorcycle(id, brand, model, year, plate, rate,
                                        kondisi, keterangan, engine, motoType);
            }
            
            // Save to manager
            vehicleManager.addVehicle(vehicle);
            
            // Refresh table
            refreshTable();
            
            // Close dialog
            dialog.dispose();
            
            // Show success message
            JOptionPane.showMessageDialog(parentFrame, "Kendaraan berhasil ditambahkan!");
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(dialog, "Format angka tidak valid!");
        }
    });
}
```

**Penjelasan**:
- Event handler dipanggil oleh NetBeans generated code
- Dialog menggunakan modal (blocking) dengan parameter `true`
- Dynamic UI: fields berubah sesuai jenis kendaraan
- Try-catch untuk handle input validation
- Polymorphism: Car atau Motorcycle dibuat berdasarkan pilihan user

### 3. CustomerPanel.java

**Tujuan**: Panel untuk manajemen pelanggan.

**Fitur Utama**:
- Tabel untuk menampilkan semua pelanggan
- Form tambah pelanggan dengan validasi
- Form edit pelanggan
- Hapus pelanggan dengan konfirmasi

**Implementasi Similar dengan VehiclePanel**:
```java
// Event handler untuk tombol tambah
private void btnTambahActionPerformed(java.awt.event.ActionEvent evt) {
    showAddCustomerDialog();
}

// Dialog lebih sederhana (tidak ada dynamic fields)
private void showAddCustomerDialog() {
    JDialog dialog = new JDialog(parentFrame, "Tambah Pelanggan", true);
    
    // Fields: ID, Nama, Telepon, Alamat, No. KTP
    JTextField idField = new JTextField(customerManager.generateCustomerId());
    idField.setEditable(false);
    JTextField nameField = new JTextField();
    JTextField phoneField = new JTextField();
    JTextField addressField = new JTextField();
    JTextField idNumberField = new JTextField();
    
    // Save logic
    saveButton.addActionListener(e -> {
        // Validasi
        if (name.isEmpty() || phone.isEmpty() || address.isEmpty() || idNumber.isEmpty()) {
            JOptionPane.showMessageDialog(dialog, "Semua field harus diisi!");
            return;
        }
        
        // Create dan save
        Customer customer = new Customer(id, name, phone, address, idNumber);
        customerManager.addCustomer(customer);
        refreshTable();
        dialog.dispose();
    });
}
```

### 4. RentalPanel.java

**Tujuan**: Panel untuk transaksi rental kendaraan.

**Fitur Khusus**:
- ComboBox untuk pilih pelanggan dan kendaraan
- Kalkulasi otomatis total biaya
- Filter kendaraan yang tersedia
- Status management (Aktif/Selesai/Dibatalkan)

**Callback Mechanism**:
```java
private Runnable onRentalChanged;

public void setOnRentalChanged(Runnable callback) {
    this.onRentalChanged = callback;
}

private void notifyRentalChanged() {
    if (onRentalChanged != null) {
        onRentalChanged.run();
    }
}
```

**Dialog Rental Baru**:
```java
private void showAddRentalDialog() {
    // 1. Validasi data tersedia
    List<Customer> customers = customerManager.getAllCustomers();
    List<Vehicle> availableVehicles = vehicleManager.getAvailableVehicles();
    
    if (customers.isEmpty()) {
        JOptionPane.showMessageDialog(parentFrame, "Belum ada pelanggan terdaftar!");
        return;
    }
    
    if (availableVehicles.isEmpty()) {
        JOptionPane.showMessageDialog(parentFrame, "Tidak ada kendaraan yang tersedia!");
        return;
    }
    
    // 2. Create ComboBox dengan data
    JComboBox<Customer> customerCombo = new JComboBox<>(
        customers.toArray(new Customer[0])
    );
    JComboBox<Vehicle> vehicleCombo = new JComboBox<>(
        availableVehicles.toArray(new Vehicle[0])
    );
    
    // 3. Auto-calculate total
    ActionListener calculateTotal = e -> {
        try {
            int days = Integer.parseInt(daysField.getText());
            Vehicle selected = (Vehicle) vehicleCombo.getSelectedItem();
            if (selected != null) {
                double total = days * selected.getDailyRate();
                totalField.setText(String.format("Rp %.0f", total));
            }
        } catch (NumberFormatException ex) {
            totalField.setText("0");
        }
    };
    
    // 4. Attach listeners
    daysField.addKeyListener(new KeyAdapter() {
        @Override
        public void keyReleased(KeyEvent e) {
            calculateTotal.actionPerformed(null);
        }
    });
    vehicleCombo.addActionListener(calculateTotal);
    
    // 5. Save rental
    saveButton.addActionListener(e -> {
        // Parse tanggal
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        Date startDate = sdf.parse(startDateField.getText());
        
        // Hitung end date
        Calendar cal = Calendar.getInstance();
        cal.setTime(startDate);
        cal.add(Calendar.DAY_OF_MONTH, days);
        Date endDate = cal.getTime();
        
        // Create rental
        Rental rental = new Rental(id, customer, vehicle, startDate, endDate, days);
        rentalManager.addRental(rental);
        
        // Refresh dan notify
        refreshTable();
        notifyRentalChanged();  // Ini akan refresh VehiclePanel
        
        dialog.dispose();
    });
}
```

**Complete Rental**:
```java
private void completeRental() {
    int selectedRow = rentalTable.getSelectedRow();
    if (selectedRow == -1) {
        JOptionPane.showMessageDialog(parentFrame, "Pilih rental yang akan diselesaikan!");
        return;
    }
    
    Rental rental = rentalManager.getAllRentals().get(selectedRow);
    if (!rental.getStatus().equals("Aktif")) {
        JOptionPane.showMessageDialog(parentFrame, "Rental ini sudah tidak aktif!");
        return;
    }
    
    int confirm = JOptionPane.showConfirmDialog(parentFrame, "Selesaikan rental ini?");
    if (confirm == JOptionPane.YES_OPTION) {
        rentalManager.completeRental(selectedRow);
        refreshTable();
        notifyRentalChanged();  // Update vehicle availability di VehiclePanel
    }
}
```

**Penjelasan**:
- **Observer pattern** via callback untuk komunikasi antar panel
- Real-time calculation menggunakan event listeners
- Date manipulation dengan Calendar API
- Validasi multi-layer (empty check, data availability, status check)

### 5. ReportPanel.java

**Tujuan**: Menampilkan statistik dan laporan aplikasi.

**Implementation**:
```java
public void refreshReport() {
    StringBuilder sb = new StringBuilder();
    sb.append("=== LAPORAN APLIKASI RENTAL KENDARAAN ===\n\n");
    
    // Get data from managers
    List<Vehicle> vehicles = vehicleManager.getAllVehicles();
    List<Customer> customers = customerManager.getAllCustomers();
    List<Rental> rentals = rentalManager.getAllRentals();
    List<Rental> activeRentals = rentalManager.getActiveRentals();
    
    // Statistik Kendaraan
    sb.append("Total Kendaraan: ").append(vehicles.size()).append("\n");
    long availableVehicles = vehicles.stream()
        .filter(Vehicle::isAvailable)
        .count();
    sb.append("Kendaraan Tersedia: ").append(availableVehicles).append("\n");
    sb.append("Kendaraan Disewa: ").append(vehicles.size() - availableVehicles).append("\n\n");
    
    // Statistik Pelanggan
    sb.append("Total Pelanggan: ").append(customers.size()).append("\n\n");
    
    // Statistik Rental
    sb.append("Total Transaksi Rental: ").append(rentals.size()).append("\n");
    sb.append("Rental Aktif: ").append(activeRentals.size()).append("\n");
    
    long completedRentals = rentals.stream()
        .filter(r -> r.getStatus().equals("Selesai"))
        .count();
    sb.append("Rental Selesai: ").append(completedRentals).append("\n");
    
    long cancelledRentals = rentals.stream()
        .filter(r -> r.getStatus().equals("Dibatalkan"))
        .count();
    sb.append("Rental Dibatalkan: ").append(cancelledRentals).append("\n\n");
    
    // Revenue
    double totalRevenue = rentalManager.getTotalRevenue();
    sb.append(String.format("Total Pendapatan: Rp %.0f\n", totalRevenue));
    
    // Display di JTextArea
    reportArea.setText(sb.toString());
}
```

**Penjelasan**:
- Menggunakan Stream API untuk aggregation dan counting
- StringBuilder untuk efficient string concatenation
- Format currency dengan `String.format()`
- Read-only display (JTextArea dengan `setEditable(false)`)

---

## NetBeans JForm Implementation

NetBeans JForm adalah visual GUI designer yang generate kode Java secara otomatis.

### Struktur JForm File

Setiap panel memiliki 2 file:
1. **`.java` file**: Kode Java dengan generated code sections
2. **`.form` file**: XML yang menyimpan design metadata

**Contoh: VehiclePanel.form (XML)**
```xml
<?xml version="1.0" encoding="UTF-8" ?>
<Form version="1.3" maxVersion="1.9" type="org.netbeans.modules.form.forminfo.JPanelFormInfo">
  <Layout>
    <DimensionLayout dim="0">
      <Group type="103" groupAlignment="0" attributes="0">
        <!-- Layout horizontal definitions -->
      </Group>
    </DimensionLayout>
    <DimensionLayout dim="1">
      <Group type="103" groupAlignment="0" attributes="0">
        <!-- Layout vertical definitions -->
      </Group>
    </DimensionLayout>
  </Layout>
  <SubComponents>
    <Component class="javax.swing.JLabel" name="titleLabel">
      <Properties>
        <Property name="font" type="java.awt.Font" editor="...">
          <Font name="Arial" size="18" style="1"/>
        </Property>
        <Property name="text" type="java.lang.String" value="Manajemen Kendaraan"/>
      </Properties>
    </Component>
    <!-- More components -->
  </SubComponents>
</Form>
```

### Generated Code Section

**Di VehiclePanel.java**:
```java
/**
 * This method is called from within the constructor to initialize the form.
 * WARNING: Do NOT modify this code. The content of this method is always
 * regenerated by the Form Editor.
 */
@SuppressWarnings("unchecked")
// <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
private void initComponents() {
    // NetBeans generated initialization code
    titleLabel = new javax.swing.JLabel();
    jScrollPane1 = new javax.swing.JScrollPane();
    vehicleTable = new javax.swing.JTable();
    buttonPanel = new javax.swing.JPanel();
    btnTambah = new javax.swing.JButton();
    // ... more components
    
    // Set properties
    titleLabel.setFont(new java.awt.Font("Arial", 1, 18));
    titleLabel.setText("Manajemen Kendaraan");
    
    // Set table model
    vehicleTable.setModel(new javax.swing.table.DefaultTableModel(
        new Object [][] {},
        new String [] {
            "ID", "Merk", "Model", "Tahun", "Plat Nomor", 
            "Tipe", "Kondisi", "Keterangan", "Tarif/Hari", "Status"
        }
    ) {
        boolean[] canEdit = new boolean [] {
            false, false, false, false, false, false, false, false, false, false
        };
        public boolean isCellEditable(int rowIndex, int columnIndex) {
            return canEdit [columnIndex];
        }
    });
    
    // Add action listeners
    btnTambah.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(java.awt.event.ActionEvent evt) {
            btnTambahActionPerformed(evt);
        }
    });
    
    // Layout code (GroupLayout)
    javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
    this.setLayout(layout);
    layout.setHorizontalGroup(...);  // Complex layout definitions
    layout.setVerticalGroup(...);
}// </editor-fold>//GEN-END:initComponents
```

### Event Handler Stubs

```java
private void btnTambahActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTambahActionPerformed
    // Custom code here
    showAddVehicleDialog();
}//GEN-LAST:event_btnTambahActionPerformed
```

### Variables Declaration

```java
// Variables declaration - do not modify//GEN-BEGIN:variables
private javax.swing.JButton btnEdit;
private javax.swing.JButton btnHapus;
private javax.swing.JButton btnRefresh;
private javax.swing.JButton btnTambah;
private javax.swing.JPanel buttonPanel;
private javax.swing.JScrollPane jScrollPane1;
private javax.swing.JLabel titleLabel;
private javax.swing.JTable vehicleTable;
// End of variables declaration//GEN-END:variables
```

### Keuntungan JForm

1. **Visual Design**: Drag-and-drop components di NetBeans
2. **GroupLayout**: Layout manager yang powerful dan fleksibel
3. **Separation**: Design (XML) terpisah dari logic (Java)
4. **Consistency**: Format kode yang konsisten
5. **Maintainability**: Mudah di-edit di NetBeans GUI Builder

### Best Practices

1. **Jangan edit generated code manually**
   - Gunakan NetBeans GUI Builder untuk perubahan UI
   - Hanya edit di luar `//GEN-BEGIN` dan `//GEN-END` markers

2. **Custom logic di event handlers**
   - Event handler stubs adalah tempat untuk custom code
   - Pisahkan business logic ke method terpisah

3. **Initialize custom components di constructor**
   - Setelah `initComponents()`, lakukan custom setup
   - Contoh: `setupTableModel()` di panels

---

## Alur Kerja Aplikasi

### 1. Startup Sequence

```
1. main() method di MainFrame
   ↓
2. Set System Look and Feel
   ↓
3. SwingUtilities.invokeLater()
   ↓
4. new MainFrame()
   ├─→ new DataManager() → initializeData() (load 20 kendaraan)
   ├─→ new VehicleManager(dataManager)
   ├─→ new CustomerManager(dataManager)
   └─→ new RentalManager(dataManager, vehicleManager)
   ↓
5. Create GUI Panels
   ├─→ new VehiclePanel(this, vehicleManager)
   ├─→ new CustomerPanel(this, customerManager)
   ├─→ new RentalPanel(this, rentalManager, ...)
   └─→ new ReportPanel(this, ...)
   ↓
6. Setup Callbacks
   └─→ rentalPanel.setOnRentalChanged(...)
   ↓
7. Refresh Tables (load initial data)
   ↓
8. setVisible(true) → Show Window
```

### 2. Tambah Kendaraan Flow

```
User clicks "Tambah Kendaraan"
   ↓
btnTambahActionPerformed()
   ↓
showAddVehicleDialog()
   ├─→ Create JDialog (modal)
   ├─→ Create form fields
   ├─→ Generate auto ID
   └─→ Setup dynamic fields (Car/Motor)
   ↓
User fills form dan click "Simpan"
   ↓
Validate input
   ├─→ If invalid: Show error message
   └─→ If valid: Continue
   ↓
Create Vehicle object (Car atau Motorcycle)
   ↓
vehicleManager.addVehicle(vehicle)
   ↓
dataManager.getVehicles().add(vehicle)
   ↓
refreshTable()
   ├─→ Clear table rows
   ├─→ Get all vehicles from manager
   └─→ Populate table
   ↓
dialog.dispose()
   ↓
Show success message
```

### 3. Create Rental Flow

```
User clicks "Rental Baru"
   ↓
btnRentalBaruActionPerformed()
   ↓
showAddRentalDialog()
   ├─→ Get all customers
   ├─→ Get available vehicles only
   ├─→ Validate data exists
   └─→ Create dialog with ComboBoxes
   ↓
User selects customer dan vehicle
   ↓
User enters days
   ↓
Auto-calculate triggered (KeyListener)
   ├─→ days * vehicle.getDailyRate()
   └─→ Display di totalField
   ↓
User clicks "Simpan"
   ↓
Parse dates (start + days = end)
   ↓
Create Rental object
   ├─→ Constructor calculates total
   └─→ Set vehicle.available = false
   ↓
rentalManager.addRental(rental)
   ↓
refreshTable() (rental table)
   ↓
notifyRentalChanged()
   ├─→ Execute callback
   └─→ vehiclePanel.refreshTable()
   ↓
Both tables updated!
```

### 4. Complete Rental Flow

```
User selects rental row
   ↓
User clicks "Selesaikan Rental"
   ↓
btnSelesaikanActionPerformed()
   ↓
completeRental()
   ├─→ Get selected row
   ├─→ Validate selection exists
   ├─→ Validate status = "Aktif"
   └─→ Show confirmation dialog
   ↓
User confirms
   ↓
rentalManager.completeRental(index)
   ├─→ rental.completeRental()
   ├──→ status = "Selesai"
   └──→ vehicle.available = true
   ↓
refreshTable() (rental table)
   ↓
notifyRentalChanged()
   └─→ vehiclePanel.refreshTable()
   ↓
Vehicle kembali tersedia di VehiclePanel!
```

### 5. Generate Report Flow

```
User clicks tab "Laporan"
   ↓
User clicks "Refresh Laporan"
   ↓
btnRefreshActionPerformed()
   ↓
refreshReport()
   ├─→ Get all vehicles
   ├─→ Get all customers
   ├─→ Get all rentals
   └─→ Get active rentals
   ↓
Calculate statistics
   ├─→ Count available vehicles (stream filter)
   ├─→ Count rented vehicles
   ├─→ Count completed rentals (stream filter)
   ├─→ Count cancelled rentals (stream filter)
   └─→ Sum revenue (stream mapToDouble + sum)
   ↓
Build report string (StringBuilder)
   ↓
Display in JTextArea
```

---

## Kesimpulan

Aplikasi Rental Kendaraan ini mendemonstrasikan:

### Konsep OOP (Object-Oriented Programming)
- **Encapsulation**: Atribut private dengan getter/setter
- **Inheritance**: Vehicle → Car/Motorcycle
- **Polymorphism**: `getVehicleType()` berbeda untuk Car dan Motorcycle
- **Abstraction**: Vehicle sebagai abstract class

### Design Patterns
- **MVC-like Architecture**: Model-Manager-GUI separation
- **Dependency Injection**: Pass managers ke panels via constructor
- **Observer Pattern**: Callback mechanism untuk panel communication
- **Factory-like**: Manager generate IDs secara konsisten

### Java Features
- **Collections**: ArrayList untuk dynamic storage
- **Generics**: List<Vehicle>, JComboBox<Customer>
- **Stream API**: Filter, map, collect operations
- **Lambda Expressions**: Event listeners dan callbacks
- **Date/Calendar API**: Date manipulation
- **Exception Handling**: Try-catch untuk validation

### GUI Best Practices
- **NetBeans JForm**: Visual design dengan generated code
- **Separation of Concerns**: UI terpisah dari business logic
- **Event-Driven**: User actions trigger event handlers
- **Validation**: Input validation di multiple layers
- **User Feedback**: Confirmation dialogs dan success messages

### Software Engineering
- **Modular Design**: Each class has single responsibility
- **Maintainability**: Clean code, clear naming
- **Extensibility**: Easy to add new features
- **Code Reuse**: Managers can be used by any GUI

---

## Tips untuk Demonstrasi

1. **Mulai dari arsitektur**: Jelaskan 3-layer architecture
2. **Tunjukkan data flow**: Dari GUI → Manager → Model
3. **Demo inheritance**: Tunjukkan Car dan Motorcycle
4. **Demo JForm**: Buka di NetBeans, tunjukkan visual designer
5. **Live demo**: Jalankan aplikasi, tunjukkan fitur-fitur
6. **Jelaskan callback**: Bagaimana rental update vehicle table
7. **Tunjukkan stream API**: Filtering dan aggregation di report
8. **Highlight validations**: Multiple validation points

---

**Dibuat untuk demonstrasi Aplikasi Rental Kendaraan**  
**Menggunakan Java Swing dengan NetBeans JForm Designer**
