package com.tmac.repository;

import com.tmac.entity.Role;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface RoleRepository extends PagingAndSortingRepository<Role, String> {
    Page<Role> findAllByIdContainingAndNameContaining(final String id, final String name, final Pageable pageable);

    List<Role> findById(final String id);

    Integer countById(final String id);

    Integer countByName(final String name);

    List<Role> findByName(final String name);

    List<Role> findAllByNameContainingOrIdContaining(final String name, final String id);
}
