package com.tmac.service;

import com.tmac.entity.Menu;
import com.tmac.mapper.MenuMapper;
import com.tmac.repository.MenuRepository;
import com.tmac.repository.TestRepository;
import com.tmac.dynamic.model.QueryParameters;
import com.tmac.validator.MenuValidator;
import com.tmac.vo.MenuVo;
import com.tmac.vo.TreeNode;
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
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class MenuService {
    private MenuRepository menuRepository;
    private MenuValidator menuValidator;
    private TestRepository testRepository;

    @Autowired
    public MenuService(final MenuRepository menuRepository, final MenuValidator menuValidator, final TestRepository testRepository) {
        this.menuRepository = menuRepository;
        this.menuValidator = menuValidator;
        this.testRepository = testRepository;
    }

    public Page<Menu> selectList(final String parentId,
                                 final String name,
                                 final String url,
                                 final Integer page, final Integer size) {
        final QueryParameters queryParameters = new QueryParameters();
        this.testRepository.findAll(queryParameters, Menu.class);
        return this.menuRepository
                .findAllByParentIdContainingAndNameContainingAndUrlContaining(
                        parentId, name, url, new PageRequest(page, size, new Sort("sortId")));
    }

    public Menu get(final String id) {
        return this.menuRepository.findOne(id);
    }

    public Menu save(final Menu menu) {
        this.menuValidator.validateBeforeSave(menu);
        return this.menuRepository.save(menu);
    }

    @Transactional
    public void delete(final String[] ids) {
        Arrays.stream(ids)
                .forEach(menuRepository::delete);
    }

    public ValidationResponse nameUnique(final String name) {
        return new ValidationResponse(Objects.equals(this.menuRepository.countByName(name), 0));
    }

    public List<TreeNode> getTree(final String pId) {
        return this.menuRepository.findByParentId(pId)
                .stream()
                .map(MenuMapper::mapToTreeNode)
                .map(this::fillChildren)
                .collect(Collectors.toList());
    }

    private TreeNode fillChildren(final TreeNode treeNode) {
        final List<Menu> childrenList = this.menuRepository.findByParentId(treeNode.getId());
        childrenList.stream()
                .findAny()
                .ifPresent((anyNode) -> {
                    treeNode.setChildren(
                            childrenList.stream()
                                    .map(MenuMapper::mapToTreeNode)
                                    .collect(Collectors.toList())
                    );
                });
        return treeNode;
    }

    @Modifying
    public Menu modify(final Menu menu) {
        this.menuValidator.validateBeforeModify(menu);
        final Menu existMenu = this.menuRepository.findOne(menu.getId());
        return this.menuRepository.save(MenuMapper.mapToModifiedMenu(existMenu, menu));
    }

    public List<MenuVo> generateMenuTree(final String originId, final List<String> functionIds) {
        return this.menuRepository.findAllByParentIdAndFunctionIdInOrderBySortIdAsc(originId, functionIds)
                .stream()
                .map((menu) -> {
                    final MenuVo menuVo = new MenuVo();
                    menuVo.setRouterLink(menu.getUrl());
                    menuVo.setIcon(menu.getLogo());
                    menuVo.setLabel(menu.getName());
                    menuVo.setChildren(this.generateMenuTree(menu.getId(), functionIds));
                    return menuVo;
                }).collect(Collectors.toList());
    }
}
