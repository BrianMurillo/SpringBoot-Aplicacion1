package com.ciencias.controller;

import com.ciencias.entity.Usuario;
import com.ciencias.repository.RoleRepository;
import com.ciencias.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class UserController {

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private RoleRepository roleRepository;

    @GetMapping("/")
    public String getHome(){
        return "index";
    }

    @GetMapping("/userForm")
    public String getRegistro(Model model, Usuario usuario){
        var usuarios = usuarioService.getAllUsers();
        var roles = roleRepository.findAll();
        model.addAttribute("userForm", usuario);
        model.addAttribute("usuarios",usuarios);
        model.addAttribute("roles",roles);
        return "user-form/user-view";
    }

}
