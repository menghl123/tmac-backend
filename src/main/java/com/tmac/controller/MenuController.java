package com.tmac.controller;

import com.tmac.entity.Menu;
import com.tmac.service.MenuService;
import com.tmac.vo.TreeNode;
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
@RequestMapping("menus")
public class MenuController {
    final private MenuService menuService;

    @Autowired
    public MenuController(MenuService menuService) {
        this.menuService = menuService;
    }

    @GetMapping
    @PreAuthorize("hasAuthority('F11000')")
    public Page<Menu> findAll(@RequestParam final String parentId,
                              @RequestParam(required = false) final String name,
                              @RequestParam(required = false) final String url,
                              @RequestParam final Integer page,
                              @RequestParam final Integer size) {
        return this.menuService.selectList(parentId, name, url, page - 1, size);
    }

    @GetMapping(value = "/{id}")
    @PreAuthorize("hasAuthority('F11000')")
    public Menu get(@PathVariable("id") final String id) {
        return this.menuService.get(id);
    }

    @PostMapping
    @PreAuthorize("hasAuthority('F11001')")
    public Menu save(@RequestBody final Menu menu) {
        return this.menuService.save(menu);
    }

    @PutMapping
    @PreAuthorize("hasAnyAuthority('F11001')")
    public Menu modify(@RequestBody final Menu menu) {
        return this.menuService.modify(menu);
    }

    @DeleteMapping()
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("hasAuthority('F11001')")
    public void delete(final String[] ids) {
        this.menuService.delete(ids);
    }

    @GetMapping(value = "/nameUnique")
    @PreAuthorize("hasAuthority('F11000')")
    public ResponseEntity<ValidationResponse> nameUnique(@RequestParam final String name) {
        return ResponseEntity.ok(
                this.menuService.nameUnique(name)
        );
    }

    @GetMapping(value = "/tree")
    @PreAuthorize("hasAuthority('F11000')")
    public ResponseEntity<List<TreeNode>> getTree(@RequestParam final String pId) {
        return ResponseEntity.ok(
                this.menuService.getTree(pId)
        );
    }
}
