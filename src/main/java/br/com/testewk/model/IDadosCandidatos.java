package br.com.testewk.model;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import br.com.testewk.dto.CandidatoDTO;

public interface IDadosCandidatos {

    public Map<String, Integer> qtdPessoasPorEstado(List<CandidatoDTO> dto);
    public Map<String, BigDecimal> mediaImcPorIdade(List<CandidatoDTO> dto);
    public Map<String, Double> percentuaisObesidadePorSexo(List<CandidatoDTO> dto);
    public Map<String, BigDecimal> mediaIdadePorTipoSanguineo(List<CandidatoDTO> dto);
    public Map<String, Integer> qtdDoadoresPorTipoSanguineo(List<CandidatoDTO> dto);
    
}
