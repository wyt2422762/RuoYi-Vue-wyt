package com.ruoyi.framework.web.service;

import com.ruoyi.bus.domain.Consumer;
import com.ruoyi.bus.domain.Nurse;
import com.ruoyi.bus.service.IConsumerService;
import com.ruoyi.bus.service.INurseService;
import com.ruoyi.common.constant.Constants;
import com.ruoyi.common.core.domain.model.LoginUser;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.common.utils.uuid.IdUtils;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.*;
import java.util.stream.Collectors;

/**
 * token验证处理
 *
 * @author ruoyi
 */
@Slf4j
@Component
public class TokenService {
    @Autowired
    private IConsumerService consumerService;
    @Autowired
    private INurseService nurseService;

    /**
     * 令牌自定义标识
     */
    @Value("${token.header}")
    private String header;

    /**
     * 令牌秘钥
     */
    @Value("${token.secret}")
    private String secret;

    /**
     * 令牌有效期（分钟）
     */
    @Value("${token.expireTime}")
    private long expireTime;

    /**
     * 获取type(0 客户, 1 护工)
     *
     * @return 用户信息
     */
    public String getType(HttpServletRequest request) {
        // 获取请求携带的令牌
        String token = getToken(request);
        if (StringUtils.isNotEmpty(token)) {
            Claims claims = parseToken(token);
            return claims.get("type", String.class);
        }
        return null;
    }

    /**
     * 获取客户信息
     *
     * @return 客户信息
     */
    public Consumer getMpConsumer(HttpServletRequest request) {
        // 获取请求携带的令牌
        String token = getToken(request);
        if (StringUtils.isNotEmpty(token)) {
            Claims claims = parseToken(token);
            Long consumerId = claims.get("consumerId", Long.class);
            return consumerService.selectConsumerById(consumerId);
        }
        return null;
    }

    /**
     * 获取护工信息
     *
     * @return 护工信息
     */
    public Nurse getMpNurse(HttpServletRequest request) {
        // 获取请求携带的令牌
        String token = getToken(request);
        if (StringUtils.isNotEmpty(token)) {
            Claims claims = parseToken(token);
            Long nurseId = claims.get("nurseId", Long.class);
            return nurseService.selectNurseById(nurseId);
        }
        return null;
    }

    public Authentication getAuthentication(String token) {
        Claims claims = parseToken(token);
        Map<String, String> map = claims.get("auth", HashMap.class);
        Collection<? extends GrantedAuthority> authorities =
                Arrays.stream(claims.get("authorities", String.class).split(","))
                        .map(SimpleGrantedAuthority::new)
                        .collect(Collectors.toList());
        User principal = new User(map.get("username"), map.get("password"), authorities);
        return new UsernamePasswordAuthenticationToken(principal, token, authorities);
    }

    /**
     * 创建令牌
     *
     * @param loginUser 用户信息
     * @return 令牌
     */
    public String createToken(LoginUser loginUser) {
        String token = IdUtils.fastUUID();
        loginUser.setToken(token);
        Map<String, Object> claims = new HashMap<>();
        claims.put(Constants.LOGIN_USER_KEY, token);
        return createToken(claims);
    }

    /**
     * 创建令牌(护工)
     *
     * @param nurse 护工信息
     * @return 令牌
     */
    public String createToken(Nurse nurse) {
        String token = IdUtils.fastUUID();
        Map<String, Object> claims = new HashMap<>();
        claims.put(Constants.MP_NURSE_KEY, token);
        claims.put("nurseId", nurse.getNurseId());
        claims.put("type", "1");
        Map<String, String> map = new HashMap<>(2);
        map.put("username", nurse.getPhonenumber());
        map.put("password", "");
        claims.put("auth", map);
        claims.put("authorities", "nurse");
        return createToken(claims);
    }

    /**
     * 创建令牌(客户)
     *
     * @param consumer 客户信息
     * @return 令牌
     */
    public String createToken(Consumer consumer) {
        String token = IdUtils.fastUUID();
        Map<String, Object> claims = new HashMap<>();
        claims.put(Constants.MP_NURSE_KEY, token);
        claims.put("consumerId", consumer.getConsumerId());
        claims.put("type", "0");
        Map<String, String> map = new HashMap<>(2);
        map.put("username", consumer.getPhonenumber());
        map.put("password", "");
        claims.put("auth", map);
        claims.put("authorities", "consumer");
        return createToken(claims);
    }

    /**
     * 从数据声明生成令牌
     *
     * @param claims 数据声明
     * @return 令牌
     */
    private String createToken(Map<String, Object> claims) {
        return Jwts.builder()
                .setClaims(claims)
                //当前时间
                .setIssuedAt(new Date(System.currentTimeMillis()))
                //过期时间
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * expireTime))
                .signWith(SignatureAlgorithm.HS512, secret).compact();
    }

    /**
     * 从令牌中获取数据声明
     *
     * @param token 令牌
     * @return 数据声明
     */
    public Claims parseToken(String token) {
        return Jwts.parser()
                .setSigningKey(secret)
                .parseClaimsJws(token)
                .getBody();
    }

    /**
     * 获取请求token
     *
     * @param request req
     * @return token token
     */
    public String getToken(HttpServletRequest request) {
        String token = request.getHeader(header);
        if (StringUtils.isNotEmpty(token) && token.startsWith(Constants.TOKEN_PREFIX)) {
            token = token.replace(Constants.TOKEN_PREFIX, "");
        }
        return token;
    }

    /**
     * 验证token是否有效
     *
     * @param token token
     * @return 结果
     */
    public boolean validateToken(String token) {
        try {
            Jwts.parser().setSigningKey(secret).parseClaimsJws(token);
            return true;
        } catch (ExpiredJwtException e) {
            log.error(e.getMessage());
            e.printStackTrace();
        } catch (Exception e) {
            log.error("JWT token is invalid.");
            e.printStackTrace();
        }
        return false;
    }

}
