package com.npn.vita.soft.model.request.services;

import com.npn.vita.soft.model.request.Request;
import com.npn.vita.soft.model.request.RequestState;
import com.npn.vita.soft.model.request.services.interfaces.RequestInterface;
import com.npn.vita.soft.model.request.services.repositories.RequestRepository;
import com.npn.vita.soft.model.security.UserDTO;
import com.npn.vita.soft.model.security.services.interfaces.UserDTOInterface;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.List;
import java.util.Optional;

@Service
public class RequestService implements RequestInterface {

    private RequestRepository repository;
    private UserDTOInterface userService;

    /**
     * Creates a {@link Request}, request body should be a Json object with charset UTF-8 and with not Null parameters:
     * "message" and "header".
     *
     * @param json Json string
     * @param userName user's name
     * @throws ParseException on error parse Json string
     * @throws IllegalArgumentException if Json object's parameters "message" and "header" is null or user's name doesn't exist.
     */
    @Override
    public void addNewRequest(String json, String userName) throws ParseException {
        JSONParser parser = new JSONParser();
        JSONObject object = (JSONObject) parser.parse(json);
        Request request = getRequestFromJSONObject(object, userName);
        repository.saveAndFlush(request);
    }

    /**
     * Returns the {@link Request} list for user.
     *
     * @param userName user's name
     * @return request list
     * @throws IllegalArgumentException if user's name doesn't exist.
     */
    @Override
    public List<Request> getRequestsByUserName(String userName) {
        return repository.findAllByOwner(getUser(userName));
    }

    /**
     * Returns the {@link Request} for user.
     *
     * @param id request's id
     * @param userName user's name
     * @return {@link Request}
     * @throws IllegalArgumentException if request doesn't exist
     */
    @Override
    public Request getRequestByIdAndUserName(Long id, String userName) {
        return getRequest(id, userName);
    }

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
    @Override
    public void updateRequest(Long id, String json, String userName) throws ParseException {
        JSONParser parser = new JSONParser();
        JSONObject object = (JSONObject) parser.parse(json);

        Request request = updateRequest(id, userName, object);
        repository.saveAndFlush(request);
    }

    /**
     * Sends the user's request.
     *
     * @param id request's id
     * @param userName user's name
     * @throws IllegalArgumentException if request doesn't exist or user's name doesn't exist.
     */
    @Override
    public void sendRequest(Long id, String userName) {
        Request request = getRequest(id, userName);
        if (request.getState() == RequestState.RAW || request.getState() == RequestState.SEND) {
            request.setState(RequestState.SEND);
        }
        repository.saveAndFlush(request);
    }

    /**
     * Gets the requests list with state "SEND".
     *
     * @return the requests list as Json array.
     */
    @Override
    public String getSendedRequestWithFormat() {
        List<Request> requests = repository.findAllByStateOrderByHeader(RequestState.SEND);
        JSONArray array = new JSONArray();
        requests.forEach(x-> array.add(x.getAsFormattedJson()));
        return array.toJSONString();
    }

    /**
     * Accepts the user's request.
     *
     * @param id request's id
     * @throws IllegalArgumentException if request doesn't exist.
     * @throws IllegalStateException if request state isn't SEND or ACCEPT
     */
    @Override
    public void acceptRequest(Long id) {
        Request request = getRequest(id);
        if (request.getState() == RequestState.SEND || request.getState() == RequestState.ACCEPT) {
            request.setState(RequestState.ACCEPT);
        } else {
            throw new IllegalStateException("Can't accept request with state " + request.getState().name());
        }
        repository.saveAndFlush(request);
    }

    /**
     * Rejects the user's request.
     *
     * @param id request's id
     * @throws IllegalArgumentException if request doesn't exist.
     * @throws IllegalStateException if request state isn't SEND or REJECT
     */
    @Override
    public void rejectRequest(Long id) {
        Request request = getRequest(id);
        if (request.getState() == RequestState.SEND || request.getState() == RequestState.REJECT) {
            request.setState(RequestState.REJECT);
        } else {
            throw new IllegalStateException("Can't accept request with state " + request.getState().name());
        }
        repository.saveAndFlush(request);
    }


    private Request getRequest(Long id, String userName) {
        Optional<Request> request = repository.findFirstByIdAndOwner(id,getUser(userName));
        Assert.isTrue(request.isPresent(),"Message doesn't exist");
        return request.get();
    }

    private Request getRequest(Long id) {
        Optional<Request> request = repository.findById(id);
        Assert.isTrue(request.isPresent(),"Message doesn't exist");
        return request.get();
    }

    private Request updateRequest(Long id, String userName, JSONObject object) {
        Request request = getRequest(id, userName);
        String message = (String) object.get("message");
        String header = (String) object.get("header");
        checkRequestParts(message, header);


        request.setMessage(message);
        request.setHeader(header);
        return request;
    }

    private void checkRequestParts(String message, String header){
        Assert.noNullElements(new String[]{message, header},"Request field can't be null.");
        Assert.isTrue(!message.trim().isEmpty(),"Message can't be empty.");
        Assert.isTrue(!header.trim().isEmpty(),"Header can't be empty.");
    }

    private UserDTO getUser(String userName) {
        UserDTO user = userService.getUserByName(userName);
        Assert.notNull(user,"Unexpected error, user " + userName + " doesn't exist.");
        return user;
    }

    private Request getRequestFromJSONObject(JSONObject object, String userName) {
        String message = (String) object.get("message");
        String header = (String) object.get("header");
        UserDTO userDTO = getUser(userName);
        checkRequestParts(message, header);


        return new Request(RequestState.RAW,message,header,userDTO);
    }

    /**
     * Sets RequestRepository
     *
     * @param repository RequestRepository
     */
    @Autowired
    public void setRepository(RequestRepository repository) {
        this.repository = repository;
    }

    /**
     * Sets userService
     *
     * @param userService userService
     */
    @Autowired
    public void setUserService(UserDTOInterface userService) {
        this.userService = userService;
    }
}
