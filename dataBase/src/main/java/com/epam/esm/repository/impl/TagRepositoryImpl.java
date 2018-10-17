package com.epam.esm.repository.impl;

import com.epam.esm.domain.GiftCertificate;
import com.epam.esm.domain.Tag;
import com.epam.esm.repository.TagRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * The Tag repository implementation.
 * * Gets objects from data base and send in service
 */
@Transactional
@Repository
public class TagRepositoryImpl implements TagRepository {
    private static final String GET_TOP_TAG = "SELECT name,id,u from tags where m=(select max(m) from tags) group by name,id,u;";
    private static final String GET_TAG_BY_ID = "SELECT t from Tag t WHERE t.id =:id";
    private int defaultPageSize = 10;

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Optional<List<Tag>> getAll(Integer page, Integer size) {
        CriteriaQuery<Tag> criteriaQuery = entityManager.getCriteriaBuilder().createQuery(Tag.class);
        Root<Tag> root = criteriaQuery.from(Tag.class);
        CriteriaQuery<Tag> select = criteriaQuery.select(root);
        TypedQuery<Tag> query = entityManager.createQuery(select);
        return paginationResult(query, page, size);
    }

    @Override
    public Optional<Tag> getOne(final long id) {
        TypedQuery<Tag> query = entityManager.createQuery(GET_TAG_BY_ID, Tag.class);
        query.setParameter("id", id);
        List<Tag> tags = query.getResultList();
        if (tags.isEmpty()) {
            return Optional.empty();
        } else {
            return Optional.of(tags.get(0));
        }
    }

    @Override
    public void delete(final long id) {
        Optional<Tag> tag = getOne(id);
        tag.ifPresent(tag1 -> entityManager.remove(tag1));
    }

    @Override
    public void create(final Tag tag) {
        entityManager.persist(tag);
    }

    @Override
    public Optional<List<Tag>> getTagByName(final String name) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Tag> criteriaQuery = criteriaBuilder.createQuery(Tag.class);
        Root<Tag> root = criteriaQuery.from(Tag.class);
        criteriaQuery.select(root).where(criteriaBuilder.equal(root.get("name"), name));
        List<Tag> tags = entityManager.createQuery(criteriaQuery).getResultList();
        return tags.isEmpty() ? Optional.empty() : Optional.of(tags);

    }

    @Override
    public Optional<List<Object[]>> getTopTag() {
        Query query = entityManager.createNativeQuery(GET_TOP_TAG);
        return Optional.of(query.getResultList());

    }

}
