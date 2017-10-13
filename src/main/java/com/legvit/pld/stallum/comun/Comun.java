package com.legvit.pld.stallum.comun;

import java.util.ArrayList;
import java.util.List;

import com.legvit.pld.stallum.utilerias.PhoneticSpanish;
import com.legvit.pld.stallum.utilerias.Utilerias;
import com.legvit.pld.stallum.vo.PalabraNombre;
import com.legvit.pld.stallum.vo.Token;

/**
 *
 * @author Ruben Ramírez
 */
public class Comun {
    
        // MASM - 09/12/2015 - Se agregan variables para utilizar un modo permisivo de 1 apellido
        // Indica si se activa el modo permisivo
        private static boolean permisiveMatch;
        // Indica el mímimo orcentaje para incluir un token dentro del modo permisivo
        private static double factor;
        // Indica el numero minimo de tokens que deben coincidir para calcular en el modo permisivo
        private static int minimumTokens;
    
        public static List<Token> transformaCadenaATokens(String cadena){
            List<Token> listaToken = new ArrayList<Token>();
            try{
                String[] obj = cadena.split("\\ ");
                //Se crean los token
                for(String objCadena:obj){
                    Token token = new Token();    
                    token.setCadena(objCadena);
                    listaToken.add(token);
                }                
            }catch(Exception e){
                e.getStackTrace();
            }
            return listaToken;
        }    
        
        public static String transformaANombreCompleto(String nombre, String apPaterno, String apMaterno){
            StringBuffer cadena = new StringBuffer();
            try{
                if(!"".equals(nombre))
                    cadena.append(nombre);
                if(!"".equals(apPaterno) && apPaterno != null){
                    cadena.append(" ");
                    cadena.append(apPaterno);
                }
                if(!"".equals(apMaterno) && apMaterno != null){
                    cadena.append(" ");
                    cadena.append(apMaterno);
                }
            }catch(Exception e){
                e.printStackTrace();
            }

            return cadena.toString();
        }
        
        // MASM - 26/11/2015 - Elimina caracteres especiales
        public static String removeSpecialChars(String nombre){
            String clean = nombre.toLowerCase();
            // Elimina acentos
            clean = clean.replace("á", "a");
            clean = clean.replace("é", "e");
            clean = clean.replace("í", "i");
            clean = clean.replace("ó", "o");
            clean = clean.replace("ú", "u");
            // Cambia la ñ
            clean = clean.replace("ñ", "n");
            // Elimina diéresis
            clean = clean.replace("ä", "a");
            clean = clean.replace("ë", "e");
            clean = clean.replace("ï", "i");
            clean = clean.replace("ö", "o");
            clean = clean.replace("ü", "u");
            // Sustituye caracteres espceciales
            clean = clean.replaceAll("[^a-zA-Z0-9 ]+","");
            // Elimina dobles espacios
            clean = clean.replace("  ", " ");
            // Elimina espacios al inicio y al final
            clean = clean.trim();
            return clean;
        }
        
        // MASM - 30/11/2015 - Jun+ta los artículos con la palabra a la derecha
	public static String removeArticles(String name) {
            String result = name.toUpperCase();
            result = result.replaceAll(" DE ", " DE");
            result = result.replaceAll(" DEL ", " DEL");
            result = result.replaceAll(" EL ", " EL");
            result = result.replaceAll(" LOS ", " LOS");
            result = result.replaceAll(" LA ", " LA");
            result = result.replaceAll(" LAS ", " LAS");
            result = result.replaceAll(" UN ", " UN");
            result = result.replaceAll(" UNA ", " UNA");
            result = result.replaceAll(" A ", " A");
            result = result.replaceAll(" AL ", " AL");
            result = result.replaceAll(" Y ", " Y");
            result = result.replaceAll(" E ", " ");
            return result;
	}

    public static String removeSociedadMercantil(String nombre, List<String> sociedadesMercantiles){
        String clean = nombre.toUpperCase();
        for (int i = 0; i < sociedadesMercantiles.size(); i++) {
            clean = eliminaInfoDerecha(clean, sociedadesMercantiles.get(i));
        }
        
        return clean;
    }

    private static String eliminaInfoDerecha(String str, String info) {
        String tmpStrMayus;
        int contador;
        int posicion;

        tmpStrMayus = str.toUpperCase();
        info = info.toUpperCase();
        contador = 1;
        posicion = tmpStrMayus.length();
        while (contador <= info.length() && posicion != 0) {
            if (info.substring(info.length() - contador, info.length() - contador + 1).equals(tmpStrMayus.substring(posicion - 1, posicion))) {
                contador++;
            } else if (!".".equals(tmpStrMayus.substring(posicion - 1, posicion)) && !" ".equals(tmpStrMayus.substring(posicion - 1, posicion))
                    && !",".equals(tmpStrMayus.substring(posicion - 1, posicion)) && !";".equals(tmpStrMayus.substring(posicion - 1, posicion))) {
                break;
            }

            posicion--;
        }

        //Verificamos que la posición sea mayor a cero
        if (posicion > 0 && " ".equals(str.substring(posicion - 1, posicion))) {
            str = str.substring(0, posicion).trim();
        }

        return str;
    }

    public static List<PalabraNombre> getPalabrasNombre(String nombre) throws PldException, Exception{
        Utilerias util = new Utilerias();
        List<PalabraNombre> palabrasNombre = new ArrayList<PalabraNombre>();
        List<Token> tokens = transformaCadenaATokens(nombre.toUpperCase());
        int consecutivo = 1;
        for(Token token : tokens) {
        	//TODO VALIDAR EL VALOR DE LA PROPIEDAD Propiedades.get("regExp.articulos")
            if(token.getCadena().length() > 1 && !util.validaRegExp("", token.getCadena())) {
                PalabraNombre palabra = new PalabraNombre();
                palabra.setPersonaPalabra(token.getCadena());
                palabra.setConsecutivo(consecutivo);
                consecutivo++;
                palabrasNombre.add(palabra);
            }
        }
        return palabrasNombre;
    }

    public static List<PalabraNombre> getPalabrasFoneticas(String nombre) throws PldException, Exception{
        PhoneticSpanish fonetico = new PhoneticSpanish();
        List<PalabraNombre> palabrasNombre =  Comun.getPalabrasNombre(nombre);
        List<PalabraNombre> palabrasFoneticas = new ArrayList<PalabraNombre>();
        for(PalabraNombre palabra : palabrasNombre) {
            PalabraNombre palabraFonetica = new PalabraNombre();
            palabraFonetica.setPersonaPalabra(fonetico.Fonetica(palabra.getPersonaPalabra()));
            palabraFonetica.setConsecutivo(palabra.getConsecutivo());
            palabrasFoneticas.add(palabraFonetica);
        }
        return palabrasFoneticas;
    }

    public static boolean isPermisiveMatch() {
        return permisiveMatch;
    }

    public static void setPermisiveMatch(boolean permisiveMatch1) {
        permisiveMatch = permisiveMatch1;
    }

    public static double getFactor() {
        return factor;
    }

    public static void setFactor(double factor1) {
        factor = factor1;
    }

    public static int getMinimumTokens() {
        return minimumTokens;
    }

    public static void setMinimumTokens(int minimumTokens1) {
        minimumTokens = minimumTokens1;
    }
        
        
}
