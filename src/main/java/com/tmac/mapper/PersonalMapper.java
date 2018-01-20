package com.tmac.mapper;

import com.tmac.entity.User;

public class PersonalMapper {
    public static User mapToModifiedUser(final User existUser, final User user) {
        existUser.setUsername(user.getUsername());
        existUser.setPhone(user.getPhone());
        existUser.setSex(user.getSex());
        existUser.setDepartment(user.getDepartment());
        return existUser;
    }
}
