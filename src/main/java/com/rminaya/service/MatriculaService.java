package com.rminaya.service;

import com.rminaya.model.MatriculaDocument;
import reactor.core.publisher.Mono;

public interface MatriculaService {
    Mono<MatriculaDocument> save(MatriculaDocument document);
}
