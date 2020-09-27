package com.npn.vita.soft.model.security.services;

import com.npn.vita.soft.model.security.UserDTO;
import com.npn.vita.soft.model.security.services.interfaces.UserDTOInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

/**
 * Provides the {@link UserDetailsService} representation.
 */
public class MyUserDetailService implements UserDetailsService {
    private UserDTOInterface userDTOService;


    /**
     * Locates the user based on the username.
     *
     * @param userName the username identifying the user whose data is required.
     * @throws UsernameNotFoundException if the user could not be found.
     */
    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        UserDTO user = userDTOService.getUserByName(userName);
        if (user == null) throw new UsernameNotFoundException("User "+userName+" not found");
        return new User(user.getUserName(),user.getPassword(),user.getAuthorities());
    }

    /**
     * Sets a users service
     *
     * @param userDTOService  a users service
     */
    @Autowired
    public void setUserDTOService(UserDTOInterface userDTOService) {
        this.userDTOService = userDTOService;
    }
}
