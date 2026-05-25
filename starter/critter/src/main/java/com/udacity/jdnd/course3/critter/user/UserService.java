package com.udacity.jdnd.course3.critter.user;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private EmployeeRepository employeeRepository;

    public Customer saveCustomer(Customer customer) { return customerRepository.save(customer); }

    public List<Customer> getAllCustomers() { return customerRepository.findAll(); }

    public Optional<Customer> findCustomerById(Long id) { return customerRepository.findById(id); }

    public Employee saveEmployee(Employee employee) { return employeeRepository.save(employee); }

    public Optional<Employee> findEmployeeById(Long id) { return employeeRepository.findById(id); }

    public List<Employee> getAllEmployees() { return employeeRepository.findAll(); }
}
