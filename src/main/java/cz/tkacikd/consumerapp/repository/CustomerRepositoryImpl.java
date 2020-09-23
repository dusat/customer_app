package cz.tkacikd.consumerapp.repository;

import cz.tkacikd.consumerapp.domain.Customer;
import cz.tkacikd.consumerapp.domain.CustomerCard;
import cz.tkacikd.consumerapp.domain.CustomerRegisterData;
import cz.tkacikd.consumerapp.exception.AuthenticationException;
import org.springframework.stereotype.Repository;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import org.mindrot.jbcrypt.BCrypt;

@Repository
public class CustomerRepositoryImpl implements CustomerRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Customer saveNewCustomer(Customer cst) {
        CustomerRegisterData customerRegisterData = new CustomerRegisterData();
        String hashedPassword = BCrypt.hashpw(cst.getCustomerRegData().getPassword(), BCrypt.gensalt(10));
        customerRegisterData.setPassword(hashedPassword);
        customerRegisterData.setEmail(cst.getCustomerRegData().getEmail());
        cst.setCustomerRegData(customerRegisterData);
        cst.addCustomerCard(new CustomerCard(9000L, 1L));
        cst.addCustomerCard(new CustomerCard(10001L, 2L));
        entityManager.persist(cst);
        return entityManager.find(Customer.class, cst.getId());
    }

    @Override
    public Customer authenticateCustomer(String email, String password) {
        try {
            Query nq = entityManager.createNativeQuery("SELECT * FROM customer WHERE email=:email", Customer.class);
            nq.setParameter("email", email);
            Customer cst = (Customer) nq.getSingleResult();
            if (!BCrypt.checkpw(password, cst.getCustomerRegData().getPassword())) {
                throw new AuthenticationException("Database password is not equal to customer password");
            }
            return cst;
        } catch (AuthenticationException e) {
            throw new AuthenticationException("Authentication error occurred");
        }
    }

}
