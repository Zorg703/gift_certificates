package com.epam.esm.service.impl;

import com.epam.esm.domain.Order;
import com.epam.esm.dto.OrderDTO;
import com.epam.esm.repository.OrderRepository;
import com.epam.esm.util.OrderDTOConverter;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import org.mockito.runners.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;
@RunWith(MockitoJUnitRunner.class)
public class OrderServiceImplTest {
    private OrderDTO orderDTO;
    @Mock
    private OrderRepository orderRepository;

    @InjectMocks
    private OrderServiceImpl orderService;
    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        orderDTO=new OrderDTO();
        orderDTO.setUserName("");
    }
    @Test
    public void getUserOrders() {
        List<OrderDTO> dtoList=new ArrayList<>();
        dtoList.add(orderDTO);
        Optional<List<Order>> list=Optional.of(OrderDTOConverter.mapAll(dtoList,Order.class));
        when(orderRepository.getUserOrders("",1,1)).thenReturn(list);
        orderService.getUserOrders("",1,1);
        verify(orderRepository).getUserOrders("",1,1);


    }

    @Test
    public void getOneUserOrder() {
        Optional<Order> order=Optional.of(OrderDTOConverter.map(orderDTO,Order.class));
        when(orderRepository.getOneUserOrder("",1L)).thenReturn(order);
        orderService.getOneUserOrder("",1L);
        verify(orderRepository).getOneUserOrder("",1L);
    }

    @Test
    public void getAll() {
        List<OrderDTO> dtoList=new ArrayList<>();
        dtoList.add(orderDTO);
        Optional<List<Order>> list=Optional.of(OrderDTOConverter.mapAll(dtoList,Order.class));
        when(orderRepository.getAll(1,1)).thenReturn(list);
        orderService.getAll(1,1);
        verify(orderRepository).getAll(1,1);
    }
}