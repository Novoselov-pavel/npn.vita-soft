package com.npn.vita.soft.model.request.services.repositories;

import com.npn.vita.soft.model.request.Request;
import com.npn.vita.soft.model.request.RequestState;
import com.npn.vita.soft.model.security.UserDTO;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

/**
 * Interface for access to the table with {@link Request} in a database.
 */
public interface RequestRepository extends JpaRepository<Request,Long> {

    List<Request> findAllByOwner(UserDTO owner);

    List<Request> findAllByStateOrderByHeader(RequestState state);

    Optional<Request> findFirstByIdAndOwner(Long id, UserDTO user);
}
