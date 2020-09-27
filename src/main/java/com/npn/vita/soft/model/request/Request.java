package com.npn.vita.soft.model.request;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.npn.vita.soft.model.security.UserDTO;
import org.json.simple.JSONObject;

import javax.persistence.*;
import java.util.Objects;

/**
 * Provides the request representation.
 */
@Entity
@Table(name = "request_storage")
public class Request {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id", updatable = false, nullable = false)
    private Long id;

    @Column(name = "request_state", nullable = false)
    private RequestState state;

    @Column(name = "request_message", nullable = false)
    private String message;

    @Column(name = "request_header", nullable = false)
    private String header;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private UserDTO owner;


    public String getMessage() {
        return message;
    }

    /**
     * @deprecated This method is only for JPA provider.
     */
    @Deprecated
    public Request() {
    }

    /**
     * Creates a new request
     *
     * @param state state of request
     * @param message request's message
     * @param header request's header
     * @param owner request's owner
     */
    public Request(RequestState state, String message, String header, UserDTO owner) {
        this.state = state;
        this.message = message;
        this.header = header;
        this.owner = owner;
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
     * Sets the request's message.
     *
     * @param message the request's message.
     */
    public void setMessage(String message) {
        this.message = message;
    }

    /**
     * Returns the request's header.
     *
     * @return request's header
     */
    public String getHeader() {
        return header;
    }

    /**
     * Sets the request's header.
     *
     * @param header the request's header.
     */
    public void setHeader(String header) {
        this.header = header;
    }

    /**
     * Returns the representation of header with format for Operator.
     *
     * @return the request's header.
     */
    @JsonIgnore
    public String getFormattedHeader() {
        if (header!=null && !header.isEmpty()) {
            StringBuilder builder = new StringBuilder();
            for (int i = 0; i < header.length()-1; i++) {
                builder
                        .append(header.charAt(i))
                        .append("-");
            }
            builder.append(header.charAt(header.length()-1));
            return builder.toString();
        }
        return header;
    }

    /**
     * Returns the request's state.
     *
     * @return the request's state.
     */
    public RequestState getState() {
        return state;
    }

    /**
     * Sets the request's state.
     *
     * @param state the request's state.
     */
    public void setState(RequestState state) {
        this.state = state;
    }

    /**
     * Returns the request's owner.
     *
     * @return the request's owner.
     */
    public UserDTO getOwner() {
        return owner;
    }

    /**
     * @deprecated This method is only for JPA provider.
     *
     * @param owner
     */
    @Deprecated
    public void setOwner(UserDTO owner) {
        this.owner = owner;
    }

    /**
     * Return request as {@link JSONObject}.
     *
     * @return {@link JSONObject}.
     */
    @JsonIgnore
    public JSONObject getAsFormattedJson() {
        JSONObject object = new JSONObject();
        object.put("id", id);
        object.put("state", state.name());
        object.put("message", message);
        object.put("header", getFormattedHeader());
        object.put("owner", owner);
        return object;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Request request = (Request) o;
        return Objects.equals(id, request.id) &&
                state == request.state &&
                Objects.equals(message, request.message) &&
                Objects.equals(header, request.header) &&
                Objects.equals(owner, request.owner);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, state, message, header, owner);
    }
}
