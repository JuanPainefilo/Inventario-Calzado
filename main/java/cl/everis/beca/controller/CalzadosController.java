package cl.everis.beca.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cl.everis.beca.service.CalzadosServices;

@RestController
@RequestMapping("/clase1")
public class CalzadosController {
	
	@Autowired
	private CalzadosServices calzadosServices;

}
