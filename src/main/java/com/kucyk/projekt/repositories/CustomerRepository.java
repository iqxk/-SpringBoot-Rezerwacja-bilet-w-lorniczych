package com.kucyk.projekt.repositories;

import com.kucyk.projekt.models.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer, Long>
{

    Customer findByUserId(Long userId);
}
