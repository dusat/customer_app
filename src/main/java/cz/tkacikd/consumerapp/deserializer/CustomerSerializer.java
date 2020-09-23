package cz.tkacikd.consumerapp.deserializer;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import cz.tkacikd.consumerapp.domain.Customer;

import java.io.IOException;

public class CustomerSerializer extends StdSerializer<Customer> {

    public CustomerSerializer() {
        this(null);
    }

    public CustomerSerializer(Class<Customer> t) {
        super(t);
    }

    @Override
    public void serialize(Customer customer, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        jsonGenerator.writeStartObject();
        jsonGenerator.writeNumberField("id", customer.getId());
        jsonGenerator.writeStringField("name", customer.getName());
        jsonGenerator.writeStringField("surname", customer.getSurname());
        jsonGenerator.writeNumberField("phone", customer.getPhone());
        jsonGenerator.writeStringField("email", customer.getCustomerRegData().getEmail());
        jsonGenerator.writeStringField("password", customer.getCustomerRegData().getPassword());
        jsonGenerator.writeStringField("city", customer.getCustomerAdress().getCity());
        jsonGenerator.writeStringField("street", customer.getCustomerAdress().getStreet());
        jsonGenerator.writeNumberField("postnumber", customer.getCustomerAdress().getPostNumber());
        jsonGenerator.writeEndObject();

    }
}
