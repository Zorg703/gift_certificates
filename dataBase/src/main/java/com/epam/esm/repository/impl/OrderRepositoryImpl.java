package com.epam.esm.repository.impl;

import com.epam.esm.domain.GiftCertificate;
import com.epam.esm.domain.GiftCertificateState;
import com.epam.esm.domain.Order;
import com.epam.esm.domain.Tag;
import com.epam.esm.repository.OrderRepository;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * The type Order repository.
 * Gets objects from data base and send in service
 */
@Repository
public class OrderRepositoryImpl implements OrderRepository {
    private static final String GET_USER_ORDERS = "SELECT o FROM Order o INNER JOIN o.certificate c where o.userName=:name";
    private static final String GET_ONE_USER_ORDER = "SELECT o FROM Order o INNER JOIN o.certificate c where o.userName=:name AND o.id=:id";
    private static final String GET_STATE = "SELECT g.state from GiftCertificate g WHERE g.id =:id";
    private static final String GET_GIFT_CERTIFICATE_BY_ID = "SELECT g from GiftCertificate g WHERE g.id =:id";
    private static final String UPDATE_STATE ="update Certificate set state='ACTIVE' where id=:id";
    private int defaultPageSize = 10;

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Optional<List<Order>> getUserOrders(String userName, Integer page, Integer size) {
        TypedQuery<Order> query = entityManager.createQuery(GET_USER_ORDERS, Order.class);
        query.setParameter("name", userName);
        return paginationResult(query, page, size);
    }

    @Override
    public Map<Integer, Optional<List<Order>>> getOrdersByUserName(String userName, Integer page, Integer size) {
        TypedQuery<Order> query = entityManager.createQuery(GET_USER_ORDERS, Order.class);
        query.setParameter("name", userName);
        query.setFirstResult(0);
        query.setMaxResults(java.lang.Integer.MAX_VALUE);
        Integer count=query.getResultList().size();
        Optional<List<Order>> list=paginationResult(query, page, size);
        Map<Integer, Optional<List<Order>>> map=new HashMap<>();
        map.put(count,list);
        return map;
    }

    @Override
    public Optional<Order> getOneUserOrder(String name, Long orderId) {
        TypedQuery<Order> query = entityManager.createQuery(GET_ONE_USER_ORDER, Order.class);
        query.setParameter("name", name);
        query.setParameter("id", orderId);
        return Optional.ofNullable(query.getSingleResult());
    }

    @Override
    public void save(Order order) {
        try {
            entityManager.merge(order);
        }catch (DataAccessException e){

        }
    }

    @Override
    public Optional<List<Order>> getAll(Integer page, Integer size) {
        CriteriaQuery<Order> criteriaQuery = entityManager.getCriteriaBuilder().createQuery(Order.class);
        Root<Order> root = criteriaQuery.from(Order.class);
        CriteriaQuery<Order> select = criteriaQuery.select(root);
        TypedQuery<Order> query = entityManager.createQuery(select);
        return paginationResult(query, page, size);
    }

    private Optional<List<Order>> paginationResult(TypedQuery<Order> query, Integer page, Integer size) {
        if (page != null && size != null && page > 0 && size > 0) {
            query.setFirstResult((page - 1) * size).setMaxResults(size);
        } else {
            query.setMaxResults(defaultPageSize);
        }
        return Optional.of(query.getResultList());
    }
}
