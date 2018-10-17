package com.epam.esm.repository;

import com.epam.esm.domain.Order;

import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * The interface Order repository.
 */
public interface OrderRepository {
    /**
     * Gets user orders.
     *
     * @param userName the user name
     * @param page     the page
     * @param size     the size
     * @return the user orders
     */
    Optional<List<Order>> getUserOrders(String userName, Integer page, Integer size);

    Map<Integer, Optional<List<Order>>> getOrdersByUserName(String userName, Integer page, Integer size);

    /**
     * Gets one user order.
     *
     * @param name    the name
     * @param orderId the order id
     * @return the one user order
     */
    Optional<Order> getOneUserOrder(String name, Long orderId);

    /**
     * Save.
     *
     * @param order the order
     */
    void save(Order order);

    /**
     * Gets all.
     *
     * @param page the page
     * @param size the size
     * @return the all
     */
    Optional<List<Order>> getAll(Integer page, Integer size);
}
