package com.ciencias.service;

import com.ciencias.dto.ChangePasswordForm;
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

    @Override
    public Usuario getUserById(Long id) throws Exception {
        return userRepository.findById(id).orElseThrow(() -> new Exception("El usuario no existe"));
    }

    @Override
    public Usuario updateUser(Usuario fromUser) throws Exception {
        Usuario toUser = getUserById(fromUser.getId());
        mapUser(fromUser,toUser);
        return userRepository.save(toUser);
    }

    @Override
    public void deleteUsuario(Long id) throws Exception {
        Usuario usuario = userRepository.findById(id).orElseThrow(()->new Exception("User not found for delete"+this.getClass().getName()));
        userRepository.delete(usuario);
    }

    @Override
    public Usuario changePassword(ChangePasswordForm form) throws Exception{
        Usuario user = userRepository
                .findById( form.getId() )
                .orElseThrow(() -> new Exception("UsernotFound in ChangePassword -"+this.getClass().getName()));

        if(!user.getPassword().equals(form.getCurrentPassword())){
            throw new Exception("Current Password invalido");
        }

        if(user.getPassword().equals(form.getNewPassword())){
            throw new Exception("Nuevo debe ser diferente al password actual");
        }

        if(!form.getNewPassword().equals(form.getConfirmPassword())){
            throw new Exception("Nuevo y current password no coinciden");
        }

        user.setPassword(form.getNewPassword());
        return userRepository.save(user);
    }

    protected void mapUser(Usuario from, Usuario to){
        to.setUsername(from.getUsername());
        to.setFirstName(from.getFirstName());
        to.setLastName(from.getLastName());
        to.setEmail(from.getEmail());
        to.setRoles(from.getRoles());
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
