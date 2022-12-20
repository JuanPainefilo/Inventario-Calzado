package cl.everis.beca.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Util {

	public static Boolean validarRut(String rut) {
		return Boolean.TRUE;
	}
	
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
