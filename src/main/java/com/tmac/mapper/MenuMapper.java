package com.tmac.mapper;

import com.tmac.entity.Menu;
import com.tmac.vo.TreeNode;
import org.springframework.stereotype.Service;

public class MenuMapper {

    public static TreeNode mapToTreeNode(final Menu menu) {
        final TreeNode treeNode = new TreeNode();
        treeNode.setId(menu.getId());
        treeNode.setLogo(menu.getLogo());
        treeNode.setName(menu.getName());
        return treeNode;
    }

    public static Menu mapToModifiedMenu(final Menu existMenu, final Menu menu) {
        existMenu.setName(menu.getName());
        existMenu.setUrl(menu.getUrl());
        existMenu.setLogo(menu.getLogo());
        existMenu.setSortId(menu.getSortId());
        return existMenu;
    }
}
