package br.com.testewk.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.testewk.dto.CandidatoDTO;
import br.com.testewk.dto.ResponseDTO;
import br.com.testewk.service.CandidatosService;
import br.com.testewk.service.InfoCandidatosService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("candidatos")
public class InfoCandidatosController {

    @Autowired
    InfoCandidatosService infoCandidatosService;

    @Autowired
    CandidatosService candidatosService;

    @CrossOrigin
    @GetMapping("/informacoes")
    public ResponseEntity<Object> recuperarInformacoes() {
        Object response = candidatosService.recuperarInfoCandidatos();
        return ResponseEntity.ok(response);
    }

    @CrossOrigin
    @PostMapping("/informacoes/processar")
    public ResponseEntity<ResponseDTO> processarInformacoes(@RequestBody @Valid List<CandidatoDTO> dto) {
        ResponseDTO response = infoCandidatosService.processarInfoCandidatos(dto);
        return ResponseEntity.ok(response);
    }
    
}
