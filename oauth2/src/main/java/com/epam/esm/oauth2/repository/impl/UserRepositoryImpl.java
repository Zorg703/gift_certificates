package com.epam.esm.oauth2.repository.impl;

import com.epam.esm.oauth2.repository.domain.Role;
import com.epam.esm.oauth2.repository.domain.User;
import com.epam.esm.oauth2.repository.UserRepository;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * The type User repository.
 */
@Repository
public class UserRepositoryImpl implements UserRepository {

    @PersistenceContext
    private EntityManager entityManager;


    @Override
    public void save(User user) {
        List<Role> list=new ArrayList<>();
        Role role=entityManager.createQuery("select r from Role r where r.name='user'",Role.class).getSingleResult();
        list.add(role);
        user.setRoles(list);
        entityManager.merge(user);
    }

    @Override
    public Optional<User> findByUsername(String username) {
        CriteriaBuilder criteriaBuilder=entityManager.getCriteriaBuilder();
        CriteriaQuery<User> criteriaQuery=criteriaBuilder.createQuery(User.class);
        Root<User> root = criteriaQuery.from(User.class);
        criteriaQuery.select(root).where(criteriaBuilder.equal(root.get("username"), username));
        return Optional.of(entityManager.createQuery(criteriaQuery).getSingleResult());
    }


}
