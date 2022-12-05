package br.com.testewk.model;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import br.com.testewk.dto.CandidatoDTO;

public interface IDadosCandidatos {

    public Map<String, Integer> calcularNumeroDePessoasPorEstado(List<CandidatoDTO> dto);
    public Map<String, BigDecimal> recuperarIMCMedioPorIdade(List<CandidatoDTO> dto);
    public Map<String, Double> recuperarPecentualObesidadePorSexo(List<CandidatoDTO> dto);
    public Map<String, BigDecimal> recuperarMediaIdadePorTipoSanguineo(List<CandidatoDTO> dto);
    public Map<String, Integer> recuperarQtdDoadoresPorTipoSanguineo(List<CandidatoDTO> dto);
    
}
