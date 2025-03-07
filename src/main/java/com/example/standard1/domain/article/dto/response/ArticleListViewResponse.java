package com.example.standard1.domain.article.dto.response;

import com.example.standard1.domain.article.domain.Article;

public class ArticleListViewResponse {

    private final Long id;
    private final String title;
    private final String content;

    public ArticleListViewResponse(Article article) {
        this.id = article.getId();
        this.title = article.getTitle();
        this.content = article.getContent();
    }
}
