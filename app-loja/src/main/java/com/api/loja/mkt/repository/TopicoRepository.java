package com.api.loja.mkt.repository;

import com.api.loja.mkt.model.Topico;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface TopicoRepository extends JpaRepository<Topico, Long> {

	Page<Topico> findByCursoNome(String nomeCurso, Pageable paginacao);

	@EntityGraph(attributePaths = {"respostas"})
	Optional<Topico> findById(Long id);
}
