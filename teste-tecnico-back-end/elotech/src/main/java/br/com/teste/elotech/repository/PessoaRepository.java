package br.com.teste.elotech.repository;

import br.com.teste.elotech.model.Pessoa;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PessoaRepository extends JpaRepository<Pessoa, Long> {

    Page<Pessoa> findAllByNomeContaining(String nome, Pageable pageable);

}
