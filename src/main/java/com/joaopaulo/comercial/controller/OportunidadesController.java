package com.joaopaulo.comercial.controller;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.joaopaulo.comercial.model.Oportunidade;
import com.joaopaulo.comercial.repository.OportunidadesRepository;

@CrossOrigin //origem cruzada
@RestController
@RequestMapping("/oportunidades")
public class OportunidadesController {
	
	@Autowired
	private OportunidadesRepository oportunidades;
	
	@GetMapping
	public List<Oportunidade> listar() {		
		return oportunidades.findAll();
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Oportunidade> buscar(@PathVariable Long id){
		Optional<Oportunidade> oportunidade = oportunidades.findById(id);
		if (oportunidade.equals(null)) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok(oportunidade.get());
	}
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED) //retorna status 401, sucesso
	public Oportunidade adicionar (@Valid @RequestBody Oportunidade oportunidade) {
		Optional<Oportunidade> oportunidadeExistente = oportunidades
				.findByDescricaoAndNomeProspecto(oportunidade.getDescricao(), oportunidade.getNomeProspecto());
		if (oportunidadeExistente.isPresent()) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
					"Já existe cadastro com essa mesma descrição");
		}		
		return oportunidades.save(oportunidade);
	}

}
