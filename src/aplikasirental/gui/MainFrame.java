package aplikasirental.gui;

import aplikasirental.manager.*;
import aplikasirental.model.*;
import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.*;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.List;

public class MainFrame extends JFrame {
    private DataManager dataManager;
    private VehicleManager vehicleManager;
    private CustomerManager customerManager;
    private RentalManager rentalManager;
    
    private JTabbedPane tabbedPane;
    
    // Vehicle Panel Components
    private JTable vehicleTable;
    private DefaultTableModel vehicleTableModel;
    
    // Customer Panel Components
    private JTable customerTable;
    private DefaultTableModel customerTableModel;
    
    // Rental Panel Components
    private JTable rentalTable;
    private DefaultTableModel rentalTableModel;
    
    public MainFrame() {
        // Initialize managers
        dataManager = new DataManager();
        vehicleManager = new VehicleManager(dataManager);
        customerManager = new CustomerManager(dataManager);
        rentalManager = new RentalManager(dataManager, vehicleManager);
        
        // Setup frame
        setTitle("Aplikasi Rental Kendaraan");
        setSize(1000, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        
        // Create tabbed pane
        tabbedPane = new JTabbedPane();
        
        // Add tabs
        tabbedPane.addTab("Kendaraan", createVehiclePanel());
        tabbedPane.addTab("Pelanggan", createCustomerPanel());
        tabbedPane.addTab("Transaksi Rental", createRentalPanel());
        tabbedPane.addTab("Laporan", createReportPanel());
        
        add(tabbedPane);
        
        // Load initial data
        refreshVehicleTable();
        refreshCustomerTable();
        refreshRentalTable();
    }
    
    // ========== VEHICLE PANEL ==========
    private JPanel createVehiclePanel() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        // Title
        JLabel titleLabel = new JLabel("Manajemen Kendaraan", JLabel.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        panel.add(titleLabel, BorderLayout.NORTH);
        
        // Table
        String[] columns = {"ID", "Merk", "Model", "Tahun", "Plat Nomor", "Tipe", "Kondisi", "Keterangan", "Tarif/Hari", "Status"};
        vehicleTableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        vehicleTable = new JTable(vehicleTableModel);
        vehicleTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane scrollPane = new JScrollPane(vehicleTable);
        panel.add(scrollPane, BorderLayout.CENTER);
        
        // Buttons
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        
        JButton addButton = new JButton("Tambah Kendaraan");
        addButton.addActionListener(e -> showAddVehicleDialog());
        buttonPanel.add(addButton);
        
        JButton editButton = new JButton("Edit Kendaraan");
        editButton.addActionListener(e -> showEditVehicleDialog());
        buttonPanel.add(editButton);
        
        JButton deleteButton = new JButton("Hapus Kendaraan");
        deleteButton.addActionListener(e -> deleteVehicle());
        buttonPanel.add(deleteButton);
        
        JButton refreshButton = new JButton("Refresh");
        refreshButton.addActionListener(e -> refreshVehicleTable());
        buttonPanel.add(refreshButton);
        
        panel.add(buttonPanel, BorderLayout.SOUTH);
        
        return panel;
    }
    
    private void refreshVehicleTable() {
        vehicleTableModel.setRowCount(0);
        List<Vehicle> vehicles = vehicleManager.getAllVehicles();
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
    
    private void showAddVehicleDialog() {
        JDialog dialog = new JDialog(this, "Tambah Kendaraan", true);
        dialog.setLayout(new BorderLayout(10, 10));
        dialog.setSize(500, 450);
        dialog.setLocationRelativeTo(this);
        
        JPanel formPanel = new JPanel(new GridLayout(0, 2, 10, 10));
        formPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        // Vehicle Type
        formPanel.add(new JLabel("Tipe Kendaraan:"));
        JComboBox<String> typeCombo = new JComboBox<>(new String[]{"Mobil", "Motor"});
        formPanel.add(typeCombo);
        
        // Basic Fields
        formPanel.add(new JLabel("ID Kendaraan:"));
        JTextField idField = new JTextField(vehicleManager.generateVehicleId());
        idField.setEditable(false);
        formPanel.add(idField);
        
        formPanel.add(new JLabel("Merk:"));
        JTextField brandField = new JTextField();
        formPanel.add(brandField);
        
        formPanel.add(new JLabel("Model:"));
        JTextField modelField = new JTextField();
        formPanel.add(modelField);
        
        formPanel.add(new JLabel("Tahun:"));
        JTextField yearField = new JTextField();
        formPanel.add(yearField);
        
        formPanel.add(new JLabel("Plat Nomor:"));
        JTextField plateField = new JTextField();
        formPanel.add(plateField);
        
        formPanel.add(new JLabel("Tarif Harian (Rp):"));
        JTextField rateField = new JTextField();
        formPanel.add(rateField);
        
        formPanel.add(new JLabel("Kondisi:"));
        JComboBox<String> kondisiCombo = new JComboBox<>(new String[]{"Sangat Baik", "Baik", "Cukup", "Perlu Perbaikan"});
        formPanel.add(kondisiCombo);
        
        formPanel.add(new JLabel("Keterangan:"));
        JTextField keteranganField = new JTextField();
        formPanel.add(keteranganField);
        
        // Car-specific fields
        JLabel doorsLabel = new JLabel("Jumlah Pintu:");
        JTextField doorsField = new JTextField();
        formPanel.add(doorsLabel);
        formPanel.add(doorsField);
        
        JLabel transLabel = new JLabel("Transmisi:");
        JComboBox<String> transCombo = new JComboBox<>(new String[]{"Manual", "Otomatis"});
        formPanel.add(transLabel);
        formPanel.add(transCombo);
        
        // Motorcycle-specific fields
        JLabel engineLabel = new JLabel("Kapasitas Mesin (cc):");
        JTextField engineField = new JTextField();
        engineLabel.setVisible(false);
        engineField.setVisible(false);
        formPanel.add(engineLabel);
        formPanel.add(engineField);
        
        JLabel motoTypeLabel = new JLabel("Tipe Motor:");
        JComboBox<String> motoTypeCombo = new JComboBox<>(new String[]{"Matic", "Sport", "Bebek"});
        motoTypeLabel.setVisible(false);
        motoTypeCombo.setVisible(false);
        formPanel.add(motoTypeLabel);
        formPanel.add(motoTypeCombo);
        
        // Type change listener
        typeCombo.addActionListener(e -> {
            boolean isCar = typeCombo.getSelectedItem().equals("Mobil");
            doorsLabel.setVisible(isCar);
            doorsField.setVisible(isCar);
            transLabel.setVisible(isCar);
            transCombo.setVisible(isCar);
            engineLabel.setVisible(!isCar);
            engineField.setVisible(!isCar);
            motoTypeLabel.setVisible(!isCar);
            motoTypeCombo.setVisible(!isCar);
        });
        
        dialog.add(formPanel, BorderLayout.CENTER);
        
        // Buttons
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JButton saveButton = new JButton("Simpan");
        saveButton.addActionListener(e -> {
            try {
                String id = idField.getText();
                String brand = brandField.getText();
                String model = modelField.getText();
                int year = Integer.parseInt(yearField.getText());
                String plate = plateField.getText();
                double rate = Double.parseDouble(rateField.getText());
                String kondisi = (String) kondisiCombo.getSelectedItem();
                String keterangan = keteranganField.getText();
                
                if (brand.isEmpty() || model.isEmpty() || plate.isEmpty()) {
                    JOptionPane.showMessageDialog(dialog, "Semua field harus diisi!", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                
                Vehicle vehicle;
                if (typeCombo.getSelectedItem().equals("Mobil")) {
                    int doors = Integer.parseInt(doorsField.getText());
                    String trans = (String) transCombo.getSelectedItem();
                    vehicle = new Car(id, brand, model, year, plate, rate, kondisi, keterangan, doors, trans);
                } else {
                    int engine = Integer.parseInt(engineField.getText());
                    String motoType = (String) motoTypeCombo.getSelectedItem();
                    vehicle = new Motorcycle(id, brand, model, year, plate, rate, kondisi, keterangan, engine, motoType);
                }
                
                vehicleManager.addVehicle(vehicle);
                refreshVehicleTable();
                dialog.dispose();
                JOptionPane.showMessageDialog(this, "Kendaraan berhasil ditambahkan!");
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(dialog, "Format angka tidak valid!", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });
        buttonPanel.add(saveButton);
        
        JButton cancelButton = new JButton("Batal");
        cancelButton.addActionListener(e -> dialog.dispose());
        buttonPanel.add(cancelButton);
        
        dialog.add(buttonPanel, BorderLayout.SOUTH);
        dialog.setVisible(true);
    }
    
    private void showEditVehicleDialog() {
        int selectedRow = vehicleTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Pilih kendaraan yang akan diedit!", "Peringatan", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        Vehicle vehicle = vehicleManager.getAllVehicles().get(selectedRow);
        
        JDialog dialog = new JDialog(this, "Edit Kendaraan", true);
        dialog.setLayout(new BorderLayout(10, 10));
        dialog.setSize(500, 400);
        dialog.setLocationRelativeTo(this);
        
        JPanel formPanel = new JPanel(new GridLayout(0, 2, 10, 10));
        formPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        formPanel.add(new JLabel("ID Kendaraan:"));
        JTextField idField = new JTextField(vehicle.getVehicleId());
        idField.setEditable(false);
        formPanel.add(idField);
        
        formPanel.add(new JLabel("Merk:"));
        JTextField brandField = new JTextField(vehicle.getBrand());
        formPanel.add(brandField);
        
        formPanel.add(new JLabel("Model:"));
        JTextField modelField = new JTextField(vehicle.getModel());
        formPanel.add(modelField);
        
        formPanel.add(new JLabel("Tahun:"));
        JTextField yearField = new JTextField(String.valueOf(vehicle.getYear()));
        formPanel.add(yearField);
        
        formPanel.add(new JLabel("Plat Nomor:"));
        JTextField plateField = new JTextField(vehicle.getLicensePlate());
        formPanel.add(plateField);
        
        formPanel.add(new JLabel("Tarif Harian (Rp):"));
        JTextField rateField = new JTextField(String.valueOf(vehicle.getDailyRate()));
        formPanel.add(rateField);
        
        formPanel.add(new JLabel("Kondisi:"));
        JComboBox<String> kondisiCombo = new JComboBox<>(new String[]{"Sangat Baik", "Baik", "Cukup", "Perlu Perbaikan"});
        kondisiCombo.setSelectedItem(vehicle.getKondisi() != null ? vehicle.getKondisi() : "Baik");
        formPanel.add(kondisiCombo);
        
        formPanel.add(new JLabel("Keterangan:"));
        JTextField keteranganField = new JTextField(vehicle.getKeterangan() != null ? vehicle.getKeterangan() : "");
        formPanel.add(keteranganField);
        
        if (vehicle instanceof Car) {
            Car car = (Car) vehicle;
            formPanel.add(new JLabel("Jumlah Pintu:"));
            JTextField doorsField = new JTextField(String.valueOf(car.getNumberOfDoors()));
            formPanel.add(doorsField);
            
            formPanel.add(new JLabel("Transmisi:"));
            JComboBox<String> transCombo = new JComboBox<>(new String[]{"Manual", "Otomatis"});
            transCombo.setSelectedItem(car.getTransmissionType());
            formPanel.add(transCombo);
        } else if (vehicle instanceof Motorcycle) {
            Motorcycle moto = (Motorcycle) vehicle;
            formPanel.add(new JLabel("Kapasitas Mesin (cc):"));
            JTextField engineField = new JTextField(String.valueOf(moto.getEngineCapacity()));
            formPanel.add(engineField);
            
            formPanel.add(new JLabel("Tipe Motor:"));
            JComboBox<String> motoTypeCombo = new JComboBox<>(new String[]{"Matic", "Sport", "Bebek"});
            motoTypeCombo.setSelectedItem(moto.getMotorcycleType());
            formPanel.add(motoTypeCombo);
        }
        
        dialog.add(formPanel, BorderLayout.CENTER);
        
        // Buttons
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JButton saveButton = new JButton("Simpan");
        saveButton.addActionListener(e -> {
            try {
                vehicle.setBrand(brandField.getText());
                vehicle.setModel(modelField.getText());
                vehicle.setYear(Integer.parseInt(yearField.getText()));
                vehicle.setLicensePlate(plateField.getText());
                vehicle.setDailyRate(Double.parseDouble(rateField.getText()));
                vehicle.setKondisi((String) kondisiCombo.getSelectedItem());
                vehicle.setKeterangan(keteranganField.getText());
                
                vehicleManager.updateVehicle(selectedRow, vehicle);
                refreshVehicleTable();
                dialog.dispose();
                JOptionPane.showMessageDialog(this, "Kendaraan berhasil diupdate!");
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(dialog, "Format angka tidak valid!", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });
        buttonPanel.add(saveButton);
        
        JButton cancelButton = new JButton("Batal");
        cancelButton.addActionListener(e -> dialog.dispose());
        buttonPanel.add(cancelButton);
        
        dialog.add(buttonPanel, BorderLayout.SOUTH);
        dialog.setVisible(true);
    }
    
    private void deleteVehicle() {
        int selectedRow = vehicleTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Pilih kendaraan yang akan dihapus!", "Peringatan", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        int confirm = JOptionPane.showConfirmDialog(this, "Apakah Anda yakin ingin menghapus kendaraan ini?", 
                "Konfirmasi", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            vehicleManager.deleteVehicle(selectedRow);
            refreshVehicleTable();
            JOptionPane.showMessageDialog(this, "Kendaraan berhasil dihapus!");
        }
    }
    
    // ========== CUSTOMER PANEL ==========
    private JPanel createCustomerPanel() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        // Title
        JLabel titleLabel = new JLabel("Manajemen Pelanggan", JLabel.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        panel.add(titleLabel, BorderLayout.NORTH);
        
        // Table
        String[] columns = {"ID", "Nama", "No. Telepon", "Alamat", "No. KTP"};
        customerTableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        customerTable = new JTable(customerTableModel);
        customerTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane scrollPane = new JScrollPane(customerTable);
        panel.add(scrollPane, BorderLayout.CENTER);
        
        // Buttons
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        
        JButton addButton = new JButton("Tambah Pelanggan");
        addButton.addActionListener(e -> showAddCustomerDialog());
        buttonPanel.add(addButton);
        
        JButton editButton = new JButton("Edit Pelanggan");
        editButton.addActionListener(e -> showEditCustomerDialog());
        buttonPanel.add(editButton);
        
        JButton deleteButton = new JButton("Hapus Pelanggan");
        deleteButton.addActionListener(e -> deleteCustomer());
        buttonPanel.add(deleteButton);
        
        JButton refreshButton = new JButton("Refresh");
        refreshButton.addActionListener(e -> refreshCustomerTable());
        buttonPanel.add(refreshButton);
        
        panel.add(buttonPanel, BorderLayout.SOUTH);
        
        return panel;
    }
    
    private void refreshCustomerTable() {
        customerTableModel.setRowCount(0);
        List<Customer> customers = customerManager.getAllCustomers();
        for (Customer c : customers) {
            Object[] row = {
                c.getCustomerId(),
                c.getName(),
                c.getPhoneNumber(),
                c.getAddress(),
                c.getIdNumber()
            };
            customerTableModel.addRow(row);
        }
    }
    
    private void showAddCustomerDialog() {
        JDialog dialog = new JDialog(this, "Tambah Pelanggan", true);
        dialog.setLayout(new BorderLayout(10, 10));
        dialog.setSize(400, 300);
        dialog.setLocationRelativeTo(this);
        
        JPanel formPanel = new JPanel(new GridLayout(0, 2, 10, 10));
        formPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        formPanel.add(new JLabel("ID Pelanggan:"));
        JTextField idField = new JTextField(customerManager.generateCustomerId());
        idField.setEditable(false);
        formPanel.add(idField);
        
        formPanel.add(new JLabel("Nama:"));
        JTextField nameField = new JTextField();
        formPanel.add(nameField);
        
        formPanel.add(new JLabel("No. Telepon:"));
        JTextField phoneField = new JTextField();
        formPanel.add(phoneField);
        
        formPanel.add(new JLabel("Alamat:"));
        JTextField addressField = new JTextField();
        formPanel.add(addressField);
        
        formPanel.add(new JLabel("No. KTP:"));
        JTextField idNumberField = new JTextField();
        formPanel.add(idNumberField);
        
        dialog.add(formPanel, BorderLayout.CENTER);
        
        // Buttons
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JButton saveButton = new JButton("Simpan");
        saveButton.addActionListener(e -> {
            String id = idField.getText();
            String name = nameField.getText();
            String phone = phoneField.getText();
            String address = addressField.getText();
            String idNumber = idNumberField.getText();
            
            if (name.isEmpty() || phone.isEmpty() || address.isEmpty() || idNumber.isEmpty()) {
                JOptionPane.showMessageDialog(dialog, "Semua field harus diisi!", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            Customer customer = new Customer(id, name, phone, address, idNumber);
            customerManager.addCustomer(customer);
            refreshCustomerTable();
            dialog.dispose();
            JOptionPane.showMessageDialog(this, "Pelanggan berhasil ditambahkan!");
        });
        buttonPanel.add(saveButton);
        
        JButton cancelButton = new JButton("Batal");
        cancelButton.addActionListener(e -> dialog.dispose());
        buttonPanel.add(cancelButton);
        
        dialog.add(buttonPanel, BorderLayout.SOUTH);
        dialog.setVisible(true);
    }
    
    private void showEditCustomerDialog() {
        int selectedRow = customerTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Pilih pelanggan yang akan diedit!", "Peringatan", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        Customer customer = customerManager.getAllCustomers().get(selectedRow);
        
        JDialog dialog = new JDialog(this, "Edit Pelanggan", true);
        dialog.setLayout(new BorderLayout(10, 10));
        dialog.setSize(400, 300);
        dialog.setLocationRelativeTo(this);
        
        JPanel formPanel = new JPanel(new GridLayout(0, 2, 10, 10));
        formPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        formPanel.add(new JLabel("ID Pelanggan:"));
        JTextField idField = new JTextField(customer.getCustomerId());
        idField.setEditable(false);
        formPanel.add(idField);
        
        formPanel.add(new JLabel("Nama:"));
        JTextField nameField = new JTextField(customer.getName());
        formPanel.add(nameField);
        
        formPanel.add(new JLabel("No. Telepon:"));
        JTextField phoneField = new JTextField(customer.getPhoneNumber());
        formPanel.add(phoneField);
        
        formPanel.add(new JLabel("Alamat:"));
        JTextField addressField = new JTextField(customer.getAddress());
        formPanel.add(addressField);
        
        formPanel.add(new JLabel("No. KTP:"));
        JTextField idNumberField = new JTextField(customer.getIdNumber());
        formPanel.add(idNumberField);
        
        dialog.add(formPanel, BorderLayout.CENTER);
        
        // Buttons
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JButton saveButton = new JButton("Simpan");
        saveButton.addActionListener(e -> {
            customer.setName(nameField.getText());
            customer.setPhoneNumber(phoneField.getText());
            customer.setAddress(addressField.getText());
            customer.setIdNumber(idNumberField.getText());
            
            customerManager.updateCustomer(selectedRow, customer);
            refreshCustomerTable();
            dialog.dispose();
            JOptionPane.showMessageDialog(this, "Pelanggan berhasil diupdate!");
        });
        buttonPanel.add(saveButton);
        
        JButton cancelButton = new JButton("Batal");
        cancelButton.addActionListener(e -> dialog.dispose());
        buttonPanel.add(cancelButton);
        
        dialog.add(buttonPanel, BorderLayout.SOUTH);
        dialog.setVisible(true);
    }
    
    private void deleteCustomer() {
        int selectedRow = customerTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Pilih pelanggan yang akan dihapus!", "Peringatan", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        int confirm = JOptionPane.showConfirmDialog(this, "Apakah Anda yakin ingin menghapus pelanggan ini?", 
                "Konfirmasi", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            customerManager.deleteCustomer(selectedRow);
            refreshCustomerTable();
            JOptionPane.showMessageDialog(this, "Pelanggan berhasil dihapus!");
        }
    }
    
    // ========== RENTAL PANEL ==========
    private JPanel createRentalPanel() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        // Title
        JLabel titleLabel = new JLabel("Transaksi Rental", JLabel.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        panel.add(titleLabel, BorderLayout.NORTH);
        
        // Table
        String[] columns = {"ID Rental", "Pelanggan", "Kendaraan", "Tgl Mulai", "Tgl Selesai", "Hari", "Total", "Status"};
        rentalTableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        rentalTable = new JTable(rentalTableModel);
        rentalTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane scrollPane = new JScrollPane(rentalTable);
        panel.add(scrollPane, BorderLayout.CENTER);
        
        // Buttons
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        
        JButton addButton = new JButton("Rental Baru");
        addButton.addActionListener(e -> showAddRentalDialog());
        buttonPanel.add(addButton);
        
        JButton completeButton = new JButton("Selesaikan Rental");
        completeButton.addActionListener(e -> completeRental());
        buttonPanel.add(completeButton);
        
        JButton cancelButton = new JButton("Batalkan Rental");
        cancelButton.addActionListener(e -> cancelRental());
        buttonPanel.add(cancelButton);
        
        JButton refreshButton = new JButton("Refresh");
        refreshButton.addActionListener(e -> refreshRentalTable());
        buttonPanel.add(refreshButton);
        
        panel.add(buttonPanel, BorderLayout.SOUTH);
        
        return panel;
    }
    
    private void refreshRentalTable() {
        rentalTableModel.setRowCount(0);
        List<Rental> rentals = rentalManager.getAllRentals();
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        for (Rental r : rentals) {
            Object[] row = {
                r.getRentalId(),
                r.getCustomer().getName(),
                r.getVehicle().getBrand() + " " + r.getVehicle().getModel(),
                sdf.format(r.getStartDate()),
                sdf.format(r.getEndDate()),
                r.getDays(),
                String.format("Rp %.0f", r.getTotalCost()),
                r.getStatus()
            };
            rentalTableModel.addRow(row);
        }
    }
    
    private void showAddRentalDialog() {
        List<Customer> customers = customerManager.getAllCustomers();
        List<Vehicle> availableVehicles = vehicleManager.getAvailableVehicles();
        
        if (customers.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Belum ada pelanggan terdaftar!", "Peringatan", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        if (availableVehicles.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Tidak ada kendaraan yang tersedia!", "Peringatan", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        JDialog dialog = new JDialog(this, "Rental Baru", true);
        dialog.setLayout(new BorderLayout(10, 10));
        dialog.setSize(500, 400);
        dialog.setLocationRelativeTo(this);
        
        JPanel formPanel = new JPanel(new GridLayout(0, 2, 10, 10));
        formPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        formPanel.add(new JLabel("ID Rental:"));
        JTextField idField = new JTextField(rentalManager.generateRentalId());
        idField.setEditable(false);
        formPanel.add(idField);
        
        formPanel.add(new JLabel("Pelanggan:"));
        JComboBox<Customer> customerCombo = new JComboBox<>(customers.toArray(new Customer[0]));
        formPanel.add(customerCombo);
        
        formPanel.add(new JLabel("Kendaraan:"));
        JComboBox<Vehicle> vehicleCombo = new JComboBox<>(availableVehicles.toArray(new Vehicle[0]));
        formPanel.add(vehicleCombo);
        
        formPanel.add(new JLabel("Tanggal Mulai:"));
        JTextField startDateField = new JTextField(new SimpleDateFormat("dd/MM/yyyy").format(new Date()));
        formPanel.add(startDateField);
        
        formPanel.add(new JLabel("Jumlah Hari:"));
        JTextField daysField = new JTextField("1");
        formPanel.add(daysField);
        
        formPanel.add(new JLabel("Total Biaya:"));
        JTextField totalField = new JTextField("0");
        totalField.setEditable(false);
        formPanel.add(totalField);
        
        // Calculate total on changes
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
        
        daysField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                calculateTotal.actionPerformed(null);
            }
        });
        vehicleCombo.addActionListener(calculateTotal);
        calculateTotal.actionPerformed(null);
        
        dialog.add(formPanel, BorderLayout.CENTER);
        
        // Buttons
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JButton saveButton = new JButton("Simpan");
        saveButton.addActionListener(e -> {
            try {
                String id = idField.getText();
                Customer customer = (Customer) customerCombo.getSelectedItem();
                Vehicle vehicle = (Vehicle) vehicleCombo.getSelectedItem();
                
                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                Date startDate = sdf.parse(startDateField.getText());
                int days = Integer.parseInt(daysField.getText());
                
                Calendar cal = Calendar.getInstance();
                cal.setTime(startDate);
                cal.add(Calendar.DAY_OF_MONTH, days);
                Date endDate = cal.getTime();
                
                Rental rental = new Rental(id, customer, vehicle, startDate, endDate, days);
                rentalManager.addRental(rental);
                refreshRentalTable();
                refreshVehicleTable();
                dialog.dispose();
                JOptionPane.showMessageDialog(this, "Rental berhasil ditambahkan!");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(dialog, "Error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        });
        buttonPanel.add(saveButton);
        
        JButton cancelButton = new JButton("Batal");
        cancelButton.addActionListener(e -> dialog.dispose());
        buttonPanel.add(cancelButton);
        
        dialog.add(buttonPanel, BorderLayout.SOUTH);
        dialog.setVisible(true);
    }
    
    private void completeRental() {
        int selectedRow = rentalTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Pilih rental yang akan diselesaikan!", "Peringatan", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        Rental rental = rentalManager.getAllRentals().get(selectedRow);
        if (!rental.getStatus().equals("Aktif")) {
            JOptionPane.showMessageDialog(this, "Rental ini sudah tidak aktif!", "Peringatan", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        int confirm = JOptionPane.showConfirmDialog(this, "Selesaikan rental ini?", 
                "Konfirmasi", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            rentalManager.completeRental(selectedRow);
            refreshRentalTable();
            refreshVehicleTable();
            JOptionPane.showMessageDialog(this, "Rental berhasil diselesaikan!");
        }
    }
    
    private void cancelRental() {
        int selectedRow = rentalTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Pilih rental yang akan dibatalkan!", "Peringatan", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        Rental rental = rentalManager.getAllRentals().get(selectedRow);
        if (!rental.getStatus().equals("Aktif")) {
            JOptionPane.showMessageDialog(this, "Rental ini sudah tidak aktif!", "Peringatan", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        int confirm = JOptionPane.showConfirmDialog(this, "Batalkan rental ini?", 
                "Konfirmasi", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            rentalManager.cancelRental(selectedRow);
            refreshRentalTable();
            refreshVehicleTable();
            JOptionPane.showMessageDialog(this, "Rental berhasil dibatalkan!");
        }
    }
    
    // ========== REPORT PANEL ==========
    private JPanel createReportPanel() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        // Title
        JLabel titleLabel = new JLabel("Laporan", JLabel.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        panel.add(titleLabel, BorderLayout.NORTH);
        
        // Report area
        JTextArea reportArea = new JTextArea();
        reportArea.setEditable(false);
        reportArea.setFont(new Font("Monospaced", Font.PLAIN, 12));
        JScrollPane scrollPane = new JScrollPane(reportArea);
        panel.add(scrollPane, BorderLayout.CENTER);
        
        // Refresh button
        JButton refreshButton = new JButton("Refresh Laporan");
        refreshButton.addActionListener(e -> {
            StringBuilder sb = new StringBuilder();
            sb.append("=== LAPORAN APLIKASI RENTAL KENDARAAN ===\n\n");
            
            List<Vehicle> vehicles = vehicleManager.getAllVehicles();
            List<Customer> customers = customerManager.getAllCustomers();
            List<Rental> rentals = rentalManager.getAllRentals();
            List<Rental> activeRentals = rentalManager.getActiveRentals();
            
            sb.append("Total Kendaraan: ").append(vehicles.size()).append("\n");
            long availableVehicles = vehicles.stream().filter(Vehicle::isAvailable).count();
            sb.append("Kendaraan Tersedia: ").append(availableVehicles).append("\n");
            sb.append("Kendaraan Disewa: ").append(vehicles.size() - availableVehicles).append("\n\n");
            
            sb.append("Total Pelanggan: ").append(customers.size()).append("\n\n");
            
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
            
            double totalRevenue = rentalManager.getTotalRevenue();
            sb.append(String.format("Total Pendapatan: Rp %.0f\n", totalRevenue));
            
            reportArea.setText(sb.toString());
        });
        
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.add(refreshButton);
        panel.add(buttonPanel, BorderLayout.SOUTH);
        
        return panel;
    }
    
    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        SwingUtilities.invokeLater(() -> {
            MainFrame frame = new MainFrame();
            frame.setVisible(true);
        });
    }
}
