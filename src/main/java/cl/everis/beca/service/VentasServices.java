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
import cl.everis.beca.repository.DetalleRepository;
import cl.everis.beca.repository.VentasRepository;
import cl.everis.beca.utils.Util;

/**
 * Clase Service para las ventas
 * 
 * @author jpainefi
 *
 */
@Service
public class VentasServices {

	@Value("${uri.inventario.edicion}")
	private String uriInventario;

	@Value("${uri.inventario.buscar}")
	private String uriBusqueda;

	@Autowired
	private VentasRepository ventasRepository;

	@Autowired
	private DetalleRepository detalleRepository;

	/**
	 * Devuelve todas las ventas en sistema
	 * 
	 * @return Las ventas encontradas en sistema
	 */
	public ResponseEntity<List<Ventas>> verVentas() {
		return new ResponseEntity<List<Ventas>>(ventasRepository.findAll(), HttpStatus.OK);
	}

	/**
	 * Anula la venta indicada, segun su ID
	 * 
	 * @param id La id de la venta a anular
	 * @return La venta anulada. En caso de no existir una venta asociada, retorna
	 *         NOT_FOUND
	 */
	public ResponseEntity<Ventas> anularVenta(Long id) {
		Optional<Ventas> venta = ventasRepository.findById(id);
		if (null == venta.get()) {
			return new ResponseEntity<Ventas>(HttpStatus.NOT_FOUND);
		}
		venta.get().setAnulada(Boolean.TRUE);
		venta.get().setFechaAnulacion(Date.from(Instant.now()));
		ventasRepository.save(venta.get());
		return new ResponseEntity<Ventas>(venta.get(), HttpStatus.OK);
	}

	/**
	 * Busca ventas por rut de cliente y/o en un periodo determinado.
	 * 
	 * @param rut          Rut del cliente a consultar
	 * @param fechaInicio  Fecha de inicio del intervalo. Incluyente
	 * @param fechaTermino Fecha de termino del intervalo. Excluyente
	 * @return Las ventas asociadas encontradas. Retorna NOT_FOUND si no encuentra
	 *         ventas asociadas
	 */
	public ResponseEntity<List<Ventas>> buscar(String rut, String fechaInicio, String fechaTermino) {
		Date inicio = Util.fechaStringaDate(fechaInicio);
		Date termino = Util.fechaStringaDate(fechaTermino);
		List<Ventas> ventas;
		if (null == inicio || null == termino) {
			ventas = ventasRepository.findByRutCliente(rut);
		} else if (rut.isBlank()) {
			ventas = ventasRepository.findByFechaBetween(inicio, termino);
		} else {
			ventas = ventasRepository.findByRutClienteAndFechaBetween(rut, inicio, termino);
		}
		if (ventas.isEmpty()) {
			return new ResponseEntity<List<Ventas>>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<List<Ventas>>(ventas, HttpStatus.OK);
	}

	/**
	 * Busca una venta por id.
	 * 
	 * @param id ID de la venta a consultar
	 * @return La venta correspondiente. Retorna NOT_FOUND si no la encuentra
	 */
	public ResponseEntity<Ventas> buscarPorId(Long id) {
		Optional<Ventas> venta = ventasRepository.findById(id);
		if (null == venta.get()) {
			return new ResponseEntity<Ventas>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<Ventas>(venta.get(), HttpStatus.OK);
	}

	/**
	 * Realiza una venta, en caso de que lo ingresado cumpla las validaciones.
	 * Realiza la rebaja usando el servicio de Inventario de acuerdo a la cantidad
	 * de cada articulo a comprar articulo[i] es la id del articulo a comprar,
	 * mientras que cantidad[i] es la cantidad de dicho articulo a comprar
	 * Las rutas de busqueda y edicion de stock se deben ingresar en el properties
	 * 
	 * @param rutCliente Cliente que realiza la compra
	 * @param articulos  Lista de ids de articulos a comprar. Cada articulo debe
	 *                   tener una cantidad asociada
	 * @param cantidad   Lista de cantidad que se comprara de cada articulo. Cada
	 *                   cantidad debe tener un articulo asociado
	 * @return la venta realizada, si es que fue realizada. Si no se realizo,
	 *         retorna BAD_REQUEST
	 */
	public ResponseEntity<Ventas> hacerVenta(String rutCliente, Long[] articulos, Integer[] cantidad) {
		if (!Util.validarRut(rutCliente)) {
			return new ResponseEntity<Ventas>(HttpStatus.BAD_REQUEST);
		}
		for (int i = 0; i < articulos.length; i++) {
			RestTemplate restTemplate = new RestTemplate();
			Calzados calzado = restTemplate.getForObject(uriBusqueda + "?id=" + articulos[i], Calzados.class);
			if (calzado.getStockTienda() < cantidad[i] || cantidad[i] < 1) {
				return new ResponseEntity<Ventas>(HttpStatus.BAD_REQUEST);
			}
		}
		Ventas venta = new Ventas();
		Ventas ventaRetorno = new Ventas();
		venta.setDetalleVentas(new ArrayList<DetalleVentas>());
		for (int i = 0; i < articulos.length; i++) {
			RestTemplate restTemplate = new RestTemplate();
			Calzados calzado = restTemplate.getForObject(uriBusqueda + "?id=" + articulos[i], Calzados.class);
			calzado.setStockTienda(calzado.getStockTienda() - cantidad[i]);
			if (!restTemplate.postForEntity(uriInventario, calzado, calzado.getClass()).hasBody())
				return new ResponseEntity<Ventas>(HttpStatus.BAD_REQUEST);
			DetalleVentas detalle = new DetalleVentas();
			detalle.setCantidad(cantidad[i]);
			detalle.setId_producto(articulos[i]);
			detalle.setValor(calzado.getPrecio());
			venta.setRutCliente(rutCliente);
			ventaRetorno = ventasRepository.save(venta);
			detalle.setVenta(ventaRetorno);
			venta.getDetalleVentas().add(detalleRepository.save(detalle));
		}
		return new ResponseEntity<Ventas>(venta, HttpStatus.OK);
	}
}
