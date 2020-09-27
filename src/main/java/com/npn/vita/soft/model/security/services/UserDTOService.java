package com.npn.vita.soft.model.security.services;

import com.npn.vita.soft.model.security.MyUserAuthority;
import com.npn.vita.soft.model.security.UserDTO;
import com.npn.vita.soft.model.security.UserRoles;
import com.npn.vita.soft.model.security.services.interfaces.MyUserAuthorityInterface;
import com.npn.vita.soft.model.security.services.interfaces.UserDTOInterface;
import com.npn.vita.soft.model.security.services.repositories.UserDTORepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserDTOService implements UserDTOInterface {
    private final static String JSON_ROLES_VARIABLE_NAME = "roles";

    private MyUserAuthorityInterface authorityService;
    private UserDTORepository repository;

    @Override
    public UserDTO getUserByName(String userName) {
        return repository.getFirstByUserName(userName)
                .orElse(null);
    }

    @Override
    public List<UserDTO> getUserList() {
        return repository
                .findAll()
                .stream()
                .sorted(Comparator.comparing(UserDTO::getUserName))
                .collect(Collectors.toList());
    }


    @Override
    public void setOperatorRole(Long id) {
        Optional<UserDTO> user = repository.findById(id);
        Assert.isTrue(user.isPresent(),"User with id = "+id+" doesn't exist");
        MyUserAuthority operatorRole = authorityService.getAuthorityByRole(UserRoles.ROLE_OPERATOR);
        UserDTO userDTO = user.get();
        userDTO.addAuthority(operatorRole);
        repository.saveAndFlush(userDTO);
    }

    @Override
    public void removeOperatorRole(Long id) {
        Optional<UserDTO> user = repository.findById(id);
        Assert.isTrue(user.isPresent(),"User with id = "+id+" doesn't exist");
        MyUserAuthority operatorRole = authorityService.getAuthorityByRole(UserRoles.ROLE_OPERATOR);
        UserDTO userDTO = user.get();
        userDTO.removeAuthority(operatorRole);
        repository.saveAndFlush(userDTO);
    }

    @Autowired
    public void setRepository(UserDTORepository repository) {
        this.repository = repository;
    }

    @Autowired
    public void setAuthorityService(MyUserAuthorityInterface authorityService) {
        this.authorityService = authorityService;
    }
}
