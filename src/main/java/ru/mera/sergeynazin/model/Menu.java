package ru.mera.sergeynazin.model;

import java.util.List;
import java.util.Map;

public class Menu {
    //private Shaurma[] shaurmaList;
    //private Double[] shaurmaCost;

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

    private List<Shaurma> shaurmaList;
    private List<Double> shaurmaCost;

}
