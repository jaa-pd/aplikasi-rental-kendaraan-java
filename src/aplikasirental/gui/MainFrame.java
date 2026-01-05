package aplikasirental.gui;

import aplikasirental.manager.*;
import javax.swing.*;
import java.awt.*;

public class MainFrame extends JFrame {
    private DataManager dataManager;
    private VehicleManager vehicleManager;
    private CustomerManager customerManager;
    private RentalManager rentalManager;
    
    private JTabbedPane tabbedPane;
    private VehiclePanel vehiclePanel;
    private CustomerPanel customerPanel;
    private RentalPanel rentalPanel;
    private ReportPanel reportPanel;
    
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
        
        // Create panels
        vehiclePanel = new VehiclePanel(this, vehicleManager);
        customerPanel = new CustomerPanel(this, customerManager);
        rentalPanel = new RentalPanel(this, rentalManager, vehicleManager, customerManager);
        reportPanel = new ReportPanel(this, vehicleManager, customerManager, rentalManager);
        
        // Set callback for rental changes to refresh vehicle panel
        rentalPanel.setOnRentalChanged(() -> vehiclePanel.refreshTable());
        
        // Add tabs
        tabbedPane.addTab("Kendaraan", vehiclePanel);
        tabbedPane.addTab("Pelanggan", customerPanel);
        tabbedPane.addTab("Transaksi Rental", rentalPanel);
        tabbedPane.addTab("Laporan", reportPanel);
        
        add(tabbedPane);
        
        // Load initial data
        vehiclePanel.refreshTable();
        customerPanel.refreshTable();
        rentalPanel.refreshTable();
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
