package peaksoft.enums;

import org.springframework.security.core.GrantedAuthority;

public enum Role implements GrantedAuthority {
    CHEF,
    ADMIN,
    WAITER;

    @Override
    public String getAuthority() {
        return name();
    }
}
