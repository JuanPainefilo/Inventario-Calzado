package cl.everis.beca.repository;

import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;

import cl.everis.beca.entity.Ventas;

/**
 * Clase repositorio para Ventas
 * 
 * @author jpainefi
 *
 */
public interface VentasRepository extends JpaRepository<Ventas, Long> {

	/**
	 * Devuelve la lista de ventas realizadas al cliente individualizado por rut
	 * 
	 * @param rutCliente Rut del cliente a buscar
	 * @return Lista de ventas registradas a ese rut
	 */
	@Transactional
	List<Ventas> findByRutCliente(String rutCliente);

	/**
	 * Devuelve una lista de ventas realizadas en cierto periodo de tiempo
	 * 
	 * @param publicationTimeStart Fecha inicial para la busqueda
	 * @param publicationTimeEnd   Fecha final para la busqueda
	 * @return Las ventas realizadas en el periodo indicado
	 */
	@Transactional
	List<Ventas> findByFechaBetween(Date publicationTimeStart, Date publicationTimeEnd);

	/**
	 * Devuelve la lista de ventas realizadas a cierto cliente, en cierto periodo de
	 * tiempo
	 * 
	 * @param rutCliente           Rut del cliente a buscar
	 * @param publicationTimeStart Fecha inicial para la busqueda
	 * @param publicationTimeEnd   Fecha final para la busqueda
	 * @return Las ventas realizadas al cliente indicado, en el periodo indicado
	 */
	@Transactional
	List<Ventas> findByRutClienteAndFechaBetween(String rutCliente, Date publicationTimeStart, Date publicationTimeEnd);
}
