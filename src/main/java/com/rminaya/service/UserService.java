package com.rminaya.service;

import com.rminaya.model.UserDocument;
import reactor.core.publisher.Mono;

public interface UserService extends CrudService<UserDocument, String> {

    Mono<UserDocument> saveHash(UserDocument user);
    Mono<com.rminaya.security.User> searchByUser(String username);
}
