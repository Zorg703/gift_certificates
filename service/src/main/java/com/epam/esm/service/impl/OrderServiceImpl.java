package com.epam.esm.service.impl;

import com.epam.esm.domain.GiftCertificate;
import com.epam.esm.domain.GiftCertificateState;
import com.epam.esm.domain.Order;
import com.epam.esm.dto.GiftCertificateDTO;
import com.epam.esm.dto.OrderDTO;
import com.epam.esm.dto.OrderPagingDTO;
import com.epam.esm.dto.PageParamDTO;
import com.epam.esm.repository.GiftCertificateRepository;
import com.epam.esm.repository.OrderRepository;
import com.epam.esm.service.OrderService;
import com.epam.esm.util.GiftCertificateDTOConverter;
import com.epam.esm.util.OrderDTOConverter;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * The type Order service.
 */
@Service
@Transactional
public class OrderServiceImpl implements OrderService {
    private GiftCertificateRepository certificateRepository;
    private OrderRepository repository;

    public OrderServiceImpl(GiftCertificateRepository certificateRepository, OrderRepository repository) {
        this.certificateRepository = certificateRepository;
        this.repository = repository;
    }

    @Override
    public List<OrderDTO> getUserOrders(String name, PageParamDTO param) {
        Optional<List<Order>> orders = repository.getUserOrders(name, param.getPage(), param.getSize());
        return orders.map(orders1 -> OrderDTOConverter.mapAll(orders1, OrderDTO.class)).orElse(null);
    }

    @Override
    public OrderPagingDTO getOrdersByUserName(String name, PageParamDTO param) {
        Map<Integer, Optional<List<Order>>> map = repository.getOrdersByUserName(name, param.getPage(), param.getSize());
        Integer size=null;
        OrderPagingDTO orderPagingDTO=new OrderPagingDTO();
        Optional<List<Order>> orders = Optional.empty();
        for (Map.Entry<Integer, Optional<List<Order>>> entry : map.entrySet()) {
            size = entry.getKey();
            orders = entry.getValue();
        }
        List<OrderDTO> dtoList = new ArrayList<>();
        if (orders.isPresent()){
            dtoList = OrderDTOConverter.mapAll(orders.get(), OrderDTO.class);
        }
        orderPagingDTO.setList(dtoList);
        orderPagingDTO.setSize(size);
        return orderPagingDTO;
    }

    @Override
    public OrderDTO getOneUserOrder(String name, Long orderId) {
        Optional<Order> order = repository.getOneUserOrder(name, orderId);
        return order.map(order1 -> OrderDTOConverter.map(order1, OrderDTO.class)).orElse(null);
    }

    @Override
    public void makeOrder(String name, long orderId, Integer count) {
        Optional<GiftCertificate> giftCertificate = certificateRepository.getOne(orderId);
        if (giftCertificate.isPresent()) {
            GiftCertificate current=giftCertificate.get();
                if (current.getState().equals(GiftCertificateState.INACTIVE)) {
                    current.setState(GiftCertificateState.ACTIVE);
                    certificateRepository.update(current);
                }
            giftCertificate = certificateRepository.getOne(orderId);
            GiftCertificateDTO giftCertificateDTO = GiftCertificateDTOConverter.map(giftCertificate.get(), GiftCertificateDTO.class);
            OrderDTO orderDTO = new OrderDTO();
            orderDTO.setUserName(name);
            orderDTO.setCertificate(giftCertificateDTO);
            orderDTO.setCost(giftCertificate.get().getPrice());
            Order currentOrder = OrderDTOConverter.map(orderDTO, Order.class);
            for(int i=0;i<count;i++) {
                repository.save(currentOrder);
            }
        }
    }

    @Override
    public List<OrderDTO> getAll(PageParamDTO param) {
        Optional<List<Order>> orders = repository.getAll(param.getPage(), param.getSize());
        return orders.map(orders1 -> OrderDTOConverter.mapAll(orders1, OrderDTO.class)).orElse(null);
    }
}
