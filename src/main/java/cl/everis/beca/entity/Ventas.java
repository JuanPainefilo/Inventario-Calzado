package cl.everis.beca.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.CreationTimestamp;

import lombok.Data;

@Data
@Entity
@Table(name = "Ventas")
public class Ventas {

	protected static final String VENTAS_SEQ = "ventas_seq";
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = VENTAS_SEQ)
	@SequenceGenerator(sequenceName = VENTAS_SEQ, allocationSize = 1, name = "VENTAS_SEQ")
	private Long id;
	@CreationTimestamp
	@Temporal(TemporalType.TIMESTAMP)
	private Date fecha;
	private String rut;
	private Boolean anulada = Boolean.FALSE;
	@Temporal(TemporalType.TIMESTAMP)
	private Date fechaAnulacion = null;
	@OneToMany(mappedBy = "venta")
	private List<DetalleVentas> detalleVentas = new ArrayList<DetalleVentas>();
}
