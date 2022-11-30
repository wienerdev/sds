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
import java.util.TreeMap;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.testewk.dto.CandidatoDTO;
import br.com.testewk.dto.ResponseDTO;
import br.com.testewk.model.Candidato;
import br.com.testewk.repository.CandidatoRepository;

@Service
public class InfoCandidatosService {

    @Autowired
    CandidatoRepository repository;

    @Autowired
    ModelMapper modelMapper;

    public ResponseDTO processarInfoCandidatos(List<CandidatoDTO> dto) {
        ResponseDTO response = new ResponseDTO();
        popularEntidadesNoBanco(dto);
        response.setQtdPessoasPorEstado(calcularNumeroDePessoasPorEstado(dto));
        response.setMediaImcPorIdade(recuperarIMCMedioPorIdade(dto));
        response.setPercentuaisObesidadePorSexo(recuperarPecentualObesidade(dto));
		response.setMediaIdadePorTipoSanguineo(recuperarMediaIdadePorTipoSanguineo(dto));
		response.setQtdDoadoresPorTipoSanguineo(recuperarQtdDoadoresPorTipoSanguineo(dto));
        return response;
    }

	private void popularEntidadesNoBanco(List<CandidatoDTO> dto) {
		dto.forEach(candidato -> {
			repository.save(modelMapper.map(candidato, Candidato.class));
		});
	}

	private Map<String, Integer> calcularNumeroDePessoasPorEstado(List<CandidatoDTO> dto) {
		Map<String, Integer> listaPessoas = new TreeMap<>();
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
		DateTimeFormatter dateformatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

		dto.forEach(cand -> {
			cand.setImc(cand.getPeso() / Math.pow(cand.getAltura(), 2));
			cand.setIdade(Period.between(LocalDate.parse(cand.getDtNascimento(), dateformatter), LocalDate.now()).getYears());
		});

		return calcularImcMedioPorIdade(dto);
	}

	private Map<String, Double> recuperarPecentualObesidade(List<CandidatoDTO> dto) {
		Map<String, Double> percentuaisObesidade = new HashMap<>();

		List<CandidatoDTO> listaHomens = dto.stream()
				.filter(cand -> cand.getSexo().equals("Masculino"))
				.collect(Collectors.toList());

		List<CandidatoDTO> listaMulheres = dto.stream()
				.filter(cand -> cand.getSexo().equals("Feminino"))
				.collect(Collectors.toList());

		List<CandidatoDTO> listaHomemObeso = listaHomens.stream()
				.filter(homem -> homem.getImc() > 30)
				.collect(Collectors.toList());

		List<CandidatoDTO> listaMulherObesa = listaMulheres
				.stream().filter(mulher -> mulher.getImc() > 30)
				.collect(Collectors.toList());

		percentuaisObesidade.put("Homens", listaHomemObeso.size()*listaHomens.size()/100.00);
		percentuaisObesidade.put("Mulheres", listaMulherObesa.size()*listaMulherObesa.size()/100.00);

		return percentuaisObesidade;
	}

	private Map<String, BigDecimal> recuperarMediaIdadePorTipoSanguineo(List<CandidatoDTO> dto) {
		Map<String, BigDecimal> mediaIdadePorTipoSangue = new LinkedHashMap<>();

		List<CandidatoDTO> tipoAMais = recuperarCandidatosPorTipoSanguineo(dto, "A+");

		List<CandidatoDTO> tipoAMenos = recuperarCandidatosPorTipoSanguineo(dto, "A-");

		List<CandidatoDTO> tipoBMais = recuperarCandidatosPorTipoSanguineo(dto, "B+");

		List<CandidatoDTO> tipoBMenos = recuperarCandidatosPorTipoSanguineo(dto, "B-");

		List<CandidatoDTO> tipoABMais = recuperarCandidatosPorTipoSanguineo(dto, "AB+");

		List<CandidatoDTO> tipoABMenos = recuperarCandidatosPorTipoSanguineo(dto, "AB-");

		List<CandidatoDTO> tipoOMais = recuperarCandidatosPorTipoSanguineo(dto, "O+");

		List<CandidatoDTO> tipoOMenos = recuperarCandidatosPorTipoSanguineo(dto, "O-");

		mediaIdadePorTipoSangue.put("A+", BigDecimal.valueOf(
			calcularMediaIdadePorTipoSanguineo(tipoAMais)).setScale(2, RoundingMode.HALF_UP));

		mediaIdadePorTipoSangue.put("A-", BigDecimal.valueOf(
			calcularMediaIdadePorTipoSanguineo(tipoAMenos)).setScale(2, RoundingMode.HALF_UP));

		mediaIdadePorTipoSangue.put("B+", BigDecimal.valueOf(
			calcularMediaIdadePorTipoSanguineo(tipoBMais)).setScale(2, RoundingMode.HALF_UP));

		mediaIdadePorTipoSangue.put("B-", BigDecimal.valueOf(
			calcularMediaIdadePorTipoSanguineo(tipoBMenos)).setScale(2, RoundingMode.HALF_UP));

		mediaIdadePorTipoSangue.put("AB+", BigDecimal.valueOf(
			calcularMediaIdadePorTipoSanguineo(tipoABMais)).setScale(2, RoundingMode.HALF_UP));

		mediaIdadePorTipoSangue.put("AB-", BigDecimal.valueOf(
			calcularMediaIdadePorTipoSanguineo(tipoABMenos)).setScale(2, RoundingMode.HALF_UP));

		mediaIdadePorTipoSangue.put("O+", BigDecimal.valueOf(
			calcularMediaIdadePorTipoSanguineo(tipoOMais)).setScale(2, RoundingMode.HALF_UP));

		mediaIdadePorTipoSangue.put("O-", BigDecimal.valueOf(
			calcularMediaIdadePorTipoSanguineo(tipoOMenos)).setScale(2, RoundingMode.HALF_UP));

		return mediaIdadePorTipoSangue;
	}

	private Map<String, Integer> recuperarQtdDoadoresPorTipoSanguineo(List<CandidatoDTO> dto) {
		Map<String, Integer> qtdPessoasPorTipoSanguineo = new LinkedHashMap<>();

		List<CandidatoDTO> tipoAMais = recuperarCandidatosPorTipoSanguineo(dto, "A+");

		List<CandidatoDTO> tipoAMenos = recuperarCandidatosPorTipoSanguineo(dto, "A-");

		List<CandidatoDTO> tipoBMais = recuperarCandidatosPorTipoSanguineo(dto, "B+");

		List<CandidatoDTO> tipoBMenos = recuperarCandidatosPorTipoSanguineo(dto, "B-");

		List<CandidatoDTO> tipoABMais = recuperarCandidatosPorTipoSanguineo(dto, "AB+");

		List<CandidatoDTO> tipoABMenos = recuperarCandidatosPorTipoSanguineo(dto, "AB-");

		List<CandidatoDTO> tipoOMais = recuperarCandidatosPorTipoSanguineo(dto, "O+");

		List<CandidatoDTO> tipoOMenos = recuperarCandidatosPorTipoSanguineo(dto, "O-");

		qtdPessoasPorTipoSanguineo.put(
			"A+",
			tipoAMais.size()+tipoAMenos.size()+tipoOMais.size()+tipoOMenos.size());

		qtdPessoasPorTipoSanguineo.put(
			"A-",
			tipoAMenos.size()+tipoOMenos.size());

		qtdPessoasPorTipoSanguineo.put(
			"B+",
			tipoBMais.size()+tipoBMenos.size()+tipoOMais.size()+tipoOMenos.size());

		qtdPessoasPorTipoSanguineo.put(
			"B-",
			tipoBMenos.size()+tipoOMenos.size());

		qtdPessoasPorTipoSanguineo.put(
			"AB+",
			tipoAMais.size()+tipoAMenos.size()+tipoBMais.size()+tipoBMenos.size()+
			tipoABMais.size()+tipoABMenos.size()+tipoOMais.size()+tipoOMenos.size());

		qtdPessoasPorTipoSanguineo.put(
			"AB-",
			tipoAMenos.size()+tipoBMenos.size()+tipoOMenos.size()+tipoABMenos.size());

		qtdPessoasPorTipoSanguineo.put(
			"O+",
			tipoOMais.size()+tipoOMenos.size());

		qtdPessoasPorTipoSanguineo.put(
			"O-",
			tipoOMenos.size());

		return qtdPessoasPorTipoSanguineo;
	}

    private Map<String, BigDecimal> calcularImcMedioPorIdade(List<CandidatoDTO> dto) {
		Map<String, BigDecimal> listaMediaImc = new LinkedHashMap<>();

		List<CandidatoDTO> faixaMenorQueDezAnos = dto.stream()
                .filter(cand -> cand.getIdade() <= 10).collect(Collectors.toList());

        List<CandidatoDTO> faixaEntreOnzeEVinteAnos = dto.stream()
                .filter(cand -> cand.getIdade() >= 11 && cand.getIdade() <= 20)
				.collect(Collectors.toList());

        List<CandidatoDTO> faixaEntreVinteETrintaAnos = dto.stream()
                .filter(cand -> cand.getIdade() >= 21 && cand.getIdade() <= 30)
				.collect(Collectors.toList());

        List<CandidatoDTO> faixaEntreTrintaEQuarentaAnos = dto.stream()
                .filter(cand -> cand.getIdade() >= 31 && cand.getIdade() <= 40)
				.collect(Collectors.toList());

        List<CandidatoDTO> faixaEntreQuarentaECinquentaAnos = dto.stream()
                .filter(cand -> cand.getIdade() >= 41 && cand.getIdade() <= 50)
				.collect(Collectors.toList());

        List<CandidatoDTO> faixaEntreCinquentaESessentaAnos = dto.stream()
                .filter(cand -> cand.getIdade() >= 51 && cand.getIdade() <= 60)
				.collect(Collectors.toList());

        List<CandidatoDTO> faixaEntreSessentaESetentaAnos = dto.stream()
                .filter(cand -> cand.getIdade() >= 61 && cand.getIdade() <= 70)
				.collect(Collectors.toList());

        List<CandidatoDTO> faixaEntreSetentaEOitentaAnos = dto.stream()
                .filter(cand -> cand.getIdade() >= 61 && cand.getIdade() <= 70)
				.collect(Collectors.toList());

        List<CandidatoDTO> faixaEntreOitentaENoventaAnos = dto.stream()
                .filter(cand -> cand.getIdade() >= 81 && cand.getIdade() <= 90)
				.collect(Collectors.toList());

        listaMediaImc.put("0 a 10", BigDecimal.valueOf(
			calcularMediaImcPorFaixa(faixaMenorQueDezAnos)).setScale(2, RoundingMode.HALF_UP));

        listaMediaImc.put("11 a 20", BigDecimal.valueOf(
					calcularMediaImcPorFaixa(faixaEntreOnzeEVinteAnos)).setScale(2, RoundingMode.HALF_UP));

        listaMediaImc.put("21 a 30", BigDecimal.valueOf(
			calcularMediaImcPorFaixa(faixaEntreVinteETrintaAnos)).setScale(2, RoundingMode.HALF_UP));

        listaMediaImc.put("31 a 40", BigDecimal.valueOf(
			calcularMediaImcPorFaixa(faixaEntreTrintaEQuarentaAnos)).setScale(2, RoundingMode.HALF_UP));

        listaMediaImc.put("41 a 50", BigDecimal.valueOf(
			calcularMediaImcPorFaixa(faixaEntreQuarentaECinquentaAnos)).setScale(2, RoundingMode.HALF_UP));

        listaMediaImc.put("51 a 60", BigDecimal.valueOf(
			calcularMediaImcPorFaixa(faixaEntreCinquentaESessentaAnos)).setScale(2, RoundingMode.HALF_UP));

        listaMediaImc.put("61 a 70", BigDecimal.valueOf(
			calcularMediaImcPorFaixa(faixaEntreSessentaESetentaAnos)).setScale(2, RoundingMode.HALF_UP));

        listaMediaImc.put("71 a 80", BigDecimal.valueOf(
			calcularMediaImcPorFaixa(faixaEntreSetentaEOitentaAnos)).setScale(2, RoundingMode.HALF_UP));

        listaMediaImc.put("81 a 90", BigDecimal.valueOf(
			calcularMediaImcPorFaixa(faixaEntreOitentaENoventaAnos)).setScale(2, RoundingMode.HALF_UP));

		return listaMediaImc;
    }

	private Double calcularMediaImcPorFaixa(List<CandidatoDTO> faixa) {
		return faixa.stream()
				.mapToDouble(CandidatoDTO::getImc)
				.average()
				.orElse(0.00);
	}

	private Double calcularMediaIdadePorTipoSanguineo(List<CandidatoDTO> tipo) {
		return tipo.stream()
				.mapToDouble(CandidatoDTO::getIdade)
				.average()
				.orElse(0);
	}

	private List<CandidatoDTO> recuperarCandidatosPorTipoSanguineo(List<CandidatoDTO> dto, String tipo) {
		return dto.stream()
				.filter(cand -> cand.getTipoSanguineo().equals(tipo))
				.collect(Collectors.toList());
	}
    
    
}
