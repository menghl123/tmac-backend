package com.tmac.controller;

import com.tmac.entity.User;
import com.tmac.service.UserService;
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

@RestController
@RequestMapping("users")
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(final UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    @PreAuthorize("hasAuthority('F14000')")
    public Page<User> findAll(
            @RequestParam(required = false) final String name,
            @RequestParam(required = false) final String department,
            @RequestParam final Integer page,
            @RequestParam final Integer size) {
        return this.userService.selectList(name, department, page - 1, size);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('F14000')")
    public User findOne(@PathVariable(value = "id") final String id) {
        return this.userService.selectOne(id);
    }

    @PostMapping
    @PreAuthorize("hasAuthority('F14001')")
    User save(@RequestBody final User user) {
        return this.userService.save(user);
    }

    @PutMapping
    @PreAuthorize("hasAuthority('F14001')")
    User modify(@RequestBody final User user) {
        return this.userService.modify(user);
    }

    @DeleteMapping()
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("hasAuthority('F14001')")
    public void delete(final String[] ids) {
        this.userService.delete(ids);
    }

    @PostMapping(value = "/relate")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("hasAuthority('F14002')")
    public void relateUserRoles(@RequestBody final User user) {
        this.userService.relateUserRoles(user);
    }

    @GetMapping(value = "/accountUnique")
    @PreAuthorize("hasAuthority('F14001')")
    public ResponseEntity<ValidationResponse> nameUnique(@RequestParam final String account) {
        return ResponseEntity.ok(
                this.userService.nameUnique(account)
        );
    }
}
