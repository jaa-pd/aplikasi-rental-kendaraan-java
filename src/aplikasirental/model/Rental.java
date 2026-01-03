package aplikasirental.model;

import java.io.Serializable;
import java.util.Date;

public class Rental implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private String rentalId;
    private Customer customer;
    private Vehicle vehicle;
    private Date startDate;
    private Date endDate;
    private int days;
    private double totalCost;
    private String status; // "Aktif", "Selesai", "Dibatalkan"
    
    public Rental() {
        this.status = "Aktif";
    }
    
    public Rental(String rentalId, Customer customer, Vehicle vehicle, 
                  Date startDate, Date endDate, int days) {
        this.rentalId = rentalId;
        this.customer = customer;
        this.vehicle = vehicle;
        this.startDate = startDate;
        this.endDate = endDate;
        this.days = days;
        this.totalCost = days * vehicle.getDailyRate();
        this.status = "Aktif";
    }
    
    // Getters and Setters
    public String getRentalId() {
        return rentalId;
    }
    
    public void setRentalId(String rentalId) {
        this.rentalId = rentalId;
    }
    
    public Customer getCustomer() {
        return customer;
    }
    
    public void setCustomer(Customer customer) {
        this.customer = customer;
    }
    
    public Vehicle getVehicle() {
        return vehicle;
    }
    
    public void setVehicle(Vehicle vehicle) {
        this.vehicle = vehicle;
    }
    
    public Date getStartDate() {
        return startDate;
    }
    
    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }
    
    public Date getEndDate() {
        return endDate;
    }
    
    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }
    
    public int getDays() {
        return days;
    }
    
    public void setDays(int days) {
        this.days = days;
        if (vehicle != null) {
            this.totalCost = days * vehicle.getDailyRate();
        }
    }
    
    public double getTotalCost() {
        return totalCost;
    }
    
    public void setTotalCost(double totalCost) {
        this.totalCost = totalCost;
    }
    
    public String getStatus() {
        return status;
    }
    
    public void setStatus(String status) {
        this.status = status;
    }
    
    @Override
    public String toString() {
        return rentalId + " - " + customer.getName() + " - " + vehicle.toString();
    }
}
