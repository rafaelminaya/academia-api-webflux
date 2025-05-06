package com.rminaya.repository;

import com.rminaya.model.EstudianteDocument;
import reactor.core.publisher.Flux;

public interface EstudianteRepository extends GenericRepository<EstudianteDocument, String> {
    Flux<EstudianteDocument> findAllByOrderByEdadAsc();
    Flux<EstudianteDocument> findAllByOrderByEdadDesc();
}
