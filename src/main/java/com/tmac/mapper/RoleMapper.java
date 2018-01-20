package com.tmac.mapper;

import com.tmac.entity.Role;

public class RoleMapper {
    public static Role mapToModifiedRole(final Role existRole, final Role role) {
        existRole.setName(role.getName());
        existRole.setDescription(role.getDescription());
        return existRole;
    }
}
