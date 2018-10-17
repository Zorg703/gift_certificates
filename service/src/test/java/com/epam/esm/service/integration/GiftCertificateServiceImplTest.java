package com.epam.esm.service.integration;

import com.epam.esm.configuration.TestConfig;
import com.epam.esm.dto.GiftCertificateDTO;
import com.epam.esm.service.GiftCertificateService;
import com.epam.esm.service.impl.GiftCertificateServiceImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TestTransaction;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {GiftCertificateServiceImpl.class, TestConfig.class})
@ActiveProfiles("test")
@Transactional
public class GiftCertificateServiceImplTest {

    @Autowired
    private GiftCertificateService giftCertificateService;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Test
    public void update() {
        GiftCertificateDTO giftCertificate=giftCertificateService.getOne(1);
        giftCertificate.setName("abc");
        GiftCertificateDTO giftCertificateAfterUpdate=giftCertificateService.getOne(1);
        assertNotEquals(giftCertificate,giftCertificateAfterUpdate);
        TestTransaction.flagForRollback();
        TestTransaction.end();
        assertEquals(giftCertificate,giftCertificateAfterUpdate);
    }

    @Test
    public void create() {
        GiftCertificateDTO certificate=new GiftCertificateDTO();
        certificate.setPrice(new BigDecimal(100));
        certificate.setName("abc");
        certificate.setDescription("Great offer");
        certificate.setDuration(3);
        giftCertificateService.create(certificate);
        assertNotEquals(certificate.getId(),0);
        TestTransaction.flagForRollback();
        TestTransaction.end();
        assertEquals(certificate.getId(),0);
    }

    @Test
    public void delete() {
        Integer count=jdbcTemplate.queryForObject("select count(id) from gift_certificate",Integer.class);
        giftCertificateService.delete(1);
        Integer count2=jdbcTemplate.queryForObject("select count(id) from gift_certificate",Integer.class);
        assertNotEquals(count,count2);
        TestTransaction.flagForRollback();
        TestTransaction.end();
        count2=jdbcTemplate.queryForObject("select count(id) from gift_certificate",Integer.class);
        assertEquals(count,count2);

    }


    @Test
    public void changePrice() {
        GiftCertificateDTO giftCertificate=giftCertificateService.getOne(1);
        giftCertificate.setPrice(new BigDecimal(123));
        GiftCertificateDTO giftCertificateAfterUpdate=giftCertificateService.getOne(1);
        assertNotEquals(giftCertificate,giftCertificateAfterUpdate);
        TestTransaction.flagForRollback();
        TestTransaction.end();
        assertEquals(giftCertificate,giftCertificateAfterUpdate);
    }
}