package org.example.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.example.pojo.Article;
import org.springframework.stereotype.Service;

import java.util.List;

@Mapper
@Service
public interface ArticleMapper {

    //新增
    @Insert("insert into article(title,content,cover_img,state,category_id,create_user,create_time,update_time)"+
    "values(#{title},#{content},#{coverImg},#{state},#{categoryId},#{createUser},#{createTime},#{updateTime})")
    void add(Article article);

    @Select("<script>" +
            "SELECT * FROM article " +
            "<where>" +
            "   <if test='categoryId != null'>" +
            "       category_id = #{categoryId} " +
            "   </if>" +
            "   <if test='state != null'>" +
            "       AND state = #{state} " +
            "   </if>" +
            "   AND create_user = #{id} " +
            "</where>" +
            "</script>")
    List<Article> list(Integer id, Integer categoryId, String state);

    @Select("select count(*) from article where category_id = #{categoryId}")
    int countByCategoryId(Integer categoryId);
}
