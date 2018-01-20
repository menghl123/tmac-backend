package com.tmac.enums;

public enum CommentType {
    // 博客评论 评论评论
    COMMENT_COMMENT("comment_comment"), ARTICLE_COMMENT("article_comment");
    private String name;

    private CommentType(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
