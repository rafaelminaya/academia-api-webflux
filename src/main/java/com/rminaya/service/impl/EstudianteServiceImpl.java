package com.rminaya.service.impl;

import com.rminaya.model.EstudianteDocument;
import com.rminaya.repository.EstudianteRepository;
import com.rminaya.repository.GenericRepository;
import com.rminaya.service.EstudianteService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

@Service
@RequiredArgsConstructor
public class EstudianteServiceImpl extends CrudServiceImpl<EstudianteDocument, String> implements EstudianteService {

    private final EstudianteRepository estudianteRepository;

    @Override
    protected GenericRepository<EstudianteDocument, String> getRepository() {
        return estudianteRepository;
    }

    @Override
    public Flux<EstudianteDocument> findAllByOrderByEdadAsc() {
        return estudianteRepository.findAllByOrderByEdadAsc();
    }

    @Override
    public Flux<EstudianteDocument> findAllByOrderByEdadDesc() {
        return estudianteRepository.findAllByOrderByEdadDesc();
    }
}
