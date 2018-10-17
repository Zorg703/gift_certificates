package com.epam.esm.repository;

import com.epam.esm.domain.GiftCertificate;

import javax.persistence.TypedQuery;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * The interface Repository.
 *
 * @param <T> the type parameter
 */
public interface Repository<T> {
    int defaultPageSize = 10;
    int defaultPage = 1;

    /**
     * Gets all.
     *
     * @return the all
     */
    Optional<List<T>> getAll(Integer page, Integer size);

    /**
     * Gets one.
     *
     * @param id the id
     * @return the one
     */
    Optional<T> getOne(long id);

    /**
     * Delete.
     *
     * @param id the id
     */
    void delete(long id);

    /**
     * Create long.
     *
     * @param t the t
     */
    void create(T t);

    default Optional<List<T>> paginationResult(TypedQuery<T> query, Integer page, Integer size) {
        if (page != null && size != null && page > 0 && size > 0) {
            query.setFirstResult((page - 1) * size).setMaxResults(size);
        } else if (page != null && size == null && page > 0) {
            query.setFirstResult((page - 1) * defaultPageSize).setMaxResults(defaultPageSize);
        } else if (page == null && size != null && size > 0) {
            query.setMaxResults(size);
        } else {
            query.setMaxResults(defaultPageSize);
        }
        return Optional.of(query.getResultList());
    }
}
