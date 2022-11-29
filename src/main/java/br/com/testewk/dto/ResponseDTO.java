package br.com.testewk.dto;

import java.math.BigDecimal;
import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ResponseDTO {

    private Map<String, Integer> qtdPessoasPorEstado;
    private Map<String, BigDecimal> mediaImcPorIdade;
    private Map<String, Double> percentuaisObesidadePorSexo;
    private Map<String, BigDecimal> mediaIdadePorTipoSanguineo;
    private Map<String, Integer> qtdDoadoresPorTipoSanguineo;
    
}
