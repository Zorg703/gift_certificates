package com.epam.esm.repository.impl;

import com.epam.esm.domain.GiftCertificate;
import com.epam.esm.domain.GiftCertificateState;
import com.epam.esm.repository.GiftCertificateRepository;
import com.epam.esm.util.QueryGiftCertificateBuilder;
import org.hibernate.annotations.SQLDelete;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * The Gift certificate repository implementation.
 * Gets objects from data base and send in service
 */
@Repository
public class GiftCertificateRepositoryImpl implements GiftCertificateRepository {
    private static final String GET_GIFT_CERTIFICATE_BY_ID = "SELECT g from GiftCertificate g WHERE g.id =:id";
    private static final String GET_STATE = "SELECT g.state from GiftCertificate g WHERE g.id =:id";

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public void create(final GiftCertificate giftCertificate) {
        entityManager.merge(giftCertificate);

    }

    @Override
    public void update(final GiftCertificate giftCertificate) {
        entityManager.merge(giftCertificate);
    }

    @Override
    public Optional<List<GiftCertificate>> getAll(Integer page, Integer size) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<GiftCertificate> criteriaQuery = criteriaBuilder.createQuery(GiftCertificate.class);
        Root<GiftCertificate> root = criteriaQuery.from(GiftCertificate.class);
        CriteriaQuery<GiftCertificate> select = criteriaQuery.select(root);
        TypedQuery<GiftCertificate> query = entityManager.createQuery(select);

        return paginationResult(query, page, size);
    }


    @Override
    public Optional<GiftCertificate> getOne(final long id) {
        TypedQuery<GiftCertificate> query = entityManager.createQuery(GET_GIFT_CERTIFICATE_BY_ID, GiftCertificate.class);
        query.setParameter("id", id);
        List<GiftCertificate> giftCertificates = query.getResultList();
        if (giftCertificates.isEmpty()) {
            return Optional.empty();
        } else {
            return Optional.of(giftCertificates.get(0));
        }
    }

    @Override
    public void delete(final long id) {
        TypedQuery<GiftCertificateState> query = entityManager.createQuery(GET_STATE, GiftCertificateState.class);
        query.setParameter("id", id);
        GiftCertificateState state = query.getSingleResult();
        if(state.equals(GiftCertificateState.INACTIVE)) {
            entityManager.remove(getOne(id).get());
        }
        if(state.equals(GiftCertificateState.ACTIVE)) {
          GiftCertificate certificate= getOne(id).get();
          certificate.setState(GiftCertificateState.DELETED);
          update(certificate);
        }
    }



    @Override
    public Map<Integer, Optional<List<GiftCertificate>>> getGiftCertificatesByTextAndTags(List<String> tags, Integer page, Integer size, String text,String sort) {
        Map<TypedQuery<GiftCertificate>,Integer> buildQuery=QueryGiftCertificateBuilder.buildQuery(tags, text, sort, entityManager);
        TypedQuery<GiftCertificate> query=null;
        Integer count=null;
        for(Map.Entry<TypedQuery<GiftCertificate>,Integer> entry:buildQuery.entrySet()){
            query=entry.getKey();
            count=entry.getValue();
        }
        Optional<List<GiftCertificate>> list=paginationResult(query, page, size);
        Map<Integer, Optional<List<GiftCertificate>>> map=new HashMap<>();
        map.put(count,list);

        return map;
    }

}
