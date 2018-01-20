package com.tmac.repository;

import com.tmac.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface UserRepository extends PagingAndSortingRepository<User, String> {
    List<User> findByAccount(final String account);

    List<User> findByUsername(final String name);

    Page<User> findAllByUsernameContainingAndDepartmentContaining(final String name,
                                                                  final String department,
                                                                  final Pageable pageable);

    User findByAccountAndPassword(final String account, final String password);

}
