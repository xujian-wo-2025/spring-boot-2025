package org.example.controller;

import org.example.pojo.Article;
import org.example.pojo.PageBean;
import org.example.pojo.Result;
import org.example.service.ArticleService;
import org.example.utils.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.Map;

@RestController
@RequestMapping("/article")
public class ArticleController {

    @Autowired
    private ArticleService articleService;


    @PostMapping
    public Result add(@RequestBody @Validated Article article){
            articleService.add(article);
            return Result.success();
    }


    @GetMapping
    public Result<PageBean<Article>> list(
            Integer pageNum,
            Integer pageSize,
            @RequestParam(required = false) Integer categoryId,
            @RequestParam(required = false) String state
    ){
        PageBean<Article> pb = articleService.list(pageNum,pageSize,categoryId,state);
        return Result.success(pb);
    }



//    @GetMapping("/list")
//    public Result<String> list(/*@RequestHeader(name = "Authorization") String token, HttpServletResponse response*/){
//        //验证token
////        try {
////            Map<String, Object> claims = JwtUtil.parseToken(token);
////            return Result.success("所有的文章数据");
////        } catch (Exception e){
////            response.setStatus(401);
////            return Result.error("未登录");
////        }
//
//        return Result.success("所有的文章数据");
//    }
}
