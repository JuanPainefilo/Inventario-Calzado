package cl.everis.beca.service;

import java.util.ArrayList;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import cl.everis.beca.entity.Calzados;
import cl.everis.beca.repository.CalzadosRepository;

@Service
public class CalzadosServices {

	@Autowired
	private CalzadosRepository repoCalzados;

	public ResponseEntity<Calzados> agregarCalzado(String nombreModelo, String tipoCalzado, Integer stockTienda,
			Double precio) {
		Calzados calzado = new Calzados();
		calzado.setNombreModelo(nombreModelo);
		calzado.setTipoCalzado(tipoCalzado);
		calzado.setPrecio(precio);
		calzado.setStockTienda(stockTienda);
		return new ResponseEntity<Calzados>(repoCalzados.save(calzado), HttpStatus.OK);
	}

	public ArrayList<Calzados> buscarCalzadoPorModelo(String nombreModelo) {
		return new ArrayList<Calzados>(repoCalzados.findByNombreModelo(nombreModelo));
	}

	public Optional<Calzados> findById(Long id) {
		return repoCalzados.findById(id);
	}

	public ResponseEntity<Calzados> editarDatos(Long id, String nombreModelo, String tipoCalzado, Integer stockTienda,
			Double precio) {
		try {
			Calzados calzado = new Calzados();
			repoCalzados.deleteById(id);
			calzado.setId(id);
			calzado.setNombreModelo(nombreModelo);
			calzado.setTipoCalzado(tipoCalzado);
			calzado.setPrecio(precio);
			calzado.setStockTienda(stockTienda);
			return new ResponseEntity<Calzados>(repoCalzados.save(calzado), HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<Calzados>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

}
