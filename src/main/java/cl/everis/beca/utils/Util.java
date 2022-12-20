package cl.everis.beca.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Util {

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
		int aux = 7;
		int acumulado = 0;
		for (Character valor: rutSinDv.toCharArray()) {
			if (aux==1) {
				aux=7;
			}
			acumulado = Integer.parseInt(""+valor)*aux;
			aux--;
			
		}
		int dv = acumulado/11 - acumulado%11;
		if (dv==10 && ("k").equals(rut.substring(rut.length()-1)))
			return Boolean.TRUE;
		if ((""+dv).equals(rut.substring(rut.length()-1))) {
			return Boolean.TRUE;
		}
		return Boolean.FALSE;
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
