package br.com.teste.elotech.service;

import br.com.teste.elotech.dto.ContatoDto;
import br.com.teste.elotech.model.Contato;
import br.com.teste.elotech.model.Pessoa;
import br.com.teste.elotech.repository.ContatoRepository;
import br.com.teste.elotech.repository.PessoaRepository;
import jakarta.persistence.EntityNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ContatoService {

    @Autowired
    private ContatoRepository contatoRepository;

    @Autowired
    private PessoaRepository pessoaRepository;

    @Autowired
    private ModelMapper modelMapper;

    public List<ContatoDto> getAllContatos() {
        return contatoRepository.findAll().stream()
                .map(p -> modelMapper.map(p, ContatoDto.class))
                .collect(Collectors.toList());
    }

    public ContatoDto getContato(Long id) {
        Contato contato = contatoRepository.findById(id)
                .orElseThrow(EntityNotFoundException::new);
        return modelMapper.map(contato, ContatoDto.class);
    }

    public ContatoDto criaContato(ContatoDto contatoDto) {
        Contato contato = modelMapper.map(contatoDto, Contato.class);
        contatoRepository.save(contato);
        pessoaRepository.save(contato.getPessoa());
        return modelMapper.map(contato, ContatoDto.class);
    }

    public ContatoDto atualizaContato(ContatoDto contatoDto) {
        Contato contato = modelMapper.map(contatoDto, Contato.class);
        contato = contatoRepository.save(contato);
        return modelMapper.map(contato, ContatoDto.class);
    }

    public void deleteContato(Long id) {
        contatoRepository.deleteById(id);
    }

}
