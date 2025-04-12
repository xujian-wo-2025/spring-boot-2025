package org.example.service;

import org.apache.ibatis.annotations.Insert;
import org.example.pojo.Category;

import java.util.List;

public interface CategoryService {

    //新增分类
    void add(Category category);

    //查询
    List<Category> list();

    // 查找
    Category findById(Integer id);

    //更新分类
    void update(Category category);

    //删除
    void delete(Integer id);
}
