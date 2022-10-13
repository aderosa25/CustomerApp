package id.co.bankmandiri.customerapp.dao;

import id.co.bankmandiri.customerapp.domain.Customer;
import id.co.bankmandiri.customerapp.domain.CustomerException;
import id.co.bankmandiri.customerapp.service.CustomerService;
import id.co.bankmandiri.customerapp.util.DbUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CustomerDao implements CustomerService {
    private Connection connection;
    private final String queryDisplayAllCustomer = "SELECT * FROM customers";
    private final String queryFindCustomerById = "SELECT * FROM customers WHERE customerId = ?";
    private final String queryEditCustomer = "UPDATE customers SET firstName = ?, lastName = ?, dateOfBirth = ? " +
            "WHERE customerId = ?";
    private final String queryDeleteCustomer = "DELETE from customers WHERE customerId = ?";
    private final String queryInsertCustomer = "INSERT INTO customers(firstName, lastName, dateOfBirth) VALUE (?, ?, ?)";

    public CustomerDao(){
        connection = DbUtil.getConnection();
    }
    public CustomerDao(Connection connection){
        this.connection = connection;
    }
    @Override
    public List<Customer> displayAllCustomer() {
        List<Customer> customers = new ArrayList<>();
        try(
                Statement statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery(queryDisplayAllCustomer);
        ){
            while (resultSet.next()){
                customers.add(new Customer(
                        resultSet.getInt("customerId"),
                        resultSet.getString("firstName"),
                        resultSet.getString("lastName"),
                        resultSet.getDate("dateOfBirth").toLocalDate()
                ));
            }
        } catch (SQLException sqlException){
            sqlException.printStackTrace();
        }
        return null;
    }
    @Override
    public void addCustomer(Customer customer) throws CustomerException {
        try (
                PreparedStatement ps = connection.prepareStatement(queryInsertCustomer);
                ){
            ps.setString(1, customer.getFirstName());
            ps.setString(2, customer.getLastName());
            ps.setDate(3, Date.valueOf(customer.getDateOfBirth()));
            ps.executeUpdate();
        }catch (SQLException sqle){
            sqle.printStackTrace();
            throw new CustomerException("Add customer fail");
        }
    }
    @Override
    public void editCustomer(Customer customer) throws CustomerException {
        try (
                PreparedStatement ps = connection.prepareStatement(queryEditCustomer);
        ){
            ps.setString(1, customer.getFirstName());
            ps.setString(2, customer.getLastName());
            ps.setDate(3, Date.valueOf(customer.getDateOfBirth()));
            ps.setInt(4, customer.getCustomerId());
            ps.executeUpdate();
        }catch (SQLException sqle){
            sqle.printStackTrace();
            throw new CustomerException("Edit customer fail");
        }
    }
    @Override
    public Customer findCustomerById(int id) throws CustomerException {
        Customer customer = null;
        try (
                PreparedStatement ps = connection.prepareStatement(queryFindCustomerById)
                ){
            ps.setInt(1, id);
            ResultSet resultSet = ps.executeQuery();
            if (resultSet.next()){
                customer = new Customer(
                        resultSet.getInt("customerId"),
                        resultSet.getString("firstName"),
                        resultSet.getString("lastName"),
                        resultSet.getDate("dateOfBirth").toLocalDate()
                );
            }
        } catch (SQLException sqle){
            sqle.printStackTrace();
            throw new CustomerException("Find customer by id fail");
        }
        if (customer != null){
            return customer;
        } else {
            throw new CustomerException("Customer tidak ditemukan");
        }

    }
    @Override
    public void deleteCustomer(int id) throws CustomerException {
        try (
                PreparedStatement ps = connection.prepareStatement(queryDeleteCustomer);
        ){
            ps.setInt(1, id);
            ps.executeUpdate();
        }catch (SQLException sqle){
            sqle.printStackTrace();
            throw new CustomerException("Delete customer fail");
        }
    }
}
