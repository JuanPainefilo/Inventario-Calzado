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
	private double precio;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNombreModelo() {
		return nombreModelo;
	}

	public void setNombreModelo(String nombreModelo) {
		this.nombreModelo = nombreModelo;
	}

	public String getTipoCalzado() {
		return tipoCalzado;
	}

	public void setTipoCalzado(String tipoCalzado) {
		this.tipoCalzado = tipoCalzado;
	}

	public String getRut() {
		return rut;
	}

	public void setRut(String rut) {
		this.rut = rut;
	}

	public Integer getStockTienda() {
		return stockTienda;
	}

	public void setStockTienda(Integer stockTienda) {
		this.stockTienda = stockTienda;
	}

	public double getPrecio() {
		return precio;
	}

	public void setPrecio(double precio) {
		this.precio = precio;
	}

}
