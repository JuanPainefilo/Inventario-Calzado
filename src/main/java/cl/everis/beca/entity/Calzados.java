package cl.everis.beca.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import lombok.Data;

/**
 * Clase de entidad para calzado
 * 
 * @author jmelladh
 *
 */

@Data
@Entity
@Table(name = "Calzados")
public class Calzados {

	protected static final String CALZADOS_SEQ = "calzados_seq";
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = CALZADOS_SEQ)
	@SequenceGenerator(sequenceName = CALZADOS_SEQ, allocationSize = 1, name = "calzados_seq")
	private Long id;
	private String nombreCalzado;
	private Integer stockTienda;
	private Double precio;

	@ManyToOne
	@JoinColumn(name = "modelo_id")
	private Modelos modelo;

}
