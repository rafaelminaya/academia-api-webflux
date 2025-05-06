package com.rminaya.service;

import com.rminaya.model.EstudianteDocument;
import reactor.core.publisher.Flux;

public interface EstudianteService extends CrudService<EstudianteDocument, String> {
    Flux<EstudianteDocument> findAllByOrderByEdadAsc();
    Flux<EstudianteDocument> findAllByOrderByEdadDesc();
}
