package ru.mera.sergeynazin.model.old;

import ru.mera.sergeynazin.model.Shaurma;

import javax.persistence.*;

@Entity
@Table(name = "menu")
public class __MenuEntry {

    // TODO: every POJOs' <code>insertable</code> and <code>updatable</code> is doubtful here. Do we need them?
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true, nullable = false, insertable = false, updatable = false)
    private Long id;

    @OneToOne
    @PrimaryKeyJoinColumn(name="shaurma_id", referencedColumnName="id")
    private Shaurma shaurma;

    @Column(name = "shaurma_id", unique = true, nullable = false)
    private Shaurma shaurmaId;

    @org.hibernate.annotations.Type(type = "big_decimal")
    @Column(precision = 7, scale = 2)
    private Double price;

    public __MenuEntry() {
    }

    public Shaurma getShaurma() {
        return shaurma;
    }

    public void setShaurma(Shaurma shaurma) {
        this.shaurma = shaurma;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Shaurma getShaurmaId() {
        return this.shaurma.getId();
    }

    public void setShaurmaId(Shaurma shaurmaId) {
        this.shaurma.setId(shaurmaId);
        this.shaurmaId = shaurmaId;
    }

    public Double getPrice() {
        return this.shaurma.getCost();
    }

    public void setPrice(Double price) {
        this.price = price;
    }
}
