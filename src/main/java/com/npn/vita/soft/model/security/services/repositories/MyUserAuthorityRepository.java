package com.npn.vita.soft.model.security.services.repositories;

import com.npn.vita.soft.model.security.MyUserAuthority;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * Interface for access to the table with {@link MyUserAuthority} in a database.
 */
public interface MyUserAuthorityRepository extends JpaRepository<MyUserAuthority, Long> {

    Optional<MyUserAuthority> getFirstByRole(String role);

    Optional<MyUserAuthority> getFirstById(Long id);
}
