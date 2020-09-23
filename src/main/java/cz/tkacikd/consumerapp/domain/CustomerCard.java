package cz.tkacikd.consumerapp.domain;


import javax.persistence.*;

@Entity
@Table(name = "customer_card")
public class CustomerCard {

    public CustomerCard(){}

    public CustomerCard(Long sum, Long discount) {
        this.sum = sum;
        this.discount = discount;
    }

    @Id
    @GeneratedValue(generator = "customercard_generator", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name="customercard_generator",
            sequenceName="custcard_id_seq", allocationSize=10)
    private Long id;

    @Column (name = "sum")
    private Long sum;

    @Column (name = "discount")
    private Long discount;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id")
    private Customer customer;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getSum() {
        return sum;
    }

    public void setSum(Long sum) {
        this.sum = sum;
    }

    public Long getDiscount() {
        return discount;
    }

    public void setDiscount(Long discount) {
        this.discount = discount;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CustomerCard)) return false;

        CustomerCard that = (CustomerCard) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (sum != null ? !sum.equals(that.sum) : that.sum != null) return false;
        return discount != null ? discount.equals(that.discount) : that.discount == null;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (sum != null ? sum.hashCode() : 0);
        result = 31 * result + (discount != null ? discount.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "CustomerCard{" +
                "id=" + id +
                ", sum=" + sum +
                ", discount=" + discount +
                ", customer=" + customer +
                '}';
    }
}
