package com.rminaya.controller;

import com.rminaya.dto.EstudianteDTO;
import com.rminaya.model.EstudianteDocument;
import com.rminaya.service.EstudianteService;
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
@RequestMapping("/v1/estudiantes")
public class EstudianteController {

    private final EstudianteService estudianteService;
    @Qualifier("estudianteMapper")
    private final ModelMapper mapper;

    @GetMapping
    public Mono<ResponseEntity<Flux<EstudianteDTO>>> findAll() {
        Flux<EstudianteDTO> flux = estudianteService.findAll().map(this::convertToDTO);
        return Mono.just(ResponseEntity
                .ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(flux)
        );
    }

    @GetMapping("/{id}")
    public Mono<ResponseEntity<EstudianteDTO>> findById(@PathVariable("id") String idEstudiante) {

        return estudianteService.findById(idEstudiante)
                .map(this::convertToDTO)
                .map(estudianteDTO -> ResponseEntity
                        .ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(estudianteDTO)
                )
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @GetMapping("/por-edad-asc")
    public Mono<ResponseEntity<Flux<EstudianteDTO>>> findAllByEdadAsc() {
        Flux<EstudianteDTO> flux = estudianteService.findAllByOrderByEdadAsc().map(this::convertToDTO);
        return Mono.just(ResponseEntity
                .ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(flux)
        );
    }

    @GetMapping("/por-edad-desc")
    public Mono<ResponseEntity<Flux<EstudianteDTO>>> findAllByEdadDesc() {
        Flux<EstudianteDTO> flux = estudianteService.findAllByOrderByEdadDesc().map(this::convertToDTO);
        return Mono.just(ResponseEntity
                .ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(flux)
        );
    }

    @PostMapping
    public Mono<ResponseEntity<EstudianteDTO>> save(@Valid @RequestBody EstudianteDTO estudianteDTO, final ServerHttpRequest req) {
        return estudianteService.save(this.convertToDocument(estudianteDTO))
                .map(this::convertToDTO)
                .map(dto -> ResponseEntity
                        .created(URI.create(req.getURI().toString().concat("/").concat(dto.getId())))
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(dto)
                );
    }

    @PutMapping("/{id}")
    public Mono<ResponseEntity<EstudianteDTO>> update(@PathVariable("id") String idEstudiante, @Valid @RequestBody EstudianteDTO estudianteDTO) {
        return Mono.just(estudianteDTO)
                .map(dto -> {
                    dto.setId(idEstudiante);
                    return dto;
                })
                .flatMap(dto -> estudianteService.update(idEstudiante, convertToDocument(dto)))
                .map(this::convertToDTO)
                .map(dto -> ResponseEntity
                        .ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(dto)
                )
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public Mono<ResponseEntity<Void>> delete(@PathVariable("id") String idEstudiante) {
        return estudianteService.delete(idEstudiante)
                .flatMap(aBoolean -> {
                    if (aBoolean) {
                        return Mono.just(ResponseEntity.noContent().build());
                    } else {
                        return Mono.just(ResponseEntity.notFound().build());
                    }
                });
    }

    private EstudianteDTO convertToDTO(EstudianteDocument document) {
        return mapper.map(document, EstudianteDTO.class);
    }

    private EstudianteDocument convertToDocument(EstudianteDTO dto) {
        return mapper.map(dto, EstudianteDocument.class);
    }
}
