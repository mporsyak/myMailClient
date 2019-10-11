package com.exampel.myMail.config;

import com.exampel.myMail.service.MessageService;
import com.exampel.myMail.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;

import javax.sql.DataSource;


@Configuration
@EnableWebSecurity
@ComponentScan({"com.exampel.myMail","com.exampel.myMail.service"})
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    UserService userService;
    @Autowired
    MessageService messageService;

    @Autowired
    DataSource myDataSource;

    @Autowired
    AuthenticationEntryPoint entryPoint;

    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.jdbcAuthentication().dataSource(myDataSource)
                .usersByUsernameQuery(
                        "select login, password, true from user where login = ? ")
                .authoritiesByUsernameQuery(
                        "select login, 'ROLE_ADMIN' from user where login = ?");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .authorizeRequests()
                .antMatchers("/**/*.js", "/**/*.css", "/greeting", "/register", "/addUser", "/login").permitAll()
//                .antMatchers("/all/**").hasRole("ADMIN")
                .anyRequest().authenticated()
                .and()
                .formLogin()
                .loginPage("/login")
                .usernameParameter("login")
                .passwordParameter("password")
                .permitAll()
                .defaultSuccessUrl("/all", true)
                .and().exceptionHandling().accessDeniedPage("/access_denied")


                .and()

                .logout()
                .logoutUrl("/logout")
                .permitAll();

        http.httpBasic().authenticationEntryPoint(entryPoint);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return NoOpPasswordEncoder.getInstance();
    }


}
