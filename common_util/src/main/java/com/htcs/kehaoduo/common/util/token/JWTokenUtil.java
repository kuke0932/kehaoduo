package com.htcs.kehaoduo.common.util.token;


import com.htcs.kehaoduo.common.util.UUIDUtil;
import io.jsonwebtoken.*;
import io.jsonwebtoken.impl.Base64Codec;
import io.jsonwebtoken.impl.DefaultClaims;

/**
 * Created by htcs on 2017/7/19.
 */
public class JWTokenUtil {

    /**
     * 创建一个token
     *
     * @param subject
     * @param expire  过期时间 单位是秒
     * @return
     */
    public static String createToken(String subject, int expire) {
        String compact = Jwts.builder()
                .setClaims(new DefaultClaims()
                                .setSubject(subject)
                        /*.setExpiration(DateUtils.addSeconds(new Date(), expire))*/)
                .signWith(SignatureAlgorithm.HS512, Base64Codec.BASE64.encode("aksjlkwernalksjdf"))
                .compact();


        return compact;
    }

    /**
     * 创建一个token，默认时间是3600s
     *
     * @param subject
     * @return
     */
    public static String createToken(String subject) {

        String compact = Jwts.builder()
                .setClaims(new DefaultClaims()
                                .setSubject(subject)
                                .setId(UUIDUtil.getUUIDWithDateRandom())
                        /*.setExpiration(DateUtils.addSeconds(new Date(), CacheConstans.USER_TIME))*/)
                .signWith(SignatureAlgorithm.HS512, Base64Codec.BASE64.encode("aksjlkwernalksjdf"))
                .compact();
        return compact;
    }

    public static VerifyResult<Jws<Claims>> verify(String sign) {
        try {
            Jws<Claims> claimsJws = Jwts.parser().setSigningKey(Base64Codec.BASE64.encode("aksjlkwernalksjdf"))
                    .parseClaimsJws(sign);
            return new VerifyResult(VerifyResult.ResultCode.OK, claimsJws);
        } catch (Exception e) {
            if (e instanceof ClaimJwtException) {
                throw new JwtException((e.getMessage()));
            } else {
                throw new JwtException(e.getMessage(), e);
            }
        }
    }


    public static void main(String[] args) throws InterruptedException {
        String hello = JWTokenUtil.createToken("hello");
        System.out.println(hello);
        VerifyResult b = JWTokenUtil.verify(hello);
        System.out.println(b.getResultCode());
    }
}
