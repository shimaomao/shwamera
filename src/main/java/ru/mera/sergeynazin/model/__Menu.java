package ru.mera.sergeynazin.model;

import javax.persistence.*;
import java.util.List;
import java.util.Map;

public class __Menu {

    @Entity
    @Table(name = "menu")
    public static class MenuEntry {

        // TODO: every POJOs' <code>insertable</code> and <code>updatable</code> is doubtful here. Do we need them?
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        @Column(unique = true, nullable = false, insertable = false, updatable = false)
        private Long id;

        @Column(name = "shaurma_id", unique = true, nullable = false)
        private Long shaurmaId;

        @org.hibernate.annotations.Type(type = "big_decimal")
        @Column(precision = 7, scale = 2)
        private Double price;

        public MenuEntry() {
        }

        public Long getId() {
            return id;
        }

        public void setId(Long id) {
            this.id = id;
        }

        public Long getShaurmaId() {
            return shaurmaId;
        }

        public void setShaurmaId(Long shaurmaId) {
            this.shaurmaId = shaurmaId;
        }

        public Double getPrice() {
            return price;
        }

        public void setPrice(Double price) {
            this.price = price;
        }
    }


    private List<String> shaurmaNamesList;

    private List<Double> shaurmaCost;


    public List<String> getShaurmaNamesList() {
        return shaurmaNamesList;
    }

    public List<Double> getShaurmaCost() {
        return shaurmaCost;
    }

    public void setShaurmaMenu(List<Shaurma> shaurmaList) {

        this.shaurmaCost = null;
        this.shaurmaNamesList = null;
    }

//    public List<Shaurma> getShaurmaList() {
//        return shaurmaList;
//    }
//
//    public void setShaurmaList(List<Shaurma> shaurmaList) {
//        this.shaurmaList = shaurmaList;
//    }



}
