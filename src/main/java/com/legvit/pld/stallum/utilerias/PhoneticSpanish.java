package com.legvit.pld.stallum.utilerias;
/**
 *
 * @author David Tovar
 */
public class PhoneticSpanish {

    String sTmp = "";
    String resto= "";
    String reemplazo = "";
    String primera_letra = "";
    String soundex = "";
    
    public String Fonetica(String sPalabra){
        sTmp = sPalabra.trim().toUpperCase();
        
        // Si no hay texto a procesar retornamos un texto vacÃ­o
        if(sTmp.equals("")){return "";}
        // realizamos un preprocesado del texto
        Limpieza();
        // aplicamos el algoritmo
        soundex();
        
        return soundex;
    }
    
    public void Limpieza (){
        /* 1) limpieza */
        // eliminamos la H inicial (incluso si hay mas de una)
        sTmp = sTmp.replaceAll("^(H+)(.*)", "$2");
        
        // retornar vacÃ­o si no nos queda texto para analizar
        if(sTmp.equals("")){return;}
        
        // eliminamos los acentos y la Ã‘
        String caracteres_buscar = "Ã‘Ã�Ã‰Ã�Ã“ÃšÃ€ÃˆÃŒÃ’Ã™Ãœ";
        String caracteres_reemplazar = "NAEIOUAEIOUU";
        
        String caracter = "";
        for (int i=0; i < caracteres_buscar.length(); i++){
            caracter = caracteres_buscar.substring(i, 1 + i);
            //System.out.println(caracteres_reemplazar.substring(i, 1 + i));
            if (caracteres_buscar.indexOf(caracter)>0){
                sTmp = sTmp.replaceAll(caracter, caracteres_reemplazar.substring(i, 1 + i));
            }// buscar.IndexOf(&caracter)>0
            
        }// i=1 to buscar.Length() ...
        
        // eliminamos caracteres no alfabÃ©ticos (nÃºmeros, signos, sÃ­mbolos, etc)
        sTmp = sTmp.replaceAll("[^A-Z]", "");
        
        /* 2) ajustar primera letra */
        // fenÃ³menos o casos especiales: GE y GI se convierten en JE y JI, CA en KA
        primera_letra = sTmp.substring(0,1);
        resto = sTmp.substring(1,sTmp.length());
        
        if(primera_letra.equals("V")) {
            reemplazo = "B"; // VACA -> BACA, VALOR ->BALOR
        }else if(primera_letra.equals("Z") || primera_letra.equals("X")) {
            reemplazo = "S"; // ZAPATO -> SAPATO, XILÃ“FONO -> SILÃ“FONO
        }else if(primera_letra.equals("G") && (sTmp.substring(1, 2).equals("E") || sTmp.substring(1, 2).equals("I"))) {
            reemplazo = "J"; // GIMNASIO -> JIMNASIO, GERANIO -> JERANIO
        }else if(primera_letra.equals("C") && !sTmp.substring(1, 2).equals("H") && !sTmp.substring(1, 2).equals("E") && !sTmp.substring(1,2).equals("I")) {
            reemplazo = "K"; // CASA -> KASA, COLOR ->KOLOR, CULPA -> KULPA
        }else{
            reemplazo = primera_letra;
        }
        
        sTmp = reemplazo + resto;
        
        /* 3) corregir letras compuestas, volverlas una sola*/
        sTmp = sTmp.replaceAll("CH", "V");
        sTmp = sTmp.replaceAll("QU", "K");
        sTmp = sTmp.replaceAll("LL", "J");
        sTmp = sTmp.replaceAll("CE", "S");
        sTmp = sTmp.replaceAll("CI", "S");
        sTmp = sTmp.replaceAll("YA", "J");
        sTmp = sTmp.replaceAll("YE", "J");
        sTmp = sTmp.replaceAll("YI", "J");
        sTmp = sTmp.replaceAll("YO", "J");
        sTmp = sTmp.replaceAll("YU", "J");
        //sTmp = sTmp.ReplaceRegEx("GE", "J")
        //sTmp = sTmp.ReplaceRegEx("GI", "J")
        sTmp = sTmp.replaceAll("NY", "N");
        sTmp = sTmp.replaceAll("NH", "N"); // anho, banho, tamanho, inhalador
    } // "limpieza" ...
    
    public void soundex(){
        //4) obtener primera letra
        primera_letra = sTmp.substring(0, 1);
        //5) obtener el resto del texto 
        resto = sTmp.substring(1, sTmp.length());
        //6) en el resto, eliminar vocales y consonantes fonÃ©tica
        resto = resto.replaceAll("[AEIOUHWY]","");
        //7) convertir letras fonÃ©ticamente equivalentes a nÃºmero
        resto = resto.replaceAll("[BPFV]", "1");
        resto = resto.replaceAll("[CGKSXZ]", "2");
        resto = resto.replaceAll("[DT]", "3");
        resto = resto.replaceAll("[L]", "4");
        resto = resto.replaceAll("[MN]", "5");
        resto = resto.replaceAll("[R]", "6");
        resto = resto.replaceAll("[QJ]", "7");
        //Eliminamos nÃºmeros iguales adyacentes7
        resto = resto.replaceAll("(\\d)\\1+", "$1");
        soundex = primera_letra + resto.trim();

        if(soundex.length() < 4){
           //soundex = String.format("%1$-4s", soundex);
           soundex = soundex + "0000";
           soundex = soundex.substring(0, 4);
        }else{
            soundex = soundex.substring(0,4);
        }
    }
}