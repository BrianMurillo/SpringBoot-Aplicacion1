package com.ciencias.service;

import com.ciencias.entity.Usuario;

public interface UsuarioService {
    public Iterable<Usuario> getAllUsers();
    public Usuario saveUser(Usuario usuario) throws Exception;

    public  Usuario getUserById(Long id) throws Exception;

    public Usuario updateUser(Usuario usuario) throws Exception;
    public void deleteUsuario(Long id)throws Exception;
}
