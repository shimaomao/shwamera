package ru.mera.sergeynazin.model;

import java.util.Set;

public class Order {

    private Long orderNumber;
    private Double totalCost;
    private Set<Shaurma> shaurmaSet;

    public Long getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(Long orderNumber) {
        this.orderNumber = orderNumber;
    }

    public Double getTotalCost() {
        return totalCost;
    }

    public void setTotalCost(Double totalCost) {
        this.totalCost = totalCost;
    }

    public Set<Shaurma> getShaurmaSet() {
        return shaurmaSet;
    }

    public void setShaurmaSet(Set<Shaurma> shaurmaSet) {
        this.shaurmaSet = shaurmaSet;
    }
}
