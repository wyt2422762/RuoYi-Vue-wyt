package com.ruoyi.framework.security.filter;

import com.ruoyi.bus.domain.Consumer;
import com.ruoyi.bus.domain.Nurse;
import com.ruoyi.common.core.domain.model.LoginMpUser;
import com.ruoyi.common.core.domain.model.LoginUser;
import com.ruoyi.common.exception.CustomException;
import com.ruoyi.common.utils.SecurityUtils;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.framework.web.service.TokenService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * token过滤器 验证token有效性
 * 
 * @author ruoyi
 */
@Component
public class JwtAuthenticationTokenFilter extends OncePerRequestFilter
{
    @Autowired
    private TokenService tokenService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws ServletException, IOException
    {
        String token = tokenService.getToken(request);
        if (StringUtils.isNotNull(token) && StringUtils.isNull(SecurityUtils.getAuthentication()))
        {
            try {
                Claims claims = tokenService.parseToken(token);
                String type = claims.get("type", String.class);
                UsernamePasswordAuthenticationToken authenticationToken = null;
                if("0".equals(type)){
                    //客户
                    Consumer mpConsumer = tokenService.getMpConsumer(request);
                    authenticationToken = getAuthentication(mpConsumer);
                } else if("1".equals(type)){
                    //护工
                    Nurse nurse = tokenService.getMpNurse(request);
                    authenticationToken = getAuthentication(nurse);
                }
                authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            } catch (ExpiredJwtException e) {
                logger.error(e);
                throw new CustomException("token过期", 401);
            } catch (Exception e) {
                logger.error(e);
                throw new CustomException("系统内部错误", 500);
            }
        }
        chain.doFilter(request, response);
    }

    private UsernamePasswordAuthenticationToken getAuthentication(Consumer consumer) {
        List<GrantedAuthority> authorities = new ArrayList<>(1);
        authorities.add(new SimpleGrantedAuthority("consumer"));
        Set<String> permissions = new HashSet<>(1);
        permissions.add("consumer");
        //踩坑提醒 此处password不能为null
        LoginUser loginUser = new LoginMpUser(consumer.getPhonenumber(), "", permissions);
        return new UsernamePasswordAuthenticationToken(loginUser, null, null);
    }

    private UsernamePasswordAuthenticationToken getAuthentication(Nurse nurse) {
        List<GrantedAuthority> authorities = new ArrayList<>(1);
        authorities.add(new SimpleGrantedAuthority("nurse"));
        Set<String> permissions = new HashSet<>(1);
        permissions.add("nurse");
        //踩坑提醒 此处password不能为null
        LoginUser loginUser = new LoginMpUser(nurse.getPhonenumber(), "", permissions);
        return new UsernamePasswordAuthenticationToken(loginUser, null, authorities);
    }

}
