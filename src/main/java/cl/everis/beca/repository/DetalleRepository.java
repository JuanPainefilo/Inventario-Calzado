package cl.everis.beca.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import cl.everis.beca.entity.DetalleVentas;

/**
 * Clase de repositorio para el detalle de las ventas
 * 
 * @author jpainefi
 *
 */
public interface DetalleRepository extends JpaRepository<DetalleVentas, Long> {

}
