package com.bookshelf2.demo;

import com.bookshelf2.demo.service.UserService;
import com.bookshelf2.demo.util.TwoFactorAuthenticationProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.*;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;
import org.thymeleaf.spring5.SpringTemplateEngine;
import org.thymeleaf.spring5.templateresolver.SpringResourceTemplateResolver;
import org.thymeleaf.spring5.view.ThymeleafViewResolver;

import java.util.Locale;

@Configuration
public class DemoConfig implements WebMvcConfigurer {

    @Autowired
    ApplicationContext applicationContex;


        @Override
        public void addCorsMappings(CorsRegistry registry) {
            registry.addMapping("/**")
                    .allowedOrigins("*")
                    .allowedMethods("GET", "POST", "PUT", "DELETE", "HEAD");
        }
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry){
        registry.addResourceHandler("/files/view/plain/**").addResourceLocations("/WEB-INF/files/");
        registry.addResourceHandler("/files/pdf/**").addResourceLocations("/WEB-INF/pdf/");
        //registry.addResourceHandler("/files/view/txt**").addResourceLocations("/WEB-INF/files/");
        registry.addResourceHandler("/files/view/pdf/**").addResourceLocations("/WEB-INF/files/");
        registry.addResourceHandler("/files/view/text/html/**").addResourceLocations("/WEB-INF/files/");
        registry.addResourceHandler("/files/view/json/**").addResourceLocations("/WEB-INF/files/");
        registry.addResourceHandler("/files/view/xml/**").addResourceLocations("/WEB-INF/files/");
        registry.addResourceHandler("/files/view/jpeg/**").addResourceLocations("/WEB-INF/files/");
        registry.addResourceHandler("/files/view/png/**").addResourceLocations("/WEB-INF/files/");
        registry.addResourceHandler("/files/view/gif/**").addResourceLocations("/WEB-INF/files/");
        registry.addResourceHandler("/files/view/docx/**").addResourceLocations("/WEB-INF/files/");
        registry.addResourceHandler("/files/view/vnd.openxmlformats-officedocument.spreadsheetml.sheet/**").addResourceLocations("/WEB-INF/files/");
        registry.addResourceHandler("/files/view/octet-stream/**").addResourceLocations("/WEB-INF/files/");
        registry.addResourceHandler("/files/view/mpeg/**").addResourceLocations("/WEB-INF/files/");
        registry.addResourceHandler("/files/view/mp4/**").addResourceLocations("/WEB-INF/files/");
        registry.addResourceHandler("/files/view/text/javascript/**").addResourceLocations("/WEB-INF/files/");
        registry.addResourceHandler("/files/view/css/**").addResourceLocations("/WEB-INF/files/");
    }

    @Override
    public void addViewControllers (final ViewControllerRegistry registry){
        registry.addViewController("/login");
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(localeChangeInterceptor());

    }

    @Bean
    public LocaleResolver localeResolver(){
        SessionLocaleResolver slr = new SessionLocaleResolver();
        slr.setDefaultLocale(Locale.ITALIAN);
        return slr;
    }
    @Bean
    public LocaleChangeInterceptor localeChangeInterceptor(){
        LocaleChangeInterceptor lci = new LocaleChangeInterceptor();
        lci.setParamName("lang");
        return lci;
    }

    @Bean
    public ViewResolver viewResolver(){
        ThymeleafViewResolver viewResolver = new ThymeleafViewResolver();
        viewResolver.setTemplateEngine(templateEngine());
        viewResolver.setOrder(0);
        return viewResolver;
    }

    @Bean
    public SpringResourceTemplateResolver templateResolver(){
        SpringResourceTemplateResolver templateResolver = new SpringResourceTemplateResolver();
        templateResolver.setApplicationContext(applicationContex);
        templateResolver.setPrefix("/WEB-INF/views/");
        templateResolver.setSuffix(".html");
        return templateResolver;
    }

    @Bean
    public SpringTemplateEngine templateEngine(){
        SpringTemplateEngine templateEngine = new SpringTemplateEngine();
        templateEngine.setTemplateResolver(templateResolver());
        templateEngine.setEnableSpringELCompiler(true);
        return templateEngine;
    }
}
