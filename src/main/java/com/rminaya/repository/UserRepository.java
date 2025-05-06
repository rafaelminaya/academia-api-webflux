package com.rminaya.repository;

import com.rminaya.model.UserDocument;
import reactor.core.publisher.Mono;

public interface UserRepository extends GenericRepository<UserDocument, String> {
    Mono<UserDocument> findOneByUsername(String username);
}
