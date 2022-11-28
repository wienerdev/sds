package br.com.testewk.dto;

import java.math.BigDecimal;
import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ResponseDTO {

    private Map<String, Integer> listaPessoasPorEstado;
    private Map<String, BigDecimal> listaImcPorIdade;
    
}
