package com.tmac.controller;

import com.tmac.entity.Role;
import com.tmac.service.RoleService;
import com.tmac.vo.ValidationResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("roles")
public class RoleController {
    private final RoleService roleService;

    @Autowired
    public RoleController(final RoleService roleService) {
        this.roleService = roleService;
    }

    @GetMapping
    @PreAuthorize("hasAuthority('F13000')")
    public Page<Role> findAll(
            @RequestParam(required = false) final String id,
            @RequestParam(required = false) final String name,
            @RequestParam final Integer page,
            @RequestParam final Integer size) {
        return this.roleService.selectList(id, name, page - 1, size);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('F13000')")
    public Role findOne(@PathVariable(value = "id") final String roleId) {
        return this.roleService.selectOne(roleId);
    }

    @PostMapping
    @PreAuthorize("hasAuthority('F13001')")
    Role save(@RequestBody final Role role) {
        return this.roleService.save(role);
    }

    @PutMapping
    @PreAuthorize("hasAuthority('F13001')")
    Role modify(@RequestBody final Role role) {
        return this.roleService.modify(role);
    }

    @DeleteMapping()
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("hasAuthority('F13001')")
    public void delete(final String[] ids) {
        this.roleService.delete(ids);
    }

    @GetMapping(value = "/idUnique")
    @PreAuthorize("hasAuthority('F13000')")
    public ResponseEntity<ValidationResponse> idUnique(@RequestParam final String id) {
        return ResponseEntity.ok(
                this.roleService.idUnique(id)
        );
    }

    @GetMapping(value = "/search")
    @PreAuthorize("hasAuthority('F13000')")
    public ResponseEntity<List<Role>> search(@RequestParam final String term) {
        return ResponseEntity.ok(
                this.roleService.search(term)
        );
    }

    @GetMapping(value = "/nameUnique")
    @PreAuthorize("hasAuthority('F13000')")
    public ResponseEntity<ValidationResponse> nameUnique(@RequestParam final String name) {
        return ResponseEntity.ok(
                this.roleService.nameUnique(name)
        );
    }

    @PostMapping(value = "/relate")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("hasAuthority('F13002')")
    public void relateRoleFunction(@RequestBody final Role role) {
        this.roleService.relateRoleFunctions(role);
    }
}
