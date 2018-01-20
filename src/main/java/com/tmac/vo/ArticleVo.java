package com.tmac.vo;

import com.tmac.entity.User;
import com.tmac.enums.ArticleNature;
import com.tmac.enums.ArticleStatus;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.Column;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import java.util.Date;

public class ArticleVo {
    private String id;

    private String title;

    private String text;

    private ArticleNature nature;

    private String labels;

    private Integer isPrivate;

    private String type;

    private Integer isAnonymous;

    private Integer sortId;

    private Integer viewCount;

    private Integer canComment;

    private ArticleStatus status;

    private Integer commentCount;

    private User author;

    private String creater;

    private Date createTime;

    private String modifier;

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

    public String getType() {
        return type;
    }

    public void setType(final String type) {
        this.type = type;
    }

    public Integer getIsAnonymous() {
        return isAnonymous;
    }

    public void setIsAnonymous(final Integer isAnonymous) {
        this.isAnonymous = isAnonymous;
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

    public ArticleStatus getStatus() {
        return status;
    }

    public void setStatus(final ArticleStatus status) {
        this.status = status;
    }

    public Integer getCommentCount() {
        return commentCount;
    }

    public void setCommentCount(final Integer commentCount) {
        this.commentCount = commentCount;
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

    public User getAuthor() {
        return author;
    }

    public void setAuthor(final User author) {
        this.author = author;
    }
}
