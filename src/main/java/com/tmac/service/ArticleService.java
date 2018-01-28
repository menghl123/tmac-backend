package com.tmac.service;

import com.tmac.entity.Article;
import com.tmac.enums.ArticleStatus;
import com.tmac.mapper.ArticleMapper;
import com.tmac.repository.ArticleRepository;
import com.tmac.vo.ArticleVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.Objects;
import java.util.Optional;

/**
 * Created by menghonglin on 2017/8/27.
 */
@Service
public class ArticleService {
    private final ArticleRepository articleRepository;
    private final ArticleMapper articleMapper;

    @Autowired
    public ArticleService(ArticleRepository articleRepository, final ArticleMapper articleMapper) {
        this.articleRepository = articleRepository;
        this.articleMapper = articleMapper;
    }

    public Page<ArticleVo> indexFindAll(final Integer page, final Integer size, final String type) {
        final Page<Article> pager = this.articleRepository.findAllByIsPrivateAndStatusNotAndTypeContaining(
                0,
                ArticleStatus.DELETED,
                type,
                new PageRequest(page, size, new Sort(Sort.Direction.DESC, "createTime")));
        return this.articleMapper.mapToArticleVoPager(pager);
    }

    public Page<ArticleVo> findAll(final String creater, final Integer page, final Integer size) {
        final Page<Article> pager = this.articleRepository.findAllByCreater(creater,
                new PageRequest(page, size, new Sort(Sort.Direction.DESC, "sortId")));
        return this.articleMapper.mapToArticleVoPager(pager);
    }

    public Article find(final String id) {
        return this.articleRepository.findOne(id);
    }

    public Article save(final Article article) {
        return this.articleRepository.save(article);
    }

    @Transactional
    public void delete(final String[] ids) {
        Arrays.stream(ids)
                .map(articleRepository::findOne)
                .forEach(article -> {
                    if (Objects.equals(article.getStatus(), ArticleStatus.DELETED)) {
                        articleRepository.delete(article.getId());
                    } else {
                        article.setStatus(ArticleStatus.DELETED);
                        articleRepository.save(article);
                    }
                });
    }

    public void increaseViewCount(final String id) {
        final Article article = this.articleRepository.findOne(id);
        Integer viewCount = Optional.ofNullable(article.getViewCount())
                .orElse(0);
        viewCount++;
        article.setViewCount(viewCount);
        this.articleRepository.save(article);
    }

    public Article changeCanComment(final String id) {
        final Article article = this.articleRepository.findOne(id);
        if (Objects.equals(article.getCanComment(), 1)) {
            article.setCanComment(0);
        } else {
            article.setCanComment(1);
        }
        return this.articleRepository.save(article);
    }

    public void topArticle(final String id) {
        final Article nowTopArticle = this.articleRepository.findTopByOrderBySortIdDesc();
        Integer nowMaxSortId = nowTopArticle.getSortId();
        nowMaxSortId++;
        final Article article = this.articleRepository.findOne(id);
        article.setSortId(nowMaxSortId);
        this.articleRepository.save(article);
    }

    @Modifying
    @Transactional
    public Article modify(final Article article) {
        final Article existArticle = this.articleRepository.findOne(article.getId());
        return this.articleRepository.save(ArticleMapper.mapToModifiedArticle(existArticle, article));
    }

    public Integer countByUserId(final String userId, final ArticleStatus articleStatus) {
        return this.articleRepository.countByCreaterAndStatusNot(userId, articleStatus);
    }
}
