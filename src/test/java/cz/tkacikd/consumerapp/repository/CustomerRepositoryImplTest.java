package cz.tkacikd.consumerapp.repository;

import cz.tkacikd.consumerapp.domain.Customer;
import cz.tkacikd.consumerapp.domain.CustomerAdress;
import cz.tkacikd.consumerapp.domain.CustomerCard;
import cz.tkacikd.consumerapp.domain.CustomerRegisterData;
import cz.tkacikd.consumerapp.exception.AuthenticationException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;
import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@DataJpaTest
class CustomerRepositoryImplTest {

    @Autowired
    private TestEntityManager em;

    @Test
    void saveNewCustomer() {
        Customer customer = new Customer();
        CustomerAdress customerAdress = new CustomerAdress();
        customerAdress.setCity("Praha");
        customerAdress.setStreet("Novákova");
        customerAdress.setPostNumber(123);
        CustomerRegisterData customerRegisterData = new CustomerRegisterData();
        customerRegisterData.setEmail("1@1.com");
        customerRegisterData.setPassword("1234");
        customer.setName("Martin");
        customer.setSurname("Novák");
        customer.setPhone(123456L);
        customer.setCustomerAdress(customerAdress);
        customer.setCustomerRegData(customerRegisterData);
        customer.addCustomerCard(new CustomerCard(9000L, 1L));
        customer.addCustomerCard(new CustomerCard(10001L, 2L));
        em.persist(customer);
        em.flush();
        em.find(Customer.class, 1L);
        assertEquals(1, customer.getId());
    }

    @Test
    void checkPasswordNoException () {
        String password = "test_password";
        String hashedPassword = "$2a$10$vMCZRL1f11MkldkoWeFAJuwmI/V/VlIK1Q4E4ussfwADZHCWaUu7q";
        if (!BCrypt.checkpw(password, hashedPassword)) {
            throw new AuthenticationException("Database password is not equal to customer password");
        }
    }

    @Test
    void checkPasswordThrowException () {
        String password = "bad_test_password";
        String hashedPassword = "$2a$10$vMCZRL1f11MkldkoWeFAJuwmI/V/VlIK1Q4E4ussfwADZHCWaUu7q";
        Assertions.assertThrows(AuthenticationException.class, () -> {
            if (!BCrypt.checkpw(password, hashedPassword)) {
                throw new AuthenticationException("Database password is not equal to customer password");
            }
        });
    }
}

