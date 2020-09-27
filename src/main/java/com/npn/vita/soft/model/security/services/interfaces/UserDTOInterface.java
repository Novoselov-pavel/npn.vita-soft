package com.npn.vita.soft.model.security.services.interfaces;

import com.npn.vita.soft.model.security.UserDTO;

import java.util.List;

/**
 * Interface for working with users.
 */
public interface UserDTOInterface {
    /**
     * Returns user by name.
     *
     * @param userName user's name
     * @return user or null/
     */
    UserDTO getUserByName(String userName);

    /**
     * Returns the user list.
     *
     * @return the user list or empty list.
     */
    List<UserDTO> getUserList();

    /**
     * Adds role Operator.
     *
     * @param id
     */
    void setOperatorRole(Long id);

    /**
     * Remove role Operator.
     *
     * @param id
     */
    void removeOperatorRole(Long id);
}
