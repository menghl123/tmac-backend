package com.tmac.mapper;

import com.tmac.entity.User;

public class UserRoleMapper {
    public static User mapToModifiedUser(final User existUser, final User user) {
        existUser.setRoles(user.getRoles());
        return existUser;
    }
}
