package com.tmac.vo;

import com.tmac.entity.Comment;
import com.tmac.entity.User;
import com.tmac.enums.CommentType;
import org.springframework.data.domain.Page;

import java.util.Date;

public class CommentVo {

    private String id;

    private String text;

    private String destId;

    private Integer floor;

    private CommentType commentType;

    private User creater;

    private Date createTime;

    private String modifier;

    private Date modifyTime;

    private Page<CommentVo> replyComments;

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

    public User getCreater() {
        return creater;
    }

    public void setCreater(final User creater) {
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

    public Page<CommentVo> getReplyComments() {
        return replyComments;
    }

    public void setReplyComments(final Page<CommentVo> replyComments) {
        this.replyComments = replyComments;
    }
}
