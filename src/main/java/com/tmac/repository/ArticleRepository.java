package com.tmac.repository;

import com.tmac.entity.Article;
import com.tmac.enums.ArticleStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface ArticleRepository extends PagingAndSortingRepository<Article, String> {
    Article findTopByOrderBySortIdDesc();

    Page<Article> findAllByCreater(final String creater, final Pageable pageable);

    Page<Article> findAllByIsPrivateAndStatusNotAndTypeContaining(final Integer isPrivate,
                                                        final ArticleStatus articleStatus,
                                                        final String type,
                                                        final Pageable pageable);

    Integer countByCreaterAndStatusNot(final String creater, final ArticleStatus articleStatus);
}
