package cz.tkacikd.consumerapp.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import cz.tkacikd.consumerapp.deserializer.CustomerDeserializer;
import cz.tkacikd.consumerapp.deserializer.CustomerSerializer;
import cz.tkacikd.consumerapp.domain.Customer;
import cz.tkacikd.consumerapp.domain.CustomerAdress;
import cz.tkacikd.consumerapp.domain.CustomerRegisterData;
import cz.tkacikd.consumerapp.service.CustomerService;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import java.nio.charset.StandardCharsets;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest
@EnableAutoConfiguration
class CustomerControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private CustomerService customerService;

    @Autowired
    private CustomerController customerController;

    @Test
    void save() throws Exception {
            objectMapper = new ObjectMapper();
            SimpleModule simpleModule = new SimpleModule();
            simpleModule.addSerializer(Customer.class, new CustomerSerializer());
            simpleModule.addDeserializer(Customer.class, new CustomerDeserializer());
            objectMapper.registerModule(simpleModule);

            Customer customer = createCustomer();
            String json = "{\"id\":1,\"name\":\"Martin\",\"surname\":\"Novák\",\"phone\":123456,\"email\":\"1@1.com\",\"password\":\"1234\",\"city\":\"Praha\",\"street\":\"Novákova\",\"postnumber\":123}";
            Mockito.when(customerController.save(Mockito.any())).thenReturn(customer);

            String result = mockMvc.perform(post("/app/createCustomer")
                    .content(objectMapper.writeValueAsString(customer))
                    .contentType(APPLICATION_JSON)
                    .characterEncoding("UTF-8"))
                    .andExpect(status().isOk())
                    .andReturn()
                    .getResponse()
                    .getContentAsString(StandardCharsets.UTF_8);

            assertEquals(json, result);
          }


    private Customer createCustomer() {
        Customer customer = new Customer();
        CustomerAdress customerAdress = new CustomerAdress();
        customerAdress.setCity("Praha");
        customerAdress.setStreet("Novákova");
        customerAdress.setPostNumber(123);
        CustomerRegisterData customerRegisterData = new CustomerRegisterData();
        customerRegisterData.setEmail("1@1.com");
        customerRegisterData.setPassword("1234");
        customer.setId(1L);
        customer.setName("Martin");
        customer.setSurname("Novák");
        customer.setPhone(123456L);
        customer.setCustomerAdress(customerAdress);
        customer.setCustomerRegData(customerRegisterData);
        return customer;
    }
}
