package cl.everis.beca.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import cl.everis.beca.entity.Modelos;
import cl.everis.beca.service.ModelosServices;

@RestController
@RequestMapping("/InventarioModelos")
public class ModelosController {

	@Autowired
	private ModelosServices modelosServices;

	@PostMapping(value = "/buscarPorNombreModelo", produces = "application/json")
	public ResponseEntity<Modelos> buscarPorNombreModelo(@RequestParam String nombreModelo) {
		return new ResponseEntity<Modelos>(modelosServices.findByNombreModelo(nombreModelo), HttpStatus.OK);
	}
	
	@PostMapping(value = "/buscarPorNombreModeloSimilar", produces = "application/json")
    public ResponseEntity<List<Modelos>> buscarPorNombreModeloSimilar(@RequestParam String nombreModelo) {
        return new ResponseEntity<List<Modelos>>(modelosServices.findByNombreModeloSimilar(nombreModelo), HttpStatus.OK);
    }
}