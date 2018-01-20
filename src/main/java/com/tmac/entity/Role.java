package com.tmac.entity;

import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;

@Entity
@EntityListeners(AuditingEntityListener.class)
public class Role {

    @Id
    @Column(name = "id", updatable = false)
    @NotNull(message = "{role.id.not.null}")
    private String id;

    @NotNull(message = "{role.name.not.null}")
    private String name;

    @ManyToMany(cascade = CascadeType.PERSIST, fetch = FetchType.EAGER)
    @JoinTable(name = "role_function",
            joinColumns = {@JoinColumn(name = "role_id", referencedColumnName = "id")},
            inverseJoinColumns = {@JoinColumn(name = "function_id", referencedColumnName = "id")}
    )
    @NotFound(action = NotFoundAction.IGNORE)
    private List<Function> functions;

    private String description;

    public List<Function> getFunctions() {
        return functions;
    }

    public void setFunctions(final List<Function> functions) {
        this.functions = functions;
    }

    @NotNull(message = "{role.creater.not.null}")
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

    public String getDescription() {
        return description;
    }

    public void setDescription(final String description) {
        this.description = description;
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
