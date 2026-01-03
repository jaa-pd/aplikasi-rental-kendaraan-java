package aplikasirental.model;

import java.io.Serializable;

public abstract class Vehicle implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private String vehicleId;
    private String brand;
    private String model;
    private int year;
    private String licensePlate;
    private double dailyRate;
    private boolean available;
    private String kondisi;
    private String keterangan;
    
    public Vehicle() {
        this.available = true;
    }
    
    public Vehicle(String vehicleId, String brand, String model, int year, 
                   String licensePlate, double dailyRate) {
        this.vehicleId = vehicleId;
        this.brand = brand;
        this.model = model;
        this.year = year;
        this.licensePlate = licensePlate;
        this.dailyRate = dailyRate;
        this.available = true;
        this.kondisi = "Baik";
        this.keterangan = "";
    }
    
    public Vehicle(String vehicleId, String brand, String model, int year, 
                   String licensePlate, double dailyRate, String kondisi, String keterangan) {
        this.vehicleId = vehicleId;
        this.brand = brand;
        this.model = model;
        this.year = year;
        this.licensePlate = licensePlate;
        this.dailyRate = dailyRate;
        this.available = true;
        this.kondisi = kondisi;
        this.keterangan = keterangan;
    }
    
    // Getters and Setters
    public String getVehicleId() {
        return vehicleId;
    }
    
    public void setVehicleId(String vehicleId) {
        this.vehicleId = vehicleId;
    }
    
    public String getBrand() {
        return brand;
    }
    
    public void setBrand(String brand) {
        this.brand = brand;
    }
    
    public String getModel() {
        return model;
    }
    
    public void setModel(String model) {
        this.model = model;
    }
    
    public int getYear() {
        return year;
    }
    
    public void setYear(int year) {
        this.year = year;
    }
    
    public String getLicensePlate() {
        return licensePlate;
    }
    
    public void setLicensePlate(String licensePlate) {
        this.licensePlate = licensePlate;
    }
    
    public double getDailyRate() {
        return dailyRate;
    }
    
    public void setDailyRate(double dailyRate) {
        this.dailyRate = dailyRate;
    }
    
    public boolean isAvailable() {
        return available;
    }
    
    public void setAvailable(boolean available) {
        this.available = available;
    }
    
    public String getKondisi() {
        return kondisi;
    }
    
    public void setKondisi(String kondisi) {
        this.kondisi = kondisi;
    }
    
    public String getKeterangan() {
        return keterangan;
    }
    
    public void setKeterangan(String keterangan) {
        this.keterangan = keterangan;
    }
    
    public abstract String getVehicleType();
    
    @Override
    public String toString() {
        return vehicleId + " - " + brand + " " + model + " (" + year + ") - " + licensePlate;
    }
}
