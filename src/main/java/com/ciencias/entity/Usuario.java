package com.ciencias.entity;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Objects;
import java.util.Set;

@Entity
//@Table(name = "usuario")
public class Usuario implements Serializable{

    public static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column
    @NotBlank
    @Size(min = 3, max = 10, message = "El tamaño no se cumple")
    private String firstName;
    @Column
    @NotBlank
    private String lastName;
    @Column
    @NotBlank
    @Email
    private String email;
    @Column
    @NotBlank
    private String username;
    @Column
    @NotBlank
    private String password;

    //La notacion hace que no se ocupe en la base de datos ese atributo, entonces se omite para la DB
    @Transient
    private String confirmPassword;

    @ManyToMany(fetch = FetchType.LAZY)
    //name es el nombre de nueva tabla
    //@Join column se agrega nombre de la columna
    //a que tablas se le hara la transaccion primero se crea la columna con el nombre user_id y luego la columna con el nombre de role_id
    @JoinTable(name = "user_roles", joinColumns = @JoinColumn(name = "user_id"),inverseJoinColumns = @JoinColumn(name = "role_id"))
    //Con se no se repiten valores
    private Set<Role> roles;

    public Usuario() {
    }

    public Usuario(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Usuario)) return false;
        Usuario usuario = (Usuario) o;
        return Objects.equals(id, usuario.id) && Objects.equals(firstName, usuario.firstName) && Objects.equals(lastName, usuario.lastName) && Objects.equals(email, usuario.email) && Objects.equals(username, usuario.username) && Objects.equals(password, usuario.password) && Objects.equals(confirmPassword, usuario.confirmPassword) && Objects.equals(roles, usuario.roles);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, firstName, lastName, email, username, password, confirmPassword, roles);
    }

    @Override
    public String toString() {
        return "Usuario{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", confirmPassword='" + confirmPassword + '\'' +
                ", roles=" + roles +
                '}';
    }
}
