package com.npn.vita.soft.model.security.services.interfaces;

import com.npn.vita.soft.model.security.MyUserAuthority;
import com.npn.vita.soft.model.security.UserRoles;

/**
 * Interface for working with user's roles.
 */
public interface MyUserAuthorityInterface {
    /**
     * Returns {@link MyUserAuthority} for {@link UserRoles}.
     *
     * @param role {@link UserRoles} or null
     * @return {@link MyUserAuthority}
     */
    MyUserAuthority getAuthorityByRole(UserRoles role);

    /**
     * Returns {@link MyUserAuthority} by id.
     *
     * @param id id of MyUserAuthority
     * @return {@link MyUserAuthority} or null
     */
    MyUserAuthority getAuthorityByID(Long id);
}
