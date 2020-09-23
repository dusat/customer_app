package cz.tkacikd.consumerapp.deserializer;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import cz.tkacikd.consumerapp.domain.Customer;
import cz.tkacikd.consumerapp.domain.CustomerAdress;
import cz.tkacikd.consumerapp.domain.CustomerRegisterData;
import java.io.IOException;

public class CustomerDeserializer extends StdDeserializer<Customer> {

    public CustomerDeserializer() {
        this(null);
    }

    public CustomerDeserializer(Class<?> vc) {
        super(vc);
    }

    @Override
    public Customer deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JsonProcessingException {
        JsonNode node = jsonParser.getCodec().readTree(jsonParser);
        String name = node.get("name").asText();
        String surname = node.get("surname").asText();
        Long phone = (Long) node.get("phone").longValue();
        String email = node.get("email").asText();
        String password = node.get("password").asText();
        String city = node.get("city").asText();
        String street = node.get("street").asText();
        Integer postNumber = (Integer) node.get("postnumber").intValue();
        Customer cst = new Customer();
        CustomerAdress cstAdress = new CustomerAdress();
        CustomerRegisterData cstRegData = new CustomerRegisterData();
        cst.setName(name);
        cst.setSurname(surname);
        cst.setPhone(phone);
        cstAdress.setCity(city);
        cstAdress.setStreet(street);
        cstAdress.setPostNumber(postNumber);
        cst.setCustomerAdress(cstAdress);
        cstRegData.setEmail(email);
        cstRegData.setPassword(password);
        cst.setCustomerRegData(cstRegData);
        return cst;
    }
}
