package br.com.testewk.service;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.testewk.dto.CandidatoDTO;
import br.com.testewk.dto.ResponseDTO;
import br.com.testewk.impl.DadosCandidatosImpl;
import br.com.testewk.model.Candidato;
import br.com.testewk.repository.CandidatoRepository;

@Service
public class InfoCandidatosService {

	@Autowired
	DadosCandidatosImpl dadosCandidatosImpl;

    @Autowired
    CandidatoRepository repository;

    @Autowired
    ModelMapper modelMapper;

    public ResponseDTO processarInfoCandidatos(List<CandidatoDTO> dto) {
        ResponseDTO response = new ResponseDTO();
        popularEntidadesNoBanco(dto);
        response.setQtdPessoasPorEstado(dadosCandidatosImpl.calcularNumeroDePessoasPorEstado(dto));
        response.setMediaImcPorIdade(dadosCandidatosImpl.recuperarIMCMedioPorIdade(dto));
        response.setPercentuaisObesidadePorSexo(dadosCandidatosImpl.recuperarPecentualObesidade(dto));
		response.setMediaIdadePorTipoSanguineo(dadosCandidatosImpl.recuperarMediaIdadePorTipoSanguineo(dto));
		response.setQtdDoadoresPorTipoSanguineo(dadosCandidatosImpl.recuperarQtdDoadoresPorTipoSanguineo(dto));
        return response;
    }

	private void popularEntidadesNoBanco(List<CandidatoDTO> dto) {
		dto.forEach(candidato -> {
			repository.save(modelMapper.map(candidato, Candidato.class));
		});
	}
    
    
}
