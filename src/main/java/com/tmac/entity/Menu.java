package com.tmac.entity;

import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;

@Entity
@EntityListeners(AuditingEntityListener.class)
public class Menu {

    @Id
    @GenericGenerator(name = "idGenerator", strategy = "uuid")
    @Column(name = "id", insertable = false, updatable = false)
    @GeneratedValue(generator = "idGenerator")
    private String id;

    private String name;

    private String url;

    private String logo;

    @Column(name = "function_id")
    private String functionId;

    @Column(name = "parent_id")
    private String parentId;

    @Column(name = "sort_id")
    private Integer sortId;

    @CreatedBy
    private String creater;

    @Column(name = "create_time", nullable = false)
    @Temporal(TemporalType.DATE)
    @CreatedDate
    private Date creareTime;

    @LastModifiedBy
    private String modifier;

    @Column(name = "modify_time")
    @Temporal(TemporalType.DATE)
    @LastModifiedDate
    private Date modifyTime;

    public String getFunctionId() {
        return functionId;
    }

    public void setFunctionId(final String functionId) {
        this.functionId = functionId;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(final String parentId) {
        this.parentId = parentId;
    }

    public String getId() {
        return id;
    }

    public void setId(final String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(final String url) {
        this.url = url;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(final String logo) {
        this.logo = logo;
    }

    public Integer getSortId() {
        return sortId;
    }

    public void setSortId(final Integer sortId) {
        this.sortId = sortId;
    }

    public String getCreater() {
        return creater;
    }

    public void setCreater(final String creater) {
        this.creater = creater;
    }

    public Date getCreareTime() {
        return creareTime;
    }

    public void setCreareTime(final Date creareTime) {
        this.creareTime = creareTime;
    }

    public String getModifier() {
        return modifier;
    }

    public void setModifier(final String modifier) {
        this.modifier = modifier;
    }

    public Date getModifyTime() {
        return modifyTime;
    }

    public void setModifyTime(final Date modifyTime) {
        this.modifyTime = modifyTime;
    }
}
