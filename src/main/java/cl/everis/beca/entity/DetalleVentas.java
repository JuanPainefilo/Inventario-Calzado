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

@Data
@Entity
@Table(name = "DetalleVentas")
public class DetalleVentas {

	protected static final String DETALLE_VENTAS_SEQ = "detalle_ventas_seq";
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = DETALLE_VENTAS_SEQ)
	@SequenceGenerator(sequenceName = DETALLE_VENTAS_SEQ, allocationSize = 1, name = "DETALLE_VENTAS_SEQ")
	private Long id;
	private Double valor;
	private Integer cantidad;
	private Long id_producto;
	private Double impuesto = 0.19;
	
	@ManyToOne
    @JoinColumn(name = "ventas_id")
    private Ventas venta;
}
