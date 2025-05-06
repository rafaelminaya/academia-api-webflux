package com.rminaya.service.impl;

import com.rminaya.repository.GenericRepository;
import com.rminaya.service.CrudService;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public abstract class CrudServiceImpl<T, ID> implements CrudService<T, ID> {

    protected abstract GenericRepository<T, ID> getRepository();

    @Override
    public Flux<T> findAll() {
        return getRepository().findAll();
    }

    @Override
    public Mono<T> findById(ID id) {
        return getRepository().findById(id);
    }

    @Override
    public Mono<T> save(T t) {
        return getRepository().save(t);
    }

    @Override
    public Mono<T> update(ID id, T t) {
        return getRepository().findById(id).flatMap(e -> getRepository().save(t));
    }

    @Override
    public Mono<Boolean> delete(ID id) {
        return getRepository().findById(id)
                .hasElement()
                .flatMap(aBoolean -> {
                    if (aBoolean) {
                        return getRepository().deleteById(id).thenReturn(true);
                    } else {
                        return Mono.just(false);
                    }
                });
    }
}
