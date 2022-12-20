package cl.everis.beca.controller;

import java.util.ArrayList;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import cl.everis.beca.entity.Calzados;
import cl.everis.beca.service.CalzadosServices;

@RestController
@RequestMapping("/calzadosInv")
public class CalzadosController {

	@Autowired
	private CalzadosServices calzadosServices;

	@PostMapping(value = "/agregarCalzado", produces = "application/json")
	public ResponseEntity<Calzados> agregar(@RequestParam String nombreModelo, String tipoCalzado, Integer stockTienda,
			Double precio) {
		return calzadosServices.agregarCalzado(nombreModelo, tipoCalzado, stockTienda, precio);
	}

	@GetMapping(value = "/buscarPorId", produces = "application/json")
	public Optional<Calzados> buscarPorId(@RequestParam Long id) {
		return calzadosServices.findById(id);
	}

	@GetMapping(value = "/buscarPorModelo", produces = "application/json")
	public ArrayList<Calzados> buscarCalzadoPorModelo(@RequestParam String nombreModelo) {
		return calzadosServices.buscarCalzadoPorModelo(nombreModelo);
	}

	@PostMapping(value = "/editarRegistros", produces = "application/json")
	public ResponseEntity<Calzados> editarDatos(@RequestParam Long id, String nombreModelo, String tipoCalzado,
			Integer stockTienda, Double precio) {
		return calzadosServices.editarDatos(id, nombreModelo, tipoCalzado, stockTienda, precio);
	}
}
