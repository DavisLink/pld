package com.legvit.pld.stallum.vo;

/**
 *
 * @author Ruben Ramírez
 */
public class Token {
    private String cadena;

    public String getCadena() {
        return cadena;
    }

    public void setCadena(String cadena) {
        this.cadena = cadena;
    }

    public int length(){
        return this.getCadena().length();
    }

    public boolean matches(Token token){
        return this.getCadena().matches(token.getCadena() + "(.*)");
    }
    
    public char[] toCharArray(){
        return this.getCadena().toCharArray();
    }
}
