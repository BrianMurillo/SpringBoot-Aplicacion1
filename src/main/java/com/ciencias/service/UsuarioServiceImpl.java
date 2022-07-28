package com.ciencias.service;

import com.ciencias.entity.Usuario;
import com.ciencias.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UsuarioServiceImpl implements UsuarioService{

    @Autowired
    private UserRepository userRepository;


    @Override
    public Iterable<Usuario> getAllUsers() {
        return userRepository.findAll();
    }
}
