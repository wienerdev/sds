package br.com.testewk.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.testewk.dto.CandidatoDTO;
import br.com.testewk.dto.ResponseDTO;
import br.com.testewk.service.CandidatosService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("candidatos")
public class CandidatosController {

    @Autowired
    CandidatosService service;

    @GetMapping("/getAllInformacoes")
    public ResponseEntity<ResponseDTO> processarInformacoes(@RequestBody @Valid List<CandidatoDTO> dto) {
        ResponseDTO response = service.recuperarInformacoes(dto);
        return ResponseEntity.ok(response);
    }
    
}
