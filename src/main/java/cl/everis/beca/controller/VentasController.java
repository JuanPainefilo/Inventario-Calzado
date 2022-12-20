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

@RestController
@RequestMapping("/ventas")
public class VentasController {

	@Autowired
	private VentasServices ventasServices;
	
	@GetMapping(value="/listar",  produces = "application/json")
	public ResponseEntity<List<Ventas>> listar() {
		return ventasServices.verVentas();
	}
	
	@GetMapping(value="/buscar",  produces = "application/json")
	public ResponseEntity<List<Ventas>> buscarPorFecha(@RequestParam(defaultValue = "") String rut, @RequestParam(defaultValue = "") String fechaInicio,@RequestParam(defaultValue = "") String fechaTermino) {
		return ventasServices.buscar(rut, fechaInicio, fechaTermino);
	}
	
	@GetMapping(value="/buscarPorId",  produces = "application/json")
	public ResponseEntity<Ventas> buscar(@RequestParam Long id) {
		return ventasServices.buscarPorId(id);
	}
	
	@PostMapping(value="/hacerVenta",  produces = "application/json")
	public ResponseEntity<Ventas> vender(@RequestParam String rut, @RequestParam Long[] articulos, @RequestParam Integer[] cantidad) {
		return ventasServices.hacerVenta(rut, articulos, cantidad);
	}
	
	@PatchMapping(value="/buscarPorId",  produces = "application/json")
	public ResponseEntity<Ventas> anularVenta(@RequestParam Long id) {
		return ventasServices.anularVenta(id);
	}
}
