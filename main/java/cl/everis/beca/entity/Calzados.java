package cl.everis.beca.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name = "Calzados")
public class Calzados {

	protected static final String ALUMNOS_SEQ = "calzados_seq";
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = ALUMNOS_SEQ)
	@SequenceGenerator(sequenceName = ALUMNOS_SEQ, allocationSize = 1, name = "ALUMNOS_SEQ")
	private Long id;
	private String nombreModelo;
	private String tipoCalzado;
	private String rut;
	private Integer stockTienda;
	private Double precio;

}
