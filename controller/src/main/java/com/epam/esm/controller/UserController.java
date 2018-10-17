package com.epam.esm.controller;

import com.epam.esm.dto.OrderDTO;
import com.epam.esm.dto.OrderPagingDTO;
import com.epam.esm.dto.PageParamDTO;
import com.epam.esm.exception.ControllerException;
import com.epam.esm.service.OrderService;
import com.epam.esm.util.DataReaderFromToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/rest/users")
public class UserController {
    private OrderService orderService;
    @Autowired
    private DataReaderFromToken dataReaderFromToken;

    public UserController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping("/orders")
    @PreAuthorize("(hasAuthority('admin') or (hasAuthority('user'))) AND #oauth2.hasScope('read')")
    public ResponseEntity<OrderPagingDTO> getAllUserOrders(@Valid PageParamDTO param, Errors errors) {
        String name = dataReaderFromToken.getUserNameFromToken();
        OrderPagingDTO orderPagingDTO;
        if (errors.hasErrors()) {
            throw new ControllerException();
        }
        else{
             orderPagingDTO= orderService.getOrdersByUserName(name, param);
        }
        return new ResponseEntity<>(orderPagingDTO, HttpStatus.OK);
    }

    @GetMapping("/orders/{orderId}")
    @PreAuthorize("(hasAuthority('admin') or (hasAuthority('user'))) AND #oauth2.hasScope('read')")
    public ResponseEntity<OrderDTO> getOneUserOrders(final @PathVariable String username,
                                                     final @PathVariable long orderId) {
        OrderDTO orderDTO = null;
        String name = dataReaderFromToken.getUserNameFromToken();
        String role = dataReaderFromToken.getUserRoleFromToken();
        if ("admin".equals(role)) {
            orderDTO = orderService.getOneUserOrder(username, orderId);

        } else if (name.equals(username)) {
            orderDTO = orderService.getOneUserOrder(name, orderId);
        }
        return new ResponseEntity<>(orderDTO, HttpStatus.OK);
    }
}
