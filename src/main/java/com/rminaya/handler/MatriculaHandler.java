package com.rminaya.handler;

import com.rminaya.dto.MatriculaDTO;
import com.rminaya.model.MatriculaDocument;
import com.rminaya.service.MatriculaService;
import com.rminaya.validator.RequestValidator;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.net.URI;
import java.time.LocalDateTime;

import static org.springframework.web.reactive.function.BodyInserters.fromValue;

@Component
@RequiredArgsConstructor
public class MatriculaHandler {

    private final MatriculaService matriculaService;
    @Qualifier("matriculaMapper")
    private final ModelMapper mapper;
    private final RequestValidator validator;

    public Mono<ServerResponse> save(ServerRequest request) {
        Mono<MatriculaDTO> matriculaDTOMono = request.bodyToMono(MatriculaDTO.class);
        String uri = request.uri().toString();

        return matriculaDTOMono
                .flatMap(validator::validate)
                .map(dto -> {
                    dto.setFechaMatricula(LocalDateTime.now());
                    return dto;
                })
                .map(this::convertToDocument)
                .flatMap(matriculaService::save)
                .map(this::convertToDTO)
                .flatMap(dto -> ServerResponse
                        .created(URI.create(uri.concat("/").concat(dto.getId())))
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(fromValue(dto))
                );
    }

    private MatriculaDTO convertToDTO(MatriculaDocument document) {
        return mapper.map(document, MatriculaDTO.class);
    }

    private MatriculaDocument convertToDocument(MatriculaDTO dto) {
        return mapper.map(dto, MatriculaDocument.class);
    }

}
