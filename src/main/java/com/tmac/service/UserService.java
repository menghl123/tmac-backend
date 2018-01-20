package com.tmac.service;

import com.tmac.entity.User;
import com.tmac.mapper.UserMapper;
import com.tmac.mapper.UserRoleMapper;
import com.tmac.repository.UserRepository;
import com.tmac.vo.UserVo;
import com.tmac.vo.ValidationResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Autowired
    public UserService(final UserRepository userRepository, final UserMapper userMapper) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }


    public Page<User> selectList(final String name,
                                 final String department,
                                 final Integer page, final Integer size) {
        return this.userRepository
                .findAllByUsernameContainingAndDepartmentContaining(
                        name,
                        department,
                        new PageRequest(page, size, new Sort("username"))
                );
    }

    public User save(final User user) {
        return this.userRepository.save(user);
    }


    @Transactional
    public void relateUserRoles(final User user) {
        final User existUser = this.userRepository.findOne(user.getId());
        this.userRepository.save(UserRoleMapper.mapToModifiedUser(existUser, user));
    }

    @Modifying
    @Transactional
    public User modify(final User user) {
        final User existUser = this.userRepository.findOne(user.getId());
        return this.userRepository.save(UserMapper.mapToModifiedUser(existUser, user));
    }

    @Transactional
    public void delete(final String[] ids) {
        Arrays.stream(ids)
                .forEach(userRepository::delete);
    }

    public User selectOne(final String UserId) {
        return this.userRepository.findOne(UserId);
    }

    public ValidationResponse nameUnique(final String account) {
        return new ValidationResponse(
                !this.userRepository.findByAccount(account)
                        .stream()
                        .findAny()
                        .isPresent()
        );
    }

    public UserVo login(final String account, final String password) {
        final User user = this.userRepository.findByAccountAndPassword(account, password);
        return this.userMapper.mapToUserVo(user);
    }
}
