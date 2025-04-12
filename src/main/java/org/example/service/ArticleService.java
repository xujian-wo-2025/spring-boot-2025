package org.example.service;


import org.example.pojo.Article;
import org.example.pojo.PageBean;
import org.springframework.stereotype.Service;

@Service
public interface ArticleService {

    //新增
    void add(Article article);

    //条件分页列表查询
    PageBean<Article> list(Integer pageNum, Integer pageSize, Integer categoryId, String state);
}
