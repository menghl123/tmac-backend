package com.tmac.vo;

import java.util.List;

public class MenuVo {
    private String routerLink;
    private String label;
    private String icon;
    private List<MenuVo> children;

    public String getRouterLink() {
        return routerLink;
    }

    public void setRouterLink(final String routerLink) {
        this.routerLink = routerLink;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(final String label) {
        this.label = label;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(final String icon) {
        this.icon = icon;
    }

    public List<MenuVo> getChildren() {
        return children;
    }

    public void setChildren(final List<MenuVo> children) {
        this.children = children;
    }
}
