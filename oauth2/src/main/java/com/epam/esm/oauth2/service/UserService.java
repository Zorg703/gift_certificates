package com.epam.esm.oauth2.service;

import com.epam.esm.oauth2.service.dto.UserDTO;

/**
 * The interface User service.
 */
public interface UserService {
    /**
     * Save.
     *
     * @param user the user
     */
    void save(UserDTO user);
}
