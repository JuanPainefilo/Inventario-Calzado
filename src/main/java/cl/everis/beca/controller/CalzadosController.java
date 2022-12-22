package cl.everis.beca.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import cl.everis.beca.entity.Calzados;
import cl.everis.beca.service.CalzadosServices;

@RestController
@RequestMapping("/InventarioCalzados")
public class CalzadosController {

	@Autowired
	private CalzadosServices calzadosServices;

	/**
	 * @param Recibe todos los elementos asociados al registro de un calzado en la
	 *               BD
	 * @return el objeto con los elementos ingresados
	 */
	@PostMapping(value = "/agregarCalzado", produces = "application/json")
	public ResponseEntity<Calzados> agregar(@RequestParam String nombreCalzado, Integer stockTienda, Double precio,
			String nombreModelo) {
		if (null == nombreCalzado || stockTienda <= 0 || precio <= 0 || null == nombreModelo) {
			return new ResponseEntity<Calzados>(HttpStatus.BAD_REQUEST);
		}
		return calzadosServices.agregarCalzado(nombreModelo, stockTienda, precio, nombreModelo);
	}

	/**
	 * @param recibe el numero de id
	 * @return el calzado que coincida con la id proporcionada
	 */
	@GetMapping(value = "/buscarPorId", produces = "application/json")
	public ResponseEntity<Calzados> buscarPorId(@RequestParam Long id) {
		if (id <= 0) {
			return new ResponseEntity<Calzados>(HttpStatus.BAD_REQUEST);
		}
		return calzadosServices.buscarPorId(id);
	}

	/**
	 * @param recibe el nombre del tipo de calzado
	 * @return todos los calzados que coincidan con el calzado entregado
	 */
	@GetMapping(value = "/buscarPorModelo", produces = "application/json")
	public ResponseEntity<List<Calzados>> buscarCalzadoPorModelo(@RequestParam(defaultValue = "") String nombreModelo) {
		if (nombreModelo.equalsIgnoreCase("")) {
			return new ResponseEntity<List<Calzados>>(HttpStatus.BAD_REQUEST);
		}
		return calzadosServices.buscarCalzadoPorModelo(nombreModelo);
	}

	/**
	 * @param recibe los elementos asociados a un calzado
	 * @return el objeto modificado dentro del sistema y como quedaria
	 */
	@PostMapping(value = "/editarRegistros", produces = "application/json")
	public ResponseEntity<Calzados> editarInformacion(@RequestBody Calzados calzado) {
		if (null == calzado.getId() || calzado.getId() <= 0) {
			return new ResponseEntity<Calzados>(HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<Calzados>(calzadosServices.editarDatos(calzado), HttpStatus.OK);
	}

	/**
	 * @param recibe el id del objeto en cuestion a eliminar
	 * @return la validacion de este proceso
	 */
	@DeleteMapping(value = "/eliminarRegistro", produces = "application/json")
	public ResponseEntity<Calzados> eliminarRegistro(@RequestParam Long id) {
		if (null == id || id <= 0) {
			return new ResponseEntity<Calzados>(HttpStatus.BAD_REQUEST);
		}
		return calzadosServices.eliminarRegistro(id);
	}
}
