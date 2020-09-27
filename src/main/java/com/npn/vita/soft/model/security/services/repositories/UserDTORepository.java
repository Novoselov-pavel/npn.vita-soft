package com.npn.vita.soft.model.security.services.repositories;

import com.npn.vita.soft.model.security.UserDTO;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * Interface for access to the table with {@link UserDTO} in a database.
 */
public interface UserDTORepository extends JpaRepository<UserDTO, Long> {
    Optional<UserDTO> getFirstByUserName(String userName);
}
