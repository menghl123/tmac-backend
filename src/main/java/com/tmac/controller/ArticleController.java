package com.tmac.controller;

import com.tmac.entity.Article;
import com.tmac.security.CurrentUser;
import com.tmac.service.ArticleService;
import com.tmac.vo.ArticleVo;
import com.tmac.vo.UserVo;
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
@RequestMapping("articles")
public class ArticleController {
    private final ArticleService articleService;

    @Autowired
    public ArticleController(final ArticleService articleService) {
        this.articleService = articleService;
    }


    @GetMapping
    public Page<ArticleVo> findAll(@CurrentUser UserVo userVo,
                                   @RequestParam final Integer page,
                                   @RequestParam final Integer size) {
        return this.articleService.findAll(userVo.getId(), page - 1, size);
    }

    @PostMapping
    Article save(@RequestBody final Article article) {
        return this.articleService.save(article);
    }

    @PutMapping
    Article modify(@RequestBody final Article article) {
        return this.articleService.modify(article);
    }

    @DeleteMapping()
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(final String[] ids) {
        this.articleService.delete(ids);
    }

    @GetMapping(value = "/increaseViewCount")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void increaseViewCount(@RequestParam final String id) {
        this.articleService.increaseViewCount(id);
    }

    @GetMapping(value = "/{id}")
    public Article get(@PathVariable("id") final String id) {
        return articleService.find(id);
    }

    @GetMapping(value = "/changeCanComment")
    public Article changeCanComment(@RequestParam final String id) {
        return this.articleService.changeCanComment(id);
    }

    @GetMapping(value = "/topArticle")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void topArticle(@RequestParam final String id) {
        this.articleService.topArticle(id);
    }

}
