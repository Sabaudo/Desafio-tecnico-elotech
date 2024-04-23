package br.com.teste.elotech.model;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertFalse;

@SpringBootTest
@AutoConfigureMockMvc
public class PessoaValidationTest {

    private ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
    private Validator validator = validatorFactory.getValidator();

    @Test
    void naoDevePossuirNomeVazio() {
        Pessoa pessoa = new Pessoa();
        pessoa.setNome("");
        Set<ConstraintViolation<Pessoa>> violations = validator.validate(pessoa);
        assertFalse(violations.isEmpty());
    }

    @Test
    void naoDevePossuirTamanhoDeNomeInvalido() {
        Pessoa pessoa = new Pessoa();
        pessoa.setNome("oi");
        Set<ConstraintViolation<Pessoa>> violations = validator.validate(pessoa);
        assertFalse(violations.isEmpty());

        pessoa.setNome("Nome grandeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeee");
        violations = validator.validate(pessoa);
        assertFalse(violations.isEmpty());
    }

    @Test
    void naoDevePossuirCpfVazio() {
        Pessoa pessoa = new Pessoa();
        pessoa.setCpf("");
        Set<ConstraintViolation<Pessoa>> violations = validator.validate(pessoa);
        assertFalse(violations.isEmpty());
    }

    @Test
    void naoDevePossuirCpfInvalido() {
        Pessoa pessoa = new Pessoa();
        pessoa.setCpf("12345678900");
        Set<ConstraintViolation<Pessoa>> violations = validator.validate(pessoa);
        assertFalse(violations.isEmpty());
    }

    @Test
    void naoDevePossuirDataDeNascimentoNula() {
        Pessoa pessoa = new Pessoa();
        pessoa.setDataNascimento(null);
        Set<ConstraintViolation<Pessoa>> violations = validator.validate(pessoa);
        assertFalse(violations.isEmpty());
    }

    @Test
    void naoDevePossuirDataDeNascimentoFutura() {
        Pessoa pessoa = new Pessoa();
        pessoa.setDataNascimento(LocalDate.of(2045, 4, 22));
        Set<ConstraintViolation<Pessoa>> violations = validator.validate(pessoa);
        assertFalse(violations.isEmpty());
    }

}
