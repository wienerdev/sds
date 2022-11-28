package br.com.testewk.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.testewk.model.Candidato;

@Repository
public interface CandidatoRepository extends JpaRepository<Candidato, Long> {
    
}
