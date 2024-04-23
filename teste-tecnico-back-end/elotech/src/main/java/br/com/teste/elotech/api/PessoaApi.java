package br.com.teste.elotech.api;

import br.com.teste.elotech.dto.PessoaDto;
import br.com.teste.elotech.service.PessoaService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@CrossOrigin("http://localhost:4200")
@RestController
@RequestMapping("/pessoa")
public class PessoaApi {

    @Autowired
    PessoaService pessoaService;

    @GetMapping()
    public Page<PessoaDto> getPessoas(@RequestParam(value = "nome", required = false) String nome,
                                      @PageableDefault(size = 10) Pageable pageable) {
        return pessoaService.getAllPessoas(nome, pageable);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PessoaDto> getPessoa(@PathVariable @NotNull Long id) {
        PessoaDto pessoaDto = pessoaService.getPessoa(id);

        return ResponseEntity.ok(pessoaDto);
    }

    @PostMapping()
    public ResponseEntity<PessoaDto> criaPessoa(@RequestBody @Valid PessoaDto pessoaDto, UriComponentsBuilder uriBuilder) {
        PessoaDto pessoaCriada = pessoaService.criaPessoa(pessoaDto);

        URI endereco = uriBuilder.path("/pessoa/{id}").buildAndExpand(pessoaCriada.getId()).toUri();

        return ResponseEntity.created(endereco).body(pessoaCriada);

    }

    @PutMapping("/{id}")
    public ResponseEntity<PessoaDto> atualizaPessoa(@PathVariable Long id, @RequestBody @Valid PessoaDto pessoaDto) {
        PessoaDto pessoaAtualizada = pessoaService.atualizaPessoa(id, pessoaDto);

        return ResponseEntity.ok(pessoaAtualizada);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<PessoaDto> removerPessoa(@PathVariable @NotNull Long id) {
        pessoaService.deletePessoa(id);
        return ResponseEntity.noContent().build();
    }


}
