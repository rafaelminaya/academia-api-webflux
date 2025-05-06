package com.rminaya.controller;

import com.rminaya.dto.MatriculaDTO;
import com.rminaya.model.MatriculaDocument;
import com.rminaya.service.MatriculaService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.net.URI;
import java.time.LocalDateTime;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/matriculas")
public class MatriculaController {

    private final MatriculaService service;
    @Qualifier("matriculaMapper")
    private final ModelMapper mapper;

    @PostMapping
    public Mono<ResponseEntity<MatriculaDTO>> save(@Valid @RequestBody MatriculaDTO matriculaDTO, final ServerHttpRequest req) {
        return Mono.just(matriculaDTO)
                .map(dto -> {
                    dto.setFechaMatricula(LocalDateTime.now());
                    return dto;
                })
                .flatMap(dto -> service.save(this.convertToDocument(dto)))
                .map(this::convertToDTO)
                .map(dto -> ResponseEntity
                        .created(URI.create(req.getURI().toString().concat("/").concat(dto.getId())))
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(dto)
                );
    }

    private MatriculaDTO convertToDTO(MatriculaDocument document) {
        return mapper.map(document, MatriculaDTO.class);
    }

    private MatriculaDocument convertToDocument(MatriculaDTO dto) {
        return mapper.map(dto, MatriculaDocument.class);
    }
}
