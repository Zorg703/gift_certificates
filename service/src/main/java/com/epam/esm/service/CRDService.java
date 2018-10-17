package com.epam.esm.service;

import com.epam.esm.dto.PageParamDTO;

import java.util.List;


/**
 * The interface Crd service.
 * Gets data from controller and pass then in to repository
 *
 * @param <T> the type parameter
 *            <p>
 *            Gets data from controller and pass then in to repository
 */
public interface CRDService<T> {
    /**
     * Create Object
     *
     * @param t the t
     * @return the long
     */
    void create(final T t);


    List<T> getAll(PageParamDTO param);

    /**
     * Gets one.
     *
     * @param id the id
     * @return the one
     */
    T getOne(final long id);

    /**
     * Delete.
     *
     * @param id the id
     */
    void delete(final long id);

}
