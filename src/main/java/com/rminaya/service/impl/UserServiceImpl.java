package com.rminaya.service.impl;

import com.rminaya.model.RoleDocument;
import com.rminaya.model.UserDocument;
import com.rminaya.repository.GenericRepository;
import com.rminaya.repository.RoleRepository;
import com.rminaya.repository.UserRepository;
import com.rminaya.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class UserServiceImpl extends CrudServiceImpl<UserDocument, String> implements UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final BCryptPasswordEncoder bcrypt;

    @Override
    protected GenericRepository<UserDocument, String> getRepository() {
        return userRepository;
    }

    @Override
    public Mono<UserDocument> saveHash(UserDocument user) {
        user.setPassword(bcrypt.encode(user.getPassword()));
        return userRepository.save(user);
    }

    @Override
    public Mono<com.rminaya.security.User> searchByUser(String username) {
        return userRepository.findOneByUsername(username)
                .flatMap(user -> Flux.fromIterable(user.getRoles())
                        .flatMap(userRole -> roleRepository.findById(userRole.getId())
                                .map(RoleDocument::getName))
                        .collectList()
                        .map(roles -> new com.rminaya.security.User(user.getUsername(), user.getPassword(), user.isStatus(), roles))
                );
    }
}
