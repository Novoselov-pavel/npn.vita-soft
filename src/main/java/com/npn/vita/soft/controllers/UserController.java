package com.npn.vita.soft.controllers;

import com.npn.vita.soft.model.security.UserDTO;
import com.npn.vita.soft.model.security.UserRoles;
import com.npn.vita.soft.model.security.services.interfaces.UserDTOInterface;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

/**
 * Provides controllers for all action with {@link UserDTO}.
 *
 */
@Secured(UserRoles.Code.ADMIN)
@RestController
@RequestMapping("/api/users")
public class UserController {

    private final Logger logger = LoggerFactory.getLogger(UserController.class);
    private UserDTOInterface userService;


    /**
     * Returns the users list.
     *
     * @return users list as Json object
     */
    @GetMapping(value = {"","/"}, produces = {"application/json;charset=UTF-8"})
    public List<UserDTO> getUsers(){
        return userService.getUserList();
    }


    /**
     * Adds a role Operator to the user.
     * Returns HTTP status 400 on error.
     *
     * @param id user's id
     */
    @PutMapping(value = "/{userId}/addOperatorRole")
    public void setOperatorRole(@PathVariable(value = "userId") Long id) {
        try {
            userService.setOperatorRole(id);
        } catch (IllegalArgumentException exception) {
            logger.warn("Failed to add role 'operator' to user with id=" + id + " ", exception);
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,exception.getMessage());
        }

    }

    /**
     * Removes a role Operator from the user.
     * Returns HTTP status 400 on error.
     *
     * @param id user's id
     */
    @PutMapping(value = "/{userId}/removeOperatorRole")
    public void removeOperatorRole(@PathVariable(value = "userId") Long id) {
        try {
            userService.removeOperatorRole(id);
        } catch (IllegalArgumentException exception) {
            logger.warn("Failed to remove role 'operator' to user with id=" + id + " ", exception);
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,exception.getMessage());
        }

    }

    /**
     * Sets the users' service.
     *
     * @param userService the users' service.
     */
    @Autowired
    public void setUserService(UserDTOInterface userService) {
        this.userService = userService;
    }
}
