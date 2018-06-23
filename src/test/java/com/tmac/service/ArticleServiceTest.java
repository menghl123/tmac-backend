package com.tmac.service;

import com.google.common.collect.ImmutableList;
import com.tmac.AbstractTest;
import com.tmac.entity.Article;
import com.tmac.mapper.ArticleMapper;
import com.tmac.repository.ArticleRepository;
import com.tmac.vo.ArticleVo;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

/**
 * Created by menghonglin on 2017/8/27.
 */
public class ArticleServiceTest extends AbstractTest {
    @InjectMocks
    private ArticleService artivleService;
    @Mock
    private ArticleRepository articleRepository;
    @Mock
    private ArticleMapper articleMapper;

    @Test
    public void should_success_whith_config() throws Exception {
        //given
        final int page = 1;
        final int size = 8;
        final Article article = new Article();
        final PageRequest pageable = new PageRequest(page, size, new Sort(Sort.Direction.DESC, "sortId"));
        final Page<Article> pages = new PageImpl<Article>(ImmutableList.of(article));
        when(articleRepository.findAllByCreater("1", pageable))
                .thenReturn(pages);
        when(articleMapper.mapToArticleVoPager(pages))
                .thenReturn(new PageImpl<ArticleVo>(ImmutableList.of(new ArticleVo())));
        //when

        final Page<ArticleVo> articles = artivleService.findAll("1", page, size);

        //then
        assertThat(articles.getContent().size()).isEqualTo(1);
    }
}