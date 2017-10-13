package com.legvit.pld.stallum.utilerias;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author Jesús Mateos
 */
public class Utilerias {

    /* Función para validar valores nulos
     * @param obj El dato (object) a validar.
     * @return true si es nulo caso contrario regresa false.
     */
    public boolean esNulo(Object obj) throws Exception {
        try {
            return obj == null;
        } catch (Exception e) {
            throw e;
        }
    }

    /**
     * Función para la validación de nulos
     *
     * @param valor El valor a verificar.
     * @param reemplazo El valor de reemplazo en caso de que el valor a verificar sea nulo.
     * @return String La cadena de reemplazo
     */
    public String reemplazaNulo(String valor, String reemplazo) throws Exception {
        try {
            if (valor == null) {
                valor = reemplazo;
            }
        } catch (Exception e) {
            throw e;
        }
        return valor;
    }
    
    /**
     * Función para obtener la fecha actual
     *
     * @param formatoSalida El formato de salida para la fecha.
     * @return String La fecha de salida de acuerdo al formato de salida
     */
    public String getFechaActual(String formatoSalida) throws Exception {
        SimpleDateFormat formato;
        String fechaFormateada = "";
        Calendar calendar;
        try {
            calendar = Calendar.getInstance();
            formato = new SimpleDateFormat(formatoSalida);
            fechaFormateada = formato.format(calendar.getTime());
        } catch (Exception e) {
            throw e;
        }
        return fechaFormateada;
    }
	
    /**
     * Función para obtener la diferencia en dias entre dos fechas
     *
     * @param fecha1 La fecha 1.
     * @param formatoEntradaFecha1 El formato en que se encuentra la fecha 1.
     * @param fecha2 La fecha 2.
     * @param formatoEntradaFecha2 El formato en que se encuentra la fecha 2.
     * @return long La diferencia en días.
     * @throws Exception
     */
    public long getDiferenciaDias(String fecha1, String formatoEntradaFecha1, String fecha2, String formatoEntradaFecha2) throws Exception {
        Calendar calFecha1;
        Calendar calFecha2;
        long difDias = 0;
        try {
            calFecha1 = getFecha(fecha1, formatoEntradaFecha1);
            calFecha2 = getFecha(fecha2, formatoEntradaFecha2);

            long diferencia = calFecha1.getTimeInMillis() - calFecha2.getTimeInMillis();
            difDias = diferencia / (24 * 60 * 60 * 1000);
        } catch (Exception e) {
            throw e;
        }
        return difDias;
    }
    
    /**
     * Función para convertir una cadena de fecha a una fecha de tipo Calendar
     *
     * @param fecha La fecha en texto.
     * @param formatoEntrada El formato en que se encuentra la cadena de fecha.
     * @return Calendar La fecha en el objeto Calendar
     */
    public Calendar getFecha(String fecha, String formatoEntrada) throws Exception {
        SimpleDateFormat formatter = new SimpleDateFormat(formatoEntrada);
        Calendar calendar;
        try {
            calendar = Calendar.getInstance();
            calendar.setTime(formatter.parse(fecha));
        } catch (Exception e) {
            throw e;
        }
        return calendar;
    }

    /**
     * Función para validar expresiones regulares
     *
     * @param valor El dato a validar.
     * @return true si la expresion regular coincide.
     */
    public boolean validaRegExp(String regExp, String valor) throws Exception {
        try {
            Pattern pattern = Pattern.compile(regExp);
            Matcher matcher = pattern.matcher(valor);
            return matcher.find();
        } catch (Exception e) {
            throw e;
        }
    }

}
