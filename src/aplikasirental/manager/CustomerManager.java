package aplikasirental.manager;

import aplikasirental.model.*;
import java.util.*;

public class CustomerManager {
    private List<Customer> customers;
    private DataManager dataManager;
    
    public CustomerManager(DataManager dataManager) {
        this.dataManager = dataManager;
        this.customers = dataManager.loadCustomers();
    }
    
    public void addCustomer(Customer customer) {
        customers.add(customer);
        dataManager.saveCustomers(customers);
    }
    
    public void updateCustomer(int index, Customer customer) {
        if (index >= 0 && index < customers.size()) {
            customers.set(index, customer);
            dataManager.saveCustomers(customers);
        }
    }
    
    public void deleteCustomer(int index) {
        if (index >= 0 && index < customers.size()) {
            customers.remove(index);
            dataManager.saveCustomers(customers);
        }
    }
    
    public List<Customer> getAllCustomers() {
        return new ArrayList<>(customers);
    }
    
    public Customer getCustomerById(String customerId) {
        for (Customer c : customers) {
            if (c.getCustomerId().equals(customerId)) {
                return c;
            }
        }
        return null;
    }
    
    public String generateCustomerId() {
        int maxId = 0;
        for (Customer c : customers) {
            String id = c.getCustomerId();
            if (id != null && id.startsWith("CUS")) {
                try {
                    int num = Integer.parseInt(id.substring(3));
                    if (num > maxId) {
                        maxId = num;
                    }
                } catch (NumberFormatException e) {
                    // Skip invalid IDs
                }
            }
        }
        return "CUS" + String.format("%04d", maxId + 1);
    }
}
