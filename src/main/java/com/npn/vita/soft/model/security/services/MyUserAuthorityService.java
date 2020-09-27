package com.npn.vita.soft.model.security.services;

import com.npn.vita.soft.model.security.MyUserAuthority;
import com.npn.vita.soft.model.security.UserRoles;
import com.npn.vita.soft.model.security.services.interfaces.MyUserAuthorityInterface;
import com.npn.vita.soft.model.security.services.repositories.MyUserAuthorityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Provides the user's roles representation.
 */
@Service
public class MyUserAuthorityService implements MyUserAuthorityInterface {

    MyUserAuthorityRepository repository;

    /**
     * Returns {@link MyUserAuthority} for {@link UserRoles}.
     *
     * @param role {@link UserRoles} or null
     * @return {@link MyUserAuthority}
     */
    @Override
    public MyUserAuthority getAuthorityByRole(UserRoles role) {
        return repository.getFirstByRole(role.getAuthority())
                .orElse(null);
    }

    /**
     * Returns {@link MyUserAuthority} by id.
     *
     * @param id id of MyUserAuthority
     * @return {@link MyUserAuthority} or null
     */
    @Override
    public MyUserAuthority getAuthorityByID(Long id) {
        return repository.getFirstById(id)
                .orElse(null);
    }

    /**
     * Sets a user's repository
     * @param repository a user's repository
     */
    @Autowired
    public void setRepository(MyUserAuthorityRepository repository) {
        this.repository = repository;
    }
}
