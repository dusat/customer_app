package cz.tkacikd.consumerapp.repository;

import cz.tkacikd.consumerapp.domain.Customer;

public interface CustomerRepository {
    Customer saveNewCustomer(Customer cst);
    Customer authenticateCustomer(String email, String password);
}
