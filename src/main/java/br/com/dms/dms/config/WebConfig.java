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
                .addPathPatterns("/**") // Protege TODAS as páginas do sistema
                .excludePathPatterns(
                        "/login",           // Libera a rota de tentar logar
                        "/assets/**",       // Libera as imagens
                        "/css/**",          // Libera o CSS
                        "/js/**"            // Libera o JavaScript (se tiver pasta js)
                );
    }
}