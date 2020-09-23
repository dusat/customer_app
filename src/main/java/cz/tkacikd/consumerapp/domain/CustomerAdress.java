package cz.tkacikd.consumerapp.domain;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class CustomerAdress {

    @Column(name = "city")
    private String city;

    @Column(name = "street")
    private String street;

    @Column(name = "post_number")
    private Integer postNumber;

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public Integer getPostNumber() {
        return postNumber;
    }

    public void setPostNumber(Integer postNumber) {
        this.postNumber = postNumber;
    }

    @Override
    public String toString() {
        return "CustomerAdress{" +
                "city='" + city + '\'' +
                ", street='" + street + '\'' +
                ", postNumber=" + postNumber +
                '}';
    }
}
