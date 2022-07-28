package com.ciencias.repository;

import com.ciencias.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface UserRepository extends JpaRepository<Usuario,Long> {
    //Ejemplo de consulta
    public Set<Usuario> findByUsername(String username);
    public Set<Usuario> findByPassword(String password);
    //un suponer que quisieramos traer a todos los usuarios activos
    //public Set<Usuario> findAllByStatus(String status);
}
