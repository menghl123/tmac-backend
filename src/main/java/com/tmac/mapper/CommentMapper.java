package com.tmac.mapper;

import com.tmac.entity.Comment;
import com.tmac.repository.CommentRepository;
import com.tmac.repository.UserRepository;
import com.tmac.vo.CommentVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CommentMapper extends BaseMapper {
    private final CommentRepository commentRepository;
    private final UserRepository userRepository;

    @Autowired
    public CommentMapper(final CommentRepository commentRepository, final UserRepository userRepository) {
        this.commentRepository = commentRepository;
        this.userRepository = userRepository;
        getMapperFactory().classMap(Comment.class, CommentVo.class).byDefault().register();
    }

    public CommentVo mapToCommentVo(final Comment comment) {
        final CommentVo commentVo = this.mapLeft(CommentVo.class, comment);
        final Page<Comment> page = this.commentRepository.findAllByDestId(comment.getId(),
                new PageRequest(0, 10, new Sort(Sort.Direction.ASC, "floor")));
        commentVo.setReplyComments(
                this.mapToCommentVoPager(page)
        );
        commentVo.setCreater(userRepository.findOne(comment.getCreater()));
        return commentVo;
    }

    public Page<CommentVo> mapToCommentVoPager(final Page<Comment> commentPage) {
        final List<CommentVo> commentVos = commentPage.getContent()
                .stream()
                .map(this::mapToCommentVo)
                .collect(Collectors.toList());
        return new PageImpl<CommentVo>(commentVos,
                new PageRequest(commentPage.getNumber(), commentPage.getSize()),
                commentPage.getTotalElements());
    }
}
