package br.com.teste.elotech.api;

import br.com.teste.elotech.dto.ContatoDto;
import br.com.teste.elotech.dto.ContatoDto;
import br.com.teste.elotech.model.Pessoa;
import br.com.teste.elotech.repository.ContatoRepository;
import br.com.teste.elotech.service.ContatoService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/contatos")
public class ContatoApi {

    @Autowired
    private ContatoService contatoService;

    @GetMapping()
    public List<ContatoDto> getContato() {
        return contatoService.getAllContatos();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ContatoDto> getContato(@PathVariable @NotNull Long id) {
        ContatoDto contatoDto = contatoService.getContato(id);

        return ResponseEntity.ok(contatoDto);
    }

    @PostMapping()
    public ResponseEntity<ContatoDto> criaContato(@RequestBody @Valid ContatoDto contatoDto, UriComponentsBuilder uriBuilder) {
        ContatoDto contatoCriado = contatoService.criaContato(contatoDto);

        URI endereco = uriBuilder.path("/contatos/{id}").buildAndExpand(contatoCriado.getId()).toUri();

        return ResponseEntity.created(endereco).body(contatoCriado);

    }

    @PutMapping("/{id}")
    public ResponseEntity<ContatoDto> atualizaContato(@PathVariable Long id, @RequestBody @Valid ContatoDto contatoDto) {
        ContatoDto contatoAtualizado = contatoService.atualizaContato(contatoDto);

        return ResponseEntity.ok(contatoAtualizado);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ContatoDto> removerContato(@PathVariable @NotNull Long id) {
        contatoService.deleteContato(id);
        return ResponseEntity.noContent().build();
    }

}
