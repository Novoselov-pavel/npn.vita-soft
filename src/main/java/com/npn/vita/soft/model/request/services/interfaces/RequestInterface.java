package com.npn.vita.soft.model.request.services.interfaces;

import com.npn.vita.soft.model.request.Request;
import org.json.simple.parser.ParseException;

import java.util.List;

/**
 * Interface for working with {@link Request}
 */
public interface RequestInterface {

    /**
     * Creates a {@link Request}, request body should be a Json object with charset UTF-8 and with not Null parameters:
     * "message" and "header".
     *
     * @param json Json string
     * @param userName user's name
     * @throws ParseException on error parse Json string
     * @throws IllegalArgumentException if Json object's parameters "message" and "header" is null or user's name doesn't exist.
     */
    void addNewRequest(String json, String userName) throws ParseException;

    /**
     * Returns the {@link Request} list for user.
     *
     * @param userName user's name
     * @return request list
     * @throws IllegalArgumentException if user's name doesn't exist.
     */
    List<Request> getRequestsByUserName(String userName);

    /**
     * Returns the {@link Request} for user.
     *
     * @param id request's id
     * @param userName user's name
     * @return {@link Request}
     * @throws IllegalArgumentException if request doesn't exist
     */
    Request getRequestByIdAndUserName(Long id, String userName);

    /**
     * Updates the user's request with state "RAW" by id.
     * Json string should be a Json object with not Null parameters:
     * "message" and "header".
     *
     * @param id request's id
     * @param json json sting
     * @param userName user's name
     * @throws ParseException on error parse Json string
     * @throws IllegalArgumentException if request doesn't exist, Json object's parameters "message" and "header" is null
     * or user's name doesn't exist.
     */
    void updateRequest(Long id, String json, String userName) throws ParseException;

    /**
     * Sends the user's request.
     *
     * @param id request's id
     * @param userName user's name
     * @throws IllegalArgumentException if request doesn't exist or user's name doesn't exist.
     */
    void sendRequest(Long id, String userName);

    /**
     * Gets the requests list with state "SEND".
     *
     * @return the requests list as Json array.
     */
    String getSendedRequestWithFormat();

    /**
     * Accepts the user's request.
     *
     * @param id request's id
     * @throws IllegalArgumentException if request doesn't exist.
     * @throws IllegalStateException if request state isn't SEND or ACCEPT
     */
    void acceptRequest(Long id);

    /**
     * Rejects the user's request.
     *
     * @param id request's id
     * @throws IllegalArgumentException if request doesn't exist.
     * @throws IllegalStateException if request state isn't SEND or REJECT
     */
    void rejectRequest(Long id);
}
