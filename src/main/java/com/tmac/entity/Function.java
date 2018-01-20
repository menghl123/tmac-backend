package com.tmac.entity;

import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Entity
@EntityListeners(AuditingEntityListener.class)
public class Function {
    @Id
    @Column(name = "id", updatable = false)
    @NotNull(message = "{function.id.not.null}")
    private String id;

    @NotNull(message = "{function.name.not.null}")
    private String name;

    @NotNull(message = "{function.creater.not.null}")
    @CreatedBy
    private String creater;

    @Column(name = "create_time", nullable = false)
    @Temporal(TemporalType.DATE)
    @CreatedDate
    private Date createTime;

    @LastModifiedBy
    private String modifier;

    @Column(name = "modify_time")
    @Temporal(TemporalType.DATE)
    @LastModifiedDate
    private Date modifyTime;

    public Function(final String id) {
        this.id = id;
    }

    public Function() {
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

    public String getCreater() {
        return creater;
    }

    public void setCreater(final String creater) {
        this.creater = creater;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(final Date createTime) {
        this.createTime = createTime;
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
