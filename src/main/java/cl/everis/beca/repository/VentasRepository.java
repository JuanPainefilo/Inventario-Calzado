package cl.everis.beca.repository;

import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;

import cl.everis.beca.entity.Ventas;

public interface VentasRepository extends JpaRepository<Ventas, Long> {
	
	@Transactional
	List<Ventas> findAllByRutAndFechaBetween(String rut, Date publicationTimeStart, Date publicationTimeEnd);
	
}
