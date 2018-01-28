package com.tmac.controller;

import com.tmac.entity.Article;
import com.tmac.entity.User;
import com.tmac.service.ArticleService;
import com.tmac.service.UserService;
import com.tmac.vo.ArticleVo;
import com.tmac.vo.ValidationResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("index")
public class IndexController {
    private final ArticleService articleService;
    private final UserService userService;

    @Autowired
    public IndexController(final ArticleService articleService, final UserService userService) {
        this.articleService = articleService;
        this.userService = userService;
    }


    @GetMapping(value = "articles")
    public Page<ArticleVo> findAllArticles(@RequestParam final Integer page,
                                           @RequestParam final Integer size,
                                           @RequestParam(required = false) final String type) {
        return this.articleService.indexFindAll(page - 1, size,type);
    }

    @GetMapping(value = "articles/{id}")
    public Article findArticle(@PathVariable("id") final String id) {
        return this.articleService.find(id);
    }

    @GetMapping("users/accountUnique")
    public ResponseEntity<ValidationResponse> nameUnique(@RequestParam final String account) {
        return ResponseEntity.ok(
                this.userService.nameUnique(account)
        );
    }

    @PostMapping("register")
    User save(@RequestBody final User user) {
        return this.userService.save(user);
    }
}
