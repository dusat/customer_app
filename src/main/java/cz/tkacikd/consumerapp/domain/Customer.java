package cz.tkacikd.consumerapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import cz.tkacikd.consumerapp.deserializer.CustomerDeserializer;
import cz.tkacikd.consumerapp.deserializer.CustomerSerializer;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "customer")
@JsonDeserialize(using = CustomerDeserializer.class)
@JsonSerialize(using = CustomerSerializer.class)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Customer {

    @Id
    @GeneratedValue(generator = "customer_generator", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name="customer_generator",
            sequenceName="customer_id_seq", allocationSize=10)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "surname")
    private String surname;

    @Column(name = "phone")
    private Long phone;

    @JsonIgnore
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "customer", orphanRemoval = true)
    Set<CustomerCard> customerCards = new HashSet<>();

    @Embedded
    private CustomerAdress customerAdress;

    @Embedded
    private CustomerRegisterData customerRegData;

    public void addCustomerCard(CustomerCard customerCard) {
        customerCards.add(customerCard);
        customerCard.setCustomer(this);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public Long getPhone() {
        return phone;
    }

    public void setPhone(Long phone) {
        this.phone = phone;
    }

    public Set<CustomerCard> getCustomerCards() {
        return customerCards;
    }

    public void setCustomerCards(Set<CustomerCard> customerCards) {
        this.customerCards = customerCards;
    }

    public CustomerAdress getCustomerAdress() {
        return customerAdress;
    }

    public void setCustomerAdress(CustomerAdress customerAdress) {
        this.customerAdress = customerAdress;
    }

    public CustomerRegisterData getCustomerRegData() {
        return customerRegData;
    }

    public void setCustomerRegData(CustomerRegisterData customerRegData) {
        this.customerRegData = customerRegData;
    }

    @Override
    public String toString() {
        return "Customer{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", phone=" + phone +
                ", customerCards=" + customerCards +
                ", customerAdress=" + customerAdress +
                ", customerRegData=" + customerRegData +
                '}';
    }
}
