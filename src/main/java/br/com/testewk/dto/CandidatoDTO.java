package br.com.testewk.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CandidatoDTO {

    private Long id;
    private String nome;
    private String cpf;
    private String rg;
    private String sexo;
    private String email;
    private String cep;
    private String endereco;
    private Integer numero;
    private String bairro;
    private String cidade;
    private String estado;
    private String celular;
    private Double altura;
    private Integer peso;
    private Double imc;
    private Integer idade;
    private Boolean isDoador;

    @JsonProperty(value="data_nasc")
    private String dtNascimento;

    @JsonProperty(value="mae")
    private String nomeMae;

    @JsonProperty(value="pai")
    private String nomePai;

    @JsonProperty(value="telefone_fixo")
    private String telefoneFixo;

    @JsonProperty(value="tipo_sanguineo")
    private String tipoSanguineo;
    
}
