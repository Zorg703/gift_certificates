package com.epam.esm.service;

import com.epam.esm.dto.OrderDTO;
import com.epam.esm.dto.OrderPagingDTO;
import com.epam.esm.dto.PageParamDTO;

import java.util.List;

/**
 * The interface Order service.
 */
public interface OrderService {

    OrderPagingDTO getOrdersByUserName(String name, PageParamDTO param);

    /**
     * Gets one user order.
     *
     * @param name    the name
     * @param orderId the order id
     * @return the one user order
     */
    OrderDTO getOneUserOrder(String name, Long orderId);

    /**
     * Make order.
     *  @param name  the name
     * @param order the order
     * @param count
     */
    void makeOrder(String name, long order, Integer count);

    /**
     * Gets user orders.
     *
     * @param username the username
     * @param param    the param
     * @return the user orders
     */
    List<OrderDTO> getUserOrders(String username, PageParamDTO param);

    /**
     * Gets all.
     *
     * @param param the param
     * @return the all
     */
    List<OrderDTO> getAll(PageParamDTO param);
}
