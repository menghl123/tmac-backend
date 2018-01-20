package com.tmac.enums;

public enum ArticleNature {
    // 原创 转载 翻译
    ORIGINAL("original"), REPRINT("reprint"), TRANSLATE("translate");
    private String name;

    private ArticleNature(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public static  ArticleNature getByName(final String name) {
        for(ArticleNature articleNature : ArticleNature.values()) {
            if(articleNature.name == name) {
                return articleNature;
            }
        }
        throw new IllegalArgumentException("No element matches " + name);
    }
}
