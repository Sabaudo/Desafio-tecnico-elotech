package br.com.teste.elotech.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.br.CPF;

import java.time.LocalDate;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "pessoa")
public class Pessoa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "O nome da pessoa é obrigatório")
    @Size(min = 3, max = 70, message = "O nome da pessoa deve conter entre 3 e 70 caracteres")
    private String nome;

    @Column(unique = true)
    @NotBlank(message = "o CPF da pessoa é obrigatório")
    @CPF(message = "O CPF informado não é válido")
    private String cpf;

    @NotNull(message = "A data de nascimento é obrigatória")
    @Past(message = "A data de nascimento não é válida")
    private LocalDate dataNascimento;

    @NotNull
    @NotEmpty
    @JsonIgnore
    @OneToMany(mappedBy = "pessoa", cascade = CascadeType.ALL)
    private List<Contato> contatos;

    public Pessoa(Long id, String nome) {
        this.id = id;
        this.nome = nome;
    }
}
