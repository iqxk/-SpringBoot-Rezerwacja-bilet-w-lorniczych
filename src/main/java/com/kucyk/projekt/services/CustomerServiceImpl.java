package com.kucyk.projekt.services;

import com.kucyk.projekt.models.Customer;
import com.kucyk.projekt.repositories.CustomerRepository;
import com.kucyk.projekt.repositories.UserRepository;
import com.kucyk.projekt.services.interfaces.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("customerService")
public class CustomerServiceImpl implements CustomerService
{
    @Autowired
    CustomerRepository customerRepository;

    @Autowired
    UserRepository userRepository;

    public boolean checkIfCustomerExistsByUserId(Long id)
    {
        var user = customerRepository.findByUserId(id);
        return user != null;
    }

    public Customer getByUserId(Long userId)
    {
        return customerRepository.findByUserId(userId);
    }

    public Customer saveCustomer(Customer c)
    {
        return customerRepository.save(c);
    }

    public Customer getById(Long id)
    {
        return customerRepository.getById(id);
    }

    public Customer getByUsername(String username)
    {
        return customerRepository.findByUserId(userRepository.findByUsername(username).getId());
    }

    public boolean checkIfCustomerExistsByUsername(String username)
    {
        var user = customerRepository.findByUserId(userRepository.findByUsername(username).getId());
        return user != null;
    }
}
