package com.epam.esm.service.integration;

import com.epam.esm.configuration.TestConfig;
import com.epam.esm.domain.Tag;
import com.epam.esm.service.TagService;
import com.epam.esm.service.impl.TagServiceImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TestTransaction;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {TagServiceImpl.class, TestConfig.class})
@ActiveProfiles("test")
@Transactional
public class TagServiceTest {
    @Autowired
    private TagService tagService;
    @Autowired
    private JdbcTemplate jdbcTemplate;
    private RowMapper<Tag> mapper= (rs, row) -> new Tag(rs.getLong("id"), rs.getString("name"));

    @Test
    public void delete() {
        Integer count=jdbcTemplate.queryForObject("select count(id) from tag",Integer.class);
        tagService.delete(2);
        Integer count2=jdbcTemplate.queryForObject("select count(id) from tag",Integer.class);
        assertNotEquals(count,count2);
        TestTransaction.flagForRollback();
        TestTransaction.end();
        assertEquals(count,count2);

    }

}