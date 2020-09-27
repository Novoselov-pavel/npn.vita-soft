package com.npn.vita.soft.model.security;

/**
 * Enum with roles.
 */
public enum UserRoles {
    ROLE_USER(Code.USER), ROLE_OPERATOR(Code.OPERATOR), ROLE_ADMIN(Code.ADMIN);

    private final String authority;

    UserRoles(final String authority) {
        this.authority = authority;
    }

    public String getAuthority() {
        return authority;
    }

    @SuppressWarnings("InnerClassMayBeStatic")
    public class Code {
        public static final String ADMIN = "ROLE_ADMIN";
        public static final String USER = "ROLE_USER";
        public static final String OPERATOR = "ROLE_OPERATOR";
    }

}
