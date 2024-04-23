package br.com.teste.elotech.service;

import br.com.teste.elotech.dto.ContatoDto;
import br.com.teste.elotech.dto.PessoaDto;
import br.com.teste.elotech.model.Pessoa;
import br.com.teste.elotech.repository.PessoaRepository;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@SpringBootTest
@AutoConfigureMockMvc
public class PessoaServiceTest {

    @Mock
    private PessoaRepository pessoaRepository;

    @InjectMocks
    private PessoaService pessoaService;

    private ModelMapper modelMapper = new ModelMapper();
    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        pessoaService.setModelMapper(modelMapper);
    }
    @Test
    void deveRetornarTodasAsPessoas() {
        String nome = "Luana";
        Pageable pageable = Pageable.ofSize(10);

        List<Pessoa> pessoas = new ArrayList<>();
        pessoas.add(new Pessoa(1L, "Luana"));
        Page<Pessoa> page = new PageImpl<>(pessoas);
        when(pessoaRepository.findAllByNomeContaining(eq(nome), eq(pageable))).thenReturn(page);

        Page<PessoaDto> resultPage = pessoaService.getAllPessoas(nome, pageable);

        verify(pessoaRepository, times(1)).findAllByNomeContaining(eq(nome), eq(pageable));

        assertNotNull(resultPage);
        assertEquals(page.getTotalElements(), resultPage.getTotalElements());
        assertEquals(page.getTotalPages(), resultPage.getTotalPages());
        assertEquals(page.getNumber(), resultPage.getNumber());
        assertEquals(page.getSize(), resultPage.getSize());

        assertEquals(1, resultPage.getContent().size());
        assertEquals("Luana", resultPage.getContent().get(0).getNome());
    }

    @Test
    void deveRetornarUmaPessoa() {
        Long id = 1L;
        Pessoa pessoa = new Pessoa(id, "Luana");

        when(pessoaRepository.findById(eq(id))).thenReturn(Optional.of(pessoa));
        PessoaDto pessoaDto = pessoaService.getPessoa(id);

        assertEquals(id, pessoaDto.getId());
        assertEquals("Luana", pessoaDto.getNome());
    }

    @Test
    void deveLancarEntityNotFoundException() {
        Long id = 1L;
        when(pessoaRepository.findById(eq(id))).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> {
            pessoaService.getPessoa(id);
        });
    }

    @Test
    void deveCriarUmaPessoa() {
        PessoaDto pessoaDto = new PessoaDto(1L,
                "Luana",
                "29134882006",
                LocalDate.of(2003, 2, 5),
                List.of(new ContatoDto(1L, "Contato 1", "43991931999", "contato1@gmail.com")));

        PessoaDto pessoaCriada = pessoaService.criaPessoa(pessoaDto);
        ContatoDto contatoCriado = pessoaCriada.getContatos().get(0);

        assertNotNull(pessoaCriada);
        assertEquals("Luana", pessoaCriada.getNome());
        assertEquals("29134882006", pessoaCriada.getCpf());
        assertEquals(LocalDate.of(2003, 2, 5), pessoaCriada.getDataNascimento());
        assertEquals("Contato 1", contatoCriado.getNome());
        assertEquals("43991931999", contatoCriado.getTelefone());
        assertEquals("contato1@gmail.com", contatoCriado.getEmail());
        verify(pessoaRepository, times(1)).save(any());
    }

    @Test
    void naoDeveCriarPessoaComListaDeContatosNula() {
        PessoaDto pessoaDto = new PessoaDto(1L,
                "Luana",
                "29134882006",
                LocalDate.of(2003, 2, 5));

        assertThrows(IllegalArgumentException.class, () -> {
            pessoaService.criaPessoa(pessoaDto);
        });

        verify(pessoaRepository, never()).save(any());
    }

    @Test
    void naoDeveCriarPessoaComListaDeContatosVazia() {
        List<ContatoDto> contatosDto = new ArrayList<>();
        PessoaDto pessoaDto = new PessoaDto(1L,
                "Luana",
                "29134882006",
                LocalDate.of(2003, 2, 5), contatosDto);

        assertThrows(IllegalArgumentException.class, () -> {
            pessoaService.criaPessoa(pessoaDto);
        });

        verify(pessoaRepository, never()).save(any());
    }

    @Test
    void deveAtualizarUmaPessoa() {
        Pessoa pessoaExistente = new Pessoa(1L, "Primeiro nome");

        PessoaDto pessoaDto = new PessoaDto(pessoaExistente.getId(),
                "Luana",
                "29134882006",
                LocalDate.of(2003, 2, 5),
                List.of(new ContatoDto(1L, "Contato 1", "43991931999", "contato1@gmail.com")));


        when(pessoaRepository.findById(eq(pessoaDto.getId()))).thenReturn(Optional.of(pessoaExistente));
        when(pessoaRepository.save(any())).thenAnswer(invocation -> invocation.getArgument(0));

        PessoaDto pessoaAtualizadaDto = pessoaService.atualizaPessoa(pessoaDto.getId(), pessoaDto);

        verify(pessoaRepository, times(1)).findById(eq(pessoaDto.getId()));
        verify(pessoaRepository, times(1)).save(any());

        assertNotNull(pessoaAtualizadaDto);
        assertEquals(pessoaDto.getNome(), pessoaAtualizadaDto.getNome());
    }

    @Test
    void deveLancarEntityNotFoundExceptionAoAtualizarIdInvalido() {
        Long id = 1L;
        PessoaDto pessoaDto = new PessoaDto();
        pessoaDto.setNome("Luana");

        when(pessoaRepository.findById(eq(id))).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> {
            pessoaService.atualizaPessoa(id, pessoaDto);
        });

        verify(pessoaRepository, never()).save(any());
    }

    @Test
    void deveDeletarUmaPessoa() {
        PessoaDto pessoaDto = new PessoaDto(1L,
                "Luana",
                "29134882006",
                LocalDate.of(2003, 2, 5),
                List.of(new ContatoDto(1L, "Contato 1", "43991931999", "contato1@gmail.com")));


        pessoaService.deletePessoa(pessoaDto.getId());

        verify(pessoaRepository, times(1)).deleteById(eq(pessoaDto.getId()));
    }

}
