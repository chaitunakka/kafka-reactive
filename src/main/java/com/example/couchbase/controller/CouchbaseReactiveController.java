package com.example.couchbase.controller;

import com.example.couchbase.model.Beer;
import com.example.couchbase.model.Brewery;
import com.example.couchbase.repository.BeerRepository;
import com.example.couchbase.repository.BreweryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

import java.time.Duration;

@RestController
@RequestMapping("/couch")
@RequiredArgsConstructor
public class CouchbaseReactiveController {

    private final BeerRepository beerRepository;
    private final BreweryRepository breweryRepository;

    @GetMapping("/beers")
    public Flux<Beer> getBeers() {
        return beerRepository.findAll().limitRequest(10).log().delayElements(Duration.ofSeconds(1));
    }

    @GetMapping("/breweries")
    public Flux<Brewery> getBreweries() {
        return breweryRepository.findAll().limitRequest(10).log().delayElements(Duration.ofSeconds(1));
    }
}
