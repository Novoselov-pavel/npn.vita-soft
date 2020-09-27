package com.npn.vita.soft.controllers;

import com.npn.vita.soft.model.request.Request;
import com.npn.vita.soft.model.request.services.interfaces.RequestInterface;
import com.npn.vita.soft.model.security.UserRoles;
import org.json.simple.parser.ParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.security.Principal;
import java.util.List;

/**
 * Provides controllers for all action with {@link Request}.
 */
@RestController
@RequestMapping("/api/requests")
public class RequestController {

    private final Logger logger = LoggerFactory.getLogger(RequestController.class);

    private RequestInterface service;

    /**
     * Creates a Request, request body should be a Json object with charset UTF-8 and with not Null parameters:
     * "message" and "header".
     * Returns HTTP status 400 on error.
     *
     * @param principal {@link Principal}
     * @param body Json object
     */
    @Secured(UserRoles.Code.USER)
    @PostMapping(value = "/add", consumes = {"application/json;charset=UTF-8"})
    public void createRequest(Principal principal, @RequestBody String body) {
        try {
            service.addNewRequest(body, principal.getName());
        } catch (ParseException exception) {
            logger.warn("Failed to create a request, request body parsing error", exception);
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,exception.getMessage());
        } catch (IllegalArgumentException exception) {
            logger.warn("Failed to create a request, illegal message parts", exception);
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,exception.getMessage());
        }
    }

    /**
     * Return the user's requests.
     * Returns HTTP status 400 on error.
     *
     * @param principal {@link Principal}
     * @return array of Json objects in charset UTF-8
     */
    @Secured(UserRoles.Code.USER)
    @GetMapping(value = "/getMyRequests", produces = {"application/json;charset=UTF-8"})
    public List<Request> getUserRequest(Principal principal){
        try {
            return service.getRequestsByUserName(principal.getName());
        } catch (IllegalArgumentException exception) {
            logger.warn("Failed to get requests", exception);
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,exception.getMessage());
        }
    }

    /**
     *
     * Return the user's request by id.
     * Returns HTTP status 400 on error.
     *
     * @param id request's id
     * @param principal {@link Principal}
     * @return {@link Request} as Json object
     */
    @Secured(UserRoles.Code.USER)
    @GetMapping(value = "/{id}", produces = {"application/json;charset=UTF-8"})
    public Request getUserRequest(@PathVariable(value = "id") Long id, Principal principal){
        try {
            return service.getRequestByIdAndUserName(id,principal.getName());
        } catch (IllegalArgumentException exception) {
            logger.warn("Failed to get a request", exception);
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,exception.getMessage());
        }
    }

    /**
     * Updates the user's request with state "RAW" by id.
     * Request body should be a Json object with charset UTF-8 and with not Null parameters:
     * "message" and "header".
     * Returns HTTP status 400 on error.
     *
     * @param id request's id
     * @param principal {@link Principal}
     * @param body Json object
     */
    @Secured(UserRoles.Code.USER)
    @PutMapping(value = "/{id}/updateRawRequest", consumes = {"application/json;charset=UTF-8"})
    public void updateRawRequest(@PathVariable(value = "id") Long id, Principal principal, @RequestBody String body) {
        try {
            service.updateRequest(id, body, principal.getName());
        } catch (ParseException exception) {
            logger.warn("Failed to update a request", exception);
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,exception.getMessage());
        } catch (IllegalArgumentException exception) {
            logger.warn("Failed to update a request", exception);
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,exception.getMessage());
        }
    }

    /**
     * Sends the user's request.
     * Returns HTTP status 400 on error.
     *
     * @param id request's id
     * @param principal  {@link Principal}
     */
    @Secured(UserRoles.Code.USER)
    @PutMapping(value = "/{id}/send")
    public void sendRequest(@PathVariable(value = "id") Long id, Principal principal) {
        try {
            service.sendRequest(id, principal.getName());
        } catch (IllegalArgumentException exception) {
            logger.warn("Failed to send a request with id = " + id, exception);
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,exception.getMessage());
        }
    }

    /**
     * Accepts the user's request.
     * Returns HTTP status 400 on error.
     *
     * @param id request's id
     */
    @Secured(UserRoles.Code.OPERATOR)
    @PutMapping(value = "/{id}/accept")
    public void acceptRequest(@PathVariable(value = "id") Long id){
        try {
            service.acceptRequest(id);
        }catch (IllegalArgumentException | IllegalStateException exception) {
            logger.warn("Failed to accept a request with id = " + id, exception);
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,exception.getMessage());
        }
    }

    /**
     * Rejects the user's request.
     * Returns HTTP status 400 on error.
     *
     * @param id request's id
     */
    @Secured(UserRoles.Code.OPERATOR)
    @PutMapping(value = "/{id}/reject")
    public void rejectRequest(@PathVariable(value = "id") Long id){
        try {
            service.rejectRequest(id);
        }catch (IllegalArgumentException | IllegalStateException exception) {
            logger.warn("Failed to reject a request with id = " + id, exception);
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,exception.getMessage());
        }
    }


    /**
     * Returns all the requests with status "SEND".
     *
     * @return  array of Json objects in charset UTF-8
     */
    @Secured(UserRoles.Code.OPERATOR)
    @GetMapping(value = "/getSendedRequests", produces = {"application/json;charset=UTF-8"})
    public String getSendedRequest() {
        return service.getSendedRequestWithFormat();
    }

    /**
     * Sets the requests' service.
     *
     * @param service
     */
    @Autowired
    public void setService(RequestInterface service) {
        this.service = service;
    }
}
