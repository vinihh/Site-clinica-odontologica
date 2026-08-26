package br.com.dms.dms.config; // Ajuste o pacote conforme a sua estrutura

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Autowired
    private LoginInterceptor loginInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(loginInterceptor)
                .addPathPatterns("/**") 
                .excludePathPatterns(
                        "/login",          
                        "/assets/**",       
                        "/css/**",          
                        "/js/**"           
                );
    }
}
