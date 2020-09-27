package com.npn.vita.soft.model.security;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.Collection;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;

/**
 * Provides the user representation.
 */
@Entity
@Table(name = "usrs")
public class UserDTO {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id",updatable = false,nullable = false)
    private Long id;

    @Column(name = "user_name",unique = true,nullable = false)
    private String userName;

    @JsonIgnore
    @Column(name = "pass_hash", nullable = false)
    private String password;

    @OneToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "usrs_authority",
            joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id"))
    private final Set<MyUserAuthority> authorities = new CopyOnWriteArraySet<>();


    /**
     * Create new User
     */
    public UserDTO() {
    }

    /**
     * Returns id
     *
     * @return the id
     */
    public Long getId() {
        return id;
    }

    /**
     * @deprecated This method is only for JPA provider.
     *
     * @param id id
     */
    @Deprecated
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Returns the user's name.
     *
     * @return the user's name
     */
    public String getUserName() {
        return userName;
    }

    /**
     * Sets the user's name.
     *
     * @param userName the user's name
     */
    public void setUserName(String userName) {
        this.userName = userName;
    }

    /**
     * Returns the hashed password.
     *
     * @return the hashed password.
     */
    public String getPassword() {
        return password;
    }

    /**
     * Set the hashed password.
     *
     * @param password the hashed password.
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Returns the user's authorities
     *
     * @return {@link MyUserAuthority} collection
     */
    public Collection<MyUserAuthority> getAuthorities() {
        return authorities;
    }

    /**
     * Add the authority.
     *
     * @param authority {@link MyUserAuthority}
     */
    public void addAuthority(MyUserAuthority authority) {
        authorities.add(authority);
    }

    /**
     * Removes the authority.
     *
     * @param authority {@link MyUserAuthority}
     */
    public void removeAuthority(MyUserAuthority authority) {
        authorities.remove(authority);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserDTO userDTO = (UserDTO) o;
        return id.equals(userDTO.id) &&
                Objects.equals(userName, userDTO.userName) &&
                Objects.equals(password, userDTO.password);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, userName, password);
    }
}
