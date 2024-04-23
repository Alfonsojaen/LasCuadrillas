package github.alfonsojaen.model.entity;

import java.io.Serializable;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Objects;

public class User  {
    private String nameUser;
    private String password;
    private String gmail;
    private String name;

    public User(String nameUser, String password, String gmail, String name) {
        this.nameUser = nameUser;
        setPassword(password);
        this.gmail = gmail;
        this.name = name;
    }

    public User() {
        this("", "", "", "");
    }

    public String getNameUser() {
        return nameUser;
    }

    public void setNameUser(String nameUser) {
        this.nameUser = nameUser;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
    }

    public String getGmail() {
        return gmail;
    }

    public void setGmail(String gmail) {
        this.gmail = gmail;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    @Override
    public String toString() {
        String hiddenPassword = password.replaceAll(".", "*");
        return "Usuario -> " + name + '\'' +
                "Nombre del usuario : " + nameUser + '\'' +
                "Contraseña : " + hiddenPassword + '\'' +
                "Gmail del usuario : " + gmail;
    }

}