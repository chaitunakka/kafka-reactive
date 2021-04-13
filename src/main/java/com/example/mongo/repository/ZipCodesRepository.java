package com.example.mongo.repository;

import com.example.mongo.model.ZipCodes;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Flux;

public interface ZipCodesRepository extends ReactiveMongoRepository<ZipCodes, String> {

    Flux<ZipCodes> findAllByState(String state);
}
