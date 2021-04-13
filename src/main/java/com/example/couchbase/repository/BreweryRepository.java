package com.example.couchbase.repository;

import com.example.couchbase.model.Brewery;
import org.springframework.data.couchbase.repository.ReactiveCouchbaseRepository;

public interface BreweryRepository extends ReactiveCouchbaseRepository<Brewery, String> {
}
