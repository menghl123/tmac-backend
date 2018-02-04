package com.tmac.dynamic.model;

public class QueryParameters {
    private Integer pageIndex;
    private Integer pageSize;
    private Sort[] sorts;
    private Filter[] filters;

    public int getPageIndex() {
        return pageIndex;
    }

    public void setPageIndex(final Integer pageIndex) {
        this.pageIndex = pageIndex;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(final Integer pageSize) {
        this.pageSize = pageSize;
    }

    public Sort[] getSorts() {
        return sorts;
    }

    public void setSorts(final Sort[] sorts) {
        this.sorts = sorts;
    }

    public Filter[] getFilters() {
        return filters;
    }

    public void setFilters(final Filter[] filters) {
        this.filters = filters;
    }
}
