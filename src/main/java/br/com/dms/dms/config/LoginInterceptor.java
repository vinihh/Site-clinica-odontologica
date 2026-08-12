package br.com.dms.dms.config; // Ajuste o pacote conforme a sua estrutura

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class LoginInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        HttpSession session = request.getSession(false);

        // Verifica se a sessão existe e se o usuário está logado
        // (Assumindo que no seu LoginController você salva algo como session.setAttribute("usuarioLogado", nome))
        if (session == null || session.getAttribute("usuarioLogado") == null) {

            // Se não estiver logado, chuta o cara pra tela de login
            response.sendRedirect("/login");
            return false; // Bloqueia o acesso à página solicitada
        }

        return true; // Deixa passar se estiver logado
    }
}