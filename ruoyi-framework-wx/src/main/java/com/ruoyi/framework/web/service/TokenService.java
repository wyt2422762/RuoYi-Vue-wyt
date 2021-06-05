package com.ruoyi.framework.web.service;

import com.ruoyi.bus.domain.Consumer;
import com.ruoyi.bus.domain.Nurse;
import com.ruoyi.bus.service.IConsumerService;
import com.ruoyi.bus.service.INurseService;
import com.ruoyi.common.constant.Constants;
import com.ruoyi.common.core.domain.model.LoginUser;
import com.ruoyi.common.core.redis.RedisCache;
import com.ruoyi.common.utils.ServletUtils;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.common.utils.ip.AddressUtils;
import com.ruoyi.common.utils.ip.IpUtils;
import com.ruoyi.common.utils.uuid.IdUtils;
import eu.bitwalker.useragentutils.UserAgent;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * token验证处理
 *
 * @author ruoyi
 */
@Component
public class TokenService
{
    @Autowired
    private IConsumerService consumerService;
    @Autowired
    private INurseService nurseService;

    // 令牌自定义标识
    @Value("${token.header}")
    private String header;

    // 令牌秘钥
    @Value("${token.secret}")
    private String secret;

    // 令牌有效期（默认30分钟）
    @Value("${token.expireTime}")
    private long expireTime;

    protected static final long MILLIS_SECOND = 1000;

    protected static final long MILLIS_MINUTE = 60 * MILLIS_SECOND;

    private static final Long MILLIS_MINUTE_TEN = 20 * 60 * 1000L;

    /**
     * 获取type(0 客户, 1 护工)
     *
     * @return 用户信息
     */
    public String getType(HttpServletRequest request)
    {
        // 获取请求携带的令牌
        String token = getToken(request);
        if (StringUtils.isNotEmpty(token))
        {
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
    public Consumer getMpConsumer(HttpServletRequest request)
    {
        // 获取请求携带的令牌
        String token = getToken(request);
        if (StringUtils.isNotEmpty(token))
        {
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
    public Nurse getMpNurse(HttpServletRequest request)
    {
        // 获取请求携带的令牌
        String token = getToken(request);
        if (StringUtils.isNotEmpty(token))
        {
            Claims claims = parseToken(token);
            Long nurseId = claims.get("nurseId", Long.class);
            return nurseService.selectNurseById(nurseId);
        }
        return null;
    }

    /**
     * 创建令牌(客户)
     *
     * @param consumer 客户信息
     * @return 令牌
     */
    public String createToken(Consumer consumer)
    {
        String token = IdUtils.fastUUID();
        Map<String, Object> claims = new HashMap<>();
        claims.put(Constants.MP_CONSUMER_KEY, token);
        claims.put("consumerId", consumer.getConsumerId());
        claims.put("type", 0);
        return createToken(claims);
    }

    /**
     * 创建令牌
     *
     * @param loginUser 用户信息
     * @return 令牌
     */
    public String createToken(LoginUser loginUser)
    {
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
    public String createToken(Nurse nurse)
    {
        String token = IdUtils.fastUUID();
        Map<String, Object> claims = new HashMap<>();
        claims.put(Constants.MP_NURSE_KEY, token);
        claims.put("nurseId", nurse.getNurseId());
        claims.put("type", 1);
        return createToken(claims);
    }

    /**
     * 从数据声明生成令牌
     *
     * @param claims 数据声明
     * @return 令牌
     */
    private String createToken(Map<String, Object> claims)
    {
        String token = Jwts.builder()
                .setClaims(claims)
                //当前时间
                .setIssuedAt(new Date(System.currentTimeMillis()))
                //过期时间
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * expireTime))
                .signWith(SignatureAlgorithm.HS512, secret).compact();
        return token;
    }

    /**
     * 从令牌中获取数据声明
     *
     * @param token 令牌
     * @return 数据声明
     */
    public Claims parseToken(String token)
    {
        return Jwts.parser()
                .setSigningKey(secret)
                .parseClaimsJws(token)
                .getBody();
    }

    /**
     * 获取请求token
     *
     * @param request
     * @return token
     */
    public String getToken(HttpServletRequest request)
    {
        String token = request.getHeader(header);
        if (StringUtils.isNotEmpty(token) && token.startsWith(Constants.TOKEN_PREFIX))
        {
            token = token.replace(Constants.TOKEN_PREFIX, "");
        }
        return token;
    }

}
