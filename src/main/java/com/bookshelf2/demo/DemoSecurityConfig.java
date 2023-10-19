package com.bookshelf2.demo;

import com.bookshelf2.demo.service.BookshelfUserDetailsContextMapper;
import com.bookshelf2.demo.service.UserService;
import com.bookshelf2.demo.util.TwoFactorAuthenticationFilter;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.apache.catalina.filters.CorsFilter;
import org.hibernate.resource.jdbc.spi.JdbcSessionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;
import org.springframework.security.web.authentication.ui.DefaultLoginPageGeneratingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import javax.servlet.http.Cookie;
import javax.sql.DataSource;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@Configuration
@EnableWebSecurity
@ComponentScan("com.bookshelf2.demo")
public class DemoSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private DataSource dataSource;

    @Autowired
    private BookshelfUserDetailsContextMapper ctxMapper;

    @Autowired
    AuthenticationProvider twoFactorAuthenticationProvider;


    @Autowired
    UserService userService;


    @Override
    @CrossOrigin(origins = "http://localhost:4200", methods = RequestMethod.POST)
    protected void configure(final HttpSecurity http) throws Exception {


        http
                .cors().and()
                .csrf(AbstractHttpConfigurer::disable)

                .sessionManagement()
                .sessionFixation()
                .migrateSession()
                .and()

                .authorizeRequests()
                .antMatchers("/anonymus*").anonymous() //role anonymus
                .antMatchers("/login*").permitAll()
                .antMatchers("/loadFile").permitAll()
                .antMatchers("/restFiles").authenticated()
                .antMatchers("/createRemote").hasRole("ADMIN")
                .antMatchers("/createCompany").hasRole("ADMIN")

                //.antMatchers("http://localhost:4200/file-manager").authenticated()
                /*.antMatchers("/google**").permitAll()
                .antMatchers("/oauth2/authorization/google").permitAll()
                .antMatchers("/login/oauth2/code/google").permitAll()*/

                .antMatchers("/api/**").authenticated()
                .antMatchers("/registration*").permitAll()
                .antMatchers("/static/**").permitAll() //resources
                .antMatchers("/addAuthors").hasRole("USER")
                .antMatchers("/addBooks").hasRole("USER")
                .antMatchers("/*").permitAll()
                .anyRequest().permitAll()


                .and()
                .formLogin()
                .loginPage("http://localhost:4200/demo/login")
                .loginProcessingUrl("/perform_login")

                .successHandler((request, response, authentication) -> {

                    Cookie customCookie = new Cookie("Authenticated", "true");
                    customCookie.setMaxAge(86400); // Durata in secondi

                    customCookie.setPath("/demo"); // Imposta il percorso del cookie
                    customCookie.setHttpOnly(false);
                    customCookie.setDomain("localhost");
                    // Dopo una corretta autenticazione, restituisci una risposta JSON
                    response.setContentType("application/json");
                    UserDetails userDetails = (UserDetails) authentication.getPrincipal();
                    String role = authentication.getAuthorities().iterator().next().getAuthority();
                    System.out.println("ruoli"+role);
                    boolean enable = userService.findUser(userDetails.getUsername()).getUsing2FA();
                    String email = userDetails.getUsername();
                    Map<String, Object> responseData = new HashMap<>();
                    responseData.put("error", false);
                    responseData.put("2fa", enable);
                    responseData.put("email",email);
                    responseData.put("role",role);
                    responseData.put("cookie", customCookie);
                    //responseData.put("session",);
                    response.getWriter().write(new ObjectMapper().writeValueAsString(responseData));
                })
                .failureHandler((request, response, exception) -> {
                    // Gestisci l'errore di autenticazione
                    //response.setStatus(HttpStatus.UNAUTHORIZED.value());
                    response.setContentType("application/json");
                    response.getWriter().write("{ \"error\": \"true\" }");
                })
                //.failureUrl("/login?error=true")
                .permitAll()
                //.defaultSuccessUrl("/home", true)

                .and()
                .oauth2Login()

                .and()
                .rememberMe()
                .key("superSecretKey")
                .tokenValiditySeconds(18000) //5 ore
                .tokenRepository(tokenRepository())

                .and()
                .logout()
                .logoutSuccessUrl("/")
                .logoutSuccessHandler((request, response, authentication) -> {
                    System.out.println("logout");
                    Cookie customCookie = new Cookie("Authenticated", "true");
                    customCookie.setMaxAge(86400); // Durata in secondi
                    customCookie.setPath("/demo"); // Imposta il percorso del cookie
                    customCookie.setHttpOnly(false);
                    customCookie.setDomain("localhost");
                    response.setContentType("application/json");
                    Map<String, Object> responseData = new HashMap<>();
                    responseData.put("error", false);
                    responseData.put("cookie", customCookie);
                    response.getWriter().write(new ObjectMapper().writeValueAsString(responseData));
                })
                .logoutRequestMatcher(new AntPathRequestMatcher("/perform_logout", "GET"))

                .invalidateHttpSession(true)
                .deleteCookies("JSESSIONID")

                .permitAll()

                .and()
                .addFilterAfter(new TwoFactorAuthenticationFilter("/2fa-login", userService, corsConfigurationSource()), DefaultLoginPageGeneratingFilter.class)
                .authenticationProvider(twoFactorAuthenticationProvider); // Configura la 2FA






                /*.and()
                .oauth2Login()*/
        //.loginPage("/api/login")


    }


    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Arrays.asList("http://localhost:4200", "http://localhost:4200/demo"));
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE"));
        configuration.setAllowedHeaders(Arrays.asList("Authorization", "Content-Type"));
        configuration.setAllowCredentials(true);
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

    @Bean
    public PersistentTokenRepository tokenRepository() {
        JdbcTokenRepositoryImpl token = new JdbcTokenRepositoryImpl();
        token.setDataSource(dataSource);
        return token;
    }


    @Override
    @CrossOrigin(origins = "http://localhost:4200", methods = RequestMethod.POST)
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

