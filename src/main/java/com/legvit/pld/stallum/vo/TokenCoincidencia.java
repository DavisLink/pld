package com.legvit.pld.stallum.vo;

import java.util.ArrayList;
import java.util.List;


/**
 *
 * @author Ruben Ramírez
 */
public class TokenCoincidencia extends Token{

    private List<Letra> letrasCoincidencias;
    private List<Letra> letrasCoincidenciasReversa;
    private float porcentajeCoincidencias;
    

    public void matchToken(Token token){
        letrasCoincidencias = new ArrayList<Letra>();
        letrasCoincidenciasReversa = new ArrayList<Letra>();
        boolean pass =false;
        try{
            //Compara si los token son exactamente iguales
            if(this.length() == token.length() && this.matches(token)){
                int cont=0;
                for(char letraToken :token.toCharArray()){
                        Letra letraTokenCoin = new Letra();
                        letraTokenCoin.setCaracter(letraToken);
                        letraTokenCoin.setPosicion(cont);
                        letrasCoincidencias.add(letraTokenCoin);
                    }
                this.porcentajeCoincidencias = obtienesPorcentaje(token);
            }
            //Compara si los token son iguales pero tokenCoincidencia mayor  que token
            else if (this.length() > token.length() && this.matches(token)){
                int cont=0;
                for(char letraToken :token.toCharArray()){
                        Letra letraTokenCoin = new Letra();
                        letraTokenCoin.setCaracter(letraToken);
                        letraTokenCoin.setPosicion(cont);
                        letrasCoincidencias.add(letraTokenCoin);
                    }                
                //this.porcentajeCoincidencias = Integer.parseInt(Propiedades.get("porcentaje.coincidencia.igual"));
                this.porcentajeCoincidencias = 51;
                pass = true;
            }
            //Compara si los token son iguales pero token  mayor  que tokenCoincidencia
            else if (token.length() > this.length() && token.matches(this)){
                int cont=0;
                for(char letraToken :this.toCharArray()){
                        Letra letraTokenCoin = new Letra();
                        letraTokenCoin.setCaracter(letraToken);
                        letraTokenCoin.setPosicion(cont);
                        letrasCoincidencias.add(letraTokenCoin);
                    }                
                //this.porcentajeCoincidencias = Integer.parseInt(Propiedades.get("porcentaje.coincidencia.igual"));
                //this.porcentajeCoincidencias = 51;
                //pass = true;
            }    
            else{
                //Compara iterando letra por letra
                boolean letraCoincide = false;
                boolean finMatchReverse = false;
                boolean coincLetra1 = true;
                for(char letraToken :token.toCharArray()){
                    int cont = 0;
                    for(char letra :this.toCharArray()){
                        //Posicionarse en la letra siguiente
                        if(letrasCoincidencias.size()>0){
                           Letra letraUltima = letrasCoincidencias.get(letrasCoincidencias.size()-1);
                           if(cont<=letraUltima.getPosicion()){
                               cont++;
                               continue;
                           }
                        }
                        Letra letraTokenCoin = new Letra();
                        letraTokenCoin.setCaracter(letra);
                        letraTokenCoin.setPosicion(cont);
                        //Si las letras de ambos lados son iguales
                        if(letraToken == letraTokenCoin.getCaracter()){
                            letrasCoincidencias.add(letraTokenCoin);
                            letraCoincide = true;
                            break;
                        }else{
                            if(cont == 0){
                                coincLetra1 = false;
                                break;
                            }
                            //Se realiza Match-reversa
                            matchReverseToken(token);
                            finMatchReverse = true;
                            break;
                        }
                    }
                    if(coincLetra1 == false) break;
                    cont++;
                    if(finMatchReverse) break;
                    if(letraCoincide){
                        letraCoincide = false;
                        continue;
                    }
                }
            }
            if(pass == false) obtienesPorcentaje(token);
        }catch(Exception e){
            e.getStackTrace();
        }
    }
    
    public void matchReverseToken(Token token ){
        int posicionUltimaCoinc = this.length() - (letrasCoincidencias.size());
        String tokenReversed=new StringBuffer(token.getCadena()).reverse().toString();
        String tokenCoinReversed=new StringBuffer(this.getCadena()).reverse().toString();
        boolean letraCoincide = false;
        boolean finMatch = false;
        for(char letraToken :tokenReversed.toCharArray()){
            int cont = 0;
            for(char letra : tokenCoinReversed.toCharArray()){
                //Si posicion actual es mayor o igual a la posicion ultima de coincidencia (izq-der)
                if(cont >= posicionUltimaCoinc){
                    finMatch = true;
                    break;
                }
                //Posicionarse en la letra siguiente
                if(letrasCoincidenciasReversa.size()>0){
                   Letra letraUltima = letrasCoincidenciasReversa.get(letrasCoincidenciasReversa.size()-1);
                   if(cont<=letraUltima.getPosicion()){
                       cont++;
                       continue;
                   }
                       
                }                
                Letra letraTokenCoin = new Letra();
                letraTokenCoin.setCaracter(letra);
                letraTokenCoin.setPosicion(cont);
                
                //Si las letras de ambos lados son iguales
                if(letraToken == letraTokenCoin.getCaracter()){
                    letrasCoincidenciasReversa.add(letraTokenCoin);
                    letraCoincide = true;
                    cont++;
                    break;
                }else{
                    finMatch  = true;
                    break;
                }
            }
            if(finMatch) break;
            if(letraCoincide){
                letraCoincide = false;
                continue;
            }            
        }

        
    }
    
    public float obtienesPorcentaje(Token token){
        float porcentaje = 0f;
        float porcentajeReversa = 0f;
        float porcentajeTotal = 0f;
        try{
            int longitud = token.length();
            //Regla de 3 para letras coincidencias (ïzq-der)
            if(this.getLetrasCoincidencias().size()>0)
                //porcentaje = (longitud*100)/this.getLetrasCoincidencias().size();
                porcentaje = (this.getLetrasCoincidencias().size()*100)/longitud;
            //Regla de 3 para letras coincidencias (der-izq)
            if(this.getLetrasCoincidenciasReversa().size()>0)
                porcentajeReversa = (this.getLetrasCoincidenciasReversa().size()*100)/longitud;
            porcentajeTotal = (porcentaje+porcentajeReversa > 100)?100.00f:porcentaje+porcentajeReversa;
            this.setPorcentajeCoincidencias(porcentaje+porcentajeReversa);
        }catch(Exception e){
            e.getStackTrace();
        }
        return porcentaje;
        
    }

    public String toStringLetrasCoincidencias(){
        String cadena = "";
        if(letrasCoincidencias.size()>0){
            StringBuffer sf = new StringBuffer();
            for(Letra letra: letrasCoincidencias){
                sf.append(letra.getCaracter() + ",");
            }
            cadena = sf.toString();
        }
        return cadena;
        
    }
    
    public String toStringLetrasCoincidenciasReverse(){
        String cadena = "";
        if(letrasCoincidenciasReversa.size()>0){
            StringBuffer sf = new StringBuffer();
            for(Letra letra: letrasCoincidenciasReversa){
                sf.append(letra.getCaracter() + ",");
            }
            cadena = sf.toString();
        }
        return cadena;
        
    }    
    public List<Letra> getLetrasCoincidencias() {
        return letrasCoincidencias;
    }

    public void setLetrasCoincidencias(List<Letra> letrasCoincidencias) {
        this.letrasCoincidencias = letrasCoincidencias;
    }

    public float getPorcentajeCoincidencias() {
        return porcentajeCoincidencias;
    }

    public void setPorcentajeCoincidencias(float porcentajeCoincidencias) {
        this.porcentajeCoincidencias = porcentajeCoincidencias;
    }

    public List<Letra> getLetrasCoincidenciasReversa() {
        return letrasCoincidenciasReversa;
    }

    public void setLetrasCoincidenciasReversa(List<Letra> letrasCoincidenciasReversa) {
        this.letrasCoincidenciasReversa = letrasCoincidenciasReversa;
    }
    
}
