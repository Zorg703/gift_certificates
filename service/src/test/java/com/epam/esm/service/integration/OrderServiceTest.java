package com.epam.esm.service.integration;

import com.epam.esm.configuration.TestConfig;

import com.epam.esm.service.OrderService;
import com.epam.esm.service.impl.OrderServiceImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TestTransaction;
import org.springframework.transaction.annotation.Transactional;


import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {OrderServiceImpl.class, TestConfig.class})
@ActiveProfiles("test")
@Transactional
public class OrderServiceTest {
    @Autowired
    OrderService orderService;

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Test
    public void makeOrder() {
        int count=jdbcTemplate.queryForObject("select count(user_name) from order_ where user_name=?",new Object[]{},Integer.class);
        orderService.makeOrder("user",1, count);
        int count2=jdbcTemplate.queryForObject("select count(user_name) from order_ where user_name=?",new Object[]{},Integer.class);
        assertNotEquals(count,count2);
        TestTransaction.flagForRollback();
        TestTransaction.end();
        assertEquals(count,count2);
    }

}