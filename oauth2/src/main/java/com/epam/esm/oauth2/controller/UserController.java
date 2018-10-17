package com.epam.esm.oauth2.controller;


import com.epam.esm.oauth2.repository.domain.User;
import com.epam.esm.oauth2.service.UserService;
import com.epam.esm.oauth2.service.dto.UserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;


/**
 * The type User controller.
 */
@RestController
@RequestMapping("/rest/users")
public class UserController { ;

    private UserService userService;

    /**
     * Instantiates a new User controller.
     *
     * @param userService the user service
     */
    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    /**
     * Sign up response entity.
     *
     * @param user the user
     * @return the response entity
     */
    @PostMapping("/signUp")
    public ResponseEntity signUp(final @RequestBody @Valid UserDTO user ){
        userService.save(user);
        return new ResponseEntity(HttpStatus.OK);
    }
}
