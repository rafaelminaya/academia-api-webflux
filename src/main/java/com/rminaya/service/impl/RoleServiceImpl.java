package com.rminaya.service.impl;

import com.rminaya.model.RoleDocument;
import com.rminaya.repository.GenericRepository;
import com.rminaya.repository.RoleRepository;
import com.rminaya.service.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RoleServiceImpl extends CrudServiceImpl<RoleDocument, String> implements RoleService {

    private final RoleRepository roleRepository;

    @Override
    protected GenericRepository<RoleDocument, String> getRepository() {
        return roleRepository;
    }
}
