package com.epam.esm.oauth2.service.impl;


import com.epam.esm.oauth2.repository.domain.User;
import com.epam.esm.oauth2.repository.UserRepository;
import com.epam.esm.oauth2.service.UserService;
import com.epam.esm.oauth2.service.dto.UserDTO;
import com.epam.esm.oauth2.service.util.UserDTOConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * The type User service.
 */
@Service
@Transactional
public class UserServiceImpl implements UserService {

    private UserRepository repository;

    /**
     * Instantiates a new User service.
     *
     * @param repository the repository
     */
    @Autowired
    public UserServiceImpl(UserRepository repository) {
        this.repository = repository;
    }


    @Override
    public void save(UserDTO userDTO) {

        User user = UserDTOConverter.map(userDTO, User.class);
        user.setPassword(new BCryptPasswordEncoder().encode(userDTO.getPassword()));
        repository.save(user);
    }


}
