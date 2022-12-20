package cl.everis.beca.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import cl.everis.beca.entity.Calzados;

public interface CalzadosRepository extends JpaRepository<Calzados, Long> {

	@Transactional
	List<Calzados> findByNombreModelo(String NombreModelo);
	
}

