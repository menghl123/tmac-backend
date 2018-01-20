package com.tmac.service;

import com.tmac.entity.Role;
import com.tmac.mapper.RoleFunctionMapper;
import com.tmac.mapper.RoleMapper;
import com.tmac.repository.RoleRepository;
import com.tmac.validator.RoleValidator;
import com.tmac.vo.ValidationResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;

@Service
public class RoleService {
    private final RoleRepository roleRepository;
    private final RoleValidator roleValidator;

    @Autowired
    public RoleService(final RoleRepository roleRepository, final RoleValidator roleValidator) {
        this.roleRepository = roleRepository;
        this.roleValidator = roleValidator;
    }

    public Page<Role> selectList(final String id,
                                 final String name,
                                 final Integer page, final Integer size) {
        return this.roleRepository
                .findAllByIdContainingAndNameContaining(
                        id,
                        name,
                        new PageRequest(page, size, new Sort("id"))
                );
    }

    public Role save(final Role role) {
        this.roleValidator.validateBeforeSave(role);
        return this.roleRepository.save(role);
    }

    @Modifying
    @Transactional
    public Role modify(final Role role) {
        this.roleValidator.validateBeforeModify(role);
        final Role existRole = this.roleRepository.findOne(role.getId());
        return this.roleRepository.save(RoleMapper.mapToModifiedRole(existRole, role));
    }

    @Transactional
    public void delete(final String[] ids) {
        Arrays.stream(ids)
                .forEach(roleRepository::delete);
    }

    public ValidationResponse idUnique(final String id) {
        return new ValidationResponse(
                !this.roleRepository.findById(id)
                        .stream()
                        .findAny()
                        .isPresent()
        );
    }

    public ValidationResponse nameUnique(final String name) {
        return new ValidationResponse(
                !this.roleRepository.findByName(name)
                        .stream()
                        .findAny()
                        .isPresent()
        );
    }

    public Role selectOne(final String roleId) {
        return this.roleRepository.findOne(roleId);
    }

    @Transactional
    public void relateRoleFunctions(final Role role) {
        final Role existRole = this.roleRepository.findOne(role.getId());
        this.roleRepository.save(RoleFunctionMapper.mapToModifiedRole(existRole, role));
    }

    public List<Role> search(final String term) {
        return this.roleRepository.findAllByNameContainingOrIdContaining(term, term);
    }
}
