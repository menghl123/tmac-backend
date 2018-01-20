package com.tmac.mapper;

import com.tmac.entity.Role;

public class RoleFunctionMapper {
    public static Role mapToModifiedRole(final Role existRole, final Role role) {
        existRole.setFunctions(role.getFunctions());
        return existRole;
    }
}
