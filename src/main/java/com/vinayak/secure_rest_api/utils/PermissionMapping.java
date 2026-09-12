package com.vinayak.secure_rest_api.utils;

import com.vinayak.secure_rest_api.entities.enums.Permissions;
import com.vinayak.secure_rest_api.entities.enums.Roles;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class PermissionMapping {

    private static final Map<Roles, Set<Permissions>> map = Map.of(
            Roles.USER, Set.of(
                    Permissions.TASK_VIEW,
                    Permissions.TASK_CREATE,
                    Permissions.TASK_UPDATE,
                    Permissions.TASK_DELETE,
                    Permissions.USER_VIEW,
                    Permissions.USER_DELETE
                    ),

            Roles.ADMIN,Set.of(
                    Permissions.USER_VIEW,
                    Permissions.USER_CREATE,
                    Permissions.USER_DELETE,
                    Permissions.USER_UPDATE,
                    Permissions.TASK_VIEW,
                    Permissions.TASK_DELETE
                    ),

            Roles.EMPLOYEE,Set.of(),
            Roles.MANAGER,Set.of()
    );

    public static Set<SimpleGrantedAuthority> getAuthoritiesForRoles(Roles role) {
        return map
                .getOrDefault(role, Set.of())
                .stream()
                .map(permissions -> new SimpleGrantedAuthority(permissions.name()))
                .collect(Collectors.toSet());
    }

}
