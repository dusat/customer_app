package cz.tkacikd.consumerapp.service;

import cz.tkacikd.consumerapp.domain.Customer;
import cz.tkacikd.consumerapp.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CustomerServiceImpl implements  CustomerService {

    private CustomerRepository cstRepository;

    @Autowired
    CustomerServiceImpl (CustomerRepository cstRepository) {
        this.cstRepository = cstRepository;
    }

    @Override
    @Transactional
    public Customer saveNewCustomer(Customer cst) {
        return cstRepository.saveNewCustomer(cst);
    }

    @Override
    @Transactional
    public Customer authenticateCustomer(String email, String password) {
        return cstRepository.authenticateCustomer(email, password);
    }
}
