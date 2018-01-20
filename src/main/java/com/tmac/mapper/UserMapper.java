package com.tmac.mapper;

import com.tmac.entity.Function;
import com.tmac.entity.User;
import com.tmac.service.MenuService;
import com.tmac.vo.UserVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.stream.Collectors;

@Service
public class UserMapper extends BaseMapper {
    private static final String MENU_ORIGIN_ID = "0000000000000000000000000";
    private final MenuService menuService;

    @Autowired
    public UserMapper(final MenuService menuService) {
        this.menuService = menuService;
        getMapperFactory().classMap(User.class, UserVo.class).byDefault().register();
    }

    public UserVo mapToUserVo(final User user) {
        final UserVo userVo = this.mapLeft(UserVo.class, user);
        userVo.setFunctions(user.getRoles()
                .stream()
                .flatMap(role -> role.getFunctions().stream())
                .distinct()
                .collect(Collectors.toList()));
        userVo.setMenus(
                this.menuService.generateMenuTree(
                        MENU_ORIGIN_ID,
                        userVo.getFunctions().stream().map(Function::getId).collect(Collectors.toList())
                )
        );
        return userVo;
    }

    public static User mapToModifiedUser(final User existUser, final User user) {
        existUser.setDepartment(user.getDepartment());
        existUser.setUsername(user.getUsername());
        existUser.setPassword(user.getPassword());
        return existUser;
    }
}
