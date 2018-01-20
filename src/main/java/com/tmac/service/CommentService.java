package com.tmac.service;

import com.tmac.entity.Comment;
import com.tmac.mapper.CommentMapper;
import com.tmac.repository.CommentRepository;
import com.tmac.vo.CommentVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by menghonglin on 2017/8/27.
 */
@Service
public class CommentService {
    final private CommentRepository commentRepository;
    final private CommentMapper commentMapper;

    @Autowired
    public CommentService(final CommentRepository commentRepository, final CommentMapper commentMapper) {
        this.commentRepository = commentRepository;
        this.commentMapper = commentMapper;
    }


    public Page<CommentVo> findAll(final String destId, final Integer page, final Integer size) {
        final Page<Comment> pager = this.commentRepository.findAllByDestId(destId,
                new PageRequest(page, size, new Sort(Sort.Direction.ASC, "floor")));
        return this.commentMapper.mapToCommentVoPager(pager);
    }

    public Comment find(final String id) {
        return this.commentRepository.findOne(id);
    }

    public Comment save(final Comment comment) {
        this.commentRepository.findTopByDestIdOrderByFloorDesc(comment.getDestId())
                .ifPresent(maxFloorComment -> {
                    Integer maxFloor = maxFloorComment.getFloor();
                    maxFloor++;
                    comment.setFloor(maxFloor);
                });
        return this.commentRepository.save(comment);
    }

    @Transactional
    public void delete(final String[] ids) {

    }

    public Integer countByUserId(final String userId) {
        return this.commentRepository.countByCreater(userId);
    }

}
