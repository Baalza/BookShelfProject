package com.bookshelf2.demo;

import ch.qos.logback.core.net.LoginAuthenticator;
import com.bookshelf2.demo.service.BookshelfUserDetailsContextMapper;
import com.bookshelf2.demo.service.UserService;
import com.bookshelf2.demo.util.TwoFactorAuthenticationFilter;
import com.bookshelf2.demo.util.TwoFactorAuthenticationProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;
import org.springframework.security.web.authentication.ui.DefaultLoginPageGeneratingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.sql.DataSource;

@Configuration
@EnableWebSecurity
@ComponentScan("com.bookshelf2.demo")
public class DemoSecurityConfig  extends WebSecurityConfigurerAdapter {

    @Autowired
    private DataSource dataSource;

    @Autowired
    private BookshelfUserDetailsContextMapper ctxMapper;

    @Autowired
    AuthenticationProvider twoFactorAuthenticationProvider;


    @Autowired
    UserService userService;





    @Override
    protected void configure (final HttpSecurity http) throws Exception{


        http
                .authorizeRequests()
                .antMatchers("/anonymus*").anonymous() //role anonymus
                .antMatchers("/login*").permitAll()

                /*.antMatchers("/google**").permitAll()
                .antMatchers("/oauth2/authorization/google").permitAll()
                .antMatchers("/login/oauth2/code/google").permitAll()*/

                .antMatchers("/api/**").authenticated()
                .antMatchers("/registration*").permitAll()
                .antMatchers("/static/**").permitAll() //resources
                .antMatchers("/addAuthors").hasRole("USER")
                .antMatchers("/addBooks").hasRole("USER")
                .antMatchers("/*").permitAll()
                .anyRequest().authenticated()

                .and()
                .formLogin()
                .loginPage("/login")
                .loginProcessingUrl("/perform_login")
                .failureUrl("/login?error=true")
                .permitAll()
                .defaultSuccessUrl("/2fa-login", true)

                .and()
                .rememberMe()
                .key("superSecretKey")
                .tokenValiditySeconds(18000) //5 ore
                .tokenRepository(tokenRepository())

                .and()
                .logout()
                .logoutSuccessUrl("/")
                .logoutRequestMatcher(new AntPathRequestMatcher("/perform_logout", "GET"))
                .invalidateHttpSession(true)
                .deleteCookies("JSESSIONID")
                .permitAll()

                .and()
                .addFilterAfter(new TwoFactorAuthenticationFilter("/2fa-login",userService),  DefaultLoginPageGeneratingFilter.class)
                .authenticationProvider(twoFactorAuthenticationProvider); // Configura la 2FA





                /*.and()
                .oauth2Login()*/
        //.loginPage("/api/login")


    }


    @Bean
    public PersistentTokenRepository tokenRepository() {
        JdbcTokenRepositoryImpl token = new JdbcTokenRepositoryImpl();
        token.setDataSource(dataSource);
        return token;
    }

    @Override
    protected void configure(final AuthenticationManagerBuilder auth) throws Exception {

        /*auth.inMemoryAuthentication()
                .withUser("pippo").password(passwordEncoder().encode("1234")).roles("USER");
        */

        auth.jdbcAuthentication().dataSource(dataSource).passwordEncoder(passwordEncoder())
                .and().authenticationProvider(twoFactorAuthenticationProvider);



        /*auth.ldapAuthentication()
                .userDnPatterns("uid={0},ou=people")
                .groupSearchBase("ou=groups")
                .contextSource()
                .url("ldap://localhost:8389/dc=bookshelf2,dc=com")
                .and()
                .passwordCompare()
                .passwordEncoder(passwordEncoder())
                .passwordAttribute("userPassword")
                .and()
                .userDetailsContextMapper(ctxMapper);*/

    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


}

