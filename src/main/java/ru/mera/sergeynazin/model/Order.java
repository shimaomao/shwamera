package ru.mera.sergeynazin.model;

import org.hibernate.annotations.Generated;
import org.hibernate.annotations.GenerationTime;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "order")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequence-generator")
    @SequenceGenerator(name = "sequence-generator", sequenceName = "order_sequence", allocationSize = 1)
    @Column(unique = true, nullable = false, updatable = false)
    private Long id;

    @Generated(GenerationTime.INSERT)
    @Column(        // TODO: Insertable seems to be redundant
        unique = true,
        nullable = false,
        updatable = false,
        columnDefinition = "VARCHAR(32) AS CONCAT (CURRENT_DATE, '_', SUM(1, (SELECT MAX(id) FROM order WHERE id LIKE CONCAT(CURRENT_DATE, '%'))))"
    )
    private String orderNumber;

    @org.hibernate.annotations.Type(type = "big_decimal")
    @Column(precision = 7, scale = 2)
    private Double totalCost;


    // TODO: TypeConverter from String to int
    @ManyToMany(cascade = { CascadeType.ALL })
    @JoinTable(
        name = "order_has_shaurma",
        joinColumns = { @JoinColumn(name = "order_orderNumber", referencedColumnName = "id") },
        inverseJoinColumns = { @JoinColumn(name = "shaurma_id", referencedColumnName = "id") }
    )
    private Set<Shaurma> shaurmaSet;

    // TODO: 10/23/17 Do I need it empty constructor?
    public Order() {
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getTotalCost() {
        return totalCost;
    }

    public void setTotalCost(Double totalCost) {
        this.totalCost = totalCost;
    }

    public String getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber;
    }

    public Set<Shaurma> getShaurmaSet() {
        return shaurmaSet;
    }

    public void setShaurmaSet(Set<Shaurma> shaurmaSet) {
        this.shaurmaSet = shaurmaSet;
    }
}
