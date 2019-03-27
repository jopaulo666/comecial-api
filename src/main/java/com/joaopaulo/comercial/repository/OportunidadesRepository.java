package com.joaopaulo.comercial.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.joaopaulo.comercial.model.Oportunidade;

//faz consulta no banco de dados, delete, update, etc...

public interface OportunidadesRepository extends JpaRepository<Oportunidade, Long> {

	Optional<Oportunidade> findByDescricaoAndNomeProspecto(String descricao, String nomeProspecto);
	
}
