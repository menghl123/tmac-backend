package com.tmac.entity;

import com.tmac.enums.ArticleNature;
import com.tmac.enums.ArticleStatus;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * Created by menghonglin on 2017/8/27.
 */
@Entity
@EntityListeners(AuditingEntityListener.class)
public class Article {
    @Id
    @GenericGenerator(name = "idGenerator", strategy = "uuid")
    @Column(name = "id", insertable = false, updatable = false)
    @GeneratedValue(generator = "idGenerator")
    private String id;

    @NotNull(message = "{article.title.not.null}")
    private String title;

    @NotNull(message = "{article.text.not.null}")
    private String text;

    @NotNull(message = "{article.nature.not.null}")
    @Enumerated(EnumType.STRING)
    private ArticleNature nature;

    private String labels;

    @Column(name = "is_private")
    private Integer isPrivate;

    @NotNull(message = "{article.type.not.null}")
    private String type;

    @Column(name = "is_anonymous")
    private Integer isAnonymous;

    @Column(name = "sort_id")
    private Integer sortId;

    @Column(name = "view_count")
    private Integer viewCount;

    @Column(name = "can_comment")
    private Integer canComment;

    @NotNull(message = "{article.status.not.null}")
    @Enumerated(EnumType.STRING)
    private ArticleStatus status;

    @NotNull(message = "{function.creater.not.null}")
    @CreatedBy
    private String creater;

    @Column(name = "create_time", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    @CreatedDate
    private Date createTime;

    @LastModifiedBy
    private String modifier;

    @Column(name = "modify_time")
    @Temporal(TemporalType.TIMESTAMP)
    @LastModifiedDate
    private Date modifyTime;

    public String getId() {
        return id;
    }

    public void setId(final String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(final String title) {
        this.title = title;
    }

    public String getText() {
        return text;
    }

    public void setText(final String text) {
        this.text = text;
    }

    public String getType() {
        return type;
    }

    public void setType(final String type) {
        this.type = type;
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

    public ArticleNature getNature() {
        return nature;
    }

    public void setNature(final ArticleNature nature) {
        this.nature = nature;
    }

    public String getLabels() {
        return labels;
    }

    public void setLabels(final String labels) {
        this.labels = labels;
    }

    public Integer getIsPrivate() {
        return isPrivate;
    }

    public void setIsPrivate(final Integer isPrivate) {
        this.isPrivate = isPrivate;
    }

    public Integer getIsAnonymous() {
        return isAnonymous;
    }

    public void setIsAnonymous(final Integer isAnonymous) {
        this.isAnonymous = isAnonymous;
    }

    public ArticleStatus getStatus() {
        return status;
    }

    public void setStatus(final ArticleStatus status) {
        this.status = status;
    }

    public Integer getSortId() {
        return sortId;
    }

    public void setSortId(final Integer sortId) {
        this.sortId = sortId;
    }

    public Integer getViewCount() {
        return viewCount;
    }

    public void setViewCount(final Integer viewCount) {
        this.viewCount = viewCount;
    }

    public Integer getCanComment() {
        return canComment;
    }

    public void setCanComment(final Integer canComment) {
        this.canComment = canComment;
    }
}
