package ru.mera.sergeynazin.model;

import javax.persistence.*;

@Entity
@Table(name = "menu")
public class MenuEntry {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true, nullable = false, insertable = false, updatable = false)
    private Long id;

    @OneToOne(cascade = CascadeType.ALL, optional = false)
    @PrimaryKeyJoinColumn(name = "shaurma_id", referencedColumnName = "id")
    private Shaurma shaurma;

    @org.hibernate.annotations.Type(type = "big_decimal")
    @Column(precision = 7, scale = 2, nullable = false)
    private Double price;

    public MenuEntry() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Shaurma getShaurma() {
        return shaurma;
    }

    public void setShaurma(Shaurma shaurma) {
        this.shaurma = shaurma;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

}
