package com.chige.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
public class MyWebSecurity extends WebSecurityConfigurerAdapter {

    /**
     *
     * @date 2020/9/5
     * @description 设定角色权限（角色与权限的对应关系）
     * @auth ChiGe
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http.authorizeRequests().antMatchers("/")
                .permitAll()
                .antMatchers("/level1/**").hasRole("VIP1")
                .antMatchers("/level2/**").hasRole("VIP2")
                .antMatchers("/level3/**").hasRole("VIP3");
                //TODO 其他资源，如map.html,graph.html等资源单独访问时需要设置访问权限,未处理好


        //开启自动配置的登录功能
        http.formLogin().usernameParameter("username").passwordParameter("password")
                .loginPage("/login");

//        super.configure(http);
    }

    /**
     * @date 2020/9/5
     * @description 设定 用户，密码，及角色的关联关系
     * @auth ChiGe
     */
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {

        //1.对原生密码进行加密操作
        String password = new BCryptPasswordEncoder().encode("123.com");
        auth.inMemoryAuthentication().passwordEncoder(new BCryptPasswordEncoder())
                .withUser("root").password(password).roles("VIP1","VIP2","VIP3")
                .and()
                .withUser("guest").password(password).roles("VIP1")
                .and()
                .withUser("student").password(password).roles("VIP1","VIP2")
                .and()
                .withUser("teacher").password(password).roles("VIP2");


        //        super.configure(auth);
    }
}
