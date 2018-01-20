package com.tmac.controller;

import com.tmac.entity.Function;
import com.tmac.service.FunctionService;
import com.tmac.vo.ValidationResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("functions")
public class FunctionController {
    private final FunctionService functionService;

    @Autowired
    public FunctionController(final FunctionService functionService) {
        this.functionService = functionService;
    }

    @GetMapping
    @PreAuthorize("hasAuthority('F12000')")
    public Page<Function> findAll(
            @RequestParam(required = false) final String id,
            @RequestParam(required = false) final String name,
            @RequestParam final Integer page,
            @RequestParam final Integer size) {
        return this.functionService.selectList(id, name, page - 1, size);
    }

    @PostMapping
    @PreAuthorize("hasAuthority('F12001')")
    Function save(@RequestBody final Function function) {
        return this.functionService.save(function);
    }

    @PutMapping
    @PreAuthorize("hasAuthority('F12001')")
    Function modify(@RequestBody final Function function) {

        return this.functionService.modify(function);
    }

    @DeleteMapping()
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("hasAuthority('F12001')")
    public void delete(final String[] ids) {
        this.functionService.delete(ids);
    }

    @GetMapping(value = "/idUnique")
    @PreAuthorize("hasAuthority('F12000')")
    public ResponseEntity<ValidationResponse> idUnique(@RequestParam final String id) {
        return ResponseEntity.ok(
                this.functionService.idUnique(id)
        );
    }

    @GetMapping(value = "/nameUnique")
    @PreAuthorize("hasAuthority('F12000')")
    public ResponseEntity<ValidationResponse> nameUnique(@RequestParam final String name) {
        return ResponseEntity.ok(
                this.functionService.nameUnique(name)
        );
    }
}
