package com.example.demo.repository;

import com.example.demo.model.ZipCodes;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Flux;

public interface ZipCodesRepository extends ReactiveMongoRepository<ZipCodes, String> {

    Flux<ZipCodes> findAllByState(String state);
}
