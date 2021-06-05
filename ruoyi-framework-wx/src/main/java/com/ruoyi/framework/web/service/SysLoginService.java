package com.ruoyi.framework.web.service;

import com.ruoyi.bus.domain.Consumer;
import com.ruoyi.bus.domain.Nurse;
import com.ruoyi.common.core.redis.RedisCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * 登录校验方法
 * 
 * @author ruoyi
 */
@Component
public class SysLoginService
{
    @Autowired
    private TokenService tokenService;

    @Resource
    private AuthenticationManager authenticationManager;

    /**
     * 登陆验证
     * @param consumer 客户信息
     * @return
     */
    public String login(Consumer consumer)
    {
        // 登陆
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(consumer.getPhonenumber(), ""));
        // 生成token
        return tokenService.createToken(consumer);
    }

    /**
     * 登陆验证
     * @param nurse 护工信息
     * @return
     */
    public String login(Nurse nurse)
    {
        // 登陆
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(nurse.getPhonenumber(), ""));
        // 生成token
        return tokenService.createToken(nurse);
    }

}
