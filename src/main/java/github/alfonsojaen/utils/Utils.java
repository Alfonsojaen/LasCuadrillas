package github.alfonsojaen.utils;

import java.security.MessageDigest;

public class Utils {
    public static String encryptSHA256 (String s){
        String result = null;
        try {
            MessageDigest md = MessageDigest.getInstance("SHA256");

            md.update(s.getBytes()); 	// Se actualiza el MessageDigest con los bytes de la cadena de entrada

            StringBuilder sb = new StringBuilder();
            for (byte aByte : md.digest()) {
                sb.append(Integer.toString((aByte & 0xff) + 0x100, 16).substring(1));
            }
            result = sb.toString();
        }catch(Exception e){
            e.printStackTrace();
        }
        return result;  // Se devuelve el resultado del hash en formato hexadecimal
    }
}
