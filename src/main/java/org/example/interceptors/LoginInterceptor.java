package org.example.interceptors;

import org.example.pojo.Result;
import org.example.utils.JwtUtil;
import org.example.utils.ThreadLocalUtil;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

@Component
public class LoginInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //令牌验证
        String authorization = request.getHeader("Authorization");
        try {
            Map<String, Object> claims = JwtUtil.parseToken(authorization);
            ThreadLocalUtil.set(claims);
            return true;   // 放行
        } catch (Exception e){
            response.setStatus(401);
            return false;
        }

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        //清空threadlocal中的数据
        ThreadLocalUtil.remove();
    }
}
