package com.tp.sstjpcb.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.access.AccessDeniedHandler;

@Configuration
@EnableWebSecurity
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private AccessDeniedHandler accessDeniedHandler;

    @Autowired
    private CustomAuthenticationProvider customAuthenticationProvider;

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http.csrf().disable()
                .authorizeRequests()
                //Providing all sub-folders of 'static' folder as permitAll() access is important,
                // Otherwise files of such folders(i.e css/js files or images) won't be loaded until you login into the application.
                .antMatchers("/css/**","/js/**","/public/**").permitAll()
                .antMatchers("/", "/home", "/about", "/signup", "/create-user").permitAll()
                .antMatchers("/admin/**").hasAnyAuthority("ADMIN")
                .antMatchers("/user/**").hasAnyAuthority("USER")
                .anyRequest().authenticated()
                .and()
                .formLogin()
                .loginPage("/login")
                .permitAll()
                .and()
                .logout()
                .permitAll()
                .and()
                .exceptionHandling().accessDeniedHandler(accessDeniedHandler);
    }

    // Method 1 :- Using 'InMemory Authentication'
    // create two users, admin and user
    //    @Autowired
    //    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
    //
    //        auth.inMemoryAuthentication()
    //                .withUser("user").password(passwordEncoder().encode("password")).roles("USER")
    //                .and()
    //                .withUser("admin").password(passwordEncoder().encode("password")).roles("ADMIN");
    //    }

    // Method 2 :- Using 'Authentication Provider'
    // For details about 'Authentication Provider' visit page - "https://www.baeldung.com/spring-security-multiple-auth-providers".
    @Override
    public void configure(AuthenticationManagerBuilder auth)
            throws Exception {
        auth.authenticationProvider(customAuthenticationProvider);
    }
    // #Note : -
    // 1.) Apart from 'InMemory Authentication' and 'Authentication Provider' service we can also use 'User Details Service' to authenticate user.Check Servinja project to see how we can use that.
    // 2.) We can use several authentication method together also like we can use InMemory Authentication and Authentication Provider together in same configure() method - one after another.
}