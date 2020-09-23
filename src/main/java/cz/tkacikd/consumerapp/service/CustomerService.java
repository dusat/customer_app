package cz.tkacikd.consumerapp.service;

import cz.tkacikd.consumerapp.domain.Customer;

public interface CustomerService {
    Customer saveNewCustomer(Customer cst);
    Customer authenticateCustomer(String email, String password);
}
