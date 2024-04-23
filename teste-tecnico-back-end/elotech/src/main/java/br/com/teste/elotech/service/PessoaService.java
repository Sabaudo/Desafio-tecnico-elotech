package br.com.teste.elotech.service;

import br.com.teste.elotech.dto.PessoaDto;
import br.com.teste.elotech.model.Contato;
import br.com.teste.elotech.model.Pessoa;
import br.com.teste.elotech.repository.PessoaRepository;
import jakarta.persistence.EntityNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PessoaService {

    @Autowired
    private PessoaRepository pessoaRepository;

    @Autowired
    private ModelMapper modelMapper;



    public Page<PessoaDto> getAllPessoas(String nome, Pageable page) {
        return pessoaRepository.findAllByNomeContaining(nome, page)
                .map(p -> modelMapper.map(p, PessoaDto.class));
    }

    public PessoaDto getPessoa(Long id) {
        Pessoa pessoa = pessoaRepository.findById(id)
                .orElseThrow(EntityNotFoundException::new);
        return modelMapper.map(pessoa, PessoaDto.class);
    }

    public PessoaDto criaPessoa(PessoaDto pessoaDto) {
        this.validaContatos(pessoaDto);
        Pessoa pessoa = modelMapper.map(pessoaDto, Pessoa.class);
        pessoa.getContatos().forEach(c -> c.setPessoa(pessoa));
        pessoa.getContatos().forEach(c -> c.setId(null));
        pessoaRepository.save(pessoa);
        return modelMapper.map(pessoa, PessoaDto.class);
    }

    public PessoaDto atualizaPessoa(Long id, PessoaDto pessoaDto) {
        Optional<Pessoa> pessoa = pessoaRepository.findById(id);
        if(pessoa.isPresent()) {
            Pessoa pessoaAtualizada = modelMapper.map(pessoaDto, Pessoa.class);
            for (Contato contato : pessoaAtualizada.getContatos()) {
                contato.setPessoa(pessoaAtualizada);
            }
            pessoaAtualizada = pessoaRepository.save(pessoaAtualizada);
            return modelMapper.map(pessoaAtualizada, PessoaDto.class);
        } else {
            throw new EntityNotFoundException("Pessoa não encontrada");
        }
    }

    public void deletePessoa(Long id) {
        pessoaRepository.deleteById(id);
    }

    public void validaContatos(PessoaDto pessoaDto) {
        if(pessoaDto.getContatos() == null || pessoaDto.getContatos().isEmpty()) {
            throw new IllegalArgumentException("A pessoa deve possuir ao menos um contato");
        }
    }

    public void setModelMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

}
