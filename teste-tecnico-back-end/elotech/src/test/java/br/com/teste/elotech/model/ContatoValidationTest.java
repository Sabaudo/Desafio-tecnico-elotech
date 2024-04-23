package br.com.teste.elotech.model;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertFalse;

@SpringBootTest
@AutoConfigureMockMvc
public class ContatoValidationTest {

    private ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
    private Validator validator = validatorFactory.getValidator();

    @Test
    void naoDevePossuirNomeVazio() {
        Contato contato = new Contato();
        contato.setNome("");
        Set<ConstraintViolation<Contato>> violations = validator.validate(contato);
        assertFalse(violations.isEmpty());
    }

    @Test
    void naoDevePossuirTelefoneVazio() {
        Contato contato = new Contato();
        contato.setTelefone("");
        Set<ConstraintViolation<Contato>> violations = validator.validate(contato);
        assertFalse(violations.isEmpty());
    }

    @Test
    void naoDevePossuirEmailVazio() {
        Contato contato = new Contato();
        contato.setEmail("");
        Set<ConstraintViolation<Contato>> violations = validator.validate(contato);
        assertFalse(violations.isEmpty());
    }

    @Test
    void naoDevePossuirEmailInvalido() {
        Contato contato = new Contato();
        contato.setEmail("testeemailinvalido@");
        Set<ConstraintViolation<Contato>> violations = validator.validate(contato);
        assertFalse(violations.isEmpty());
    }

}
