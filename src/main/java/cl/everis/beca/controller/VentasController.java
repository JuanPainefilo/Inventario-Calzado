package cl.everis.beca.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import cl.everis.beca.entity.Ventas;
import cl.everis.beca.service.VentasServices;

/**
 * Controlador que expone las funciones asociadas a las ventas
 * 
 * @author jpainefi
 *
 */
@RestController
@RequestMapping("/ventas")
public class VentasController {

	@Autowired
	private VentasServices ventasServices;

	/**
	 * Lista todas las ventas realizadas
	 * 
	 * @return lista de ventas encontradas en sistema
	 */
	@GetMapping(value = "/listar", produces = "application/json")
	public ResponseEntity<List<Ventas>> listar() {
		return ventasServices.verVentas();
	}

	/**
	 * Busca entre fechas, por rut de cliente o por ambos. Si se incluye una fecha,
	 * requiere la otra. Las fechas deben estar en formato dd/mm/YYYY
	 * 
	 * @param rut          El rut de cliente a buscar
	 * @param fechaInicio  Fecha inicial del intervalo que se desea buscar
	 * @param fechaTermino Fecha final del intervalo que se desea buscar
	 * @return una lista con las ventas correspondientes al rut y/o al intervalo
	 *         indicado
	 */
	@GetMapping(value = "/buscar", produces = "application/json")
	public ResponseEntity<List<Ventas>> buscarVarios(@RequestParam(defaultValue = "") String rut,
			@RequestParam(defaultValue = "") String fechaInicio, @RequestParam(defaultValue = "") String fechaTermino) {
		return ventasServices.buscar(rut, fechaInicio, fechaTermino);
	}

	/**
	 * Busca una venta por id
	 * 
	 * @param id ID de la venta a consultar
	 * @return La venta encontrada, en caso de que la encuentre
	 */
	@GetMapping(value = "/buscarPorId", produces = "application/json")
	public ResponseEntity<Ventas> buscar(@RequestParam Long id) {
		return ventasServices.buscarPorId(id);
	}

	/**
	 * Agrega una nueva venta en el sistema. Ver VentasServices.hacerVenta
	 * 
	 * @param rut       El rut del cliente
	 * @param articulos Lista de ids de artículos a comprar
	 * @param cantidad  Lista de cantidades de articulos a comprar
	 * @return Los datos de la venta, en caso de que ésta logre realizarse
	 */
	@PostMapping(value = "/hacerVenta", produces = "application/json")
	public ResponseEntity<Ventas> vender(@RequestParam String rut, @RequestParam Long[] articulos,
			@RequestParam Integer[] cantidad) {
		return ventasServices.hacerVenta(rut, articulos, cantidad);
	}

	/**
	 * Marca una venta como anulada.
	 * 
	 * @param id La ID de la venta a anular
	 * @return Los datos de la venta anulada
	 */
	@PatchMapping(value = "/anularVenta", produces = "application/json")
	public ResponseEntity<Ventas> anularVenta(@RequestParam Long id) {
		return ventasServices.anularVenta(id);
	}
}
