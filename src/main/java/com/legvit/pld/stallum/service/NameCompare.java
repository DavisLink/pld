package com.legvit.pld.stallum.service;

import java.text.DecimalFormat;
import java.util.List;

import com.legvit.pld.stallum.comun.Comun;
import com.legvit.pld.stallum.vo.Name;
import com.legvit.pld.stallum.vo.Word;

public class NameCompare {
	
	private static final double EMPTY = -1d;
	//private String log;
	//private String subLog;
	private DecimalFormat dFormat = new DecimalFormat("000.00");
	private int mode;
        
	// Constructor de la clase
	public double nameCompare(String nameA, String nameB, int mode) {
		Name name1;
		Name name2;
		Name aux;
		double percentage = 0d;
		//log = "";
                this.mode = mode;
		
		// Se valida el caso en el que alguna palabra esté vacia
		if(nameA.equals("") || nameB.equals("")){
			//addToLog("No se puede realizar la comparación si alguna de las cadenas está vacia");
			return 0d;
		}
		
		// Se comparan las cadenas para el caso del 100%
		if(nameA.equals(nameB)) {
			//addToLog("Entró en el caso del 100%");
			return 100d;
		}
		
		name1 = new Name(nameA);
		name2 = new Name(nameB);
		
		// Se coloca la cadena mas larga en name1
		if(name1.getLength() < name2.getLength()){
			aux = new Name(name1.getName());
			aux.setArticles(name1.hasArticles());
			name1 = new Name(name2.getName());
			name1.setArticles(name2.hasArticles());
			name2 = new Name(aux.getName());
			name2.setArticles(aux.hasArticles());
		}
		
		//addToLog("Se compararon los nombres \"" + name1.getName() + "\" y \"" + name2.getName() + "\"");
		//addToLog("Se toma como mandatorio el nombre " + name1.getName());
		//addToLog("");
		
		percentage = compare(name1, name2);
		return percentage;
	}
	
	public double compare(Name name1, Name name2) {
		double percentage = 0d;
		double[][] matrix;
		double max = 0d;
		double[] score;
		double weighting = 1.00d;
		boolean matched = false;
		int row = 0;
		int column = 0;
		int outOfPosition = 0;
		int matchPositionA;
		int matchPositionB;
		String aux = "";
		WordCompare comparator = new WordCompare();
		List<Word> tokens1;
		List<Word> tokens2;
		Word token;
		Word tokenAux;
		Match matchAux;
		//subLog = "";
		
		tokens1 = name1.getTokens();
		tokens2 = name2.getTokens();

		// Se realiza el proceso para el llenado de la matriz	
		matrix = initMatrix(tokens1.size(), tokens2.size());
		score = new double[tokens1.size()];

		for(Word token1 : tokens1){
			row = 0;
			aux = " | ";
			max = 0d;
			for(Word token2 : tokens2){
				// Si ya se encontró al 100, se compara con el que sigue
				if(token2.isFound100() || token1.isFound100()){
					row++;
					aux += format(-1d);
					continue;
				}
				else {
					tokenAux = comparator.wordCompare(token1, token2, mode);
					percentage = tokenAux.getRealPercentage();
					// Se encuentra un 100% total, y en su posición adecuada
					if(percentage == 100d){
						if(row == column){
							token1.setFound100(true);
							token2.setFound100(true);
						}
						// Se encontró un 100%, pero fuera de su posición
						else {
							outOfPosition++;
						}
					}
					token1.setMaxPercentage(percentage, token2.getPosition());
					token1.setScore(tokenAux.getScore());
					
					// Agregar el posible match a la lista
					if(percentage < 0)
						percentage = 0d;
					matchAux = new Match(Integer.toString(token2.getPosition()),
										Double.toString(percentage),
										token2.getWord());
					token1.addMatch(matchAux);
				}
				
				//addToSubLog(comparator.getLog());
				aux += format(percentage);
				if(percentage > max){
					max = percentage;
				}
				matrix[column][row] = percentage;
				row++;
			}
			score[column] = max;
			aux += "  >>> " + format(max);
			//addToLog(aux);
			column++;
			
			token1.sortMatchList();
		}
		
		// Se revisan las parejas de cada token para no repetir pareja
		while(!matched){
			matched = true;
			// Se itera cada token
			for(int j=0; j<tokens1.size(); j++){
				token = tokens1.get(j);
				// Si ya está al 100 no se compara
				if(token.isFound100()){
					continue;
				}
				// Se compara con los demás tokens
				matchPositionA = token.getMatchPosition();
				for(int i=0; i<tokens1.size(); i++){
					tokenAux = tokens1.get(i);
					matchPositionB = tokenAux.getMatchPosition();
					// Para que no se compare consigo mismo
					if(token.getPosition() == tokenAux.getPosition()){
						continue;
					}
					// Si su máximo porcentaje es 0, no se necesita seguir comparando
					if(token.getMaxPercentage() == 0){
						continue;
					}
					// Se encontró que los tokens comparten match
					if(matchPositionA > 0 && matchPositionA == matchPositionB){
						matched = false;
						//System.out.println("Los tokens " + token.getWord() + "(" + token.getPosition() + ")" + " y " + tokenAux.getWord() + "(" + tokenAux.getPosition() + ")" + " tienen la misma matchPosition: " + matchPositionA);
						// Se cambia el match de tokenAux
						if(token.getMaxPercentage() >= tokenAux.getMaxPercentage()){
							tokenAux.decreaseMatchPonter();
						}
						// Se cambia el match de token
						else{
							token.decreaseMatchPonter();
						}
						
					}
				}
				
			}
		}
                
                // MASM - 10/12/2015
                // Se genera el cálculo del porcentaje en modo permisiveMatch
                if(Comun.isPermisiveMatch()){
                    double perc;
                    double total = 0d; // Total de puntos
                    double weight; // Porcentaje a restar del total
                    int tokens = 0; // Número de tokens que cumplen con el factor
                    for(int j=0; j<tokens1.size(); j++){
                        perc = tokens1.get(j).getMaxPercentage();
                        if(perc >= Comun.getFactor()){
                            total += perc;
                            tokens++;
                        }
                    }
                    perc = total / tokens;
                    // por cada token faltante se baja el peso total
                    weight = tokens1.size() - tokens;
                    weight *= 3;
                    // Se valida si se cumple el mínimo de tokens
                    if(tokens >= Comun.getMinimumTokens() && perc >= 70){
                       /*System.out.println(">>> Con " + tokens + " se obtiene una suma de " + total);
                       System.out.println(">>> El promedio queda en: " + perc);
                       System.out.println(">>> El peso queda en: " + weight);*/
                       return perc - weight;
                    }
                }
		
		// Se crea el arreglo con los nuevos porcentajes
		for(int j=0; j<tokens1.size(); j++){
			score[j] = tokens1.get(j).getMaxPercentage();
		}
		
		// Se calcula el porcentaje
		percentage = 0d;
		for(double d : score){
			percentage += d;
		}
		
		//System.out.println("Al final, el porcentaje queda en: " + percentage);
		
		// Prueba:
		//addToLog("\nAnálisis final: \n");
		
		/*//addToLog("\n\tSe calcula " + percentage + " / " + column);
		percentage = percentage / column;*/
		// Se calcula la ponderacion
		if(name1.hasArticles() || name2.hasArticles()){
			weighting -= 0.05d;
			//addToLog("* Se encontraron artículos, se baja la ponderación: " + weighting);
		}
		if(outOfPosition > 0){
			weighting -= 0.05d;
			//addToLog("* Se encontraron " + outOfPosition + " coincidencias fuera de su posición, se baja la ponderación: " + weighting);
		}
		
		percentage = getPercentage(name1);
		//addToLog("\n\t* Se obtiene un porcentaje de: " + percentage + "%");
		//addToLog("\t* Se pondera a " + weighting);
		percentage = percentage * weighting;
		
		// Se redondea a 2 decimales
		percentage =  Math.round(percentage * 100.0) / 100.0;
		//addToLog("\n\tRESULTADO = " + percentage + "%");
		
		return percentage;
	}
	
	// Agrega lineas de texto al log
	/*public void addToLog(String line){
		if(log == null){
			log = "";
		}
		log = log + line + "\n";
	}*/
	
	// Agrega lineas de texto al subLog
	/*public void addToSubLog(String line){
		subLog = subLog + line + "\n";
	}*/
	
	// formatea un resultado para imprimirlo
	public String format(double percentage){
		if(percentage < 0){
			return "---.-- | ";
		}
		return dFormat.format(percentage) + " | ";
	}
	
	// Devuelve un double[][] de n x m con todas sus celdas en EMPTY
	public double[][] initMatrix(int n, int m) {
		double[][] matrix = new double[n][m];
		for(int i = 0; i < n; i++){
			for(int j = 0; j < m; j++){
				matrix[i][j] = EMPTY;
			}
		}
		return matrix;
	}
	
	// Calcula el porcentaje basado en la longitud de cada palabra
	public double getPercentage(Name name){
		double percentage = 0d;
		double partial = 0d;
		//addToLog("* El nombre completo tiene " + name.getLength() + " letras (quitando los espacios en blanco)");
		//addToLog("* El nombre se compone de las siguientes palabras:");
		for(Word token : name.getTokens()){
			//addToLog("\n\t" + token.getWord() + ": ");
			//addToLog("\t\tPosición: " + token.getPosition());
			//addToLog("\t\tPorcentaje: " + token.getMaxPercentage() + "%");
			//addToLog("\t\tPuntaje: " + token.getScore());
			partial = (double)token.getLength() / (double)name.getLength();
			//addToLog("\t\tProporción respecto al nombre: " + partial);
			partial = partial * token.getMaxPercentage();
			//addToLog("\t\tPorcentaje parcial: " + partial + "%");
			percentage += partial;
		}
		return percentage;
	}

	/*public String getLog(){
		return log;
	*/
	
	/*public String getSubLog(){
		if(subLog == null){
			subLog = "";
		}
		return subLog;
	}*/

}
