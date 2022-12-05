package br.com.testewk.impl;

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

import org.springframework.stereotype.Component;

import br.com.testewk.dto.CandidatoDTO;

@Component
public class DadosCandidatosImpl {

    public Map<String, Integer> calcularNumeroDePessoasPorEstado(List<CandidatoDTO> dto) {
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

	public Map<String, BigDecimal> recuperarIMCMedioPorIdade(List<CandidatoDTO> dto) {
		DateTimeFormatter dateformatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

		dto.forEach(cand -> {
			cand.setImc(cand.getPeso() / Math.pow(cand.getAltura(), 2));
			cand.setIdade(Period.between(LocalDate.parse(cand.getDtNascimento(), dateformatter), LocalDate.now()).getYears());
		});

		return calcularImcMedioPorIdade(dto);
	}

	public Map<String, Double> recuperarPecentualObesidade(List<CandidatoDTO> dto) {
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

	public Map<String, BigDecimal> recuperarMediaIdadePorTipoSanguineo(List<CandidatoDTO> dto) {
		Map<String, BigDecimal> mediaIdadePorTipoSangue = new LinkedHashMap<>();

		mediaIdadePorTipoSangue.put("A+", BigDecimal.valueOf(
			calcularMediaIdadePorTipoSanguineo(dto, "A+")).setScale(2, RoundingMode.HALF_UP));

		mediaIdadePorTipoSangue.put("A-", BigDecimal.valueOf(
			calcularMediaIdadePorTipoSanguineo(dto, "A-")).setScale(2, RoundingMode.HALF_UP));

		mediaIdadePorTipoSangue.put("B+", BigDecimal.valueOf(
			calcularMediaIdadePorTipoSanguineo(dto, "B+")).setScale(2, RoundingMode.HALF_UP));

		mediaIdadePorTipoSangue.put("B-", BigDecimal.valueOf(
			calcularMediaIdadePorTipoSanguineo(dto, "B-")).setScale(2, RoundingMode.HALF_UP));

		mediaIdadePorTipoSangue.put("AB+", BigDecimal.valueOf(
			calcularMediaIdadePorTipoSanguineo(dto, "AB+")).setScale(2, RoundingMode.HALF_UP));

		mediaIdadePorTipoSangue.put("AB-", BigDecimal.valueOf(
			calcularMediaIdadePorTipoSanguineo(dto, "AB-")).setScale(2, RoundingMode.HALF_UP));

		mediaIdadePorTipoSangue.put("O+", BigDecimal.valueOf(
			calcularMediaIdadePorTipoSanguineo(dto, "O+")).setScale(2, RoundingMode.HALF_UP));

		mediaIdadePorTipoSangue.put("O-", BigDecimal.valueOf(
			calcularMediaIdadePorTipoSanguineo(dto, "O-")).setScale(2, RoundingMode.HALF_UP));

		return mediaIdadePorTipoSangue;
	}

	public Map<String, Integer> recuperarQtdDoadoresPorTipoSanguineo(List<CandidatoDTO> dto) {
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

		listaMediaImc.put("0 a 10", BigDecimal.valueOf(
				calcularMediaImcPorFaixa(dto, 0, 10)).setScale(2, RoundingMode.HALF_UP));

		listaMediaImc.put("11 a 20", BigDecimal.valueOf(
				calcularMediaImcPorFaixa(dto, 11, 20)).setScale(2, RoundingMode.HALF_UP));

		listaMediaImc.put("21 a 30", BigDecimal.valueOf(
				calcularMediaImcPorFaixa(dto, 21, 30)).setScale(2, RoundingMode.HALF_UP));

		listaMediaImc.put("31 a 40", BigDecimal.valueOf(
				calcularMediaImcPorFaixa(dto, 31, 40)).setScale(2, RoundingMode.HALF_UP));

		listaMediaImc.put("41 a 50", BigDecimal.valueOf(
				calcularMediaImcPorFaixa(dto, 41, 50)).setScale(2, RoundingMode.HALF_UP));

		listaMediaImc.put("51 a 60", BigDecimal.valueOf(
				calcularMediaImcPorFaixa(dto, 51, 60)).setScale(2, RoundingMode.HALF_UP));

		listaMediaImc.put("61 a 70", BigDecimal.valueOf(
				calcularMediaImcPorFaixa(dto, 61, 70)).setScale(2, RoundingMode.HALF_UP));

		listaMediaImc.put("71 a 80", BigDecimal.valueOf(
				calcularMediaImcPorFaixa(dto, 71, 80)).setScale(2, RoundingMode.HALF_UP));

		listaMediaImc.put("81 a 90", BigDecimal.valueOf(
				calcularMediaImcPorFaixa(dto, 81, 90)).setScale(2, RoundingMode.HALF_UP));

		return listaMediaImc;
    }

	private Double calcularMediaImcPorFaixa(List<CandidatoDTO> dto, Integer idadeInicial, Integer idadeFinal) {
		return dto.stream()
				.filter(cand -> cand.getIdade() >= idadeInicial && cand.getIdade() <= idadeFinal)
				.mapToDouble(CandidatoDTO::getImc)
				.average()
				.orElse(0.00);
	}

	private Double calcularMediaIdadePorTipoSanguineo(List<CandidatoDTO> dto, String tipoSanguineo) {
		return dto.stream()
				.filter(cand -> cand.getTipoSanguineo().equals(tipoSanguineo) && isCandidatoApto(cand))
				.mapToDouble(CandidatoDTO::getIdade)
				.average()
				.orElse(0.00);
	}

	private List<CandidatoDTO> recuperarCandidatosPorTipoSanguineo(List<CandidatoDTO> dto, String tipo) {
		return dto.stream()
				.filter(cand -> cand.getTipoSanguineo().equals(tipo) && isCandidatoApto(cand))
				.collect(Collectors.toList());
	}

	private boolean isCandidatoApto(CandidatoDTO cand) {
		return cand.getIdade() >= 16 && cand.getIdade() <= 69 && cand.getPeso() >= 50;
	}
    
}
