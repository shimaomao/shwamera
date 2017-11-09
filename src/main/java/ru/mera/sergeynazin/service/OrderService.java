package ru.mera.sergeynazin.service;

import ru.mera.sergeynazin.model.Order;
import ru.mera.sergeynazin.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

/**
 * Again, for educational purposes different approach is used here
 * IT COULD BE GENERIFIED AS WELL AS
 * @see JpaRepository
 *
 * @author sergeynazin
 * */

public interface OrderService {
    Order loadAsPersistent(Long id);
    void save(Order transient_);
    List<Order> getAll();
    void update(Order detached);
    void delete(Order detached);
    Optional<Order> optionalIsExist(Long id);
    Optional<Order> optionalIsExist(String orderNumber);
}
