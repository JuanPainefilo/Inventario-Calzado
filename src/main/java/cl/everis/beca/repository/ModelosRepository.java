package cl.everis.beca.repository;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;

import cl.everis.beca.entity.Modelos;

public interface ModelosRepository extends JpaRepository<Modelos, Long> {

	@Transactional
	Modelos findByNombreModelo(String nombreModelo);

}
