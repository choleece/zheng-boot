package cn.choleece.zhengboot.admin.shiro;

import org.apache.shiro.authc.AuthenticationToken;

/**
 * @author choleece
 * @description: JWT token
 * @date 2018/7/22 14:21
 */
public class JWTToken implements AuthenticationToken {

    /**
     * 密钥
     */
    private String token;

    public JWTToken(String token) {
        this.token = token;
    }

    @Override
    public Object getPrincipal() {
        return token;
    }

    @Override
    public Object getCredentials() {
        return token;
    }
}
