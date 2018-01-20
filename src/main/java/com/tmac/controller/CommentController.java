package com.tmac.controller;

import com.tmac.entity.Comment;
import com.tmac.service.CommentService;
import com.tmac.vo.CommentVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("comments")
public class CommentController {
    private final CommentService commentService;

    @Autowired
    public CommentController(final CommentService commentService) {
        this.commentService = commentService;
    }


    @GetMapping
    public Page<CommentVo> findAll(
            @RequestParam final String destId,
            @RequestParam final Integer page,
            @RequestParam final Integer size) {
        return this.commentService.findAll(destId, page - 1, size);
    }

    @PostMapping
    Comment save(@RequestBody final Comment comment) {
        return this.commentService.save(comment);
    }

    @PutMapping
    Comment modify(@RequestBody final Comment comment) {
        return this.commentService.save(comment);
    }

    @DeleteMapping()
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(final String[] ids) {
        this.commentService.delete(ids);
    }

    @GetMapping(value = "/{id}")
    public Comment get(@PathVariable("id") final String id) {
        return commentService.find(id);
    }

}
