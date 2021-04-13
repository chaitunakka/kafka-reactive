package com.example.couchbase.repository;

import com.example.couchbase.model.Beer;
import org.springframework.data.couchbase.repository.ReactiveCouchbaseRepository;

public interface BeerRepository extends ReactiveCouchbaseRepository<Beer, String> {
}
