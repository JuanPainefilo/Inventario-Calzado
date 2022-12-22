package cl.everis.beca.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Clase de utilidades
 * @author jpainefi
 */

public class Util {

	/**
     * Recibe un rut sin puntos ni guion y lo valida
     * @param rut El rut a validar, como string
     * @return True si el rut es valido. False en otro caso
     */
	public static Boolean validarRut(String rut) {
		if (null==rut) {
			return Boolean.FALSE;
		}
		if (rut.length()<2 || rut.length()>9)
			return Boolean.FALSE;
		rut = rut.toLowerCase();
		String rutSinDv=rut.substring(0, rut.length()-1);
		String comparador = "0123456789k";
		if (!comparador.contains(rut.substring(rut.length()-1)))
			return Boolean.FALSE;

		for (Character valor: rutSinDv.toCharArray()) {
			if (!"0123456789".contains(""+valor))
				return Boolean.FALSE;
		}
		int aux = 2;
		int acumulado = 0;
		StringBuilder rutInverso = new StringBuilder();
		rutInverso.append(rutSinDv);
		rutInverso.reverse();
		for (Character valor: (""+rutInverso).toCharArray()) {
			if (aux==8) {
				aux=2;
			}
			acumulado += Integer.parseInt(""+valor)*aux;
			aux++;
			
		}
		int dv = 11 - acumulado%11;
		if (dv==10 && ("k").equals(rut.substring(rut.length()-1)))
			return Boolean.TRUE;
		if ((""+dv).equals(rut.substring(rut.length()-1))) {
			return Boolean.TRUE;
		}
		return Boolean.FALSE;
	}
	
	/**
     * Convierte una fecha en formato dd/MM/yyyy a objeto Date
     * @param fecha como String
     * @return la fecha convertida. Si no se puede convertir, retorna null
     */
	public static Date fechaStringaDate(String fecha) {
		Date fechaConvertida;
		try {
			fechaConvertida = new SimpleDateFormat("dd/MM/yyyy").parse(fecha);  
		} catch (ParseException e) {
			return  null;
		}
		
		return fechaConvertida;
	} 
	
}
