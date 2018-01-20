package com.tmac.entity;

import com.tmac.enums.CommentType;
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

@Entity
@EntityListeners(AuditingEntityListener.class)
public class Comment {
    @Id
    @GenericGenerator(name = "idGenerator", strategy = "uuid")
    @Column(name = "id", insertable = false, updatable = false)
    @GeneratedValue(generator = "idGenerator")
    private String id;

    @NotNull(message = "{comment.text.not.null}")
    private String text;

    @NotNull(message = "{comment.destId.not.null}")
    @Column(name = "destId", nullable = false)
    private String destId;

    @NotNull(message = "{comment.floor.not.null}")
    @Column(name = "floor", nullable = false)
    private Integer floor;

    @NotNull(message = "{comment.commentType.not.null}")
    @Column(name = "comment_type", nullable = false)
    @Enumerated(EnumType.STRING)
    private CommentType commentType;

    @NotNull(message = "{comment.creater.not.null}")
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

    public String getText() {
        return text;
    }

    public void setText(final String text) {
        this.text = text;
    }

    public String getDestId() {
        return destId;
    }

    public void setDestId(final String destId) {
        this.destId = destId;
    }

    public CommentType getCommentType() {
        return commentType;
    }

    public void setCommentType(final CommentType commentType) {
        this.commentType = commentType;
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

    public Integer getFloor() {
        return floor;
    }

    public void setFloor(final Integer floor) {
        this.floor = floor;
    }

}
