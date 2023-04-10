package top.xsword.common_service.filter;

import org.springframework.web.filter.OncePerRequestFilter;
import top.xsword.common_util.jwt.JwtUtil;
import top.xsword.model.entity.User;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Data:2022/11/22
 * Author:ywx
 * Description:
 */
public class AuthFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String token = request.getHeader("X-Token");
        if (token != null) {
            User user = JwtUtil.parse(token, User.class);
            if (user != null && user.getId() != null && user.getRole() != null) { //将其转为user对象放入request域中，以便切面做验证
                request.setAttribute("user", user);
                request.setAttribute("token", token);
            }
        }
        //若没有token，则先放行
        filterChain.doFilter(request, response);
    }
}
