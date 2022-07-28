package com.ciencias.service;

import com.ciencias.entity.Usuario;

public interface UsuarioService {
    public Iterable<Usuario> getAllUsers();
    public Usuario saveUser(Usuario usuario) throws Exception;
}
