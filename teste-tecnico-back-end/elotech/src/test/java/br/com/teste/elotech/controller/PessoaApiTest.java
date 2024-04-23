package br.com.teste.elotech.controller;

import br.com.teste.elotech.api.PessoaApi;
import br.com.teste.elotech.dto.ContatoDto;
import br.com.teste.elotech.dto.PessoaDto;
import br.com.teste.elotech.service.PessoaService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
@AutoConfigureMockMvc
public class PessoaApiTest {

    @Mock
    private PessoaService pessoaService;

    @InjectMocks
    private PessoaApi pessoaApi;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }
    @Test
    void deveRetornarStatusOkAoCriarPessoa() {
        PessoaDto pessoaDto = new PessoaDto(1L,
                "Luana",
                "29134882006",
                LocalDate.of(2003, 2, 5),
                List.of(new ContatoDto(1L, "Contato 1", "43991931999", "contato1@gmail.com")));

        when(pessoaService.criaPessoa(any(PessoaDto.class))).thenReturn(pessoaDto);
        UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromPath("");
        ResponseEntity<PessoaDto> responseEntity = pessoaApi.criaPessoa(pessoaDto, uriBuilder);

        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());

        URI expectedUri = URI.create("/pessoa/1");
        assertEquals(expectedUri, responseEntity.getHeaders().getLocation());

        assertEquals(pessoaDto, responseEntity.getBody());
        verify(pessoaService, times(1)).criaPessoa(eq(pessoaDto));
    }


    @Test
    void deveRetornarPessoasComFiltro() {
        PessoaDto pessoaDto = new PessoaDto(1L,
                "Luana",
                "29134882006",
                LocalDate.of(2003, 2, 5),
                List.of(new ContatoDto(1L, "Contato 1", "43991931999", "contato1@gmail.com")));

        Pageable pageable = Pageable.ofSize(10);

        Page<PessoaDto> expectedPage = new PageImpl<>(List.of(new PessoaDto()));
        when(pessoaService.getAllPessoas(eq(pessoaDto.getNome()), eq(pageable))).thenReturn(expectedPage);

        Page<PessoaDto> resultPage = pessoaApi.getPessoas(pessoaDto.getNome(), pageable);

        verify(pessoaService, times(1)).getAllPessoas(eq(pessoaDto.getNome()), eq(pageable));

        assertNotNull(resultPage);
        assertEquals(expectedPage, resultPage);
    }

    @Test
    void deveRetornarStatusOkAoRetornarUmaPessoa() {
        PessoaDto pessoaDto = new PessoaDto(1L,
                "Luana",
                "29134882006",
                LocalDate.of(2003, 2, 5),
                List.of(new ContatoDto(1L, "Contato 1", "43991931999", "contato1@gmail.com")));

        when(pessoaService.getPessoa(eq(pessoaDto.getId()))).thenReturn(pessoaDto);

        ResponseEntity<PessoaDto> responseEntity = pessoaApi.getPessoa(pessoaDto.getId());

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(pessoaDto, responseEntity.getBody());
        verify(pessoaService, times(1)).getPessoa(eq(pessoaDto.getId()));
    }

    @Test
    void deveRetornarStatusOkAoAtualizarPessoa() {
        PessoaDto pessoaDto = new PessoaDto(1L,
                "Luana",
                "29134882006",
                LocalDate.of(2003, 2, 5),
                List.of(new ContatoDto(1L, "Contato 1", "43991931999", "contato1@gmail.com")));

        PessoaDto pessoaAtualizada = new PessoaDto();
        pessoaAtualizada.setId(pessoaDto.getId());
        pessoaAtualizada.setNome("Carlos");
        when(pessoaService.atualizaPessoa(eq(pessoaDto.getId()), eq(pessoaDto))).thenReturn(pessoaAtualizada);

        ResponseEntity<PessoaDto> responseEntity = pessoaApi.atualizaPessoa(pessoaDto.getId(), pessoaDto);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());

        PessoaDto returnedPessoa = responseEntity.getBody();
        assertNotNull(returnedPessoa);
        assertEquals(pessoaAtualizada.getId(), returnedPessoa.getId());
        assertEquals(pessoaAtualizada.getNome(), returnedPessoa.getNome());

        verify(pessoaService, times(1)).atualizaPessoa(eq(pessoaDto.getId()), eq(pessoaDto));
    }

    @Test
    void deveRetornarNoContentAoExcluirPessoa() {
        PessoaDto pessoaDto = new PessoaDto(1L,
                "Luana",
                "29134882006",
                LocalDate.of(2003, 2, 5),
                List.of(new ContatoDto(1L, "Contato 1", "43991931999", "contato1@gmail.com")));


        ResponseEntity<PessoaDto> responseEntity = pessoaApi.removerPessoa(pessoaDto.getId());

        assertEquals(HttpStatus.NO_CONTENT, responseEntity.getStatusCode());
        verify(pessoaService, times(1)).deletePessoa(eq(pessoaDto.getId()));
    }

}
