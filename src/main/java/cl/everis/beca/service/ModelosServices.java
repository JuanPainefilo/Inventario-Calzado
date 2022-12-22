package cl.everis.beca.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cl.everis.beca.entity.Modelos;
import cl.everis.beca.repository.ModelosRepository;

@Service
public class ModelosServices {

	@Autowired
	private ModelosRepository modelosRepository;

/**
 * 
 * @param nombreModelo nombre modelo a buscar
 * @return modelo encontrado
 */
	public Modelos findByNombreModelo(String nombreModelo) {
		return modelosRepository.findByNombreModelo(nombreModelo);
	}
	
	/**
 * se buscan modelos por nombres que contengan el nombre ingresado
 * @param nombreModelo nombre modelo a buscar
 * @return lista de modelos similares
 */

	    public List<Modelos> findByNombreModeloSimilar(String nombreModelo) {
	        List<Modelos> similares = new ArrayList<>();
	        List<Modelos> allModels = modelosRepository.findAll();
	        for (Modelos modelo : allModels) {

		           if(modelo.getNombreModelo().contains(nombreModelo)) {
	                similares.add(modelo);
	            }
	        }
	        return similares;
	    }

}