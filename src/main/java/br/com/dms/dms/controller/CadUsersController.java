package br.com.dms.dms.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class CadUsersController {

    @GetMapping("/cad-usuario")
    public String usuarios() {
        return "cad-usuario";
    }

}