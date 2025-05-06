package com.rminaya.controller;

import com.rminaya.dto.CursoDTO;
import com.rminaya.model.CursoDocument;
import com.rminaya.service.CursoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.net.URI;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/cursos")
public class CursoController {

    private final CursoService cursoService;
    @Qualifier("cursoMapper")
    private final ModelMapper mapper;

    @GetMapping
    public Mono<ResponseEntity<Flux<CursoDTO>>> findAll() {
        Flux<CursoDTO> flux = cursoService.findAll().map(this::convertToDTO);
        return Mono.just(ResponseEntity
                .ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(flux)
        );
    }

    @GetMapping("/{id}")
    public Mono<ResponseEntity<CursoDTO>> findById(@PathVariable("id") String idCurso) {

        return cursoService.findById(idCurso)
                .map(this::convertToDTO)
                .map(dto -> ResponseEntity
                        .ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(dto)
                )
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @PostMapping
    public Mono<ResponseEntity<CursoDTO>> save(@Valid @RequestBody CursoDTO cursoDTO, final ServerHttpRequest req) {
        return cursoService.save(this.convertToDocument(cursoDTO))
                .map(this::convertToDTO)
                .map(dto -> ResponseEntity
                        .created(URI.create(req.getURI().toString().concat("/").concat(dto.getId())))
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(dto)
                );
    }

    @PutMapping("/{id}")
    public Mono<ResponseEntity<CursoDTO>> update(@PathVariable("id") String idCurso, @Valid @RequestBody CursoDTO cursoDTO) {
        return Mono.just(cursoDTO)
                .map(dto -> {
                    dto.setId(idCurso);
                    return dto;
                })
                .flatMap(e -> cursoService.update(idCurso, convertToDocument(cursoDTO)))
                .map(this::convertToDTO)
                .map(dto -> ResponseEntity
                        .ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(dto)
                )
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public Mono<ResponseEntity<Void>> delete(@PathVariable("id") String idCurso) {
        return cursoService.delete(idCurso)
                .flatMap(aBoolean -> {
                    if (aBoolean) {
                        return Mono.just(ResponseEntity.noContent().build());
                    } else {
                        return Mono.just(ResponseEntity.notFound().build());
                    }
                });
    }

    private CursoDTO convertToDTO(CursoDocument document) {
        return mapper.map(document, CursoDTO.class);
    }

    private CursoDocument convertToDocument(CursoDTO dto) {
        return mapper.map(dto, CursoDocument.class);
    }
}
