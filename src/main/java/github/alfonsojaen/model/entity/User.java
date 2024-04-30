package github.alfonsojaen.model.entity;

import java.io.Serializable;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.Objects;

public class User  {
    private String username;
    private String password;
    private String email;
    private String name;
    private List<Cuadrilla> Cuadrilla;


    public User(String nameUser, String password, String email, String name) {
        this.username = nameUser;
        this.password = password;
        this.email = email;
        this.name = name;
    }

    public User() {
        this("", "", "", "");
    }

    public String getUsername() {
            return username;
    }

    public void setUsername(String nameUser) {
        this.username = nameUser;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password){this.password = password; }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Cuadrilla> getCuadrilla() {
        return Cuadrilla;
    }

    public void setCuadrilla(List<Cuadrilla> cuadrilla) {
        Cuadrilla = cuadrilla;
    }

    @Override
    public String toString() {
        String hiddenPassword = password.replaceAll(".", "*");
        return "Usuario -> " + name + '\'' +
                "Nombre del usuario : " + username + '\'' +
                "Contraseña : " + hiddenPassword + '\'' +
                "Gmail del usuario : " + email;
    }

}