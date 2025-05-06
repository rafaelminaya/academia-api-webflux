package com.rminaya.handler;

import com.rminaya.dto.CursoDTO;
import com.rminaya.dto.EstudianteDTO;
import com.rminaya.model.EstudianteDocument;
import com.rminaya.service.EstudianteService;
import com.rminaya.validator.RequestValidator;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.net.URI;

import static org.springframework.web.reactive.function.BodyInserters.fromValue;

@Component
@RequiredArgsConstructor
public class EstudianteHandler {

    private final EstudianteService estudianteService;
    @Qualifier("estudianteMapper")
    private final ModelMapper mapper;
    private final RequestValidator validator;

    public Mono<ServerResponse> findAll(ServerRequest request) {
        Flux<EstudianteDTO> flx = estudianteService.findAll().map(this::convertToDTO);

        return ServerResponse
                .ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(flx, CursoDTO.class);
    }

    public Mono<ServerResponse> findAllByOrderByEdadAsc(ServerRequest request) {
        Flux<EstudianteDTO> flx = estudianteService.findAllByOrderByEdadAsc().map(this::convertToDTO);

        return ServerResponse
                .ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(flx, CursoDTO.class);
    }

    public Mono<ServerResponse> findAllByOrderByEdadDesc(ServerRequest request) {
        Flux<EstudianteDTO> flx = estudianteService.findAllByOrderByEdadDesc().map(this::convertToDTO);

        return ServerResponse
                .ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(flx, CursoDTO.class);
    }


    public Mono<ServerResponse> findById(ServerRequest request) {
        String idEstudiante = request.pathVariable("id");

        return estudianteService.findById(idEstudiante)
                .map(this::convertToDTO)
                .flatMap(dto -> ServerResponse
                        .ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(fromValue(dto))
                )
                .switchIfEmpty(ServerResponse.notFound().build());
    }

    public Mono<ServerResponse> save(ServerRequest request) {
        Mono<EstudianteDTO> estudianteDTOMono = request.bodyToMono(EstudianteDTO.class);
        String uri = request.uri().toString();

        return estudianteDTOMono
                .flatMap(validator::validate)
                .map(this::convertToDocument)
                .flatMap(estudianteService::save)
                .map(this::convertToDTO)
                .flatMap(dto -> ServerResponse
                        .created(URI.create(uri.concat("/").concat(dto.getId())))
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(fromValue(dto))
                );
    }

    public Mono<ServerResponse> update(ServerRequest request) {
        String idEstudiante = request.pathVariable("id");
        Mono<EstudianteDTO> estudianteDTOMono = request.bodyToMono(EstudianteDTO.class);

        return estudianteDTOMono
                .flatMap(validator::validate)
                .map(dto -> {
                    dto.setId(idEstudiante);
                    return dto;
                })
                .map(this::convertToDocument)
                .flatMap(document -> estudianteService.update(idEstudiante, document))
                .map(this::convertToDTO)
                .flatMap(dto -> ServerResponse
                        .ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(fromValue(dto))
                )
                .switchIfEmpty(ServerResponse.notFound().build());
    }

    public Mono<ServerResponse> delete(ServerRequest request) {
        String idEstudiante = request.pathVariable("id");

        return estudianteService.delete(idEstudiante)
                .flatMap(aBoolean -> {
                    if (aBoolean) {
                        return ServerResponse.noContent().build();
                    } else {
                        return ServerResponse.notFound().build();
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
