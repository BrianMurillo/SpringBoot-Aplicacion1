package com.ciencias.service;

import com.ciencias.entity.Usuario;
import com.ciencias.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class UsuarioServiceImpl implements UsuarioService{

    @Autowired
    private UserRepository userRepository;


    @Override
    public Iterable<Usuario> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public Usuario saveUser(Usuario usuario) throws Exception {
        if(checkUsernameAvailable(usuario) && checkPasswordValid(usuario)) {
            usuario = userRepository.save(usuario);
        }
        return usuario;
    }

    public boolean checkUsernameAvailable(Usuario user) throws Exception {
        Set<Usuario> userFound = userRepository.findByUsername(user.getUsername());
        if(!userFound.isEmpty()){
            throw new Exception("Username no disponible");
        }
        return true;
    }

    public boolean checkPasswordValid(Usuario user) throws Exception {
       if(!user.getPassword().equals(user.getConfirmPassword())){
           throw new Exception("Passwords no son iguales");
       }
       return true;
    }
}
