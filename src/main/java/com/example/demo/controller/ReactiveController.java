package com.example.demo.controller;

import com.example.demo.model.ZipCodes;
import com.example.demo.repository.ZipCodesRepository;
import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.kafka.receiver.KafkaReceiver;

import java.time.Duration;
import java.time.LocalTime;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/reactive")
public class ReactiveController {

    private final ZipCodesRepository zipCodesRepository;
    private final KafkaReceiver<String, String> kafkaReceiver;

    @GetMapping(produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<ZipCodes> getAllReactive() {
        return zipCodesRepository.findAll().delayElements(Duration.ofMillis(1000)).log().limitRequest(10);
    }

    @GetMapping(value = "nr")
    public Mono<List<ZipCodes>> getAll() {
        return zipCodesRepository.findAllByState("MA").delayElements(Duration.ofMillis(1000)).log().limitRequest(10)
                .collectList();
    }

    @GetMapping(value = "/zips/{state}", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<ZipCodes> getByStates(String state) {
        return zipCodesRepository.findAllByState(state).delayElements(Duration.ofMillis(500)).log();
    }

    @GetMapping(path = "/kafka", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<String> streamKafka() {
        return kafkaReceiver.receive().checkpoint("Receiving messages from topic")
                .log()
                .doOnNext(r -> r.receiverOffset().acknowledge())
                .map(ConsumerRecord::value)
                .checkpoint("Done Receiving messages from topic");
    }
}
