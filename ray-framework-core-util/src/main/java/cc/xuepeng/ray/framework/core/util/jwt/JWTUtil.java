package cc.xuepeng.ray.framework.core.util.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.util.Date;
import java.util.Map;
import java.util.UUID;

/**
 * JWT的工具类。
 *
 * @author xuepeng
 */
public class JWTUtil {

    /**
     * 私有构造函数。
     */
    private JWTUtil() {
    }

    /**
     * 创建一个JWT。
     *
     * @param claims 信息。
     * @param secret 算法密钥。
     * @return JWT字符串。
     */
    public static String create(final Map<String, Object> claims, final String secret) {
        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;
        return Jwts.builder().setClaims(claims)
                .setId(UUID.randomUUID().toString())
                .setIssuedAt(new Date())
                .signWith(signatureAlgorithm, secret)
                .compact();
    }

    /**
     * 解码token获取信息。
     *
     * @param token  用户令牌。
     * @param secret 算法密钥。
     * @param key    信息键。
     * @return 信息值。
     */
    public static String get(String token, String secret, String key) {
        Claims claims = Jwts.parser()
                .setSigningKey(secret)
                .parseClaimsJws(token)
                .getBody();
        return claims.get(key, String.class);
    }

    /**
     * 判断用户令牌是否为指定信息。
     *
     * @param token  用户令牌。
     * @param secret 算法密钥。
     * @param key    信息键。
     * @param value  信息值。
     * @return 是否为指定信息。
     */
    public static boolean verify(String token, String secret, String key, String value) {
        return value.equals(get(token, secret, key));
    }

}
