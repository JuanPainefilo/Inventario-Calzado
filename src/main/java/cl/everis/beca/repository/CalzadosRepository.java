package cl.everis.beca.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;

import cl.everis.beca.entity.Calzados;

/**
 * Clase de repositorio para el calzado
 * @author jmelladh
 */

public interface CalzadosRepository extends JpaRepository<Calzados, Long> {

	/**
	 * @param id
	 * @return
	 */
	@Transactional
	List<Calzados> findByModeloId(Long id);

}
