package cc.xuepeng.ray.framework.core.web.security.url;

import lombok.Data;

import java.util.Set;

/**
 * 定义SpringSecurity所需的Url。
 *
 * @author xuepeng
 */
@Data
public class WebSecurityUrlProperties {

    /**
     * 登录Url。
     */
    private String signin;
    /**
     * 登出Url。
     */
    private String signout;
    /**
     * 忽略的Url（不鉴权）。
     */
    private Set<String> ignoreUrls;
    /**
     * 登录页面地址。
     */
    private String loginPage;

}
