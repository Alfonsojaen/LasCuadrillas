package github.alfonsojaen.model.entity;

import java.io.Serializable;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Objects;

public class User implements Serializable {
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
    public User(){
        this("","","","");
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

    /**
     * Metodo para la contraseña de usuario
     * @param password Se pasa hasheada
     */
    public void setPassword(String password) {
        try {

            MessageDigest digest = MessageDigest.getInstance("SHA-256");

            byte[] hashedBytes = digest.digest(password.getBytes());

            StringBuilder stringBuilder = new StringBuilder();
            for (byte b : hashedBytes) {
                stringBuilder.append(String.format("%02x", b));
            }
            String hashedPassword = stringBuilder.toString();

            this.password = hashedPassword;
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
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

    /**Ningun usuario puede tener un Gmail un nombre de usuario igual
     * @param o
     * @return de vuelve si es igual o no
     */
    @Override
    public boolean equals(Object o) {
        boolean igual;
        if (this == o){
            igual= true;
        }
        if (o == null || getClass() != o.getClass()){
            igual= false;
        }
        User user = (User) o;
        igual = Objects.equals(nameUser, user.nameUser) || Objects.equals(gmail, user.gmail);
        return igual;
    }




    @Override
    public String toString() {
        String hiddenPassword = password.replaceAll(".", "*");
        return "Usuario -> " + name + '\''+
                "Nombre del usuario : " + nameUser  + '\'' +
                "Contraseña : " + hiddenPassword + '\'' +
                "Gmail del usuario : " + gmail  ;
    }

    /**
     * Compara la contraseña del usuario con la que tiene
     * @param inputPassword la contraseña introducida por el usuario
     * @return devuelve si la contraseña es valida con la suya
     */
    public boolean comparePassword(String inputPassword) {
        boolean comp;
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");

            byte[] hashedBytes = digest.digest(inputPassword.getBytes());

            StringBuilder stringBuilder = new StringBuilder();
            for (byte b : hashedBytes) {
                stringBuilder.append(String.format("%02x", b));
            }
            String hashedInputPassword = stringBuilder.toString();

            comp = hashedInputPassword.equals(password);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            comp = false;
        }
        return comp;
    }
}

