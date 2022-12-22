package cl.everis.beca.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;

@Data
@Entity
@Table(name = "Modelos")
public class Modelos {

	protected static final String MODELOS_SEQ = "modelos_seq";
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = MODELOS_SEQ)
	@SequenceGenerator(sequenceName = MODELOS_SEQ, allocationSize = 1, name = "modelos_seq")
	private Long id;
	private String nombreModelo;

	@JsonIgnore
	@OneToMany(mappedBy = "modelo")
	private List<Calzados> calzados = new ArrayList<Calzados>();

}