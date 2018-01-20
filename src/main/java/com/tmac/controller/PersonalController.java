package com.tmac.controller;

import com.google.common.collect.ImmutableMap;
import com.tmac.entity.User;
import com.tmac.enums.ArticleStatus;
import com.tmac.mapper.PersonalMapper;
import com.tmac.service.ArticleService;
import com.tmac.service.CommentService;
import com.tmac.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("personal")
public class PersonalController {
    private final UserService userService;
    private final ArticleService articleService;
    private final CommentService commentService;

    @Autowired
    public PersonalController(final UserService userService, final ArticleService articleService, final CommentService commentService) {
        this.userService = userService;
        this.articleService = articleService;
        this.commentService = commentService;
    }


    @GetMapping(value = "changeImg")
    public User changeImg(@RequestParam final String img,
                          @RequestParam final String userId) {
        final User user = this.userService.selectOne(userId);
        user.setImg(img);
        return this.userService.save(user);
    }

    @GetMapping(value = "dashboard")
    public Map<String, Integer> dashboard(@RequestParam final String userId) {
        final Integer articleCount = this.articleService.countByUserId(userId, ArticleStatus.DELETED);
        final Integer commentCount = this.commentService.countByUserId(userId);
        final Integer loginCount = 5;
        return ImmutableMap.of(
                "articleCount", articleCount,
                "commentCount", commentCount,
                "loginCount", loginCount);
    }

    @PostMapping(value = "supplyUserInfo")
    public User supplyUserInfo(@RequestBody final User user) {
        final User exsitUser = this.userService.selectOne(user.getId());
        return this.userService.save(PersonalMapper.mapToModifiedUser(exsitUser, user));
    }


}
