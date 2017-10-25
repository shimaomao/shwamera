package ru.mera.sergeynazin.model;

import org.hibernate.HibernateException;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.engine.spi.SessionImplementor;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.id.IdentifierGenerator;
import org.hibernate.id.enhanced.SequenceStyleGenerator;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Set;

@Entity
@Table(name = "order")
public class Order {

    @Id


    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "date_plus_sequence_generator")
    @GenericGenerator(name = "date_plus_sequence_generator", )



    @Column(
        length = 32,
        unique = true,
        nullable = false,
        insertable = false,
        updatable = false
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

    public static class OrderIdGenerator extends SequenceStyleGenerator {


        @Override
        public Serializable generate(SharedSessionContractImplementor session, Object object)
            throws HibernateException {
            LocalDate localDate = LocalDate.now();
            String prefix = localDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd")) + "_";


            Connection connection = session.connection();
            try {

                PreparedStatement ps = connection
                    .prepareStatement("SELECT MAX(vlaue) as vlaue from hibernate_tutorial.pk_table");

                ResultSet rs = ps.executeQuery();
                if (rs.next()) {
                    int id = rs.getInt("vlaue");
                    String code = prefix + new Integer(id).toString();
                    System.out.println("Generated Stock Code: " + code);
                    return code;
                }

            } catch (SQLException e) {
                e.printStackTrace();
            }
            return null;
        }
    }
}
