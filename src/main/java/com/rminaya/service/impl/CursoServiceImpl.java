package com.rminaya.service.impl;

import com.rminaya.model.CursoDocument;
import com.rminaya.repository.CursoRepository;
import com.rminaya.repository.GenericRepository;
import com.rminaya.service.CursoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CursoServiceImpl extends CrudServiceImpl<CursoDocument, String> implements CursoService {

    private final CursoRepository cursoRepository;

    @Override
    protected GenericRepository<CursoDocument, String> getRepository() {
        return cursoRepository;
    }
}
