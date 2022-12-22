package cl.everis.beca.service;

import java.util.List;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import cl.everis.beca.entity.Calzados;
import cl.everis.beca.entity.Modelos;
import cl.everis.beca.repository.CalzadosRepository;
import cl.everis.beca.repository.ModelosRepository;

/**
 * Clase Service para el calzado
 * 
 * @author jmelladh
 *
 */

@Service
public class CalzadosServices {

	@Autowired
	private CalzadosRepository calzadosRepository;

	@Autowired
	private ModelosRepository modelosRepository;

	/**
	 * @param recibe los valores asociados un registro de calzado
	 * @return el objeto ya registrado en sistema
	 */
	public ResponseEntity<Calzados> agregarCalzado(String nombreCalzado, Integer stockTienda, Double precio,
			String nombreModelo) {
		Calzados calzado = new Calzados();
		Modelos modelo = new Modelos();
		if (nombreCalzado == null || stockTienda == null || precio == null) {
			return new ResponseEntity<Calzados>(HttpStatus.NOT_ACCEPTABLE);
		} else {
			if (precio == 0 || stockTienda == 0) {
				return new ResponseEntity<Calzados>(HttpStatus.NOT_ACCEPTABLE);
			} else {
				calzado.setNombreCalzado(nombreCalzado);
				calzado.setPrecio(precio);
				calzado.setStockTienda(stockTienda);
				modelo = modelosRepository.findByNombreModelo(nombreModelo);
				if (modelosRepository.findByNombreModelo(nombreModelo) == null) {
					Modelos auxModelo = new Modelos();
					auxModelo.setNombreModelo(nombreModelo);
					modelosRepository.save(auxModelo);
					calzado.setModelo(auxModelo);
					return new ResponseEntity<Calzados>(calzadosRepository.save(calzado), HttpStatus.OK);
				} else {
					calzado.setModelo(modelo);
					return new ResponseEntity<Calzados>(calzadosRepository.save(calzado), HttpStatus.OK);
				}

			}
		}
	}

	/**
	 * @param recibe el nombre exacto del modelo a buscar
	 * @return las coincidencias en la BD
	 */
	public ResponseEntity<List<Calzados>> buscarCalzadoPorModelo(String nombreModelo) {
		Modelos modelosEncontrados = new Modelos();

		if (modelosRepository.findByNombreModelo(nombreModelo) == null || nombreModelo == null) {
			return new ResponseEntity<List<Calzados>>(HttpStatus.NOT_FOUND);
		}
		modelosEncontrados = modelosRepository.findByNombreModelo(nombreModelo);
		Modelos auxModelo = modelosEncontrados;
		return new ResponseEntity<List<Calzados>>(auxModelo.getCalzados(), HttpStatus.OK);
	}

	/**
	 * @param recibe la id del objeto a buscar
	 * @return las coincidencias en la BD
	 */
	public ResponseEntity<Calzados> buscarPorId(Long id) {
		if (calzadosRepository.findById(id).isEmpty()) {
			return new ResponseEntity<Calzados>(HttpStatus.NOT_FOUND);
		} else {
			return new ResponseEntity<Calzados>(calzadosRepository.findById(id).get(), HttpStatus.OK);
		}
	}

	/**
	 * @param recibe los parametros asociados al registro de un calzado previamente,
	 *               valida la existencia de este por medio de la Id
	 * @return como quedaria el objeto ya modificado en nuestra BD
	 */
	public Calzados editarDatos(Calzados calzadoIngresado) {
		Calzados calzado = calzadosRepository.findById(calzadoIngresado.getId()).get();

		if (calzadosRepository.findById(calzadoIngresado.getId()).isEmpty()) {
			return calzado;
		} else {
			if (Objects.nonNull(calzadoIngresado.getId()) && calzadoIngresado.getId() <= 0) {
				calzado.setId(calzadoIngresado.getId());
			}
			if (Objects.nonNull(calzadoIngresado.getNombreCalzado())
					&& !"".equalsIgnoreCase(calzadoIngresado.getNombreCalzado())) {
				calzado.setNombreCalzado(calzadoIngresado.getNombreCalzado());
			}
			if (Objects.nonNull(calzadoIngresado.getPrecio()) && calzadoIngresado.getPrecio() <= 0) {
				calzado.setPrecio(calzadoIngresado.getPrecio());
			}

			if (Objects.nonNull(calzadoIngresado.getStockTienda())) {
				calzado.setStockTienda(calzadoIngresado.getStockTienda());
			}

			return calzadosRepository.save(calzado);
		}

	}

	/**
	 * @param recibe la id del objeto a eliminar
	 * @return la validacion de su eliminacion en sistema
	 */
	public ResponseEntity<Calzados> eliminarRegistro(Long id) {
		if (calzadosRepository.findById(id).isEmpty()) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		} else {
			calzadosRepository.deleteById(id);
			return new ResponseEntity<>(HttpStatus.OK);
		}
	}

}
