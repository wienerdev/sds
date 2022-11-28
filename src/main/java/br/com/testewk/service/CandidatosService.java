package br.com.testewk.service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.testewk.dto.CandidatoDTO;
import br.com.testewk.dto.ResponseDTO;
import br.com.testewk.model.Candidato;
import br.com.testewk.repository.CandidatoRepository;

@Service
public class CandidatosService {

    @Autowired
    CandidatoRepository repository;

    @Autowired
    private ModelMapper modelMapper;

    public ResponseDTO recuperarInformacoes(List<CandidatoDTO> dto) {
        ResponseDTO response = new ResponseDTO();
        popularEntidadesNoBanco(dto);
        response.setListaPessoasPorEstado(calcularNumeroDePessoasPorEstado(dto));
        response.setListaImcPorIdade(recuperarIMCMedioPorIdade(dto));
        return response;
    }

    private void popularEntidadesNoBanco(List<CandidatoDTO> dto) {
        dto.forEach(candidato -> {
            repository.save(modelMapper.map(candidato, Candidato.class));
        });
    }

    private Map<String, Integer> calcularNumeroDePessoasPorEstado(List<CandidatoDTO> dto) {
        Map<String, Integer> listaPessoas = new HashMap<>();
        ArrayList<String> listaEstados = new ArrayList<>();

        dto.stream().forEach(cand -> {
            listaEstados.add(cand.getEstado());
        });

        dto.forEach(candidato -> {
            listaPessoas.put(
                    candidato.getEstado(),
                    Collections.frequency(listaEstados, candidato.getEstado()));
        });

        return listaPessoas;
    }

    private Map<String, BigDecimal> recuperarIMCMedioPorIdade(List<CandidatoDTO> dto) {
        Map<String, BigDecimal> listaMediaImc = new LinkedHashMap<>();
        DateTimeFormatter dateformatter = DateTimeFormatter.ofPattern("d/MM/yyyy");

        dto.forEach(cand -> {
            cand.setImc(cand.getPeso() / Math.pow(cand.getAltura(), 2));
            cand.setIdade(Period.between(LocalDate.parse(cand.getDtNascimento(), dateformatter), LocalDate.now()).getYears());            
        });

        popularImcMedioPorIdade(dto, listaMediaImc);

        return listaMediaImc;
    }

    private void popularImcMedioPorIdade(List<CandidatoDTO> dto, Map<String, BigDecimal> listaMediaImc) {
        List<CandidatoDTO> faixaMenorQueDezAnos = dto.stream()
                .filter(cand -> cand.getIdade() <= 10).collect(Collectors.toList());

        List<CandidatoDTO> faixaEntreOnzeEVinteAnos = dto.stream()
                .filter(cand -> cand.getIdade() >= 11 && cand.getIdade() <= 20).collect(Collectors.toList());

        List<CandidatoDTO> faixaEntreVinteETrintaAnos = dto.stream()
                .filter(cand -> cand.getIdade() >= 21 && cand.getIdade() <= 30).collect(Collectors.toList());

        List<CandidatoDTO> faixaEntreTrintaEQuarentaAnos = dto.stream()
                .filter(cand -> cand.getIdade() >= 31 && cand.getIdade() <= 40).collect(Collectors.toList());

        List<CandidatoDTO> faixaEntreQuarentaECinquentaAnos = dto.stream()
                .filter(cand -> cand.getIdade() >= 41 && cand.getIdade() <= 50).collect(Collectors.toList());

        List<CandidatoDTO> faixaEntreCinquentaESessentaAnos = dto.stream()
                .filter(cand -> cand.getIdade() >= 51 && cand.getIdade() <= 60).collect(Collectors.toList());

        List<CandidatoDTO> faixaEntreSessentaESetentaAnos = dto.stream()
                .filter(cand -> cand.getIdade() >= 61 && cand.getIdade() <= 70).collect(Collectors.toList());

        List<CandidatoDTO> faixaEntreSetentaEOitentaAnos = dto.stream()
                .filter(cand -> cand.getIdade() >= 61 && cand.getIdade() <= 70).collect(Collectors.toList());

        List<CandidatoDTO> faixaEntreOitentaENoventaAnos = dto.stream()
                .filter(cand -> cand.getIdade() >= 81 && cand.getIdade() <= 90).collect(Collectors.toList());

        listaMediaImc.put("0 a 10",
                BigDecimal.valueOf(calcularMediaPorFaixa(faixaMenorQueDezAnos)).setScale(2, RoundingMode.HALF_UP));

        listaMediaImc.put("11 a 20",
                BigDecimal.valueOf(calcularMediaPorFaixa(faixaEntreOnzeEVinteAnos)).setScale(2, RoundingMode.HALF_UP));

        listaMediaImc.put("21 a 30",
                BigDecimal.valueOf(calcularMediaPorFaixa(faixaEntreVinteETrintaAnos)).setScale(2, RoundingMode.HALF_UP));

        listaMediaImc.put("31 a 40",
                BigDecimal.valueOf(calcularMediaPorFaixa(faixaEntreTrintaEQuarentaAnos)).setScale(2, RoundingMode.HALF_UP));

        listaMediaImc.put("41 a 50",
                BigDecimal.valueOf(calcularMediaPorFaixa(faixaEntreQuarentaECinquentaAnos)).setScale(2, RoundingMode.HALF_UP));

        listaMediaImc.put("51 a 60",
                BigDecimal.valueOf(calcularMediaPorFaixa(faixaEntreCinquentaESessentaAnos)).setScale(2, RoundingMode.HALF_UP));

        listaMediaImc.put("61 a 70",
                BigDecimal.valueOf(calcularMediaPorFaixa(faixaEntreSessentaESetentaAnos)).setScale(2, RoundingMode.HALF_UP));

        listaMediaImc.put("71 a 80",
                BigDecimal.valueOf(calcularMediaPorFaixa(faixaEntreSetentaEOitentaAnos)).setScale(2, RoundingMode.HALF_UP));

        listaMediaImc.put("81 a 90",
                BigDecimal.valueOf(calcularMediaPorFaixa(faixaEntreOitentaENoventaAnos)).setScale(2, RoundingMode.HALF_UP));
    }

    private Double calcularMediaPorFaixa(List<CandidatoDTO> faixa) {
        return faixa.stream()
                .mapToDouble(CandidatoDTO::getImc)
                .average()
                .orElse(0.00);

    }
    
    
}
