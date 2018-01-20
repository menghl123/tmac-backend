package com.tmac.enums;

public enum ArticleStatus {
    // 草稿 未审批 已审批
    DRAFT("draft"), NOT_EXAMINED("not_examined"), EXAMINED("examined"), DELETED("deleted");
    private String name;

    // 构造方法
    private ArticleStatus(final String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public static ArticleStatus getByName(final String name) {
        for (ArticleStatus articleStatus : ArticleStatus.values()) {
            if (articleStatus.name == name) {
                return articleStatus;
            }
        }
        throw new IllegalArgumentException("No element matches " + name);
    }
}
