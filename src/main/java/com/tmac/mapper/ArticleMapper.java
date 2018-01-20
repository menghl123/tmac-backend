package com.tmac.mapper;

import com.tmac.entity.Article;
import com.tmac.entity.Comment;
import com.tmac.enums.ArticleStatus;
import com.tmac.repository.CommentRepository;
import com.tmac.repository.UserRepository;
import com.tmac.vo.ArticleVo;
import com.tmac.vo.CommentVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class ArticleMapper extends BaseMapper {
    private final CommentRepository commentRepository;
    private final UserRepository userRepository;

    @Autowired
    public ArticleMapper(final CommentRepository commentRepository, final UserRepository userRepository) {
        this.commentRepository = commentRepository;
        this.userRepository = userRepository;
        getMapperFactory().classMap(Comment.class, CommentVo.class).byDefault().register();
    }

    public ArticleVo mapToArticleVo(final Article article) {
        final ArticleVo articleVo = this.mapLeft(ArticleVo.class, article);
        articleVo.setCommentCount(commentRepository.countByDestId(article.getId()));
        articleVo.setAuthor(userRepository.findOne(article.getCreater()));
        return articleVo;
    }

    public Page<ArticleVo> mapToArticleVoPager(final Page<Article> articlePage) {
        final List<ArticleVo> articleVos = articlePage.getContent()
                .stream()
                .map(this::mapToArticleVo)
                .collect(Collectors.toList());
        return new PageImpl<ArticleVo>(articleVos,
                new PageRequest(articlePage.getNumber(), articlePage.getSize()),
                articlePage.getTotalElements());
    }


    public static Article mapToModifiedArticle(final Article existArticle, final Article article) {
        existArticle.setTitle(article.getTitle());
        existArticle.setText(article.getText());
        existArticle.setIsAnonymous(article.getIsAnonymous());
        existArticle.setIsPrivate(article.getIsPrivate());
        existArticle.setCanComment(article.getCanComment());
        if (Objects.equals(article.getStatus(), ArticleStatus.DRAFT)) {
            existArticle.setStatus(article.getStatus());
        }
        return existArticle;
    }
}
