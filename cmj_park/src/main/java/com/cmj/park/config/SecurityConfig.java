package com.cmj.park.config;

import com.cmj.park.security.filter.JwtAuthFilter;
import com.cmj.park.security.handler.AuthenticationEntryPointImpl;
import com.cmj.park.security.handler.AccessDeniedHandlerImpl;
import com.cmj.park.security.handler.LogoutSuccessHandlerImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.time.Duration;
import java.util.Arrays;

/**
 * spring security 配置
 *
 */
@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    /**
     * 路径白名单
     */
    private static final String[] WHITE_LIST = {"/login", "/park/register/insert", "/upload/**","/login/admins"};
    /**
     * 路径检查名单
     * user 用户操作路径
     * admin 管理员操作路径
     */
    private static final String[] CHECK_LIST = {"/getLoggedInUserInfo"};
    private static final String[] USER_CHECK_LIST = {"/park/member/**"};
    private static final String[] ADMIN_CHECK_LIST = {"/park/admin/**"};

    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowCredentials(true);
        configuration.setAllowedOrigins(Arrays.asList("*"));
        configuration.setAllowedMethods(Arrays.asList("*"));
        configuration.setAllowedHeaders(Arrays.asList("*"));
        configuration.setMaxAge(Duration.ofHours(1));
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

    /**
     * 认证失败处理器
     */
    @Autowired
    private AuthenticationEntryPointImpl unauthorizedHandler;

    /**
     * 退出处理器
     */
    @Autowired
    private LogoutSuccessHandlerImpl logoutSuccessHandler;

    /**
     * jwt认证过滤器
     */
    @Autowired
    private JwtAuthFilter jwtAuthFilter;

    /**
     * 403 权限认证处理器
     */
    @Autowired
    private AccessDeniedHandlerImpl accessDeniedHandler;

    /**
     * 强散列哈希加密器
     */
    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * 自定义用户认证逻辑
     */
    @Autowired
    private UserDetailsService userDetailsService;

    /**
     * 认证管理器（无法直接注入AuthenticationManager）
     *
     * @return
     * @throws Exception
     */
    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    /**
     * 身份认证接口
     */
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder());
    }

    /**
     * anyRequest          |   匹配所有请求路径
     * access              |   SpringEl表达式结果为true时可以访问
     * anonymous           |   匿名可以访问
     * denyAll             |   用户不能访问
     * fullyAuthenticated  |   用户完全认证可以访问（非remember-me下自动登录）
     * hasAnyAuthority     |   如果有参数，参数表示权限，则其中任何一个权限可以访问
     * hasAnyRole          |   如果有参数，参数表示角色，则其中任何一个角色可以访问
     * hasAuthority        |   如果有参数，参数表示权限，则其权限可以访问
     * hasIpAddress        |   如果有参数，参数表示IP地址，如果用户IP和参数匹配，则可以访问
     * hasRole             |   如果有参数，参数表示角色，则其角色可以访问
     * permitAll           |   用户可以任意访问
     * rememberMe          |   允许通过remember-me登录的用户访问
     * authenticated       |   用户登录后可访问
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)// 基于token，所以不需要session
                .and()
                .authorizeRequests().antMatchers(WHITE_LIST).anonymous()
                .and()
                .authorizeRequests()
                .antMatchers(CHECK_LIST).authenticated()
                //拦截用户和管理员的操作路径并检查是否有对应的权限
                .antMatchers(USER_CHECK_LIST).hasAuthority("ROLE_USER_COMMON")
                .antMatchers(ADMIN_CHECK_LIST).hasAuthority("ROLE_ADMIN_SUPER")
                .and()
                .authorizeRequests().anyRequest().authenticated()// 其它没有配置的请求都需要身份认证
                .and()
                .cors()// 启用cors
                .configurationSource(corsConfigurationSource())
                .and()
                .csrf()
                .disable();// 禁用CSRF，因为不使用session
        // 添加jwt filter，每次jwt认证通过后，会将认证信息放入spring security上下文
        http.addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);
        // 认证失败处理器
        http.exceptionHandling().authenticationEntryPoint(unauthorizedHandler);
        // 注销成功处理器
        http.logout().logoutUrl("/logout").logoutSuccessHandler(logoutSuccessHandler);
        // 权限认证处理器
        http.exceptionHandling().accessDeniedHandler(accessDeniedHandler);
    }
}
