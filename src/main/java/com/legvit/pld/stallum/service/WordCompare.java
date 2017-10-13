package com.legvit.pld.stallum.service;

import com.legvit.pld.stallum.vo.Word;

public class WordCompare {
	
	//private String log;
	private int lastPlace = 0;
	private int success = 0;
	private int partial = 0;
	private int wordLenght = 0;
	private double score = 0d;
	private int tries = 0;
	private int mode;
        
	public Word wordCompare(Word tokenA, Word tokenB, int mode){
		double percentage = 0d;
		Word aux;
                this.mode = mode;
		//log = "";
		lastPlace = -1;
		success = 0;
		partial = 0;
		
		//addToLog("Se comparar�n las palabras \"" + tokenA.getWord() + "\" y \"" + tokenB.getWord() + "\"");
		
		// Se verifica el caso de que sean exactamente iguales:
		if(tokenA.getWord().equals(tokenB.getWord())){
			//addToLog("Son exactamente iguales");
			tokenA.setMaxPercentage(100d, tokenB.getPosition());
			tokenA.setScore(tokenA.getLength());
			return tokenA;
		}
		
		// Se verifica cual de los 2 tokens es mas grande para colocarlo en TokenA
		if(tokenA.getLength() < tokenB.getLength()){
			aux = new Word(tokenA);
			tokenA = new Word(tokenB);
			tokenB = new Word(aux);
		}
                
                tries = 0;
		
		// realiza el an�lisis normal (izq -> der)
		percentage = compare(tokenA, tokenB, false);
		
		// Realiza el an�lisis inverso (der -> izq)
		if(percentage < 0 && lastPlace < tokenB.getLength()){
			// Se invierten tokenA y tokenB
			if(lastPlace == -1){
				tokenA.reverse();
				tokenB.reverse();
			}
			else {
				tokenA.reverse(lastPlace+1);
				tokenB.reverse(lastPlace+1);
			}
			percentage = compare(tokenA, tokenB, true);
			tokenA.returnToNormal();
			tokenB.returnToNormal();
		}
		
		tokenA.setMaxPercentage(percentage, tokenB.getMatchPosition());
		tokenA.setScore(score);
		
		return tokenA;
	}
	
	private double compare(Word tokenA, Word tokenB, boolean inverse) {
		double percentage = 0d;
		double score = 0d;
		char[] A;
		char[] B;
		int success = 0;
		int partial = 0;
		int errores = 0;
		int lengthDiference = 0;
		int weighting = 100;
		int wordLenght = 0;
		boolean found = false;
		String word1 = tokenA.getWord();
		String word2 = tokenB.getWord();
		
		//addToLog("\"" + word1 + "\" es de mayor o igual longitud que \"" + word2 + "\"");
		lengthDiference = word1.length() - word2.length();
		
		// Se agregan espacios blancos para que la longitud de las palabras quede igual
		//addToLog("Se agregar�n * a \"" + word2 + "\" para que ambas palabras tengan la misma longitud:");
		for(int i = 0; i < lengthDiference; i++) {
			word2 += "*";
		}
		//addToLog("Palabra1 = |" + word1 + "|");
		//addToLog("Palabra2 = |" + word2 + "|");
		A = word1.toCharArray();
		B = word2.toCharArray();
		
		wordLenght = word1.length();
		if(!inverse)
			this.wordLenght = wordLenght;
		
		//addToLog("La longitud para ambas palabras queda de " + wordLenght + " letras");
		
		//addToLog("Se realiza la iteraci�n:");
		
		// Se itera sobre la primera palabra
		for(int position = 0; position < wordLenght; position++) {
			//addToLog("\tPosici�n " + position + ":");
			found = false;
			if(A[position] == B[position]){
				//addToLog("\tLa letra " + A[position] + " de Palabra1 coincide con la letra " + B[position] + " de Palabra2");
				success++;
				found = true;
				tries++;
			}
			else {
				// Comparar una posici�n atras (siempre que no sea la primera posicion)
				if(position > 0) {
					//addToLog("\tLa letra " + A[position] + " No coincidi� en la misma posici�n, se compara con la posici�n n-1:");
					if(A[position] == B[position-1]){
						//addToLog("\tLa letra " + A[position] + " de Palabra1 coincide con la letra " + B[position-1] + " de Palabra2");
						partial++;
						found = true;
						tries += 2;
					}
				}
				// Comparar una posici�n adelante
				if(!found && position < (wordLenght-1)) {
					//addToLog("\tLa letra " + A[position] + " No coincidi� con la posici�n n-1, se compara con la posici�n n+1:");
					if(A[position] == B[position+1]){
						//addToLog("\tLa letra " + A[position] + " de Palabra1 coincide con la letra " + B[position+1] + " de Palabra2");
						partial++;
						found = true;
						tries += 2;
					}
				}
			}
			if(!found){
				//addToLog("\tNo hubo coincidencia para la letra " + A[position] + " de Palabra1");
				errores++;
				if(position > 0 && position < (wordLenght-1))
					tries += 3;
				else
					tries += 2;
			}
			// Al llegar a 3 errores se realiza el an�lisis de las palaras invertidas
			if(errores == 3){
                                lastPlace = position;
				// Si los 3 errores se encontraron al realizar el an�lisis inverso, es 0 autom�ticamente
				if(inverse){
					//addToLog("\n\t* Se han encontrado 3 errores durante el an�lisis inverso");
					//addToLog("\t* Se obtiene un porcentaje de 0.0%");
					return 0.0d;
				}
				//addToLog("Se han encontrado 3 errores, se realizar� el an�lisis inverso");
				// Se almacenan los datos obtenidos en el primer an�isis (iq -> der)
				this.success = success;
				this.partial = partial;
				this.wordLenght = wordLenght;
				return -1d;
			}
		}
		
		//addToLog("Termina la iteraci�n\n");
		
		// Se suma el score obtenido en la revisi�n izq -> der
		success = this.success + success;
		partial = this.partial + partial;
		
		/* CASOS
		 * Si la palabra est� inversa, se tomar� la ponderaci�n al 90 y los partial se valuan en 1 // caso 1
		 * Si hay 1 error y 0 partial se deja en 100 la ponderaci�n // caso 2
		 * Si hay 2 partial y 0 errores la weighting se toma al 95 y los partial se valuan en 1 // caso 3
		 * Si hay 2 o mas partial y 1 error o mas, los partial valdr�n 1 y se pondera al 95 // caso 4
		 * Si hay solo 1 parcial y 0 errores se deja en 100 a ponderaci�n y el parcial se val�a en .5 // caso 5
		 * Si hay 0 errores
		 * Para todos los dem�s casos, se toma una ponderaci�n del 95 y los partial valen .5 // Caso 6
		 */
	
		//addToLog("Se calcula el valor de los parciales y de la ponderaci�n...");
		// Se realiza el c�lculo de la ponderaci�n y los partial
                // Se pone el valaor de mode en hard code para pruebas
                //mode = 1;
                if(mode != 0){
                    if(inverse) {
			if(partial >= this.wordLenght) {
                            //addToLog("Caso 0");
                            weighting = 95;
                            score = ((double)partial) / 2;
			}	
			else {
                            //addToLog("Caso 1");
                            weighting = 95;
                            score = partial;
			}
                    }
                    else if(errores == 1 && partial < 2) {
                        //addToLog("Caso 2");
                        weighting = 100;
                        score = ((double)partial) / 2;
                    }
                    else if(errores == 0 && partial == 2){
                        //addToLog("Caso 3");
                        weighting = 95;
                        score = partial;
                    }
                    else if(errores > 0 && partial > 1){
                        //addToLog("Caso 4");
                        weighting = 95;
                        score = partial;
                    }
                    else if(errores == 0 && partial == 1){
                        //addToLog("Caso 5");
                        weighting = 100;
                        score = 0.5d;
                    }
                    else if(partial > success){
                        //addToLog("Caso 6");
                        weighting = 95;
                        score = partial;
                    }  
                    else {
                        //addToLog("Caso 7");
                        weighting = 95;
                        score = ((double)partial) / 2;
                    }                    
                    score += success;
                }
                else{
                    score += success + partial;
                }
                
		this.score = score;
		if(mode == 0 ) { // Divide entre número de intentos
                    percentage = getPercentage(score, this.tries, weighting);
                }
                else{ // Divide entre cantidad de letras
                    percentage = getPercentage(score, this.wordLenght, weighting);
                }
		
		//addToLog("Aciertos: " + success);
		//addToLog("Parciales: " + partial);
		//addToLog("Puntaje: " + score);
		//addToLog("Errores: " + errores);
		//addToLog("Longitud total: " + this.wordLenght);
		//addToLog("N�mero de intentos realizados: " + this.tries);
		//addToLog("Ponderaci�n: " + weighting);
		//addToLog("\n\tRESULTADO: " + percentage + "%");
		
		tokenA.setMaxPercentage(percentage, tokenB.getPosition());
		tokenB.setMaxPercentage(percentage, tokenB.getPosition());
		tokenA.setScore(score);
		tokenB.setScore(score);
		
		return percentage;
	}
	
	// Realiza el c�lculo del percentage
	public double getPercentage(double success, int tries, int weighting) {
		double cantidad = success / (double)tries;
		double percentage = cantidad * (double)weighting;
		// Se redondea a 2 decimales
		percentage = Math.round(percentage * 100.0) / 100.0;
		return percentage;
	}
	
	// Agrega lineas de texto al log
	/*public void //addToLog(String line){
		log = log + line + "\n";
	}*/
	
	/*public String getLog(){
		if(log == null){
			log = "";
		}
		return log;
	}*/

}