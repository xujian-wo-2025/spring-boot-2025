package org.example.controller;

import org.example.pojo.Result;
import org.example.pojo.User;
import org.example.service.UserService;
import org.example.utils.JwtUtil;
import org.example.utils.Md5Util;
import org.example.utils.ThreadLocalUtil;
import org.hibernate.validator.constraints.URL;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.util.StringUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/user")
public class Usercontroller {

    @Autowired
    private UserService userService;
    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @PostMapping("/register")
    public Result register(String username, String password){

        if (username != null && username.length()>=5 && username.length()<=16 &&
            password!=null && password.length()>=5 && password.length()<=16
        ){
            User user = userService.findByUserName(username);
            if (user == null){
                userService.register(username, password);
                return Result.success();
            }else {
                return Result.error("该用户名已被暂用");
            }
        }else {
            return Result.error("参数不合法");
        }



    }


    @PostMapping("/login")
    public Result<String> login(String username, String password){
        User byUserName = userService.findByUserName(username);
        if (byUserName == null){
            return Result.error("用户名错误");
        }

        if (Md5Util.getMD5String(password).equals(byUserName.getPassword())){
            Map<String,Object> claims = new HashMap<>();
            claims.put("id", byUserName.getId());
            claims.put("username", byUserName.getUsername());
            String token = JwtUtil.genToken(claims);

            //把token存储到redis中
            ValueOperations<String, String> valueOperations = stringRedisTemplate.opsForValue();
            valueOperations.set(token,token,1, TimeUnit.HOURS);
            return Result.success(token);
        }
        return Result.error("密码错误");

    }

    @GetMapping("/userInfo")
    public Result<User> userInfo(/*@RequestHeader(name = "Authorization") String token*/){
        //根据用户名查询用户
//        Map<String, Object> map = JwtUtil.parseToken(token);
//        String username = (String) map.get("username");

        Map<String, Object> map = ThreadLocalUtil.get();

        String username = (String) map.get("username");
        User byUserName = userService.findByUserName(username);
        return Result.success(byUserName);

    }

    @PutMapping("/update")
    public Result update(@RequestBody @Validated User user){
        userService.update(user);
        return Result.success();
    }


    @PatchMapping("updateAvatar")    //图片上传
    public Result updateAvatar(@RequestParam @URL String avatarUrl){
        userService.updateAvatar(avatarUrl);
        return Result.success();
    }

    @PatchMapping("/updatePwd")
    public Result updatePwd(@RequestBody Map<String, String> params ,@RequestHeader("Authorization") String token){

        // 1 校验参数
        String oldPwd = params.get("old_pwd");
        String newPwd = params.get("new_pwd");
        String rePwd = params.get("re_pwd");

        if (!StringUtils.hasLength(oldPwd) || !StringUtils.hasLength(newPwd) || !StringUtils.hasLength(rePwd)){
            return Result.error("缺少必要的参数");
        }

        //原密码是否正确
        // 调用userService根据用户名拿到原密码，再和old_pwd比对
        Map<String,Object> map = ThreadLocalUtil.get();
        String username = (String) map.get("username");
        User byUserName = userService.findByUserName(username);
        if (!byUserName.getPassword().equals(Md5Util.getMD5String(oldPwd))){
            return Result.error("密码错误");
        }

        //newPwd和rePwd是否一样
        if (!rePwd.equals(newPwd)){
            return Result.error("两次填写的密码不一样");
        }

        // 2 调用service完成密码更新
        userService.updatePwd(newPwd);
        //删除redis中对应的token

        ValueOperations<String, String> valueOperations = stringRedisTemplate.opsForValue();
        valueOperations.getOperations().delete(token);
        return Result.success();
    }
}
