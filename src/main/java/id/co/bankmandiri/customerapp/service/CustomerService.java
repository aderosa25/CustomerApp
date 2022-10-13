package id.co.bankmandiri.customerapp.service;

import id.co.bankmandiri.customerapp.domain.Customer;
import id.co.bankmandiri.customerapp.domain.CustomerException;

import java.util.List;

public interface CustomerService {
    List<Customer> displayAllCustomer();
    void addCustomer(Customer customer) throws CustomerException;
    void editCustomer(Customer customer) throws CustomerException;
    Customer findCustomerById(int id) throws CustomerException;
    void deleteCustomer(int id) throws CustomerException;
}
