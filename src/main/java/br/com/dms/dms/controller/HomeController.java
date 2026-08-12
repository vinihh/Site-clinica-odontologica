package br.com.dms.dms.controller;

import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping("/")
    public String home(HttpSession session, Model model) {

        String usuario = (String) session.getAttribute("usuarioLogado");

        model.addAttribute("usuario", usuario);

        return "home";
    }
}
