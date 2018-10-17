package com.epam.esm.oauth2.repository;


import com.epam.esm.oauth2.repository.domain.User;

import java.util.Optional;

/**
 * The interface User repository.
 */
public interface UserRepository {
    /**
     * Save.
     *
     * @param user the user
     */
    void save(User user);

    /**
     * Find by username optional.
     *
     * @param username the username
     * @return the optional
     */
    Optional<User> findByUsername(String username);



}
