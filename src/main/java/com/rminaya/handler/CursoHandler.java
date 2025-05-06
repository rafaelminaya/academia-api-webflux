package com.rminaya.handler;

import com.rminaya.dto.CursoDTO;
import com.rminaya.model.CursoDocument;
import com.rminaya.service.CursoService;
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
public class CursoHandler {

    private final CursoService cursoService;
    @Qualifier("cursoMapper")
    private final ModelMapper mapper;
    private final RequestValidator validator;

    public Mono<ServerResponse> findAll(ServerRequest request) {
        Flux<CursoDTO> flx = cursoService.findAll().map(this::convertToDTO);

        return ServerResponse
                .ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(flx, CursoDTO.class);
    }

    public Mono<ServerResponse> findById(ServerRequest request) {
        String idCurso = request.pathVariable("id");

        return cursoService.findById(idCurso)
                .map(this::convertToDTO)
                .flatMap(dto -> ServerResponse
                        .ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(fromValue(dto))
                )
                .switchIfEmpty(ServerResponse.notFound().build());
    }

    public Mono<ServerResponse> save(ServerRequest request) {
        Mono<CursoDTO> cursoDTOMono = request.bodyToMono(CursoDTO.class);
        String uri = request.uri().toString();

        return cursoDTOMono
                .flatMap(validator::validate)
                .map(this::convertToDocument)
                .flatMap(cursoService::save)
                .map(this::convertToDTO)
                .flatMap(dto -> ServerResponse
                        .created(URI.create(uri.concat("/").concat(dto.getId())))
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(fromValue(dto))
                );
    }

    public Mono<ServerResponse> update(ServerRequest request) {
        String idCurso = request.pathVariable("id");
        Mono<CursoDTO> cursoDTOMono = request.bodyToMono(CursoDTO.class);

        return cursoDTOMono
                .flatMap(validator::validate)
                .map(dto -> {
                    dto.setId(idCurso);
                    return dto;
                })
                .map(this::convertToDocument)
                .flatMap(document -> cursoService.update(idCurso, document))
                .map(this::convertToDTO)
                .flatMap(dto -> ServerResponse
                        .ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(fromValue(dto))
                )
                .switchIfEmpty(ServerResponse.notFound().build());
    }

    public Mono<ServerResponse> delete(ServerRequest request) {
        String idCurso = request.pathVariable("id");

        return cursoService.delete(idCurso)
                .flatMap(aBoolean -> {
                    if (aBoolean) {
                        return ServerResponse.noContent().build();
                    } else {
                        return ServerResponse.notFound().build();
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
