package cl.everis.beca.service;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import cl.everis.beca.entity.Calzados;
import cl.everis.beca.entity.DetalleVentas;
import cl.everis.beca.entity.Ventas;
import cl.everis.beca.repository.VentasRepository;
import cl.everis.beca.utils.Util;
@Service
public class VentasServices {

	@Value("${uri.inventario.edicion}")
	private String uriInventario;
	
	@Value("${uri.inventario.buscar}")
	private String uriBusqueda;
	
	@Autowired
	private VentasRepository ventasRepository;
	
	public ResponseEntity<List<Ventas>> verVentas() {
		return new ResponseEntity<List<Ventas>>(ventasRepository.findAll(), HttpStatus.OK);
	}
	
	public ResponseEntity<Ventas> anularVenta(Long id) {
		Optional<Ventas> venta = ventasRepository.findById(id);
		if (null == venta.get()) {
			return new ResponseEntity<Ventas>(HttpStatus.NOT_FOUND);
		}
		venta.get().setAnulada(Boolean.TRUE);
		venta.get().setFechaAnulacion(Date.from(Instant.now()));
		return new ResponseEntity<Ventas>(venta.get(), HttpStatus.OK);
	}

	public ResponseEntity<List<Ventas>> buscar(String rut, String fechaInicio, String fechaTermino) {
		Date inicio = Util.fechaStringaDate(fechaInicio);
		Date termino = Util.fechaStringaDate(fechaTermino);
		List<Ventas> ventas = ventasRepository.findAllByRutAndFechaBetween(rut, inicio, termino);
		if (ventas.isEmpty()) {
			return new ResponseEntity<List<Ventas>>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<List<Ventas>>(ventas, HttpStatus.OK);
	}
	
	public ResponseEntity<Ventas> buscarPorId(Long id) {
		Optional<Ventas> venta = ventasRepository.findById(id);
		if (null == venta.get()) {
			return new ResponseEntity<Ventas>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<Ventas>(venta.get(),HttpStatus.OK);
	}

	public ResponseEntity<Ventas> hacerVenta(String rutCliente, Long[] articulos, Integer[] cantidad) {
		if (!Util.validarRut(rutCliente)) {
			return null; //TODO: PERSONALIZAR ERROR
		}
		if (articulos.length != cantidad.length) {
			return null; //TODO: PERSONALIZAR ERROR
		}
		for (int i=0; i<articulos.length; i++) {
			RestTemplate restTemplate = new RestTemplate();
			Calzados calzado = restTemplate.getForObject(uriBusqueda+"?id="+articulos[i], Calzados.class);
			if (calzado.getStockTienda()<cantidad[i]) {
				return null; //TODO: PERSONALIZAR ERROR DE QUE NO HAY STOCK SUFICIENTE
			}
		}
		Ventas venta = new Ventas();
		venta.setDetalleVentas(new ArrayList<DetalleVentas>());
		for (int i=0; i<articulos.length; i++) {
			RestTemplate restTemplate = new RestTemplate();
			Calzados calzado = restTemplate.getForObject(uriBusqueda+"?id="+articulos[i], Calzados.class);
			calzado.setStockTienda(calzado.getStockTienda()-cantidad[i]);
			restTemplate.postForLocation(uriInventario, calzado);
			DetalleVentas detalle = new DetalleVentas();
			detalle.setCantidad(cantidad[i]);
			detalle.setId_producto(articulos[i]);
			detalle.setValor(calzado.getPrecio());
			venta.getDetalleVentas().add(detalle);
		}
		return new ResponseEntity<Ventas>(ventasRepository.save(venta),HttpStatus.OK);
	}
}
