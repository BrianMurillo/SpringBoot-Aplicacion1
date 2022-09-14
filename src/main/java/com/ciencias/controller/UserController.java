package com.ciencias.controller;

import com.ciencias.dto.ChangePasswordForm;
import com.ciencias.entity.Usuario;
import com.ciencias.repository.RoleRepository;
import com.ciencias.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;
import java.util.stream.Collectors;

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
    public String getRegistro(Model model,Usuario user){
        var usuarios = usuarioService.getAllUsers();
        var roles = roleRepository.findAll();
        model.addAttribute("usuario", user);
        model.addAttribute("usuarios",usuarios);
        model.addAttribute("roles",roles);
        model.addAttribute("listTab","active");
        return "user-form/user-view";
    }

    @PostMapping("/saveUser")
    public String saveUser(@Valid Usuario usuario, Errors errors, Model model){
        if(errors.hasErrors()){
            model.addAttribute("usuarios",usuarioService.getAllUsers());
            model.addAttribute("roles",roleRepository.findAll());
            model.addAttribute("formTab","active");
            return "user-form/user-view";
        } else {
            try {
                usuarioService.saveUser(usuario);
            } catch (Exception e) {
                model.addAttribute("usuarios",usuarioService.getAllUsers());
                model.addAttribute("formErrorMessage", e.getMessage());
                model.addAttribute("roles",roleRepository.findAll());
                model.addAttribute("formTab","active");
                return "user-form/user-view";
            }
        }
        return "redirect:/userForm";
    }

    @GetMapping("/editUser/{id}")
    public String getEditUserForm(Model model, @PathVariable(name="id")Long id) throws Exception {
        var usuario = usuarioService.getUserById(id);
        var usuarios = usuarioService.getAllUsers();
        var roles = roleRepository.findAll();
        model.addAttribute("usuario", usuario);
        model.addAttribute("usuarios",usuarios);
        model.addAttribute("roles",roles);
        model.addAttribute("formTab","active");
        model.addAttribute("editMode","true");
        model.addAttribute("passwordForm", new ChangePasswordForm(id));
        return "user-form/user-view";
    }

    @PostMapping("/editUser")
    public String postEditUser(@Valid Usuario user, Errors errors,Model model){
        if(errors.hasErrors()){
            model.addAttribute("usuarios",usuarioService.getAllUsers());
            model.addAttribute("roles",roleRepository.findAll());
            model.addAttribute("formTab","active");
            model.addAttribute("editMode","true");
            model.addAttribute("passwordForm", new ChangePasswordForm(user.getId()));
            return "user-form/user-view";
        } else {
            try {
                usuarioService.updateUser(user);
            } catch (Exception e) {
                model.addAttribute("usuarios",usuarioService.getAllUsers());
                model.addAttribute("formErrorMessage", e.getMessage());
                model.addAttribute("roles",roleRepository.findAll());
                model.addAttribute("formTab","active");
                model.addAttribute("editMode","true");
                model.addAttribute("passwordForm", new ChangePasswordForm(user.getId()));
                return "user-form/user-view";
            }
        }
        return "redirect:/userForm";
    }

    @GetMapping("/userForm/cancel")
    public String cancelEditUser(Model model){
        return "redirect:/userForm";
    }

    @GetMapping("/deleteUser/{id}")
    public String deleteUser(Model model, @PathVariable(name = "id")Long id) {
        try {
            usuarioService.deleteUsuario(id);
        } catch (Exception e) {
            model.addAttribute("listErrorMessage", e.getMessage());
        }
        return getRegistro(model, new Usuario());
    }

    @PostMapping("/editUser/changePassword")
    public ResponseEntity postEditUseChangePassword(@Valid @RequestBody ChangePasswordForm form, Errors errors) {
        try {
            //If error, just return a 400 bad request, along with the error message
            if (errors.hasErrors()) {
                String result = errors.getAllErrors()
                        .stream().map(x -> x.getDefaultMessage())
                        .collect(Collectors.joining(""));

                throw new Exception(result);
            }
            usuarioService.changePassword(form);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
        return ResponseEntity.ok("success");
    }
}
