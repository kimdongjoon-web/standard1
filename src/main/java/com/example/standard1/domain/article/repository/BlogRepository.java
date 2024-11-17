package com.example.standard1.domain.article.repository;

import com.example.standard1.domain.article.domain.Article;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BlogRepository extends JpaRepository<Article, Long> {
}
