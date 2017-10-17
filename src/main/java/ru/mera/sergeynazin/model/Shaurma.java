package ru.mera.sergeynazin.model;

import java.util.Objects;
import java.util.Set;
import java.util.concurrent.atomic.DoubleAdder;

public class Shaurma {

    private Long id;
    private String name;
    private Set<Ingredient> ingredientSet;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<Ingredient> getIngredientSet() {
        return ingredientSet;
    }

    public void setIngredientSet(Set<Ingredient> ingredientSet) {
        this.ingredientSet = ingredientSet;
    }

    public Double getCost() {
        DoubleAdder doubleAdder = new DoubleAdder();
        //TODO ifNeeded to avoid unboxing + boxing as listed below
//        ingredientSet.parallelStream()
//                .forEach(ingredient -> doubleAdder.add(ingredient.getCost()));
//        return doubleAdder.doubleValue();


        ingredientSet.parallelStream()
                .mapToDouble(Ingredient::getCost)
                .forEach(doubleAdder::add);
        return doubleAdder.doubleValue();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Shaurma shaurma = (Shaurma) o;

        return Objects.equals(this.name, shaurma.name)
                && Objects.equals(this.id, shaurma.id)
                && Objects.equals(this.ingredientSet, shaurma.ingredientSet);
    }

    @Override
    public int hashCode() {
        int result = id.hashCode();
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (ingredientSet != null ? ingredientSet.hashCode() : 0);
        return result;
    }



}