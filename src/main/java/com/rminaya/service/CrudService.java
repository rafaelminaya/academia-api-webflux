package com.rminaya.service;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface CrudService<T, ID> {
    Flux<T> findAll();
    Mono<T> findById(ID id);
    Mono<T> save(T t);
    Mono<T> update(ID id, T t);
    Mono<Boolean> delete(ID id);
}
