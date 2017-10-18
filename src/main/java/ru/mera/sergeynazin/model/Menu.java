package ru.mera.sergeynazin.model;

import javax.persistence.*;
import java.util.List;
import java.util.Map;

@Entity
@Table(name = "menu")
public class Menu {

    //private Shaurma[] shaurmaList;
    //private Double[] shaurmaCost;

    // TODO: every POJOs' <code>insertable</code> and <code>updatable</code> is doubtful here. Do we need them?
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true, nullable = false, insertable = false, updatable = false)
    private Long id;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinTable(
        name = "shaurma_has_menu",
        joinColumns = { @JoinColumn(name = "menu_id", referencedColumnName = "id") },
        inverseJoinColumns = { @JoinColumn(name = "shaurma_id", referencedColumnName = "id") }
    )
    private List<Shaurma> shaurmaList;

    private List<Double> shaurmaCost;





    public Menu() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }


    public List<Shaurma> getShaurmaList() {
        return shaurmaList;
    }

    public void setShaurmaList(List<Shaurma> shaurmaList) {
        this.shaurmaList = shaurmaList;
    }

    public List<Double> getShaurmaCost() {
        return shaurmaCost;
    }

    public void setShaurmaCost(List<Double> shaurmaCost) {
        this.shaurmaCost = shaurmaCost;
    }



}
