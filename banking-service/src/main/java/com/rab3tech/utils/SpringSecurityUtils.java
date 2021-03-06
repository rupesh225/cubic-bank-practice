package com.rab3tech.utils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Set;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class SpringSecurityUtils {

    public static Collection<GrantedAuthority> convertGrantedAuthority(Set<String> privilegesList){
        Collection<GrantedAuthority> authList = new ArrayList<GrantedAuthority>(10);
        privilegesList.forEach(item->{
            authList.add(new SimpleGrantedAuthority(item));
        });
        return authList;
    }

    /**
     *
     * @param role
     * @return
     */
    public static boolean hasRole(String role) {
        Collection<GrantedAuthority> authorities = (Collection<GrantedAuthority>) SecurityContextHolder.getContext().
                getAuthentication().getAuthorities();
        boolean hasRole = false;
        for (GrantedAuthority authority : authorities) {
            hasRole = authority.getAuthority().equalsIgnoreCase(role);
            if (hasRole) {
                break;
            }
        }
        return hasRole;
    }

}
