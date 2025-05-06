package com.rminaya.service.impl;

import com.rminaya.model.MatriculaDocument;
import com.rminaya.repository.MatriculaRepository;
import com.rminaya.service.MatriculaService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class MatriculaServiceImpl implements MatriculaService {

    private final MatriculaRepository matriculaRepository;

    @Override
    public Mono<MatriculaDocument> save(MatriculaDocument document) {
        return matriculaRepository.save(document);
    }
}
